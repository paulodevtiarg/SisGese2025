package br.com.sysgese.controllers;

import br.com.sysgese.dtos.CargoDTO;
import br.com.sysgese.dtos.FuncaoDTO;
import br.com.sysgese.models.Cargo;
import br.com.sysgese.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sysgese.mappers.FuncaoMapper;
import br.com.sysgese.models.Funcao;
import br.com.sysgese.services.FuncaoService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funcoes")
public class FuncaoController {
	
	@Autowired
	private FuncaoService funcaoService;
	
	@Autowired
	private FuncaoMapper funcaoMapper;

	@Autowired
	private UrlUtils urlUtils;
	
	@GetMapping
	public String index(
			@ModelAttribute("filtro") FuncaoDTO filtro,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			HttpSession session,
			Model model
	){
		boolean isMaster = (Boolean) session.getAttribute("isMaster");

		Pageable pageable = PageRequest.of(page, size);
		Page<Funcao> pagina =  funcaoService.buscarComFiltro(filtro,pageable);

		Page<FuncaoDTO> funcaoDTO = pagina.map(funcaoMapper::toDTO);
		
		//Converter para DTO
		model.addAttribute("activeMenu", "pessoal");
		model.addAttribute("funcoes", funcaoDTO);
		model.addAttribute("lista", funcaoDTO.getContent());
		model.addAttribute("pageTitle", "Funcao");
		model.addAttribute("size", size);
		model.addAttribute("queryParams", urlUtils.funcaoQuery(filtro, page));
		model.addAttribute("isMaster", AuthUtil.isMaster(session));
        
        return "funcao/index";
		
		
	};

}
