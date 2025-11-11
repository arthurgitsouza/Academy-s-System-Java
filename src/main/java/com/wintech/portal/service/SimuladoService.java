package com.wintech.portal.service;

import com.wintech.portal.repository.SimuladoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimuladoService {

    private final SimuladoRepository simuladoRepository;

    @Autowired
    public SimuladoService(SimuladoRepository simuladoRepository) {
        this.simuladoRepository = simuladoRepository;
    }

    
}
