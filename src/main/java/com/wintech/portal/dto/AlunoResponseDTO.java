package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
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
    private String statusComportamento; // "Excelente", "Bom", "Em Risco"
    private Double mediaNota; // Adicionei este campo para o front poder mostrar o número (ex: 4.5)

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
        }

        this.telefone = aluno.getTelefone();
        this.nomeResponsavel = aluno.getNomeResponsavel();

        // Pegando nome da turma
        if (aluno.getTurma() != null) {
            this.nomeTurma = aluno.getTurma().getNomeTurma();
        }

        // 1. Calcula a média numérica (Sua lógica)
        this.mediaNota = calcularMediaComportamento(aluno.getComportamentos());

        // 2. Define o texto baseado na média calculada
        this.statusComportamento = calcularStatusTexto(this.mediaNota);
    }

    // --- SUA LÓGICA DE CÁLCULO (Média dos 4 critérios) ---
    private Double calcularMediaComportamento(List<Comportamento> lista) {
        if (lista == null || lista.isEmpty()) return null;

        double soma = 0;
        int count = 0;

        for (Comportamento c : lista) {
            if (c == null) continue;

            // Média dos 4 critérios para este registro específico
            // Usa 0 se algum campo for nulo para evitar erro
            double mediaDoRegistro = (
                    (c.getParticipacao() != null ? c.getParticipacao() : 0) +
                            (c.getResponsabilidade() != null ? c.getResponsabilidade() : 0) +
                            (c.getSociabilidade() != null ? c.getSociabilidade() : 0) +
                            (c.getAssiduidade() != null ? c.getAssiduidade() : 0)
            ) / 4.0;

            soma += mediaDoRegistro;
            count++;
        }

        return count == 0 ? null : soma / count;
    }

    // --- Lógica para transformar o número em texto/status ---
    private String calcularStatusTexto(Double media) {
        if (media == null) return "Novo"; // Sem avaliações ainda

        // Escala de 1 a 5
        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }
    public String getNomeTurma() { return nomeTurma; }
    public void setNomeTurma(String nomeTurma) { this.nomeTurma = nomeTurma; }
    public String getStatusComportamento() { return statusComportamento; }
    public void setStatusComportamento(String statusComportamento) { this.statusComportamento = statusComportamento; }
    public Double getMediaNota() { return mediaNota; }
    public void setMediaNota(Double mediaNota) { this.mediaNota = mediaNota; }
}