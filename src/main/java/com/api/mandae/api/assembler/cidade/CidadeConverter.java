package com.api.mandae.api.assembler.cidade;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.CidadeController;
import com.api.mandae.api.controller.EstadoController;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeConverter extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public CidadeConverter() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {

        CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);

        if (mandaeSecurity.podeConsultarCidades()){
                    cidadeDTO.add(mandaeLinks.linkToCidades("cidades"));
        }

        if (mandaeSecurity.podeConsultarEstados()){
        cidadeDTO.getEstado().add(mandaeLinks.linkToEstado(cidadeDTO.getEstado().getId()));
        }

        return cidadeDTO;
    }

    @Override public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);

        if (mandaeSecurity.podeConsultarCidades()) {
            collectionModel.add(mandaeLinks.linkToCidades());
        }

        return collectionModel;
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
