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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        PerfilAlunoDTO perfil = new PerfilAlunoDTO(aluno);

        // Buscar disciplinas do aluno (TODO: implementar consulta real)
        List<DisciplinaSimplificadaDTO> disciplinas = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            disciplinas.add(new DisciplinaSimplificadaDTO((long) i, "Matemática"));
        }
        perfil.setDisciplinas(disciplinas);

        return perfil;
    }

    /**
     * Buscar histórico de comportamento agrupado por bimestre
     */
    public List<HistoricoComportamentoDTO> buscarHistoricoPorBimestre(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<Comportamento> comportamentos = comportamentoRepository
                .findByAlunoOrderByDataRegistroDesc(aluno);

        // Agrupar por bimestre
        Map<Integer, List<Comportamento>> porBimestre = comportamentos.stream()
                .collect(Collectors.groupingBy(c -> calcularBimestre(c.getDataRegistro())));

        List<HistoricoComportamentoDTO> historico = new ArrayList<>();

        for (int bim = 1; bim <= 4; bim++) {
            List<Comportamento> compsBimestre = porBimestre.getOrDefault(bim, new ArrayList<>());

            String status = calcularStatusBimestre(compsBimestre);
            String periodo = obterPeriodoBimestre(bim);

            historico.add(new HistoricoComportamentoDTO(
                    bim + " Bimestre",
                    periodo,
                    status
            ));
        }

        return historico;
    }

    private int calcularBimestre(LocalDate data) {
        Month mes = data.getMonth();
        if (mes.getValue() <= 3) return 1;
        if (mes.getValue() <= 6) return 2;
        if (mes.getValue() <= 9) return 3;
        return 4;
    }

    private String calcularStatusBimestre(List<Comportamento> comportamentos) {
        if (comportamentos.isEmpty()) return "Novo";

        double media = comportamentos.stream()
                .mapToDouble(c -> {
                    int soma = (c.getParticipacao() != null ? c.getParticipacao() : 0) +
                            (c.getResponsabilidade() != null ? c.getResponsabilidade() : 0) +
                            (c.getSociabilidade() != null ? c.getSociabilidade() : 0) +
                            (c.getAssiduidade() != null ? c.getAssiduidade() : 0);
                    return soma / 4.0;
                })
                .average()
                .orElse(0.0);

        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
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