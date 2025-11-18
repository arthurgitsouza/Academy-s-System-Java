package com.wintech.portal.dto;

public class LoginResponseDTO {

    private String token;
    private String tipo; // Sempre será "Bearer"
    private Long idUsuario;
    private String nome;
    private String email;
    private String perfil; // "ADMIN", "PROFESSOR", "ALUNO"

    // Construtor completo
    public LoginResponseDTO(String token, Long idUsuario, String nome, String email, String perfil) {
        this.token = token;
        this.tipo = "Bearer";
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}