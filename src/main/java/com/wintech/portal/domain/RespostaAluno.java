package com.wintech.portal.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "resposta_aluno")
public class RespostaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_resposta_aluno;

    // Relacionamento: Muitas respostas pertencem a Um Simulado.
    @ManyToOne
    @JoinColumn(name = "id_simulado", nullable = false)
    private Simulado simulado;

    // Relacionamento: Muitas respostas são dadas por Um Aluno.
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    // Relacionamento: Muitas respostas se referem a Uma Questão.
    @ManyToOne
    @JoinColumn(name = "id_questao", nullable = false)
    private Questao questao;

    // --- MUDANÇA PRINCIPAL AQUI ---
    // Em vez de uma referência à entidade Alternativa, agora simplesmente
    // armazenamos qual opção (A, B, C ou D) o aluno escolheu.
    // Usar o Enum OpcaoCorreta garante que apenas valores válidos sejam salvos.
    @Enumerated(EnumType.STRING)
    @Column(name = "alternativa_escolhida", nullable = false)
    private OpcaoCorreta alternativaEscolhida;


    // Construtores, Getters e Setters... (Gere na sua IDE)

    public RespostaAluno() {
    }

    public Long getId_resposta_aluno() {
        return id_resposta_aluno;
    }

    public void setId_resposta_aluno(Long id_resposta_aluno) {
        this.id_resposta_aluno = id_resposta_aluno;
    }

    public Simulado getSimulado() {
        return simulado;
    }

    public void setSimulado(Simulado simulado) {
        this.simulado = simulado;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public OpcaoCorreta getAlternativaEscolhida() {
        return alternativaEscolhida;
    }

    public void setAlternativaEscolhida(OpcaoCorreta alternativaEscolhida) {
        this.alternativaEscolhida = alternativaEscolhida;
    }
}