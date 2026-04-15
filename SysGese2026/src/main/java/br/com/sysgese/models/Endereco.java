package br.com.sysgese.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ENDERECO")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_ADOLESCENTE")
	private Adolescente adolescente;
	
	  @Column(name = "CEP", length = 10)
	    private String cep;

	    @Column(name = "LOGRADOURO", length = 255)
	    private String logradouro;

	    @Column(name = "NUMERO", length = 20)
	    private String numero;

	    @Column(name = "BAIRRO", length = 100)
	    private String bairro;

	    @Column(name = "CIDADE", length = 100)
	    private String cidade;

	    @Column(name = "UF", length = 2)
	    private String uf;

	    @Column(name = "REFERENCIA", length = 255)
	    private String referencia;
	    
	    @Column(name = "COMPLEMENTO", length = 255)
	    private String complemento;

	    @Column(name = "DATA_INICIO")
	    private LocalDate dataInicio;

	    @Column(name = "DATA_FIN")
	    private LocalDate dataFim;

	    @Column(name = "ATIVO")
	    private Boolean ativo;
	    
	    
	    
	    
	    
	    
	    

		public String getComplemento() {
			return complemento;
		}

		public void setComplemento(String complemento) {
			this.complemento = complemento;
		}

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

		public String getCep() {
			return cep;
		}

		public void setCep(String cep) {
			this.cep = cep;
		}

		public String getLogradouro() {
			return logradouro;
		}

		public void setLogradouro(String logradouro) {
			this.logradouro = logradouro;
		}

		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public String getBairro() {
			return bairro;
		}

		public void setBairro(String bairro) {
			this.bairro = bairro;
		}

		public String getCidade() {
			return cidade;
		}

		public void setCidade(String cidade) {
			this.cidade = cidade;
		}

		public String getUf() {
			return uf;
		}

		public void setUf(String uf) {
			this.uf = uf;
		}

		public String getReferencia() {
			return referencia;
		}

		public void setReferencia(String referencia) {
			this.referencia = referencia;
		}

		public LocalDate getDataInicio() {
			return dataInicio;
		}

		public void setDataInicio(LocalDate dataInicio) {
			this.dataInicio = dataInicio;
		}

		public LocalDate getDataFim() {
			return dataFim;
		}

		public void setDataFim(LocalDate dataFim) {
			this.dataFim = dataFim;
		}

		public Boolean getAtivo() {
			return ativo;
		}

		public void setAtivo(Boolean ativo) {
			this.ativo = ativo;
		}
	    
	    

}
