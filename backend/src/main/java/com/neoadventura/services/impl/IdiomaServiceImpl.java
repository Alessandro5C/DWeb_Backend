package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwIdiomaDto;
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
    public VwIdiomaDto getIdiomaById(Long id) throws NeoAdventuraException {
        VwIdiomaDto vwIdiomaDto = modelMapper.map(getIdiomaEntity(id), VwIdiomaDto.class);
        return vwIdiomaDto;
    }

    @Override
    public List<VwIdiomaDto> getIdiomas() throws NeoAdventuraException {
        List<Idioma> idiomasEntity = idiomaRepository.findAll();
        List<VwIdiomaDto> vwIdiomaDtos = idiomasEntity.stream().map(idioma -> modelMapper.map(idioma, VwIdiomaDto.class))
                .collect(Collectors.toList());
        return vwIdiomaDtos;
    }

    @Override
    public List<VwIdiomaDto> getIdiomasByUsuarioId(Long usuario_id) throws NeoAdventuraException {
        List<Idioma> idiomasEntity = idiomaRepository.findAllByUsuario(getUsuarioEntity(usuario_id));
        List<VwIdiomaDto> vwIdiomaDtos = idiomasEntity.stream().map(idioma -> modelMapper.map(idioma, VwIdiomaDto.class))
                .collect(Collectors.toList());
        return vwIdiomaDtos;
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
