package br.com.fiap.projeto_mottu.control;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.projeto_mottu.model.Funcao;
import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.repository.FilialRepository;
import br.com.fiap.projeto_mottu.repository.FuncaoRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository repFunc;

	@Autowired
	private FuncaoRepository repFc;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private FilialRepository repFilial;

	@GetMapping("/funcionario/lista")
	public ModelAndView listarFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/lista");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
		op.ifPresent(func -> mv.addObject("funcionario", func));
		mv.addObject("funcionarios", repFunc.findAll());
		mv.addObject("lista_funcoes", repFc.findAll());
		mv.addObject("lista_filiais", repFilial.findAll());
		return mv;
	}

	@GetMapping("/funcionario/novo")
	public ModelAndView novoFuncionario() {
		ModelAndView mv = new ModelAndView("funcionario/novo");
		mv.addObject("funcionario", new Funcionario());
		mv.addObject("lista_funcoes", repFc.findAll());
		mv.addObject("lista_filiais", repFilial.findAll());
		return mv;
	}

	@PostMapping("/funcionario/novo")
	public ModelAndView inserirFuncionario(Funcionario funcionario, @RequestParam(name = "id_funcao") Long id_funcao) {
		funcionario.setNm_senha(encoder.encode(funcionario.getNm_senha()));
		Set<Funcao> funcoes = new HashSet<>();
		if (id_funcao != null) {
			funcoes.add(repFc.findById(id_funcao).orElse(null));
		}
		funcionario.setFuncoes(funcoes);
		repFunc.save(funcionario);
		return new ModelAndView("redirect:/funcionario/lista");
	}

	@GetMapping("/funcionario/editar")
	public ModelAndView editarFuncionarioForm(@RequestParam Long id) {
		ModelAndView mv = new ModelAndView("funcionario/editar");
		Funcionario funcionario = repFunc.findById(id).orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
		mv.addObject("funcionario", funcionario);
		mv.addObject("lista_funcoes", repFc.findAll());
		mv.addObject("lista_filiais", repFilial.findAll());
		return mv;
	}

	@PostMapping("/funcionario/editar")
	public ModelAndView editarFuncionario(Funcionario funcionario, @RequestParam(name = "id_funcao") Long id_funcao) {
		funcionario.setNm_senha(encoder.encode(funcionario.getNm_senha()));
		Set<Funcao> funcoes = new HashSet<>();
		if (id_funcao != null) {
			funcoes.add(repFc.findById(id_funcao).orElse(null));
		}
		funcionario.setFuncoes(funcoes);
		repFunc.save(funcionario);
		return new ModelAndView("redirect:/funcionario/lista");
	}

	@GetMapping("/funcionario/excluir/{id}")
	public ModelAndView removerFuncionarioExistente(@PathVariable Long id) {
		repFunc.deleteById(id);
		return new ModelAndView("redirect:/funcionario/lista");
	}
}
