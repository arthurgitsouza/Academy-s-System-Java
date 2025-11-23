package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;

// ========================================
// DTO: Card do Aluno (Grid/Lista)
// ========================================
public class AlunoCardDTO {
    private Long id;
    private String nome;
    private String foto;
    private Integer idade;
    private String statusComportamento; // "Excelente", "Bom", "Em Risco"
    private String statusCor; // "green", "blue", "red"
    private Integer totalDisciplinas;

    public AlunoCardDTO() {}

    public AlunoCardDTO(Aluno aluno) {
        this.id = aluno.getId_aluno();
        this.nome = aluno.getUsuario() != null ? aluno.getUsuario().getNome() : "";
        this.foto = aluno.getFoto();
        this.idade = calcularIdade(aluno.getDataNascimento());
        this.statusComportamento = calcularStatusComportamento(aluno);
        this.statusCor = mapearCor(this.statusComportamento);
        this.totalDisciplinas = 12; // TODO: calcular real
    }

    private Integer calcularIdade(java.time.LocalDate dataNascimento) {
        if (dataNascimento == null) return null;
        return java.time.Period.between(dataNascimento, java.time.LocalDate.now()).getYears();
    }

    private String calcularStatusComportamento(Aluno aluno) {
        // Mesma lógica do AlunoResponseDTO
        if (aluno.getComportamentos() == null || aluno.getComportamentos().isEmpty()) {
            return "Novo";
        }

        double media = aluno.getComportamentos().stream()
                .mapToDouble(c -> {
                    int soma = (c.getParticipacao() != null ? c.getParticipacao() : 0) +
                            (c.getResponsabilidade() != null ? c.getResponsabilidade() : 0) +
                            (c.getSociabilidade() != null ? c.getSociabilidade() : 0) +
                            (c.getAssiduidade() != null ? c.getAssiduidade() : 0);
                    return soma / 4.0;
                })
                .average()
                .orElse(0.0);

        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
    }

    private String mapearCor(String status) {
        switch (status) {
            case "Excelente": return "green";
            case "Bom": return "blue";
            case "Em Risco": return "red";
            default: return "gray";
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
    public String getStatusComportamento() { return statusComportamento; }
    public void setStatusComportamento(String statusComportamento) { this.statusComportamento = statusComportamento; }
    public String getStatusCor() { return statusCor; }
    public void setStatusCor(String statusCor) { this.statusCor = statusCor; }
    public Integer getTotalDisciplinas() { return totalDisciplinas; }
    public void setTotalDisciplinas(Integer totalDisciplinas) { this.totalDisciplinas = totalDisciplinas; }
}