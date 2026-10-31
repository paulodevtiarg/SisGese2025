package br.com.sysgese.models;

import br.com.sysgese.enumerators.ParentescoEnum;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FAMILIAR")
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // Relacionamento com o Adolescente (Lado "N" da relação 1:N)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ADOLESCENTE", nullable = false)
    private Adolescente adolescente;

    @Column(name = "FAMILIAR", nullable = false, length = 255)
    private String nomeFamiliar; // Nome do familiar

    @Enumerated(EnumType.STRING)
    @Column(name = "PARENTESCO")
    private ParentescoEnum parentesco;

    @Column(name = "DATA_CAD")
    private LocalDateTime dataCad;

    @Column(name = "DATA_ALT")
    private LocalDateTime dataAlt;

    @Column(name = "STATUS")
    private Boolean status;

    // ==========================================
    // CONTROLE AUTOMÁTICO DE DATAS
    // ==========================================

    @PrePersist
    public void prePersist() {
        this.dataCad = LocalDateTime.now();
        this.dataAlt = LocalDateTime.now();
        if (this.status == null) {
            this.status = true; // Ativo por padrão
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS E SETTERS
    // ==========================================

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

    public String getNomeFamiliar() {
        return nomeFamiliar;
    }

    public void setNomeFamiliar(String nomeFamiliar) {
        this.nomeFamiliar = nomeFamiliar;
    }

    public ParentescoEnum getParentesco() {
        return parentesco;
    }

    public void setParentesco(ParentescoEnum parentesco) {
        this.parentesco = parentesco;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}