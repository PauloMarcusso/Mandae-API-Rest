package com.api.mandae.api.assembler.restaurante;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.RestauranteController;
import com.api.mandae.api.model.RestauranteDTO;
import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    public RestauranteConverter() {
        super(RestauranteController.class, RestauranteDTO.class);
    }

    @Override
    public RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(mandaeLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
                mandaeLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteModel.getEndereco().getCidade().add(
                mandaeLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteModel.add(mandaeLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                "formas-pagamento"));

        restauranteModel.add(mandaeLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                "responsaveis"));

        return restauranteModel;
    }

    /**
     * @Override public RestauranteDTO toModel(Restaurante restaurante) {
     * <p>
     * return modelMapper.map(restaurante, RestauranteDTO.class);
     * <p>
     * CozinhaDTO cozinhaDTO = new CozinhaDTO();
     * cozinhaDTO.setId(restaurante.getCozinha().getId());
     * cozinhaDTO.setNome(restaurante.getCozinha().getNome());
     * <p>
     * RestauranteDTO restauranteDTO = new RestauranteDTO();
     * restauranteDTO.setId(restaurante.getId());
     * restauranteDTO.setNome(restaurante.getNome());
     * restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
     * restauranteDTO.setCozinha(cozinhaDTO);
     * return restauranteDTO;
     * }
     */

    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(mandaeLinks.linkToRestaurantes());
    }

    /**
     * @Override public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
     * return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
     * }
     */


    public Restaurante toDomainObject(RestauranteInput restauranteInput) {

        return modelMapper.map(restauranteInput, Restaurante.class);

//		Restaurante restaurante = new Restaurante();
//		restaurante.setNome(restauranteInput.getNome());
//		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(restauranteInput.getCozinha().getId());
//		restaurante.setCozinha(cozinha);
//
//		return restaurante;
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {

        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }
}
