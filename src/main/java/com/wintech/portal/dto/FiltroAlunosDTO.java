package com.wintech.portal.dto;

public class FiltroAlunosDTO {
    private Long idTurma;
    private String busca;
    private String statusComportamento;

    public FiltroAlunosDTO() {}

    public FiltroAlunosDTO(Long idTurma, String busca, String statusComportamento) {
        this.idTurma = idTurma;
        this.busca = busca;
        this.statusComportamento = statusComportamento;
    }

    // Getters e Setters
    public Long getIdTurma() { return idTurma; }
    public void setIdTurma(Long idTurma) { this.idTurma = idTurma; }
    public String getBusca() { return busca; }
    public void setBusca(String busca) { this.busca = busca; }
    public String getStatusComportamento() { return statusComportamento; }
    public void setStatusComportamento(String statusComportamento) { this.statusComportamento = statusComportamento; }
}