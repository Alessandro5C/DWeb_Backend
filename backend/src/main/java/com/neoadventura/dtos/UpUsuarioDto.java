package com.neoadventura.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpUsuarioDto {
    private Long id;
    private String nickname;
    private String email;
    private Long rol_id;
    private String img;
//    private Boolean suscribed;
//    private BigDecimal monedero_virtual;
}
