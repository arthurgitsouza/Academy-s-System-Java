package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Esta entidade armazena os registros de avaliação de comportamento dos alunos.
 * É o coração da funcionalidade "Carômetro".
 */
@Entity // Marca a classe como uma entidade JPA.
@Table(name = "comportamento") // Define o nome da tabela no banco de dados.
public class Comportamento {

    @Id // Marca o campo como a chave primária (PK).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a PK para ser autoincrementável.
    private Double nota;
    
    private Long id_comportamento;

    @Column(name = "data_registro", nullable = false) // Mapeia para a coluna 'data_registro' e a torna obrigatória.
    private LocalDate dataRegistro;

    @Column // Mapeia para a coluna 'participacao'. A nota pode ser de 1 a 5, por exemplo.
    private Integer participacao;

    @Column
    private Integer responsabilidade;

    @Column
    private Integer sociabilidade;

    @Column
    private Integer assiduidade;

    @Column
    private String observacao;

    // --- Relacionamentos ---

    /**
     * Relacionamento Muitos-para-Um com Aluno.
     * Muitos registros de Comportamento podem pertencer a Um Aluno.
     */
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false) // A coluna 'id_aluno' é a chave estrangeira e não pode ser nula.
    private Aluno aluno;

    /**
     * Relacionamento Muitos-para-Um com Disciplina.
     * Muitos registros de Comportamento podem estar associados a Uma Disciplina.
     */
    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false) // A coluna 'id_disciplina' é a chave estrangeira e não pode ser nula.
    private Disciplina disciplina;

    /**
     * Relacionamento Muitos-para-Um com Professor.
     * Muitos registros de Comportamento podem ser feitos por Um Professor.
     */
    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false) // A coluna 'id_professor' é a chave estrangeira e não pode ser nula.
    private Professor professor;


    // --- Construtores, Getters e Setters ---

    public Comportamento() {
    }

    // Getters e Setters para todos os campos...

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
    
    public Long getId_comportamento() {
        return id_comportamento;
    }

    public void setId_comportamento(Long id_comportamento) {
        this.id_comportamento = id_comportamento;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Integer getParticipacao() {
        return participacao;
    }

    public void setParticipacao(Integer participacao) {
        this.participacao = participacao;
    }

    public Integer getResponsabilidade() {
        return responsabilidade;
    }

    public void setResponsabilidade(Integer responsabilidade) {
        this.responsabilidade = responsabilidade;
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}