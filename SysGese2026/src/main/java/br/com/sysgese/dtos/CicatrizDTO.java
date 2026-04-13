package br.com.sysgese.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.sysgese.enumerators.TipoCicatrizEnum;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class CicatrizDTO {
	  
	 private Long id;

	    @NotNull(message = "Adolescente é obrigatório")
	    private Long adolescenteId;

	    @NotBlank(message = "Local da cicatriz é obrigatório")
	    @Size(max = 255, message = "Local não pode ter mais de 255 caracteres")
	    private String local;

	    @NotBlank(message = "Forma da cicatriz é obrigatória")
	    @Size(max = 255, message = "Forma não pode ter mais de 255 caracteres")
	    private String forma;

	    @Size(max = 355, message = "Descrição detalhada não pode ter mais de 355 caracteres")
	    private String descricaoDetalhe;

	    @NotNull(message = "Tamanho em cm é obrigatório")
	    @DecimalMin(value = "0.01", message = "Tamanho deve ser maior que 0")
	    @DecimalMax(value = "999.99", message = "Tamanho não pode ser maior que 999.99 cm")
	    private BigDecimal tamanhoCm;

	    @PastOrPresent(message = "Data de ocorrência não pode ser futura")
	    private LocalDate dataOcorrido;

	    private TipoCicatrizEnum tipo;

	    @NotBlank(message = "Foto da cicatriz é obrigatória")
	    private String foto;

	public Long getAdolescenteId() {
			return adolescenteId;
		}

		public void setAdolescenteId(Long adolescenteId) {
			this.adolescenteId = adolescenteId;
		}

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

	public String getDescricaoDetalhe() {
		return descricaoDetalhe;
	}

	public void setDescricaoDetalhe(String descricaoDetalhe) {
		this.descricaoDetalhe = descricaoDetalhe != null ? descricaoDetalhe.toUpperCase() : null;
	}

	public BigDecimal getTamanhoCm() {
		return tamanhoCm;
	}

	public void setTamanhoCm(BigDecimal tamanhoCm) {
		this.tamanhoCm = tamanhoCm;
	}

	public LocalDate getDataOcorrido() {
		return dataOcorrido;
	}

	public void setDataOcorrido(LocalDate dataOcorrido) {
		this.dataOcorrido = dataOcorrido;
	}

	public TipoCicatrizEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoCicatrizEnum tipo) {
		this.tipo = tipo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


    
    
    
    
    
    
}
