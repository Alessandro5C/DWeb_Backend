package com.neoadventura.services;

import com.neoadventura.dtos.CreateUsuarioDto;
import com.neoadventura.dtos.UsuarioDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface UsuarioService {
    UsuarioDto CreateUsuario(CreateUsuarioDto usuarioDto) throws NeoAdventuraException;
    UsuarioDto getUsuarioById(Long id) throws NeoAdventuraException;
    List<UsuarioDto> getUsuarios() throws NeoAdventuraException;
    UsuarioDto addIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException;
}
