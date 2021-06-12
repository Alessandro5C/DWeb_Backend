package com.neoadventura.exceptions;

import com.neoadventura.dtos.ErrorDto;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class FormatException extends NeoAdventuraException{
    public FormatException(ErrorDto data){
        super(data, HttpStatus.NOT_MODIFIED.value());
    }

    public FormatException(String code,String message){
        super(code, HttpStatus.NOT_MODIFIED.value(), message);
    }

    public FormatException(String code, String message, ErrorDto data){
        super(code, HttpStatus.NOT_MODIFIED.value(), message, Arrays.asList(data));
    }
}
