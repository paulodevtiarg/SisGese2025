package br.com.sysgese.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.ServidorRepository;
import br.com.sysgese.services.CodigoSegurancaService;
import br.com.sysgese.services.EmailService;

@Controller
public class ForgotPasswordController {
	  @Autowired
	    private ServidorRepository servidorRepository;

	    @Autowired
	    private CodigoSegurancaService codigoService;

	    @Autowired
	    private EmailService emailService;

	    @PostMapping("/esqueci-senha")
	    public String esqueciSenha(@RequestParam String login,
	                               RedirectAttributes ra) {

	        Servidor servidor = servidorRepository
	                .findByLoginOrCpfOrMatricula(login, login, login)
	                .orElse(null);

	        if (servidor == null) {
	            ra.addFlashAttribute("erro", "Usuário não encontrado");
	            return "redirect:/login?modo=forgot";
	        }

	        String codigo = codigoService.gerarCodigo();

	        servidor.setCodigoSeguranca(codigo);
	        servidor.setDataExpiracao(LocalDateTime.now().plusMinutes(15));

	        servidorRepository.save(servidor);

	        emailService.enviarCodigo(
	        	    servidor.getEmail(),
	        	    codigo,
	        	    servidor.getPrimeiroAcesso()
	        	);
	        
	        // 🔥 AQUI:
	        ra.addFlashAttribute("primeiroAcesso", servidor.getPrimeiroAcesso());

	        return "redirect:/login?modo=reset&login=" + login;
	    }
	    
	    @GetMapping("/validar-reset-senha")
	    public String tela() {
	        return "login/reset-senha";
	    }
	    
	    @GetMapping("/esqueci-senha")
	    public String tela(Model model) {
	        model.addAttribute("recuperacao", true);
	        return "login/index";
	    }
	    
	    @GetMapping("/reset-senha")
	    public String telaReset(@RequestParam String login, Model model) {
	    	  model.addAttribute("modo", "reset");
	    	
	        model.addAttribute("login", login);
	        return "login/reset-senha";
	    }
	    
	    
	    @PostMapping("/reset-senha")
	    public String resetSenha(@RequestParam String login,
	                              @RequestParam String codigo,
	                              @RequestParam String senha,
	                              @RequestParam String confirmarSenha,
	                              RedirectAttributes ra) {

	        if (!senha.equals(confirmarSenha)) {
	            ra.addFlashAttribute("erro", "Senhas não conferem");
	            return "redirect:/login?modo=reset&login=" + login;
	        }

	        Servidor servidor = servidorRepository
	                .findByLoginOrCpfOrMatricula(login, login, login)
	                .orElse(null);

	        if (servidor == null) {
	            return "redirect:/login?modo=login";
	        }

	        if (servidor.getDataExpiracao() == null ||
	            servidor.getDataExpiracao().isBefore(LocalDateTime.now())) {

	            ra.addFlashAttribute("erro", "Código expirado");
	            return "redirect:/login?modo=forgot";
	        }

	        if (!codigo.equals(servidor.getCodigoSeguranca())) {
	            ra.addFlashAttribute("erro", "Código inválido");
	            return "redirect:/login?modo=reset&login=" + login;
	        }

	        servidor.setSenha(senha);
	        // 🔥 AQUI
	        servidor.setPrimeiroAcesso(false);
	        servidor.setCodigoSeguranca(null);
	        servidor.setDataExpiracao(null);

	        servidorRepository.save(servidor);

	        return "redirect:/login?modo=login";
	    }
}
