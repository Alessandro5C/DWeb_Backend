package com.neoadventura.exceptions;

import com.neoadventura.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
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
