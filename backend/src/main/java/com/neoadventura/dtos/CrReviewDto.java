package com.neoadventura.dtos;

import lombok.Getter;

@Getter
public class CrReviewDto {
    //private Long reviewKey_id;
    private Long usuario_id;
    private Long servicio_id;

    private Integer score;
    private String description;
    private Boolean reported;
}
