package com.api.mandae.api.controller;

import com.api.mandae.domain.filter.VendaDiariaFilter;
import com.api.mandae.domain.model.dto.VendaDiaria;
import com.api.mandae.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00")
            String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }
}
