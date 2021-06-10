package com.neoadventura.services;

import com.neoadventura.dtos.VwRolDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface RolService {
    VwRolDto getRolById(Long id) throws NeoAdventuraException;
    List<VwRolDto> getRoles() throws NeoAdventuraException;
}
