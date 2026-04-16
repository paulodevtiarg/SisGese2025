package br.com.sysgese.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.sysgese.enumerators.EstadosEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name ="SERVIDOR")
public class Servidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 255)
    private String nome;

    @Column(name = "MATRICULA", nullable = false, length = 255)
    private String matricula;

    @Column(name = "LOGIN", nullable = false, length = 20)
    private String login;

    @Column(name = "SENHA", nullable = false, length = 10)
    private String senha;

    @Column(name = "TELEFONE", length = 13)
    private String telefone;

    @Column(name = "CELULAR", length = 14)
    private String celular;

    @Column(name = "SEXO", length = 1)
    private String sexo;

    @Column(name = "LOGRADOURO", length = 200)
    private String logradouro;

    @Column(name = "CEP", length = 10)
    private String cep;
    
    @Column(name = "FOTO")
    private String foto;

    
    @Column(name = "CPF", length = 14)
    private String cpf;

    @Column(name = "BAIRRO", length = 255)
    private String bairro;

    @Column(name = "NUMERO", length = 255)
    private String numero;

    @Column(name = "CIDADE", nullable = false, length = 255)
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "UF", nullable = false, length = 2)
    private EstadosEnum uf;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    // =============================
    // RELACIONAMENTOS
    // =============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    private Perfil perfil;

    

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "DATA_CAD")
    private LocalDate dataCad;

    @Column(name = "DATA_ALT")
    private LocalDate dataAlt;
    
    
    
    @Column(name="PRIMEIRO_ACESSO")
    private Boolean primeiroAcesso;

    @Column(name="CODIGO_SEGURANCA")
    private String codigoSeguranca;
    
    
    @Column(name="CODIGO_EXPIRACAO")
    private LocalDateTime dataExpiracao;
    
    
    // =============================
    // CONTROLE AUTOMÁTICO
    // =============================

    @PrePersist
    public void prePersist() {
        this.dataCad = LocalDate.now();
        this.dataAlt = LocalDate.now();
        if (this.status == null) {
            this.status = 1;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlt = LocalDate.now();
    }
    
    
    
    
    
    
    

	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public LocalDateTime getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(LocalDateTime dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public EstadosEnum getUf() {
		return uf;
	}

	public void setUf(EstadosEnum uf) {
		this.uf = uf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

    
}