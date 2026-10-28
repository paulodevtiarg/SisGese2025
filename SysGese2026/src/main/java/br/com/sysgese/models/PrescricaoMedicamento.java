package br.com.sysgese.models;

import br.com.sysgese.enumerators.HorarioEnum;
import br.com.sysgese.enumerators.TipoDoseEnum;
import br.com.sysgese.enumerators.ViaAdmEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity // 🔥 ISSO AQUI ESTÁ FALTANDO OU COM PROBLEMA
@Table(name = "PRESCRICAO_MEDICAMENTO")
public class PrescricaoMedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ATENDIMENTO", nullable = false)
    private AtendimentoEnfermagem atendimento;

    @Column(name = "MEDICAMENTO", nullable = false)
    private String medicamento;

    @Column(name = "DOSE", nullable = false, precision = 10, scale = 2)
    private BigDecimal dose;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOSE", nullable = false)
    private TipoDoseEnum tipoDose;

    @Column(name = "TIPO_DOSE_OUTROS")
    private String tipoDoseOutros;

    @Enumerated(EnumType.STRING)
    @Column(name = "VIA_ADM", nullable = false)
    private ViaAdmEnum via;

    @Enumerated(EnumType.STRING)
    @Column(name = "HORARIO", nullable = false)
    private HorarioEnum horario;

    @Column(name = "HORARIO_OUTROS")
    private String horarioOutros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtendimentoEnfermagem getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(AtendimentoEnfermagem atendimento) {
        this.atendimento = atendimento;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    public TipoDoseEnum getTipoDose() {
        return tipoDose;
    }

    public void setTipoDose(TipoDoseEnum tipoDose) {
        this.tipoDose = tipoDose;
    }

    public String getTipoDoseOutros() {
        return tipoDoseOutros;
    }

    public void setTipoDoseOutros(String tipoDoseOutros) {
        this.tipoDoseOutros = tipoDoseOutros;
    }

    public ViaAdmEnum getVia() {
        return via;
    }

    public void setVia(ViaAdmEnum via) {
        this.via = via;
    }

    public HorarioEnum getHorario() {
        return horario;
    }

    public void setHorario(HorarioEnum horario) {
        this.horario = horario;
    }

    public String getHorarioOutros() {
        return horarioOutros;
    }

    public void setHorarioOutros(String horarioOutros) {
        this.horarioOutros = horarioOutros;
    }
}
