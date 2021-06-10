package com.neoadventura.dtos;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class CrUsuarioDto {
    private String name;
    private String email;
    private Date birth_day;
    //private Date registered;
    //private Boolean suscribed;
    private BigDecimal monedero_oferta;
    private Boolean same_language;
    //private Boolean banned;
    private Long rol_id;
}
