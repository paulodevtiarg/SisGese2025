package br.com.sysgese.dtos;

import java.time.LocalDate;

public class PerfilDTO {

    private Long id;
    private String descricao;
    private Integer status;
    private LocalDate dataCad;
    private LocalDate dataAlt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

    // getters e setters
    
}
