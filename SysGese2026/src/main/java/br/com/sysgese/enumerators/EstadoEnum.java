package br.com.sysgese.enumerators;

public enum EstadoEnum {

    BOM("Bom"),
    REGULAR("Regular"),
    MAU("Mau"),
    OUTROS("Outros");


    private final String descricao;

    EstadoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
