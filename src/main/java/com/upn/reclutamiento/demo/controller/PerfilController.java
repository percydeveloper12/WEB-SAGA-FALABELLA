package com.upn.reclutamiento.demo.controller;

import com.upn.reclutamiento.demo.model.PerfilPostulante;
import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.repository.PerfilPostulanteRepository;
import com.upn.reclutamiento.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilPostulanteRepository perfilPostulanteRepository;

    // Mostrar perfil del usuario
    @GetMapping
    public String mostrarPerfil(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName());
        if (usuario == null) return "redirect:/login";

        model.addAttribute("usuario", usuario);

        // Asegurarse que exista el perfil del postulante
        PerfilPostulante perfil = usuario.getPerfilPostulante();
        if (perfil == null) {
            perfil = new PerfilPostulante();
            perfil.setUsuario(usuario);
            perfilPostulanteRepository.save(perfil);
            usuario.setPerfilPostulante(perfil);
            usuarioRepository.save(usuario);
        }

        model.addAttribute("perfil", perfil);
        return "perfil";
    }

    // Guardar cambios en perfil del usuario
    @PostMapping("/guardar")
    public String guardarPerfil(@RequestParam String nombre,
                                @RequestParam String apellido,
                                @RequestParam String dni,
                                @RequestParam String cv,
                                @RequestParam(required = false) String descripcion,
                                Principal principal) {

        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName());
        if (usuario == null) return "redirect:/login";

        // Actualizar datos del perfil
        PerfilPostulante perfil = usuario.getPerfilPostulante();
        if (perfil == null) {
            perfil = new PerfilPostulante();
            perfil.setUsuario(usuario);
        }

        perfil.setNombre(nombre);
        perfil.setApellido(apellido);
        perfil.setDni(dni);
        perfil.setCv(cv);

        perfilPostulanteRepository.save(perfil);

        // Guardar descripción en usuario (si aplica)
        if (descripcion != null) {
            usuario.setDescripcion(descripcion);
            usuarioRepository.save(usuario);
        }

        return "redirect:/perfil?success";
    }
}
