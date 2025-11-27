package com.wintech.portal.controller;

import com.wintech.portal.dto.PerfilAlunoDTO;
import com.wintech.portal.dto.PerfilProfessorDTO;
import com.wintech.portal.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PerfilController {

    private final PerfilService perfilService;

    @Autowired
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    /**
     * Buscar perfil de aluno
     * GET /api/alunos/{id}/perfil
     */
    @GetMapping("/alunos/{id}/perfil")
    public ResponseEntity<PerfilAlunoDTO> buscarPerfilAluno(@PathVariable Long id) {
        try {
            System.out.println("🔍 Buscando perfil do aluno ID: " + id);
            PerfilAlunoDTO perfil = perfilService.buscarPerfilAluno(id);
            System.out.println("✅ Perfil do aluno encontrado: " + perfil.getNome());
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            System.err.println("❌ Erro ao buscar perfil do aluno: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar perfil de professor
     * GET /api/professores/{id}/perfil
     */
    @GetMapping("/professores/{id}/perfil")
    public ResponseEntity<PerfilProfessorDTO> buscarPerfilProfessor(@PathVariable Long id) {
        try {
            System.out.println("🔍 Buscando perfil do professor ID: " + id);
            PerfilProfessorDTO perfil = perfilService.buscarPerfilProfessor(id);
            System.out.println("✅ Perfil do professor encontrado: " + perfil.getNome());
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            System.err.println("❌ Erro ao buscar perfil do professor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint alternativo para admin - Aluno
     * GET /api/admin/dashboard/alunos/{id}
     */
    @GetMapping("/admin/dashboard/alunos/{id}")
    public ResponseEntity<PerfilAlunoDTO> buscarPerfilAlunoAdmin(@PathVariable Long id) {
        return buscarPerfilAluno(id);
    }

    /**
     * Endpoint alternativo para admin - Professor
     * GET /api/admin/dashboard/professores/{id}
     */
    @GetMapping("/admin/dashboard/professores/{id}")
    public ResponseEntity<PerfilProfessorDTO> buscarPerfilProfessorAdmin(@PathVariable Long id) {
        return buscarPerfilProfessor(id);
    }
}