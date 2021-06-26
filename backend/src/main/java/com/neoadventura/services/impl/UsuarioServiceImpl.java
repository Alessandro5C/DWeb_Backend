package com.neoadventura.services.impl;

import com.neoadventura.dtos.*;
import com.neoadventura.entities.*;
import com.neoadventura.exceptions.*;
import com.neoadventura.repositories.*;
import com.neoadventura.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwUsuarioDto CreateUsuario(CrUsuarioDto crUsuarioDto) throws NeoAdventuraException {
        Usuario usuario = new Usuario();
        Boolean is_valid = false;

        //When registered
        usuario.setName(crUsuarioDto.getName());
        usuario.setEmail(crUsuarioDto.getEmail());
        usuario.setBirth_day(crUsuarioDto.getBirth_day());
        usuario.setImg(crUsuarioDto.getImg());

        //By default
        usuario.setBanned(false);
        usuario.setSuscribed(false);
        usuario.setSame_language(false);
        usuario.setRol(getRolEntity(1L));
        usuario.setRegistered(usuarioRepository.getNow());

        usuario.setMonedero_virtual(BigDecimal.valueOf(0));
        usuario.setMonedero_oferta(BigDecimal.valueOf(0));

        if (VerifiedEmail(usuario.getEmail()) && usuario.getName().length()>0 &&
            usuarioRepository.getYearDiff(usuario.getBirth_day()) >= 18)
            is_valid = true;

        if (!is_valid) throw new FormatException("301", "NOMBRE o AGE estan mal");

        try {
            usuarioRepository.save(usuario);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_ERROR", "INTERNAL_ERROR");
        }
        return modelMapper.map(getUsuarioEntity(usuario.getId()), VwUsuarioDto.class);
    }

    @Override
    public VwUsuarioDto getUsuarioById(Long id) throws NeoAdventuraException {
        Usuario usuario = getUsuarioEntity(id);
        VwUsuarioDto vwUsuarioDto = modelMapper.map(usuario, VwUsuarioDto.class);
        vwUsuarioDto.setRol_name(usuario.getRol().getName());
        return vwUsuarioDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VwUsuarioDto> getUsuarios() throws NeoAdventuraException {
        List<Usuario> usuariosEntity = usuarioRepository.findAll();
        List<VwUsuarioDto> vwUsuarioDtos = usuariosEntity.stream().map(usuario -> modelMapper.map(usuario, VwUsuarioDto.class))
                .collect(Collectors.toList());
        for (int i = 0; i < vwUsuarioDtos.size(); i++) {
            vwUsuarioDtos.get(i).setRol_name(usuariosEntity.get(i).getRol().getName());
        }
        return vwUsuarioDtos;
    }

    @Override
    public VwAnfitrionDto getAnfitrionById(Long id) throws NeoAdventuraException {
        Usuario usuarioEntity = getUsuarioEntity(id);
        if (usuarioEntity.getRol().getId()!=2)
            throw new UnauthorizedException(new ErrorDto("THE USER EXITS BUT HAS NOT AUTHORIZED TO SHARE ITS PROFILE", "401"));
        VwAnfitrionDto vwAnfitrionDto = modelMapper.map(usuarioEntity, VwAnfitrionDto.class);
        return vwAnfitrionDto;
    }

    @Override
    public List<VwAnfitrionDto> getAnfitriones() throws NeoAdventuraException {
        List<Usuario> usuariosEntity = usuarioRepository.findAllByRol(getRolEntity(2L));
        List<VwAnfitrionDto> vwAnfitrionDtos = usuariosEntity.stream().map(usuario -> modelMapper.map(usuario, VwAnfitrionDto.class))
                .collect(Collectors.toList());
        return vwAnfitrionDtos;
    }

    @Override
    public VwUsuarioDto addIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException {
        Idioma idioma = getIdiomaEntity(idioma_id);

        Usuario usuario = getUsuarioEntity(usuario_id);

        usuario.addIdioma(idioma);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, VwUsuarioDto.class);
    }

    @Override
    public VwUsuarioDto delIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException {
        Idioma idioma = getIdiomaEntity(idioma_id);

        Usuario usuario = getUsuarioEntity(usuario_id);

        if (usuario.getIdiomas().size()<2)
            throw new FormatException("304", "HAVE TO HAVE 1 LANGUAGE SPECIFIED AT LEAST");

        usuario.delIdioma(idioma);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, VwUsuarioDto.class);
    }

    @Override
    public VwUsuarioDto switchSameLanguage(Long id) throws NeoAdventuraException {
        Usuario usuario = getUsuarioEntity(id);

        usuario.setSame_language(!usuario.getSame_language());

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, VwUsuarioDto.class);
    }

    @Override
    public VwUsuarioDto updateUsuario(UpUsuarioDto upUsuarioDto) throws NeoAdventuraException {
        Usuario usuario = getUsuarioEntity(upUsuarioDto.getId());
        Rol rol = getRolEntity(upUsuarioDto.getRol_id());

        Boolean is_valid = false;
        //Requirement to become an Anfitrion
        if ((usuario.getIdiomas().size()>0 || rol.getId()==1) &&
                VerifiedEmail(upUsuarioDto.getEmail()))
            is_valid = true;

        if (!is_valid) throw new FormatException("304", "ANFITRION REQUIREMENT: HAVE AT LEAST 1 LANGUAGE");

        usuario.setNickname("@" + upUsuarioDto.getNickname());
        usuario.setEmail(upUsuarioDto.getEmail());
        usuario.setImg(upUsuarioDto.getImg());
        usuario.setRol(rol);


        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, VwUsuarioDto.class);
    }

    private Boolean VerifiedEmail(String email) throws NeoAdventuraException {
        //In this case, it will only accept from Google, Outlook and Hotmail
        if (email.contains("@")) {
            String[] var = email.split("@");
            if (var[0].length()>0 && (
                    var[1].contentEquals("gmail.com") ||
                    var[1].contentEquals("outlook.com") ||
                    var[1].contentEquals("hotmail.com"
                    )))
                return true;
        }
        throw new FormatException(new ErrorDto("EMAIL ISN'T CORRECT", "304"));
    }


    private Idioma getIdiomaEntity(Long idioma_id) throws NeoAdventuraException {
        return idiomaRepository.findById(idioma_id)
                .orElseThrow(() -> new NotFoundException("404", "IDIOMA_IN_USER_NOT_FOUND"));
    }

    private Rol getRolEntity(Long rol_id) throws NeoAdventuraException {
        return rolRepository.findById(rol_id)
                .orElseThrow(() -> new NotFoundException("404", "ROL_IN_USER_NOT_FOUND"));
    }

    private Usuario getUsuarioEntity(Long id) throws NeoAdventuraException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("404", "USUARIO_NOTFOUND-404"));
    }

}
