package com.neoadventura.controllers;

import com.neoadventura.dtos.VwIdiomaDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/neo-adventura"+"/v1")
public class IdiomaController {

    @Autowired
    private IdiomaService idiomaService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/idiomas/{idiomaId}")
    public NeoAdventuraResponse<VwIdiomaDto> getIdiomaById(@PathVariable Long idiomaId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                idiomaService.getIdiomaById(idiomaId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/idiomas")
    public NeoAdventuraResponse<List<VwIdiomaDto>> getIdiomas()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                idiomaService.getIdiomas());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/idiomas/usuario/{usuarioId}")
    public NeoAdventuraResponse<List<VwIdiomaDto>> getIdiomaByUsuarioId(@PathVariable Long usuarioId)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                idiomaService.getIdiomasByUsuarioId(usuarioId));
    }

}
