package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwRolDto;
import com.neoadventura.entities.Rol;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.RolRepository;
import com.neoadventura.services.RolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwRolDto getRolById(Long id) throws NeoAdventuraException {
        return modelMapper.map(getRolEntityById(id), VwRolDto.class);
    }

    @Override
    public List<VwRolDto> getRoles() throws NeoAdventuraException {
        List<Rol> rolesEntity = rolRepository.findAll();

        return rolesEntity.stream().map(rol -> modelMapper.map(rol, VwRolDto.class))
                .collect(Collectors.toList());
    }

    private Rol getRolEntityById(Long id) throws NeoAdventuraException{
        return rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "ROL_NOTFOUND-404"));
    }
}
