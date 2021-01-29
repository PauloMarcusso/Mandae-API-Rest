package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.pedido.PedidoConverter;
import com.api.mandae.api.assembler.pedido.PedidoResumoConverter;
import com.api.mandae.api.model.PedidoDTO;
import com.api.mandae.api.model.PedidoResumoDTO;
import com.api.mandae.api.model.input.PedidoInput;
import com.api.mandae.api.openapi.controller.PedidoControllerOpenApi;
import com.api.mandae.core.data.PageWrapper;
import com.api.mandae.core.data.PageableTranslator;
import com.api.mandae.domain.exception.EntidadeNaoEncontradaException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.filter.PedidoFilter;
import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.model.Usuario;
import com.api.mandae.domain.repository.PedidoRepository;
import com.api.mandae.domain.service.EmissaoPedidoService;
import com.api.mandae.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoConverter pedidoConverter;

    @Autowired
    private PedidoResumoConverter pedidoResumoConverter;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PagedResourcesAssembler<Pedido> pedidoPagedResourcesAssembler;

    @GetMapping
    public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable) {

        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

//        List<PedidoResumoDTO> pedidosDTO = pedidoResumoConverter.toCollectionDTO(pedidosPage.getContent());
//        Page<PedidoResumoDTO> pedidosResumoDTOPage = new PageImpl<>(pedidosDTO, pageable,
//                pedidosPage.getTotalElements());

        return pedidoPagedResourcesAssembler.toModel(pedidosPage, pedidoResumoConverter);
    }

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//
//        List<Pedido> pedidos = pedidoRepository.findAll();
//
//        List<PedidoResumoDTO> pedidosModel = pedidoResumoConverter.toCollectionDTO(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)){
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }


    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        return pedidoConverter.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@RequestBody @Valid PedidoInput pedidoInput) {

        try {
            Pedido novoPedido = pedidoConverter.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);
            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoConverter.toModel(novoPedido);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    public Pageable traduzirPageable(Pageable apiPageable) {

        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "cliente.nome", "cliente.nome",
                "valorTotal", "valorTotal"
                                        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }


}
