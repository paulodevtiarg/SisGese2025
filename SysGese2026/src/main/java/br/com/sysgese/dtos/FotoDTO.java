package br.com.sysgese.dtos;

public class FotoDTO {
    private Long id;
    private String descricaoDetalhe;
    private byte[] foto;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricaoDetalhe() {
        return descricaoDetalhe;
    }

    public void setDescricaoDetalhe(String descricaoDetalhe) {
        this.descricaoDetalhe = descricaoDetalhe;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
