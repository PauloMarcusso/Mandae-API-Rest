package com.api.mandae.api.assembler.restaurante;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.RestauranteController;
import com.api.mandae.api.model.RestauranteBasicoDTO;
import com.api.mandae.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    public RestauranteBasicoConverter() {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
    }

    @Override public RestauranteBasicoDTO toModel(Restaurante restaurante) {
        RestauranteBasicoDTO restauranteBasicoDTO = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteBasicoDTO);

        restauranteBasicoDTO.add(mandaeLinks.linkToRestaurantes("restaurantes"));

        restauranteBasicoDTO.getCozinha().add(mandaeLinks.linkToCozinha(restaurante.getCozinha().getId()));

        return restauranteBasicoDTO;
    }

    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(mandaeLinks.linkToRestaurantes());
    }
}
