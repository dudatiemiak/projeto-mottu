package br.com.fiap.projeto_mottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_mottu.dto.ClienteDTO;
import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.repository.ClienteRepository;
import br.com.fiap.projeto_mottu.service.ClienteCachingService;
import br.com.fiap.projeto_mottu.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteApiController {

    @Autowired
    private ClienteRepository repC;
    @Autowired
    private ClienteService servC;
    @Autowired
    private ClienteCachingService cacheC;

	@GetMapping("/todos")
	public List<ClienteDTO> retornaTodosClientes(){
		return repC.findAll().stream().map(ClienteDTO::new).toList();
	}

	@GetMapping("/todos_cacheable")
	public List<ClienteDTO> retornaTodosClientesCacheable(){
		return cacheC.findAll().stream().map(ClienteDTO::new).toList();
	}

    @GetMapping("/paginados")
    public ResponseEntity<Page<ClienteDTO>> paginarClientes(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size){
        PageRequest pr = PageRequest.of(page, size);
        Page<ClienteDTO> paginas_clientes_dto = servC.paginar(pr);
        return ResponseEntity.ok(paginas_clientes_dto);
    }

	@GetMapping("/{id_cliente}")
	public ClienteDTO retornaClientePorID(@PathVariable Long id_cliente) {
		Optional<Cliente> op = cacheC.findById(id_cliente);
		if(op.isPresent()) {
			return new ClienteDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/inserir")
	public ClienteDTO inserirCliente(@RequestBody Cliente cliente) {
		repC.save(cliente);
		cacheC.limparCache();
		return new ClienteDTO(cliente);
	}

	@GetMapping("/buscar_por_substring")
	public List<ClienteDTO> buscarClientePorSubstring(@RequestParam String filtro) {
		return repC.buscarClientePorSubstringOrdenadoPorNome(filtro).stream().map(ClienteDTO::new).toList();
	}

	@PutMapping("/atualizar/{id_cliente}")
	public ClienteDTO atualizarCliente(@RequestBody Cliente cliente, @PathVariable Long id_cliente) {
		Optional<Cliente> op = cacheC.findById(id_cliente);
		if (op.isPresent()) {
			Cliente cliente_atual = op.get();
			cliente_atual.setNm_cliente(cliente.getNm_cliente());
			cliente_atual.setNr_cpf(cliente.getNr_cpf());
			cliente_atual.setNm_email(cliente.getNm_email());
			repC.save(cliente_atual);
			cacheC.limparCache();
			return new ClienteDTO(cliente_atual);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

    @DeleteMapping("/remover/{id_cliente}")
    public ClienteDTO removerCliente(@PathVariable Long id_cliente) {
        Optional<Cliente> op = cacheC.findById(id_cliente);
        if (op.isPresent()) {
            Cliente cliente = op.get();
            repC.delete(cliente);
            cacheC.limparCache();
            return new ClienteDTO(cliente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
