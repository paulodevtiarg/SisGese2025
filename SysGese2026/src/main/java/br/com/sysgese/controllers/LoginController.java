package br.com.sysgese.controllers;

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
@RequestMapping("/")
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
	 // ===== Aqui vamos carregar a lotação =====
	    // ===== Carrega a lotação ativa do servidor =====
       /*LotacaoDTO lotacao = lotacaoService.findAtivaDTOByServidorId(servidor.getId())
                                          // .orElse(null); // se não tiver lotação ativa, fica null */
        
        Lotacao lotacao = lotacaoService.findAtivaByServidorId(servidor.getId())
                .orElse(null);
        
        session.setAttribute("lotacaoUsuarioLogado", lotacao);
	    session.setAttribute("usuarioLogado", servidor);

	    return "redirect:/home";
	}

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
	
}
