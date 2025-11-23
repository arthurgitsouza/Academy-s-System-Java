package com.wintech.portal.dto;

public class HistoricoComportamentoDTO {
    private String bimestre; // "1 Bimestre", "2 Bimestre"
    private String periodo; // "Jan - Mar"
    private String status; // "Excelente", "Bom"
    private String statusCor;

    public HistoricoComportamentoDTO() {}

    public HistoricoComportamentoDTO(String bimestre, String periodo, String status) {
        this.bimestre = bimestre;
        this.periodo = periodo;
        this.status = status;
        this.statusCor = mapearCor(status);
    }

    private String mapearCor(String status) {
        switch (status) {
            case "Excelente": return "green";
            case "Bom": return "blue";
            case "Em Risco": return "red";
            default: return "gray";
        }
    }

    // Getters e Setters
    public String getBimestre() { return bimestre; }
    public void setBimestre(String bimestre) { this.bimestre = bimestre; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStatusCor() { return statusCor; }
    public void setStatusCor(String statusCor) { this.statusCor = statusCor; }
}