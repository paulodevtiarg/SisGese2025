package br.com.sysgese.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class FuncaoDTO {

    private Long id;
    @NotBlank(message = "Descrição da Função é obrigatório")
    private String descricao;
    private Integer status;
    private LocalDateTime dataCad;
    private LocalDateTime dataAlt;
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
	public LocalDateTime getDataCad() {
		return dataCad;
	}
	public void setDataCad(LocalDateTime dataCad) {
		this.dataCad = dataCad;
	}
	public LocalDateTime getDataAlt() {
		return dataAlt;
	}
	public void setDataAlt(LocalDateTime dataAlt) {
		this.dataAlt = dataAlt;
	}

    // getters e setters
    
}
