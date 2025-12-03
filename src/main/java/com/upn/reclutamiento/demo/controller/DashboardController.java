package com.upn.reclutamiento.demo.controller;

import com.upn.reclutamiento.demo.model.Postulacion;
import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.repository.PostulacionRepository;
import com.upn.reclutamiento.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, HttpSession session, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String correo = principal.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            session.invalidate();
            return "redirect:/login?error=usuarioNoEncontrado";
        }

        session.setAttribute("usuarioLogueado", usuario);
        model.addAttribute("usuario", usuario);

        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {

            List<Postulacion> postulaciones = postulacionRepository.findAll();

            Map<String, Long> conteo = postulaciones.stream()
                    .filter(p -> p.getVacante() != null && p.getVacante().getTitulo() != null)
                    .collect(Collectors.groupingBy(
                            p -> p.getVacante().getTitulo(),
                            LinkedHashMap::new,
                            Collectors.counting()
                    ));

            model.addAttribute("postulaciones", postulaciones);
            model.addAttribute("labels", new ArrayList<>(conteo.keySet()));
            model.addAttribute("datos", new ArrayList<>(conteo.values()));

            return "dashboard"; 
        }

        return "redirect:/proyecto";
    }

    
    @GetMapping("/dashboard/postulaciones")
    public String verPostulaciones(Model model, HttpSession session, Principal principal) {
        String correo = principal.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            session.invalidate();
            return "redirect:/login";
        }

        List<Postulacion> postulaciones = postulacionRepository.findAll();
        model.addAttribute("postulaciones", postulaciones);
        model.addAttribute("usuario", usuario);

        return "postulaciones";
    }
}
