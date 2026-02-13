package br.com.sysgese.dtos;

import java.time.LocalDate;

import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.enumerators.TipoMedidaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InternacaoDTO {

    private Long id;

    @NotNull(message = "Adolescente é obrigatório")
    private Long idAdolescente;

    @NotNull(message = "Unidade é obrigatória")
    private Long idUnidade;

    @NotBlank(message = "Número do processo é obrigatório")
    @Size(max = 50)
    private String numeroProcesso;

    @NotNull(message = "Tipo de medida é obrigatório")
    private TipoMedidaEnum tipoMedida;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataPrevisaoSaida;

    private LocalDate dataSaida;

    @NotBlank(message = "Motivo é obrigatório")
    private String motivo;

    @NotNull(message = "Status é obrigatório")
    private StatusInternacaoEnum status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAdolescente() {
		return idAdolescente;
	}

	public void setIdAdolescente(Long idAdolescente) {
		this.idAdolescente = idAdolescente;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public TipoMedidaEnum getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(TipoMedidaEnum tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataPrevisaoSaida() {
		return dataPrevisaoSaida;
	}

	public void setDataPrevisaoSaida(LocalDate dataPrevisaoSaida) {
		this.dataPrevisaoSaida = dataPrevisaoSaida;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public StatusInternacaoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusInternacaoEnum status) {
		this.status = status;
	}

    // getters e setters
    
}