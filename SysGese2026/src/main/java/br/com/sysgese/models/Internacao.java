package br.com.sysgese.models;

import java.time.LocalDate;

import br.com.sysgese.enumerators.DocumentosEnum;
import br.com.sysgese.enumerators.MotivoEnum;
import br.com.sysgese.enumerators.ProcedenciaEnum;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.enumerators.TipoMedidaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "INTERNACAO")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_ADOLESCENTE", nullable = false)
    private Adolescente adolescente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_UNIDADE", nullable = false)
    private Unidade unidade;

    @Column(name = "NUMERO_DOC_APRESENTADO", nullable = false, length = 50)
    private String numeroProcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_MEDIDA", nullable = false, length = 30)
    private TipoMedidaEnum tipoMedida;

    @Column(name = "DATA_INICIO", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "DATA_PREVISAO_SAIDA")
    private LocalDate dataPrevisaoSaida;

    @Column(name = "DATA_SAIDA")
    private LocalDate dataSaida;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOTIVO", nullable = false)
    private MotivoEnum motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private StatusInternacaoEnum status;

    @Column(name = "DATA_CAD", nullable = false)
    private LocalDate dataCad;

    @Column(name = "DATA_ALT", nullable = false)
    private LocalDate dataAlt;
    
    @Column(name="REICIDENTE", nullable=true)
    private Boolean reicidente;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "DOC_APRESENTADO", nullable = false)
    private DocumentosEnum docApresentado;
    
    @Column(name = "DATA_APREENSAO", nullable = false)
    private LocalDate dataAprensao;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "PROCEDENCIA", nullable = false)
    private ProcedenciaEnum procedencia;
    
    
    @Column(name = "PROCED_OUTRO", nullable = false, length = 50)
    private String procedenciaOutros;
    
    @Column(name = "PROCED_TRANSF", nullable = false, length = 50)
    private String procedenciaTransferecia;

    @PrePersist
    public void prePersist() {
        this.dataCad = LocalDate.now();
        this.dataAlt = LocalDate.now();
        this.status = StatusInternacaoEnum.ATIVA;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlt = LocalDate.now();
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

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
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



	public StatusInternacaoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusInternacaoEnum status) {
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

	public MotivoEnum getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoEnum motivo) {
		this.motivo = motivo;
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