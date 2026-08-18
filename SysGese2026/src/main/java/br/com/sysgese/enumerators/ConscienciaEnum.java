package br.com.sysgese.enumerators;

public enum ConscienciaEnum {

    CONSCIENTE("Consciente"),
    SONOLENTO("Sonolento"),
    CONFUSO("Confuso"),
    AGITADO("Agitado"),
    OUTROS("Outros");


    private final String descricao;

    ConscienciaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
