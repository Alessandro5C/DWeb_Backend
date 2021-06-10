package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwRegionDto;
import com.neoadventura.entities.Region;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.RegionRepository;
import com.neoadventura.services.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionRepository regionRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwRegionDto getRegionById(Long id) throws NeoAdventuraException {
        return modelMapper.map(getRegionEntity(id), VwRegionDto.class);
    }

    @Override
    public List<VwRegionDto> getRegions() throws NeoAdventuraException {
        List<Region> regionsEntity = regionRepository.findAll();
        return regionsEntity.stream().map(region -> modelMapper.map(region, VwRegionDto.class))
                .collect(Collectors.toList());
    }

    public Region getRegionEntity(Long id) throws NeoAdventuraException{
        return regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "REGION-NOT-FOUND-404"));
    }
}
