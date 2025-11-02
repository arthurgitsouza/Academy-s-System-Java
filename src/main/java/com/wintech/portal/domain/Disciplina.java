package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.util.List;

/**
 * Esta entidade representa uma Disciplina oferecida pela escola.
 * Contém informações como nome e carga horária.
 */
@Entity // Marca a classe como uma entidade JPA.
@Table(name = "disciplina") // Define o nome da tabela no banco de dados.
public class Disciplina {

    @Id // Marca o campo como a chave primária (PK).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a PK para ser autoincrementável.
    private Long id_disciplina;

    @Column(name = "nome_disciplina", nullable = false) // Mapeia para a coluna "nome_disciplina" e a torna obrigatória.
    private String nomeDisciplina;

    @Column(name = "carga_horaria") // Mapeia para a coluna "carga_horaria".
    private Integer cargaHoraria;

    @Column // No seu DER o nome da coluna é 'ativo'.
    private Boolean ativo;

    // --- Relacionamentos ---

    /**
     * Relacionamento Um-para-Muitos com TurmaDisciplina.
     * Uma Disciplina pode estar presente em várias entradas da tabela de ligação.
     * 'mappedBy = "disciplina"' indica que a entidade TurmaDisciplina é a "dona" da relação.
     */
    @OneToMany(mappedBy = "disciplina")
    private List<TurmaDisciplina> turmaDisciplinas;


    // --- Construtores, Getters e Setters ---

    public Disciplina() {
    }

    // Getters e Setters para todos os campos...

    public Long getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Long id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<TurmaDisciplina> getTurmaDisciplinas() {
        return turmaDisciplinas;
    }

    public void setTurmaDisciplinas(List<TurmaDisciplina> turmaDisciplinas) {
        this.turmaDisciplinas = turmaDisciplinas;
    }
}