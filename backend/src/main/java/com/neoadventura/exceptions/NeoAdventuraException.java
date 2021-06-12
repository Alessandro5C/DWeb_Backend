package com.neoadventura.exceptions;

import com.neoadventura.dtos.ErrorDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NeoAdventuraException extends Exception{
    private final String code;
    private final int responseCode;
    private final List<ErrorDto> errorList=new ArrayList<>();

    public NeoAdventuraException(ErrorDto errorDto, int responseCode) {
        super(errorDto.getName());
        this.code=errorDto.getValue();
        this.responseCode=responseCode;
    }

    public NeoAdventuraException(String code,int responseCode,String message){
        super(message);
        this.code=code;
        this.responseCode=responseCode;
    }

    public NeoAdventuraException(String code,int responseCode,String message,
                           List<ErrorDto> errorList){
        super(message);
        this.code=code;
        this.responseCode=responseCode;
        this.errorList.addAll(errorList);
    }
}
