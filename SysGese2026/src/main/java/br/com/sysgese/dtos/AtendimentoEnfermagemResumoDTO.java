package br.com.sysgese.dtos;

import br.com.sysgese.enumerators.EncerramentoEnum;

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
    private EncerramentoEnum encerramento; // Adicione o campo

    private boolean temPendencia; // 🔥 Novo campo


    private int quantidadePendentes;

    public int getQuantidadePendentes() {
        return quantidadePendentes;
    }

    public void setQuantidadePendentes(int quantidadePendentes) {
        this.quantidadePendentes = quantidadePendentes;
    }


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

    public void setAdolescenteId(Long adolescenteId) {
        this.adolescenteId = adolescenteId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public void setUfNascimento(String ufNascimento) {
        this.ufNascimento = ufNascimento;
    }

    public void setFotoRegistro(String fotoRegistro) {
        this.fotoRegistro = fotoRegistro;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public void setUnidadeNome(String unidadeNome) {
        this.unidadeNome = unidadeNome;
    }

    public void setUnidadeCadastroNome(String unidadeCadastroNome) {
        this.unidadeCadastroNome = unidadeCadastroNome;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setQuantidadeAtendimentos(Long quantidadeAtendimentos) {
        this.quantidadeAtendimentos = quantidadeAtendimentos;
    }

    public void setUltimoAtendimento(LocalDate ultimoAtendimento) {
        this.ultimoAtendimento = ultimoAtendimento;
    }

    public EncerramentoEnum getEncerramento() {
        return encerramento;
    }

    public void setEncerramento(EncerramentoEnum encerramento) {
        this.encerramento = encerramento;
    }

    public boolean isTemPendencia() {
        return temPendencia;
    }

    public void setTemPendencia(boolean temPendencia) {
        this.temPendencia = temPendencia;
    }
}