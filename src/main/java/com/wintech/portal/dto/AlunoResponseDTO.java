package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

public class AlunoResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String perfil;
    private String dataNascimento;
    private String telefone;
    private String nomeResponsavel;
    private String nomeTurma;
    private String statusComportamento; // "Excelente", "Bom", "Mediano", "Ruim", "Péssimo"
    private Integer idade;

    public AlunoResponseDTO() {}

    // --- CONSTRUTOR DE CONVERSÃO ---
    public AlunoResponseDTO(Aluno aluno) {
        this.id = aluno.getId_aluno();

        // Pegando dados do Usuario vinculado
        if (aluno.getUsuario() != null) {
            this.nome = aluno.getUsuario().getNome();
            this.email = aluno.getUsuario().getEmail();
            this.perfil = aluno.getUsuario().getPerfil();
        }

        if (aluno.getDataNascimento() != null) {
            this.dataNascimento = aluno.getDataNascimento().toString();
            this.idade = Period.between(aluno.getDataNascimento(), LocalDate.now()).getYears();
        }

        this.telefone = aluno.getTelefone();
        this.nomeResponsavel = aluno.getNomeResponsavel();

        // Pegando nome da turma
        if (aluno.getTurma() != null) {
            this.nomeTurma = aluno.getTurma().getNomeTurma();
        }

        // Busca o status geral (última avaliação de comportamento)
        this.statusComportamento = buscarStatusGeralDoAluno(aluno.getComportamentos());
    }

    /**
     * Busca o status geral do aluno (última avaliação de comportamento)
     * Usa a nova estrutura: bimestre, anoLetivo, status
     */
    private String buscarStatusGeralDoAluno(List<Comportamento> comportamentos) {
        if (comportamentos == null || comportamentos.isEmpty()) {
            return "Sem avaliação";
        }

        // Pega a avaliação mais recente (por data de registro)
        Comportamento ultimaAvaliacao = comportamentos.stream()
                .max(Comparator.comparing(Comportamento::getDataRegistro))
                .orElse(null);

        if (ultimaAvaliacao == null) {
            return "Sem avaliação";
        }

        // Calcula a média dos 4 critérios
        double media = (
                (ultimaAvaliacao.getParticipacao() != null ? ultimaAvaliacao.getParticipacao() : 0) +
                        (ultimaAvaliacao.getResponsabilidade() != null ? ultimaAvaliacao.getResponsabilidade() : 0) +
                        (ultimaAvaliacao.getSociabilidade() != null ? ultimaAvaliacao.getSociabilidade() : 0) +
                        (ultimaAvaliacao.getAssiduidade() != null ? ultimaAvaliacao.getAssiduidade() : 0)
        ) / 4.0;

        // Retorna o status baseado na média
        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
    }

    // --- Getters e Setters ---
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getStatusComportamento() {
        return statusComportamento;
    }

    public void setStatusComportamento(String statusComportamento) {
        this.statusComportamento = statusComportamento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}