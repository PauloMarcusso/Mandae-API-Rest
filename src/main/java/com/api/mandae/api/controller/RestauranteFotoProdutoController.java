package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.fotoproduto.FotoProdutoConverter;
import com.api.mandae.api.model.FotoProdutoDTO;
import com.api.mandae.api.model.input.FotoProdutoInput;
import com.api.mandae.domain.model.FotoProduto;
import com.api.mandae.domain.model.Produto;
import com.api.mandae.domain.service.CadastroProdutoService;
import com.api.mandae.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @Autowired
    private FotoProdutoConverter fotoProdutoConverter;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
        foto.setTamanho(arquivo.getSize());

        FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

        return fotoProdutoConverter.toDTO(fotoSalva);

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
