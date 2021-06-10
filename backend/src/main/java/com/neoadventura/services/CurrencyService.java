package com.neoadventura.services;

import com.neoadventura.dtos.VwCurrencyDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface CurrencyService {
    VwCurrencyDto getCurrencyById(Long id) throws NeoAdventuraException;
    List<VwCurrencyDto> getCurrencys() throws NeoAdventuraException;
}
