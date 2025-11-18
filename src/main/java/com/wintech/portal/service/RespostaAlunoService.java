package com.wintech.portal.service;

import com.wintech.portal.domain.RespostaAluno;
import com.wintech.portal.repository.RespostaAlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespostaAlunoService {

    private final RespostaAlunoRepository repository;

    public RespostaAlunoService(RespostaAlunoRepository repository) {
        this.repository = repository;
    }

    public void salvarRespostas(List<RespostaAluno> respostas) {
        repository.saveAll(respostas);
    }
}
