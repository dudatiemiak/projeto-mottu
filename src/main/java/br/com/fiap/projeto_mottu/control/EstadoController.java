package br.com.fiap.projeto_mottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_mottu.model.Estado;
import br.com.fiap.projeto_mottu.repository.EstadoRepository;
import br.com.fiap.projeto_mottu.service.EstadoCachingService;

@Controller
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository repE;
	
	@Autowired
	private EstadoCachingService cacheE;
	
	@GetMapping(value = "/todos")
	public List<Estado> retornaTodosEstados(){
		return repE.findAll();
	}
	
	@GetMapping(value = "/{id_estado}")
	public Estado retornaEstadoPorID(@PathVariable Long id_estado) {
		
		Optional<Estado> op = cacheE.findById(id_estado);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Estado inserirEstado(@RequestBody Estado estado) {
		repE.save(estado);
		cacheE.limparCache();
		return estado;
	}
	
	@PutMapping(value = "/atualizar/{id_estado}")
	public Estado atualizarEstado(@RequestBody Estado estado, @PathVariable Long id_estado) {

		Optional<Estado> op = cacheE.findById(id_estado);

		if (op.isPresent()) {

			Estado estado_atual = op.get();

			estado_atual.setNm_estado(estado.getNm_estado());
			

			repE.save(estado_atual);
			cacheE.limparCache();

			return estado_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping(value = "/remover/{id_estado}")
	public Estado removerEstado(@PathVariable Long id_estado) {

		Optional<Estado> op = cacheE.findById(id_estado);

		if (op.isPresent()) {

			Estado estado = op.get();
			repE.delete(estado);
			cacheE.limparCache();
			return estado;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
}
