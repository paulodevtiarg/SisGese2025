package br.com.sysgese.models;

import java.time.LocalDate;
import java.util.ArrayList;
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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="ADOLESCENTE")
public class Adolescente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOME", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "APELIDO", nullable = false, length = 255)
    private String apelido;
    
    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
    
    @Column(name = "CIDADE_NASCIMENTO", nullable = false, length = 255)
    private String cidadeNascimento;
    
    @Column(name = "UF_NASCIMENTO", nullable = false, length = 2)
    private String ufNascimento;
    
    @Column(name = "DATA_ESTIMADA")
    private Boolean dataEstimada;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO", nullable = false, columnDefinition = "ENUM('MASCULINO','FEMININO','TRANS_MASCULINO','TRANS_FEMININO','NAO_INFORMADO','OUTRO')")
    private GeneroEnum genero = GeneroEnum.NAO_INFORMADO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "RACA_COR", nullable = false, columnDefinition = "ENUM('BRANCO','NEGRO','PARDO','AMARELO','INDIGENA','OUTRO','NAO_INFORMADO')")
    private RacaCorEnum racaCor = RacaCorEnum.NAO_INFORMADO;
    
    @Column(name = "PAI", length = 255)
    private String pai;
    
    @Column(name = "MAE", length = 255)
    private String mae;
    
    @Column(name = "RESPONSAVEL", length = 255)
    private String responsavel;
    
    @Column(name = "PARENTESCO_RESPONSAVEL", length = 255)
    private String parentescoResponsavel;
    
    /*ADICIONAR AO DTO*/
    @Enumerated(EnumType.STRING)
    @Column(name="ESTATURA")
    private EstaturaEnum estatura;
    
    @Enumerated(EnumType.STRING)
    @Column(name="BIOFISICO")
    private BiofisicoEnum biofisico;
    
    @Enumerated(EnumType.STRING)
    @Column(name="NIVELESCOLARIDADE")
    private NivelEscolaridadeEnum nivelEscolaridade;
    
    @Enumerated(EnumType.STRING)
    @Column(name="ANOESCOLARIDADE")
    private AnoEscolaridadeEnum anoEscolaridade;
    
    @Enumerated(EnumType.STRING)
    @Column(name="SOBRANCELHA")
    private SobrancelhaEnum sobrancelha;
    
    @Enumerated(EnumType.STRING)
    @Column(name="NARIZ")
    private NarizEnum nariz;
    
    @Column(name="OBS_NARIZ")
    private String obsNariz;
    
    @Column(name="COND_SAUDE")
    private String condSaude;
    
    
    @Column(name="CARAC_MARCANTE")
    private boolean caracMarcante;
    
    @Column(name="DESCRICAO_CARAC")
    private String descricaoCarac;
    
    @Enumerated(EnumType.STRING)
    @Column(name="ORELHA")
    private OrelhaEnum orelha;
    
    @Enumerated(EnumType.STRING)
    @Column(name="PESCOCO")
    private PescocoEnum pescoco;
    
    @Enumerated(EnumType.STRING)
    @Column(name="ROSTO")
    private RostoEnum rosto;
    
    @Enumerated(EnumType.STRING)
    @Column(name="OLHOS")
    private OlhosEnum olhos;
    
    @Enumerated(EnumType.STRING)
    @Column(name="COR_OLHOS")
    private CorOlhosEnum corOlhos;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name="CABELO")
    private CabeloEnum cabelo;
    

    @Column(name="DENTES_OBS")
    private String dentesObs;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name="RELIGIAO")
    private ReligiaoEnum religiao;
    
    @Column(name="FILHOS")
    private boolean filhos;
    
    @Column(name="QTD_FILHOS")
    private Integer qtdFilhos;
    
    
    @Column(name = "TELEFONE1", length = 13)
    private String telefone1;
    
    @Column(name = "TELEFONE2", length = 13)
    private String telefone2;
    
     
    @Column(name = "TELEFONE_RECADO", length = 13)
    private String telefoneRecado;
    
    @Column(name = "NOME_CONTATO_RECADO", length = 255)
    private String nomeContatoRecado;
    
    @Column(name = "REGISTRO_CIVIL", length = 255)
    private String registroCivil;
    
    @Column(name = "CARTORIO", length = 255)
    private String cartorio;
    
    @Column(name = "IDENTIDADE", length = 255)
    private String identidade;
    
    @Column(name = "CPF", length = 255)
    private String cpf;
    
    @Column(name = "RESERVISTA", length = 255)
    private String reservista;
    
    @Column(name = "TITULO_ELEITOR", length = 255)
    private String tituloEleitor;
    
    @Column(name = "DATA_CAD", nullable = false)
    private LocalDate dataCad;
    
    @Column(name = "DATA_ALT", nullable = false)
    private LocalDate dataAlt;
    
    @Column(name = "STATUS", nullable = false)
    private Boolean status;
    
    @Column(name = "FOTO_REGISTRO")
    private String fotoRegistro;
    
    @Column(name = "ZONA_ELEITORAL")
    private String zonaEleitoral;
    
    @Column(name = "SECAO_ELEITORAL")
    private String secaoEleitoral;
    
    @Column(name = "MUNICIPIO_ELEITORAL")
    private String municipioEleitoral;

    @Column(name = "UF_ELEITORAL")
    private String ufEleitoral;
    
    
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Endereco> enderecos;
    
    
    
    
    
    public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCondSaude() {
		return condSaude;
	}

	public void setCondSaude(String condSaude) {
		this.condSaude = condSaude;
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
		this.secaoEleitoral = secaoEleitoral;
	}

	public String getMunicipioEleitoral() {
		return municipioEleitoral;
	}

	public void setMunicipioEleitoral(String municipioEleitoral) {
		this.municipioEleitoral = municipioEleitoral;
	}

	public String getUfEleitoral() {
		return ufEleitoral;
	}

	public void setUfEleitoral(String ufEleitoral) {
		this.ufEleitoral = ufEleitoral;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	//Histórico: VAMOS SABER QUAL UNIDADE O CADASTROU
    @Column(name = "ID_UNIDADE_CADASTRO")
    private Long idUnidadeCadastro;
    
    
    
    public Long getIdUnidadeCadastro() {
		return idUnidadeCadastro;
	}

	public void setIdUnidadeCadastro(Long idUnidadeCadastro) {
		this.idUnidadeCadastro = idUnidadeCadastro;
	}

	// Relacionamentos
    @OneToMany(mappedBy = "adolescente", 
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Cicatriz> cicatrizes = new ArrayList<>();
    
    @OneToMany(mappedBy = "adolescente", 
            orphanRemoval = true,
            fetch = FetchType.LAZY)
 private List<Tatuagem> tatuagens = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "adolescente", 
            orphanRemoval = true,
            fetch = FetchType.LAZY)
 private List<Foto> fotos = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "adolescente",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
 private List<Internacao> internacoes = new ArrayList<>();
    
    public List<Internacao> getInternacoes() {
        return internacoes;
    }

    public void setInternacoes(List<Internacao> internacoes) {
        this.internacoes = internacoes;
    }
    
    public void adicionarInternacao(Internacao internacao) {
        internacoes.add(internacao);
        internacao.setAdolescente(this);
    }

    public void removerInternacao(Internacao internacao) {
        internacoes.remove(internacao);
        internacao.setAdolescente(null);
    }
     

 // Adiciona métodos auxiliares
 public void adicionarFoto(Foto foto) {
     fotos.add(foto);
     foto.setAdolescente(this);
 }

 public void removerFoto(Foto foto) {
     fotos.remove(foto);
     foto.setAdolescente(null);
 }

 public List<Foto> getFotos() {
     return fotos;
 }

 public void setFotos(List<Foto> fotos) {
     this.fotos = fotos;
 }
    
    
    
    // Construtores
    public Adolescente() {
        this.dataCad = LocalDate.now();
        this.dataAlt = LocalDate.now();
        this.status = true; // Ativo por padrão
    }
    
    // Métodos auxiliares para manter consistência nas relações
    public void adicionarCicatriz(Cicatriz cicatriz) {
        cicatrizes.add(cicatriz);
        cicatriz.setAdolescente(this);
    }
    
    public void removerCicatriz(Cicatriz cicatriz) {
        cicatrizes.remove(cicatriz);
        cicatriz.setAdolescente(null);
    }
    
    public void adicionarTatuagem(Tatuagem tatuagem) {
        tatuagens.add(tatuagem);
        tatuagem.setAdolescente(this);
    }
    
    public void removerTatuagem(Tatuagem tatuagem) {
        tatuagens.remove(tatuagem);
        tatuagem.setAdolescente(null);
    }
    
    // Método para atualizar data de alteração
    @PreUpdate
    public void preUpdate() {
        this.dataAlt = LocalDate.now();
    }
    
    // Getters e Setters
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
    

    
    public List<Cicatriz> getCicatrizes() {
        return cicatrizes;
    }
    
    public void setCicatrizes(List<Cicatriz> cicatrizes) {
        this.cicatrizes = cicatrizes;
    }
    
    public List<Tatuagem> getTatuagens() {
        return tatuagens;
    }
    
    public void setTatuagens(List<Tatuagem> tatuagens) {
        this.tatuagens = tatuagens;
    }
    
    
    
    public String getFotoRegistro() {
		return fotoRegistro;
	}

	public void setFotoRegistro(String fotoRegistro) {
		this.fotoRegistro = fotoRegistro;
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
		this.obsNariz = obsNariz;
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
		this.descricaoCarac = descricaoCarac;
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
		this.dentesObs = dentesObs;
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
	
	
	

	public CorOlhosEnum getCorOlhos() {
		return corOlhos;
	}

	public void setCorOlhos(CorOlhosEnum corOlhos) {
		this.corOlhos = corOlhos;
	}

	@Override
    public String toString() {
        return "Adolescente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                '}';
    }
	
	

}
