package com.neoadventura.controllers;

import com.neoadventura.dtos.VwMetodoDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.MetodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/neo-adventura"+"/v1")
public class MetodoController {

    @Autowired
    private MetodoService metodoService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/metodos/{metodoId}")
    public NeoAdventuraResponse<VwMetodoDto> getMetodoById(@PathVariable Long metodoId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                metodoService.getMetodoById(metodoId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/metodos")
    public NeoAdventuraResponse<List<VwMetodoDto>> getMetodos()
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                metodoService.getMetodos());
    }
}
