package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ComportamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComportamentoService {

    private final ComportamentoRepository comportamentoRepository;
    private final AlunoRepository alunoRepository;

    @Autowired
    public ComportamentoService(ComportamentoRepository comportamentoRepository, AlunoRepository alunoRepository) {
        this.comportamentoRepository = comportamentoRepository;
        this.alunoRepository = alunoRepository;
    }

    /**
     * Buscar histórico completo de comportamento do aluno
     */
    public List<Comportamento> buscarHistoricoDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return comportamentoRepository.findByAlunoOrderByAnoLetivoDescBimestreDesc(aluno);
    }

    /**
     * Buscar comportamento de um aluno em um bimestre específico
     */
    public Comportamento buscarPorBimestre(Long idAluno, Integer anoLetivo, Integer bimestre) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return comportamentoRepository.findByAlunoAndAnoLetivoAndBimestre(aluno, anoLetivo, bimestre)
                .orElseThrow(() -> new RuntimeException("Comportamento não encontrado para este bimestre"));
    }

    /**
     * Buscar status geral (último comportamento registrado)
     */
    public String buscarStatusGeralDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<Comportamento> historico = comportamentoRepository
                .findByAlunoOrderByAnoLetivoDescBimestreDesc(aluno);

        if (historico.isEmpty()) {
            return "Sem avaliação";
        }

        return historico.get(0).getStatus();
    }

    /**
     * Salvar nova avaliação de comportamento
     */
    public Comportamento salvarAvaliacao(Comportamento novaAvaliacao) {
        novaAvaliacao.setDataRegistro(LocalDate.now());
        return comportamentoRepository.save(novaAvaliacao);
    }

    /**
     * Atualizar avaliação existente
     */
    public Comportamento atualizarAvaliacao(Long idComportamento, Comportamento comportamentoAtualizado) {
        Comportamento comportamento = comportamentoRepository.findById(idComportamento)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        comportamento.setStatus(comportamentoAtualizado.getStatus());
        comportamento.setObservacao(comportamentoAtualizado.getObservacao());

        return comportamentoRepository.save(comportamento);
    }

    /**
     * Deletar avaliação
     */
    public void deletarAvaliacao(Long idComportamento) {
        comportamentoRepository.deleteById(idComportamento);
    }
}