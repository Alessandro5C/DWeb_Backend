package com.neoadventura.services;

import com.neoadventura.dtos.*;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface UsuarioService {
    VwUsuarioDto CreateUsuario(CrUsuarioDto usuarioDto) throws NeoAdventuraException;
    VwUsuarioDto getUsuarioById(Long id) throws NeoAdventuraException;
    List<VwUsuarioDto> getUsuarios() throws NeoAdventuraException;

    VwAnfitrionDto getAnfitrionById(Long id) throws NeoAdventuraException;
    List<VwAnfitrionDto> getAnfitriones() throws NeoAdventuraException;

    VwUsuarioDto addIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException;
    VwUsuarioDto delIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException;
    VwUsuarioDto switchSameLanguage(Long id) throws NeoAdventuraException;

    VwUsuarioDto updateUsuario(UpUsuarioDto upUsuarioDto) throws NeoAdventuraException;
}
