package com.neoadventura.dtos;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class CrUsuarioDto {
    private String name;
    private String email;
    private Date birth_day;
    private String img;
}
