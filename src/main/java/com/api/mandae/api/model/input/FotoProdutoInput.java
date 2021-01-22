package com.api.mandae.api.model.input;


import com.api.mandae.core.validation.FileContentType;
import com.api.mandae.core.validation.FileSize;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FotoProdutoInput {

    @ApiModelProperty(hidden = true)
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile arquivo;

    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    @NotBlank
    private String descricao;
}
