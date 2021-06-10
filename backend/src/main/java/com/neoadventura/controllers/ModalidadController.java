package com.neoadventura.controllers;


import com.neoadventura.dtos.VwModalidadDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.ModalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/neo-adventura"+"/v1")
public class ModalidadController {

    @Autowired
    private ModalidadService modalidadService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/modalidades/{modalidadId}")
    public NeoAdventuraResponse<VwModalidadDto> getModalidadById(@PathVariable Long modalidadId)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                modalidadService.getModalidadById(modalidadId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/modalidades")
    public NeoAdventuraResponse<List<VwModalidadDto>> getModalidades()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                modalidadService.getModalidades());
    }

}
