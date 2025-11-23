package com.wintech.portal.dto;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Disciplina;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilAlunoDTO {
    private Long id;
    private String nome;
    private String foto;
    private Integer idade;
    private String turma;
    private String idAluno; // Matrícula
    private String status; // "Matriculado"
    private String statusComportamento;
    private List<DisciplinaSimplificadaDTO> disciplinas;

    public PerfilAlunoDTO() {}

    public PerfilAlunoDTO(Aluno aluno) {
        this.id = aluno.getId_aluno();
        this.nome = aluno.getUsuario() != null ? aluno.getUsuario().getNome() : "";
        this.foto = aluno.getFoto();
        this.idade = calcularIdade(aluno.getDataNascimento());
        this.turma = aluno.getTurma() != null ? aluno.getTurma().getNomeTurma() : "";
        this.idAluno = "#" + String.format("%015d", aluno.getId_aluno());
        this.status = "Matriculado";
        this.statusComportamento = calcularStatus(aluno);
        // disciplinas precisa ser preenchido pelo service
    }

    private Integer calcularIdade(java.time.LocalDate dataNascimento) {
        if (dataNascimento == null) return null;
        return java.time.Period.between(dataNascimento, java.time.LocalDate.now()).getYears();
    }

    private String calcularStatus(Aluno aluno) {
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
    public String getIdAluno() { return idAluno; }
    public void setIdAluno(String idAluno) { this.idAluno = idAluno; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStatusComportamento() { return statusComportamento; }
    public void setStatusComportamento(String statusComportamento) { this.statusComportamento = statusComportamento; }
    public List<DisciplinaSimplificadaDTO> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<DisciplinaSimplificadaDTO> disciplinas) { this.disciplinas = disciplinas; }
}