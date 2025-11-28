package com.wintech.portal.dto;

public class AvaliacaoComportamentoDTO {
    private Long alunoId;
    private Integer bimestre; // 1, 2, 3 ou 4
    private Integer responsabilidade; // 1 a 5
    private Integer participacao; // 1 a 5
    private Integer comportamento; // 1 a 5 (será mapeado para sociabilidade)
    private String observacao;

    public AvaliacaoComportamentoDTO() {}

    // Getters e Setters
    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public Integer getResponsabilidade() {
        return responsabilidade;
    }

    public void setResponsabilidade(Integer responsabilidade) {
        this.responsabilidade = responsabilidade;
    }

    public Integer getParticipacao() {
        return participacao;
    }

    public void setParticipacao(Integer participacao) {
        this.participacao = participacao;
    }

    public Integer getComportamento() {
        return comportamento;
    }

    public void setComportamento(Integer comportamento) {
        this.comportamento = comportamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}