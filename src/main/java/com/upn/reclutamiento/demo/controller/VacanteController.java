package com.upn.reclutamiento.demo.controller;


import com.upn.reclutamiento.demo.service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    @GetMapping("/vacantes")
    public String listarVacantes(Model model) {
        model.addAttribute("vacantes", vacanteService.listarTodas());
        return "vacantes"; 
    }
}

