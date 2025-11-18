package com.wintech.portal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.domain.TurmaDisciplina;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ProfessorRepository;
import com.wintech.portal.repository.TurmaDisciplinaRepository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ProfessorService(ProfessorRepository professorRepository, TurmaDisciplinaRepository turmaDisciplinaRepository, AlunoRepository alunoRepository, PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.turmaDisciplinaRepository = turmaDisciplinaRepository;
        this.alunoRepository = alunoRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public List<Aluno> buscarAlunosDoProfessor(Long idProfessor) {

        //Buscar Professor pelo ID
        Professor professor = professorRepository.findById(idProfessor)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + idProfessor));

        //Buscar máterias do Professor
        List<TurmaDisciplina> materias = turmaDisciplinaRepository.findByProfessor(professor);

        //Criar uma Lista de Turmas
        List<Turma> turmas = new ArrayList<>();

        for (TurmaDisciplina materia : materias) {
            turmas.add(materia.getTurma());
            }

        //Para cada Turma, buscar os alunos e juntar em uma lista final
        List<Aluno> alunos = new ArrayList<>();

        for (Turma turma : turmas) {
            alunos.addAll(alunoRepository.findByTurma(turma));
        }
        //Retorna Lista completa de Alunos
        return alunos;

        }

    public Professor salvarNovoProfessor(Professor novoProfessor) {

        String senhaPura = novoProfessor.getUsuario().getSenhaHash();

        String senhaCriptografada = passwordEncoder.encode(senhaPura);

        novoProfessor.getUsuario().setSenhaHash(senhaCriptografada);

        return professorRepository.save(novoProfessor);
    }
}
