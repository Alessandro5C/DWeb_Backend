package com.neoadventura.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VwAnfitrionDto {
    private Long id;
    private String name;
    private String nickname;
    private Boolean banned;
    private String img;
//    private Long rol_id;
}
