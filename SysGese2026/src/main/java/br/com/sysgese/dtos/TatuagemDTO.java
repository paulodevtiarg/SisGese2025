package br.com.sysgese.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TatuagemDTO {
private Long id;
    
    @NotBlank(message = "Local da tatuagem é obrigatório")
    @Size(max = 255, message = "Local não pode ter mais de 255 caracteres")
    private String local;
    
    @NotBlank(message = "Forma da tatuagem é obrigatória")
    @Size(max = 255, message = "Forma não pode ter mais de 255 caracteres")
    private String forma;
    
    @NotBlank(message = "Descrição da tatuagem é obrigatória")
    private String descricao;
    
    @NotNull(message = "Informe se tem significado")
    private Boolean temSignificado;
    
    private String qualSignificado;
    
    @Min(value = 1900, message = "Ano de realização deve ser maior que 1900")
    @Max(value = 2100, message = "Ano de realização não pode ser maior que 2100")
    private Integer anoRealizacao;
    
    @NotNull(message = "Informe se foi feita por profissional")
    private Boolean profissional;
    
    @NotNull(message = "Informe se há interesse em remover")
    private Boolean interesseRemover;
    
    
    @NotNull(message = "Adolescente é obrigatório")
    private Long adolescenteId;
    
    
    public Long getAdolescenteId() {
		return adolescenteId;
	}

	public void setAdolescenteId(Long adolescenteId) {
		this.adolescenteId = adolescenteId;
	}
	  @NotBlank(message = "Foto da tatuagem é obrigatória")
	private String foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local != null ? local.toUpperCase() : null;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma != null ? forma.toUpperCase() : null;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao != null ? descricao.toUpperCase() : null;
	}

	public Boolean getTemSignificado() {
		return temSignificado;
	}

	public void setTemSignificado(Boolean temSignificado) {
		this.temSignificado = temSignificado;
	}

	public String getQualSignificado() {
		return qualSignificado;
	}

	public void setQualSignificado(String qualSignificado) {
		this.qualSignificado = qualSignificado  != null ? qualSignificado.toUpperCase() : null;
	}

	public Integer getAnoRealizacao() {
		return anoRealizacao;
	}

	public void setAnoRealizacao(Integer anoRealizacao) {
		this.anoRealizacao = anoRealizacao;
	}

	public Boolean getProfissional() {
		return profissional;
	}

	public void setProfissional(Boolean profissional) {
		this.profissional = profissional;
	}

	public Boolean getInteresseRemover() {
		return interesseRemover;
	}

	public void setInteresseRemover(Boolean interesseRemover) {
		this.interesseRemover = interesseRemover;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}



    
}
