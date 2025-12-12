package br.com.sysgese.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TATUAGEM")
public class Tatuagem {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ADOLESCENTE_ID", nullable = false)
	    private Adolescente adolescente;
	    
	    @Column(name = "LOCAL", nullable = false, length = 255)
	    private String local;
	    
	    @Column(name = "FORMA", nullable = false, length = 255)
	    private String forma;
	    
	    @Lob
	    @Column(name = "DESCRICAO", nullable = false)
	    private String descricao;
	    
	    @Column(name = "TEM_SIGNIFICADO", nullable = false)
	    private Boolean temSignificado = false;
	    
	    @Lob
	    @Column(name = "QUAL_SIGNIFICADO")
	    private String qualSignificado;
	    
	    @Column(name = "ANO_REALIZACAO")
	    private Integer anoRealizacao;
	    
	    @Column(name = "PROFISSIONAL", nullable = false)
	    private Boolean profissional = false;
	    
	    @Column(name = "INTERESSE_REMOVER", nullable = false)
	    private Boolean interesseRemover = false;
	    
	    @Lob
	    @Column(name = "FOTO")
	    private byte[] foto;

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

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
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
			this.qualSignificado = qualSignificado;
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

		public byte[] getFoto() {
			return foto;
		}

		public void setFoto(byte[] foto) {
			this.foto = foto;
		}
	    
	    
	    
	    


}
