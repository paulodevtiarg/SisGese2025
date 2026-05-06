package br.com.sysgese.enumerators;

public enum ProcedenciaEnum {

    COMARCA("Comarca"),
    DELEGACIA("Delegacia"),
    IGNORADO("Ignorado"),
    OUTROS("Outros"),
    TRANSF_UNIDADE("Transferido de Unidade");

    private final String descricao;

    ProcedenciaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
