package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.domain.Professor;
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
    public ComportamentoService(ComportamentoRepository comportamentoRepository,
                                AlunoRepository alunoRepository) {
        this.comportamentoRepository = comportamentoRepository;
        this.alunoRepository = alunoRepository;
    }

    /**
     * Buscar ou criar avaliação para um aluno em um bimestre específico
     */
    public Comportamento buscarOuCriarAvaliacao(Aluno aluno, Professor professor,
                                                Integer anoLetivo, Integer bimestre) {
        return comportamentoRepository
                .findByAlunoAndAnoLetivoAndBimestre(aluno, anoLetivo, bimestre)
                .orElseGet(() -> {
                    Comportamento novo = new Comportamento();
                    novo.setAluno(aluno);
                    novo.setProfessor(professor);
                    novo.setAnoLetivo(anoLetivo);
                    novo.setBimestre(bimestre);
                    novo.setDataRegistro(LocalDate.now());
                    novo.setStatus("Sem avaliação");
                    return novo;
                });
    }

    /**
     * Salvar avaliação de comportamento
     */
    public Comportamento salvar(Comportamento comportamento) {
        return comportamentoRepository.save(comportamento);
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
     * Buscar status geral do aluno (baseado na última avaliação)
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
     * Deletar avaliação
     */
    public void deletarAvaliacao(Long idComportamento) {
        comportamentoRepository.deleteById(idComportamento);
    }
}