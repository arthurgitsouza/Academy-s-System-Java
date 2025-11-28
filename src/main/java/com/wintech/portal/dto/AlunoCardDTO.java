package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

/**
 * DTO: Card do Aluno (Grid/Lista)
 * Usado para exibir alunos na lista de turmas (para professores/admins)
 */
public class AlunoCardDTO {
    private Long id;
    private String nome;
    private String foto;
    private Integer idade;
    private String statusComportamento; // "Excelente", "Bom", "Mediano", "Ruim", "Péssimo"
    private String statusCor; // "green", "blue", "yellow", "orange", "red"
    private Integer totalDisciplinas;

    public AlunoCardDTO() {}

    public AlunoCardDTO(Aluno aluno) {
        this.id = aluno.getId_aluno();
        this.nome = aluno.getUsuario() != null ? aluno.getUsuario().getNome() : "Sem nome";
        this.foto = aluno.getFoto();
        this.idade = calcularIdade(aluno.getDataNascimento());
        this.statusComportamento = buscarStatusGeralDoAluno(aluno);
        this.statusCor = mapearCor(this.statusComportamento);
        this.totalDisciplinas = 12; // TODO: calcular quantidade real de disciplinas
    }

    /**
     * Calcula a idade a partir da data de nascimento
     */
    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return null;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    /**
     * Busca o status geral do aluno (última avaliação de comportamento)
     */
    private String buscarStatusGeralDoAluno(Aluno aluno) {
        if (aluno.getComportamentos() == null || aluno.getComportamentos().isEmpty()) {
            return "Sem avaliação";
        }

        // Pega a avaliação mais recente (por ano letivo e bimestre)
        Comportamento ultimaAvaliacao = aluno.getComportamentos().stream()
                .max(Comparator
                        .comparingInt(Comportamento::getAnoLetivo)
                        .thenComparingInt(Comportamento::getBimestre))
                .orElse(null);

        if (ultimaAvaliacao == null) {
            return "Sem avaliação";
        }

        return ultimaAvaliacao.getStatus(); // "Excelente", "Bom", "Mediano", "Ruim", "Péssimo"
    }

    /**
     * Mapeia o status de comportamento para uma cor
     */
    private String mapearCor(String status) {
        switch (status) {
            case "Excelente":
                return "green";
            case "Bom":
                return "blue";
            case "Mediano":
                return "yellow";
            case "Ruim":
                return "orange";
            case "Péssimo":
                return "red";
            default:
                return "gray"; // "Sem avaliação"
        }
    }

    // ===== GETTERS E SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getStatusComportamento() {
        return statusComportamento;
    }

    public void setStatusComportamento(String statusComportamento) {
        this.statusComportamento = statusComportamento;
    }

    public String getStatusCor() {
        return statusCor;
    }

    public void setStatusCor(String statusCor) {
        this.statusCor = statusCor;
    }

    public Integer getTotalDisciplinas() {
        return totalDisciplinas;
    }

    public void setTotalDisciplinas(Integer totalDisciplinas) {
        this.totalDisciplinas = totalDisciplinas;
    }
}