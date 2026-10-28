package br.com.sysgese.enumerators;

public enum EncaminhamentoInternoEnum {
    ATENDIMENTO_MEDICO("Atendimento Médico Interno"),
    PSICOLOGIA("Psicologia Interna"),
    SERVICO_SOCIAL("Serviço Social Interno"),
    ODONTOLOGIA("Odontologia Interna"),
    OUTROS("Outro Interno");

    private final String descricao;
    EncaminhamentoInternoEnum(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}