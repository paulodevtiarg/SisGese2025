package br.com.sysgese.controllers;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.enumerators.*;
import br.com.sysgese.mappers.InternacaoMapper;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.services.AdolescenteService;
import br.com.sysgese.services.InternacaoService;
import br.com.sysgese.services.UnidadeService;
import br.com.sysgese.utils.UrlUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
import java.util.List;

@Controller
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;
    private final UnidadeService unidadeService;
    private final InternacaoMapper internacaoMapper;
    private final AdolescenteService adolescenteService;
    private final UrlUtils urlUtils;

    public InternacaoController(
            InternacaoService internacaoService,
            UnidadeService unidadeService,
            InternacaoMapper internacaoMapper,
            AdolescenteService adolescenteService,
            UrlUtils urlUtils
    ) {
        this.internacaoService = internacaoService;
        this.unidadeService = unidadeService;
        this.internacaoMapper = internacaoMapper;
        this.adolescenteService = adolescenteService;
        this.urlUtils = urlUtils;
    }
	

@GetMapping
public String index(
        @ModelAttribute("filtro") InternacaoDTO filtro,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        HttpSession session,
        Model model) {

	boolean isMaster = (Boolean) session.getAttribute("isMaster");         

    Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");
    
    Long unidadeFiltro;

    if (isMaster) {
        // MASTER pode escolher unidade
        unidadeFiltro = (filtro.getFiltroUnidadeId() != null) 
            ? filtro.getFiltroUnidadeId() 
            : lotacaoAtiva.getUnidade().getId(); // padrão
    } else {
        // NÃO MASTER sempre usa a própria unidade
        unidadeFiltro = lotacaoAtiva.getUnidade().getId();
    }

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by("dataInicio").descending()
    );

    Page<Internacao> pagina = internacaoService
            .buscarComFiltro(filtro, unidadeFiltro, isMaster, pageable);



    Page<InternacaoDTO> paginaDTO = pagina.map(internacaoMapper::toDTO);

    model.addAttribute("pagina", paginaDTO);
    model.addAttribute("lista", paginaDTO.getContent());

    if (isMaster) {
        model.addAttribute("unidades", unidadeService.listarTodas());
    }

    model.addAttribute("isMaster", isMaster);
    model.addAttribute("size", size);
    model.addAttribute("queryParams", urlUtils.internacaoQuery(filtro, page));
    model.addAttribute("pageTitle", "Internações");
    model.addAttribute("statusList", StatusInternacaoEnum.values());
    model.addAttribute("tipoMedidaList", TipoMedidaEnum.values());
    model.addAttribute("unidadeId", unidadeFiltro);

    return "internacao/index";
}

    @GetMapping("/novo")
    public String novo(
            @ModelAttribute("filtro") InternacaoDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            Model model
    ){
        boolean isMaster = (Boolean) session.getAttribute("isMaster");

        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

        Long unidadeFiltro = lotacaoAtiva.getUnidade().getId();

        List<AdolescenteDTO> adolescentesElegiveis =   adolescenteService.buscarElegiveisParaInternacao(unidadeFiltro,  isMaster );

        InternacaoDTO internacao = new InternacaoDTO();

        internacao.setStatus(StatusInternacaoEnum.ATIVA);

        // Se NÃO for master, já define a unidade automaticamente
        if (!isMaster) {
            internacao.setIdUnidade(unidadeFiltro);
        }

        model.addAttribute("internacao", internacao);

        // Master recebe lista de unidades
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }
        //Carregar o objeto
        model.addAttribute("usuarioMaster", isMaster);
        model.addAttribute("adolescentesElegiveis", adolescentesElegiveis);
        model.addAttribute("tipoMedidas", TipoMedidaEnum.values());
        model.addAttribute("motivos", MotivoEnum.values());
        model.addAttribute("statusInternacqao", StatusInternacaoEnum.values());
        model.addAttribute("documentosApresentado", DocumentosEnum.values());
        model.addAttribute("procedencias", ProcedenciaEnum.values());
        model.addAttribute("activeMenu", "gestao");
        model.addAttribute("queryParams", urlUtils.internacaoQuery(filtro, page));
        model.addAttribute("pageTitle", "Internações");

        return "internacao/form";
    }
    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute("filtro") InternacaoDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            @Valid @ModelAttribute("internacao") InternacaoDTO dto,
            BindingResult resultInternacao,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {


        // 🚨 VALIDAÇÃO
        if (resultInternacao.hasErrors()) {
            boolean isMaster = (Boolean) session.getAttribute("isMaster");

            Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

            Long unidadeFiltro = lotacaoAtiva.getUnidade().getId();

            List<AdolescenteDTO> adolescentesElegiveis = adolescenteService.buscarElegiveisParaInternacao(unidadeFiltro, isMaster);

            // Master recebe lista de unidades
            if (isMaster) {
                model.addAttribute("unidades", unidadeService.listarTodas());
            }
            model.addAttribute("usuarioMaster", isMaster);
            model.addAttribute("adolescentesElegiveis", adolescentesElegiveis);
            model.addAttribute("tipoMedidas", TipoMedidaEnum.values());
            model.addAttribute("motivos", MotivoEnum.values());
            model.addAttribute("statusInternacqao", StatusInternacaoEnum.values());
            model.addAttribute("documentosApresentado", DocumentosEnum.values());
            model.addAttribute("procedencias", ProcedenciaEnum.values());
            model.addAttribute("activeMenu", "gestao");
            model.addAttribute("queryParams", urlUtils.internacaoQuery(filtro, page));
            model.addAttribute("pageTitle", "Internações");

            return "internacao/form";
        }
        try {
            internacaoService.salvar(dto);
            redirectAttributes.addFlashAttribute("msgOk", "Registro de Internação salvo com sucesso!");

        }
        catch (Exception e) {

            redirectAttributes.addFlashAttribute("msgErro", "Erro ao salvar o Registtro: " + e.getMessage());
        }

        return "redirect:/internacoes";
    }
}
