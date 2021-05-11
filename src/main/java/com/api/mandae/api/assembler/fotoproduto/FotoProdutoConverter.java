package com.api.mandae.api.assembler.fotoproduto;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.RestauranteFotoProdutoController;
import com.api.mandae.api.model.FotoProdutoDTO;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoConverter extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public FotoProdutoConverter() {
        super(RestauranteFotoProdutoController.class, FotoProdutoDTO.class);
    }

    public FotoProdutoDTO toDTO(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoDTO.class);
    }

    @Override
    public FotoProdutoDTO toModel(FotoProduto foto) {
        FotoProdutoDTO fotoProdutoModel = modelMapper.map(foto, FotoProdutoDTO.class);

        if (mandaeSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(mandaeLinks.linkToFotoProduto(
                    foto.getRestauranteId(), foto.getProduto().getId()));

            fotoProdutoModel.add(mandaeLinks.linkToProduto(
                    foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModel;
    }
}
