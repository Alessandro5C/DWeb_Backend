package com.neoadventura.controllers;

import com.neoadventura.dtos.VwCurrencyDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/neo-adventura"+"/v1")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/currencys/{currencyId}")
    public NeoAdventuraResponse<VwCurrencyDto> getCurrencyById(@PathVariable Long currencyId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                currencyService.getCurrencyById(currencyId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/currencys")
    public NeoAdventuraResponse<List<VwCurrencyDto>> getCurrencys()
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Succes",String.valueOf(HttpStatus.OK),"OK",
                currencyService.getCurrencys());
    }
}
