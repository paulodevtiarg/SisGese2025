package br.com.sysgese.dtos;

import br.com.sysgese.enumerators.TipoFuncaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FotoDTO {
    private Long id;
    @NotBlank(message = "Descrição  é obrigatório")
    private String descricaoDetalhe;

    @NotNull(message = "Tipo da Função é Obrigatório")
    private TipoFuncaoEnum chaveSistema;

    private String foto;

    
    @NotNull(message = "Adolescente é obrigatório")
    private Long adolescenteId;
    
    public boolean isVazio() {
        return (descricaoDetalhe == null || descricaoDetalhe.isBlank());
    }
    
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
