package com.duoc.unidosanimales.service;

import com.duoc.unidosanimales.model.Adoptante;
import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.repository.SolicitudAdopcionRepository;
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
@DisplayName("Tests unitarios - SolicitudAdopcionService")
class SolicitudAdopcionServiceTest {
    @Mock private SolicitudAdopcionRepository solicitudRepository;
    @Mock private MascotaService mascotaService;
    @InjectMocks private SolicitudAdopcionService solicitudService;

    private Mascota mascota;
    private Adoptante adoptante;
    private SolicitudAdopcion solicitud;

    @BeforeEach
    void setUp() {
        mascota = new Mascota("Firulais", "Perro", "Mestizo", 3, "Desc", "DISPONIBLE", null);
        mascota.setId(1L);
        adoptante = new Adoptante("Maria", "maria@email.com", "+56912345678", "Vina del Mar", "Me gustan los animales");
        adoptante.setId(1L);
        solicitud = new SolicitudAdopcion(mascota, adoptante, "Quiero adoptarlo");
        solicitud.setId(1L);
    }

    @Test @DisplayName("Listar todas retorna lista")
    void listarTodas_retornaLista() {
        when(solicitudRepository.findAll()).thenReturn(List.of(solicitud));
        assertEquals(1, solicitudService.listarTodas().size());
    }

    @Test @DisplayName("Listar pendientes filtra por PENDIENTE")
    void listarPendientes_filtraCorrectamente() {
        when(solicitudRepository.findByEstado("PENDIENTE")).thenReturn(List.of(solicitud));
        assertEquals(1, solicitudService.listarPendientes().size());
    }

    @Test @DisplayName("Buscar por ID existente retorna solicitud")
    void buscarPorId_existente_retorna() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        assertTrue(solicitudService.buscarPorId(1L).isPresent());
    }

    @Test @DisplayName("Guardar solicitud llama al repositorio")
    void guardar_llama_repositorio() {
        when(solicitudRepository.save(any(SolicitudAdopcion.class))).thenReturn(solicitud);
        assertNotNull(solicitudService.guardar(solicitud));
        verify(solicitudRepository).save(solicitud);
    }

    @Test @DisplayName("Aprobar solicitud cambia estado y guarda coordinador")
    void aprobar_cambiaEstadoYGuardaCoordinador() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(mascotaService.marcarAdoptada(1L)).thenReturn(mascota);
        when(solicitudRepository.save(any(SolicitudAdopcion.class))).thenReturn(solicitud);
        SolicitudAdopcion r = solicitudService.aprobar(1L, "coordinador");
        assertEquals("APROBADA", r.getEstado());
        assertEquals("coordinador", r.getCoordinadorUsername());
        verify(mascotaService).marcarAdoptada(1L);
    }

    @Test @DisplayName("Rechazar solicitud cambia estado a RECHAZADA")
    void rechazar_cambiaEstado() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(solicitudRepository.save(any(SolicitudAdopcion.class))).thenReturn(solicitud);
        SolicitudAdopcion r = solicitudService.rechazar(1L, "coordinador");
        assertEquals("RECHAZADA", r.getEstado());
    }

    @Test @DisplayName("Aprobar ID inexistente lanza excepcion")
    void aprobar_inexistente_lanzaExcepcion() {
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> solicitudService.aprobar(99L, "admin"));
    }

    @Test @DisplayName("Contar pendientes retorna cantidad correcta")
    void contarPendientes_retornaCantidad() {
        when(solicitudRepository.findByEstado("PENDIENTE")).thenReturn(Arrays.asList(solicitud, new SolicitudAdopcion()));
        assertEquals(2, solicitudService.contarPendientes());
    }

    @Test @DisplayName("Eliminar solicitud llama deleteById")
    void eliminar_llama_deleteById() {
        doNothing().when(solicitudRepository).deleteById(1L);
        solicitudService.eliminar(1L);
        verify(solicitudRepository).deleteById(1L);
    }
}
