package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.util.List;

/**
 * Esta entidade representa um Professor na escola.
 * Ela é uma especialização da entidade Usuario e contém informações
 * específicas do professor, como matrícula e especialidade.
 */
@Entity // Marca a classe como uma entidade JPA.
@Table(name = "professor") // Define o nome da tabela no banco de dados.
public class Professor {

    @Id // Marca o campo como a chave primária (PK).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a PK para ser autoincrementável.
    private Long id_professor;

    @Column(unique = true) // Mapeia para a coluna 'matricula' e garante que cada matrícula seja única.
    private String matricula;

    @Column
    private String especialidade;

    @Column // No seu DER o nome da coluna é 'ativo'.
    private Boolean ativo;

    // --- Relacionamentos ---

    /**
     * Relacionamento Um-para-Um com Usuario.
     * Cada Professor corresponde a exatamente um Usuario.
     * Esta é a "dona" da relação, pois é a tabela 'professor' que contém a chave estrangeira.
     */
    @OneToOne(cascade = CascadeType.ALL) // Define a relação 1-para-1. CascadeType.ALL significa que operações (como salvar) no Professor serão propagadas para o Usuario associado.
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true) // Especifica como a junção é feita: através da coluna 'id_usuario'. unique = true reforça que a relação é 1-para-1.
    private Usuario usuario;

    /**
     * Relacionamento Um-para-Muitos com TurmaDisciplina.
     * Um Professor pode lecionar em várias combinações de Turma e Disciplina.
     * 'mappedBy = "professor"' indica que a entidade TurmaDisciplina gerencia este relacionamento.
     */
    @OneToMany(mappedBy = "professor")
    private List<TurmaDisciplina> turmaDisciplinas;


    // --- Construtores, Getters e Setters ---

    public Professor() {
    }

    // Getters e Setters para todos os campos...

    public Long getId_professor() {
        return id_professor;
    }

    public void setId_professor(Long id_professor) {
        this.id_professor = id_professor;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<TurmaDisciplina> getTurmaDisciplinas() {
        return turmaDisciplinas;
    }

    public void setTurmaDisciplinas(List<TurmaDisciplina> turmaDisciplinas) {
        this.turmaDisciplinas = turmaDisciplinas;
    }
}