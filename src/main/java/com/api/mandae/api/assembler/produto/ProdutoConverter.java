package com.api.mandae.api.assembler.produto;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.RestauranteProdutoController;
import com.api.mandae.api.model.ProdutoDTO;
import com.api.mandae.api.model.input.ProdutoInput;
import com.api.mandae.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    public ProdutoConverter() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    @Override
    public ProdutoDTO toModel(Produto produto) {
        ProdutoDTO produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        produtoModel.add(mandaeLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        return produtoModel;
    }

    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
