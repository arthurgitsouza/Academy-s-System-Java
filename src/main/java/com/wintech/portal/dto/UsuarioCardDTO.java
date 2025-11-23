package com.wintech.portal.dto;

public class UsuarioCardDTO {
    private Long id;
    private String nome;
    private String foto;
    private String tipo; // "ALUNO", "PROFESSOR"
    private String status;
    private Integer totalTurmas; // Para professor
    private Integer idade; // Para aluno

    public UsuarioCardDTO() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getTotalTurmas() { return totalTurmas; }
    public void setTotalTurmas(Integer totalTurmas) { this.totalTurmas = totalTurmas; }
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
}