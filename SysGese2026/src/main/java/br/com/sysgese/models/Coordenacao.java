package br.com.sysgese.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "COORDENACAO")
public class Coordenacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // =============================
    // RELACIONAMENTOS
    // =============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVIDOR", nullable = false)
    private Servidor servidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", nullable = false)
    private Unidade unidade;

    @Column(name = "DATA_INICIO_COORDENACAO", nullable = false)
    private LocalDate dataInicioCoordenacao;

    @Column(name = "DATA_FIM_CORRDENACAO", nullable = false)
    private LocalDate dataFimCoordenacao;

    @Column(name = "DATA_CAD", nullable = false)
    private LocalDate dataCad;

    @Column(name = "DATA_ALT", nullable = false)
    private LocalDate dataAlt;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public LocalDate getDataInicioCoordenacao() {
		return dataInicioCoordenacao;
	}

	public void setDataInicioCoordenacao(LocalDate dataInicioCoordenacao) {
		this.dataInicioCoordenacao = dataInicioCoordenacao;
	}

	public LocalDate getDataFimCoordenacao() {
		return dataFimCoordenacao;
	}

	public void setDataFimCoordenacao(LocalDate dataFimCoordenacao) {
		this.dataFimCoordenacao = dataFimCoordenacao;
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
    
    
    
    
}