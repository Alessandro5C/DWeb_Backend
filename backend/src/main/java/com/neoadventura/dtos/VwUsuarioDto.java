package com.neoadventura.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class VwUsuarioDto {
    private Long id;
    private String name;
    private String img;
    private String nickname;
    private String email;
    private Date birth_day;
    private Date registered;
    private Boolean suscribed;
    private BigDecimal monedero_virtual;
    private BigDecimal monedero_oferta;
    private Boolean same_language;
    private Boolean banned;
    private String rol_name;
}
