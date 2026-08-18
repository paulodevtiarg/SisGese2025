package br.com.sysgese.dtos;

import java.time.LocalDate;
import java.time.Period;

public class AtendimentoEnfermagemResumoDTO {

    private Long adolescenteId;
    private String nome;
    private String cidadeNascimento;
    private String ufNascimento;
    private String fotoRegistro;
    private String mae;
    private String unidadeNome;
    private String unidadeCadastroNome;
    private LocalDate dataNascimento;
    private Integer idade;
    private Long quantidadeAtendimentos;
    private LocalDate ultimoAtendimento;

    public AtendimentoEnfermagemResumoDTO(
            Long adolescenteId,
            String nome,
            String cidadeNascimento,
            String ufNascimento,
            String fotoRegistro,
            String mae,
            String unidadeNome,
            String unidadeCadastroNome,
            LocalDate dataNascimento,
            Long quantidadeAtendimentos,
            LocalDate ultimoAtendimento) {

        this.adolescenteId = adolescenteId;
        this.nome = nome;
        this.cidadeNascimento = cidadeNascimento;
        this.ufNascimento = ufNascimento;
        this.fotoRegistro = fotoRegistro;
        this.mae = mae;
        this.unidadeNome = unidadeNome;
        this.unidadeCadastroNome = unidadeCadastroNome;
        this.dataNascimento = dataNascimento;
        this.quantidadeAtendimentos = quantidadeAtendimentos;
        this.ultimoAtendimento = ultimoAtendimento;
        this.idade = calcularIdade(dataNascimento);
    }

    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return null;
        }

        return Period.between(
                dataNascimento,
                LocalDate.now()
        ).getYears();
    }

    public LocalDate getUltimoAtendimento() {
        return ultimoAtendimento;
    }
    public Long getAdolescenteId() {
        return adolescenteId;
    }

    public String getNome() {
        return nome;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public String getUfNascimento() {
        return ufNascimento;
    }

    public String getFotoRegistro() {
        return fotoRegistro;
    }

    public String getMae() {
        return mae;
    }

    public String getUnidadeNome() {
        return unidadeNome;
    }

    public String getUnidadeCadastroNome() {
        return unidadeCadastroNome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public Long getQuantidadeAtendimentos() {
        return quantidadeAtendimentos;
    }
}