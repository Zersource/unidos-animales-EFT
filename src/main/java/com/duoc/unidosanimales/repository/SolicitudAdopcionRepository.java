package com.duoc.unidosanimales.repository;

import com.duoc.unidosanimales.model.SolicitudAdopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudAdopcionRepository extends JpaRepository<SolicitudAdopcion, Long> {
    List<SolicitudAdopcion> findByEstado(String estado);
    List<SolicitudAdopcion> findByMascotaId(Long mascotaId);
    List<SolicitudAdopcion> findByAdoptanteId(Long adoptanteId);
}
