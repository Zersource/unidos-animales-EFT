package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.repository.MascotaRepository;
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
@DisplayName("Tests unitarios - MascotaService")
class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

    private Mascota mascota1;
    private Mascota mascota2;

    @BeforeEach
    void setUp() {
        mascota1 = new Mascota("Firulais", "Perro", "Mestizo", 3,
                "Perro amigable", "DISPONIBLE", null);
        mascota1.setId(1L);

        mascota2 = new Mascota("Luna", "Gato", "Siames", 2,
                "Gata tranquila", "DISPONIBLE", null);
        mascota2.setId(2L);
    }

    @Test
    @DisplayName("Listar todas las mascotas retorna lista completa")
    void listarTodas_retornaListaCompleta() {
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(mascota1, mascota2));

        List<Mascota> resultado = mascotaService.listarTodas();

        assertEquals(2, resultado.size());
        verify(mascotaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar disponibles filtra por estado DISPONIBLE")
    void listarDisponibles_retornaDisponibles() {
        when(mascotaRepository.findByEstado("DISPONIBLE"))
                .thenReturn(Arrays.asList(mascota1, mascota2));

        List<Mascota> resultado = mascotaService.listarDisponibles();

        assertEquals(2, resultado.size());
        verify(mascotaRepository).findByEstado("DISPONIBLE");
    }

    @Test
    @DisplayName("Buscar por ID existente retorna mascota")
    void buscarPorId_existente_retornaMascota() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota1));

        Optional<Mascota> resultado = mascotaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Firulais", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Buscar por ID inexistente retorna Optional vacio")
    void buscarPorId_inexistente_retornaVacio() {
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Mascota> resultado = mascotaService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Guardar mascota llama al repositorio")
    void guardar_llama_repositorio() {
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota1);

        Mascota resultado = mascotaService.guardar(mascota1);

        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getNombre());
        verify(mascotaRepository, times(1)).save(mascota1);
    }

    @Test
    @DisplayName("Eliminar mascota llama deleteById")
    void eliminar_llama_deleteById() {
        doNothing().when(mascotaRepository).deleteById(1L);

        mascotaService.eliminar(1L);

        verify(mascotaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Marcar como adoptada cambia estado a ADOPTADO")
    void marcarAdoptada_cambiaEstado() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota1));
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota1);

        Mascota resultado = mascotaService.marcarAdoptada(1L);

        assertEquals("ADOPTADO", resultado.getEstado());
        verify(mascotaRepository).save(mascota1);
    }

    @Test
    @DisplayName("Marcar adoptada con ID inexistente lanza excepcion")
    void marcarAdoptada_inexistente_lanzaExcepcion() {
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> mascotaService.marcarAdoptada(99L));
    }

    @Test
    @DisplayName("Existe por ID retorna true cuando existe")
    void existePorId_retornaTrue() {
        when(mascotaRepository.existsById(1L)).thenReturn(true);

        assertTrue(mascotaService.existePorId(1L));
    }

    @Test
    @DisplayName("Buscar por nombre llama al repositorio con el nombre")
    void buscarPorNombre_llamaRepositorio() {
        when(mascotaRepository.findByNombreContainingIgnoreCase("firu"))
                .thenReturn(List.of(mascota1));

        List<Mascota> resultado = mascotaService.buscarPorNombre("firu");

        assertEquals(1, resultado.size());
        verify(mascotaRepository).findByNombreContainingIgnoreCase("firu");
    }
}
