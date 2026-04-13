package br.com.sysgese.enumerators;

public enum BiofisicoEnum {

    MAGRO("Magro"),
    NORMAL("Normal"),
    ATLETICO("Atlético"),
    FORTE("Forte"),
    OBESO("Obeso");

    private final String descricao;

    BiofisicoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
