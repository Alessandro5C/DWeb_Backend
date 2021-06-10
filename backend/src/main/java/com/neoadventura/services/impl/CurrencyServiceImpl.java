package com.neoadventura.services.impl;

import com.neoadventura.dtos.VwCurrencyDto;
import com.neoadventura.entities.Currency;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.CurrencyRepository;
import com.neoadventura.services.CurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public VwCurrencyDto getCurrencyById(Long id) throws NeoAdventuraException {
        return modelMapper.map(getCurrencyEntityById(id), VwCurrencyDto.class);
    }

    @Override
    public List<VwCurrencyDto> getCurrencys() throws NeoAdventuraException {
        List<Currency> currencyEntity = currencyRepository.findAll();

        return currencyEntity.stream().map(currency -> modelMapper.map(currency, VwCurrencyDto.class))
                .collect(Collectors.toList());
    }

    private Currency getCurrencyEntityById(Long id) throws NeoAdventuraException{
        return currencyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "CURRENCY_NOTFOUND-404"));
    }
}
