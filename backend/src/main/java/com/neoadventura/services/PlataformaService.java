package com.neoadventura.services;

import com.neoadventura.dtos.VwPlataformaDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface PlataformaService {
    VwPlataformaDto getPlataformaById(Long id) throws NeoAdventuraException;
    List<VwPlataformaDto> getPlataformas() throws NeoAdventuraException;
}
