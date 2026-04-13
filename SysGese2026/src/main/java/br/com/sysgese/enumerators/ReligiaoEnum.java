package br.com.sysgese.enumerators;

public enum ReligiaoEnum {

    CATOLICA("Católica"),
    EVANGELICA("Evangélica"),
    ESPIRITA("Espírita"),
    UMBANDA("Umbanda"),
    CANDOMBLE("Candomblé"),
    JUDAICA("Judaica"),
    ISLAMICA("Islâmica"),
    BUDISTA("Budista"),
    ATEU("Ateu"),
    AGNOSTICO("Agnóstico"),
    OUTRA("Outra"),
    NAO_DECLARADA("Não declarada");

    private final String descricao;

    ReligiaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}