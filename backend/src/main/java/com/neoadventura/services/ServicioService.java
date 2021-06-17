package com.neoadventura.services;

import com.neoadventura.dtos.CrServicioDto;
import com.neoadventura.dtos.VwServicioDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface ServicioService {
    VwServicioDto CreateServicio(CrServicioDto crServicioDto) throws NeoAdventuraException;
    VwServicioDto getServicioById(Long id) throws NeoAdventuraException;
    List<VwServicioDto> getServicios() throws NeoAdventuraException;
    List<VwServicioDto> getServicios(Long usuario_id) throws NeoAdventuraException;
    List<VwServicioDto> getServiciosByAnfitrion(Long anfitrion_id) throws NeoAdventuraException;
    List<VwServicioDto> getServiciosByTag(Long usuario_id, String tag, Long tag_id) throws NeoAdventuraException;
}
