package br.com.sysgese.enumerators;

public enum SobrancelhaEnum {

    FINA("Fina"),
    MEDIA("Média"),
    GROSSA("Grossa"),
    ARQUEADA("Arqueada"),
    RETA("Reta"),
    FALHADA("Falhada");

    private final String descricao;

    SobrancelhaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}