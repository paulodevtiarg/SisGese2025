package br.com.sysgese.enumerators;

public enum HorarioEnum {
    OITO_OITO("8/8h"),
    SEIS_SEIS("6/6h"),
    DOZE_DOZE("12/12h"),
    QUATRO_QUATRO("4/4h"),
    OUTROS("Outros");

    private final String descricao;

    HorarioEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
