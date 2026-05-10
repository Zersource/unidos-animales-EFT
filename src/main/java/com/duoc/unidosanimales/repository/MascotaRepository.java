package com.duoc.unidosanimales.repository;

import com.duoc.unidosanimales.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByEstado(String estado);
    List<Mascota> findByEspecie(String especie);
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);
}
