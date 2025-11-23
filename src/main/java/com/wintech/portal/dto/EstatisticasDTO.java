package com.wintech.portal.dto;

public class EstatisticasDTO {
    private long totalAlunos;
    private long totalProfessores;

    public EstatisticasDTO() {}

    public EstatisticasDTO(long totalAlunos, long totalProfessores) {
        this.totalAlunos = totalAlunos;
        this.totalProfessores = totalProfessores;
    }

    public long getTotalAlunos() { return totalAlunos; }
    public void setTotalAlunos(long totalAlunos) { this.totalAlunos = totalAlunos; }
    public long getTotalProfessores() { return totalProfessores; }
    public void setTotalProfessores(long totalProfessores) { this.totalProfessores = totalProfessores; }
}