package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ComportamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComportamentoService {

    private final ComportamentoRepository comportamentoRepository;
    private final AlunoRepository alunoRepository;


    @Autowired
    public ComportamentoService(ComportamentoRepository comportamentoRepository, AlunoRepository alunoRepository) {
        this.comportamentoRepository = comportamentoRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<Comportamento> buscarHistoricoDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(() -> new RuntimeException("Aluno no encontrado"));

        return comportamentoRepository.findByAlunoOrderByDataRegistroDesc(aluno);

    }

    public Comportamento salvar(Comportamento novaAvaliacao) {
        return comportamentoRepository.save(novaAvaliacao);
    }
    
}
