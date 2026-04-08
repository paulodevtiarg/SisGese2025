package br.com.sysgese.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sysgese.mappers.PerfilMapper;
import br.com.sysgese.models.Perfil;
import br.com.sysgese.services.PerfilService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {
   	    @Autowired
	    private PerfilService perfilService;

    @Autowired
    private PerfilMapper perfilMapper;
    
    @GetMapping("/perfil")
    public String index(
            HttpSession session,
            Model model,
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "status", required = false, defaultValue = "1") String statusFiltro,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "10") int tamanho
    ) {
       

    	// Transformar statusFiltro em array de inteiros
    	Integer[] status = StatusUtil.parseStatusFiltro(statusFiltro);


        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Perfil> page = perfilService.buscar(nome, status, pageable);

        // Converter para DTOs
        model.addAttribute("pageTitle", "Perfil");
        model.addAttribute("activeMenu", "administrativo");
        model.addAttribute("perfis", perfilMapper.toDTOList(page.getContent()));
        model.addAttribute("paginaAtual", page.getNumber());
        model.addAttribute("totalPaginas", page.getTotalPages());
        model.addAttribute("tamanhoPagina", tamanho);
        model.addAttribute("nomeBusca", nome);
        model.addAttribute("statusFiltro", statusFiltro);
        model.addAttribute("isMaster", AuthUtil.isMaster(session));

        return "perfil/index";
    }

}
