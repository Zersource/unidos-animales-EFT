package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.Adoptante;
import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.model.SolicitudAdopcion;
import com.duoc.unidosanimales.service.AdoptanteService;
import com.duoc.unidosanimales.service.MascotaService;
import com.duoc.unidosanimales.service.SolicitudAdopcionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudAdopcionController {

    private final SolicitudAdopcionService solicitudService;
    private final MascotaService mascotaService;
    private final AdoptanteService adoptanteService;

    public SolicitudAdopcionController(SolicitudAdopcionService solicitudService,
                                       MascotaService mascotaService,
                                       AdoptanteService adoptanteService) {
        this.solicitudService = solicitudService;
        this.mascotaService = mascotaService;
        this.adoptanteService = adoptanteService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("solicitudes", solicitudService.listarTodas());
        return "solicitudes/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("solicitud", new SolicitudAdopcion());
        model.addAttribute("mascotas", mascotaService.listarDisponibles());
        model.addAttribute("adoptantes", adoptanteService.listarTodos());
        return "solicitudes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long mascotaId,
                          @RequestParam Long adoptanteId,
                          @RequestParam(required = false) String mensaje,
                          RedirectAttributes flash) {
        Mascota mascota = mascotaService.buscarPorId(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        Adoptante adoptante = adoptanteService.buscarPorId(adoptanteId)
                .orElseThrow(() -> new RuntimeException("Adoptante no encontrado"));
        SolicitudAdopcion solicitud = new SolicitudAdopcion(mascota, adoptante, mensaje);
        solicitudService.guardar(solicitud);
        flash.addFlashAttribute("mensaje", "Solicitud enviada exitosamente");
        return "redirect:/solicitudes";
    }

    @GetMapping("/aprobar/{id}")
    public String aprobar(@PathVariable Long id, Authentication auth, RedirectAttributes flash) {
        solicitudService.aprobar(id, auth.getName());
        flash.addFlashAttribute("mensaje", "Solicitud aprobada");
        return "redirect:/solicitudes";
    }

    @GetMapping("/rechazar/{id}")
    public String rechazar(@PathVariable Long id, Authentication auth, RedirectAttributes flash) {
        solicitudService.rechazar(id, auth.getName());
        flash.addFlashAttribute("mensaje", "Solicitud rechazada");
        return "redirect:/solicitudes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        solicitudService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Solicitud eliminada");
        return "redirect:/solicitudes";
    }
}
