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

    // Relacionamento: Muitos Simulados podem ser aplicados a Uma Turma.
    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    // Relacionamento: Um Simulado tem Muitas Questões, e uma Questão pode estar em Muitos Simulados (N:N).
    // O JPA criará uma tabela de ligação (ex: simulado_questao) automaticamente para nós.
    @ManyToMany
    @JoinTable(
            name = "simulado_questao",
            joinColumns = @JoinColumn(name = "id_simulado"),
            inverseJoinColumns = @JoinColumn(name = "id_questao")
    )
    private List<Questao> questoes;

    // Construtores, Getters e Setters...
}