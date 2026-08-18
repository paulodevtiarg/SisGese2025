package br.com.sysgese.enumerators;

public enum TipoDoseEnum {
    MG("Miligramas"),
    ML("Mililitros"),
    G("Grama"),
    GOTAS("Gotas"),
    COMPRIMIDO("Comprimido"),
    CAPSULA("Capsula"),
    DRAGEA("Dragéa"),
    OUTRO("Outro");
    private final String descricao;

    TipoDoseEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
