package br.com.sysgese.enumerators;

public enum CondutaEnum {

    ORIENTACAO("Orientação"),
    REPOUSO("Repouso"),
    HIDRATACAO("Hidratação"),
    CURATIVO("Curativo"),
    HIGIENIZACAO_FERIMENTO("Higienização de Ferimento"),
    ADMINISTRACAO_MEDICAMENTO("Administração de Medicamento"),
    OBSERVACAO("Observação"),
    REAVALIACAO("Reavaliação"),
    COMUNICACAO_EQUIPE_MEDICA("Comunicação à equipe médica"),
    ENCAMINHAMENTO_ATENDIMENTO_MEDICO("Encaminhamento para Atendimento Médico"),
    ENCAMINHAMENTO_EXTERNO("Encaminhamento Externo"),
    OUTROS("Outros");

    private final String descricao;

    CondutaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
