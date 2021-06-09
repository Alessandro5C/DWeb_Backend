package com.neoadventura.repositories;

import com.neoadventura.dtos.IdiomaDto;
import com.neoadventura.entities.Idioma;
import com.neoadventura.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {

    List<Idioma> findAllByUsuario(Usuario usuario);
}
