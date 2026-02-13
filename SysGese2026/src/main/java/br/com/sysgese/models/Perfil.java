package br.com.sysgese.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "PERFIL")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 100)
    private String descricao;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DATA_CAD")
    private LocalDate dataCad;

    @Column(name = "DATA_ALT")
    private LocalDate dataAlt;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
