package com.neoadventura.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

//@Entity
//@Table(
//        name="pago_servicios"
//)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class PagoServicio {
//    @EmbeddedId
//    private PagoServicioKey id;
//
//    @ManyToOne
//    @MapsId("pagoId")
//    @JoinColumn(
//            name="pago_id",
//            nullable = false,
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(
//                    name = "pago_pagoServicio_fk"
//            )
//    )
//    private Pago pago;
//
//    @ManyToOne
//    @MapsId("servicioId")
//    @JoinColumn(
//            name="servicio_id",
//            nullable = false,
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(
//                    name = "servicio_pagoServicio_fk"
//            )
//    )
//    private Servicio servicio;
//
//    @Column(
//            name="reported",
//            nullable = false,
//            columnDefinition = "BOOLEAN"
//    )
//    private Boolean reported;
//
//    @Column(
//            name="description",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
//    private String description;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        PagoServicio that = (PagoServicio) o;
//        return Objects.equals(pago, that.pago) &&
//                Objects.equals(servicio, that.servicio);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(pago, servicio);
//    }
//
}
