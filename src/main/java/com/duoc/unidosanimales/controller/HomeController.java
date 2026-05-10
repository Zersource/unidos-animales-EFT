package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.service.MascotaService;
import com.duoc.unidosanimales.service.SolicitudAdopcionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MascotaService mascotaService;
    private final SolicitudAdopcionService solicitudService;

    public HomeController(MascotaService mascotaService,
                          SolicitudAdopcionService solicitudService) {
        this.mascotaService = mascotaService;
        this.solicitudService = solicitudService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/mascotas/catalogo";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalMascotas", mascotaService.listarTodas().size());
        model.addAttribute("disponibles", mascotaService.listarDisponibles().size());
        model.addAttribute("pendientes", solicitudService.contarPendientes());
        return "dashboard";
    }
}
