package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.repository.MascotaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
    }

    public List<Mascota> listarDisponibles() {
        return mascotaRepository.findByEstado("DISPONIBLE");
    }

    public Optional<Mascota> buscarPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    public List<Mascota> buscarPorNombre(String nombre) {
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }

    public Mascota marcarAdoptada(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada: " + id));
        mascota.setEstado("ADOPTADO");
        return mascotaRepository.save(mascota);
    }

    public boolean existePorId(Long id) {
        return mascotaRepository.existsById(id);
    }
}
