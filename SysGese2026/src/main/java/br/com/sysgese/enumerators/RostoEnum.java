package br.com.sysgese.enumerators;


public enum RostoEnum {

    OVAL("Oval"),
    REDONDO("Redondo"),
    QUADRADO("Quadrado"),
    TRIANGULAR("Triangular"),
    RETANGULAR("Retangular"),
    LONGO("Longo"),
    FINO("Fino"),
    LARGO("Largo");

    private final String descricao;

    RostoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
