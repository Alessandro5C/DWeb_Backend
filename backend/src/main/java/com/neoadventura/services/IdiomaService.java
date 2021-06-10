package com.neoadventura.services;

import com.neoadventura.dtos.VwIdiomaDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface IdiomaService {
    VwIdiomaDto getIdiomaById(Long id) throws NeoAdventuraException;
    List<VwIdiomaDto> getIdiomas() throws NeoAdventuraException;

    List<VwIdiomaDto> getIdiomasByUsuarioId(Long usuario_id) throws  NeoAdventuraException;
}
