package com.wintech.portal.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "questao")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_questao;

    @Column(nullable = false)
    private String enunciado;

    // As alternativas agora são colunas de texto simples nesta tabela.
    @Column(name = "alternativa_a", nullable = false)
    private String alternativaA;

    @Column(name = "alternativa_b", nullable = false)
    private String alternativaB;

    @Column(name = "alternativa_c", nullable = false)
    private String alternativaC;

    @Column(name = "alternativa_d", nullable = false)
    private String alternativaD;

    // Esta coluna vai guardar qual das opções (A, B, C ou D) é a correta.
    // Usar um Enum é a forma mais segura e limpa de fazer isso.
    @Enumerated(EnumType.STRING)
    @Column(name = "resposta_correta", nullable = false)
    private OpcaoCorreta respostaCorreta;

    // Relacionamento: Muitas questões podem pertencer a Uma Disciplina.
    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    // Relacionamento: Muitas questões podem ser criadas por Um Professor.
    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    // Construtores, Getters e Setters... (Gere na sua IDE)
}

/**
 * Referência:
 * CREATE TABLE questao (
 *     id_questao SERIAL PRIMARY KEY,
 *     enunciado TEXT NOT NULL,
 *     alternativa_a TEXT NOT NULL,
 *     alternativa_b TEXT NOT NULL,
 *     alternativa_c TEXT NOT NULL,
 *     alternativa_d TEXT NOT NULL,
 *     resposta_correta VARCHAR(1) NOT NULL, -- Armazena 'A', 'B', 'C' ou 'D'
 *     id_disciplina INT NOT NULL,
 *     id_professor INT NOT NULL,
 *     CONSTRAINT fk_disciplina FOREIGN KEY(id_disciplina) REFERENCES disciplina(id_disciplina),
 *     CONSTRAINT fk_professor FOREIGN KEY(id_professor) REFERENCES professor(id_professor)
 * );
 */