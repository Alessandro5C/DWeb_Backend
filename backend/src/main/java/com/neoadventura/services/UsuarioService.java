package com.neoadventura.services;

import com.neoadventura.dtos.AnfitrionDto;
import com.neoadventura.dtos.CreateUsuarioDto;
import com.neoadventura.dtos.IdiomaDto;
import com.neoadventura.dtos.UsuarioDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface UsuarioService {
    UsuarioDto CreateUsuario(CreateUsuarioDto usuarioDto) throws NeoAdventuraException;
    UsuarioDto getUsuarioById(Long id) throws NeoAdventuraException;
    List<UsuarioDto> getUsuarios() throws NeoAdventuraException;

    AnfitrionDto getAnfitrionById(Long id) throws NeoAdventuraException;
    List<AnfitrionDto> getAnfitriones() throws NeoAdventuraException;

    UsuarioDto addIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException;
    UsuarioDto switchSameLanguage(Long id) throws NeoAdventuraException;
}
