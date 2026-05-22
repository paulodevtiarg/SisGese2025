package br.com.sysgese.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InfracaoDTO {

    @NotBlank(message = "Artigo é obrigatória")
    @Size(max = 255, message = "Artigo não pode ter mais de 255 caracteres")
    private String artigo;


    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição não pode ter mais de 255 caracteres")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 255, message = "Categoria não pode ter mais de 255 caracteres")
    private String categoria;


    private Boolean ativo;

    //Filtros
    private String filtroArtigo;
    private String filtrDescricao;
    private String filtroCategoria;
    private String filtroAtivo;
    private Integer size = 10;

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

    public String getFiltroArtigo() {
        return filtroArtigo;
    }

    public void setFiltroArtigo(String filtroArtigo) {
        this.filtroArtigo = filtroArtigo;
    }

    public String getFiltrDescricao() {
        return filtrDescricao;
    }

    public void setFiltrDescricao(String filtrDescricao) {
        this.filtrDescricao = filtrDescricao;
    }

    public String getFiltroCategoria() {
        return filtroCategoria;
    }

    public void setFiltroCategoria(String filtroCategoria) {
        this.filtroCategoria = filtroCategoria;
    }

    public String getFiltroAtivo() {
        return filtroAtivo;
    }

    public void setFiltroAtivo(String filtroAtivo) {
        this.filtroAtivo = filtroAtivo;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
