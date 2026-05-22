package br.com.sysgese.models;

import jakarta.persistence.*;

@Entity
@Table(name="INFRACAO")
public class Infracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ARTIGO", nullable = false, length = 255)
    private String artigo;

    @Column(name = "DESCRICAO", nullable = false, length = 255)
    private String descricao;

    @Column(name = "CATEGORIA", nullable = false, length = 255)
    private String categoria;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtigo() {
        return artigo;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
