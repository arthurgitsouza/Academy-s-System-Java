package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.util.List;

/**
 * Esta entidade representa uma Turma na escola.
 * Ela armazena informações como o nome, série e turno, e se relaciona
 * com os Alunos que pertencem a ela e com as Disciplinas que são lecionadas nela.
 */
@Entity // Marca a classe como uma entidade que será mapeada para uma tabela.
@Table(name = "turma") // Define o nome da tabela no banco de dados como "turma".
public class Turma {

    @Id // Marca o campo abaixo como a chave primária (PK).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a PK para ser autoincrementável pelo banco de dados.
    private Long id_turma;

    @Column(name = "nome_turma", nullable = false) // Mapeia para a coluna "nome_turma" e a torna obrigatória.
    private String nomeTurma;

    @Column(name = "ano_letivo") // Mapeia para a coluna "ano_letivo".
    private Integer anoLetivo;

    @Column
    private String serie;

    @Column
    private String turno;

    @Column(name = "status") // No seu DER o nome da coluna é 'status', mas 'ativo' pode ser um nome melhor para o atributo.
    private Boolean ativo;

    // --- Relacionamentos ---

    /**
     * Relacionamento Um-para-Muitos com Aluno.
     * Uma Turma pode ter muitos Alunos.
     * "mappedBy = 'turma'" indica que a entidade Aluno é a "dona" deste relacionamento
     * (é nela que está a coluna @ManyToOne com a chave estrangeira).
     */
    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos;

    /**
     * Relacionamento Um-para-Muitos com TurmaDisciplina.
     * Uma Turma aparece em várias entradas da tabela de ligação TurmaDisciplina.
     * Isso nos permite, a partir de uma Turma, descobrir quais disciplinas e professores estão associados a ela.
     */
    @OneToMany(mappedBy = "turma")
    private List<TurmaDisciplina> turmaDisciplinas;


    // --- Construtores, Getters e Setters ---

    public Turma() {
    }

    // Getters e Setters para todos os campos... (gerados pela IDE)

    public Long getId_turma() {
        return id_turma;
    }

    public void setId_turma(Long id_turma) {
        this.id_turma = id_turma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public Integer getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Integer anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<TurmaDisciplina> getTurmaDisciplinas() {
        return turmaDisciplinas;
    }

    public void setTurmaDisciplinas(List<TurmaDisciplina> turmaDisciplinas) {
        this.turmaDisciplinas = turmaDisciplinas;
    }
}