package com.upn.reclutamiento.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_postulante")
public class PerfilPostulante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private String cv;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // === Getters y Setters ===
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getApellido() { 
        return apellido; 
    }

    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }

    public String getDni() { 
        return dni; 
    }

    public void setDni(String dni) { 
        this.dni = dni; 
    }

    public String getCv() { 
        return cv; 
    }

    public void setCv(String cv) { 
        this.cv = cv; 
    }

    public Usuario getUsuario() { 
        return usuario; 
    }

    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario; 
    }
}
