package com.wintech.portal.controller;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Usuario;
import com.wintech.portal.dto.AvaliacaoComportamentoDTO;
import com.wintech.portal.dto.ComportamentoResponseDTO;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ProfessorRepository;
import com.wintech.portal.service.ComportamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class ComportamentoController {

    private final ComportamentoService comportamentoService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public ComportamentoController(ComportamentoService comportamentoService,
                                   AlunoRepository alunoRepository,
                                   ProfessorRepository professorRepository) {
        this.comportamentoService = comportamentoService;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    @PostMapping("/avaliar-comportamento")
    public ResponseEntity<?> avaliarComportamento(@RequestBody AvaliacaoComportamentoDTO dto) {
        try {
            System.out.println("📥 Recebendo avaliação de comportamento");
            System.out.println("   Aluno ID: " + dto.getAlunoId());
            System.out.println("   Bimestre: " + dto.getBimestre());
            System.out.println("   Responsabilidade: " + dto.getResponsabilidade());
            System.out.println("   Participação: " + dto.getParticipacao());
            System.out.println("   Comportamento: " + dto.getComportamento());

            // 1. Buscar o aluno
            Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            // 2. Buscar o professor autenticado
            Usuario usuarioAutenticado = (Usuario) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            Professor professor = professorRepository.findByUsuario(usuarioAutenticado)
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

            // 3. Criar ou atualizar avaliação
            Comportamento comportamento = comportamentoService.buscarOuCriarAvaliacao(
                    aluno,
                    professor,
                    LocalDate.now().getYear(),
                    dto.getBimestre()
            );

            // 4. Atualizar valores
            comportamento.setResponsabilidade(dto.getResponsabilidade());
            comportamento.setParticipacao(dto.getParticipacao());
            comportamento.setSociabilidade(dto.getComportamento());
            comportamento.setAssiduidade(5);
            comportamento.setObservacao(dto.getObservacao());

            // 5. Calcular status
            double media = (
                    dto.getResponsabilidade() +
                            dto.getParticipacao() +
                            dto.getComportamento()
            ) / 3.0;

            String status;
            if (media >= 4.5) status = "Excelente";
            else if (media >= 3.5) status = "Bom";
            else if (media >= 2.5) status = "Mediano";
            else if (media >= 1.5) status = "Ruim";
            else status = "Péssimo";

            comportamento.setStatus(status);
            comportamento.setDataRegistro(LocalDate.now());

            // 6. Salvar
            Comportamento salvo = comportamentoService.salvar(comportamento);

            System.out.println("✅ Avaliação salva com sucesso!");
            System.out.println("   Status calculado: " + status);

            // 7. Retornar DTO de resposta
            ComportamentoResponseDTO response = new ComportamentoResponseDTO(salvo);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            System.err.println("❌ Erro ao avaliar comportamento: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("error", e.getMessage())
            );
        }
    }
}