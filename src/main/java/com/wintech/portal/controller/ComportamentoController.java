package com.wintech.portal.controller;

import com.wintech.portal.domain.*;
import com.wintech.portal.dto.AvaliacaoComportamentoDTO;
import com.wintech.portal.dto.ComportamentoResponseDTO;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ProfessorRepository;
import com.wintech.portal.repository.TurmaDisciplinaRepository;
import com.wintech.portal.service.ComportamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api") // Ou /api/professores se preferir manter o padrão antigo
public class ComportamentoController {

    private final ComportamentoService comportamentoService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    public ComportamentoController(ComportamentoService comportamentoService,
                                   AlunoRepository alunoRepository,
                                   ProfessorRepository professorRepository,
                                   TurmaDisciplinaRepository turmaDisciplinaRepository) {
        this.comportamentoService = comportamentoService;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.turmaDisciplinaRepository = turmaDisciplinaRepository;
    }

    @PostMapping("/avaliar-comportamento")
    public ResponseEntity<?> avaliarComportamento(@RequestBody AvaliacaoComportamentoDTO dto) {
        try {
            // 1. Buscar o aluno
            Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            // 2. Buscar o professor autenticado
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Usuario usuarioAutenticado = (Usuario) principal;

            Professor professor = professorRepository.findByUsuario(usuarioAutenticado)
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado para o usuário logado"));

            // 3. Descobrir a Disciplina (Lógica Automática)
            List<TurmaDisciplina> tds = turmaDisciplinaRepository.findByProfessor(professor);

            Disciplina disciplina = tds.stream()
                    .filter(td -> td.getTurma().getId_turma().equals(aluno.getTurma().getId_turma()))
                    .map(TurmaDisciplina::getDisciplina)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Este professor não dá aula para a turma deste aluno!"));

            // 4. Criar a Nova Avaliação (Simplificado)
            Comportamento comportamento = new Comportamento();
            comportamento.setAluno(aluno);
            comportamento.setProfessor(professor);
            comportamento.setDisciplina(disciplina); // ✅ Agora temos a disciplina!
            comportamento.setDataRegistro(LocalDate.now());

            // 5. Preencher as Notas
            comportamento.setResponsabilidade(dto.getResponsabilidade());
            comportamento.setParticipacao(dto.getParticipacao());
            comportamento.setSociabilidade(dto.getComportamento()); // Mapeia 'comportamento' do DTO para 'sociabilidade'
            comportamento.setAssiduidade(5); // Valor padrão fixo, já que não vem do front
            comportamento.setObservacao(dto.getObservacao());

            // ❌ REMOVIDO: comportamento.setStatus(...) -> Esse campo não existe mais no banco!
            // O cálculo do status (Excelente/Bom) é feito apenas na hora de ler (no DTO de resposta).

            // 6. Salvar
            Comportamento salvo = comportamentoService.salvarAvaliacao(comportamento);

            // Retorna o DTO que calcula o status automaticamente para o front ver
            return ResponseEntity.ok(new ComportamentoResponseDTO(salvo));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }
}