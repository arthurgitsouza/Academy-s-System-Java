package com.wintech.portal.controller;

import com.wintech.portal.dto.AlunoCardDTO;
import com.wintech.portal.dto.FiltroAlunosDTO;
import com.wintech.portal.service.DashboardProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professor/dashboard")
public class DashboardProfessorController {

    private final DashboardProfessorService service;

    @Autowired
    public DashboardProfessorController(DashboardProfessorService service) {
        this.service = service;
    }

    /**
     * Listar alunos do professor com paginação
     * GET /api/professor/dashboard/alunos?page=0&size=8&turma=1&busca=João
     */
    @GetMapping("/alunos")
    public ResponseEntity<Page<AlunoCardDTO>> listarAlunos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) Long idTurma,
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String statusComportamento) {

        Pageable pageable = PageRequest.of(page, size);

        FiltroAlunosDTO filtro = new FiltroAlunosDTO(idTurma, busca, statusComportamento);

        Page<AlunoCardDTO> alunos = service.listarAlunosComFiltro(filtro, pageable);

        return ResponseEntity.ok(alunos);
    }

    /**
     * Buscar turmas do professor
     * GET /api/professor/dashboard/turmas
     */
    @GetMapping("/turmas")
    public ResponseEntity<?> listarTurmasDoProfessor() {
        // Pegar o professor autenticado do contexto do Spring Security
        // Long idProfessor = ... extrair do token

        return ResponseEntity.ok(service.buscarTurmasDoProfessor(1L)); // TODO: pegar ID real
    }
}