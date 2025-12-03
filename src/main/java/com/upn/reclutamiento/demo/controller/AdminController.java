package com.upn.reclutamiento.demo.controller;

import com.upn.reclutamiento.demo.model.Postulacion;
import com.upn.reclutamiento.demo.repository.PostulacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @GetMapping("/admin/postulaciones")
    public String listarPostulaciones(Model model) {
        List<Postulacion> postulaciones = postulacionRepository.findAll();
        model.addAttribute("postulaciones", postulaciones);
        return "admin/postulaciones"; 
    }
}
