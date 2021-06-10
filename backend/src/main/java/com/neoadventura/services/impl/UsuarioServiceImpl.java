package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwAnfitrionDto;
import com.neoadventura.dtos.CrUsuarioDto;
import com.neoadventura.dtos.UpUsuarioDto;
import com.neoadventura.dtos.VwUsuarioDto;
import com.neoadventura.entities.*;
import com.neoadventura.exceptions.FormatException;
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
    public VwUsuarioDto CreateUsuario(CrUsuarioDto crUsuarioDto) throws NeoAdventuraException {
        Rol rol = rolRepository.findById(crUsuarioDto.getRol_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "ROL_IN_USER_NOT_FOUND"));

        Usuario usuario = new Usuario();


        usuario.setName(crUsuarioDto.getName()); //
        usuario.setEmail(crUsuarioDto.getEmail()); //
        usuario.setBirth_day(crUsuarioDto.getBirth_day()); //
        usuario.setRegistered(usuarioRepository.getNow());
        usuario.setSuscribed(false);
        usuario.setMonedero_virtual(crUsuarioDto.getMonedero_oferta()); //corregir más adelante
        usuario.setMonedero_oferta(crUsuarioDto.getMonedero_oferta()); //
        usuario.setSame_language(false);
        usuario.setBanned(false);
        usuario.setRol(rol);


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
        vwUsuarioDto.setRol_id(usuario.getRol().getId());
        return vwUsuarioDto;
    }

    @Override
    public List<VwUsuarioDto> getUsuarios() throws NeoAdventuraException {
        List<Usuario> usuariosEntity = usuarioRepository.findAll();
        List<VwUsuarioDto> vwUsuarioDtos = usuariosEntity.stream().map(usuario -> modelMapper.map(usuario, VwUsuarioDto.class))
                .collect(Collectors.toList());
        for (int i = 0; i < vwUsuarioDtos.size(); i++) {
            vwUsuarioDtos.get(i).setRol_id(usuariosEntity.get(i).getRol().getId());
        }
        return vwUsuarioDtos;
    }

    @Override
    public VwAnfitrionDto getAnfitrionById(Long id) throws NeoAdventuraException {
        VwAnfitrionDto vwAnfitrionDto = modelMapper.map(getUsuarioEntity(id), VwAnfitrionDto.class);
        return vwAnfitrionDto;
    }

    @Override
    public List<VwAnfitrionDto> getAnfitriones() throws NeoAdventuraException {
        List<Usuario> usuariosEntity = usuarioRepository.findAll();
        List<VwAnfitrionDto> vwAnfitrionDtos = usuariosEntity.stream().map(usuario -> modelMapper.map(usuario, VwAnfitrionDto.class))
                .collect(Collectors.toList());
        return vwAnfitrionDtos;
    }

    @Override
    public VwUsuarioDto addIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException {
        Idioma idioma = idiomaRepository.findById(idioma_id)
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "IDIOMA_NOT_FOUND"));

        Usuario usuario = getUsuarioEntity(usuario_id);

        usuario.addIdioma(idioma);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, VwUsuarioDto.class);
    }

    @Override
    public VwUsuarioDto delIdioma(Long usuario_id, Long idioma_id) throws NeoAdventuraException {
        Idioma idioma = idiomaRepository.findById(idioma_id)
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "IDIOMA_NOT_FOUND"));

        Usuario usuario = getUsuarioEntity(usuario_id);

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

        Rol rol = rolRepository.findById(upUsuarioDto.getRol_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "ROL_IN_USER_NOT_FOUND"));
        Boolean is_valid = false;
        //Requirement to become an Anfitrion
        if ((usuario.getIdiomas().size()>0 || rol.getId()==1) &&
                VerifiedEmail(upUsuarioDto.getEmail()))
            is_valid = true;

        if (!is_valid) throw new FormatException("304", "NOT MODIFIED");

        usuario.setNickname("@" + upUsuarioDto.getNickname());
        usuario.setEmail(upUsuarioDto.getEmail());
        usuario.setRol(rol);

        Usuario saveUsuario = this.usuarioRepository.save(usuario);
        return modelMapper.map(saveUsuario, VwUsuarioDto.class);
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
