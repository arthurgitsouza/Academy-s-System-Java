package com.wintech.portal.dto;

import com.wintech.portal.domain.Simulado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para Simulado (Request e Response)
 */
public class SimuladoDTO {

    private Long id;

    @NotBlank(message = "Título do simulado é obrigatório")
    private String titulo;

    private LocalDate dataCriacao;

    @NotNull(message = "ID da turma é obrigatório")
    private Long idTurma;

    @NotEmpty(message = "O simulado deve ter pelo menos uma questão")
    private List<Long> idsQuestoes; // Lista de IDs das questões

    // Campos adicionais para exibição
    private String nomeTurma;
    private Integer quantidadeQuestoes;

    // Construtores
    public SimuladoDTO() {}

    // Construtor de conversão da Entidade para DTO
    public SimuladoDTO(Simulado simulado) {
        this.id = simulado.getId_simulado();
        this.titulo = simulado.getTitulo();
        this.dataCriacao = simulado.getDataCriacao();

        if (simulado.getTurma() != null) {
            this.idTurma = simulado.getTurma().getId_turma();
            this.nomeTurma = simulado.getTurma().getNomeTurma();
        }

        if (simulado.getQuestoes() != null) {
            this.idsQuestoes = simulado.getQuestoes()
                    .stream()
                    .map(q -> q.getId_questao())
                    .collect(Collectors.toList());
            this.quantidadeQuestoes = simulado.getQuestoes().size();
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
    }

    public List<Long> getIdsQuestoes() {
        return idsQuestoes;
    }

    public void setIdsQuestoes(List<Long> idsQuestoes) {
        this.idsQuestoes = idsQuestoes;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public Integer getQuantidadeQuestoes() {
        return quantidadeQuestoes;
    }

    public void setQuantidadeQuestoes(Integer quantidadeQuestoes) {
        this.quantidadeQuestoes = quantidadeQuestoes;
    }
}
