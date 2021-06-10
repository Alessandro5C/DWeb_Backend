package com.neoadventura.services.impl;

import com.neoadventura.dtos.CrPagoDto;
import com.neoadventura.entities.Currency;
import com.neoadventura.entities.Metodo;
import com.neoadventura.entities.Pago;
import com.neoadventura.entities.Usuario;
import com.neoadventura.exceptions.InternalServerErrorException;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.CurrencyRepository;
import com.neoadventura.repositories.MetodoRepository;
import com.neoadventura.repositories.PagoRepository;
import com.neoadventura.repositories.UsuarioRepository;
import com.neoadventura.services.PagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private MetodoRepository metodoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final ModelMapper modelMapper = new ModelMapper();


    @Override
    public CrPagoDto CreatePago(CrPagoDto crPagoDto) throws NeoAdventuraException {
        Currency currency = currencyRepository.findById(crPagoDto.getCurrency_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "CURRENCY_NOT_FOUND"));

        Metodo metodo = metodoRepository.findById(crPagoDto.getMetodo_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "METODO_NOT_FOUND"));

        Usuario usuario = usuarioRepository.findById(crPagoDto.getUsuario_id())
                .orElseThrow(() -> new NotFoundException("NOT-401-1", "USUARIO_NOT_FOUND"));


        Pago pago = new Pago();


        pago.setCurrency(currency);
        pago.setMetodo(metodo);
        pago.setUsuario(usuario);
        pago.setPay_date(crPagoDto.getPay_date());
        pago.setMount(crPagoDto.getMount());


        try {
            pagoRepository.save(pago);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_ERROR", "INTERNAL_ERROR");
        }
        return modelMapper.map(getPagoEntity(pago.getId()), CrPagoDto.class);
    }

    @Override
    public CrPagoDto getPagoById(Long id) throws NeoAdventuraException {
        Pago pago =getPagoEntity(id);
        CrPagoDto crPagoDto = modelMapper.map(getPagoEntity(id), CrPagoDto.class);
        crPagoDto.setCurrency_id(pago.getCurrency().getId());
        crPagoDto.setMetodo_id(pago.getMetodo().getId());
        crPagoDto.setUsuario_id(pago.getUsuario().getId());
        return crPagoDto;
    }

    @Override
    public List<CrPagoDto> getPagos() throws NeoAdventuraException {
        List<Pago> pagosEntity = pagoRepository.findAll();
        List<CrPagoDto> crPagoDtos = pagosEntity.stream().map(servicio -> modelMapper.map(servicio, CrPagoDto.class))
                .collect(Collectors.toList());
        for (int i = 0; i < crPagoDtos.size(); i++) {
            crPagoDtos.get(i).setCurrency_id(pagosEntity.get(i).getCurrency().getId());
            crPagoDtos.get(i).setMetodo_id(pagosEntity.get(i).getMetodo().getId());
            crPagoDtos.get(i).setUsuario_id(pagosEntity.get(i).getUsuario().getId());
        }
        return crPagoDtos;
    }

    private Pago getPagoEntity(Long id) throws NeoAdventuraException {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "PAGO_NOTFOUND-404"));
    }

}
