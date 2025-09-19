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

import br.com.fiap.projeto_mottu.model.Logradouro;
import br.com.fiap.projeto_mottu.repository.LogradouroRepository;
import br.com.fiap.projeto_mottu.service.LogradouroCachingService;
import br.com.fiap.projeto_mottu.service.LogradouroService;

@Controller
@RequestMapping(value = "/logradouros")
public class LogradouroController {
	
	@Autowired
	private LogradouroRepository repL;
	
	@Autowired
	private LogradouroService servL;
	
	@Autowired
	private LogradouroCachingService cacheL;
	
	@GetMapping(value = "/todos")
	public List<Logradouro> retornaTodosLogradouros(){
		return repL.findAll();
	}
	
	@GetMapping(value = "/{id_logradouro}")
	public Logradouro retornaLogradouroPorID(@PathVariable Long id_logradouro) {
		
		Optional<Logradouro> op = cacheL.findById(id_logradouro);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Logradouro inserirLogradouro(@RequestBody Logradouro logradouro) {
		repL.save(logradouro);
		cacheL.limparCache();
		return logradouro;
	}
	
	@PutMapping(value = "/atualizar/{id_logradouro}")
	public Logradouro atualizarLogradouro(@RequestBody Logradouro logradouro, @PathVariable Long id_logradouro) {

		Optional<Logradouro> op = cacheL.findById(id_logradouro);

		if (op.isPresent()) {

			Logradouro logradouro_atual = op.get();

			logradouro_atual.setNm_logradouro(logradouro.getNm_logradouro());
			logradouro_atual.setNr_logradouro(logradouro.getNr_logradouro());
			logradouro_atual.setComplemento(logradouro.getComplemento());

			repL.save(logradouro_atual);
			cacheL.limparCache();

			return logradouro_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping(value = "/remover/{id_logradouro}")
	public Logradouro removerLogradouro(@PathVariable Long id_logradouro) {

		Optional<Logradouro> op = cacheL.findById(id_logradouro);

		if (op.isPresent()) {

			Logradouro logradouro = op.get();
			repL.delete(logradouro);
			cacheL.limparCache();
			return logradouro;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
}
