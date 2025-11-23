package com.wintech.portal.controller;

import com.wintech.portal.dto.UsuarioCardDTO;
import com.wintech.portal.dto.CriarUsuarioDTO;
import com.wintech.portal.service.DashboardAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardAdminController {

    private final DashboardAdminService service;

    @Autowired
    public DashboardAdminController(DashboardAdminService service) {
        this.service = service;
    }

    /**
     * Listar todos os usuários (Alunos, Professores) com paginação
     * GET /api/admin/dashboard/usuarios?page=0&size=8&tipo=ALUNO&busca=João
     */
    @GetMapping("/usuarios")
    public ResponseEntity<Page<UsuarioCardDTO>> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String busca) {

        Pageable pageable = PageRequest.of(page, size);

        Page<UsuarioCardDTO> usuarios = service.listarUsuariosComFiltro(tipo, busca, pageable);

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Criar novo usuário (Aluno ou Professor)
     * POST /api/admin/dashboard/usuarios
     */
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioCardDTO> criarUsuario(@RequestBody CriarUsuarioDTO dto) {
        UsuarioCardDTO novoUsuario = service.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    /**
     * Buscar estatísticas do dashboard
     * GET /api/admin/dashboard/estatisticas
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<?> buscarEstatisticas() {
        return ResponseEntity.ok(service.buscarEstatisticas());
    }
}