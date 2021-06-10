package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwModalidadDto;
import com.neoadventura.entities.Modalidad;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.ModalidadRepository;
import com.neoadventura.services.ModalidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModalidadServiceImpl implements ModalidadService {

    @Autowired
    private ModalidadRepository modalidadRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwModalidadDto getModalidadById(Long modalidadId) throws NeoAdventuraException {
        return modelMapper.map(getModalidadEntity(modalidadId), VwModalidadDto.class);
    }

    @Override
    public List<VwModalidadDto> getModalidades() throws NeoAdventuraException {
        List<Modalidad> modalidadesEntity = modalidadRepository.findAll();
        return modalidadesEntity.stream().map(restaurant -> modelMapper.map(restaurant, VwModalidadDto.class))
                .collect(Collectors.toList());
    }

    private Modalidad getModalidadEntity(Long modalidadId) throws NeoAdventuraException {
        return modalidadRepository.findById(modalidadId)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "MODALIDAD_NOTFOUND-404"));
    }
}