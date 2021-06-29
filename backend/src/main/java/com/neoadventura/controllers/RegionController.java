package com.neoadventura.controllers;

import com.neoadventura.dtos.VwRegionDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/neo-adventura"+"/v1")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/regions/{regionId}")
    public NeoAdventuraResponse<VwRegionDto> getRegionById(@PathVariable Long regionId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                regionService.getRegionById(regionId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/regions")
    public NeoAdventuraResponse<List<VwRegionDto>> getRegions()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                regionService.getRegions());
    }
}
