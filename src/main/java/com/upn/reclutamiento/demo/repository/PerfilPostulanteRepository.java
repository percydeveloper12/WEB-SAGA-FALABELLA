package com.upn.reclutamiento.demo.repository;

import com.upn.reclutamiento.demo.model.PerfilPostulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilPostulanteRepository extends JpaRepository<PerfilPostulante, Long> {
    PerfilPostulante findByUsuarioCorreo(String correo);
}
