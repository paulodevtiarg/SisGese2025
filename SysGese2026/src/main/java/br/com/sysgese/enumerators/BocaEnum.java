package br.com.sysgese.enumerators;

public enum BocaEnum {

    PEQUENA("Pequena"),
    MEDIA("Média"),
    GRANDE("Grande"),
    LABIOS_FINOS("Lábios finos"),
    LABIOS_GROSSOS("Lábios grossos");

    private final String descricao;

    BocaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}