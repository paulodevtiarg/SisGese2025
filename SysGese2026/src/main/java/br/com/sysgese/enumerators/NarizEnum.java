package br.com.sysgese.enumerators;

public enum NarizEnum {

    FINO("Fino"),
    LARGO("Largo"),
    ARREBITADO("Arrebitado"),
    ADUNCO("Adunco"),
    RETO("Reto");

    private final String descricao;

    NarizEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}