package com.wintech.portal.controller;

import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.service.ComportamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comportamento")
public class ComportamentoController {

    private final ComportamentoService comportamentoService;

    @Autowired
    public ComportamentoController(ComportamentoService comportamentoService) {
        this.comportamentoService = comportamentoService;
    }

    /**
     * Buscar histórico completo de comportamento do aluno
     * GET /api/comportamento/aluno/{idAluno}
     */
    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<Comportamento>> buscarHistorico(@PathVariable Long idAluno) {
        try {
            List<Comportamento> historico = comportamentoService.buscarHistoricoDoAluno(idAluno);
            return ResponseEntity.ok(historico);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar comportamento em um bimestre específico
     * GET /api/comportamento/aluno/{idAluno}/bimestre/{bimestre}/ano/{ano}
     */
    @GetMapping("/aluno/{idAluno}/bimestre/{bimestre}/ano/{ano}")
    public ResponseEntity<Comportamento> buscarPorBimestre(
            @PathVariable Long idAluno,
            @PathVariable Integer bimestre,
            @PathVariable Integer ano) {
        try {
            Comportamento comportamento = comportamentoService.buscarPorBimestre(idAluno, ano, bimestre);
            return ResponseEntity.ok(comportamento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar status geral do aluno
     * GET /api/comportamento/aluno/{idAluno}/status
     */
    @GetMapping("/aluno/{idAluno}/status")
    public ResponseEntity<String> buscarStatusGeral(@PathVariable Long idAluno) {
        try {
            String status = comportamentoService.buscarStatusGeralDoAluno(idAluno);
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Salvar nova avaliação de comportamento
     * POST /api/comportamento
     */
    @PostMapping
    public ResponseEntity<Comportamento> salvarAvaliacao(@RequestBody Comportamento novaAvaliacao) {
        try {
            Comportamento salvo = comportamentoService.salvarAvaliacao(novaAvaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }



    /**
     * Atualizar avaliação existente
     * PUT /api/comportamento/{idComportamento}
     */
    @PutMapping("/{idComportamento}")
    public ResponseEntity<Comportamento> atualizarAvaliacao(
            @PathVariable Long idComportamento,
            @RequestBody Comportamento comportamentoAtualizado) {
        try {
            Comportamento atualizado = comportamentoService.atualizarAvaliacao(idComportamento, comportamentoAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletar avaliação
     * DELETE /api/comportamento/{idComportamento}
     */
    @DeleteMapping("/{idComportamento}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long idComportamento) {
        try {
            comportamentoService.deletarAvaliacao(idComportamento);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}