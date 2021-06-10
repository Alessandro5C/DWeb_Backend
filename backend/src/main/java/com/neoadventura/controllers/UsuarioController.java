package com.neoadventura.controllers;


import com.neoadventura.dtos.VwAnfitrionDto;
import com.neoadventura.dtos.CrUsuarioDto;
import com.neoadventura.dtos.UpUsuarioDto;
import com.neoadventura.dtos.VwUsuarioDto;
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
    public NeoAdventuraResponse<VwUsuarioDto> createUsuario(@RequestBody CrUsuarioDto crUsuarioDto)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK),
                "OK", usuarioService.CreateUsuario(crUsuarioDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuarios/{usuarioId}")
    public NeoAdventuraResponse<VwUsuarioDto> getUsuarioById(@PathVariable Long usuarioId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarioById(usuarioId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuarios")
    public NeoAdventuraResponse<List<VwUsuarioDto>> getUsuarios()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarios());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/anfitriones/{anfitrionId}")
    public NeoAdventuraResponse<VwAnfitrionDto> getAnfitrionById(@PathVariable Long anfitrionId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getAnfitrionById(anfitrionId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/anfitriones")
    public NeoAdventuraResponse<List<VwAnfitrionDto>> getAnfitriones()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getAnfitriones());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/usuarios")
    public NeoAdventuraResponse<VwUsuarioDto> updateUsuarioInfo(@RequestBody UpUsuarioDto upUsuarioDto)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.updateUsuario(upUsuarioDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/usuarios/{usuario_id}/idioma/{idioma_id}")
    public NeoAdventuraResponse<VwUsuarioDto> insertIdiomaUsuario(@PathVariable Long usuario_id, @PathVariable Long idioma_id)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.addIdioma(usuario_id, idioma_id));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/usuarios/{usuario_id}/idioma/{idioma_id}")
    public NeoAdventuraResponse<VwUsuarioDto> deleteIdiomaUsuario(@PathVariable Long usuario_id, @PathVariable Long idioma_id)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.delIdioma(usuario_id, idioma_id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/usuarios/{usuario_id}/language")
    public NeoAdventuraResponse<VwUsuarioDto> switchSameLanguage(@PathVariable Long usuario_id)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.switchSameLanguage(usuario_id));
    }

}
