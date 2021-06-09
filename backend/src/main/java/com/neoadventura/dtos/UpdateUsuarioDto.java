package com.neoadventura.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateUsuarioDto {
    private Long id;
    private String nickname;
    private String email;
//    private Boolean suscribed;
//    private BigDecimal monedero_virtual;
//    private Long rol_id;
}
