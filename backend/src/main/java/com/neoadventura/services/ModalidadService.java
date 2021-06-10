package com.neoadventura.services;

import com.neoadventura.dtos.VwModalidadDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface ModalidadService {
    List<VwModalidadDto> getModalidades() throws NeoAdventuraException;
    VwModalidadDto getModalidadById(Long id) throws NeoAdventuraException;
}
