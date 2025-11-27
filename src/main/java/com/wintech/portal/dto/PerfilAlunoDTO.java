package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilAlunoDTO {
    private Long id;
    private String nome;
    private String foto;
    private Integer idade;
    private String turma;
    private String idMatricula; // Formato: #015 dígitos
    private String statusMatricula; // "Matriculado" ou "Inativo"
    private String statusComportamento; // "Excelente", "Bom", "Em Risco"
    private List<String> disciplinas;
    private List<ComportamentoHistoricoDTO> comportamentoHistorico;

    // Campos extras
    private String email;
    private String telefone;
    private String nomeResponsavel;
    private String dataNascimento;

    // Classe interna para histórico
    public static class ComportamentoHistoricoDTO {
        private String bimestre;
        private String meses;
        private String status;

        public ComportamentoHistoricoDTO(String bimestre, String meses, String status) {
            this.bimestre = bimestre;
            this.meses = meses;
            this.status = status;
        }

        // Getters e Setters
        public String getBimestre() { return bimestre; }
        public void setBimestre(String bimestre) { this.bimestre = bimestre; }
        public String getMeses() { return meses; }
        public void setMeses(String meses) { this.meses = meses; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public PerfilAlunoDTO() {}

    public PerfilAlunoDTO(Aluno aluno) {
        this.id = aluno.getId_aluno();

        // Dados do usuário
        if (aluno.getUsuario() != null) {
            this.nome = aluno.getUsuario().getNome();
            this.email = aluno.getUsuario().getEmail();
        }

        this.foto = aluno.getFoto();
        this.idade = calcularIdade(aluno.getDataNascimento());
        this.telefone = aluno.getTelefone();
        this.nomeResponsavel = aluno.getNomeResponsavel();

        if (aluno.getDataNascimento() != null) {
            this.dataNascimento = aluno.getDataNascimento().toString();
        }

        // Dados da turma
        if (aluno.getTurma() != null) {
            this.turma = aluno.getTurma().getNomeTurma();
        }

        // ID Matrícula formatado
        this.idMatricula = "#" + String.format("%015d", aluno.getId_aluno());

        // Status de matrícula
        this.statusMatricula = aluno.getAtivo() != null && aluno.getAtivo()
                ? "Matriculado"
                : "Inativo";

        // Status de comportamento
        this.statusComportamento = calcularStatusComportamento(aluno);

        // Disciplinas (simulado - você pode buscar do banco depois)
        this.disciplinas = List.of("Matemática", "História", "Geografia",
                "Ciências", "Português", "Inglês");

        // Histórico de comportamento
        this.comportamentoHistorico = gerarHistoricoComportamento(aluno);
    }

    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) return null;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private String calcularStatusComportamento(Aluno aluno) {
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

    private List<ComportamentoHistoricoDTO> gerarHistoricoComportamento(Aluno aluno) {
        List<ComportamentoHistoricoDTO> historico = new ArrayList<>();

        // Simulando histórico por bimestre
        // TODO: Implementar lógica real baseada em datas dos comportamentos
        historico.add(new ComportamentoHistoricoDTO("1 Bimestre", "Jan - Mar", "Excelente"));
        historico.add(new ComportamentoHistoricoDTO("2 Bimestre", "Abril - Jun", "Bom"));

        return historico;
    }

    // Getters e Setters completos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public String getTurma() { return turma; }
    public void setTurma(String turma) { this.turma = turma; }

    public String getIdMatricula() { return idMatricula; }
    public void setIdMatricula(String idMatricula) { this.idMatricula = idMatricula; }

    public String getStatusMatricula() { return statusMatricula; }
    public void setStatusMatricula(String statusMatricula) {
        this.statusMatricula = statusMatricula;
    }

    public String getStatusComportamento() { return statusComportamento; }
    public void setStatusComportamento(String statusComportamento) {
        this.statusComportamento = statusComportamento;
    }

    public List<String> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<ComportamentoHistoricoDTO> getComportamentoHistorico() {
        return comportamentoHistorico;
    }
    public void setComportamentoHistorico(List<ComportamentoHistoricoDTO> comportamentoHistorico) {
        this.comportamentoHistorico = comportamentoHistorico;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}