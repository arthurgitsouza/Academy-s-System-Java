package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;

import java.util.List;

public class AlunoResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String nomeTurma;              // Regra de Ouro 2: não inserir objeto Turma inteiro
    private String statusComportamental;   // "Excelente", "Bom", "Em Risco" ou "Sem Avaliações"
    private Double mediaComportamento;     // opcional: expor a média numérica

    public AlunoResponseDTO() {}

    // Construtor que recebe a entidade e já calcula tudo
    public AlunoResponseDTO(Aluno aluno) {
        this.id = aluno.getId_aluno();
        this.nome = aluno.getNome_aluno();
        this.email = aluno.getEmail();
        this.nomeTurma = aluno.getTurma() != null ? aluno.getTurma().getNomeTurma() : null;

        // calcular média e status a partir da lista de comportamentos
        this.mediaComportamento = calcularMediaComportamento(aluno.getComportamentos());
        this.statusComportamental = calcularStatusTexto(this.mediaComportamento);
    }

    private Double calcularMediaComportamento(List<Comportamento> lista) {
        if (lista == null || lista.isEmpty()) return null;
        // supondo que Comportamento tem um campo numérico 'nota' (int/Double). Ajuste conforme a sua entidade.
        double soma = 0;
        int count = 0;
        for (Comportamento c : lista) {
            if (c == null) continue;
            // aqui uso getNota(); se a sua entidade usa outro campo, altere
            soma += c.getNota(); 
            count++;
        }
        return count == 0 ? null : soma / count;
    }

    private String calcularStatusTexto(Double media) {
        if (media == null) return "Sem Avaliações";
        if (media >= 8.0) return "Excelente";
        if (media >= 6.0) return "Bom";
        return "Em Risco";
    }

    // getters/setters (omiti para brevidade; gere na IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomeTurma() { return nomeTurma; }
    public void setNomeTurma(String nomeTurma) { this.nomeTurma = nomeTurma; }

    public String getStatusComportamental() { return statusComportamental; }
    public void setStatusComportamental(String statusComportamental) { this.statusComportamental = statusComportamental; }

    public Double getMediaComportamento() { return mediaComportamento; }
    public void setMediaComportamento(Double mediaComportamento) { this.mediaComportamento = mediaComportamento; }
}