package com.neoadventura.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class VwPagoDto {
    private Long id;

    private Date pay_date;
    private BigDecimal mount;

    private Long metodo_name;
    private Long currency_name;
    private Long usuario_name;

    private Long servicio_name;
}
