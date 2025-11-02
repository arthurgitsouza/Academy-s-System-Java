package com.wintech.portal.domain;

import jakarta.persistence.*;

/**
 * Esta é a entidade que mapeia a tabela de ligação "turma_disciplina",
 * agora usando uma chave primária simples (autoincrementável).
 */
@Entity
@Table(name = "turma_disciplina")
public class TurmaDisciplina {

    @Id // A chave primária agora é este único campo.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura para ser autoincrementável.
    private Long id_turma_disciplina;

    // Os relacionamentos continuam aqui, mas agora são apenas colunas normais.
    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    // Construtor, Getters e Setters
    public TurmaDisciplina() {
    }

    // Getters e Setters para todos os campos...

    public Long getId_turma_disciplina() {
        return id_turma_disciplina;
    }

    public void setId_turma_disciplina(Long id_turma_disciplina) {
        this.id_turma_disciplina = id_turma_disciplina;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
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