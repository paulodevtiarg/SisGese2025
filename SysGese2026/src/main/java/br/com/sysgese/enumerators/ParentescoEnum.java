package br.com.sysgese.enumerators;

public enum ParentescoEnum {
    MAE("Mãe"),
    PAI("Pai"),
    TIO("Tio"),
    TIA("Tia"),
    IRMAO("Irmão"),
    IRMA("Irmã"),
    SOBRINHO("Sobrinho"),
    SOBRINHA("Sobrinha"),
    FILHO("Filho"),
    FILHA("Filha"),
    ENTEADO("Enteado"),
    ENTEADA("Enteada"),
    PADRINHO("Padrinho"),
    MADRINHA("Madrinha"),
    PRIMO("Primo"),
    PRIMA("Prima"),
    AVO("Avô"),
    AVO_("Avó"); // Ou como você definiu no banco (AVÓ_)

    private final String descricao;

    ParentescoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}