package br.com.sysgese.enumerators;

public enum AnoEscolaridadeEnum {

    PRE_ESCOLAR("Pré-escolar"),

    FUNDAMENTAL_1("1º ano - Fundamental"),
    FUNDAMENTAL_2("2º ano - Fundamental"),
    FUNDAMENTAL_3("3º ano - Fundamental"),
    FUNDAMENTAL_4("4º ano - Fundamental"),
    FUNDAMENTAL_5("5º ano - Fundamental"),
    FUNDAMENTAL_6("6º ano - Fundamental"),
    FUNDAMENTAL_7("7º ano - Fundamental"),
    FUNDAMENTAL_8("8º ano - Fundamental"),
    FUNDAMENTAL_9("9º ano - Fundamental"),

    MEDIO_1("1º ano - Ensino Médio"),
    MEDIO_2("2º ano - Ensino Médio"),
    MEDIO_3("3º ano - Ensino Médio"),

    SUPERIOR_INCOMPLETO("Superior Incompleto"),
    SUPERIOR_COMPLETO("Superior Completo"),

    POS_GRADUACAO("Pós-graduação");

    private final String descricao;

    AnoEscolaridadeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}