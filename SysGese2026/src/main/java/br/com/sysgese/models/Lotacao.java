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
@Table(name = "LOTACAO")
public class Lotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVIDOR", nullable = false)
    private Servidor servidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", nullable = false)
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARGO", nullable = false)
    private Cargo cargo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FUNCAO", nullable = false)
    private Funcao funcao;

    @Column(name = "OBSERVACOES", length = 500)
    private String observacoes;

    @Column(name = "DATA_INICIO", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "DATA_SAIDA")
    private LocalDate dataSaida;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "DATA_CAD", nullable = false)
    private LocalDate dataCad;

    @Column(name = "DATA_ALT", nullable = false)
    private LocalDate dataAlt;

    @PrePersist
    public void prePersist() {
        dataCad = LocalDate.now();
        dataAlt = LocalDate.now();
        if (status == null) {
            status = 1;
        }
    }

    @PreUpdate
    public void preUpdate() {
        dataAlt = LocalDate.now();
    }

    // GETTERS E SETTERS
    
    
    
    

    public Integer getId() {
        return id;
    }

    public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public void setId(Integer id) {
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
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

}
