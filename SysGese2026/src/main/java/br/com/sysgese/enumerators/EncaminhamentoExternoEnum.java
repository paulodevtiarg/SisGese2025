package br.com.sysgese.enumerators;

public enum EncaminhamentoExternoEnum {
    ATENDIMENTO_MEDICO_EXTERNO("Atendimento Médico Externo"),
    PSICOLOGIA_EXTERNO("Psicologia Externa"),
    SERVICO_SOCIAL_EXTERNO("Serviço Social Externo"),
    ODONTOLOGIA_EXTERNO("Odontologia Externa"),
    UNIDADE_SAUDE_EXTERNA("Unidade de Saúde Externa"),
    HOSPITAL("Hospital"),
    EMERGENCIA("Emergência"),
    OUTRO_EXTERNO("Outro Externo");

    private final String descricao;
    EncaminhamentoExternoEnum(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
