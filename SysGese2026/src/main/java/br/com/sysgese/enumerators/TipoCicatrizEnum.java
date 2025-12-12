package br.com.sysgese.enumerators;

public enum TipoCicatrizEnum {
	CIRURGICA("Cirúrgica"),
    ACIDENTE("Acidente"),
    AUTOINFLIGIDA("Autoinfligida"),
    OUTRO("Outro");
    
    private final String descricao;
    
    TipoCicatrizEnum(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }

}
