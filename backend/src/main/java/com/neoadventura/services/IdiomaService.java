package com.neoadventura.services;

import com.neoadventura.dtos.IdiomaDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface IdiomaService {
    IdiomaDto getIdiomaById(Long id) throws NeoAdventuraException;
    List<IdiomaDto> getIdiomas() throws NeoAdventuraException;

    List<IdiomaDto> getIdiomasByUsuarioId(Long usuario_id) throws  NeoAdventuraException;
}
