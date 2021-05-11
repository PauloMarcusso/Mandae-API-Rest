package com.api.mandae.api.assembler.restaurante;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.RestauranteController;
import com.api.mandae.api.model.RestauranteDTO;
import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.core.security.MandaeSecurity;
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
    private MandaeSecurity mandaeSecurity;

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

        if (mandaeSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(mandaeLinks.linkToRestaurantes("restaurantes"));
        }

        if (mandaeSecurity.podeGerenciarCadastroRestaurantes()) {

            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        mandaeLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(
                        mandaeLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (mandaeSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {

            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(
                        mandaeLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPertimido()) {
                restauranteModel.add(
                        mandaeLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }

        if (mandaeSecurity.podeConsultarRestaurantes()) {

            restauranteModel.add(mandaeLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }

        if (mandaeSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    mandaeLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        if (mandaeSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null
                    && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        mandaeLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }

        if (mandaeSecurity.podeConsultarRestaurantes()){
        restauranteModel.add(mandaeLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                "formas-pagamento"));
        }

        if (mandaeSecurity.podeGerenciarCadastroRestaurantes()){
            restauranteModel.add(mandaeLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                    "responsaveis"));
        }

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
        CollectionModel<RestauranteDTO> collectionModel = super.toCollectionModel(entities);

        if (mandaeSecurity.podeConsultarRestaurantes()){
            toCollectionModel(entities)
                    .add(mandaeLinks.linkToRestaurantes());
        }

        return collectionModel;
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
