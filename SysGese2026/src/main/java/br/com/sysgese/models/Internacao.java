package br.com.sysgese.models;

import java.time.LocalDate;

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

    @Column(name = "NUMERO_PROCESSO", nullable = false, length = 50)
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

    @Column(name = "MOTIVO", nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private StatusInternacaoEnum status;

    @Column(name = "DATA_CAD", nullable = false)
    private LocalDate dataCad;

    @Column(name = "DATA_ALT", nullable = false)
    private LocalDate dataAlt;

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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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

  
}