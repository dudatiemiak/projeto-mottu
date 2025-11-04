package br.com.fiap.projeto_mottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.projeto_mottu.model.Moto;
import br.com.fiap.projeto_mottu.model.Funcionario;
// import removido: br.com.fiap.projeto_mottu.model.Modelo
import br.com.fiap.projeto_mottu.repository.ModeloRepository;
import br.com.fiap.projeto_mottu.model.SituacaoEnum;
import br.com.fiap.projeto_mottu.repository.MotoRepository;
import br.com.fiap.projeto_mottu.repository.FilialDepartamentoRepository;
import br.com.fiap.projeto_mottu.repository.ClienteRepository;
import br.com.fiap.projeto_mottu.repository.ManutencaoRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;
import jakarta.validation.Valid;

@Controller
public class MotoController {

	@Autowired
	private MotoRepository repM;
	@Autowired
	private ModeloRepository modeloRepository;
	@Autowired
	private FilialDepartamentoRepository repFD;
	@Autowired
	private ClienteRepository repC;
	@Autowired
	private ManutencaoRepository repManutencao;
	@Autowired
	private FuncionarioRepository repFunc;

	@GetMapping("/moto/lista")
	public ModelAndView listarMotos() {
		ModelAndView mv = new ModelAndView("moto/lista");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
		if(op.isPresent()) {
			mv.addObject("funcionario", op.get());
		}
		mv.addObject("motos", repM.findAll());
		return mv;
	}

	@GetMapping("/moto/nova")
	public ModelAndView novaMoto() {
		ModelAndView mv = new ModelAndView("moto/nova");
		mv.addObject("moto", new Moto());
		mv.addObject("filiaisDepartamentos", repFD.findAll());
		mv.addObject("clientes", repC.findAll());
		mv.addObject("lista_modelos", modeloRepository.findAll());
		mv.addObject("lista_situacoes", SituacaoEnum.values());
		return mv;
	}

	@PostMapping("/moto/inserir")
	public ModelAndView inserirMoto(@Valid Moto moto, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("moto/nova");
			mv.addObject("moto", moto);
			mv.addObject("filiaisDepartamentos", repFD.findAll());
			mv.addObject("clientes", repC.findAll());
			mv.addObject("lista_modelos", modeloRepository.findAll());
			mv.addObject("lista_situacoes", SituacaoEnum.values());
			return mv;
		}
		repM.save(moto);
		return new ModelAndView("redirect:/moto/lista");
	}

	@GetMapping("/moto/editar/{id}")
	public ModelAndView editarMotoForm(@PathVariable Long id) {
		Optional<Moto> op = repM.findById(id);
		if (op.isPresent()) {
			ModelAndView mv = new ModelAndView("moto/editar");
			mv.addObject("moto", op.get());
			mv.addObject("filiaisDepartamentos", repFD.findAll());
			mv.addObject("clientes", repC.findAll());
			mv.addObject("lista_modelos", modeloRepository.findAll());
			mv.addObject("lista_situacoes", SituacaoEnum.values());
			return mv;
		}
		return new ModelAndView("redirect:/moto/lista");
	}

	@PostMapping("/moto/editar/{id}")
	public ModelAndView editarMoto(@PathVariable Long id, @Valid Moto moto, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("moto/editar");
			mv.addObject("moto", moto);
			mv.addObject("filiaisDepartamentos", repFD.findAll());
			mv.addObject("clientes", repC.findAll());
			mv.addObject("lista_modelos", modeloRepository.findAll());
			mv.addObject("lista_situacoes", SituacaoEnum.values());
			return mv;
		}
		Optional<Moto> op = repM.findById(id);
		if(op.isPresent()) {
			Moto atual = op.get();
			atual.setNm_placa(moto.getNm_placa());
			atual.setModelo(moto.getModelo());
			atual.setKm_rodado(moto.getKm_rodado());
			atual.setSt_moto(moto.getSt_moto());
		}
		repM.save(moto);
		return new ModelAndView("redirect:/moto/lista");
	}

	@GetMapping("/moto/excluir/{id}")
	public ModelAndView excluirMoto(@PathVariable Long id) {
		repM.deleteById(id);
		return new ModelAndView("redirect:/moto/lista");
	}

	@GetMapping("/moto/detalhes/{id}")
	public ModelAndView detalhesMoto(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("moto/detalhes");
		Moto moto = repM.findById(id).orElseThrow();
		mv.addObject("moto", moto);
		mv.addObject("manutencoes", repManutencao.buscarTodasOrdenadasPorDataEntrada().stream().filter(m -> m.getMoto().getId_moto().equals(id)).toList());
		return mv;
	}
}
