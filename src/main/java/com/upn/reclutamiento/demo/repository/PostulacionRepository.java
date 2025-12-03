package com.upn.reclutamiento.demo.repository;

import com.upn.reclutamiento.demo.model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    
    boolean existsByPerfilPostulante_IdAndVacante_Id(Long perfilId, Long vacanteId);
}
