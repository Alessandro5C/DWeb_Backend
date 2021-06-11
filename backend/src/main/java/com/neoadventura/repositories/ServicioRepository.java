package com.neoadventura.repositories;

import com.neoadventura.entities.Servicio;
import com.neoadventura.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio,Long> {

    List<Servicio> findAllByUsuario(Usuario usuario);
}
