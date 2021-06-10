package com.neoadventura.services;

import com.neoadventura.dtos.CrPagoDto;
import com.neoadventura.dtos.VwPagoDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface PagoService {

    VwPagoDto getPagoById(Long id) throws NeoAdventuraException;
    List<VwPagoDto> getPagos() throws NeoAdventuraException;
    CrPagoDto CreatePago(CrPagoDto crPagoDto) throws NeoAdventuraException;
}
