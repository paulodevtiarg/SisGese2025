package br.com.sysgese.controllers;

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

import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.enumerators.TipoMedidaEnum;
import br.com.sysgese.mappers.InternacaoMapper;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.services.InternacaoService;
import br.com.sysgese.services.UnidadeService;
import br.com.sysgese.utils.UrlUtils;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/internacoes")
public class InternacaoController {
	
	@Autowired
	private InternacaoService internacaoService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private InternacaoMapper internacaoMapper;
	
	@Autowired
	private UrlUtils urlUtils;
	

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
    
    

    // 🔥 AQUI É O PULO DO GATO
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

}
