package br.com.sysgese.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LotacaoDTO {
	private Integer id;

    @NotNull(message = "Servidor é obrigatório")
    private Integer servidorId;

    @NotNull(message = "Unidade é obrigatória")
    private Integer unidadeId;

    @NotNull(message = "Cargo é obrigatório")
    private Integer cargoId;
    
    @NotNull(message = "Cargo é obrigatório")
    private Integer funcaoId;

    @Size(max = 500, message = "Observações deve ter no máximo 500 caracteres")
    private String observacoes;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataSaida;
    private Integer status;

    // Campos para exibição
    private String servidorNome;
    private String unidadeNome;
    private String cargoNome;
    private String funcaoNome;


    // GETTERS E SETTERS

    
    
    public Integer getId() {
        return id;
    }

    public String getFuncaoNome() {
		return funcaoNome;
	}

	public void setFuncaoNome(String funcaoNome) {
		this.funcaoNome = funcaoNome;
	}

	public Integer getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(Integer funcaoId) {
		this.funcaoId = funcaoId;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServidorId() {
        return servidorId;
    }

    public void setServidorId(Integer servidorId) {
        this.servidorId = servidorId;
    }

    public Integer getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Integer unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getServidorNome() {
        return servidorNome;
    }

    public void setServidorNome(String servidorNome) {
        this.servidorNome = servidorNome;
    }

    public String getUnidadeNome() {
        return unidadeNome;
    }

    public void setUnidadeNome(String unidadeNome) {
        this.unidadeNome = unidadeNome;
    }

    public String getCargoNome() {
        return cargoNome;
    }

    public void setCargoNome(String cargoNome) {
        this.cargoNome = cargoNome;
    }
}
