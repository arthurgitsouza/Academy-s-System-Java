package com.wintech.portal.controller;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Disciplina;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.service.AlunoService;
import com.wintech.portal.service.DisciplinaService;
import com.wintech.portal.service.ProfessorService;
import com.wintech.portal.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin") // Endereço base para todas as ações do Admin
public class AdminController {

    // Este controller precisa de vários "cérebros" para gerenciar tudo
    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final TurmaService turmaService;
    private final DisciplinaService disciplinaService;

    // O Spring injeta todos os services que pedimos no construtor
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

    // --- Endpoints de Criação ---

    @PostMapping("/alunos")
    public ResponseEntity<Aluno> criarAluno(@RequestBody Aluno novoAluno) {
        // Recebe o objeto Aluno completo do front-end
        // A camada de Service (que a outra equipe fará) será responsável
        // por salvar o Aluno e também o Usuario que está dentro dele.
        Aluno alunoSalvo = alunoService.salvarNovoAluno(novoAluno); // Assumindo o nome do método no service
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    @PostMapping("/professores")
    public ResponseEntity<Professor> criarProfessor(@RequestBody Professor novoProfessor) {
        // Mesma lógica: recebe o Professor completo
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