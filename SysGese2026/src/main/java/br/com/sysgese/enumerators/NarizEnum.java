package br.com.sysgese.enumerators;

public enum NarizEnum {

	ACHATADO_LONGO("Achatado e Longo"),
	ADUNCO("Adunco"),
	ARREDONDADO("Arredondado"),
	ARREBITADO("Arrebitado/Curvo"),
	CURTO("Curto"),
	FINO("Fino"),
	GRANDE("Grande"),
	LARGO("Largo"),
	LONGO("Longo"),
	PEQUENO("Pequeno"),
	RETO("Reto");

    private final String descricao;

    NarizEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}