package br.com.sysgese.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.sysgese.enumerators.AnoEscolaridadeEnum;
import br.com.sysgese.enumerators.BiofisicoEnum;
import br.com.sysgese.enumerators.CabeloEnum;
import br.com.sysgese.enumerators.CorOlhosEnum;
import br.com.sysgese.enumerators.EstaturaEnum;
import br.com.sysgese.enumerators.GeneroEnum;
import br.com.sysgese.enumerators.NarizEnum;
import br.com.sysgese.enumerators.NivelEscolaridadeEnum;
import br.com.sysgese.enumerators.OlhosEnum;
import br.com.sysgese.enumerators.OrelhaEnum;
import br.com.sysgese.enumerators.PescocoEnum;
import br.com.sysgese.enumerators.RacaCorEnum;
import br.com.sysgese.enumerators.ReligiaoEnum;
import br.com.sysgese.enumerators.RostoEnum;
import br.com.sysgese.enumerators.SobrancelhaEnum;
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
	    @NotNull(message = "Data de nascimento é obrigatória")
	    private LocalDate dataNascimento;

	    @NotBlank(message = "Cidade de nascimento é obrigatória")
	    @Size(max = 255, message = "Cidade de nascimento não pode ter mais de 255 caracteres")
	    private String cidadeNascimento;

	    @NotBlank(message = "UF de nascimento é obrigatória")
	    private String ufNascimento;

	  
	    private String fotoRegistro;
	    
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


private EnderecoDTO enderecoAtual;


private List<EnderecoDTO> enderecos;

	    // ==============================
	    // Contatos
	    // ==============================

	  
	    private String telefone1;

	   
	    private String telefone2;



	  
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
	    
	    private String zonaEleitoral;
	    
	    private String secaoEleitoral;
	    
	    private String municipioEleitoral;

	    
	    private String ufEleitoral;
	    
	    // ==============================
	    // Controle
	    // ==============================

	  
	    private LocalDate dataCad;

	  
	    private LocalDate dataAlt;

	 
	    private Boolean status;
	    
	    private Long idUnidadeCadastro;
	    
	    private String nomeUnidadeCadastro;
	    
	    private List<CicatrizDTO> cicatrizes;
	    private List<TatuagemDTO> tatuagens;	    
	    private List<FotoDTO> fotos;
	    	       	    
	  
	    private EstaturaEnum estatura;
	    	 
	    private BiofisicoEnum biofisico;
	    	  
	    private NivelEscolaridadeEnum nivelEscolaridade;
	    	   
	    private AnoEscolaridadeEnum anoEscolaridade;
	    	 
	    private SobrancelhaEnum sobrancelha;
	    	  
	    private NarizEnum nariz;
	    	   
	    private String obsNariz;
	    	    
	    private boolean caracMarcante;
	    	   
	    private String descricaoCarac;
	    	   
	    private OrelhaEnum orelha;
	    	    
	    private PescocoEnum pescoco;
	    	   
	    private RostoEnum rosto;
	    	    
	    private OlhosEnum olhos;
	    
	    private CorOlhosEnum corOlhos;
	    	   
	    private CabeloEnum cabelo;
	    	    
	    private String dentesObs;
	      	 
	    private ReligiaoEnum religiao;
	    
	    private String condSaude;
	    	  
	    private boolean filhos;
	  
	    private Integer qtdFilhos;
	    
		/*idade*/
		@SuppressWarnings("unused")
		private Integer idade;
		
		
		@SuppressWarnings("unused")
		private String anoEscolaridadeDescricao;
		
	   @SuppressWarnings("unused")
	private String nivelEscolaridadeDescricao;

		/* CAMPOS DE FILTROS*/
		private String filtroNome;
		private String filtroApelido;
		private String filtroCidade;
        private String filtroCpf;
        private Long filtroUnidadeId;
        private Integer idadeMin;
        private Integer idadeMax;
        private String filtroStatus;
        private Integer size = 10;
		
		
		
		



	 // Getters e Setters
        
        
        
        
        
        
		
		public Integer getIdade() {
		    if (this.dataNascimento == null) {
		        return null;
		    }
		    return java.time.Period.between(this.dataNascimento, java.time.LocalDate.now()).getYears();
		}
	
		
	    
	 public List<EnderecoDTO> getEnderecos() {
			return enderecos;
		}



		public void setEnderecos(List<EnderecoDTO> enderecos) {
			this.enderecos = enderecos;
		}



	 public String getFiltroNome() {
			return filtroNome;
		}



		public void setFiltroNome(String filtroNome) {
			this.filtroNome = filtroNome;
		}



	 public String getFiltroApelido() {
			return filtroApelido;
		}


		public void setFiltroApelido(String filtroApelido) {
			this.filtroApelido = filtroApelido;
		}


		public String getFiltroCidade() {
			return filtroCidade;
		}


		public void setFiltroCidade(String filtroCidade) {
			this.filtroCidade = filtroCidade;
		}


		public String getFiltroCpf() {
			return filtroCpf;
		}


		public void setFiltroCpf(String filtroCpf) {
			this.filtroCpf = filtroCpf;
		}


		public Long getFiltroUnidadeId() {
			return filtroUnidadeId;
		}


		public void setFiltroUnidadeId(Long filtroUnidadeId) {
			this.filtroUnidadeId = filtroUnidadeId;
		}


		public Integer getIdadeMin() {
			return idadeMin;
		}


		public void setIdadeMin(Integer idadeMin) {
			this.idadeMin = idadeMin;
		}


		public Integer getIdadeMax() {
			return idadeMax;
		}


		public void setIdadeMax(Integer idadeMax) {
			this.idadeMax = idadeMax;
		}


		public String getFiltroStatus() {
			return filtroStatus;
		}


		public void setFiltroStatus(String filtroStatus) {
			this.filtroStatus = filtroStatus;
		}


		public Integer getSize() {
			return size;
		}


		public void setSize(Integer size) {
			this.size = size;
		}


	 public CorOlhosEnum getCorOlhos() {
			return corOlhos;
		}


		public String getNomeUnidadeCadastro() {
		return nomeUnidadeCadastro;
	}


	 public void setNomeUnidadeCadastro(String nomeUnidadeCadastro) {
		 this.nomeUnidadeCadastro = nomeUnidadeCadastro;
	 }


		public String getCondSaude() {
		return condSaude;
	}


	 public void setCondSaude(String condSaude) {
		 this.condSaude = condSaude;
	 }


		public void setCorOlhos(CorOlhosEnum corOlhos) {
			this.corOlhos = corOlhos;
		}


	 public List<FotoDTO> getFotos() {
	     return fotos;
	 }

	 public Long getIdUnidadeCadastro() {
		return idUnidadeCadastro;
	}

	 public void setIdUnidadeCadastro(Long idUnidadeCadastro) {
		 this.idUnidadeCadastro = idUnidadeCadastro;
	 }

	

	 public String getFotoRegistro() {
		return fotoRegistro;
	}

	 public void setFotoRegistro(String fotoRegistro) {
		 this.fotoRegistro = fotoRegistro;
	 }

	 public void setFotos(List<FotoDTO> fotos) {
	     this.fotos = fotos;
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
			this.nome =nome != null ? nome.toUpperCase() : null;
		}
		public String getApelido() {
			return apelido;
		}
		public void setApelido(String apelido) {
			this.apelido = apelido != null ? apelido.toUpperCase() : null;
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
			this.cidadeNascimento = cidadeNascimento != null ? cidadeNascimento.toUpperCase() : null;
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
			this.pai = pai  != null ? pai.toUpperCase() : null;
		}
		public String getMae() {
			return mae;
		}
		public void setMae(String mae) {
			this.mae = mae != null ? mae.toUpperCase() : null;
		}
		public String getResponsavel() {
			return responsavel;
		}
		public void setResponsavel(String responsavel) {
			this.responsavel = responsavel  != null ? responsavel.toUpperCase() : null;
		}
		public String getParentescoResponsavel() {
			return parentescoResponsavel;
		}
		public void setParentescoResponsavel(String parentescoResponsavel) {
			this.parentescoResponsavel = parentescoResponsavel  != null ? parentescoResponsavel.toUpperCase() : null;
		}
		
		public EnderecoDTO getEnderecoAtual() {
			return enderecoAtual;
		}

		public void setEnderecoAtual(EnderecoDTO enderecoAtual) {
			this.enderecoAtual = enderecoAtual;
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
			this.nomeContatoRecado = nomeContatoRecado  != null ? nomeContatoRecado.toUpperCase() : null;
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
			this.cartorio = cartorio != null ? cartorio.toUpperCase() : null;
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

		
		
		
		
		public Boolean getStatus() {
			return status;
		}



		public void setStatus(Boolean status) {
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

		public String getZonaEleitoral() {
			return zonaEleitoral;
		}

		public void setZonaEleitoral(String zonaEleitoral) {
			this.zonaEleitoral = zonaEleitoral;
		}

		public String getSecaoEleitoral() {
			return secaoEleitoral;
		}

		public void setSecaoEleitoral(String secaoEleitoral) {
			this.secaoEleitoral = secaoEleitoral ;
		}

		public String getMunicipioEleitoral() {
			return municipioEleitoral != null ? municipioEleitoral.toUpperCase() : null;
		}

		public void setMunicipioEleitoral(String municipioEleitoral) {
			this.municipioEleitoral = municipioEleitoral  != null ? municipioEleitoral.toUpperCase() : null;
		}

		public String getUfEleitoral() {
			return ufEleitoral;
		}

		public void setUfEleitoral(String ufEleitoral) {
			this.ufEleitoral = ufEleitoral;
		}

		public EstaturaEnum getEstatura() {
			return estatura;
		}

		public void setEstatura(EstaturaEnum estatura) {
			this.estatura = estatura;
		}

		public BiofisicoEnum getBiofisico() {
			return biofisico;
		}

		public void setBiofisico(BiofisicoEnum biofisico) {
			this.biofisico = biofisico;
		}

		public NivelEscolaridadeEnum getNivelEscolaridade() {
			return nivelEscolaridade;
		}

		public void setNivelEscolaridade(NivelEscolaridadeEnum nivelEscolaridade) {
			this.nivelEscolaridade = nivelEscolaridade;
		}

		public AnoEscolaridadeEnum getAnoEscolaridade() {
			return anoEscolaridade;
		}
		
		public String getNivelEscolaridadeDescricao() {
			
			return nivelEscolaridade != null ? nivelEscolaridade.getDescricao():"";
			
		}
		
		public String getAnoEscolaridadeDescricao() {
		    return anoEscolaridade != null ? anoEscolaridade.getDescricao() : "";
		}
		

		public void setAnoEscolaridade(AnoEscolaridadeEnum anoEscolaridade) {
			this.anoEscolaridade = anoEscolaridade;
		}

		public SobrancelhaEnum getSobrancelha() {
			return sobrancelha;
		}

		public void setSobrancelha(SobrancelhaEnum sobrancelha) {
			this.sobrancelha = sobrancelha;
		}

		public NarizEnum getNariz() {
			return nariz;
		}

		public void setNariz(NarizEnum nariz) {
			this.nariz = nariz;
		}

		public String getObsNariz() {
			return obsNariz;
		}

		public void setObsNariz(String obsNariz) {
			this.obsNariz = obsNariz  != null ? obsNariz.toUpperCase() : null;
		}

		public boolean isCaracMarcante() {
			return caracMarcante;
		}

		public void setCaracMarcante(boolean caracMarcante) {
			this.caracMarcante = caracMarcante;
		}

		public String getDescricaoCarac() {
			return descricaoCarac;
		}

		public void setDescricaoCarac(String descricaoCarac) {
			this.descricaoCarac = descricaoCarac  != null ? descricaoCarac.toUpperCase() : null;
		}

		public OrelhaEnum getOrelha() {
			return orelha;
		}

		public void setOrelha(OrelhaEnum orelha) {
			this.orelha = orelha;
		}

		public PescocoEnum getPescoco() {
			return pescoco;
		}

		public void setPescoco(PescocoEnum pescoco) {
			this.pescoco = pescoco;
		}

		public RostoEnum getRosto() {
			return rosto;
		}

		public void setRosto(RostoEnum rosto) {
			this.rosto = rosto;
		}

		public OlhosEnum getOlhos() {
			return olhos;
		}

		public void setOlhos(OlhosEnum olhos) {
			this.olhos = olhos;
		}

		public CabeloEnum getCabelo() {
			return cabelo;
		}

		public void setCabelo(CabeloEnum cabelo) {
			this.cabelo = cabelo;
		}

		public String getDentesObs() {
			return dentesObs;
		}

		public void setDentesObs(String dentesObs) {
			this.dentesObs = dentesObs != null ? dentesObs.toUpperCase() : null;
		}

		public ReligiaoEnum getReligiao() {
			return religiao;
		}

		public void setReligiao(ReligiaoEnum religiao) {
			this.religiao = religiao;
		}

		public boolean isFilhos() {
			return filhos;
		}

		public void setFilhos(boolean filhos) {
			this.filhos = filhos;
		}

		public Integer getQtdFilhos() {
			return qtdFilhos;
		}

		public void setQtdFilhos(Integer qtdFilhos) {
			this.qtdFilhos = qtdFilhos;
		}
	    
		
	    

}
