package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comportamento")
public class Comportamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comportamento;

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

    // --- Relacionamentos ---

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    // --- A CORREÇÃO PARA O CONTROLLER: Adicionado o campo Disciplina ---
    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    public Comportamento() {
    }

    // --- Getters e Setters ---

    public Long getId_comportamento() { return id_comportamento; }
    public void setId_comportamento(Long id_comportamento) { this.id_comportamento = id_comportamento; }

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

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    // --- Getter e Setter da Disciplina ---
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}