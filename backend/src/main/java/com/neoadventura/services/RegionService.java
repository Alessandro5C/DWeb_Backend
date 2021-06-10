package com.neoadventura.services;

import com.neoadventura.dtos.VwRegionDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface RegionService {
    VwRegionDto getRegionById(Long id) throws NeoAdventuraException;
    List<VwRegionDto> getRegions() throws NeoAdventuraException;
}
