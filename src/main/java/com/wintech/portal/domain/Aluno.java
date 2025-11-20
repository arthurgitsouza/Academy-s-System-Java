package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Esta entidade representa um Aluno na escola.
 * É uma especialização da entidade Usuario e contém informações
 * específicas do aluno, como data de nascimento e a turma a que pertence.
 */
@Entity // Marca a classe como uma entidade JPA.
@Table(name = "aluno") // Define o nome da tabela no banco de dados.
public class Aluno {

    @Id // Marca o campo como a chave primária (PK).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a PK para ser autoincrementável.
    private String nome_aluno;

    private String email;
    
    private Long id_aluno;

    @Column // Mapeia para a coluna 'foto', que pode armazenar a URL da imagem.
    private String foto;

    @Column(name = "data_nascimento") // Mapeia para a coluna 'data_nascimento'.
    private LocalDate dataNascimento; // Usar LocalDate é a prática moderna para datas sem hora.

    @Column
    private String telefone;

    @Column(name = "nome_responsavel") // Mapeia para a coluna 'nome_responsavel'.
    private String nomeResponsavel;

    @Column // No seu DER o nome da coluna é 'ativo'.
    private Boolean ativo;

    // --- Relacionamentos ---

    /**
     * Relacionamento Um-para-Um com Usuario.
     * Cada Aluno corresponde a exatamente um Usuario.
     */
    @OneToOne(cascade = CascadeType.ALL) // CascadeType.ALL propaga operações (salvar, deletar) para o Usuario associado.
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true) // Define a coluna 'id_usuario' como a chave estrangeira para a tabela Usuario.
    private Usuario usuario;

    /**
     * Relacionamento Muitos-para-Um com Turma.
     * Muitos Alunos podem pertencer a Uma Turma.
     * Esta entidade é a "dona" da relação, pois contém a chave estrangeira.
     */
    @ManyToOne
    @JoinColumn(name = "id_turma") // Especifica que a coluna 'id_turma' nesta tabela é a chave estrangeira para a tabela Turma.
    private Turma turma;

    /**
     * Relacionamento Um-para-Muitos com Comportamento.
     * Um Aluno pode ter vários registros de comportamento ao longo do tempo.
     * 'mappedBy = "aluno"' indica que a entidade Comportamento é quem gerencia o relacionamento.
     */
    @OneToMany(mappedBy = "aluno")
    private List<Comportamento> comportamentos;


    // --- Construtores, Getters e Setters ---

    public Aluno() {
    }

    // Getters e Setters para todos os campos...

    public String getNome_aluno() {
        return nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(Long id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
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

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Comportamento> getComportamentos() {
        return comportamentos;
    }

    public void setComportamentos(List<Comportamento> comportamentos) {
        this.comportamentos = comportamentos;
    }
}