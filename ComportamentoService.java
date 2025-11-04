package com.wintech.portal.service;

import com.wintech.portal.repository.ComportamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComportamentoService {

    private final ComportamentoRepository comportamentoRepository;

    @Autowired
    public ComportamentoService(ComportamentoRepository comportamentoRepository) {
        this.comportamentoRepository = comportamentoRepository;
    }

    
}
