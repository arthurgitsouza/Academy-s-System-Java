package com.wintech.portal.controller;

import com.wintech.portal.domain.Disciplina;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.dto.AlunoRequestDTO;  // Importando o DTO de entrada
import com.wintech.portal.dto.AlunoResponseDTO; // Importando o DTO de saída
import com.wintech.portal.service.AlunoService;
import com.wintech.portal.service.DisciplinaService;
import com.wintech.portal.service.ProfessorService;
import com.wintech.portal.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final TurmaService turmaService;
    private final DisciplinaService disciplinaService;

    @Autowired
    public AdminController(AlunoService alunoService,
                           ProfessorService professorService,
                           TurmaService turmaService,
                           DisciplinaService disciplinaService) {
        this.alunoService = alunoService;
        this.professorService = professorService;
        this.turmaService = turmaService;
        this.disciplinaService = disciplinaService;
    }

    // --- Endpoint de Criação de Aluno (ATUALIZADO PARA DTO) ---
    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponseDTO> criarAluno(@RequestBody AlunoRequestDTO requestDTO) {
        // 1. Recebe o DTO do Front-end
        // 2. Passa o DTO para o Service (que vai converter, criptografar senha e salvar)
        // 3. Recebe o DTO de resposta pronto do Service
        AlunoResponseDTO alunoSalvo = alunoService.salvarNovoAluno(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    // --- Outros Endpoints (Professor, Turma, Disciplina) ---
    // Obs: Idealmente, você faria o mesmo processo de DTOs para Professor também.
    // Por enquanto, mantive os outros como estavam para focar no conserto do Aluno.

    @PostMapping("/professores")
    public ResponseEntity<Professor> criarProfessor(@RequestBody Professor novoProfessor) {
        Professor professorSalvo = professorService.salvarNovoProfessor(novoProfessor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }

    @PostMapping("/turmas")
    public ResponseEntity<Turma> criarTurma(@RequestBody Turma novaTurma) {
        Turma turmaSalva = turmaService.salvar(novaTurma);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaSalva);
    }

    @PostMapping("/disciplinas")
    public ResponseEntity<Disciplina> criarDisciplina(@RequestBody Disciplina novaDisciplina) {
        Disciplina disciplinaSalva = disciplinaService.salvar(novaDisciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaSalva);
    }
}