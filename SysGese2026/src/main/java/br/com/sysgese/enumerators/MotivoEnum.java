package br.com.sysgese.enumerators;

public enum MotivoEnum {
	    BUSCA_APREENSAO("Busca e/ou Apreensão"),
	    REGRESSÃO("Regressão"),
	    IGNORADO("Ignorado"),
	    OUTROS("Outros");
	  

	    private final String descricao;

	    MotivoEnum(String descricao) {
	        this.descricao = descricao;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

}
