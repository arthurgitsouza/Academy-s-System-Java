package com.wintech.portal.service;


import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Simulado;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.SimuladoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimuladoService {

    private final SimuladoRepository simuladoRepository;
    private final AlunoRepository alunoRepository;

    public SimuladoService(SimuladoRepository simuladoRepository, AlunoRepository alunoRepository) {
        this.simuladoRepository = simuladoRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<Simulado> buscarSimuladosDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(()-> new RuntimeException("Aluno no encontrado"));

        Long idTurma = aluno.getTurma().getId_turma();
        return simuladoRepository.findByTurmaId(idTurma);

    }


}
