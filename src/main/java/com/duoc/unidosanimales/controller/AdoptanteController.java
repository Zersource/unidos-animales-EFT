package com.duoc.unidosanimales.controller;

import com.duoc.unidosanimales.model.Adoptante;
import com.duoc.unidosanimales.service.AdoptanteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adoptantes")
public class AdoptanteController {

    private final AdoptanteService adoptanteService;

    public AdoptanteController(AdoptanteService adoptanteService) {
        this.adoptanteService = adoptanteService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("adoptantes", adoptanteService.listarTodos());
        return "adoptantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("adoptante", new Adoptante());
        return "adoptantes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Adoptante adoptante,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "adoptantes/formulario";
        }
        adoptanteService.guardar(adoptante);
        flash.addFlashAttribute("mensaje", "Adoptante registrado exitosamente");
        return "redirect:/adoptantes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        adoptanteService.buscarPorId(id).ifPresent(a -> model.addAttribute("adoptante", a));
        return "adoptantes/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        adoptanteService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Adoptante eliminado");
        return "redirect:/adoptantes";
    }
}
