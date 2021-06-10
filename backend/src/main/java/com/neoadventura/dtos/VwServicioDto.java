package com.neoadventura.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

@Getter
@Setter
public class VwServicioDto {
    private Long id;
    private String name;
    private String description;
    private Date init_valid_date;
    private Date end_valid_date;
    private BigDecimal price;
    private String modalidad_name;
    private String region_name;
    private String plataforma_name;
    private String usuario_name;
}
