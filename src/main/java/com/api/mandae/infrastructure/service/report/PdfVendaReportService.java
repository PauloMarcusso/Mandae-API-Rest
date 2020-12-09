package com.api.mandae.infrastructure.service.report;

import com.api.mandae.domain.filter.VendaDiariaFilter;
import com.api.mandae.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class PdfVendaReportService implements VendaReportService {


    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        return new byte[0];
    }
}
