package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Usuario;
import com.wintech.portal.dto.*;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ProfessorRepository;
import com.wintech.portal.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardAdminService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DashboardAdminService(AlunoRepository alunoRepository,
                                 ProfessorRepository professorRepository,
                                 TurmaRepository turmaRepository,
                                 PasswordEncoder passwordEncoder) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.turmaRepository = turmaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Listar usuários com filtros e paginação
     */
    public Page<UsuarioCardDTO> listarUsuariosComFiltro(String tipo, String busca, Pageable pageable) {
        List<UsuarioCardDTO> todosUsuarios = new ArrayList<>();

        // Se tipo for ALUNO ou null, buscar alunos
        if (tipo == null || tipo.equals("ALUNO")) {
            List<Aluno> alunos = alunoRepository.findAll();
            todosUsuarios.addAll(alunos.stream()
                    .map(this::converterAlunoParaCard)
                    .collect(Collectors.toList()));
        }

        // Se tipo for PROFESSOR ou null, buscar professores
        if (tipo == null || tipo.equals("PROFESSOR")) {
            List<Professor> professores = professorRepository.findAll();
            todosUsuarios.addAll(professores.stream()
                    .map(this::converterProfessorParaCard)
                    .collect(Collectors.toList()));
        }

        // Filtrar por busca
        if (busca != null && !busca.isEmpty()) {
            todosUsuarios = todosUsuarios.stream()
                    .filter(u -> u.getNome().toLowerCase().contains(busca.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Aplicar paginação manual
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), todosUsuarios.size());

        List<UsuarioCardDTO> paginados = todosUsuarios.subList(start, end);

        return new PageImpl<>(paginados, pageable, todosUsuarios.size());
    }

    /**
     * Criar novo usuário (Aluno ou Professor)
     */
    public UsuarioCardDTO criarUsuario(CriarUsuarioDTO dto) {
        // Validar senhas
        if (!dto.getSenha().equals(dto.getConfirmarSenha())) {
            throw new RuntimeException("As senhas não coincidem");
        }

        if (dto.getTipo().equals("ALUNO")) {
            return criarAluno(dto);
        } else if (dto.getTipo().equals("PROFESSOR")) {
            return criarProfessor(dto);
        } else {
            throw new RuntimeException("Tipo de usuário inválido");
        }
    }

    private UsuarioCardDTO criarAluno(CriarUsuarioDTO dto) {
        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNomeCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        usuario.setPerfil("ALUNO");
        usuario.setAtivo(true);

        // Criar aluno
        Aluno aluno = new Aluno();
        aluno.setUsuario(usuario);
        aluno.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
        aluno.setAtivo(true);

        // Vincular turma
        if (dto.getIdTurma() != null) {
            aluno.setTurma(turmaRepository.findById(dto.getIdTurma())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada")));
        }

        Aluno alunoSalvo = alunoRepository.save(aluno);
        return converterAlunoParaCard(alunoSalvo);
    }

    private UsuarioCardDTO criarProfessor(CriarUsuarioDTO dto) {
        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNomeCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        usuario.setPerfil("PROFESSOR");
        usuario.setAtivo(true);

        // Criar professor
        Professor professor = new Professor();
        professor.setUsuario(usuario);
        professor.setMatricula(dto.getMatricula());
        professor.setEspecialidade(dto.getEspecialidade());
        professor.setAtivo(true);

        Professor professorSalvo = professorRepository.save(professor);
        return converterProfessorParaCard(professorSalvo);
    }

    /**
     * Buscar estatísticas do dashboard
     */
    public EstatisticasDTO buscarEstatisticas() {
        long totalAlunos = alunoRepository.count();
        long totalProfessores = professorRepository.count();

        return new EstatisticasDTO(totalAlunos, totalProfessores);
    }

    // Métodos auxiliares de conversão
    private UsuarioCardDTO converterAlunoParaCard(Aluno aluno) {
        UsuarioCardDTO card = new UsuarioCardDTO();
        card.setId(aluno.getId_aluno());
        card.setNome(aluno.getUsuario() != null ? aluno.getUsuario().getNome() : "");
        card.setFoto(aluno.getFoto());
        card.setTipo("ALUNO");
        card.setStatus(calcularStatusAluno(aluno));

        if (aluno.getDataNascimento() != null) {
            card.setIdade(java.time.Period.between(aluno.getDataNascimento(), LocalDate.now()).getYears());
        }

        return card;
    }

    private UsuarioCardDTO converterProfessorParaCard(Professor professor) {
        UsuarioCardDTO card = new UsuarioCardDTO();
        card.setId(professor.getId_professor());
        card.setNome(professor.getUsuario() != null ? professor.getUsuario().getNome() : "");
        card.setTipo("PROFESSOR");
        card.setStatus("Ativo");
        card.setTotalTurmas(3); // TODO: calcular real
        return card;
    }

    private String calcularStatusAluno(Aluno aluno) {
        if (aluno.getComportamentos() == null || aluno.getComportamentos().isEmpty()) {
            return "Novo";
        }
        return "Matriculado";
    }
}