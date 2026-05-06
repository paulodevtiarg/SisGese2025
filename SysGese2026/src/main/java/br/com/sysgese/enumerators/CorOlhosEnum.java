package br.com.sysgese.enumerators;

public enum CorOlhosEnum {

    CASTANHO("Castanho"),
    CASTANHO_ESCURO("Castanho Escuro"),
    PRETO("Preto"),
    AZUL("Azul"),
    VERDE("Verde"),
    MEL("Mel"),
    CINZA("Cinza"),
    CLARO("Claro"),
    HETEROCROMIA("Heterocromia");

    private final String descricao;

    CorOlhosEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}