package br.com.sysgese.controllers;

import br.com.sysgese.dtos.CargoDTO;
import br.com.sysgese.dtos.InternacaoDTO;
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

import br.com.sysgese.mappers.CargoMapper;
import br.com.sysgese.models.Cargo;
import br.com.sysgese.services.CargoService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private CargoMapper cargoMapper;

	@Autowired
	private UrlUtils urlUtils;
	
	@GetMapping
	public String index(
			@ModelAttribute("filtro") CargoDTO filtro,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			HttpSession session,
			Model model
	){
		boolean isMaster = (Boolean) session.getAttribute("isMaster");

		Pageable pageable = PageRequest.of(page, size);
		Page<Cargo> pagina =  cargoService.buscarComFiltro(filtro,pageable);

		Page<CargoDTO> cargoDTO = pagina.map(cargoMapper::toDTO);

		model.addAttribute("activeMenu", "pessoal");
		model.addAttribute("cargos", cargoDTO);
		model.addAttribute("lista", cargoDTO.getContent());
		model.addAttribute("pageTitle", "Cargo");
		model.addAttribute("size", size);
		model.addAttribute("queryParams", urlUtils.cargoQuery(filtro, page));
        model.addAttribute("isMaster", AuthUtil.isMaster(session));
        
        return "cargo/index";
		
		
	};

}
