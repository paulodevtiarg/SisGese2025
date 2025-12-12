package br.com.sysgese.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.sysgese.enumerators.GeneroEnum;
import br.com.sysgese.enumerators.RacaCorEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="ADOLESCENTE")
public class Adolescente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOME", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "APELIDO", nullable = false, length = 255)
    private String apelido;
    
    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
    
    @Column(name = "CIDADE_NASCIMENTO", nullable = false, length = 255)
    private String cidadeNascimento;
    
    @Column(name = "UF_NASCIMENTO", nullable = false, length = 2)
    private String ufNascimento;
    
    @Column(name = "DATA_ESTIMADA")
    private Boolean dataEstimada;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO", nullable = false, columnDefinition = "ENUM('MASCULINO','FEMININO','TRANS_MASCULINO','TRANS_FEMININO','NAO_INFORMADO','OUTRO')")
    private GeneroEnum genero = GeneroEnum.NAO_INFORMADO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "RACA_COR", nullable = false, columnDefinition = "ENUM('BRANCO','NEGRO','PARDO','AMARELO','INDIGENA','OUTRO','NAO_INFORMADO')")
    private RacaCorEnum racaCor = RacaCorEnum.NAO_INFORMADO;
    
    @Column(name = "PAI", length = 255)
    private String pai;
    
    @Column(name = "MAE", length = 255)
    private String mae;
    
    @Column(name = "RESPONSAVEL", length = 255)
    private String responsavel;
    
    @Column(name = "PARENTESCO_RESPONSAVEL", length = 255)
    private String parentescoResponsavel;
    
    @Column(name = "LOGRADOURO", length = 255)
    private String logradouro;
    
    @Column(name = "BAIRRO", length = 255)
    private String bairro;
    
    @Column(name = "CIDADE", length = 255)
    private String cidade;
    
    @Column(name = "UF_ENDERECO", length = 2)
    private String ufEndereco;
    
    @Column(name = "CEP", length = 10)
    private String cep;
    
    @Column(name = "NUMERO", length = 255)
    private String numero;
    
    @Column(name = "REFERENCIA_ENDERECO", length = 355)
    private String referenciaEndereco;
    
    @Column(name = "TELEFONE1", length = 13)
    private String telefone1;
    
    @Column(name = "TELEFONE2", length = 13)
    private String telefone2;
    
    @Column(name = "TELEFONE3", length = 13)
    private String telefone3;
    
    @Column(name = "TELEFONE_RECADO", length = 13)
    private String telefoneRecado;
    
    @Column(name = "NOME_CONTATO_RECADO", length = 255)
    private String nomeContatoRecado;
    
    @Column(name = "REGISTRO_CIVIL", length = 255)
    private String registroCivil;
    
    @Column(name = "CARTORIO", length = 255)
    private String cartorio;
    
    @Column(name = "IDENTIDADE", length = 255)
    private String identidade;
    
    @Column(name = "CPF", length = 255)
    private String cpf;
    
    @Column(name = "RESERVISTA", length = 255)
    private String reservista;
    
    @Column(name = "TITULO_ELEITOR", length = 255)
    private String tituloEleitor;
    
    @Column(name = "DATA_CAD", nullable = false)
    private LocalDate dataCad;
    
    @Column(name = "DATA_ALT", nullable = false)
    private LocalDate dataAlt;
    
    @Column(name = "STATUS", nullable = false)
    private Integer status;
    
    // Relacionamentos
    @OneToMany(mappedBy = "adolescente", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Cicatriz> cicatrizes = new ArrayList<>();
    
    @OneToMany(mappedBy = "adolescente", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Tatuagem> tatuagens = new ArrayList<>();
    
    // Construtores
    public Adolescente() {
        this.dataCad = LocalDate.now();
        this.dataAlt = LocalDate.now();
        this.status = 1; // Ativo por padrão
    }
    
    // Métodos auxiliares para manter consistência nas relações
    public void adicionarCicatriz(Cicatriz cicatriz) {
        cicatrizes.add(cicatriz);
        cicatriz.setAdolescente(this);
    }
    
    public void removerCicatriz(Cicatriz cicatriz) {
        cicatrizes.remove(cicatriz);
        cicatriz.setAdolescente(null);
    }
    
    public void adicionarTatuagem(Tatuagem tatuagem) {
        tatuagens.add(tatuagem);
        tatuagem.setAdolescente(this);
    }
    
    public void removerTatuagem(Tatuagem tatuagem) {
        tatuagens.remove(tatuagem);
        tatuagem.setAdolescente(null);
    }
    
    // Método para atualizar data de alteração
    @PreUpdate
    public void preUpdate() {
        this.dataAlt = LocalDate.now();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getApelido() {
        return apelido;
    }
    
    public void setApelido(String apelido) {
        this.apelido = apelido;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getCidadeNascimento() {
        return cidadeNascimento;
    }
    
    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }
    
    public String getUfNascimento() {
        return ufNascimento;
    }
    
    public void setUfNascimento(String ufNascimento) {
        this.ufNascimento = ufNascimento;
    }
    
    public Boolean getDataEstimada() {
        return dataEstimada;
    }
    
    public void setDataEstimada(Boolean dataEstimada) {
        this.dataEstimada = dataEstimada;
    }
    
    public GeneroEnum getGenero() {
        return genero;
    }
    
    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }
    
    public RacaCorEnum getRacaCor() {
        return racaCor;
    }
    
    public void setRacaCor(RacaCorEnum racaCor) {
        this.racaCor = racaCor;
    }
    
    public String getPai() {
        return pai;
    }
    
    public void setPai(String pai) {
        this.pai = pai;
    }
    
    public String getMae() {
        return mae;
    }
    
    public void setMae(String mae) {
        this.mae = mae;
    }
    
    public String getResponsavel() {
        return responsavel;
    }
    
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    
    public String getParentescoResponsavel() {
        return parentescoResponsavel;
    }
    
    public void setParentescoResponsavel(String parentescoResponsavel) {
        this.parentescoResponsavel = parentescoResponsavel;
    }
    
    public String getLogradouro() {
        return logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getUfEndereco() {
        return ufEndereco;
    }
    
    public void setUfEndereco(String ufEndereco) {
        this.ufEndereco = ufEndereco;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getReferenciaEndereco() {
        return referenciaEndereco;
    }
    
    public void setReferenciaEndereco(String referenciaEndereco) {
        this.referenciaEndereco = referenciaEndereco;
    }
    
    public String getTelefone1() {
        return telefone1;
    }
    
    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }
    
    public String getTelefone2() {
        return telefone2;
    }
    
    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }
    
    public String getTelefone3() {
        return telefone3;
    }
    
    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }
    
    public String getTelefoneRecado() {
        return telefoneRecado;
    }
    
    public void setTelefoneRecado(String telefoneRecado) {
        this.telefoneRecado = telefoneRecado;
    }
    
    public String getNomeContatoRecado() {
        return nomeContatoRecado;
    }
    
    public void setNomeContatoRecado(String nomeContatoRecado) {
        this.nomeContatoRecado = nomeContatoRecado;
    }
    
    public String getRegistroCivil() {
        return registroCivil;
    }
    
    public void setRegistroCivil(String registroCivil) {
        this.registroCivil = registroCivil;
    }
    
    public String getCartorio() {
        return cartorio;
    }
    
    public void setCartorio(String cartorio) {
        this.cartorio = cartorio;
    }
    
    public String getIdentidade() {
        return identidade;
    }
    
    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getReservista() {
        return reservista;
    }
    
    public void setReservista(String reservista) {
        this.reservista = reservista;
    }
    
    public String getTituloEleitor() {
        return tituloEleitor;
    }
    
    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
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
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public List<Cicatriz> getCicatrizes() {
        return cicatrizes;
    }
    
    public void setCicatrizes(List<Cicatriz> cicatrizes) {
        this.cicatrizes = cicatrizes;
    }
    
    public List<Tatuagem> getTatuagens() {
        return tatuagens;
    }
    
    public void setTatuagens(List<Tatuagem> tatuagens) {
        this.tatuagens = tatuagens;
    }
    
    @Override
    public String toString() {
        return "Adolescente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                '}';
    }

}
