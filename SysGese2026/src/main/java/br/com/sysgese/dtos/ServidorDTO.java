package br.com.sysgese.dtos;

import java.time.LocalDate;

import br.com.sysgese.enumerators.EstadosEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ServidorDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 255)
    private String matricula;

    @NotBlank(message = "Login é obrigatório")
    @Size(max = 20)
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 10)
    private String senha;
    
    @NotBlank(message = "Cpf é obrigatório")
    @Size(max = 14)
    private String cpf;

    @Size(max = 13)
    private String telefone;

    @Size(max = 14)
    private String celular;

    @Size(max = 1, message = "Sexo deve ter 1 caractere")
    private String sexo;

    @Size(max = 200)
    private String logradouro;

    @Size(max = 10)
    private String cep;

    @Size(max = 255)
    private String bairro;

    @Size(max = 255)
    private String numero;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 255)
    private String cidade;

    @NotNull(message = "UF é obrigatória")
    private EstadosEnum uf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255)
    private String email;

    @NotNull(message = "Perfil é obrigatório")
    private Long perfilId;

    private String foto;

    @NotNull(message = "Status é obrigatório")
    private Integer status;

    private LocalDate dataCad;
    private LocalDate dataAlt;
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
	public Long getPerfilId() {
		return perfilId;
	}
	public void setPerfilId(Long perfilId) {
		this.perfilId = perfilId;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}


   
}
