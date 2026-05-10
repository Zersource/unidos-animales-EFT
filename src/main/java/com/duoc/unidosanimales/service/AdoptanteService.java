package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.Adoptante;
import com.duoc.unidosanimales.repository.AdoptanteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdoptanteService {

    private final AdoptanteRepository adoptanteRepository;

    public AdoptanteService(AdoptanteRepository adoptanteRepository) {
        this.adoptanteRepository = adoptanteRepository;
    }

    public List<Adoptante> listarTodos() {
        return adoptanteRepository.findAll();
    }

    public Optional<Adoptante> buscarPorId(Long id) {
        return adoptanteRepository.findById(id);
    }

    public Optional<Adoptante> buscarPorEmail(String email) {
        return adoptanteRepository.findByEmail(email);
    }

    public Adoptante guardar(Adoptante adoptante) {
        return adoptanteRepository.save(adoptante);
    }

    public void eliminar(Long id) {
        adoptanteRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return adoptanteRepository.existsById(id);
    }
}
