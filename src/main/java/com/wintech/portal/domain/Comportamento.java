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

    public Comportamento(Integer bimestre, Integer anoLetivo, String status, Aluno aluno, Professor professor) {
        this.bimestre = bimestre;
        this.anoLetivo = anoLetivo;
        this.status = status;
        this.dataRegistro = LocalDate.now();
        this.aluno = aluno;
        this.professor = professor;
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