package com.neoadventura.services.impl;

import com.neoadventura.dtos.CreateServicioDto;
import com.neoadventura.dtos.ServicioDto;
import com.neoadventura.entities.*;
import com.neoadventura.exceptions.*;
import com.neoadventura.repositories.*;
import com.neoadventura.services.ServicioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModalidadRepository modalidadRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public ServicioDto CreateServicio(CreateServicioDto createServicioDto) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(createServicioDto.getUsuario_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "USUARIO_NOT_FOUND"));

        if (usuario.getRol().getId() == 1)
            throw new UnauthorizedException("401", "THIS ACCOUNT IS NOT ANFITRION");

        Region region = regionRepository.findById(createServicioDto.getRegion_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "REGION_NOT_FOUND"));

        Plataforma plataforma = plataformaRepository.findById(createServicioDto.getPlataforma_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "PLATAFORMA_NOT_FOUND"));

        Modalidad modalidad = modalidadRepository.findById(createServicioDto.getModalidad_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "MODALIDAD_NOT_FOUND"));

        Servicio servicio = new Servicio();

        Boolean is_valid = false; //Validation section
        if (createServicioDto.getName().length()>0 && createServicioDto.getDescription().length() > 0 &&
            createServicioDto.getInit_valid_date().compareTo(usuarioRepository.getNow()) > 0 &&
            createServicioDto.getEnd_valid_date().compareTo(createServicioDto.getInit_valid_date()) > 0 &&
            createServicioDto.getPrice().compareTo(BigDecimal.valueOf(1)) > 0)
            is_valid = true;

        if (!is_valid) throw new FormatException("304", "NOT MODIFIED");

        servicio.setName(createServicioDto.getName());
        servicio.setDescription(createServicioDto.getDescription());
        servicio.setInit_valid_date(createServicioDto.getInit_valid_date());
        servicio.setEnd_valid_date(createServicioDto.getEnd_valid_date());
        servicio.setPrice(createServicioDto.getPrice());
        servicio.setModalidad(modalidad);
        servicio.setRegion(region);
        servicio.setPlataforma(plataforma);
        servicio.setUsuario(usuario);

        try {
            servicioRepository.save(servicio);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_ERROR", "INTERNAL_ERROR");
        }
        return modelMapper.map(getServicioEntity(servicio.getId()), ServicioDto.class);
    }

    @Override
    public ServicioDto getServicioById(Long id) throws NeoAdventuraException {
        Servicio servicio =getServicioEntity(id);
        ServicioDto servicioDto = modelMapper.map(getServicioEntity(id), ServicioDto.class);
        servicioDto.setModalidad_id(servicio.getModalidad().getId());
        servicioDto.setPlataforma_id(servicio.getPlataforma().getId());
        servicioDto.setRegion_id(servicio.getRegion().getId());
        servicioDto.setUsuario_id(servicio.getUsuario().getId());
        return servicioDto;
    }

    @Override
    public List<ServicioDto> getServicios() throws NeoAdventuraException {
        return getServicios(0L);
    }

    @Override
    public List<ServicioDto> getServicios(Long usuario_id) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));

        List<Servicio> serviciosEntity = servicioRepository.findAll();
        List<ServicioDto> servicioDtos = new ArrayList<>();

        Servicio servicio;
        ServicioDto servicioDto;
        for (int i = 0; i < serviciosEntity.size(); i++) {
            servicio = serviciosEntity.get(i);
            if (commonLanguage(servicio.getUsuario(), usuario)) {
                servicioDto = modelMapper.map(servicio, ServicioDto.class);
                servicioDto.setModalidad_id(servicio.getModalidad().getId());
                servicioDto.setPlataforma_id(servicio.getPlataforma().getId());
                servicioDto.setRegion_id(servicio.getRegion().getId());
                servicioDto.setUsuario_id(servicio.getUsuario().getId());
                servicioDtos.add(servicioDto);
            }
        }
        return servicioDtos;
    }

    private Boolean commonLanguage(Usuario anfitrion, Usuario usuario) {
        //Verify if it's only visitor or has SameLanguage value as false
        if (usuario.getId()==0 || !usuario.getSame_language() || usuario.getIdiomas().size()==0)
            return true;

        //Check if they had a language in common
        for (Idioma i: anfitrion.getIdiomas())
            for (Idioma j: usuario.getIdiomas())
                if (i==j)
                    return true;
        return false;
    }


    private Servicio getServicioEntity(Long id) throws NeoAdventuraException {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "SERVICIO_NOTFOUND-404"));
    }
}
