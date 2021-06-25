package com.neoadventura.dtos;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

@Getter
public class CrServicioDto {
    private String name;
    private String img;
    private String description;
    private Date init_valid_date;
    private Date end_valid_date;
    private BigDecimal price;
    private Long modalidad_id;
    private Long region_id;
    private Long plataforma_id;
    private Long usuario_id;
}
