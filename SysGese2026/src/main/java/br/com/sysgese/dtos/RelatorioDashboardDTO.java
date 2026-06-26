package br.com.sysgese.dtos;

public class RelatorioDashboardDTO {
    private String nomeUnidade;

    private Long totalAdolescentes;
    private Long totalGeral;

    private Long internadosUnidade;
    private Long internadosGeral;

    private String internacoesJson;
    private String internacoesGeralJson;
    private String internacoesPorUnidadeJson;

    private String cidadeUnidadeJson;
    private String cidadeGeralJson;
    private String cidade;
    private String cnpj;
    private String telefone;
    private String logoUrl;




    public String getNomeUnidade() {
        return nomeUnidade;
    }



    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public Long getTotalAdolescentes() {
        return totalAdolescentes;
    }

    public void setTotalAdolescentes(Long totalAdolescentes) {
        this.totalAdolescentes = totalAdolescentes;
    }

    public Long getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(Long totalGeral) {
        this.totalGeral = totalGeral;
    }

    public Long getInternadosUnidade() {
        return internadosUnidade;
    }

    public void setInternadosUnidade(Long internadosUnidade) {
        this.internadosUnidade = internadosUnidade;
    }

    public Long getInternadosGeral() {
        return internadosGeral;
    }

    public void setInternadosGeral(Long internadosGeral) {
        this.internadosGeral = internadosGeral;
    }

    public String getInternacoesJson() {
        return internacoesJson;
    }

    public void setInternacoesJson(String internacoesJson) {
        this.internacoesJson = internacoesJson;
    }

    public String getInternacoesGeralJson() {
        return internacoesGeralJson;
    }

    public void setInternacoesGeralJson(String internacoesGeralJson) {
        this.internacoesGeralJson = internacoesGeralJson;
    }

    public String getInternacoesPorUnidadeJson() {
        return internacoesPorUnidadeJson;
    }

    public void setInternacoesPorUnidadeJson(String internacoesPorUnidadeJson) {
        this.internacoesPorUnidadeJson = internacoesPorUnidadeJson;
    }

    public String getCidadeUnidadeJson() {
        return cidadeUnidadeJson;
    }

    public void setCidadeUnidadeJson(String cidadeUnidadeJson) {
        this.cidadeUnidadeJson = cidadeUnidadeJson;
    }

    public String getCidadeGeralJson() {
        return cidadeGeralJson;
    }

    public void setCidadeGeralJson(String cidadeGeralJson) {
        this.cidadeGeralJson = cidadeGeralJson;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
