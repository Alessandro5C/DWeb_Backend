package com.neoadventura.controllers;


import com.neoadventura.dtos.CreateUsuarioDto;
import com.neoadventura.dtos.UsuarioDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/neo-adventura"+"/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/usuarios")
    public NeoAdventuraResponse<UsuarioDto> createUsuario(@RequestBody CreateUsuarioDto createUsuarioDto)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK),
                "OK", usuarioService.CreateUsuario(createUsuarioDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuarios/{usuarioId}")
    public NeoAdventuraResponse<UsuarioDto> getUsuarioById(@PathVariable Long usuarioId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarioById(usuarioId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuarios")
    public NeoAdventuraResponse<List<UsuarioDto>> getUsuario()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarios());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/usuarios/{usuario_id}/idioma/{idioma_id}")
    public NeoAdventuraResponse<UsuarioDto> insertIdiomaUsuario(@PathVariable Long usuario_id, @PathVariable Long idioma_id)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.addIdioma(usuario_id, idioma_id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/usuarios/{usuario_id}/language")
    public NeoAdventuraResponse<UsuarioDto> switchSameLanguage(@PathVariable Long usuario_id)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.switchSameLanguage(usuario_id));
    }

}
