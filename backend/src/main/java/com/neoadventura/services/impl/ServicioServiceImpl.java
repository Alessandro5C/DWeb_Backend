package com.neoadventura.services.impl;

import com.neoadventura.dtos.CrServicioDto;
import com.neoadventura.dtos.VwServicioDto;
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
    public VwServicioDto CreateServicio(CrServicioDto crServicioDto) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(crServicioDto.getUsuario_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "USUARIO_NOT_FOUND"));

        if (usuario.getRol().getId() == 1)
            throw new UnauthorizedException("401", "THIS ACCOUNT IS NOT ANFITRION");

        Region region = regionRepository.findById(crServicioDto.getRegion_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "REGION_NOT_FOUND"));

        Plataforma plataforma = plataformaRepository.findById(crServicioDto.getPlataforma_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "PLATAFORMA_NOT_FOUND"));

        Modalidad modalidad = modalidadRepository.findById(crServicioDto.getModalidad_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "MODALIDAD_NOT_FOUND"));

        Servicio servicio = new Servicio();

        Boolean is_valid = false; //Validation section
        if (crServicioDto.getName().length()>0 && crServicioDto.getDescription().length() > 0 &&
            crServicioDto.getInit_valid_date().compareTo(usuarioRepository.getNow()) > 0 &&
            crServicioDto.getEnd_valid_date().compareTo(crServicioDto.getInit_valid_date()) > 0 &&
            crServicioDto.getPrice().compareTo(BigDecimal.valueOf(1)) > 0)
            is_valid = true;

        if (!is_valid) throw new FormatException("304", "NOT MODIFIED");

        servicio.setName(crServicioDto.getName());
        servicio.setDescription(crServicioDto.getDescription());
        servicio.setInit_valid_date(crServicioDto.getInit_valid_date());
        servicio.setEnd_valid_date(crServicioDto.getEnd_valid_date());
        servicio.setPrice(crServicioDto.getPrice());
        servicio.setModalidad(modalidad);
        servicio.setRegion(region);
        servicio.setPlataforma(plataforma);
        servicio.setUsuario(usuario);

        try {
            servicioRepository.save(servicio);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_ERROR", "INTERNAL_ERROR");
        }
        return modelMapper.map(getServicioEntity(servicio.getId()), VwServicioDto.class);
    }

    @Override
    public VwServicioDto getServicioById(Long id) throws NeoAdventuraException {
        Servicio servicio =getServicioEntity(id);
        VwServicioDto vwServicioDto = modelMapper.map(getServicioEntity(id), VwServicioDto.class);
        vwServicioDto.setModalidad_name(servicio.getModalidad().getName());
        vwServicioDto.setPlataforma_name(servicio.getPlataforma().getName());
        vwServicioDto.setRegion_name(servicio.getRegion().getName());
        vwServicioDto.setUsuario_name(servicio.getUsuario().getName());
        return vwServicioDto;
    }

    @Override
    public List<VwServicioDto> getServicios() throws NeoAdventuraException {
        return getServicios(0L);
    }

    @Override
    public List<VwServicioDto> getServicios(Long usuario_id) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));

        List<Servicio> serviciosEntity = servicioRepository.findAll();
        List<VwServicioDto> vwServicioDtos = new ArrayList<>();

        Servicio servicio;
        VwServicioDto vwServicioDto;
        for (int i = 0; i < serviciosEntity.size(); i++) {
            servicio = serviciosEntity.get(i);
            if (commonLanguage(servicio.getUsuario(), usuario)) {
                vwServicioDto = modelMapper.map(servicio, VwServicioDto.class);
                vwServicioDto.setModalidad_name(servicio.getModalidad().getName());
                vwServicioDto.setPlataforma_name(servicio.getPlataforma().getName());
                vwServicioDto.setRegion_name(servicio.getRegion().getName());
                vwServicioDto.setUsuario_name(servicio.getUsuario().getName());
                vwServicioDtos.add(vwServicioDto);
            }
        }
        return vwServicioDtos;
    }

    @Override
    public List<VwServicioDto> getServiciosByTag(Long usuario_id, String tag, Long tag_id) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));

        List<Servicio> serviciosEntity;
        switch (tag) {
            case "Modalidad":
                Modalidad modalidad = modalidadRepository.findById(tag_id)
                        .orElseThrow(() -> new NotFoundException("NOT-401-1", "MODALIDAD_NOT_FOUND"));
                serviciosEntity=servicioRepository.findAllByModalidad(modalidad);
                break;
            case "Plataforma":
                Plataforma plataforma = plataformaRepository.findById(tag_id)
                        .orElseThrow(() -> new NotFoundException("NOT-401-1", "PLATAFORMA_NOT_FOUND"));
                serviciosEntity=servicioRepository.findAllByPlataforma(plataforma);
                break;
            case "Region":
                Region region = regionRepository.findById(tag_id)
                        .orElseThrow(() -> new NotFoundException("NOT-401-1", "REGION_NOT_FOUND"));
                serviciosEntity=servicioRepository.findAllByRegion(region);
                break;
            default:
                serviciosEntity = new ArrayList<>();
        }

        Servicio servicio;
        VwServicioDto vwServicioDto;
        List<VwServicioDto> vwServicioDtos = new ArrayList<>();
        for (int i = 0; i < serviciosEntity.size(); i++) {
            servicio = serviciosEntity.get(i);
            if (commonLanguage(servicio.getUsuario(), usuario)) {
                vwServicioDto = modelMapper.map(servicio, VwServicioDto.class);
                vwServicioDto.setModalidad_name(servicio.getModalidad().getName());
                vwServicioDto.setPlataforma_name(servicio.getPlataforma().getName());
                vwServicioDto.setRegion_name(servicio.getRegion().getName());
                vwServicioDto.setUsuario_name(servicio.getUsuario().getName());
                vwServicioDtos.add(vwServicioDto);
            }
        }

        return vwServicioDtos;
    }

    @Override
    public List<VwServicioDto> getServiciosByAnfitrion(Long anfitrion_id) throws NeoAdventuraException {
        Usuario usuario = usuarioRepository.findById(anfitrion_id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "ANFITRION_NOTFOUND-404"));

        List<Servicio> serviciosEntity = servicioRepository.findAllByUsuario(usuario);
        List<VwServicioDto> vwServicioDtos = new ArrayList<>();

        Servicio servicio;
        VwServicioDto vwServicioDto;
        for (int i = 0; i < serviciosEntity.size(); i++) {
            servicio = serviciosEntity.get(i);
            vwServicioDto = modelMapper.map(servicio, VwServicioDto.class);
            vwServicioDto.setModalidad_name(servicio.getModalidad().getName());
            vwServicioDto.setPlataforma_name(servicio.getPlataforma().getName());
            vwServicioDto.setRegion_name(servicio.getRegion().getName());
            vwServicioDto.setUsuario_name(servicio.getUsuario().getName());
            vwServicioDtos.add(vwServicioDto);
        }
        return vwServicioDtos;
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
