package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.repository.SolicitudAdopcionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudAdopcionService {

    private final SolicitudAdopcionRepository solicitudRepository;
    private final MascotaService mascotaService;

    public SolicitudAdopcionService(SolicitudAdopcionRepository solicitudRepository,
                                    MascotaService mascotaService) {
        this.solicitudRepository = solicitudRepository;
        this.mascotaService = mascotaService;
    }

    public List<SolicitudAdopcion> listarTodas() {
        return solicitudRepository.findAll();
    }

    public List<SolicitudAdopcion> listarPendientes() {
        return solicitudRepository.findByEstado("PENDIENTE");
    }

    public Optional<SolicitudAdopcion> buscarPorId(Long id) {
        return solicitudRepository.findById(id);
    }

    public SolicitudAdopcion guardar(SolicitudAdopcion solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public SolicitudAdopcion aprobar(Long id, String coordinadorUsername) {
        SolicitudAdopcion sol = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
        sol.setEstado("APROBADA");
        sol.setCoordinadorUsername(coordinadorUsername);
        // Marcar mascota como adoptada
        mascotaService.marcarAdoptada(sol.getMascota().getId());
        return solicitudRepository.save(sol);
    }

    public SolicitudAdopcion rechazar(Long id, String coordinadorUsername) {
        SolicitudAdopcion sol = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
        sol.setEstado("RECHAZADA");
        sol.setCoordinadorUsername(coordinadorUsername);
        return solicitudRepository.save(sol);
    }

    public void eliminar(Long id) {
        solicitudRepository.deleteById(id);
    }

    public long contarPendientes() {
        return solicitudRepository.findByEstado("PENDIENTE").size();
    }
}
