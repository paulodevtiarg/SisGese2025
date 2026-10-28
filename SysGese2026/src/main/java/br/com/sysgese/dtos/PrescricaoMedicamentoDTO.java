package br.com.sysgese.dtos;

import br.com.sysgese.enumerators.HorarioEnum;
import br.com.sysgese.enumerators.TipoDoseEnum;
import br.com.sysgese.enumerators.ViaAdmEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PrescricaoMedicamentoDTO {

    private Long id;
    @NotBlank(message = "O nome do medicamento é obrigatório")
    private String medicamento;
    @NotNull(message = "A dose é obrigatória")
    @Digits(integer = 8, fraction = 2, message = "Dose inválida")
    @DecimalMin(value = "0.01", message = "A dose deve ser maior que zero")
    private BigDecimal dose;
    @NotNull(message = "O tipo de dose é obrigatório")
    private TipoDoseEnum tipoDose;
    private String tipoDoseOutros;
    @NotNull(message = "A via de administração é obrigatória")
    private ViaAdmEnum via;
    @NotNull(message = "O horário é obrigatório")
    private HorarioEnum horario;
    private String horarioOutros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
