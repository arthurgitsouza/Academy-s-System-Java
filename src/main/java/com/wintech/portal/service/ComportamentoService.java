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
     * (Ordenado pela data mais recente)
     */
    public List<Comportamento> buscarHistoricoDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Ajuste: Como não temos campo "anoLetivo" no banco, ordenamos por Data de Registro
        return comportamentoRepository.findByAlunoOrderByDataRegistroDesc(aluno);
    }

    /**
     * Buscar comportamento de um aluno em um bimestre específico
     * ATENÇÃO: Como sua tabela usa "dataRegistro", essa busca exata por "Bimestre String"
     * só funcionará se você tiver criado esse campo na Entidade.
     * Se não tiver, teremos que filtrar por data no futuro.
     */
    public Comportamento buscarPorBimestre(Long idAluno, Integer anoLetivo, String bimestre) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Você precisará garantir que este método exista no Repository aceitando String
        return comportamentoRepository.findByAlunoAndAnoLetivoAndBimestre(aluno, anoLetivo, bimestre)
                .orElseThrow(() -> new RuntimeException("Comportamento não encontrado para este bimestre"));
    }

    /**
     * Buscar status geral (Baseado na última avaliação registrada)
     */
    public String buscarStatusGeralDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Pega o histórico ordenado por data (mais recente primeiro)
        List<Comportamento> historico = comportamentoRepository.findByAlunoOrderByDataRegistroDesc(aluno);

        if (historico.isEmpty()) {
            return "Sem avaliação";
        }

        // Pega o mais recente
        Comportamento ultimo = historico.get(0);

        // CALCULA O STATUS (Já que não salvamos o texto no banco)
        double media = (
                (ultimo.getParticipacao() != null ? ultimo.getParticipacao() : 0) +
                        (ultimo.getResponsabilidade() != null ? ultimo.getResponsabilidade() : 0) +
                        (ultimo.getSociabilidade() != null ? ultimo.getSociabilidade() : 0) +
                        (ultimo.getAssiduidade() != null ? ultimo.getAssiduidade() : 0)
        ) / 4.0;

        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
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
    public Comportamento atualizarAvaliacao(Long idComportamento, Comportamento dadosAtualizados) {
        Comportamento comportamento = comportamentoRepository.findById(idComportamento)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        // Atualiza os campos reais que existem no banco
        comportamento.setParticipacao(dadosAtualizados.getParticipacao());
        comportamento.setResponsabilidade(dadosAtualizados.getResponsabilidade());
        comportamento.setSociabilidade(dadosAtualizados.getSociabilidade());
        comportamento.setAssiduidade(dadosAtualizados.getAssiduidade());
        comportamento.setObservacao(dadosAtualizados.getObservacao());

        return comportamentoRepository.save(comportamento);
    }

    /**
     * Deletar avaliação
     */
    public void deletarAvaliacao(Long idComportamento) {
        comportamentoRepository.deleteById(idComportamento);
    }
}