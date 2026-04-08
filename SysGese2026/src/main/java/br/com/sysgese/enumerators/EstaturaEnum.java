package br.com.sysgese.enumerators;

public enum EstaturaEnum {

    ABAIXO_150("Abaixo de 1,50 m"),
    DE_150_160("1,50 m a 1,60 m"),
    DE_161_170("1,61 m a 1,70 m"),
    DE_171_180("1,71 m a 1,80 m"),
    DE_181_190("1,81 m a 1,90 m"),
    ACIMA_190("Acima de 1,90 m");

    private final String descricao;

    EstaturaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}