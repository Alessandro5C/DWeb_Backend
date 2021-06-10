package com.neoadventura.services;

import com.neoadventura.dtos.VwPaisDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface PaisService {
    VwPaisDto getPaisById(Long id) throws NeoAdventuraException;
    List<VwPaisDto> getPaises() throws NeoAdventuraException;
}
