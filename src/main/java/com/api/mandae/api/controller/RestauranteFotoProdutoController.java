package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.fotoproduto.FotoProdutoConverter;
import com.api.mandae.api.model.FotoProdutoDTO;
import com.api.mandae.api.model.input.FotoProdutoInput;
import com.api.mandae.api.openapi.controller.RestauranteFotoProdutoControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.domain.exception.EntidadeNaoEncontradaException;
import com.api.mandae.domain.model.FotoProduto;
import com.api.mandae.domain.model.Produto;
import com.api.mandae.domain.service.CadastroProdutoService;
import com.api.mandae.domain.service.CatalogoFotoProdutoService;
import com.api.mandae.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces =
        MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFotoProdutoController implements RestauranteFotoProdutoControllerOpenApi {

    @Autowired
    private FotoProdutoConverter fotoProdutoConverter;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private FotoStorageService fotoStorage;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput, @RequestPart(required = true) MultipartFile arquivo) throws IOException {

        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        //MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
        foto.setTamanho(arquivo.getSize());

        FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

        return fotoProdutoConverter.toModel(fotoSalva);

    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoConverter.toModel(fotoProduto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping
    public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProduto.remover(restauranteId, produtoId);
    }

    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servir(@PathVariable Long restauranteId,
            @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) {

        try {
            FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());
            if (fotoRecuperada.temUrl()) {

                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();

            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));

            }
        } catch (EntidadeNaoEncontradaException | HttpMediaTypeNotAcceptableException e) {
            return ResponseEntity.notFound()
                    .build();
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
            List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

//    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
//            @Valid FotoProdutoInput fotoProdutoInput) {
//
//        var nomeArquivo = UUID.randomUUID()
//                              .toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
//        var arquivoFoto = Path.of("/Users/paulo.h.marcusso/Documents/Estudos/Java/Spring/imagens", nomeArquivo);
//
//        System.out.println(fotoProdutoInput.getDescricao());
//        System.out.println(arquivoFoto);
//        System.out.println(fotoProdutoInput.getArquivo().getContentType());
//
//        try {
//            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
