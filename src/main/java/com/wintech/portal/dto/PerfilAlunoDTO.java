package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
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
    private String statusComportamento; // "Excelente", "Bom", "Mediano", "Ruim", "Péssimo"
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
        this.statusMatricula = (aluno.getAtivo() != null && aluno.getAtivo())
                ? "Matriculado"
                : "Inativo";

        // Status de comportamento (última avaliação)
        this.statusComportamento = buscarStatusGeralDoAluno(aluno.getComportamentos());

        // Disciplinas da turma
        this.disciplinas = buscarDisciplinasDaTurma(aluno);

        // Histórico de comportamento por bimestre
        this.comportamentoHistorico = gerarHistoricoComportamento(aluno);
    }

    /**
     * Calcula idade a partir da data de nascimento
     */
    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) return null;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    /**
     * Busca o status geral do aluno (última avaliação de comportamento)
     */
    private String buscarStatusGeralDoAluno(List<Comportamento> comportamentos) {
        if (comportamentos == null || comportamentos.isEmpty()) {
            return "Sem avaliação";
        }

        // Pega a avaliação mais recente (Pela data de registro)
        Comportamento ultimaAvaliacao = comportamentos.stream()
                .max(Comparator.comparing(Comportamento::getDataRegistro))
                .orElse(null);

        if (ultimaAvaliacao == null) {
            return "Sem avaliação";
        }

        // Calcula o status com base na média das notas
        return calcularStatusTexto(ultimaAvaliacao);
    }

    /**
     * Busca disciplinas da turma do aluno
     */
    private List<String> buscarDisciplinasDaTurma(Aluno aluno) {
        if (aluno.getTurma() == null || aluno.getTurma().getTurmaDisciplinas() == null) {
            return new ArrayList<>();
        }

        return aluno.getTurma().getTurmaDisciplinas().stream()
                .map(turmaDisciplina -> turmaDisciplina.getDisciplina().getNomeDisciplina())
                .collect(Collectors.toList());
    }

    /**
     * Gera histórico de comportamento agrupado por bimestre (Calculado via data)
     */
    private List<ComportamentoHistoricoDTO> gerarHistoricoComportamento(Aluno aluno) {
        List<ComportamentoHistoricoDTO> historico = new ArrayList<>();
        List<Comportamento> avaliacoes = aluno.getComportamentos();

        // Se não há avaliações, retorna histórico vazio
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            for (int bim = 1; bim <= 4; bim++) {
                historico.add(new ComportamentoHistoricoDTO(
                        bim + "º Bimestre",
                        obterPeriodoBimestre(bim),
                        "Sem avaliação"
                ));
            }
            return historico;
        }

        // Descobre o ano mais recente presente nos dados
        int anoAtual = avaliacoes.stream()
                .map(c -> c.getDataRegistro().getYear())
                .max(Integer::compareTo)
                .orElse(LocalDate.now().getYear());

        // Cria entrada para cada bimestre
        for (int bim = 1; bim <= 4; bim++) {
            final int bimestreAlvo = bim;

            // Busca avaliação deste bimestre no ano atual
            // Filtra pela data (Mês)
            Comportamento compBimestre = avaliacoes.stream()
                    .filter(c -> c.getDataRegistro().getYear() == anoAtual)
                    .filter(c -> calcularBimestrePelaData(c.getDataRegistro()) == bimestreAlvo)
                    // Se tiver mais de uma no mesmo bimestre, pega a mais recente
                    .max(Comparator.comparing(Comportamento::getDataRegistro))
                    .orElse(null);

            String status = compBimestre != null ? calcularStatusTexto(compBimestre) : "Sem avaliação";

            historico.add(new ComportamentoHistoricoDTO(
                    bim + "º Bimestre",
                    obterPeriodoBimestre(bim),
                    status
            ));
        }

        return historico;
    }

    // --- MÉTODOS AUXILIARES DE CÁLCULO ---

    private int calcularBimestrePelaData(LocalDate data) {
        int mes = data.getMonthValue();
        if (mes <= 3) return 1;      // Jan, Fev, Mar -> 1º Bim
        if (mes <= 6) return 2;      // Abr, Mai, Jun -> 2º Bim
        if (mes <= 9) return 3;      // Jul, Ago, Set -> 3º Bim
        return 4;                    // Out, Nov, Dez -> 4º Bim
    }

    private String calcularStatusTexto(Comportamento c) {
        double media = (
                (c.getParticipacao() != null ? c.getParticipacao() : 0) +
                        (c.getResponsabilidade() != null ? c.getResponsabilidade() : 0) +
                        (c.getSociabilidade() != null ? c.getSociabilidade() : 0) +
                        (c.getAssiduidade() != null ? c.getAssiduidade() : 0)
        ) / 4.0;

        if (media >= 4.5) return "Excelente";
        if (media >= 3.0) return "Bom";
        return "Em Risco";
    }

    private String obterPeriodoBimestre(int bimestre) {
        switch (bimestre) {
            case 1: return "Jan - Mar";
            case 2: return "Abr - Jun";
            case 3: return "Jul - Set";
            case 4: return "Out - Dez";
            default: return "";
        }
    }

    // ===== GETTERS E SETTERS (Mantidos) =====
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
    public void setStatusMatricula(String statusMatricula) { this.statusMatricula = statusMatricula; }
    public String getStatusComportamento() { return statusComportamento; }
    public void setStatusComportamento(String statusComportamento) { this.statusComportamento = statusComportamento; }
    public List<String> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<String> disciplinas) { this.disciplinas = disciplinas; }
    public List<ComportamentoHistoricoDTO> getComportamentoHistorico() { return comportamentoHistorico; }
    public void setComportamentoHistorico(List<ComportamentoHistoricoDTO> comportamentoHistorico) { this.comportamentoHistorico = comportamentoHistorico; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
}