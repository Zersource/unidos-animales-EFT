package com.duoc.unidosanimales.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitudes_adopcion")
public class SolicitudAdopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String estado; // PENDIENTE / APROBADA / RECHAZADA

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    // Quién procesó la solicitud (coordinador o admin)
    private String coordinadorUsername;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "adoptante_id", nullable = false)
    private Adoptante adoptante;

    public SolicitudAdopcion() {
        this.fecha = LocalDate.now();
        this.estado = "PENDIENTE";
    }

    public SolicitudAdopcion(Mascota mascota, Adoptante adoptante, String mensaje) {
        this.fecha = LocalDate.now();
        this.estado = "PENDIENTE";
        this.mascota = mascota;
        this.adoptante = adoptante;
        this.mensaje = mensaje;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getCoordinadorUsername() { return coordinadorUsername; }
    public void setCoordinadorUsername(String coordinadorUsername) {
        this.coordinadorUsername = coordinadorUsername;
    }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante adoptante) { this.adoptante = adoptante; }
}
