package br.com.sysgese.dtos;

import java.time.LocalDate;

import br.com.sysgese.enumerators.DocumentosEnum;
import br.com.sysgese.enumerators.MotivoEnum;
import br.com.sysgese.enumerators.ProcedenciaEnum;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.enumerators.TipoMedidaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InternacaoDTO {

    private Long id;

    @NotNull(message = "Adolescente é obrigatório")
    private Long idAdolescente;

    @NotNull(message = "Unidade é obrigatória")
    private Long idUnidade;

    @NotBlank(message = "Número do processo é obrigatório")
    @Size(max = 50)
    private String numeroProcesso;

    @NotNull(message = "Tipo de medida é obrigatório")
    private TipoMedidaEnum tipoMedida;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataPrevisaoSaida;

    private LocalDate dataSaida;

    @NotBlank(message = "Motivo é obrigatório")
    private MotivoEnum motivo;

    @NotNull(message = "Status é obrigatório")
    private StatusInternacaoEnum status;
    
    
   
    private Boolean reicidente;
    
    
    private DocumentosEnum docApresentado;
    
  
    private LocalDate dataAprensao;
    
    
   
    private ProcedenciaEnum procedencia;
    
    
  
    private String procedenciaOutros;
    
   
    private String procedenciaTransferecia;
    
    
    
    //filtro:
    private String filtroNomeAdolescente;
    private String filtroCidadeAdolescente;
    private StatusInternacaoEnum filtroStatus; // ATIVA, FINALIZADA...
    private Long filtroUnidadeId;
    private Integer size;
     

    private String nomeUnidade;
        
 // 🔥 dados do adolescente (para tabela)
    private String nomeAdolescente;
    private String cidadeAdolescente;
    private String ufAdolescente;
    private String fotoAdolescente;
    
    private String maeDoAdolescente;

    // 🔥 unidades
    private String nomeUnidadeInternacao;
    private String nomeUnidadeCadastro;
    
    private Integer idadeAdolescente;
    
    
    
    
    
    
    

	public String getMaeDoAdolescente() {
		return maeDoAdolescente;
	}

	public void setMaeDoAdolescente(String maeDoAdolescente) {
		this.maeDoAdolescente = maeDoAdolescente;
	}

	public Integer getIdadeAdolescente() {
		return idadeAdolescente;
	}

	public void setIdadeAdolescente(Integer idadeAdolescente) {
		this.idadeAdolescente = idadeAdolescente;
	}

	public String getNomeAdolescente() {
		return nomeAdolescente;
	}

	public void setNomeAdolescente(String nomeAdolescente) {
		this.nomeAdolescente = nomeAdolescente;
	}

	public String getCidadeAdolescente() {
		return cidadeAdolescente;
	}

	public void setCidadeAdolescente(String cidadeAdolescente) {
		this.cidadeAdolescente = cidadeAdolescente;
	}



	public String getUfAdolescente() {
		return ufAdolescente;
	}

	public void setUfAdolescente(String ufAdolescente) {
		this.ufAdolescente = ufAdolescente;
	}

	public String getFotoAdolescente() {
		return fotoAdolescente;
	}

	public void setFotoAdolescente(String fotoAdolescente) {
		this.fotoAdolescente = fotoAdolescente;
	}

	public String getNomeUnidadeInternacao() {
		return nomeUnidadeInternacao;
	}

	public void setNomeUnidadeInternacao(String nomeUnidadeInternacao) {
		this.nomeUnidadeInternacao = nomeUnidadeInternacao;
	}

	public String getNomeUnidadeCadastro() {
		return nomeUnidadeCadastro;
	}

	public void setNomeUnidadeCadastro(String nomeUnidadeCadastro) {
		this.nomeUnidadeCadastro = nomeUnidadeCadastro;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}





	public String getFiltroNomeAdolescente() {
		return filtroNomeAdolescente;
	}

	public void setFiltroNomeAdolescente(String filtroNomeAdolescente) {
		this.filtroNomeAdolescente = filtroNomeAdolescente;
	}

	public String getFiltroCidadeAdolescente() {
		return filtroCidadeAdolescente;
	}

	public void setFiltroCidadeAdolescente(String filtroCidadeAdolescente) {
		this.filtroCidadeAdolescente = filtroCidadeAdolescente;
	}

	public StatusInternacaoEnum getFiltroStatus() {
		return filtroStatus;
	}

	public void setFiltroStatus(StatusInternacaoEnum filtroStatus) {
		this.filtroStatus = filtroStatus;
	}

	public Long getFiltroUnidadeId() {
		return filtroUnidadeId;
	}

	public void setFiltroUnidadeId(Long filtroUnidadeId) {
		this.filtroUnidadeId = filtroUnidadeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAdolescente() {
		return idAdolescente;
	}

	public void setIdAdolescente(Long idAdolescente) {
		this.idAdolescente = idAdolescente;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public TipoMedidaEnum getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(TipoMedidaEnum tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataPrevisaoSaida() {
		return dataPrevisaoSaida;
	}

	public void setDataPrevisaoSaida(LocalDate dataPrevisaoSaida) {
		this.dataPrevisaoSaida = dataPrevisaoSaida;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}



	public MotivoEnum getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoEnum motivo) {
		this.motivo = motivo;
	}

	public StatusInternacaoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusInternacaoEnum status) {
		this.status = status;
	}

	public Boolean getReicidente() {
		return reicidente;
	}

	public void setReicidente(Boolean reicidente) {
		this.reicidente = reicidente;
	}

	public DocumentosEnum getDocApresentado() {
		return docApresentado;
	}

	public void setDocApresentado(DocumentosEnum docApresentado) {
		this.docApresentado = docApresentado;
	}

	public LocalDate getDataAprensao() {
		return dataAprensao;
	}

	public void setDataAprensao(LocalDate dataAprensao) {
		this.dataAprensao = dataAprensao;
	}

	public ProcedenciaEnum getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(ProcedenciaEnum procedencia) {
		this.procedencia = procedencia;
	}

	public String getProcedenciaOutros() {
		return procedenciaOutros;
	}

	public void setProcedenciaOutros(String procedenciaOutros) {
		this.procedenciaOutros = procedenciaOutros;
	}

	public String getProcedenciaTransferecia() {
		return procedenciaTransferecia;
	}

	public void setProcedenciaTransferecia(String procedenciaTransferecia) {
		this.procedenciaTransferecia = procedenciaTransferecia;
	}

	
    
}