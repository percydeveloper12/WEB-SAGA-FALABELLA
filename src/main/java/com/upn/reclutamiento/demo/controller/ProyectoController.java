package com.upn.reclutamiento.demo.controller;

import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.model.Vacante;
import com.upn.reclutamiento.demo.repository.UsuarioRepository;
import com.upn.reclutamiento.demo.repository.VacanteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ProyectoController {

    private final VacanteRepository vacanteRepository;
    private final UsuarioRepository usuarioRepository;

    public ProyectoController(VacanteRepository vacanteRepository,
                              UsuarioRepository usuarioRepository) {
        this.vacanteRepository = vacanteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    
    @GetMapping("/proyecto")
    public String verVacantes(@RequestParam(name = "busqueda", required = false) String busqueda,
                              Model model,
                              Principal principal) {

        
        if (principal != null) {
            Usuario usuario = usuarioRepository.findByCorreo(principal.getName());
            model.addAttribute("usuario", usuario);
        }

        List<Vacante> vacantes;

        if (busqueda != null && !busqueda.isEmpty()) {
            
            if (busqueda.equalsIgnoreCase("rrhh")) busqueda = "Recursos Humanos";
            else if (busqueda.equalsIgnoreCase("it") || busqueda.equalsIgnoreCase("sistemas")) busqueda = "Sistemas";
            else if (busqueda.equalsIgnoreCase("logistica")) busqueda = "Logística";
            else if (busqueda.equalsIgnoreCase("marketing")) busqueda = "Marketing";

            vacantes = vacanteRepository.findByTituloContainingIgnoreCaseOrAreaContainingIgnoreCase(busqueda, busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            vacantes = vacanteRepository.findAll();
        }

        model.addAttribute("vacantes", vacantes);
        return "proyecto";
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
