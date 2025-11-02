package com.wintech.portal.domain;

import jakarta.persistence.*; // Pacote para as anotações do JPA

@Entity // 1. Anotação mais importante: diz ao Spring que esta classe é uma entidade.
@Table(name = "Usuario") // 2. Especifica o nome da tabela no banco de dados.
public class Usuario {

    @Id // 3. Marca o campo abaixo como a chave primária (PK) da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. Configura a PK para ser autoincrementável.
    private Long id_usuario;

    @Column(nullable = false, length = 50) // 5. Mapeia para uma coluna, define que não pode ser nula e limita o tamanho.
    private String perfil;

    @Column(nullable = false, unique = true) // 6. Garante que o email não pode ser nulo e deve ser único na tabela.
    private String email;

    @Column(name = "senha_hash", nullable = false) // 7. Mapeia para a coluna "senha_hash" e a torna obrigatória.
    private String senhaHash;

    @Column(nullable = false)
    private String nome;

    private Boolean ativo;

    // Construtores, Getters e Setters serão adicionados aqui depois.
    // Por enquanto, o foco é na estrutura da entidade.
}