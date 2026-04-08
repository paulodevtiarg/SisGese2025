package br.com.sysgese.enumerators;

public enum OlhosEnum {

    PEQUENOS("Pequenos"),
    MEDIOS("Médios"),
    GRANDES("Grandes"),
    FUNDOS("Fundos"),
    SALIENTES("Salientes");

    private final String descricao;

    OlhosEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}