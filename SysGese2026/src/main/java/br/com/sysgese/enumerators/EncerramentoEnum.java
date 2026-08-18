package br.com.sysgese.enumerators;

public enum EncerramentoEnum {
    RESOLVIDO("RESOLVIDO"),
    EM_OBSERVACAO("Em Observação"),
    NECESSITA_ACOMPANHAMENTO("Necessita Acompanhamento"),
    ENCAMINHADO("Encaminhado"),
    NECESSITA_REAVALIACAO("Necessita Reavaliação");

    private final String descricao;

    EncerramentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
