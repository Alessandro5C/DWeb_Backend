package com.neoadventura.controllers;

import com.neoadventura.dtos.VwPlataformaDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.PlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/neo-adventura"+"/v1")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/plataformas/{plataformaId}")
    public NeoAdventuraResponse<VwPlataformaDto> getPlataformaById(@PathVariable Long plataformaId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                plataformaService.getPlataformaById(plataformaId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/plataformas")
    public NeoAdventuraResponse<List<VwPlataformaDto>> getPlataforma()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                plataformaService.getPlataformas());
    }
}
