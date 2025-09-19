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

import br.com.fiap.projeto_mottu.model.Bairro;
import br.com.fiap.projeto_mottu.repository.BairroRepository;
import br.com.fiap.projeto_mottu.service.BairroCachingService;

@Controller
@RequestMapping(value = "/bairros")
public class BairroController {

	@Autowired
	private BairroRepository repB;
	
	@Autowired
	private BairroCachingService cacheB;
	
	@GetMapping(value = "/todos")
	public List<Bairro> retornaTodosBairros(){
		return repB.findAll();
	}
	
	@GetMapping(value = "/{id_bairro}")
	public Bairro retornaBairroPorID(@PathVariable Long id_bairro) {
		
		Optional<Bairro> op = cacheB.findById(id_bairro);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Bairro inserirBairro(@RequestBody Bairro bairro) {
		repB.save(bairro);
		cacheB.limparCache();
		return bairro;
	}
	
	@PutMapping(value = "/atualizar/{id_bairro}")
	public Bairro atualizarBairro(@RequestBody Bairro bairro, @PathVariable Long id_bairro) {

		Optional<Bairro> op = cacheB.findById(id_bairro);

		if (op.isPresent()) {

			Bairro bairro_atual = op.get();

			bairro_atual.setNm_bairro(bairro.getNm_bairro());
			

			repB.save(bairro_atual);
			cacheB.limparCache();

			return bairro_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping(value = "/remover/{id_bairro}")
	public Bairro removerBairro(@PathVariable Long id_bairro) {

		Optional<Bairro> op = cacheB.findById(id_bairro);

		if (op.isPresent()) {

			Bairro bairro = op.get();
			repB.delete(bairro);
			cacheB.limparCache();
			return bairro;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
}
