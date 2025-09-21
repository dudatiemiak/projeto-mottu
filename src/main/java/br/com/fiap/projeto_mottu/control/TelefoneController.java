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
// ...existing code...
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_mottu.dto.TelefoneDTO;
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
	public List<TelefoneDTO> retornaTodosTelefones(){
		return repT.findAll().stream().map(TelefoneDTO::new).toList();
	}
	
	@GetMapping(value = "/{id_telefone}")
	public TelefoneDTO retornaTelefonePorID(@PathVariable Long id_telefone) {
		Optional<Telefone> op = cacheT.findById(id_telefone);
		if(op.isPresent()) {
			return new TelefoneDTO(op.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/inserir")
	public TelefoneDTO inserirTelefone(@RequestBody Telefone telefone) {
		repT.save(telefone);
		cacheT.limparCache();
		return new TelefoneDTO(telefone);
	}
	
	@PutMapping(value = "/atualizar/{id_telefone}")
	public TelefoneDTO atualizarTelefone(@RequestBody Telefone telefone, @PathVariable Long id_telefone) {
		Optional<Telefone> op = cacheT.findById(id_telefone);
		if (op.isPresent()) {
			Telefone telefone_atual = op.get();
			telefone_atual.setNr_telefone(telefone.getNr_telefone());
			telefone_atual.setNr_ddi(telefone.getNr_ddi());
			telefone_atual.setNr_ddd(telefone.getNr_ddd());
			repT.save(telefone_atual);
			cacheT.limparCache();
			return new TelefoneDTO(telefone_atual);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/remover/{id_telefone}")
	public TelefoneDTO removerTelefone(@PathVariable Long id_telefone) {
		Optional<Telefone> op = cacheT.findById(id_telefone);
		if (op.isPresent()) {
			Telefone telefone = op.get();
			repT.delete(telefone);
			cacheT.limparCache();
			return new TelefoneDTO(telefone);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
