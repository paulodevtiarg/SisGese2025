package br.com.sysgese.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	/*
	@GetMapping("/login")
    public String loginPage() {
        return "login/index";
    } */
	
	@GetMapping("/login")
	public String loginPage(@RequestParam(required = false) String modo,
	                        @RequestParam(required = false) String login,
	                        Model model) {

	    model.addAttribute("modo", modo == null ? "login" : modo);
	    model.addAttribute("login", login);

	    return "login/index";
	}
	
	@PostMapping("/login")
	public String login(
	        @RequestParam String username,
	        @RequestParam String password,
	        HttpSession session,
	        @RequestParam String loginType, // <-- Recebe o tipo de login
	        RedirectAttributes ra) {

		// 1. VALIDAÇÃO DE FORMATO BASEADA NO TIPO
		if ("matricula".equals(loginType)) {
			// Se for matrícula, só pode ter números
			if (!username.matches("\\d+")) {
				ra.addFlashAttribute("erro", "Usuário ou senha inválidos");
				return "redirect:/login";
			}
		} else if ("cpf".equals(loginType)) {
			// Se for CPF, remove a máscara para contar os números
			String cpfLimpo = username.replaceAll("\\D", "");
			if (cpfLimpo.length() != 11) {
				ra.addFlashAttribute("erro", "Usuário ou senha inválidos");
				return "redirect:/login";
			}
			// Opcional: passar o CPF formatado ou limpo para o banco, dependendo de como está salvo
			// username = cpfLimpo;
		}
		// Se for "login", não precisa validar formato, aceita letras e números.

		// 2. BUSCA NO BANCO USANDO A COLUNA CORRETA
		Servidor servidor = null;

		if ("matricula".equals(loginType)) {
			servidor = servidorRepository.findByMatricula(username).orElse(null);
		} else if ("cpf".equals(loginType)) {
			// Se no banco o CPF está salvo com máscara (ex: 123.456.789-00), use o username original.
			// Se estiver salvo só números, use o cpfLimpo.
			servidor = servidorRepository.findByCpf(username).orElse(null);
		} else { // login
			servidor = servidorRepository.findByLogin(username).orElse(null);
		}

		// 3. VALIDAÇÃO DE SENHA E CONTINUAÇÃO
		if (servidor == null || !servidor.getSenha().equals(password)) {
			ra.addFlashAttribute("erro", "Usuário ou senha inválidos");
			return "redirect:/login";
		}
	    
	     // 🔥 NOVO: bloqueia primeiro acesso
	    if (servidor.getPrimeiroAcesso() != null && servidor.getPrimeiroAcesso()) {
	        session.setAttribute("usuarioTemp", servidor);
	        return "redirect:/validar-codigo";
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
