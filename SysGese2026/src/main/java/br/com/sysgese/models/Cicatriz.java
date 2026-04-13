package br.com.sysgese.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.sysgese.enumerators.TipoCicatrizEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CICATRIZ")
public class Cicatriz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADOLESCENTE_ID", nullable = false)
    private Adolescente adolescente;
	
	@Column(name = "LOCAL", nullable = false, length = 255)
    private String local;
	
	@Column(name = "FORMA", nullable = false, length = 255)
    private String forma;
    
    @Column(name = "DESCRICAO_DETALHE", length = 355)
    private String descricaoDetalhe;
    
    @Column(name = "TAMANHO_CM", nullable = false, precision = 5, scale = 2)
    private BigDecimal tamanhoCm;
    
    @Column(name = "DATA_OCORRIDO")
    private LocalDate dataOcorrido;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", columnDefinition = "ENUM('CIRURGICA','ACIDENTE','AUTOINFLIGIDA','OUTRO')")
    private TipoCicatrizEnum tipo;
    
   
    @Column(name = "FOTO")
    private String foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Adolescente getAdolescente() {
		return adolescente;
	}

	public void setAdolescente(Adolescente adolescente) {
		this.adolescente = adolescente;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public String getDescricaoDetalhe() {
		return descricaoDetalhe;
	}

	public void setDescricaoDetalhe(String descricaoDetalhe) {
		this.descricaoDetalhe = descricaoDetalhe;
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
