package com.neoadventura.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name="usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name="usuario_name_unique",
                        columnNames = "name")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @SequenceGenerator(
            name="usuario_sequence",
            sequenceName = "usuario_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name="name",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String name;

    @Column(
            name="nickname",
            nullable = true,
            columnDefinition = "VARCHAR(10)"
    )
    private String nickname;

    @Column(
            name="email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name="birth_day",
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date birth_day;

    @Column(
            name="registered",
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date registered;

    @Column(
            name="suscribed",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean suscribed;

    @Column(
            name="monedero_virtual",
            nullable = false,
            columnDefinition = "NUMERIC"
    )
    private BigDecimal monedero_virtual;

    @Column(
            name="monedero_oferta",
            nullable = false,
            columnDefinition = "NUMERIC"
    )
    private BigDecimal monedero_oferta;

    @Column(
            name="same_language",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean same_language;

    @Column(
            name="banned",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean banned;


    @ManyToOne
    @JoinColumn(
            name="rol_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "rol_usuario_fk"
            )
    )
    private Rol rol;


    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Servicio> servicios = new ArrayList<>();

    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Pago> pagos = new ArrayList<>();

    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private List<Review> reviews = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="usuario_idioma",
            joinColumns = @JoinColumn(name="idioma_id"),
            inverseJoinColumns = @JoinColumn(name="usuario_id")
    )
    private List<Idioma> idiomas;

    public void addIdioma(Idioma idioma){
        if(idiomas.contains(idioma) == false) {
            this.idiomas.add(idioma);
        }
    }

    public void delIdioma(Idioma idioma){
        if(idiomas.contains(idioma)) {
            this.idiomas.remove(idioma);
        }
    }
}
