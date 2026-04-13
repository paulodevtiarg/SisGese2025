package br.com.sysgese.enumerators;


public enum NivelEscolaridadeEnum {

    NAO_ALFABETIZADO("Não alfabetizado"),
    ENSINO_INFANTIL("Ensino Infantil"),
    ENSINO_FUNDAMENTAL("Ensino Fundamental"),
    ENSINO_MEDIO("Ensino Médio"),
    ENSINO_SUPERIOR("Ensino Superior"),
    POS_GRADUACAO("Pós-graduação");

    private final String descricao;

    NivelEscolaridadeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}