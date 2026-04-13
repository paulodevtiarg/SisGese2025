package br.com.sysgese.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.ServidorRepository;
import br.com.sysgese.services.LotacaoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
    private ServidorRepository servidorRepository;
	
	@Autowired
	private LotacaoService lotacaoService;
	
	@GetMapping("/login")
    public String loginPage() {
        return "login/index";
    }
	
	@PostMapping("/login")
	public String login(
	        @RequestParam String username,
	        @RequestParam String password,
	        HttpSession session,
	        RedirectAttributes ra) {

	    Servidor servidor =
	        servidorRepository
	            .findByLoginOrCpfOrMatricula(username, username, username)
	            .orElse(null);

	    if (servidor == null || !servidor.getSenha().equals(password)) {
	        ra.addFlashAttribute("erro", "Usuário ou senha inválidos");
	        return "redirect:/login";
	    }
	
        
        Lotacao lotacao = lotacaoService.findAtivaByServidorId(servidor.getId())
                .orElse(null);
        


        
     
        if (lotacao == null) {
            ra.addFlashAttribute("erro", "Usuário sem lotação ativa");
            return "redirect:/login";
        }
        
        
       
        

        // 🔥 Calcula isMaster UMA VEZ
        boolean isMaster = Set.of(
            "MASTER",
            "CHEFIA DE PLANTÃO",
            "COORDENACAO",
            "PLANTONISTA"
        ).contains(servidor.getPerfil().getDescricao());

        
        session.setAttribute("unidadeId", lotacao.getUnidade().getId());
        session.setAttribute("lotacaoUsuarioLogado", lotacao);
	    session.setAttribute("usuarioLogado", servidor);
	    session.setAttribute("nomeUnidade", lotacao.getUnidade().getNome());
	    session.setAttribute("isMaster", isMaster);
	    
	    System.out.println("LOTACAO: " + lotacao);
	    System.out.println("UNIDADE: " + lotacao.getUnidade());

	    return "redirect:/home";
	}

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/logout-landing")
    public String logoutLanding(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
	
}
