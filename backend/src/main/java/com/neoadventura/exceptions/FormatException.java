package com.neoadventura.exceptions;

import com.neoadventura.dtos.ErrorDto;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class FormatException extends NeoAdventuraException{
    public FormatException(String code,String message){
        super(code, HttpStatus.NOT_FOUND.value(), message);
    }

    public FormatException(String code, String message, ErrorDto data){
        super(code, HttpStatus.NOT_FOUND.value(), message, Arrays.asList(data));
    }
}
