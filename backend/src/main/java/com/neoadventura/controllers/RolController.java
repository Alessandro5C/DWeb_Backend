package com.neoadventura.controllers;

import com.neoadventura.dtos.VwRolDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/neo-adventura"+"/v1")
public class RolController {

    @Autowired
    private RolService rolService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/roles/{rolId}")
    public NeoAdventuraResponse<VwRolDto> getRolById(@PathVariable Long rolId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                rolService.getRolById(rolId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/roles")
    public NeoAdventuraResponse<List<VwRolDto>> getRoles()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                rolService.getRoles());
    }

}