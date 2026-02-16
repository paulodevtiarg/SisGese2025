package br.com.sysgese.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.services.AdolescenteService;
import br.com.sysgese.services.InternacaoService;
import br.com.sysgese.services.LotacaoService;
import br.com.sysgese.specifications.InternacaoSpecification;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdolescenteController {
	
	@Autowired
    private AdolescenteService adolescenteService;
	
	   @Autowired
	    private LotacaoService lotacaoService;
	   
	   @Autowired
	    private InternacaoService internacaoService;
	
	@GetMapping("/adolescente")
    public String index(
            HttpSession session,
            Model model,
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "apelido", required = false, defaultValue = "") String apelido,
            @RequestParam(value = "cidade", required = false, defaultValue = "") String cidade,
            @RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
            @RequestParam(value = "idadeMin", required = false) Integer idadeMin,
            @RequestParam(value = "idadeMax", required = false) Integer idadeMax,
            @RequestParam(value = "status", required = false, defaultValue = "1") String statusFiltro,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "10") int tamanho
    ) {
		
		// Pega a unidade do usuário logado
		Servidor usuarioLogado = (Servidor) session.getAttribute("usuarioLogado");

        // Converte status do filtro em Integer[]
        Integer[] status = StatusUtil.parseStatusFiltro(statusFiltro);
        
        Pageable pageable = PageRequest.of(pagina, tamanho);
        
     // =========================
        // Pega a unidade do usuário via lotação ativa
        // =========================
        Optional<Lotacao> lotacaoOpt = lotacaoService.findAtivaByServidorId(usuarioLogado.getId());

        if (lotacaoOpt.isEmpty()) {
            model.addAttribute("mensagemErro", "Usuário não possui lotação ativa.");
            return "adolescente/index";
        }

        Lotacao lotacaoAtiva = lotacaoOpt.get();
        
        Long unidadeId = lotacaoOpt.get().getUnidade().getId();
        
        System.out.println("UNIDADE LOGADA: " + unidadeId);
        Page<AdolescenteDTO> page =
                adolescenteService.buscarAdolescentesInternadosNaUnidade(
                        lotacaoAtiva.getUnidade().getId(),
                        nome,
                        apelido,
                        cidade,
                        cpf,
                        idadeMin,
                        idadeMax,
                        status,
                        pageable
                );
        
        // Popula o model para a view
        model.addAttribute("pageTitle", "Adolescentes");
        model.addAttribute("adolescentes", page.getContent());
        model.addAttribute("paginaAtual", page.getNumber());
        model.addAttribute("totalPaginas", page.getTotalPages());
        model.addAttribute("tamanhoPagina", tamanho);
        model.addAttribute("nomeBusca", nome);
        model.addAttribute("apelidoBusca", apelido);
        model.addAttribute("cidadeBusca", cidade);
        model.addAttribute("cpfBusca", cpf);
        model.addAttribute("idadeMin", idadeMin);
        model.addAttribute("idadeMax", idadeMax);
        model.addAttribute("statusFiltro", statusFiltro);
        model.addAttribute("isMaster", AuthUtil.isMaster(session));

        return "adolescente/index";
		
		
	}

}
