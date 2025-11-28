package com.wintech.portal.controller;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.service.AlunoService;
import com.wintech.portal.service.ComportamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    // Este controller precisa de dois services principais:
    private final AlunoService alunoService; // Para buscar os alunos
    private final ComportamentoService comportamentoService; // Para salvar a avaliação

    @Autowired
    public ProfessorController(AlunoService alunoService, ComportamentoService comportamentoService) {
        this.alunoService = alunoService;
        this.comportamentoService = comportamentoService;
    }

    /**
     * Endpoint para o "Carômetro" do Professor.
     * Busca todos os alunos de uma turma específica.
     *
     * @GetMapping: Responde a requisições GET (buscar dados).
     * "/turmas/{idTurma}/alunos": A URL vai receber o ID da turma.
     */
    @GetMapping("/turmas/{idTurma}/alunos")
    public ResponseEntity<List<Aluno>> buscarAlunosDaTurma(@PathVariable Long idTurma) {
        // @PathVariable: Pega o "idTurma" que veio na URL e usa como variável.

        // A lógica de "buscarAlunosDoProfessor" que discutimos antes ficará aqui
        // Por enquanto, apenas chamamos o service para buscar alunos daquela turma.
        List<Aluno> alunos = alunoService.buscarPorTurma(idTurma); // Assumindo o nome do método no service
        return ResponseEntity.ok(alunos);
    }

    /**
     * Endpoint para o professor registrar uma nova avaliação de comportamento (Carômetro).
     *
     * @PostMapping: Responde a requisições POST (enviar/criar dados).
     */
    @PostMapping("/avaliar-comportamento")
    public ResponseEntity<Comportamento> avaliarComportamento(@RequestBody Comportamento novaAvaliacao) {
        Comportamento avaliacaoSalva = comportamentoService.salvarAvaliacao(novaAvaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoSalva);
    }
}