package com.neoadventura.services;

import com.neoadventura.dtos.VwMetodoDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface MetodoService {
    VwMetodoDto getMetodoById(Long id) throws NeoAdventuraException;
    List<VwMetodoDto> getMetodos() throws NeoAdventuraException;
}
