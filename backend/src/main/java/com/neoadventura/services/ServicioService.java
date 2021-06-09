package com.neoadventura.services;

import com.neoadventura.dtos.CreateServicioDto;
import com.neoadventura.dtos.ServicioDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface ServicioService {
    ServicioDto CreateServicio(CreateServicioDto createServicioDto) throws NeoAdventuraException;
    ServicioDto getServicioById(Long id) throws NeoAdventuraException;
    List<ServicioDto> getServicios() throws NeoAdventuraException;
    List<ServicioDto> getServicios(Long usuario_id) throws NeoAdventuraException;
}
