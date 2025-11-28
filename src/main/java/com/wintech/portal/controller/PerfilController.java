package com.wintech.portal.controller;

import com.wintech.portal.dto.PerfilAlunoDTO;
import com.wintech.portal.dto.PerfilProfessorDTO;
import com.wintech.portal.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * Endpoint de teste para verificar se a rota existe
     * GET /api/meu-perfil/test
     */
    @GetMapping("/meu-perfil/test")
    public ResponseEntity<String> teste() {
        System.out.println("✅ ENDPOINT /api/meu-perfil/test ESTÁ FUNCIONANDO!");
        return ResponseEntity.ok("Endpoint funcionando! Autenticação: " +
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Buscar perfil do USUÁRIO AUTENTICADO (ALUNO)
     * GET /api/meu-perfil/aluno
     */
    @GetMapping("/meu-perfil/aluno")
    public ResponseEntity<PerfilAlunoDTO> buscarMeuPerfilAluno() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();

            String email;

            // Se o principal é um usuário, extrair o email
            if (principal instanceof com.wintech.portal.domain.Usuario) {
                com.wintech.portal.domain.Usuario usuario = (com.wintech.portal.domain.Usuario) principal;
                email = usuario.getEmail();
            } else {
                email = auth.getName();
            }

            System.out.println("🔍 Buscando perfil do aluno autenticado: " + email);
            System.out.println("📋 Principal: " + principal);
            System.out.println("🔐 Authorities: " + auth.getAuthorities());

            PerfilAlunoDTO perfil = perfilService.buscarPerfilAlunoPorEmail(email);
            System.out.println("✅ Perfil do aluno encontrado: " + perfil.getNome());
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            System.err.println("❌ Erro ao buscar perfil do aluno: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar perfil do USUÁRIO AUTENTICADO (PROFESSOR)
     * GET /api/meu-perfil/professor
     */
    @GetMapping("/meu-perfil/professor")
    public ResponseEntity<PerfilProfessorDTO> buscarMeuPerfilProfessor() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();

            String email;

            // Se o principal é um usuário, extrair o email
            if (principal instanceof com.wintech.portal.domain.Usuario) {
                com.wintech.portal.domain.Usuario usuario = (com.wintech.portal.domain.Usuario) principal;
                email = usuario.getEmail();
            } else {
                email = auth.getName();
            }

            System.out.println("🔍 Buscando perfil do professor autenticado: " + email);
            PerfilProfessorDTO perfil = perfilService.buscarPerfilProfessorPorEmail(email);
            System.out.println("✅ Perfil do professor encontrado: " + perfil.getNome());
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            System.err.println("❌ Erro ao buscar perfil do professor: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar perfil de aluno por ID (APENAS PARA ADMIN)
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
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar perfil de professor por ID (APENAS PARA ADMIN)
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