package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwPaisDto;
import com.neoadventura.entities.Pais;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.PaisRepository;
import com.neoadventura.services.PaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisServiceImpl implements PaisService {

    @Autowired
    private PaisRepository paisRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwPaisDto getPaisById(Long id) throws NeoAdventuraException {
        return modelMapper.map(getPaisEntityById(id), VwPaisDto.class);
    }

    @Override
    public List<VwPaisDto> getPaises() throws NeoAdventuraException {
        List<Pais> paisesEntity = paisRepository.findAll();

        return paisesEntity.stream().map(pais -> modelMapper.map(pais, VwPaisDto.class))
                .collect(Collectors.toList());
    }

    private Pais getPaisEntityById(Long id) throws NeoAdventuraException{
        return paisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "PAIS_NOTFOUND-404"));
    }
}
