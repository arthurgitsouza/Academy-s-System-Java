package com.wintech.portal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.dto.AlunoRequestDTO;
import com.wintech.portal.dto.AlunoResponseDTO;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.TurmaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    
    public Aluno fromRequestDto(AlunoRequestDTO dto) {
    if (dto == null) return null;

    Aluno aluno = new Aluno();
    aluno.setNome_aluno(dto.getNome());
    aluno.setEmail(dto.getEmail());
    aluno.setTelefone(dto.getTelefone());
    aluno.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
    // dataNascimento: se for LocalDate, converta apropriadamente
    // aluno.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));

    // vincular a Turma usando idTurma
    Long idTurma = dto.getIdTurma();
    if (idTurma != null) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        aluno.setTurma(turma);
    }

    // se houver senha: encriptar antes de setar (exemplo com BCrypt)
    // if (dto.getSenha() != null) aluno.setSenha(passwordEncoder.encode(dto.getSenha()));

    return aluno;
    }
    public AlunoResponseDTO salvarNovoAluno(AlunoRequestDTO dto) {
    Aluno aluno = fromRequestDto(dto);
    Objects.requireNonNull(aluno, "Aluno convertido a partir do DTO não pode ser nulo");

    // Aqui você pode aplicar validações de negócio:
    // ex: verificar se email já existe -> usuarioRepository.existsByEmail(aluno.getEmail())

    Aluno salvo = alunoRepository.save(aluno);

    // carregar comportamentos (se necessário) - geralmente já vem por JPA se mapeado corretamente
    // devolver o ResponseDTO (o construtor calcula a média e o status)
    return new AlunoResponseDTO(salvo);
    }

    public AlunoResponseDTO buscarPorIdDTO(Long id) {
    return alunoRepository.findById(id)
            .map(AlunoResponseDTO::new)
            .orElse(null);
    }
}
   