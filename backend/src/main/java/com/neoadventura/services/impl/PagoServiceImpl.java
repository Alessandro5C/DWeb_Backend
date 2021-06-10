package com.neoadventura.services.impl;

import com.neoadventura.dtos.CrPagoDto;
import com.neoadventura.dtos.VwPagoDto;
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
    public VwPagoDto getPagoById(Long id) throws NeoAdventuraException {
        Pago pago =getPagoEntity(id);
        VwPagoDto vwPagoDto = modelMapper.map(getPagoEntity(id), VwPagoDto.class);
        vwPagoDto.setCurrency_name(pago.getCurrency().getId());
        vwPagoDto.setMetodo_name(pago.getMetodo().getId());
        vwPagoDto.setUsuario_name(pago.getUsuario().getId());
        return vwPagoDto;
    }

    @Override
    public List<VwPagoDto> getPagos() throws NeoAdventuraException {
        List<Pago> pagosEntity = pagoRepository.findAll();
        List<VwPagoDto> vwPagoDtos = pagosEntity.stream().map(servicio -> modelMapper.map(servicio, VwPagoDto.class))
                .collect(Collectors.toList());
        for (int i = 0; i < vwPagoDtos.size(); i++) {
            vwPagoDtos.get(i).setCurrency_name(pagosEntity.get(i).getCurrency().getId());
            vwPagoDtos.get(i).setMetodo_name(pagosEntity.get(i).getMetodo().getId());
            vwPagoDtos.get(i).setUsuario_name(pagosEntity.get(i).getUsuario().getId());
        }
        return vwPagoDtos;
    }

    private Pago getPagoEntity(Long id) throws NeoAdventuraException {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "PAGO_NOTFOUND-404"));
    }

}
