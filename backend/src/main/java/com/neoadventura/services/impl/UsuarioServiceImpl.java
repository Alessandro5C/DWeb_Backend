package com.neoadventura.services.impl;

import com.neoadventura.dtos.AnfitrionDto;
import com.neoadventura.dtos.CreateUsuarioDto;
import com.neoadventura.dtos.UpUsuarioDto;
import com.neoadventura.dtos.UsuarioDto;
import com.neoadventura.entities.*;
import com.neoadventura.exceptions.InternalServerErrorException;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.*;
import com.neoadventura.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UsuarioDto CreateUsuario(CreateUsuarioDto createUsuarioDto) throws NeoAdventuraException {
        Rol rol = rolRepository.findById(createUsuarioDto.getRol_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "ROL_IN_USER_NOT_FOUND"));

        Usuario usuario = new Usuario();


        usuario.setName(createUsuarioDto.getName()); //
        usuario.setEmail(createUsuarioDto.getEmail()); //
        usuario.setBirth_day(createUsuarioDto.getBirth_day()); //
        usuario.setRegistered(usuarioRepository.getNow());
        usuario.setSuscribed(false);
        usuario.setMonedero_virtual(createUsuarioDto.getMonedero_oferta()); //corregir más adelante
        usuario.setMonedero_oferta(createUsuarioDto.getMonedero_oferta()); //
        usuario.setSame_language(false);
        usuario.setBanned(false);
        usuario.setRol(rol);


        try {
            usuarioRepository.save(usuario);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_ERROR", "INTERNAL_ERROR");
        }
        return modelMapper.map(getUsuarioEntity(usuario.getId()),UsuarioDto.class);
    }

    @Override
    public UsuarioDto getUsuarioById(Long id) throws NeoAdventuraException {
        Usuario usuario = getUsuarioEntity(id);
        UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);
        usuarioDto.setRol_id(usuario.getRol().getId());
        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> getUsuarios() throws NeoAdventuraException {
        List<Usuario> usuariosEntity = usuarioRepository.findAll();
        List<UsuarioDto> usuarioDtos = usuariosEntity.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .collect(Collectors.toList());
        for (int i = 0; i < usuarioDtos.size(); i++) {
            usuarioDtos.get(i).setRol_id(usuariosEntity.get(i).getRol().getId());
        }
        return usuarioDtos;
    }

    @Override
    public AnfitrionDto getAnfitrionById(Long id) throws NeoAdventuraException {
        AnfitrionDto anfitrionDto = modelMapper.map(getUsuarioEntity(id), AnfitrionDto.class);
        return anfitrionDto;
    }

    @Override
    public List<AnfitrionDto> getAnfitriones() throws NeoAdventuraException {
        List<Usuario> usuariosEntity = usuarioRepository.findAll();
        List<AnfitrionDto> anfitrionDtos = usuariosEntity.stream().map(usuario -> modelMapper.map(usuario, AnfitrionDto.class))
                .collect(Collectors.toList());
        return anfitrionDtos;
    }

    @Override
    public UsuarioDto addIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException {
        Idioma idioma = idiomaRepository.findById(idioma_id)
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "IDIOMA_NOT_FOUND"));

        Usuario usuario = getUsuarioEntity(usuario_id);

        usuario.addIdioma(idioma);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, UsuarioDto.class);
    }

    @Override
    public UsuarioDto delIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException {
        Idioma idioma = idiomaRepository.findById(idioma_id)
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "IDIOMA_NOT_FOUND"));

        Usuario usuario = getUsuarioEntity(usuario_id);

        usuario.delIdioma(idioma);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, UsuarioDto.class);
    }

    @Override
    public UsuarioDto switchSameLanguage(Long id) throws NeoAdventuraException {
        Usuario usuario = getUsuarioEntity(id);

        usuario.setSame_language(!usuario.getSame_language());

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, UsuarioDto.class);
    }

    @Override
    public UsuarioDto updateUsuario(UpUsuarioDto upUsuarioDto) throws NeoAdventuraException {
        Usuario usuario = getUsuarioEntity(upUsuarioDto.getId());

        usuario.setNickname("@" + upUsuarioDto.getNickname());
        if (VerifiedEmail(upUsuarioDto.getEmail()))
            usuario.setEmail(upUsuarioDto.getEmail());

        Rol rol = rolRepository.findById(upUsuarioDto.getRol_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "ROL_IN_USER_NOT_FOUND"));

        //Requirements to become an Anfitrion
        if (usuario.getIdiomas().size()>0 || rol.getId()==1)
            usuario.setRol(rol);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, UsuarioDto.class);
    }

    private Boolean VerifiedEmail(String email) {
        //In this case, it will only accept from Google, Outlook and Hotmail
        if (email.contains("@")) {
            String[] var = email.split("@");
            return (var[1].contentEquals("gmail.com") ||
                    var[1].contentEquals("outlook.com") ||
                    var[1].contentEquals("hotmail.com"));
        }
        return false;
    }


    private Usuario getUsuarioEntity(Long id) throws NeoAdventuraException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));
    }

}
