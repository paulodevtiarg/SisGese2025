package br.com.sysgese.enumerators;

public enum TipoMedidaEnum {

    SEMILIBERDADE("Semiliberdade"),
    INTERNACAO("Internação"),
    INTERNACAO_PROVISORIA("Internação Provisória"),
    INTERNACAO_SANCAO("Internação Sanção"),
    LIBERDADE_ASSISTIDA("Liberdade Assistida");

    private final String descricao;

    TipoMedidaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
