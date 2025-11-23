package com.wintech.portal.controller;

import com.wintech.portal.dto.PerfilAlunoDTO;
import com.wintech.portal.dto.HistoricoComportamentoDTO;
import com.wintech.portal.service.DashboardAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aluno/dashboard")
public class DashboardAlunoController {

    private final DashboardAlunoService service;

    @Autowired
    public DashboardAlunoController(DashboardAlunoService service) {
        this.service = service;
    }

    /**
     * Buscar perfil completo do aluno
     * GET /api/aluno/dashboard/perfil/{id}
     */
    @GetMapping("/perfil/{id}")
    public ResponseEntity<PerfilAlunoDTO> buscarPerfil(@PathVariable Long id) {
        PerfilAlunoDTO perfil = service.buscarPerfilCompleto(id);
        return ResponseEntity.ok(perfil);
    }

    /**
     * Buscar histórico de comportamento por bimestre
     * GET /api/aluno/dashboard/{id}/comportamento
     */
    @GetMapping("/{id}/comportamento")
    public ResponseEntity<List<HistoricoComportamentoDTO>> buscarHistorico(@PathVariable Long id) {
        List<HistoricoComportamentoDTO> historico = service.buscarHistoricoPorBimestre(id);
        return ResponseEntity.ok(historico);
    }
}