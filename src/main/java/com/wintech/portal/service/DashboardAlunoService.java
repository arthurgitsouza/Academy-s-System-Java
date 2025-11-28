package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.dto.*;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ComportamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardAlunoService {

    private final AlunoRepository alunoRepository;
    private final ComportamentoRepository comportamentoRepository;

    @Autowired
    public DashboardAlunoService(AlunoRepository alunoRepository,
                                 ComportamentoRepository comportamentoRepository) {
        this.alunoRepository = alunoRepository;
        this.comportamentoRepository = comportamentoRepository;
    }

    /**
     * Buscar perfil completo do aluno
     */
    public PerfilAlunoDTO buscarPerfilCompleto(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // ✅ O DTO já preenche tudo no construtor, inclusive as disciplinas
        return new PerfilAlunoDTO(aluno);
    }

    /**
     * Buscar histórico de comportamento agrupado por bimestre
     * NOVO: Usa a nova estrutura de Comportamento (bimestre, anoLetivo, status)
     */
    public List<HistoricoComportamentoDTO> buscarHistoricoPorBimestre(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // ✅ CORRIGIDO: Usar o novo método do repository
        List<Comportamento> comportamentos = comportamentoRepository
                .findByAlunoOrderByAnoLetivoDescBimestreDesc(aluno);

        // Se não há avaliações, retornar histórico vazio
        if (comportamentos.isEmpty()) {
            return criarHistoricoVazio();
        }

        // Agrupar por bimestre (pega o ano letivo mais recente)
        final Integer anoLetivoAtual = comportamentos.get(0).getAnoLetivo();

        List<HistoricoComportamentoDTO> historico = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            final int bim = i; // ✅ Converter para final para usar na lambda

            // Buscar avaliação deste bimestre no ano letivo atual
            Comportamento compBimestre = comportamentos.stream()
                    .filter(c -> c.getBimestre() == bim && c.getAnoLetivo() == anoLetivoAtual)
                    .findFirst()
                    .orElse(null);

            String status = compBimestre != null ? compBimestre.getStatus() : "Sem avaliação";
            String periodo = obterPeriodoBimestre(bim);

            historico.add(new HistoricoComportamentoDTO(
                    bim + "º Bimestre",
                    periodo,
                    status
            ));
        }

        return historico;
    }

    /**
     * Criar histórico vazio (quando aluno não tem avaliações)
     */
    private List<HistoricoComportamentoDTO> criarHistoricoVazio() {
        List<HistoricoComportamentoDTO> historico = new ArrayList<>();

        for (int bim = 1; bim <= 4; bim++) {
            historico.add(new HistoricoComportamentoDTO(
                    bim + "º Bimestre",
                    obterPeriodoBimestre(bim),
                    "Sem avaliação"
            ));
        }

        return historico;
    }

    /**
     * Obter período do bimestre
     */
    private String obterPeriodoBimestre(int bimestre) {
        switch (bimestre) {
            case 1:
                return "Jan - Mar";
            case 2:
                return "Abr - Jun";
            case 3:
                return "Jul - Set";
            case 4:
                return "Out - Dez";
            default:
                return "";
        }
    }
}