package com.neoadventura.services;

import com.neoadventura.dtos.CrPagoDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface PagoService {

    CrPagoDto getPagoById(Long id) throws NeoAdventuraException;
    List<CrPagoDto> getPagos() throws NeoAdventuraException;
    CrPagoDto CreatePago(CrPagoDto crPagoDto) throws NeoAdventuraException;
}
