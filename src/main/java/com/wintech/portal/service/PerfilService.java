package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Usuario;
import com.wintech.portal.dto.PerfilAlunoDTO;
import com.wintech.portal.dto.PerfilProfessorDTO;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ProfessorRepository;
import com.wintech.portal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PerfilService(ProfessorRepository professorRepository,
                         AlunoRepository alunoRepository,
                         UsuarioRepository usuarioRepository) {
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Buscar perfil do professor por ID
     */
    public PerfilProfessorDTO buscarPerfilProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + id));

        return new PerfilProfessorDTO(professor);
    }

    /**
     * Buscar perfil do aluno por ID
     */
    public PerfilAlunoDTO buscarPerfilAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

        return new PerfilAlunoDTO(aluno);
    }

    /**
     * Buscar perfil do aluno pelo EMAIL do usuário autenticado
     */
    public PerfilAlunoDTO buscarPerfilAlunoPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));

        Aluno aluno = alunoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o email: " + email));

        return new PerfilAlunoDTO(aluno);
    }

    /**
     * Buscar perfil do professor pelo EMAIL do usuário autenticado
     */
    public PerfilProfessorDTO buscarPerfilProfessorPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));

        Professor professor = professorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado para o email: " + email));

        return new PerfilProfessorDTO(professor);
    }
}