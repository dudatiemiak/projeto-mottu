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

import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.model.Telefone;
import br.com.fiap.projeto_mottu.repository.ClienteRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;
import br.com.fiap.projeto_mottu.repository.LogradouroRepository;
import br.com.fiap.projeto_mottu.repository.TelefoneRepository;
import jakarta.validation.Valid;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository repC;
	
	@Autowired
	private LogradouroRepository repL;
	
	@Autowired
	private TelefoneRepository repT;
	
	@Autowired
	private FuncionarioRepository repFunc;
	
	@GetMapping("/cliente/lista")
    public ModelAndView listarClientes() {
        ModelAndView mv = new ModelAndView("/cliente/lista"); 
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("funcionario", op.get());
		}
        
        mv.addObject("clientes", repC.findAll());
        return mv;
    }

    @GetMapping("/cliente/nova")
    public ModelAndView formularioNovoCliente() {        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
		Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());

        ModelAndView mv = new ModelAndView("/cliente/nova");
        
        if(op.isPresent()) {
			mv.addObject("funcionario",op.get());
		}

        mv.addObject("cliente", new Cliente());
        mv.addObject("logradouros", repL.findAll());
        mv.addObject("telefones", repT.findAll());
        return mv;
    }

    @PostMapping("/cliente/inserir")
	public ModelAndView inserirCliente(@Valid Cliente cliente, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/cliente/nova");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
			if(op.isPresent()) {
				mv.addObject("funcionario", op.get());
			}
			mv.addObject("cliente", cliente);
			mv.addObject("logradouros", repL.findAll());
			return mv;
		}
		// Salva cliente e todos os telefones associados
		if (cliente.getTelefones() != null) {
			cliente.getTelefones().forEach(t -> t.setCliente(cliente));
		}
		repC.save(cliente);
		return new ModelAndView("redirect:/cliente/lista");
    }

    @GetMapping("/cliente/detalhes/{id}")
    public ModelAndView detalhesCliente(@PathVariable Long id) {
        Optional<Cliente> op = repC.findById(id);
        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/cliente/detalhes");
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
    		Optional<Funcionario> op1 = repFunc.findByNmEmailCorporativo(auth.getName());
    		
    		if(op1.isPresent()) {
				mv.addObject("funcionario", op1.get());
			}
    		
            mv.addObject("cliente", op.get());
            return mv;
        }
        return new ModelAndView("redirect:/cliente/lista");
    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView editarClienteForm(@PathVariable Long id) {
        Optional<Cliente> op = repC.findById(id);
        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/cliente/editar");
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
    		Optional<Funcionario> op1 = repFunc.findByNmEmailCorporativo(auth.getName());
    		
    		if(op1.isPresent()) {
				mv.addObject("funcionario", op1.get());
			}
            
            mv.addObject("cliente", op.get());
            mv.addObject("logradouros", repL.findAll());
            mv.addObject("telefones", repT.findAll());
            return mv;
        }
        return new ModelAndView("redirect:/cliente/lista");
    }

    @PostMapping("/cliente/editar/{id}")
    public ModelAndView editarCliente(@PathVariable Long id, @Valid Cliente cliente, BindingResult bd, @Valid Telefone telefone) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/cliente/editar");
			mv.addObject("cliente", cliente);
			mv.addObject("logradouros", repL.findAll());
			return mv;
		}
		Optional<Cliente> op = repC.findById(id);
		if (op.isPresent()) {
			Cliente atual = op.get();
			atual.setNm_cliente(cliente.getNm_cliente());
			atual.setNr_cpf(cliente.getNr_cpf());
			atual.setNm_email(cliente.getNm_email());
			atual.setLogradouro(cliente.getLogradouro());
			// Remove todos os telefones antigos e adiciona os novos
			atual.getTelefones().clear();
			if (cliente.getTelefones() != null) {
				cliente.getTelefones().forEach(t -> {
					t.setCliente(atual);
					atual.getTelefones().add(t);
				});
			}
			repC.save(atual);
		}
		return new ModelAndView("redirect:/cliente/lista");
    }

    @GetMapping("/cliente/remover/{id}")
    public ModelAndView removerClienteExistente(@PathVariable Long id) {
    	Optional<Cliente> op = repC.findById(id);
    	if(op.isPresent()) {
    		repC.deleteById(id);
            return new ModelAndView("redirect:/cliente/lista");
    	}
        return new ModelAndView("redirect:/cliente/lista");
    }

}
