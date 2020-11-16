package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.pedido.PedidoConverter;
import com.api.mandae.api.assembler.pedido.PedidoResumoConverter;
import com.api.mandae.api.model.PedidoDTO;
import com.api.mandae.api.model.PedidoResumoDTO;
import com.api.mandae.api.model.input.PedidoInput;
import com.api.mandae.domain.exception.EntidadeNaoEncontradaException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.model.Usuario;
import com.api.mandae.domain.repository.PedidoRepository;
import com.api.mandae.domain.service.EmissaoPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoConverter pedidoConverter;

    @Autowired
    private PedidoResumoConverter pedidoResumoConverter;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {

        List<Pedido> pedidos = pedidoRepository.findAll();

        List<PedidoResumoDTO> pedidosModel = pedidoResumoConverter.toCollectionDTO(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if(StringUtils.isNotBlank(campos)){
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        pedidosWrapper.setFilters(filterProvider);

        return pedidosWrapper;
    }

//    @GetMapping
//    public List<PedidoResumoDTO> listar() {
//        return pedidoResumoConverter.toCollectionDTO(pedidoRepository.findAll());
//    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        return pedidoConverter.toDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@RequestBody @Valid PedidoInput pedidoInput) {

        try {
            Pedido novoPedido = pedidoConverter.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);
            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoConverter.toDTO(novoPedido);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }
}
