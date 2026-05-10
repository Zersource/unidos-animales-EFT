package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.Mascota;
import com.duoc.unidosanimales.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // ── Catálogo público ──────────────────────────────────────────────
    @GetMapping("/catalogo")
    public String catalogo(Model model, @RequestParam(required = false) String buscar) {
        if (buscar != null && !buscar.isBlank()) {
            model.addAttribute("mascotas", mascotaService.buscarPorNombre(buscar));
            model.addAttribute("buscar", buscar);
        } else {
            model.addAttribute("mascotas", mascotaService.listarDisponibles());
        }
        return "mascotas/catalogo";
    }

    // ── Lista completa (privada) ───────────────────────────────────────
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/lista";
    }

    // ── Formulario nueva mascota ───────────────────────────────────────
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "mascotas/formulario";
    }

    // ── Guardar nueva mascota ──────────────────────────────────────────
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Mascota mascota,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "mascotas/formulario";
        }
        mascotaService.guardar(mascota);
        flash.addFlashAttribute("mensaje", "Mascota guardada exitosamente");
        return "redirect:/mascotas";
    }

    // ── Formulario editar ──────────────────────────────────────────────
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        mascotaService.buscarPorId(id).ifPresent(m -> model.addAttribute("mascota", m));
        return "mascotas/formulario";
    }

    // ── Eliminar mascota ───────────────────────────────────────────────
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        mascotaService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Mascota eliminada");
        return "redirect:/mascotas";
    }
}
