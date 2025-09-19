package br.com.fiap.projeto_mottu.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView logar() {
		return new ModelAndView("login");
	}
	
	@GetMapping("/acesso_negado")
	public ModelAndView retornarAcessoNegado() {
		return new ModelAndView("acesso_negado");
	}
}
