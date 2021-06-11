package com.neoadventura.controllers;

import com.neoadventura.dtos.CrServicioDto;
import com.neoadventura.dtos.VwServicioDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/neo-adventura"+"/v1")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/servicios")
    public NeoAdventuraResponse<VwServicioDto> createServicio(@RequestBody CrServicioDto crServicioDto)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK),
                "OK", servicioService.CreateServicio(crServicioDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/servicios/{servicioId}")
    public NeoAdventuraResponse<VwServicioDto> getServicioById(@PathVariable Long servicioId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                servicioService.getServicioById(servicioId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/servicios")
    public NeoAdventuraResponse<List<VwServicioDto>> getServiciosAsVisitor()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                servicioService.getServicios());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/servicios/client/{usuarioId}")
    public NeoAdventuraResponse<List<VwServicioDto>> getServicios(@PathVariable Long usuarioId)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                servicioService.getServicios(usuarioId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/servicios/anfitrion/{anfitrionId}")
    public NeoAdventuraResponse<List<VwServicioDto>> getServiciosByAnfitrion(@PathVariable Long anfitrionId)
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                servicioService.getServiciosByAnfitrion(anfitrionId));
    }
}
