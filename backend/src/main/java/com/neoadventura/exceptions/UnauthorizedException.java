package com.neoadventura.exceptions;

import com.neoadventura.dtos.ErrorDto;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class UnauthorizedException extends NeoAdventuraException {
    public UnauthorizedException(ErrorDto data) {
        super(data, HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String code, String message){
        super("401", HttpStatus.UNAUTHORIZED.value(), message);
    }

    public UnauthorizedException(String code, String message, ErrorDto data){
        super("401", HttpStatus.UNAUTHORIZED.value(), message, Arrays.asList(data));
    }
}
