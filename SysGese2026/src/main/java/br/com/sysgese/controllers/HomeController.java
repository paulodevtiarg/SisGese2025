package br.com.sysgese.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sysgese.models.Servidor;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home(HttpSession session, Model model) {

        Servidor usuario = (Servidor) session.getAttribute("usuarioLogado");

        // 🔒 se não estiver logado → volta para login
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("pageTitle", "Home - Sisgese");

        return "home/index";
    }
}
