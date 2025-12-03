package com.upn.reclutamiento.demo.controller;

import com.upn.reclutamiento.demo.dto.RegistroFormularioDTO;
import com.upn.reclutamiento.demo.model.PerfilPostulante;
import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.repository.PerfilPostulanteRepository;
import com.upn.reclutamiento.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FormularioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilPostulanteRepository perfilPostulanteRepository;

    @GetMapping("/formulario-crear")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("registro", new RegistroFormularioDTO());
        return "Formulariocrear";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("registro") RegistroFormularioDTO dto) {
        
        if (!dto.getPassword().equals(dto.getRepetirPassword())) {
            return "redirect:/formulario-crear?error=nomatch";
        }

       
        if (usuarioRepository.findByCorreo(dto.getCorreo()) != null) {
            return "redirect:/formulario-crear?error=correo";
        }

        
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword()); 
        usuario.setRol("POSTULANTE");

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        
        PerfilPostulante perfil = new PerfilPostulante();
        perfil.setUsuario(nuevoUsuario);
        perfil.setNombre(nuevoUsuario.getNombreUsuario());
        perfil.setApellido("");
        perfil.setDni("");
        perfil.setCv("");
        perfilPostulanteRepository.save(perfil);

        return "redirect:/login?exito=true";
    }
}
