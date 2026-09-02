package br.com.sysgese.enumerators;

public enum TipoFuncaoEnum {
    ENFERMEIRO("Enfermeiro(a)"),
    MEDICO("Médico(a)"),
    PSICOLOGO("Psicólogo(a)"),
    AGENTE("Agente"),
    MOTORISTA("Motorista"),
    JURIDICO("Juridico"),
    SERVICO_SOCIAL("Assistente Social"),
    PEDAGOGO("Pedagogo(a)"),
    EDUCADOR_FISICO("Educador(a) Físico"),
    CHEFE_PLANTAO("Chefe Plantão"),
    TEC_ENFERMAGEM("Téc. Enfermagem"),
    ODONTOLOGO("Odontologo(a)"),
    COORDENADOR("Coordenador(a)"),
    ADMINISTRATIVO("Administrativo(a)");
    private final String descricao;
    TipoFuncaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
