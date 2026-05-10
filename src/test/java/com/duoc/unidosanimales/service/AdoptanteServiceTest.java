package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.Adoptante;
import com.duoc.unidosanimales.repository.AdoptanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - AdoptanteService")
class AdoptanteServiceTest {

    @Mock
    private AdoptanteRepository adoptanteRepository;

    @InjectMocks
    private AdoptanteService adoptanteService;

    private Adoptante adoptante1;
    private Adoptante adoptante2;

    @BeforeEach
    void setUp() {
        adoptante1 = new Adoptante("Maria Gonzalez", "maria@email.com",
                "+56912345678", "Vina del Mar", "Tengo patio grande");
        adoptante1.setId(1L);

        adoptante2 = new Adoptante("Carlos Perez", "carlos@email.com",
                "+56987654321", "Valparaiso", "Mi familia quiere una mascota");
        adoptante2.setId(2L);
    }

    @Test
    @DisplayName("Listar todos los adoptantes retorna lista completa")
    void listarTodos_retornaLista() {
        when(adoptanteRepository.findAll()).thenReturn(Arrays.asList(adoptante1, adoptante2));

        List<Adoptante> resultado = adoptanteService.listarTodos();

        assertEquals(2, resultado.size());
        verify(adoptanteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar por ID existente retorna adoptante")
    void buscarPorId_existente_retornaAdoptante() {
        when(adoptanteRepository.findById(1L)).thenReturn(Optional.of(adoptante1));

        Optional<Adoptante> resultado = adoptanteService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Maria Gonzalez", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Buscar por ID inexistente retorna vacio")
    void buscarPorId_inexistente_retornaVacio() {
        when(adoptanteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Adoptante> resultado = adoptanteService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Buscar por email retorna adoptante correcto")
    void buscarPorEmail_retornaAdoptante() {
        when(adoptanteRepository.findByEmail("maria@email.com"))
                .thenReturn(Optional.of(adoptante1));

        Optional<Adoptante> resultado = adoptanteService.buscarPorEmail("maria@email.com");

        assertTrue(resultado.isPresent());
        assertEquals("maria@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Guardar adoptante llama al repositorio")
    void guardar_llama_repositorio() {
        when(adoptanteRepository.save(any(Adoptante.class))).thenReturn(adoptante1);

        Adoptante resultado = adoptanteService.guardar(adoptante1);

        assertNotNull(resultado);
        assertEquals("Maria Gonzalez", resultado.getNombre());
        verify(adoptanteRepository).save(adoptante1);
    }

    @Test
    @DisplayName("Eliminar adoptante llama deleteById")
    void eliminar_llama_deleteById() {
        doNothing().when(adoptanteRepository).deleteById(1L);

        adoptanteService.eliminar(1L);

        verify(adoptanteRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Existe por ID retorna correcto")
    void existePorId_retornaTrue() {
        when(adoptanteRepository.existsById(1L)).thenReturn(true);
        when(adoptanteRepository.existsById(99L)).thenReturn(false);

        assertTrue(adoptanteService.existePorId(1L));
        assertFalse(adoptanteService.existePorId(99L));
    }
}
