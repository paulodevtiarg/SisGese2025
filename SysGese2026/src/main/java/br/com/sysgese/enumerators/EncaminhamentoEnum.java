package br.com.sysgese.enumerators;

public enum EncaminhamentoEnum {

    ATENDIMENTO_MEDICO("Atendimento Médico"),
    PSICOLOGIA("Psicologia"),
    SERVICO_SOCIAL("Serviço Social"),
    ODONTOLOGIA("Odontologia"),
    UNIDADE_SAUDE_EXTERNA("Unidade de Saúde Externa"),
    HOSPITAL("Hospital"),
    EMERGENCIA("Emergência"),
    OUTRO("Outro");
    private final String descricao;

    EncaminhamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
