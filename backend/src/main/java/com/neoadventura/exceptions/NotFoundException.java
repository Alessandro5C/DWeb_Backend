package com.neoadventura.exceptions;

import com.neoadventura.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends NeoAdventuraException {
    public NotFoundException(ErrorDto data) {
        super(data, HttpStatus.NOT_FOUND.value());
    }

    public NotFoundException(String code,String message){
        super("404", HttpStatus.NOT_FOUND.value(), message);
    }

    public NotFoundException(String code, String message, ErrorDto data){
        super("404", HttpStatus.NOT_FOUND.value(), message, Arrays.asList(data));
    }
}
