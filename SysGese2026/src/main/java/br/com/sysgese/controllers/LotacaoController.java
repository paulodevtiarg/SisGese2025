package br.com.sysgese.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sysgese.mappers.LotacaoMapper;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.services.LotacaoService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;

@Controller

public class LotacaoController {
	@Autowired
	LotacaoService lotacaoService;
	
	@Autowired
	LotacaoMapper lotacaoMapper;
	
	
	@GetMapping("/lotacao")
	public String index(
	        Model model,
	        HttpSession session,
	        @RequestParam(required = false) String servidor,
	        @RequestParam(required = false) String unidade,
	        @RequestParam(required = false) String cargo,
	        @RequestParam(required = false) String funcao,
	        @RequestParam(defaultValue = "1") String statusFiltro,
	        @RequestParam(defaultValue = "0") int pagina,
	        @RequestParam(defaultValue = "10") int tamanho
	) {

	    Integer[] status = StatusUtil.parseStatusFiltro(statusFiltro);
	    Pageable pageable = PageRequest.of(pagina, tamanho);

	    Page<Lotacao> page = lotacaoService.buscar(
	            servidor,
	            unidade,
	            cargo,
	            funcao,
	            status,
	            pageable
	    );
		model.addAttribute("pageTitle", "Lotacao");	
        model.addAttribute("activeMenu", "administrativo");
		model.addAttribute("lotacoes",  lotacaoMapper.toDTOList(page.getContent()));
	    model.addAttribute("paginaAtual", page.getNumber());
	    model.addAttribute("totalPaginas", page.getTotalPages());
	    model.addAttribute("isMaster", AuthUtil.isMaster(session));

	    return "lotacao/index";
	}
}
