package br.com.fiap.projeto_mottu.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping({"/index", "/home"})
    public ModelAndView home() {
        return new ModelAndView("home/index");
    }
}
