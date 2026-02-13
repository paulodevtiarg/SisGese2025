package br.com.sysgese.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class CoordenacaoDTO {

    private Long id;

    @NotNull(message = "Servidor é obrigatório")
    private Long servidorId;

    @NotNull(message = "Unidade é obrigatória")
    private Long unidadeId;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicioCoordenacao;

    @NotNull(message = "Data de fim é obrigatória")
    private LocalDate dataFimCoordenacao;

    @NotNull(message = "Status é obrigatório")
    private Integer status;

    private LocalDate dataCad;
    private LocalDate dataAlt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getServidorId() {
		return servidorId;
	}
	public void setServidorId(Long servidorId) {
		this.servidorId = servidorId;
	}
	public Long getUnidadeId() {
		return unidadeId;
	}
	public void setUnidadeId(Long unidadeId) {
		this.unidadeId = unidadeId;
	}
	public LocalDate getDataInicioCoordenacao() {
		return dataInicioCoordenacao;
	}
	public void setDataInicioCoordenacao(LocalDate dataInicioCoordenacao) {
		this.dataInicioCoordenacao = dataInicioCoordenacao;
	}
	public LocalDate getDataFimCoordenacao() {
		return dataFimCoordenacao;
	}
	public void setDataFimCoordenacao(LocalDate dataFimCoordenacao) {
		this.dataFimCoordenacao = dataFimCoordenacao;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public LocalDate getDataCad() {
		return dataCad;
	}
	public void setDataCad(LocalDate dataCad) {
		this.dataCad = dataCad;
	}
	public LocalDate getDataAlt() {
		return dataAlt;
	}
	public void setDataAlt(LocalDate dataAlt) {
		this.dataAlt = dataAlt;
	}
    
    
    
    
}