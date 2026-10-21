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
        model.addAttribute("encamisExterno", EncaminhamentoEnum.values());
        model.addAttribute("encerramentos", EncerramentoEnum.values());


        model.addAttribute("atendimento",atendimentoEnfermagemDTO);
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
    @PostMapping("/enfermagem/salvar")
    public String salvar(
            @ModelAttribute("filtro") AtendimentoEnfermagemDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            @Valid @ModelAttribute("atendimento") AtendimentoEnfermagemDTO dto,
            BindingResult resultAtendimento,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        boolean isMaster = (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

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



            // Master recebe lista de unidades
            if (isMaster) {
                model.addAttribute("unidades", unidadeService.listarTodas());
            }
            //Carregar Enums
            model.addAttribute("condutas", CondutaEnum.values());
            model.addAttribute("viasAdm", ViaAdmEnum.values());
            model.addAttribute("horarios", HorarioEnum.values());
            model.addAttribute("tiposDose", TipoDoseEnum.values());
            model.addAttribute("encamisExterno", EncaminhamentoEnum.values());
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

            redirectAttributes.addFlashAttribute("msgErro", "Erro ao salvar o atendimento: " + e.getMessage());
        }

        return "redirect:/atendimentos/enfermagem";
    }
}
