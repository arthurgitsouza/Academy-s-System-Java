package com.wintech.portal.dto;

import com.wintech.portal.domain.Comportamento;
import java.time.LocalDate;

public class ComportamentoResponseDTO {
    private Long id;
    private Long alunoId;
    private String alunoNome;
    private Integer anoLetivo;
    private Integer bimestre;
    private Integer responsabilidade;
    private Integer participacao;
    private Integer sociabilidade;
    private Integer assiduidade;
    private String observacao;
    private String status;
    private LocalDate dataRegistro;

    public ComportamentoResponseDTO() {}

    public ComportamentoResponseDTO(Comportamento c) {
        this.id = c.getId_comportamento();
        this.alunoId = c.getAluno().getId_aluno();
        this.alunoNome = c.getAluno().getUsuario() != null ?
                c.getAluno().getUsuario().getNome() : "";
        this.anoLetivo = c.getAnoLetivo();
        this.bimestre = c.getBimestre();
        this.responsabilidade = c.getResponsabilidade();
        this.participacao = c.getParticipacao();
        this.sociabilidade = c.getSociabilidade();
        this.assiduidade = c.getAssiduidade();
        this.observacao = c.getObservacao();
        this.status = c.getStatus();
        this.dataRegistro = c.getDataRegistro();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAlunoId() { return alunoId; }
    public void setAlunoId(Long alunoId) { this.alunoId = alunoId; }
    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }
    public Integer getAnoLetivo() { return anoLetivo; }
    public void setAnoLetivo(Integer anoLetivo) { this.anoLetivo = anoLetivo; }
    public Integer getBimestre() { return bimestre; }
    public void setBimestre(Integer bimestre) { this.bimestre = bimestre; }
    public Integer getResponsabilidade() { return responsabilidade; }
    public void setResponsabilidade(Integer responsabilidade) { this.responsabilidade = responsabilidade; }
    public Integer getParticipacao() { return participacao; }
    public void setParticipacao(Integer participacao) { this.participacao = participacao; }
    public Integer getSociabilidade() { return sociabilidade; }
    public void setSociabilidade(Integer sociabilidade) { this.sociabilidade = sociabilidade; }
    public Integer getAssiduidade() { return assiduidade; }
    public void setAssiduidade(Integer assiduidade) { this.assiduidade = assiduidade; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
}