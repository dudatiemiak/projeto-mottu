package br.com.fiap.projeto_mottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.projeto_mottu.dto.ClienteDTO;
import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.model.Telefone;
import br.com.fiap.projeto_mottu.repository.ClienteRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;
import br.com.fiap.projeto_mottu.repository.LogradouroRepository;
import br.com.fiap.projeto_mottu.repository.TelefoneRepository;
import br.com.fiap.projeto_mottu.service.ClienteCachingService;
import br.com.fiap.projeto_mottu.service.ClienteService;
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
	
	@Autowired
	private ClienteService servC;
	
	@Autowired
	private ClienteCachingService cacheC;
		
	@GetMapping(value = "/todos")
	public List<Cliente> retornaTodosClientes(){
		return repC.findAll();
	}
	
	@GetMapping(value = "/todos_cacheable")
	public List<Cliente> retornaTodosClientesCacheable(){
		return cacheC.findAll();
	}
	
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<ClienteDTO>> paginarClientes(
			@RequestParam(value = "pagina", defaultValue = "0") Integer page, 
			@RequestParam(value = "tamanho", defaultValue = "2") Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		Page<ClienteDTO> paginas_clientes_dto = servC.paginar(pr);
		
		return ResponseEntity.ok(paginas_clientes_dto);
		
	}
	
	@GetMapping(value = "/{id_cliente}")
	public Cliente retornaClientePorID(@PathVariable Long id_cliente) {
		
		Optional<Cliente> op = cacheC.findById(id_cliente);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Cliente inserirCliente(@RequestBody Cliente cliente) {
		repC.save(cliente);
		cacheC.limparCache();
		return cliente;
	}
	
	@GetMapping(value = "/buscar_por_substring")
	public List<Cliente> buscarClientePorSubstring(@RequestParam String filtro) {
        return repC.buscarClientePorSubstringOrdenadoPorNome(filtro);
    }
	
	@PutMapping(value = "/atualizar/{id_cliente}")
	public Cliente atualizarCliente(@RequestBody Cliente cliente, @PathVariable Long id_cliente) {

		Optional<Cliente> op = cacheC.findById(id_cliente);

		if (op.isPresent()) {

			Cliente cliente_atual = op.get();

			cliente_atual.setNm_cliente(cliente.getNm_cliente());
			cliente_atual.setNr_cpf(cliente.getNr_cpf());
			cliente_atual.setNm_email(cliente.getNm_email());

			repC.save(cliente_atual);
			cacheC.limparCache();

			return cliente_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/remover/{id_cliente}")
	public Cliente removerCliente(@PathVariable Long id_cliente) {

		Optional<Cliente> op = cacheC.findById(id_cliente);

		if (op.isPresent()) {

			Cliente cliente = op.get();
			repC.delete(cliente);
			cacheC.limparCache();
			return cliente;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
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
    public ModelAndView inserirCliente(@Valid Cliente cliente, BindingResult bd, @Valid Telefone telefone) {
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/cliente/nova");
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
    		Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
    		
    		if(op.isPresent()) {
				mv.addObject("funcionario", op.get());
			}
    		
            mv.addObject("cliente", cliente);
            mv.addObject("logradouros", repL.findAll());
            mv.addObject("telefones", repT.findAll());
            return mv;
        }

        repC.save(cliente);
        telefone.setCliente(cliente);
        repT.save(telefone);

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
            mv.addObject("telefones", repT.findAll());
            return mv;
        }

        Optional<Cliente> op = repC.findById(id);
        if (op.isPresent()) {
            Cliente atual = op.get();
            atual.setNm_cliente(cliente.getNm_cliente());
            atual.setNr_cpf(cliente.getNr_cpf());
            atual.setNm_email(cliente.getNm_email());
            atual.setLogradouro(cliente.getLogradouro());
            repC.save(atual);

            Optional<Telefone> telOp = repT.findByCliente(atual); // precisa criar este método no TelefoneRepository
            if (telOp.isPresent()) {
                Telefone telAtual = telOp.get();
                telAtual.setNr_ddd(telefone.getNr_ddd());
                telAtual.setNr_ddi(telefone.getNr_ddi());
                telAtual.setNr_telefone(telefone.getNr_telefone());
                repT.save(telAtual);
            } else {
                telefone.setCliente(atual);
                repT.save(telefone);
            }
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
