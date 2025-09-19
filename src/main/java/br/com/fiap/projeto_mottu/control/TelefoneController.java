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

import br.com.fiap.projeto_mottu.model.Telefone;
import br.com.fiap.projeto_mottu.repository.TelefoneRepository;
import br.com.fiap.projeto_mottu.service.TelefoneCachingService;

@Controller
@RequestMapping(value = "/telefones")
public class TelefoneController {

	@Autowired
	private TelefoneRepository repT;
	
	@Autowired
	private TelefoneCachingService cacheT;
	
	@GetMapping(value = "/todos")
	public List<Telefone> retornaTodosTelefones(){
		return repT.findAll();
	}
	
	@GetMapping(value = "/{id_telefone}")
	public Telefone retornaTelefonePorID(@PathVariable Long id_telefone) {
		
		Optional<Telefone> op = cacheT.findById(id_telefone);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Telefone inserirTelefone(@RequestBody Telefone telefone) {
		repT.save(telefone);
		cacheT.limparCache();
		return telefone;
	}
	
	@PutMapping(value = "/atualizar/{id_telefone}")
	public Telefone atualizarTelefone(@RequestBody Telefone telefone, @PathVariable Long id_telefone) {

		Optional<Telefone> op = cacheT.findById(id_telefone);

		if (op.isPresent()) {

			Telefone telefone_atual = op.get();

			telefone_atual.setNr_telefone(telefone.getNr_telefone());
			telefone_atual.setNr_ddi(telefone.getNr_ddi());
			telefone_atual.setNr_ddd(telefone.getNr_ddd());
			
			repT.save(telefone_atual);
			cacheT.limparCache();

			return telefone_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping(value = "/remover/{id_telefone}")
	public Telefone removerTelefone(@PathVariable Long id_telefone) {

		Optional<Telefone> op = cacheT.findById(id_telefone);

		if (op.isPresent()) {

			Telefone telefone = op.get();
			repT.delete(telefone);
			cacheT.limparCache();
			return telefone;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
}
