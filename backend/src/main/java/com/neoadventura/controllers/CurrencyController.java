package com.neoadventura.controllers;

import com.neoadventura.dtos.CurrencyDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/neo-adventura"+"/v1")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/currencys/{currencyId}")
    public NeoAdventuraResponse<CurrencyDto> getCurrencyById(@PathVariable Long currencyId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                currencyService.getCurrencyById(currencyId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/currencys")
    public NeoAdventuraResponse<List<CurrencyDto>> getCurrencys()
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                currencyService.getCurrencys());
    }
}
