package br.com.fiap.projeto_mottu.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.fiap.projeto_mottu.dto.FilialDTO;
import br.com.fiap.projeto_mottu.model.Filial;
import br.com.fiap.projeto_mottu.repository.FilialRepository;
import br.com.fiap.projeto_mottu.service.FilialCachingService;
import br.com.fiap.projeto_mottu.service.FilialService;

@Controller
@RequestMapping(value = "/filiais")
public class FilialController {
	@Autowired
	private FilialRepository repF;
	
	@Autowired
	private FilialService servF;
	
	@Autowired
	private FilialCachingService cacheF;
	
	@GetMapping(value = "/todas")
	public List<Filial> retornaTodasFiliais(){
		return repF.findAll();
	}
	
	@GetMapping(value = "/todas_cacheable")
	public List<Filial> retornaTodasFiliaisCacheable(){
		return cacheF.findAll();
	}
	
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<FilialDTO>> paginarFiliais(
			@RequestParam(value = "pagina", defaultValue = "0") Integer page, 
			@RequestParam(value = "tamanho", defaultValue = "2") Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		Page<FilialDTO> paginas_filiais_dto = servF.paginar(pr);
		
		return ResponseEntity.ok(paginas_filiais_dto);
		
	}
	
	@GetMapping(value = "/{id_filial}")
	public Filial retornaFilialPorID(@PathVariable Long id_filial) {
		
		Optional<Filial> op = cacheF.findById(id_filial);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Filial inserirFilial(@RequestBody Filial filial) {
		repF.save(filial);
		cacheF.limparCache();
		return filial;
	}
	
	@GetMapping("/busca_por_nome_filial")
	public ResponseEntity<List<Filial>> buscarPorNome(@RequestParam String nomeFilial) {
	    List<Filial> filiais = repF.buscarPorNome(nomeFilial);
	    if (filiais.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.ok(filiais);
	}
	
	@PutMapping(value = "/atualizar/{id_filial}")
	public Filial atualizarFilial(@RequestBody Filial filial, @PathVariable Long id_filial) {

		Optional<Filial> op = cacheF.findById(id_filial);

		if (op.isPresent()) {

			Filial filial_atual = op.get();

			filial_atual.setNome_filial(filial.getNome_filial());
			filial_atual.setLogradouro(filial.getLogradouro());
			
			repF.save(filial_atual);
			cacheF.limparCache();

			return filial_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/remover/{id_filial}")
	public Filial removerFilial(@PathVariable Long id_filial) {

		Optional<Filial> op = cacheF.findById(id_filial);

		if (op.isPresent()) {

			Filial filial = op.get();
			repF.delete(filial);
			cacheF.limparCache();
			return filial;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
}
