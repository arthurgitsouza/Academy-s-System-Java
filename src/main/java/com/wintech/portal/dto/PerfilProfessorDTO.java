package com.wintech.portal.dto;

import com.wintech.portal.domain.Professor;

import java.util.List;
import java.util.stream.Collectors;

public class PerfilProfessorDTO {
    private Long id;
    private String nome;
    private String foto;
    private Integer idade; // Pode ser calculado se tiver data de nascimento
    private String departamento; // Mapeado de 'especialidade'
    private String email;
    private String telefone;
    private List<String> turmasLecionadas;
    private Integer totalTurmas;
    private Integer totalAlunos;
    private String status; // "Ativo" ou "Inativo"

    public PerfilProfessorDTO() {}

    public PerfilProfessorDTO(Professor professor) {
        this.id = professor.getId_professor();

        // Dados do usuário vinculado
        if (professor.getUsuario() != null) {
            this.nome = professor.getUsuario().getNome();
            this.email = professor.getUsuario().getEmail();
        }

        // Dados do professor
        this.departamento = professor.getEspecialidade();
        this.status = professor.getAtivo() != null && professor.getAtivo()
                ? "Ativo"
                : "Inativo";

        // Extrair turmas únicas
        if (professor.getTurmaDisciplinas() != null && !professor.getTurmaDisciplinas().isEmpty()) {
            this.turmasLecionadas = professor.getTurmaDisciplinas().stream()
                    .map(td -> td.getTurma().getNomeTurma())
                    .distinct()
                    .collect(Collectors.toList());

            this.totalTurmas = this.turmasLecionadas.size();

            // Calcular total de alunos (estimativa: 25 alunos por turma)
            this.totalAlunos = this.totalTurmas * 25;
        } else {
            this.turmasLecionadas = List.of();
            this.totalTurmas = 0;
            this.totalAlunos = 0;
        }

        // Telefone (pode ser null)
        this.telefone = null; // TODO: Adicionar campo telefone na entidade Professor se necessário

        // Idade (calculada se houver data de nascimento)
        this.idade = null; // TODO: Adicionar cálculo se houver campo dataNascimento
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

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public List<String> getTurmasLecionadas() { return turmasLecionadas; }
    public void setTurmasLecionadas(List<String> turmasLecionadas) {
        this.turmasLecionadas = turmasLecionadas;
    }

    public Integer getTotalTurmas() { return totalTurmas; }
    public void setTotalTurmas(Integer totalTurmas) { this.totalTurmas = totalTurmas; }

    public Integer getTotalAlunos() { return totalAlunos; }
    public void setTotalAlunos(Integer totalAlunos) { this.totalAlunos = totalAlunos; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}