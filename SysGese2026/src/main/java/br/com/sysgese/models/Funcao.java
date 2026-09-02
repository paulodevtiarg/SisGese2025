package br.com.sysgese.models;

import java.time.LocalDateTime;

import br.com.sysgese.enumerators.TipoFuncaoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "FUNCAO")
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 255)
    private String descricao;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DATA_CAD")
    private LocalDateTime dataCad;

    @Column(name = "DATA_ALT")
    private LocalDateTime dataAlt;

    @PrePersist
    public void prePersist() {
        this.dataCad = LocalDateTime.now();
        this.dataAlt = LocalDateTime.now();
        if (this.status == null) {
            this.status = 1;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlt = LocalDateTime.now();
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

	@Enumerated(EnumType.STRING)
	@Column(name = "CHAVE_SISTEMA", length = 50, unique = true)
	private TipoFuncaoEnum chaveSistema;

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getDataCad() {
		return dataCad;
	}

	public void setDataCad(LocalDateTime dataCad) {
		this.dataCad = dataCad;
	}

	public LocalDateTime getDataAlt() {
		return dataAlt;
	}

	public void setDataAlt(LocalDateTime dataAlt) {
		this.dataAlt = dataAlt;
	}

	public TipoFuncaoEnum getChaveSistema() {
		return chaveSistema;
	}

	public void setChaveSistema(TipoFuncaoEnum chaveSistema) {
		this.chaveSistema = chaveSistema;
	}
}