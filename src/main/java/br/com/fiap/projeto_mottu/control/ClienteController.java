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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.projeto_mottu.dto.ClienteDTO;
import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.repository.ClienteRepository;
import br.com.fiap.projeto_mottu.service.ClienteCachingService;
import br.com.fiap.projeto_mottu.service.ClienteService;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository repC;
	
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
}
