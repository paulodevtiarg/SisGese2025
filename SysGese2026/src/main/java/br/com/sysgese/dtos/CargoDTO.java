package br.com.sysgese.dtos;

import java.time.LocalDateTime;


import jakarta.validation.constraints.NotBlank;

public class CargoDTO {
	
    private Long id;
 
    @NotBlank(message = "Descrição do Cargo é obrigatório")
    private String descricao;

   
    private Integer status;

   
    private LocalDateTime dataCad;

  
    private LocalDateTime dataAlt;

	//filtro:
	private String filtroDescricao;
	private String filtroStatus;
	private Integer size = 10;

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


	public String getFiltroDescricao() {
		return filtroDescricao;
	}

	public void setFiltroDescricao(String filtroDescricao) {
		this.filtroDescricao = filtroDescricao;
	}

	public String getFiltroStatus() {
		return filtroStatus;
	}

	public void setFiltroStatus(String filtroStatus) {
		this.filtroStatus = filtroStatus;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
