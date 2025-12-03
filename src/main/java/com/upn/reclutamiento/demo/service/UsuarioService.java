package com.upn.reclutamiento.demo.service;

import com.upn.reclutamiento.demo.dto.UsuarioPostulantedto;
import com.upn.reclutamiento.demo.model.PerfilPostulante;
import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.repository.PerfilPostulanteRepository;
import com.upn.reclutamiento.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilPostulanteRepository perfilPostulanteRepository;

    // 🔹 Registrar usuario + crear perfil vacío
    public Usuario registrarUsuario(Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        PerfilPostulante perfil = new PerfilPostulante();
        perfil.setUsuario(nuevoUsuario);
        perfil.setNombre(nuevoUsuario.getCorreo());
        perfil.setApellido("");
        perfilPostulanteRepository.save(perfil);

        return nuevoUsuario;
    }

    // 🔹 Obtener lista de postulantes (DTO)
    public List<UsuarioPostulantedto> obtenerPostulantes() {
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getCargo() != null && "Postulante".equalsIgnoreCase(u.getCargo().getNombre()))
            .map(u -> {
                var perfil = u.getPerfilPostulante();
                return new UsuarioPostulantedto(
                    u.getId(),
                    perfil != null ? perfil.getNombre() : "",
                    perfil != null ? perfil.getApellido() : "",
                    perfil != null ? perfil.getDni() : "",
                    perfil != null ? perfil.getCv() : ""
                );
            })
            .collect(Collectors.toList());
    }
}
