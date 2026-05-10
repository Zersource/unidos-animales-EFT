package com.duoc.unidosanimales;

import com.duoc.unidosanimales.model.Adoptante;
import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.repository.AdoptanteRepository;
import com.duoc.unidosanimales.repository.MascotaRepository;
import com.duoc.unidosanimales.repository.SolicitudAdopcionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MascotaRepository mascotaRepository;
    private final AdoptanteRepository adoptanteRepository;
    private final SolicitudAdopcionRepository solicitudRepository;

    public DataInitializer(MascotaRepository mascotaRepository,
                           AdoptanteRepository adoptanteRepository,
                           SolicitudAdopcionRepository solicitudRepository) {
        this.mascotaRepository = mascotaRepository;
        this.adoptanteRepository = adoptanteRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public void run(String... args) {
        // Solo cargar datos si la BD está vacía
        if (mascotaRepository.count() > 0) return;

        // ── Mascotas ──────────────────────────────────────────────────
        Mascota m1 = new Mascota("Firulais", "Perro", "Mestizo", 3,
                "Perro amigable y juguetón, le encanta correr.", "DISPONIBLE", null);
        Mascota m2 = new Mascota("Luna", "Gato", "Siamés", 2,
                "Gata tranquila, ideal para departamento.", "DISPONIBLE", null);
        Mascota m3 = new Mascota("Rocky", "Perro", "Labrador", 5,
                "Perro adulto, muy leal y entrenado.", "DISPONIBLE", null);
        Mascota m4 = new Mascota("Michi", "Gato", "Mestizo", 1,
                "Gatito joven, muy curioso y cariñoso.", "ADOPTADO", null);

        mascotaRepository.save(m1);
        mascotaRepository.save(m2);
        mascotaRepository.save(m3);
        mascotaRepository.save(m4);

        // ── Adoptantes ────────────────────────────────────────────────
        Adoptante a1 = new Adoptante("María González", "maria@email.com",
                "+56912345678", "Av. Principal 123, Viña del Mar",
                "Tengo patio grande y mucho amor para dar.");
        Adoptante a2 = new Adoptante("Carlos Pérez", "carlos@email.com",
                "+56987654321", "Calle Los Aromos 456, Valparaíso",
                "Mi familia y yo queremos darle un hogar a un perrito.");

        adoptanteRepository.save(a1);
        adoptanteRepository.save(a2);

        // ── Solicitudes ───────────────────────────────────────────────
        SolicitudAdopcion s1 = new SolicitudAdopcion(m1, a1,
                "Quisiera adoptar a Firulais, tengo experiencia con perros.");
        SolicitudAdopcion s2 = new SolicitudAdopcion(m2, a2,
                "Me interesa mucho Luna, vivo solo y sería perfecta.");

        solicitudRepository.save(s1);
        solicitudRepository.save(s2);

        System.out.println("✅ Datos de prueba cargados: 4 mascotas, 2 adoptantes, 2 solicitudes");
    }
}
