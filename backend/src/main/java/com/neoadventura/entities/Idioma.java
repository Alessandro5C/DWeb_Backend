package com.neoadventura.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name="idiomas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idioma {
    @Id
    @SequenceGenerator(
            name="idioma_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "idioma_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name="name",
            nullable = false,
            columnDefinition = "VARCHAR(15)"
    )
    private String name;

    @ManyToMany(mappedBy = "idiomas")
    private List<Usuario> usuario;

}
