package br.com.sysgese.dtos;

public class RelatorioDashboardFiltroDTO {
    private String tipoPeriodo;
    private Integer ano;
    private Integer anoInicio;
    private Integer anoFim;
    private String cidade;
    private String modoVisualizacao;

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }

    public Integer getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getModoVisualizacao() {
        return modoVisualizacao;
    }

    public void setModoVisualizacao(String modoVisualizacao) {
        this.modoVisualizacao = modoVisualizacao;
    }
}
