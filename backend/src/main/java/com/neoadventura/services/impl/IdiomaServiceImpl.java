package com.neoadventura.services.impl;

import com.neoadventura.dtos.IdiomaDto;
import com.neoadventura.entities.Idioma;
import com.neoadventura.entities.Usuario;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.IdiomaRepository;
import com.neoadventura.repositories.UsuarioRepository;
import com.neoadventura.services.IdiomaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdiomaServiceImpl implements IdiomaService {
    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public IdiomaDto getIdiomaById(Long id) throws NeoAdventuraException {
        IdiomaDto idiomaDto = modelMapper.map(getIdiomaEntity(id), IdiomaDto.class);
        return idiomaDto;
    }

    @Override
    public List<IdiomaDto> getIdiomas() throws NeoAdventuraException {
        List<Idioma> idiomasEntity = idiomaRepository.findAll();
        List<IdiomaDto> idiomaDtos = idiomasEntity.stream().map(idioma -> modelMapper.map(idioma, IdiomaDto.class))
                .collect(Collectors.toList());
        return idiomaDtos;
    }

    @Override
    public List<IdiomaDto> getIdiomasByUsuarioId(Long usuario_id) throws NeoAdventuraException {
        List<Idioma> idiomasEntity = idiomaRepository.findAllByUsuario(getUsuarioEntity(usuario_id));
        List<IdiomaDto> idiomaDtos = idiomasEntity.stream().map(idioma -> modelMapper.map(idioma, IdiomaDto.class))
                .collect(Collectors.toList());
        return idiomaDtos;
    }

    private Usuario getUsuarioEntity(Long id) throws NeoAdventuraException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));
    }

    private Idioma getIdiomaEntity(Long id) throws NeoAdventuraException {
        return idiomaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "IDIOMA_NOTFOUND-404"));
    }
}
