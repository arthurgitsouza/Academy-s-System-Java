package com.wintech.portal.dto;

public class AlunoRequestDTO {

    // Dados do Usuário
    private String nome;
    private String email;
    private String senha;       // se necessário para cadastro de usuário
    
    // Dados Específicos do Aluno
    private String dataNascimento; // ou LocalDate, se preferir
    private String telefone;
    private String nomeResponsavel;
    
    // Vínculo
    private Long idTurma;

    public AlunoRequestDTO() {}

    // getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

     // Novos Getters e Setters
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }

    public Long getIdTurma() { return idTurma; }
    public void setIdTurma(Long idTurma) { this.idTurma = idTurma; }
}
