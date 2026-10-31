package br.com.sysgese.dtos;

import br.com.sysgese.enumerators.ParentescoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FamiliarDTO {

    private Long id;

    @NotBlank(message = "O nome do familiar é obrigatório")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nomeFamiliar;

    @NotNull(message = "O parentesco é obrigatório")
    private ParentescoEnum parentesco;

    private Boolean status;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}