package com.neoadventura.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAsAnfitrionDto {
    private Long id;
    private String name;
    private String nickname;
    private Boolean banned;
    private Long rol_id;
}
