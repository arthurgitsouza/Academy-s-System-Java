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
     * ✅ MÉTODO PRINCIPAL: Salvar nova avaliação
     * O Controller chama este método (salvarAvaliacao).
     */
    public Comportamento salvarAvaliacao(Comportamento novaAvaliacao) {
        // Define a data atual automaticamente antes de salvar
        novaAvaliacao.setDataRegistro(LocalDate.now());
        return comportamentoRepository.save(novaAvaliacao);
    }

    /**
     * ✅ MÉTODO PRINCIPAL: Buscar histórico completo do aluno
     * (Usado pelo Portal do Aluno/Admin)
     */
    public List<Comportamento> buscarHistoricoDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // CORRETO: Usa o método que existe no Repository e que ordena por Data
        return comportamentoRepository.findByAlunoOrderByDataRegistroDesc(aluno);
    }

    /**
     * ✅ MÉTODO PRINCIPAL: Buscar status geral (Texto para o card do aluno)
     * (Calculado na hora, já que 'status' não está no banco)
     */
    public String buscarStatusGeralDoAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Pega o histórico ordenado por data (mais recente primeiro)
        List<Comportamento> historico = comportamentoRepository.findByAlunoOrderByDataRegistroDesc(aluno);

        if (historico.isEmpty()) {
            return "Sem avaliação";
        }

        // Pega o registro mais recente
        Comportamento ultimo = historico.get(0);

        // LÓGICA DE CÁLCULO: Média dos 4 critérios
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
     * ✅ ATUALIZAR: Atualiza os campos de notas e observação
     */
    public Comportamento atualizarAvaliacao(Long idComportamento, Comportamento dadosAtualizados) {
        Comportamento comportamento = comportamentoRepository.findById(idComportamento)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        // Atualiza apenas os campos que vieram no Body
        comportamento.setParticipacao(dadosAtualizados.getParticipacao());
        comportamento.setResponsabilidade(dadosAtualizados.getResponsabilidade());
        comportamento.setSociabilidade(dadosAtualizados.getSociabilidade());
        comportamento.setAssiduidade(dadosAtualizados.getAssiduidade());
        comportamento.setObservacao(dadosAtualizados.getObservacao());

        return comportamentoRepository.save(comportamento);
    }

    /**
     * ✅ DELETAR: Deleta a avaliação
     */
    public void deletarAvaliacao(Long idComportamento) {
        comportamentoRepository.deleteById(idComportamento);
    }

    // --------------------------------------------------------
    // MÉTODOS REMOVIDOS POIS USAM CAMPOS INEXISTENTES (anoLetivo, bimestre)
    // --------------------------------------------------------
    /*
    public Comportamento buscarOuCriarAvaliacao(...) { ... }
    public Comportamento buscarPorBimestre(...) { ... }
    */
    public Comportamento salvar(Comportamento comportamento) {
        return salvarAvaliacao(comportamento);
    }
}