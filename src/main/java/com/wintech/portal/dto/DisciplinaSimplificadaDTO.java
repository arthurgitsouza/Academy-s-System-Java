package com.wintech.portal.dto;

public class DisciplinaSimplificadaDTO {
    private Long id;
    private String nome;
    private String icone; // Para o front-end exibir ícone

    public DisciplinaSimplificadaDTO() {}

    public DisciplinaSimplificadaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.icone = "📚"; // Padrão
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }
}
