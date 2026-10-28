package br.com.sysgese.controllers;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.AtendimentoEnfermagemDTO;
import br.com.sysgese.dtos.AtendimentoEnfermagemResumoDTO;
import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.enumerators.*;
import br.com.sysgese.mappers.AtendimentoEnfermagemMapper;
import br.com.sysgese.models.AtendimentoEnfermagem;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.services.AdolescenteService;
import br.com.sysgese.services.AtendimentoEnfermagemService;
import br.com.sysgese.services.UnidadeService;
import br.com.sysgese.utils.UrlUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoController {
    @Autowired
    private AtendimentoEnfermagemService atendimentoEnfermagemService;
    @Autowired
    private  AdolescenteService adolescenteService;
    @Autowired
    private UnidadeService unidadeService;
    @Autowired
    private AtendimentoEnfermagemMapper atendimentoMapper;
    @Autowired
    private UrlUtils urlUtils;


    @GetMapping("/enfermagem")
    public String indexAtendimentoEnfermagem(
            @ModelAttribute("filtro")
            AtendimentoEnfermagemDTO filtro,
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "10")
            int size,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        boolean isMaster =  (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");
        Long unidadeSession =(Long) session.getAttribute("unidadeId");

        TipoFuncaoEnum tipoFuncaoUsuario = null;
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }
        if (lotacaoAtiva != null && lotacaoAtiva.getFuncao() != null) {
            tipoFuncaoUsuario = lotacaoAtiva
                    .getFuncao()
                    .getChaveSistema();
        }

        boolean podeIncluirAtendimentoEnfermagem =
                TipoFuncaoEnum.ENFERMEIRO.equals(tipoFuncaoUsuario)
                        || TipoFuncaoEnum.TEC_ENFERMAGEM.equals(tipoFuncaoUsuario);



        Long unidadeFiltro;

        if (isMaster) {
            unidadeFiltro = (filtro.getFiltroUnidadeId() != null)? filtro.getFiltroUnidadeId(): lotacaoAtiva.getUnidade().getId();

        } else {
            unidadeFiltro = lotacaoAtiva.getUnidade().getId();
        }
        model.addAttribute("lotacaoUsuarioLogado", lotacaoAtiva );

        if (filtro.getSize() != null) {

            size = filtro.getSize();

        } else {

            filtro.setSize(size);
        }
        Page<AtendimentoEnfermagemResumoDTO> pagina =
                atendimentoEnfermagemService
                        .buscarResumoPorAdolescente(
                                filtro,
                                unidadeSession,
                                isMaster,
                                page
                        );



        model.addAttribute("pagina", pagina);
        model.addAttribute("lista", pagina.getContent());
        model.addAttribute("isMaster", isMaster);
        model.addAttribute("size", size);
        model.addAttribute("queryParams",urlUtils.atendimentoEnfermagemQuery(filtro, page));
        model.addAttribute("pageTitle","Atendimento de Enfermagem");
        model.addAttribute("activeMenu","atendimentos");
        model.addAttribute("podeIncluirAtendimentoEnfermagem",podeIncluirAtendimentoEnfermagem);
        model.addAttribute("unidadeId",unidadeFiltro);


        return "atendimento/enfermagem/index";
    }

    @GetMapping("/enfermagem/novo")
    public String novo(
            @ModelAttribute("filtro") AtendimentoEnfermagemDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model
    ){
        boolean isMaster = (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");
        Long unidadeFiltro = lotacaoAtiva.getUnidade().getId();

        TipoFuncaoEnum tipoFuncaoUsuario = null;

        if (lotacaoAtiva != null && lotacaoAtiva.getFuncao() != null) {
            tipoFuncaoUsuario =
                    lotacaoAtiva.getFuncao().getChaveSistema();
        }

        boolean podeIncluirAtendimentoEnfermagem =
                TipoFuncaoEnum.ENFERMEIRO.equals(tipoFuncaoUsuario)
                        || TipoFuncaoEnum.TEC_ENFERMAGEM.equals(tipoFuncaoUsuario);

        // SOMENTE AQUI BLOQUEAMOS
        if (!isMaster && !podeIncluirAtendimentoEnfermagem) {

            redirectAttributes.addFlashAttribute(
                    "msgErro",
                    "Acesso não permitido. Você não possui permissão para incluir Atendimento de Enfermagem."
            );

            return "redirect:/atendimentos/enfermagem";
        }
        //Adolescentes para atendimento de enferemagem
        List<AdolescenteDTO> adolescentesUnidade =  adolescenteService.buscarAdolescentesUnidade(unidadeFiltro);

        //Objeto a ser preenchido
        AtendimentoEnfermagemDTO atendimentoEnfermagemDTO = new AtendimentoEnfermagemDTO();

        // 🔥 Pega o ID do servidor logado e joga no DTO
        atendimentoEnfermagemDTO.setIdServidor(lotacaoAtiva.getServidor().getId());
        atendimentoEnfermagemDTO.setData(LocalDate.now());
        atendimentoEnfermagemDTO.setHora(LocalTime.now());

        // Se não for master, já define a unidade
        if (!isMaster) {
            atendimentoEnfermagemDTO.setIdUnidade(lotacaoAtiva.getUnidade().getId());
        }

        // Master recebe lista de unidades
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }

        //Carregar Enums
        model.addAttribute("condutas", CondutaEnum.values());
        model.addAttribute("viasAdm", ViaAdmEnum.values());
        model.addAttribute("horarios", HorarioEnum.values());
        model.addAttribute("tiposDose", TipoDoseEnum.values());
        model.addAttribute("encerramentos", EncerramentoEnum.values());
        model.addAttribute("tiposEncaminhamento", EncaminhamentoEnum.values());
        model.addAttribute("encaminhamentosInternos", EncaminhamentoInternoEnum.values());
        model.addAttribute("encaminhamentosExternos", EncaminhamentoExternoEnum.values());
        model.addAttribute("atendimento",atendimentoEnfermagemDTO);
        model.addAttribute("usuarioMaster", isMaster);
        model.addAttribute("adolescentesUnidade", adolescentesUnidade);
        model.addAttribute("motivoAtendimentos", MotivoAtendimentoEnum.values());
        model.addAttribute("estadosGerais", EstadoEnum.values());
        model.addAttribute("conciencias", ConscienciaEnum.values());
        model.addAttribute("tipoDose", TipoDoseEnum.values());
        model.addAttribute("encaminhamento", EncaminhamentoEnum.values());
        model.addAttribute("encerramentoAtd", EncerramentoEnum.values());

        return "atendimento/enfermagem/form";

    }
    @PostMapping("/enfermagem/salvar")
    public String salvar(
            @ModelAttribute("filtro") AtendimentoEnfermagemDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            @ModelAttribute("atendimento") AtendimentoEnfermagemDTO dto,
            BindingResult resultAtendimento,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {


        boolean isMaster = (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

        System.out.println("DEBUG => Prescrições recebidas no DTO: " +
                (dto.getPrescricoes() != null ? dto.getPrescricoes().size() : "NULL"));


        // 🔥 Validação manual: se for novo atendimento (não tem ID), valida tudo. Se for conduta (tem ID), não valida.
        boolean isEdicao = dto.getId() != null;

        if (!isEdicao) {
            // Para NOVO atendimento, precisamos validar os campos obrigatórios
            if (dto.getIdAdolescente() == null || dto.getMotivoAtendimento() == null || dto.getEstadoGeral() == null) {
                // Simula um erro de validação
                resultAtendimento.reject("erro", "Preencha todos os campos obrigatórios.");
            }
        }

// Valida a conduta e o encerramento independente de ser novo ou edição
        if (dto.getConduta() == null) {
            resultAtendimento.rejectValue("conduta", "erro.conduta", "Conduta é obrigatória.");
        }
        if (dto.getEncerramento() == null) {
            resultAtendimento.rejectValue("encerramento", "erro.encerramento", "Encerramento é obrigatório.");
        }


        if (resultAtendimento.hasErrors()) {

            Long unidadeFiltro = lotacaoAtiva.getUnidade().getId();

            TipoFuncaoEnum tipoFuncaoUsuario = null;

            if (lotacaoAtiva != null && lotacaoAtiva.getFuncao() != null) {
                tipoFuncaoUsuario =
                        lotacaoAtiva.getFuncao().getChaveSistema();
            }

            boolean podeIncluirAtendimentoEnfermagem =
                    TipoFuncaoEnum.ENFERMEIRO.equals(tipoFuncaoUsuario)
                            || TipoFuncaoEnum.TEC_ENFERMAGEM.equals(tipoFuncaoUsuario);

            // SOMENTE AQUI BLOQUEAMOS
            if (!isMaster && !podeIncluirAtendimentoEnfermagem) {

                redirectAttributes.addFlashAttribute(
                        "msgErro",
                        "Acesso não permitido. Você não possui permissão para incluir Atendimento de Enfermagem."
                );

                return "redirect:/atendimentos/enfermagem";
            }
            //Adolescentes para atendimento de enferemagem
            List<AdolescenteDTO> adolescentesUnidade =  adolescenteService.buscarAdolescentesUnidade(unidadeFiltro);


            // 🔥 ADICIONE ESTA LINHA AQUI:
            // Verifica se o dto que voltou já tinha um ID (se sim, é edição/conduta, senão é novo)

            model.addAttribute("modoConduta", isEdicao);
            // Master recebe lista de unidades
            if (isMaster) {
                model.addAttribute("unidades", unidadeService.listarTodas());
            }
            //Carregar Enums
            model.addAttribute("condutas", CondutaEnum.values());
            model.addAttribute("viasAdm", ViaAdmEnum.values());
            model.addAttribute("horarios", HorarioEnum.values());
            model.addAttribute("tiposDose", TipoDoseEnum.values());
            model.addAttribute("tiposEncaminhamento", EncaminhamentoEnum.values());
            model.addAttribute("encaminhamentosInternos", EncaminhamentoInternoEnum.values());
            model.addAttribute("encaminhamentosExternos", EncaminhamentoExternoEnum.values());
            model.addAttribute("encerramentos", EncerramentoEnum.values());


            model.addAttribute("usuarioMaster", isMaster);
            model.addAttribute("adolescentesUnidade", adolescentesUnidade);
            model.addAttribute("motivoAtendimentos", MotivoAtendimentoEnum.values());
            model.addAttribute("estadosGerais", EstadoEnum.values());
            model.addAttribute("conciencias", ConscienciaEnum.values());
            model.addAttribute("conduta", CondutaEnum.values());
            model.addAttribute("tipoDose", TipoDoseEnum.values());
            model.addAttribute("viaAdm", ViaAdmEnum.values());
            model.addAttribute("horarioMed", HorarioEnum.values());
            model.addAttribute("encaminhamento", EncaminhamentoEnum.values());
            model.addAttribute("encerramentoAtd", EncerramentoEnum.values());

            return "atendimento/enfermagem/form";
        }
        try {
            // 🔥 REGRA DE OURO: O servidor logado SEMPRE é quem está salvando
            Long idServidorLogado = lotacaoAtiva.getServidor().getId();
            Long unidadeIdParaSalvar;

            if (isMaster && dto.getIdUnidade() != null) {
                // Master escolheu no select
                unidadeIdParaSalvar = dto.getIdUnidade();
            } else {
                // Usuário comum: pega da sessão por segurança
                unidadeIdParaSalvar = lotacaoAtiva.getUnidade().getId();
            }

            // Manda os dois seguros para o Service
            atendimentoEnfermagemService.salvar(dto, unidadeIdParaSalvar, idServidorLogado);
            redirectAttributes.addFlashAttribute("msgOk", "Atendimento salvo com sucesso!");


        }
        catch (Exception e) {

            // 🔥 ISSO VAI PRINTAR O ERRO VERDADEIRO NO SEU CONSOLE DO INTELLIJ
            System.out.println("============== ERRO AO SALVAR ==============");
            e.printStackTrace();
            System.out.println("============================================");

            redirectAttributes.addFlashAttribute("msgErro", "Erro ao salvar o atendimento: " + e.getMessage());
        }

        return "redirect:/atendimentos/enfermagem";
    }

    @GetMapping("/enfermagem/pendentes/{adolescenteId}")
    @ResponseBody
    public List<AtendimentoEnfermagemDTO> buscarPendentes(
            @PathVariable Long adolescenteId,
            HttpSession session) {

        boolean isMaster = (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");
        Long unidadeSession = lotacaoAtiva.getUnidade().getId();

        // Passa os dados de segurança para o Service
        return atendimentoEnfermagemService.buscarPendentes(adolescenteId, unidadeSession, isMaster);
    }

    @GetMapping("/conduta/{id}")
    public String adicionarConduta(@PathVariable Long id, Model model, HttpSession session) {
        AtendimentoEnfermagemDTO atendimentoEnfermagemDTO = atendimentoEnfermagemService.buscarPorId(id);

        model.addAttribute("atendimento", atendimentoEnfermagemDTO);
        model.addAttribute("modoConduta", true); // 🔥 Bloqueia campos clínicos

        // 5. Listas de Unidade e Adolescente (para o caso de não-Master)
        boolean isMaster = (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }
        model.addAttribute("usuarioMaster", isMaster);

        // Envia todas as listas necessárias (igual ao método novo)
        model.addAttribute("modoConduta", false);
        model.addAttribute("condutas", CondutaEnum.values());
        model.addAttribute("viasAdm", ViaAdmEnum.values());
        model.addAttribute("horarios", HorarioEnum.values());
        model.addAttribute("tiposDose", TipoDoseEnum.values());
        model.addAttribute("encerramentos", EncerramentoEnum.values());
        model.addAttribute("tiposEncaminhamento", EncaminhamentoEnum.values());
        model.addAttribute("encaminhamentosInternos", EncaminhamentoInternoEnum.values());
        model.addAttribute("encaminhamentosExternos", EncaminhamentoExternoEnum.values());
        model.addAttribute("atendimento",atendimentoEnfermagemDTO);
        model.addAttribute("usuarioMaster", isMaster);

        model.addAttribute("motivoAtendimentos", MotivoAtendimentoEnum.values());
        model.addAttribute("estadosGerais", EstadoEnum.values());
        model.addAttribute("conciencias", ConscienciaEnum.values());
        model.addAttribute("conduta", CondutaEnum.values());
        model.addAttribute("tipoDose", TipoDoseEnum.values());
        model.addAttribute("viaAdm", ViaAdmEnum.values());
        model.addAttribute("horarioMed", HorarioEnum.values());
        model.addAttribute("encaminhamento", EncaminhamentoEnum.values());
        model.addAttribute("encerramentoAtd", EncerramentoEnum.values());



        return "atendimento/enfermagem/form";
    }

    @GetMapping("/enfermagem/todos/{adolescenteId}")
    public String todosAtendimentos(@PathVariable Long adolescenteId,
                                    @RequestParam(defaultValue = "0") int page,
                                    HttpSession session, Model model,
                                    RedirectAttributes redirectAttributes) {

        // 1. Busca os dados do adolescente (Nome, Foto, etc.) para o header
        AdolescenteDTO adolescente = adolescenteService.buscarPorId(adolescenteId);
        model.addAttribute("adolescente", adolescente);



        boolean isMaster =  (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

        Long unidadeSession = lotacaoAtiva.getUnidade().getId();


        TipoFuncaoEnum tipoFuncaoUsuario = null;
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }
        if (lotacaoAtiva != null && lotacaoAtiva.getFuncao() != null) {
            tipoFuncaoUsuario = lotacaoAtiva
                    .getFuncao()
                    .getChaveSistema();
        }

        boolean podeIncluirAtendimentoEnfermagem =
                TipoFuncaoEnum.ENFERMEIRO.equals(tipoFuncaoUsuario)
                        || TipoFuncaoEnum.TEC_ENFERMAGEM.equals(tipoFuncaoUsuario);


        model.addAttribute("isMaster", isMaster);
        model.addAttribute("activeMenu","atendimentos");
        model.addAttribute("podeIncluirAtendimentoEnfermagem",podeIncluirAtendimentoEnfermagem);

        // 2. Busca os atendimentos paginados (precisa ajustar o service para retornar Page)
        // Passa a unidade e a flag para o Service
        Page<AtendimentoEnfermagemDTO> pagina = atendimentoEnfermagemService.buscarHistoricoPorAdolescente(
                adolescenteId, unidadeSession, isMaster, page);

        model.addAttribute("pagina", pagina);
        model.addAttribute("listaAtendimentos", pagina.getContent());

        if (!isMaster && pagina.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgErro", "Acesso negado. Este adolescente não pertence à sua unidade.");
            return "redirect:/atendimentos/enfermagem";
        }
        return "atendimento/enfermagem/historico";
    }
}
