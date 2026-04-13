package br.com.sysgese.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FotoDTO {
    private Long id;
    @NotBlank(message = "Descrição  é obrigatório")
    private String descricaoDetalhe;
    
    @NotBlank(message = "Foto  é obrigatório")
    private String foto;

    
    
    
    @NotNull(message = "Adolescente é obrigatório")
    private Long adolescenteId;
    
    
    
    public Long getAdolescenteId() {
		return adolescenteId;
	}

	public void setAdolescenteId(Long adolescenteId) {
		this.adolescenteId = adolescenteId;
	}

	// Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricaoDetalhe() {
        return descricaoDetalhe;
    }

    public void setDescricaoDetalhe(String descricaoDetalhe) {
        this.descricaoDetalhe = descricaoDetalhe  != null ? descricaoDetalhe.toUpperCase() : null;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
