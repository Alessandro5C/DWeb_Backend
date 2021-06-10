package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwMetodoDto;
import com.neoadventura.entities.Metodo;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.MetodoRepository;
import com.neoadventura.services.MetodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetodoServiceImpl implements MetodoService {

    @Autowired
    private MetodoRepository metodoRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwMetodoDto getMetodoById(Long id) throws NeoAdventuraException {
        return modelMapper.map(getMetodoEntityById(id), VwMetodoDto.class);
    }

    @Override
    public List<VwMetodoDto> getMetodos() throws NeoAdventuraException {
        List<Metodo> metodosEntity = metodoRepository.findAll();

        return metodosEntity.stream().map(rol -> modelMapper.map(rol, VwMetodoDto.class))
                .collect(Collectors.toList());
    }

    private Metodo getMetodoEntityById(Long id) throws NeoAdventuraException{
        return metodoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "METODO_NOTFOUND-404"));
    }
}
