package br.com.sysgese.enumerators;

public enum OrelhaEnum {

    PEQUENA("Pequena"),
    MEDIA("Média"),
    GRANDE("Grande"),
    COLADA("Colada"),
    DESCOLADA("Descolada");

    private final String descricao;

    OrelhaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}