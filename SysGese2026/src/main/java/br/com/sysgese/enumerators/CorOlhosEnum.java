package br.com.sysgese.enumerators;

public enum CorOlhosEnum {

    CASTANHO("Castanho"),
    PRETO("Preto"),
    AZUL("Azul"),
    VERDE("Verde"),
    MEL("Mel"),
    CINZA("Cinza"),
    HETEROCROMIA("Heterocromia");

    private final String descricao;

    CorOlhosEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}