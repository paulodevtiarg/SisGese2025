package br.com.sysgese.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
	
	 // 🔥 raiz do sistema
    @GetMapping("/")
    public String raiz() {
        return "landing/index";
    }
	
	@GetMapping("/landing")
    public String landingPage() {
        return "landing/index";
    }
	

}
