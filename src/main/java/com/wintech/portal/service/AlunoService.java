package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.domain.Usuario;
import com.wintech.portal.dto.AlunoRequestDTO;
import com.wintech.portal.dto.AlunoResponseDTO;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    // --- Método de Busca (Usado pelo ProfessorController) ---
    public List<Aluno> buscarPorTurma(Long idTurma) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + idTurma));
        return alunoRepository.findByTurma(turma);
    }

    // --- O MÉTODO PRINCIPAL DE SALVAR (Unificado e Correto) ---
    public AlunoResponseDTO salvarNovoAluno(AlunoRequestDTO dto) {

        // 1. Montar o objeto Usuario (Dados de Login)
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setPerfil("ALUNO"); // Define o perfil automaticamente
        usuario.setAtivo(true);

        // AQUI ESTÁ A SEGURANÇA: Criptografar a senha que veio do DTO
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        usuario.setSenhaHash(senhaCriptografada);

        // 2. Montar o objeto Aluno (Dados Específicos)
        Aluno aluno = new Aluno();
        aluno.setUsuario(usuario); // Vincula o usuário ao aluno
        aluno.setTelefone(dto.getTelefone());
        aluno.setNomeResponsavel(dto.getNomeResponsavel());
        aluno.setAtivo(true);

        // Conversão de String (DTO) para LocalDate (Entidade)
        // O DTO deve enviar a data no formato "AAAA-MM-DD" (ex: "2010-05-15")
        if (dto.getDataNascimento() != null) {
            aluno.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
        }

        // 3. Vincular a Turma
        if (dto.getIdTurma() != null) {
            Turma turma = turmaRepository.findById(dto.getIdTurma())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            aluno.setTurma(turma);
        }

        // 4. Salvar tudo (O CascadeType.ALL no Aluno vai salvar o Usuario automaticamente)
        Aluno alunoSalvo = alunoRepository.save(aluno);

        // 5. Retornar o DTO de resposta
        return new AlunoResponseDTO(alunoSalvo);
    }

    public AlunoResponseDTO buscarPorIdDTO(Long id) {
        return alunoRepository.findById(id)
                .map(AlunoResponseDTO::new)
                .orElse(null);
    }
}