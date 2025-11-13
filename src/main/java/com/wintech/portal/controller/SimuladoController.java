package com.wintech.portal.controller;

import com.wintech.portal.domain.Questao;
import com.wintech.portal.domain.RespostaAluno;
import com.wintech.portal.domain.Simulado;
import com.wintech.portal.service.QuestaoService;
import com.wintech.portal.service.RespostaAlunoService;
import com.wintech.portal.service.SimuladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulados")
public class SimuladoController {

    // Este controller precisa de todos os services relacionados ao "Banco de Questões"
    private final SimuladoService simuladoService;
    private final QuestaoService questaoService;
    private final RespostaAlunoService respostaAlunoService;

    @Autowired
    public SimuladoController(SimuladoService simuladoService,
                              QuestaoService questaoService,
                              RespostaAlunoService respostaAlunoService) {
        this.simuladoService = simuladoService;
        this.questaoService = questaoService;
        this.respostaAlunoService = respostaAlunoService;
    }

    // --- Endpoints para Professores ---

    @PostMapping("/questoes")
    public ResponseEntity<Questao> criarQuestao(@RequestBody Questao novaQuestao) {
        Questao questaoSalva = questaoService.salvar(novaQuestao);
        return ResponseEntity.status(HttpStatus.CREATED).body(questaoSalva);
    }

    @PostMapping
    public ResponseEntity<Simulado> criarSimulado(@RequestBody Simulado novoSimulado) {
        Simulado simuladoSalvo = simuladoService.salvar(novoSimulado);
        return ResponseEntity.status(HttpStatus.CREATED).body(simuladoSalvo);
    }

    // --- Endpoints para Alunos ---

    @GetMapping("/{idSimulado}/questoes")
    public ResponseEntity<List<Questao>> buscarQuestoesDoSimulado(@PathVariable Long idSimulado) {
        // O service terá a lógica para buscar as questões deste simulado
        List<Questao> questoes = simuladoService.buscarQuestoes(idSimulado);
        return ResponseEntity.ok(questoes);
    }

    @PostMapping("/submeter-respostas")
    public ResponseEntity<Void> submeterRespostas(@RequestBody List<RespostaAluno> respostas) {
        // O front-end envia uma *lista* de respostas do aluno
        respostaAlunoService.salvarRespostas(respostas);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna 201 Created sem corpo
    }
}