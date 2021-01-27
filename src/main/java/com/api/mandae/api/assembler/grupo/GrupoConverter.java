package com.api.mandae.api.assembler.grupo;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.GrupoDTO;
import com.api.mandae.api.model.input.GrupoInput;
import com.api.mandae.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoConverter implements Converter<Grupo, GrupoDTO, GrupoInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GrupoDTO toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    @Override
    public List<GrupoDTO> toCollectionDTO(List<Grupo> grupos) {
        return grupos.stream().map( grupo -> toModel(grupo)).collect(Collectors.toList());
    }

    @Override
    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    @Override
    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
