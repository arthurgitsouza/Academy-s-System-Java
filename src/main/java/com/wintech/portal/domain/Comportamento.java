package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comportamento")
public class Comportamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comportamento;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo;

    @Column(nullable = false)
    private Integer bimestre;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column
    private Integer participacao;

    @Column
    private Integer responsabilidade;

    @Column
    private Integer sociabilidade;

    @Column
    private Integer assiduidade;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column(length = 50)
    private String status;

    // Construtores
    public Comportamento() {}

    // Getters e Setters
    public Long getId_comportamento() { return id_comportamento; }
    public void setId_comportamento(Long id_comportamento) { this.id_comportamento = id_comportamento; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Integer getAnoLetivo() { return anoLetivo; }
    public void setAnoLetivo(Integer anoLetivo) { this.anoLetivo = anoLetivo; }

    public Integer getBimestre() { return bimestre; }
    public void setBimestre(Integer bimestre) { this.bimestre = bimestre; }

    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }

    public Integer getParticipacao() { return participacao; }
    public void setParticipacao(Integer participacao) { this.participacao = participacao; }

    public Integer getResponsabilidade() { return responsabilidade; }
    public void setResponsabilidade(Integer responsabilidade) { this.responsabilidade = responsabilidade; }

    public Integer getSociabilidade() { return sociabilidade; }
    public void setSociabilidade(Integer sociabilidade) { this.sociabilidade = sociabilidade; }

    public Integer getAssiduidade() { return assiduidade; }
    public void setAssiduidade(Integer assiduidade) { this.assiduidade = assiduidade; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}