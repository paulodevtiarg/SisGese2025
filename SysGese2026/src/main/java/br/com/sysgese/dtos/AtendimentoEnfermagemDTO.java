package br.com.sysgese.dtos;

import br.com.sysgese.enumerators.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class AtendimentoEnfermagemDTO {

    private Long id;
    @NotNull(message = "Adolescente é obrigatório")
    private Long idAdolescente;

    @NotNull(message = "Unidade é obrigatória")
    private Long idUnidade;

    @NotNull(message = "Servidor é obrigatória")
    private Long idServidor;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Hora é obrigatória")
    private LocalTime hora;

    @NotNull(message = "Motivo é obrigatório")
    private MotivoAtendimentoEnum motivoAtendimento;

    private String motivoOutros;

    private Integer paSistolica;
    private Integer paDiastolica;

    private Integer frequenciaCardiaca;

    private Integer frequenciaRespiratoria;

    @NumberFormat(pattern = "#,##0.00")
    @Digits(integer = 2, fraction = 2, message = "Temperatura deve ter no máximo 2 dígitos inteiros e 2 casas decimais (ex: 36.50)")
    @DecimalMin(value = "30.0", message = "Temperatura muito baixa para um ser vivo")
    @DecimalMax(value = "45.0", message = "Temperatura muito alta para um ser vivo")
    private BigDecimal temperatura;


    @Digits(integer = 2, fraction = 1, message = "Saturação deve ter até 2 dígitos inteiros e 1 casa decimal (ex: 98.5)")
    @DecimalMin(value = "70.0", message = "Saturação O₂ muito baixa (mínimo 70%)")
    @DecimalMax(value = "100.0", message = "Saturação O₂ não pode ultrapassar 100%")
    @NumberFormat(pattern = "#,##0.0")
    private BigDecimal saturacaoO2;



    @Digits(integer = 3, fraction = 1, message = "Glicemia deve ter até 3 dígitos inteiros e 1 casa decimal")
    @DecimalMin(value = "0.0", message = "Glicemia deve ser ≥ 0 mg/dL")
    @DecimalMax(value = "999.9", message = "Glicemia deve ser ≤ 999.9 mg/dL")  // Limite do DECIMAL(5,1)
    @NumberFormat(pattern = "#,##0.0")
    private BigDecimal glicemiaCapilar;

    private EstadoEnum estadoGeral;

    private String estadoGeralOutros;

    private ConscienciaEnum consciencia;

    private String conscienciaOutros;

    private Boolean dor;

    private Integer dorEscala;

    private String dorLocal;

    private CondutaEnum conduta;

    private String condutaOutros;

    private String medicamento;

    private TipoDoseEnum tipoDose;

    private String tipoDoseOutros;

    private ViaAdmEnum via;

    private HorarioEnum horario;

    private String horarioOutros;

    private EncaminhamentoEnum encaminhamentoExterno;

    private String encaminhamentoOutros;

    private EncerramentoEnum encerramento;

    private String observacoes;

    //filtro:
    private String filtroNomeAdolescente;
    private String filtroCidadeAdolescente;
    private Long filtroUnidadeId;
    private Integer size;
    private String nomeUnidade;

    // 🔥 dados do adolescente (para tabela)
    private String nomeAdolescente;
    private String cidadeAdolescente;
    private String ufAdolescente;
    private String fotoAdolescente;
    private String maeDoAdolescente;

    // 🔥 unidades
    private String nomeUnidadeInternacao;
    private String nomeUnidadeCadastro;
    private Integer idadeAdolescente;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAdolescente() {
        return idAdolescente;
    }

    public void setIdAdolescente(Long idAdolescente) {
        this.idAdolescente = idAdolescente;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Long getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(Long idServidor) {
        this.idServidor = idServidor;
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

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public TipoDoseEnum getTipoDose() {
        return tipoDose;
    }

    public void setTipoDose(TipoDoseEnum tipoDose) {
        this.tipoDose = tipoDose;
    }

    public String getTipoDoseOutros() {
        return tipoDoseOutros;
    }

    public void setTipoDoseOutros(String tipoDoseOutros) {
        this.tipoDoseOutros = tipoDoseOutros;
    }

    public ViaAdmEnum getVia() {
        return via;
    }

    public void setVia(ViaAdmEnum via) {
        this.via = via;
    }

    public HorarioEnum getHorario() {
        return horario;
    }

    public void setHorario(HorarioEnum horario) {
        this.horario = horario;
    }

    public String getHorarioOutros() {
        return horarioOutros;
    }

    public void setHorarioOutros(String horarioOutros) {
        this.horarioOutros = horarioOutros;
    }

    public EncaminhamentoEnum getEncaminhamentoExterno() {
        return encaminhamentoExterno;
    }

    public void setEncaminhamentoExterno(EncaminhamentoEnum encaminhamentoExterno) {
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

    public String getFiltroNomeAdolescente() {
        return filtroNomeAdolescente;
    }

    public void setFiltroNomeAdolescente(String filtroNomeAdolescente) {
        this.filtroNomeAdolescente = filtroNomeAdolescente;
    }

    public String getFiltroCidadeAdolescente() {
        return filtroCidadeAdolescente;
    }

    public void setFiltroCidadeAdolescente(String filtroCidadeAdolescente) {
        this.filtroCidadeAdolescente = filtroCidadeAdolescente;
    }



    public Long getFiltroUnidadeId() {
        return filtroUnidadeId;
    }

    public void setFiltroUnidadeId(Long filtroUnidadeId) {
        this.filtroUnidadeId = filtroUnidadeId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public String getNomeAdolescente() {
        return nomeAdolescente;
    }

    public void setNomeAdolescente(String nomeAdolescente) {
        this.nomeAdolescente = nomeAdolescente;
    }

    public String getCidadeAdolescente() {
        return cidadeAdolescente;
    }

    public void setCidadeAdolescente(String cidadeAdolescente) {
        this.cidadeAdolescente = cidadeAdolescente;
    }

    public String getUfAdolescente() {
        return ufAdolescente;
    }

    public void setUfAdolescente(String ufAdolescente) {
        this.ufAdolescente = ufAdolescente;
    }

    public String getFotoAdolescente() {
        return fotoAdolescente;
    }

    public void setFotoAdolescente(String fotoAdolescente) {
        this.fotoAdolescente = fotoAdolescente;
    }

    public String getMaeDoAdolescente() {
        return maeDoAdolescente;
    }

    public void setMaeDoAdolescente(String maeDoAdolescente) {
        this.maeDoAdolescente = maeDoAdolescente;
    }

    public String getNomeUnidadeInternacao() {
        return nomeUnidadeInternacao;
    }

    public void setNomeUnidadeInternacao(String nomeUnidadeInternacao) {
        this.nomeUnidadeInternacao = nomeUnidadeInternacao;
    }

    public String getNomeUnidadeCadastro() {
        return nomeUnidadeCadastro;
    }

    public void setNomeUnidadeCadastro(String nomeUnidadeCadastro) {
        this.nomeUnidadeCadastro = nomeUnidadeCadastro;
    }

    public Integer getIdadeAdolescente() {
        return idadeAdolescente;
    }

    public void setIdadeAdolescente(Integer idadeAdolescente) {
        this.idadeAdolescente = idadeAdolescente;
    }
}
