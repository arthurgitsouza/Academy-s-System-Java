package com.wintech.portal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.TurmaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository, TurmaRepository turmaRepository, PasswordEncoder passwordEncoder) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Aluno> buscarPorTurma(Long idTurma) {
    
        //Buscar a Turma pelo ID
        Turma turma = turmaRepository.findById(idTurma)
            .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + idTurma));

        //Usar metódo do repositório para buscar alunos pela turma
        return alunoRepository.findByTurma(turma);
    }

    public Aluno salvarNovoAluno(Aluno novoAluno) {

        String senhaPura = novoAluno.getUsuario().getSenhaHash();
        String senhaCriptografada = passwordEncoder.encode(senhaPura);
        novoAluno.getUsuario().setSenhaHash(senhaCriptografada);
        return alunoRepository.save(novoAluno);
    }
    
}