package com.api.mandae.api.assembler.cidade;

import com.api.mandae.api.controller.CidadeController;
import com.api.mandae.api.controller.EstadoController;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeConverter extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeConverter() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {

        CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);

        //relacionando ao método
        cidadeDTO.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeDTO.getId())).withSelfRel());

        //relacionando ao endpoint
//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//                     .slash(cidadeDTO.getId()).withSelfRel());

        cidadeDTO.add(linkTo(methodOn(CidadeController.class).listar())
                .withRel("cidades"));

//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//                .withRel("cidades"));

        cidadeDTO.add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeDTO.getEstado().getId())).withSelfRel());

//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(EstadoController.class)
//                .slash(cidadeDTO.getEstado().getId()).withSelfRel());

        return cidadeDTO;
    }

    @Override public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {

        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }


//    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades) {
//        return cidades.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
//    }

}
