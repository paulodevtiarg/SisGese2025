package br.com.sysgese.models;

import br.com.sysgese.enumerators.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ATENDIMENTO_ENFERMAGEM")
public class AtendimentoEnfermagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ADOLESCENTE", nullable = false)
    @JsonIgnore
    private Adolescente adolescente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", nullable = false)
    @JsonIgnore
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROFISSIONAL", nullable = false)
    @JsonIgnore
    private Servidor servidor;

    @Column(name = "DATA", nullable = false)
    private LocalDate data;

    @Column(name = "HORA", nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOTIVO_ATENDIMENTO", nullable = false)
    private MotivoAtendimentoEnum motivoAtendimento;

    @Column(name = "MOTIVO_OUTROS")
    private String motivoOutros;

    @Column(name = "PA_SISTOLICA")
    private Integer paSistolica;

    @Column(name = "PA_DIASTOLICA")
    private Integer paDiastolica;

    @Column(name = "FREQUENCIA_CARDIACA")
    private Integer frequenciaCardiaca;

    @Column(name = "FREQUENCIA_RESPIRATORIA")
    private Integer frequenciaRespiratoria;

    @Column(name = "TEMPERATURA", precision = 4, scale = 2)
    private BigDecimal temperatura;

    @Column(name = "SATURACAO_O2", precision = 3, scale = 1)
    private BigDecimal saturacaoO2;

    @Column(name = "GLICEMIA_CAPILAR", precision = 5, scale = 1)
    private BigDecimal glicemiaCapilar;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_GERAL")
    private EstadoEnum estadoGeral;

    @Column(name = "ESTADO_GERAL_OUTROS")
    private String estadoGeralOutros;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONSCIENCIA")
    private ConscienciaEnum consciencia;

    @Column(name = "CONSCIENCIA_OUTROS")
    private String conscienciaOutros;

    @Column(name = "DOR")
    private Boolean dor;

    @Column(name = "DOR_ESCALA")
    private Integer dorEscala;

    @Column(name = "DOR_LOCAL")
    private String dorLocal;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONDUTA")
    private CondutaEnum conduta;

    @Column(name = "CONDUTA_OUTROS")
    private String condutaOutros;


    @OneToMany(mappedBy = "atendimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescricaoMedicamento> prescricoes = new ArrayList<>();

    // Método auxiliar para salvar
    public void adicionarPrescricao(PrescricaoMedicamento prescricao) {
        prescricoes.add(prescricao);
        prescricao.setAtendimento(this);
    }


    // 1. O Tipo Principal (Interno, Externo ou Outros)
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ENCAMINHAMENTO")
    private EncaminhamentoEnum tipoEncaminhamento;

    // 2. As opções caso ele tenha escolhido Interno
    @Enumerated(EnumType.STRING)
    @Column(name = "ENCAMINHAMENTO_INTERNO")
    private EncaminhamentoInternoEnum encaminhamentoInterno;

    // 3. As opções caso ele tenha escolhido Externo (que você já tem)
    @Enumerated(EnumType.STRING)
    @Column(name = "ENCAMINHAMENTO_EXTERNO")
    private EncaminhamentoExternoEnum encaminhamentoExterno;

    // 4. O texto livre caso ele tenha escolhido "Outros" em qualquer um dos casos
    @Column(name = "ENCAMINHAMENTO_OUTROS", length = 255)
    private String encaminhamentoOutros;

    // Na Entidade
    @Enumerated(EnumType.STRING)
    @Column(name = "ENCERRAMENTO", nullable = false)
    private EncerramentoEnum encerramento;

    @Column(name = "OBSERVACOES")
    private String observacoes;

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

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public MotivoAtendimentoEnum getMotivoAtendimento() {
        return motivoAtendimento;
    }

    public void setMotivoAtendimento(MotivoAtendimentoEnum motivoAtendimento) {
        this.motivoAtendimento = motivoAtendimento;
    }

    public String getMotivoOutros() {
        return motivoOutros;
    }

    public void setMotivoOutros(String motivoOutros) {
        this.motivoOutros = motivoOutros;
    }

    public Integer getPaSistolica() {
        return paSistolica;
    }

    public void setPaSistolica(Integer paSistolica) {
        this.paSistolica = paSistolica;
    }

    public Integer getPaDiastolica() {
        return paDiastolica;
    }

    public void setPaDiastolica(Integer paDiastolica) {
        this.paDiastolica = paDiastolica;
    }

    public Integer getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(Integer frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public Integer getFrequenciaRespiratoria() {
        return frequenciaRespiratoria;
    }

    public void setFrequenciaRespiratoria(Integer frequenciaRespiratoria) {
        this.frequenciaRespiratoria = frequenciaRespiratoria;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public BigDecimal getSaturacaoO2() {
        return saturacaoO2;
    }

    public void setSaturacaoO2(BigDecimal saturacaoO2) {
        this.saturacaoO2 = saturacaoO2;
    }

    public BigDecimal getGlicemiaCapilar() {
        return glicemiaCapilar;
    }

    public void setGlicemiaCapilar(BigDecimal glicemiaCapilar) {
        this.glicemiaCapilar = glicemiaCapilar;
    }

    public EstadoEnum getEstadoGeral() {
        return estadoGeral;
    }

    public void setEstadoGeral(EstadoEnum estadoGeral) {
        this.estadoGeral = estadoGeral;
    }

    public String getEstadoGeralOutros() {
        return estadoGeralOutros;
    }

    public void setEstadoGeralOutros(String estadoGeralOutros) {
        this.estadoGeralOutros = estadoGeralOutros;
    }

    public ConscienciaEnum getConsciencia() {
        return consciencia;
    }

    public void setConsciencia(ConscienciaEnum consciencia) {
        this.consciencia = consciencia;
    }

    public String getConscienciaOutros() {
        return conscienciaOutros;
    }

    public void setConscienciaOutros(String conscienciaOutros) {
        this.conscienciaOutros = conscienciaOutros;
    }

    public Boolean getDor() {
        return dor;
    }

    public void setDor(Boolean dor) {
        this.dor = dor;
    }

    public Integer getDorEscala() {
        return dorEscala;
    }

    public void setDorEscala(Integer dorEscala) {
        this.dorEscala = dorEscala;
    }

    public String getDorLocal() {
        return dorLocal;
    }

    public void setDorLocal(String dorLocal) {
        this.dorLocal = dorLocal;
    }

    public CondutaEnum getConduta() {
        return conduta;
    }

    public void setConduta(CondutaEnum conduta) {
        this.conduta = conduta;
    }

    public String getCondutaOutros() {
        return condutaOutros;
    }

    public void setCondutaOutros(String condutaOutros) {
        this.condutaOutros = condutaOutros;
    }

    public List<PrescricaoMedicamento> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<PrescricaoMedicamento> prescricoes) {
        this.prescricoes = prescricoes;
    }

    public EncaminhamentoEnum getTipoEncaminhamento() {
        return tipoEncaminhamento;
    }

    public void setTipoEncaminhamento(EncaminhamentoEnum tipoEncaminhamento) {
        this.tipoEncaminhamento = tipoEncaminhamento;
    }

    public EncaminhamentoInternoEnum getEncaminhamentoInterno() {
        return encaminhamentoInterno;
    }

    public void setEncaminhamentoInterno(EncaminhamentoInternoEnum encaminhamentoInterno) {
        this.encaminhamentoInterno = encaminhamentoInterno;
    }

    public EncaminhamentoExternoEnum getEncaminhamentoExterno() {
        return encaminhamentoExterno;
    }

    public void setEncaminhamentoExterno(EncaminhamentoExternoEnum encaminhamentoExterno) {
        this.encaminhamentoExterno = encaminhamentoExterno;
    }

    public String getEncaminhamentoOutros() {
        return encaminhamentoOutros;
    }

    public void setEncaminhamentoOutros(String encaminhamentoOutros) {
        this.encaminhamentoOutros = encaminhamentoOutros;
    }

    public EncerramentoEnum getEncerramento() {
        return encerramento;
    }

    public void setEncerramento(EncerramentoEnum encerramento) {
        this.encerramento = encerramento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @PrePersist
    public void prePersist() {
        this.data = LocalDate.now();
        this.hora = LocalTime.now();
    }


}
