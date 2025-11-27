package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.dto.PerfilAlunoDTO;
import com.wintech.portal.dto.PerfilProfessorDTO;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;

    @Autowired
    public PerfilService(ProfessorRepository professorRepository,
                         AlunoRepository alunoRepository) {
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }

    public PerfilProfessorDTO buscarPerfilProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + id));

        return new PerfilProfessorDTO(professor);
    }

    public PerfilAlunoDTO buscarPerfilAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

        return new PerfilAlunoDTO(aluno);
    }
}