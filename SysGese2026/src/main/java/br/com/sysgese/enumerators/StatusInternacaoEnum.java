package br.com.sysgese.enumerators;

public enum StatusInternacaoEnum {

    ATIVA(1, "Ativa"),
    FINALIZADA(2, "Finalizada"),
    CANCELADA(3, "Cancelada"),
    TRANSFERIDA(4, "Transferida");

    private final Integer codigo;
    private final String descricao;

    StatusInternacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusInternacaoEnum fromCodigo(Integer codigo) {
        for (StatusInternacaoEnum status : values()) {
            if (status.getCodigo().equals(codigo)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }
}