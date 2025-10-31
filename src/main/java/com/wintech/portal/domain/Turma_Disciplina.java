package com.wintech.portal.domain;

import jakarta.persistence.*;

/**
 * Esta é a entidade que mapeia a tabela de ligação "turma_disciplina".
 * Ela representa a relação N:N entre Turma e Disciplina, com o atributo extra Professor.
 */
@Entity // Marca esta classe como uma entidade JPA, que será mapeada para uma tabela.
@Table(name = "turma_disciplina") // Especifica o nome da tabela no banco de dados.
@IdClass(TurmaDisciplinaId.class) // Aponta para a classe que acabamos de criar, definindo-a como o molde da nossa chave primária composta.
public class TurmaDisciplina {

    @Id // Marca o atributo "turma" como parte da chave primária composta.
    @ManyToOne // Define um relacionamento "Muitos-para-Um": Muitas entradas em TurmaDisciplina podem se referir a Uma Turma.
    @JoinColumn(name = "id_turma") // Especifica que este relacionamento é feito através da coluna "id_turma" nesta tabela.
    private Turma turma;

    @Id // Marca o atributo "disciplina" como parte da chave primária composta.
    @ManyToOne // Define um relacionamento "Muitos-para-Um": Muitas entradas em TurmaDisciplina podem se referir a Uma Disciplina.
    @JoinColumn(name = "id_disciplina") // Especifica que este relacionamento é feito através da coluna "id_disciplina".
    private Disciplina disciplina;

    @Id // Marca o atributo "professor" como parte da chave primária composta.
    @ManyToOne // Define um relacionamento "Muitos-para-Um": Muitas entradas em TurmaDisciplina podem se referir a Um Professor.
    @JoinColumn(name = "id_professor") // Especifica que este relacionamento é feito através da coluna "id_professor".
    private Professor professor;

    // Construtor vazio, Getters e Setters são necessários para o JPA.
    public TurmaDisciplina() {
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