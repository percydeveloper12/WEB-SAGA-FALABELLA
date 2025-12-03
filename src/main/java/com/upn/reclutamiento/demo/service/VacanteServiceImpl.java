package com.upn.reclutamiento.demo.service;

import com.upn.reclutamiento.demo.model.Vacante;
import com.upn.reclutamiento.demo.repository.VacanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacanteServiceImpl implements VacanteService {

    @Autowired
    private VacanteRepository vacanteRepository;

    @Override
    public List<Vacante> listarTodas() {
        return vacanteRepository.findAll();
    }
}
