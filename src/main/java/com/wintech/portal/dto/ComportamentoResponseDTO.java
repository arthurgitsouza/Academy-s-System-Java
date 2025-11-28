package com.wintech.portal.dto;

import com.wintech.portal.domain.Comportamento;
import java.time.LocalDate;

public class ComportamentoResponseDTO {
    private Long id;
    private Long alunoId;
    private String alunoNome;
    private String disciplinaNome; // Útil para mostrar no front
    private String professorNome;  // Útil para saber quem avaliou

    // Notas
    private Integer responsabilidade;
    private Integer participacao;
    private Integer sociabilidade;
    private Integer assiduidade;

    private String observacao;
    private String status; // Calculado (Excelente, Bom, etc)
    private LocalDate dataRegistro;

    public ComportamentoResponseDTO() {}

    public ComportamentoResponseDTO(Comportamento c) {
        this.id = c.getId_comportamento();

        // Dados do Aluno
        if (c.getAluno() != null) {
            this.alunoId = c.getAluno().getId_aluno();
            if (c.getAluno().getUsuario() != null) {
                this.alunoNome = c.getAluno().getUsuario().getNome();
            }
        }

        // Dados da Disciplina
        if (c.getDisciplina() != null) {
            this.disciplinaNome = c.getDisciplina().getNomeDisciplina();
        }

        // Dados do Professor
        if (c.getProfessor() != null && c.getProfessor().getUsuario() != null) {
            this.professorNome = c.getProfessor().getUsuario().getNome();
        }

        this.responsabilidade = c.getResponsabilidade();
        this.participacao = c.getParticipacao();
        this.sociabilidade = c.getSociabilidade();
        this.assiduidade = c.getAssiduidade();
        this.observacao = c.getObservacao();
        this.dataRegistro = c.getDataRegistro();

        // CALCULA O STATUS AGORA (Regra de Negócio Visual)
        this.status = calcularStatus();
    }

    private String calcularStatus() {
        double media = (
                (this.participacao != null ? this.participacao : 0) +
                        (this.responsabilidade != null ? this.responsabilidade : 0) +
                        (this.sociabilidade != null ? this.sociabilidade : 0) +
                        (this.assiduidade != null ? this.assiduidade : 0)
        ) / 4.0;

        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAlunoId() { return alunoId; }
    public void setAlunoId(Long alunoId) { this.alunoId = alunoId; }
    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }
    public String getDisciplinaNome() { return disciplinaNome; }
    public void setDisciplinaNome(String disciplinaNome) { this.disciplinaNome = disciplinaNome; }
    public String getProfessorNome() { return professorNome; }
    public void setProfessorNome(String professorNome) { this.professorNome = professorNome; }
    public Integer getResponsabilidade() { return responsabilidade; }
    public void setResponsabilidade(Integer responsabilidade) { this.responsabilidade = responsabilidade; }
    public Integer getParticipacao() { return participacao; }
    public void setParticipacao(Integer participacao) { this.participacao = participacao; }
    public Integer getSociabilidade() { return sociabilidade; }
    public void setSociabilidade(Integer sociabilidade) { this.sociabilidade = sociabilidade; }
    public Integer getAssiduidade() { return assiduidade; }
    public void setAssiduidade(Integer assiduidade) { this.assiduidade = assiduidade; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
}