package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comportamento")
public class Comportamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comportamento;

    @Column(name = "bimestre", nullable = false)
    private Integer bimestre; // 1, 2, 3 ou 4

    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo; // Ex: 2024

    // ===== CAMPOS QUE O FRONT ENVIA =====
    @Column(name = "responsabilidade")
    private Integer responsabilidade; // 1 a 5

    @Column(name = "participacao")
    private Integer participacao; // 1 a 5

    @Column(name = "sociabilidade")
    private Integer sociabilidade; // 1 a 5

    @Column(name = "assiduidade")
    private Integer assiduidade; // 1 a 5
    // =====================================

    @Column(name = "status", nullable = false)
    private String status; // "Excelente", "Bom", "Mediano", "Ruim", "Péssimo"

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    // --- Relacionamentos ---
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    // --- Construtores ---
    public Comportamento() {
    }

    // --- Getters e Setters ---
    public Long getId_comportamento() {
        return id_comportamento;
    }

    public void setId_comportamento(Long id_comportamento) {
        this.id_comportamento = id_comportamento;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public Integer getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Integer anoLetivo) {
        this.anoLetivo = anoLetivo;
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

    public Integer getSociabilidade() {
        return sociabilidade;
    }

    public void setSociabilidade(Integer sociabilidade) {
        this.sociabilidade = sociabilidade;
    }

    public Integer getAssiduidade() {
        return assiduidade;
    }

    public void setAssiduidade(Integer assiduidade) {
        this.assiduidade = assiduidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}