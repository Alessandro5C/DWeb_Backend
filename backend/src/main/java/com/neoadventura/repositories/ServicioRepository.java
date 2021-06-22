package com.neoadventura.repositories;

import com.neoadventura.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio,Long> {

    List<Servicio> findAllByUsuario(Usuario usuario);

    //US21
    List<Servicio> findAllByModalidad(Modalidad modalidad);
    List<Servicio> findAllByPlataforma(Plataforma plataforma);
    List<Servicio> findAllByRegion(Region region);
//    List<Servicio> findAllByPais(Pais pais);
}
