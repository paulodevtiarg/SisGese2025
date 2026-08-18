package br.com.sysgese.controllers;

import br.com.sysgese.dtos.AtendimentoEnfermagemDTO;
import br.com.sysgese.dtos.AtendimentoEnfermagemResumoDTO;
import br.com.sysgese.mappers.AtendimentoEnfermagemMapper;
import br.com.sysgese.models.AtendimentoEnfermagem;
import br.com.sysgese.models.Lotacao;
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

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoController {
    @Autowired
    private AtendimentoEnfermagemService atendimentoEnfermagemService;
    @Autowired
    private UnidadeService unidadeService;
    @Autowired
    private AtendimentoEnfermagemMapper atendimentoMapper;
    @Autowired
    private UrlUtils urlUtils;

/*
    @GetMapping("/enfermagem")
    public String indexAtendimentoEnfermagem(
            @ModelAttribute("filtro")
            AtendimentoEnfermagemDTO filtro,
            @RequestParam(value = "page", defaultValue = "0")int page,
            @RequestParam(value = "size", defaultValue = "10")int size,
            HttpSession session,
            Model model) {

        boolean isMaster = (Boolean) session.getAttribute("isMaster");
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");
        Long unidadeSession =  (Long) session.getAttribute("unidadeId");


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


        // ==========================================================
        // TAMANHO DA PÁGINA
        // ==========================================================

        if (filtro.getSize() != null) {
            size = filtro.getSize();
        } else {
            filtro.setSize(size);
        }

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                Sort.Direction.DESC,
                                "data"
                        )
                );




        Page<AtendimentoEnfermagem> pagina =  atendimentoEnfermagemService.buscarComFiltro(filtro, unidadeSession,isMaster, pageable );

        Page<AtendimentoEnfermagemDTO> paginaDTO = pagina.map(atendimentoMapper::toDTO);
        model.addAttribute("pagina", paginaDTO);
        model.addAttribute("lista", paginaDTO.getContent());
        model.addAttribute("isMaster",isMaster);
        model.addAttribute("size",size);
        model.addAttribute("queryParams",urlUtils.atendimentoEnfermagemQuery(filtro,page));
        model.addAttribute("pageTitle","Atendimento de Enfermagem");
        model.addAttribute("activeMenu","atendimentos");
        model.addAttribute("unidadeId",unidadeFiltro);


        return "atendimento/enfermagem/index";
    } */

    @GetMapping("/enfermagem")
    public String indexAtendimentoEnfermagem(

            @ModelAttribute("filtro")
            AtendimentoEnfermagemDTO filtro,

            @RequestParam(value = "page", defaultValue = "0")
            int page,

            @RequestParam(value = "size", defaultValue = "10")
            int size,

            HttpSession session,
            Model model) {


        boolean isMaster =
                (Boolean) session.getAttribute("isMaster");

        Lotacao lotacaoAtiva =
                (Lotacao) session.getAttribute(
                        "lotacaoUsuarioLogado"
                );

        Long unidadeSession =
                (Long) session.getAttribute("unidadeId");
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }

        // ==========================================================
        // UNIDADE UTILIZADA NO FILTRO
        // ==========================================================

        Long unidadeFiltro;

        if (isMaster) {

            unidadeFiltro =
                    (filtro.getFiltroUnidadeId() != null)
                            ? filtro.getFiltroUnidadeId()
                            : lotacaoAtiva.getUnidade().getId();

        } else {

            unidadeFiltro =
                    lotacaoAtiva.getUnidade().getId();
        }
        model.addAttribute("lotacaoUsuarioLogado", lotacaoAtiva );

        // ==========================================================
        // TAMANHO DA PÁGINA
        // ==========================================================

        if (filtro.getSize() != null) {

            size = filtro.getSize();

        } else {

            filtro.setSize(size);
        }


        // ==========================================================
        // PAGINAÇÃO
        // ==========================================================

        Pageable pageable =
                PageRequest.of(
                        page,
                        size
                );


        // ==========================================================
        // BUSCA UM REGISTRO POR ADOLESCENTE
        // ==========================================================

        Page<AtendimentoEnfermagemResumoDTO> pagina =
                atendimentoEnfermagemService
                        .buscarResumoPorAdolescente(
                                filtro,
                                unidadeSession,
                                isMaster,
                                pageable
                        );


        // ==========================================================
        // MODEL
        // ==========================================================

        model.addAttribute("pagina", pagina);

        model.addAttribute(
                "lista",
                pagina.getContent()
        );

        model.addAttribute(
                "isMaster",
                isMaster
        );

        model.addAttribute(
                "size",
                size
        );

        model.addAttribute(
                "queryParams",
                urlUtils.atendimentoEnfermagemQuery(
                        filtro,
                        page
                )
        );

        model.addAttribute(
                "pageTitle",
                "Atendimento de Enfermagem"
        );

        model.addAttribute(
                "activeMenu",
                "atendimentos"
        );

        model.addAttribute(
                "unidadeId",
                unidadeFiltro
        );


        return "atendimento/enfermagem/index";
    }


}
