package com.upn.reclutamiento.demo.controller;

import com.upn.reclutamiento.demo.model.PerfilPostulante;
import com.upn.reclutamiento.demo.model.Postulacion;
import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.model.Vacante;
import com.upn.reclutamiento.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PostulacionController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private VacanteRepository vacanteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilPostulanteRepository perfilPostulanteRepository;

    
    @PostMapping("/postular/{vacanteId}")
    public String postularAVacante(@PathVariable Long vacanteId, Principal principal) {
        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName());
        if (usuario == null) return "redirect:/login?error=UsuarioNoEncontrado";

        if (usuario.getPerfilPostulante() == null) {
            PerfilPostulante perfil = new PerfilPostulante();
            perfil.setUsuario(usuario);
            perfilPostulanteRepository.save(perfil);
            usuario.setPerfilPostulante(perfil);
            usuarioRepository.save(usuario);
        }

        Vacante vacante = vacanteRepository.findById(vacanteId).orElse(null);
        if (vacante == null) return "redirect:/proyecto?error=VacanteNoEncontrada";

        boolean yaPostulado = postulacionRepository
                .existsByPerfilPostulante_IdAndVacante_Id(usuario.getPerfilPostulante().getId(), vacanteId);
        if (yaPostulado) return "redirect:/proyecto?error=YaPostulado";

        Postulacion postulacion = new Postulacion();
        postulacion.setPerfilPostulante(usuario.getPerfilPostulante());
        postulacion.setVacante(vacante);
        postulacion.setFechaPostulacion(LocalDate.now());
        postulacion.setEstado("En proceso");

        postulacionRepository.save(postulacion);

        return "redirect:/proyecto?success=postulacion";
    }

    
    @GetMapping("/postulaciones")
    public String listarPostulaciones(Model model) {
        List<Postulacion> postulaciones = postulacionRepository.findAll();
        model.addAttribute("postulaciones", postulaciones);
        return "postulaciones";
    }

    
    @GetMapping("/postulacion/aprobar/{id}")
    public String aprobarPostulacion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postulacionRepository.findById(id).ifPresent(p -> {
            p.setEstado("Aprobado");
            postulacionRepository.save(p);
        });

        redirectAttributes.addFlashAttribute("mensaje", "✅ Postulación aprobada correctamente.");
        return "redirect:/dashboard";
    }

    
    @GetMapping("/postulacion/rechazar/{id}")
    public String rechazarPostulacion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postulacionRepository.findById(id).ifPresent(p -> postulacionRepository.delete(p));
        redirectAttributes.addFlashAttribute("mensaje", "❌ Postulación rechazada correctamente.");
        return "redirect:/dashboard";
    }
}
