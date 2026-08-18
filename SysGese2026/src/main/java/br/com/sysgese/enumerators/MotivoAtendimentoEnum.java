package br.com.sysgese.enumerators;

public enum MotivoAtendimentoEnum {

    MAL_ESTAR("Mal-Estar"),
    DOR("Dor"),
    FEBRE("Febre"),
    CEFALEIA("Cefaleia"),
    NAUSEA_VOMITO("Náusea/Vômito"),
    DIARREIA("Diarreia"),
    QUEIXA_RESPIRATORIA("Queixa Respiratória"),
    DOR_ABDOMINAL("Dor Abdominal"),
    LESAO_FERIMENTO("Lesão/Ferimento"),
    QUEIXA_URINARIA("Queixa Urinária"),
    QUEIXA_MUSCULOESQUELETICA("Queixa musculoesquelética"),
    ALTERACAO_EMOCIONAL_COMPORTAMENTAL("Alteração Emocional/Comportamental"),
    ADMINISTRACAO_MEDICAMENTO("Administração de Medicamento"),
    AVALIACAO_ROTINA("Avaliação de Rotina"),
    RETORNO_REAVALIACAO("Retorno/Reavaliação"),
    OUTROS("Outros");

    private final String descricao;

    MotivoAtendimentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
