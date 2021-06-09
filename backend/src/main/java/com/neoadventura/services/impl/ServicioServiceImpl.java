package com.neoadventura.services.impl;

import com.neoadventura.dtos.CreateServicioDto;
import com.neoadventura.dtos.ServicioDto;
import com.neoadventura.dtos.UsuarioDto;
import com.neoadventura.entities.*;
import com.neoadventura.exceptions.InternalServerErrorException;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.*;
import com.neoadventura.services.ServicioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Region region = regionRepository.findById(createServicioDto.getRegion_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "REGION_NOT_FOUND"));

        Plataforma plataforma = plataformaRepository.findById(createServicioDto.getPlataforma_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "PLATAFORMA_NOT_FOUND"));

        Usuario usuario = usuarioRepository.findById(createServicioDto.getUsuario_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "USUARIO_NOT_FOUND"));

        Modalidad modalidad = modalidadRepository.findById(createServicioDto.getModalidad_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "MODALIDA_NOT_FOUND"));

        Servicio servicio = new Servicio();


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
//        List<Servicio> serviciosEntity = servicioRepository.findAll();
//        List<ServicioDto> servicioDtos = serviciosEntity.stream().map(servicio -> modelMapper.map(servicio, ServicioDto.class))
//                .collect(Collectors.toList());
//        for (int i = 0; i < servicioDtos.size(); i++) {
//            servicioDtos.get(i).setModalidad_id(serviciosEntity.get(i).getModalidad().getId());
//            servicioDtos.get(i).setPlataforma_id(serviciosEntity.get(i).getPlataforma().getId());
//            servicioDtos.get(i).setRegion_id(serviciosEntity.get(i).getRegion().getId());
//            servicioDtos.get(i).setUsuario_id(serviciosEntity.get(i).getUsuario().getId());
//        }
//        return servicioDtos;
    }

    @Override
    public List<ServicioDto> getServicios(Long usuario_id) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));

        List<Servicio> serviciosEntity = servicioRepository.findAll();
        List<ServicioDto> servicioDtos = new ArrayList<>();
        // = serviciosEntity.stream().map(servicio -> modelMapper.map(servicio, ServicioDto.class))
//                .collect(Collectors.toList());
        Servicio servicio;
        ServicioDto servicioDto; // = modelMapper.map(getServicioEntity(id), ServicioDto.class);
        for (int i = 0; i < serviciosEntity.size(); i++) {
            servicio = serviciosEntity.get(i);
            if (FilterByLanguage(servicio.getUsuario(), usuario)) {
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

    private Boolean FilterByLanguage(Usuario anfitrion, Usuario usuario) {
        //Verify if it's only visitor or has SameLanguage value as false
        if (usuario.getId()==0 || !usuario.getSame_language())
            return true;
        //Verify 'anfitrion' and 'usuario' have added languages
        if (usuario.getIdiomas().size()==0 || anfitrion.getIdiomas().size()==0)
            return false;
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
