package com.wintech.portal.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "simulado")
public class Simulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_simulado;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    @ManyToMany
    @JoinTable(
            name = "simulado_questao",
            joinColumns = @JoinColumn(name = "id_simulado"),
            inverseJoinColumns = @JoinColumn(name = "id_questao")
    )
    private List<Questao> questoes;

    // Construtores
    public Simulado() {
    }

    // Getters e Setters
    public Long getId_simulado() {
        return id_simulado;
    }

    public void setId_simulado(Long id_simulado) {
        this.id_simulado = id_simulado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }
}