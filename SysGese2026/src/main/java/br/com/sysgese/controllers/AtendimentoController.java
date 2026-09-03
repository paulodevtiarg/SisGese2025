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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        if (!isMaster) {
            atendimentoEnfermagemDTO.setIdUnidade(unidadeFiltro);
            atendimentoEnfermagemDTO.setIdServidor(lotacaoAtiva.getServidor().getId());
        }
        // Master recebe lista de unidades
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }

        model.addAttribute("atendimento",atendimentoEnfermagemDTO);
        model.addAttribute("usuarioMaster", isMaster);
        model.addAttribute("adolescentesUnidade", adolescentesUnidade);
        model.addAttribute("motivoAtendimento", MotivoAtendimentoEnum.values());
        model.addAttribute("estadoGeral", EstadoEnum.values());
        model.addAttribute("conciencia", ConscienciaEnum.values());
        model.addAttribute("conduta", CondutaEnum.values());
        model.addAttribute("tipoDose", TipoDoseEnum.values());
        model.addAttribute("viaAdm", ViaAdmEnum.values());
        model.addAttribute("horarioMed", HorarioEnum.values());
        model.addAttribute("encaminhamento", EncaminhamentoEnum.values());
        model.addAttribute("encerramentoAtd", EncerramentoEnum.values());

        return "atendimento/enfermagem/form";

    }
}
