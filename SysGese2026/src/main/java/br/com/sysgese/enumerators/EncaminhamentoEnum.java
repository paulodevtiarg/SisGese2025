package br.com.sysgese.enumerators;

public enum EncaminhamentoEnum {
    INTERNO("Encaminhamento Interno"),
    EXTERNO("Encaminhamento Externo"),
    OUTROS("Outros");

    private final String descricao;
    EncaminhamentoEnum(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
