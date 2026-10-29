package br.com.sysgese.controllers;

import br.com.sysgese.dtos.FuncaoDTO;
import br.com.sysgese.dtos.PerfilDTO;
import br.com.sysgese.models.Funcao;
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

import br.com.sysgese.mappers.PerfilMapper;
import br.com.sysgese.models.Perfil;
import br.com.sysgese.services.PerfilService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfis")
public class PerfilController {
   	    @Autowired
	    private PerfilService perfilService;

    @Autowired
    private PerfilMapper perfilMapper;

    @Autowired
    private UrlUtils urlUtils;


    @GetMapping
    public String index(
            @ModelAttribute("filtro") PerfilDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session,
            Model model
    ) {


        boolean isMaster = (Boolean) session.getAttribute("isMaster");

        Pageable pageable = PageRequest.of(page, size);
        Page<Perfil> pagina =  perfilService.buscarComFiltro(filtro,pageable);

        Page<PerfilDTO> perfilDTO = pagina.map(perfilMapper::toDTO);

        // Converter para DTOs
        model.addAttribute("activeMenu", "pessoal");
        model.addAttribute("perfis", perfilDTO);
        model.addAttribute("lista", perfilDTO.getContent());
        model.addAttribute("pageTitle", "Funcao");
        model.addAttribute("size", size);
        model.addAttribute("queryParams", urlUtils.perfilQuery(filtro, page));
        model.addAttribute("isMaster", AuthUtil.isMaster(session));

        return "perfil/index";
    }

}
