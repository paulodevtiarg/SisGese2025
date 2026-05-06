package br.com.sysgese.enumerators;

public enum DocumentosEnum {

    AUTO_AP_FLAGRANTE("Auto de Apreensão em Flagrante"),
    BOL_OCORRENCIA("Boletim de Ocorrência"),
    GUIA_INT_PROVISORIA("Guia de Internação Provisória"),
    GUIA_INtERNACAO("Guia de Internação"),
    RELATORIO_INVESTIGACAO("Relatório de Investigação"),
    PROCESSO("Processo Formalizado"),
    NENHUM("Nenhum");

    private final String descricao;

    DocumentosEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
