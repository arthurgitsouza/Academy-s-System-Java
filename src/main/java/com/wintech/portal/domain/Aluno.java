package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_aluno;

    // --- REMOVIDO: nome_aluno (Já está em Usuario) ---
    // --- REMOVIDO: email (Já está em Usuario) ---

    @Column
    private String foto;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column
    private String telefone;

    @Column(name = "nome_responsavel")
    private String nomeResponsavel;

    @Column
    private Boolean ativo;

    // --- Relacionamentos ---

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true)
    private Usuario usuario; // <--- O Nome e Email vivem AQUI DENTRO

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @OneToMany(mappedBy = "aluno")
    private List<Comportamento> comportamentos;


    // --- Construtores, Getters e Setters ---

    public Aluno() {
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