package br.com.sysgese.enumerators;

public enum RacaCorEnum {
	BRANCO("B", "Branco"),
	NEGRO("N", "Negro"),
	PARDO("P","Pardo"),
	AMARELO("A", "Amarelo"),
	INDIGENA("I","Indigena"),
	OUTRO("O", "Outro"),
	NAO_INFORMADO("NI","Não Informado");

	private final String codigo;
	private final String descricao;
	
	RacaCorEnum(String codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static RacaCorEnum fromCodigo(String codigo) {
		for(RacaCorEnum racaCor : RacaCorEnum.values()) {
			if(racaCor.getCodigo().equals(codigo)) {
				return racaCor;
			}
		}
		throw new IllegalArgumentException("Código Inválido"+codigo);
	}

}
