package br.com.sysgese.enumerators;

public enum ViaAdmEnum {
    VO("Via Oral"),
    IV("Via Intravenosa"),
    IM("Via Intramuscular"),
    SC("Subcutânea"),
    TOPICA("Tópica"),
    INALATORIA("Inalatória"),
    RETAL("Retal"),
    SUBLINGUAL("Dragéa");
    private final String descricao;
    ViaAdmEnum(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
