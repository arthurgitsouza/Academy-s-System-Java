package com.wintech.portal.service;

import com.wintech.portal.domain.Disciplina;
import com.wintech.portal.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    @Autowired
    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina salvar(Disciplina novaDisciplina) {
        return disciplinaRepository.save(novaDisciplina);
    }

    
}
