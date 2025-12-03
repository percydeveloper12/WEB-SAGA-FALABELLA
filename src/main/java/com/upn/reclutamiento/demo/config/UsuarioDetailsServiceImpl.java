package com.upn.reclutamiento.demo.config;

import com.upn.reclutamiento.demo.model.Usuario;
import com.upn.reclutamiento.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }

        // Devuelve UserDetails con la contraseña tal cual
        return User.builder()
                .username(usuario.getCorreo()) // Spring validará contra el campo correo
                .password(usuario.getPassword()) // en texto plano si usas NoOpPasswordEncoder
                .roles(usuario.getRol()) // Ej: "POSTULANTE"
                .build();
    }
}
