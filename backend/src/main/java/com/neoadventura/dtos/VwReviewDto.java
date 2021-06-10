package com.neoadventura.dtos;

import com.neoadventura.entities.ReviewKey;
import com.neoadventura.entities.Servicio;
import com.neoadventura.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class VwReviewDto {

    //private Long reviewKey_id;
    private Long usuario_id;
    private Long servicio_id;

    private Integer score;
    private String description;
    private Boolean reported;

}
