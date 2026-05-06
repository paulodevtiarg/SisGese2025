package br.com.sysgese.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sysgese.models.Servidor;
import br.com.sysgese.services.AdolescenteService;
import br.com.sysgese.services.InternacaoService;
import jakarta.servlet.http.HttpSession;
import tools.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private AdolescenteService adolescenteService;
	
	@Autowired
	private InternacaoService internacaoService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public String home( @RequestParam(required = false) Integer ano,
			HttpSession session, 
			Model model) {

		 int anoAtual = (ano != null) ? ano : LocalDate.now().getYear();

	    Servidor usuario = (Servidor) session.getAttribute("usuarioLogado");
	    Long unidadeId = (Long) session.getAttribute("unidadeId");
	    String nomeUnidade = (String) session.getAttribute("nomeUnidade");

	    // 🔒 se não estiver logado → volta para login
	    if (usuario == null) {
	        return "redirect:/login";
	    }

	    Long totalAdolescentes = adolescenteService
	            .contarPorUnidade(unidadeId);

	    Long totalGeral = adolescenteService
	            .contarTodos();

	    Long internadosUnidade = internacaoService
	            .contarInternadosPorUnidade(unidadeId);

	    Long internadosGeral = internacaoService
	            .contarInternadosGeral();

	    // ✅ GRAFICO
	    Map<Integer, Long> internacoesPorMes =
	            internacaoService.buscarInternacoesPorMesPorUnidade(unidadeId, anoAtual);

	    try {
	        String json = objectMapper.writeValueAsString(internacoesPorMes);
	        model.addAttribute("internacoesJson", json);
	    } catch (Exception e) {
	        model.addAttribute("internacoesJson", "{}"); // fallback
	        e.printStackTrace();
	    }
	    
	   Map<Integer, Long> internacoesPorMesGeral=internacaoService.buscarInternacoesPorMes(anoAtual);
	   try {
	        String json = objectMapper.writeValueAsString(internacoesPorMesGeral);
	        model.addAttribute("internacoesGeralJson", json);
	    } catch (Exception e) {
	        model.addAttribute("internacoesGeralJson", "{}"); // fallback
	        e.printStackTrace();
	    }
	   
	   
	   Map<String, Long> internacoesPorUnidade =
		        internacaoService.buscarInternacoesPorUnidade(anoAtual);

		try {
		    String json = objectMapper.writeValueAsString(internacoesPorUnidade);
		    model.addAttribute("internacoesPorUnidadeJson", json);
		} catch (Exception e) {
		    model.addAttribute("internacoesPorUnidadeJson", "{}");
		}
	   
	   /*
	   Map<String, Long> fake = new HashMap<>();
	   fake.put("Unidade A", 5L);
	   fake.put("Unidade B", 3L);

	   model.addAttribute("internacoesPorUnidadeJson",
	           objectMapper.writeValueAsString(fake));*/
	   

	    model.addAttribute("totalAdolescentes", totalAdolescentes);
	    model.addAttribute("nomeUnidade", nomeUnidade);
	    model.addAttribute("usuario", usuario);
	    model.addAttribute("activeMenu", "dashboard");
	    model.addAttribute("totalGeral", totalGeral);
	    model.addAttribute("internadosUnidade", internadosUnidade);
	    model.addAttribute("internadosGeral", internadosGeral);
	    model.addAttribute("anoSelecionado", anoAtual);
	    model.addAttribute("pageTitle", "Home - Sisgese");

	    return "home/index";
	}
}
