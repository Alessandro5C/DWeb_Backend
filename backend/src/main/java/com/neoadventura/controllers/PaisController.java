package com.neoadventura.controllers;


import com.neoadventura.dtos.VwPaisDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/neo-adventura"+"/v1")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/paises/{paisId}")
    public NeoAdventuraResponse<VwPaisDto> getPaisById(@PathVariable Long paisId)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                paisService.getPaisById(paisId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/paises")
    public NeoAdventuraResponse<List<VwPaisDto>> getPaises()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                paisService.getPaises());
    }

}
