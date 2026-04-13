package br.com.sysgese.enumerators;

public enum PescocoEnum {

    CURTO("Curto"),
    MEDIO("Médio"),
    LONGO("Longo"),
    FINO("Fino"),
    GROSSO("Grosso");

    private final String descricao;

    PescocoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}