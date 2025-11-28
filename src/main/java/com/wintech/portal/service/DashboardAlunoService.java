package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import com.wintech.portal.dto.*;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.repository.ComportamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

        // O DTO já faz a conversão e cálculos necessários no construtor
        return new PerfilAlunoDTO(aluno);
    }

    /**
     * Buscar histórico de comportamento agrupado por bimestre
     * ADAPTADO: Calcula o bimestre baseado na data e o status baseado na média.
     */
    public List<HistoricoComportamentoDTO> buscarHistoricoPorBimestre(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // 1. Buscar todos os registros (o repositório já ordena por data desc)
        List<Comportamento> comportamentos = comportamentoRepository
                .findByAlunoOrderByDataRegistroDesc(aluno); // <--- MÉTODO CORRETO DO REPO

        if (comportamentos.isEmpty()) {
            return criarHistoricoVazio();
        }

        // 2. Identificar o ano mais recente para filtrar (ex: 2025)
        int anoAtual = comportamentos.get(0).getDataRegistro().getYear();

        // 3. Mapear resultados por bimestre (para preencher buracos se faltar algum)
        Map<Integer, String> statusPorBimestre = new HashMap<>();

        for (Comportamento c : comportamentos) {
            // Só considera registros do ano mais recente
            if (c.getDataRegistro().getYear() == anoAtual) {
                int bimestreCalculado = calcularBimestrePelaData(c.getDataRegistro());

                // Se ainda não temos status para esse bimestre, calculamos e salvamos
                // (Como a lista está ordenada por data, o primeiro que aparecer é o mais recente daquele bimestre)
                statusPorBimestre.putIfAbsent(bimestreCalculado, calcularStatusTexto(c));
            }
        }

        // 4. Montar a lista final (sempre retorna 4 bimestres, preenchidos ou vazios)
        List<HistoricoComportamentoDTO> historico = new ArrayList<>();

        for (int bim = 1; bim <= 4; bim++) {
            String status = statusPorBimestre.getOrDefault(bim, "Sem avaliação");

            historico.add(new HistoricoComportamentoDTO(
                    bim + "º Bimestre",
                    obterPeriodoBimestre(bim),
                    status
            ));
        }

        return historico;
    }

    // --- MÉTODOS AUXILIARES (Lógica de Negócio) ---

    private int calcularBimestrePelaData(LocalDate data) {
        int mes = data.getMonthValue();
        if (mes <= 3) return 1;
        if (mes <= 6) return 2;
        if (mes <= 9) return 3;
        return 4;
    }

    private String calcularStatusTexto(Comportamento c) {
        // Lógica idêntica à do DTO: Média aritmética dos 4 critérios
        double media = (
                (c.getAssiduidade() != null ? c.getAssiduidade() : 0) +
                        (c.getParticipacao() != null ? c.getParticipacao() : 0) +
                        (c.getResponsabilidade() != null ? c.getResponsabilidade() : 0) +
                        (c.getSociabilidade() != null ? c.getSociabilidade() : 0)
        ) / 4.0;

        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
    }

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

    private String obterPeriodoBimestre(int bimestre) {
        switch (bimestre) {
            case 1: return "Jan - Mar";
            case 2: return "Abr - Jun";
            case 3: return "Jul - Set";
            case 4: return "Out - Dez";
            default: return "";
        }
    }
}