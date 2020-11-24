package com.api.mandae.domain.service;

import com.api.mandae.domain.filter.VendaDiariaFilter;
import com.api.mandae.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
}
