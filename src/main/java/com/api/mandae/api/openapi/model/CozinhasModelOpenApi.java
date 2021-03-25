package com.api.mandae.api.openapi.model;

import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi{


    private CozinhasModelOpenApi.CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi{

        private List<CozinhaDTO> cozinhas;
    }
}
