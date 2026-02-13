package br.com.sysgese.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.sysgese.enumerators.GeneroEnum;
import br.com.sysgese.enumerators.RacaCorEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class AdolescenteDTO {
	  private Long id;

	    // ==============================
	    // Dados Pessoais
	    // ==============================

	    @NotBlank(message = "Nome é obrigatório")
	    @Size(max = 255, message = "Nome não pode ter mais de 255 caracteres")
	    private String nome;

	    @Size(max = 255, message = "Apelido não pode ter mais de 255 caracteres")
	    private String apelido;

	    @Past(message = "Data de nascimento deve ser no passado")
	    private LocalDate dataNascimento;

	    @NotBlank(message = "Cidade de nascimento é obrigatória")
	    @Size(max = 255, message = "Cidade de nascimento não pode ter mais de 255 caracteres")
	    private String cidadeNascimento;

	    @NotBlank(message = "UF de nascimento é obrigatória")
	    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
	    private String ufNascimento;

	    private Boolean dataEstimada;

	    @NotNull(message = "Gênero é obrigatório")
	    private GeneroEnum genero;

	    @NotNull(message = "Raça/Cor é obrigatória")
	    private RacaCorEnum racaCor;

	    // ==============================
	    // Responsáveis
	    // ==============================

	    @Size(max = 255)
	    private String pai;

	    @Size(max = 255)
	    private String mae;

	    @Size(max = 255)
	    private String responsavel;

	    @Size(max = 255)
	    private String parentescoResponsavel;

	    // ==============================
	    // Endereço
	    // ==============================

	    @Size(max = 255)
	    private String logradouro;

	    @Size(max = 255)
	    private String bairro;

	    @Size(max = 255)
	    private String cidade;

	    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
	    private String ufEndereco;

	    @Size(max = 10)
	    private String cep;

	    @Size(max = 255)
	    private String numero;

	    @Size(max = 355)
	    private String referenciaEndereco;

	    // ==============================
	    // Contatos
	    // ==============================

	    @Size(max = 13)
	    private String telefone1;

	    @Size(max = 13)
	    private String telefone2;

	    @Size(max = 13)
	    private String telefone3;

	    @Size(max = 13)
	    private String telefoneRecado;

	    @Size(max = 255)
	    private String nomeContatoRecado;

	    // ==============================
	    // Documentos
	    // ==============================

	    @Size(max = 255)
	    private String registroCivil;

	    @Size(max = 255)
	    private String cartorio;

	    @Size(max = 255)
	    private String identidade;

	    @Size(max = 255)
	    private String cpf;

	    @Size(max = 255)
	    private String reservista;

	    @Size(max = 255)
	    private String tituloEleitor;

	    // ==============================
	    // Controle
	    // ==============================

	    @NotNull(message = "Data de cadastro é obrigatória")
	    private LocalDate dataCad;

	    @NotNull(message = "Data de alteração é obrigatória")
	    private LocalDate dataAlt;

	    @NotNull(message = "Status é obrigatório")
	    private Integer status;
	    
	    private List<CicatrizDTO> cicatrizes;
	    private List<TatuagemDTO> tatuagens;
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
		public String getApelido() {
			return apelido;
		}
		public void setApelido(String apelido) {
			this.apelido = apelido;
		}
		public LocalDate getDataNascimento() {
			return dataNascimento;
		}
		public void setDataNascimento(LocalDate dataNascimento) {
			this.dataNascimento = dataNascimento;
		}
		public String getCidadeNascimento() {
			return cidadeNascimento;
		}
		public void setCidadeNascimento(String cidadeNascimento) {
			this.cidadeNascimento = cidadeNascimento;
		}
		public String getUfNascimento() {
			return ufNascimento;
		}
		public void setUfNascimento(String ufNascimento) {
			this.ufNascimento = ufNascimento;
		}
		public Boolean getDataEstimada() {
			return dataEstimada;
		}
		public void setDataEstimada(Boolean dataEstimada) {
			this.dataEstimada = dataEstimada;
		}
		public GeneroEnum getGenero() {
			return genero;
		}
		public void setGenero(GeneroEnum genero) {
			this.genero = genero;
		}
		public RacaCorEnum getRacaCor() {
			return racaCor;
		}
		public void setRacaCor(RacaCorEnum racaCor) {
			this.racaCor = racaCor;
		}
		public String getPai() {
			return pai;
		}
		public void setPai(String pai) {
			this.pai = pai;
		}
		public String getMae() {
			return mae;
		}
		public void setMae(String mae) {
			this.mae = mae;
		}
		public String getResponsavel() {
			return responsavel;
		}
		public void setResponsavel(String responsavel) {
			this.responsavel = responsavel;
		}
		public String getParentescoResponsavel() {
			return parentescoResponsavel;
		}
		public void setParentescoResponsavel(String parentescoResponsavel) {
			this.parentescoResponsavel = parentescoResponsavel;
		}
		public String getLogradouro() {
			return logradouro;
		}
		public void setLogradouro(String logradouro) {
			this.logradouro = logradouro;
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
		public String getUfEndereco() {
			return ufEndereco;
		}
		public void setUfEndereco(String ufEndereco) {
			this.ufEndereco = ufEndereco;
		}
		public String getCep() {
			return cep;
		}
		public void setCep(String cep) {
			this.cep = cep;
		}
		public String getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = numero;
		}
		public String getReferenciaEndereco() {
			return referenciaEndereco;
		}
		public void setReferenciaEndereco(String referenciaEndereco) {
			this.referenciaEndereco = referenciaEndereco;
		}
		public String getTelefone1() {
			return telefone1;
		}
		public void setTelefone1(String telefone1) {
			this.telefone1 = telefone1;
		}
		public String getTelefone2() {
			return telefone2;
		}
		public void setTelefone2(String telefone2) {
			this.telefone2 = telefone2;
		}
		public String getTelefone3() {
			return telefone3;
		}
		public void setTelefone3(String telefone3) {
			this.telefone3 = telefone3;
		}
		public String getTelefoneRecado() {
			return telefoneRecado;
		}
		public void setTelefoneRecado(String telefoneRecado) {
			this.telefoneRecado = telefoneRecado;
		}
		public String getNomeContatoRecado() {
			return nomeContatoRecado;
		}
		public void setNomeContatoRecado(String nomeContatoRecado) {
			this.nomeContatoRecado = nomeContatoRecado;
		}
		public String getRegistroCivil() {
			return registroCivil;
		}
		public void setRegistroCivil(String registroCivil) {
			this.registroCivil = registroCivil;
		}
		public String getCartorio() {
			return cartorio;
		}
		public void setCartorio(String cartorio) {
			this.cartorio = cartorio;
		}
		public String getIdentidade() {
			return identidade;
		}
		public void setIdentidade(String identidade) {
			this.identidade = identidade;
		}
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public String getReservista() {
			return reservista;
		}
		public void setReservista(String reservista) {
			this.reservista = reservista;
		}
		public String getTituloEleitor() {
			return tituloEleitor;
		}
		public void setTituloEleitor(String tituloEleitor) {
			this.tituloEleitor = tituloEleitor;
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
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public List<CicatrizDTO> getCicatrizes() {
			return cicatrizes;
		}
		public void setCicatrizes(List<CicatrizDTO> cicatrizes) {
			this.cicatrizes = cicatrizes;
		}
		public List<TatuagemDTO> getTatuagens() {
			return tatuagens;
		}
		public void setTatuagens(List<TatuagemDTO> tatuagens) {
			this.tatuagens = tatuagens;
		}
	    
	    

}
