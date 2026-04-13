package br.com.sysgese.enumerators;

public enum CabeloEnum {

    LISO("Liso"),
    ONDULADO("Ondulado"),
    CACHEADO("Cacheado"),
    CRESPO("Crespo"),
    CARECA("Careca"),
    RASPADO("Raspado");

    private final String descricao;

    CabeloEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}