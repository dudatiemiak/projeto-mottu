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
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_mottu.dto.ManutencaoDTO;
import br.com.fiap.projeto_mottu.model.Manutencao;
import br.com.fiap.projeto_mottu.repository.ManutencaoRepository;
import br.com.fiap.projeto_mottu.service.ManutencaoCachingService;
import br.com.fiap.projeto_mottu.service.ManutencaoService;


@Controller
@RequestMapping(value = "/manutencoes")
public class ManutencaoApiController {

	@Autowired
	private ManutencaoRepository repMan;
	
	@Autowired
	private ManutencaoService servMan;
	
	@Autowired
	private ManutencaoCachingService cacheMan;
	
	@GetMapping(value = "/todas")
	public List<Manutencao> retornaTodosManutencoes(){
		return repMan.findAll();
	}
	
	@GetMapping(value = "/todos_cacheable")
	public List<Manutencao> retornaTodasManutencoesCacheable(){
		return cacheMan.findAll();
	}
	
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<ManutencaoDTO>> paginarManutencoes(
			@RequestParam(value = "pagina", defaultValue = "0") Integer page, 
			@RequestParam(value = "tamanho", defaultValue = "2") Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		Page<ManutencaoDTO> paginas_manutencoes_dto = servMan.paginar(pr);
		
		return ResponseEntity.ok(paginas_manutencoes_dto);
		
	}
	
	@GetMapping(value = "/{id_manutencao}")
	public Manutencao retornaManutencaoPorID(@PathVariable Long id_manutencao) {
		
		Optional<Manutencao> op = cacheMan.findById(id_manutencao);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Manutencao inserirManutencao(@RequestBody Manutencao manutencao) {
		repMan.save(manutencao);
		cacheMan.limparCache();
		return manutencao;
	}
	
	@GetMapping("/buscar_todas_ordenada_por_data")
    public List<Manutencao> buscarOrdenadasPorDataEntrada() {
        return repMan.buscarTodasOrdenadasPorDataEntrada();
    }
	
	@GetMapping("/buscar_em_aberto")
    public ResponseEntity<List<Manutencao>> buscarManutencoesEmAberto() {
        List<Manutencao> manutencoes = repMan.buscarManutencoesEmAberto();
        return manutencoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(manutencoes);
    }
	
	@GetMapping("/buscar_por_descricao")
    public ResponseEntity<List<Manutencao>> buscarPorDescricao(@RequestParam String keyword) {
        List<Manutencao> manutencoes = repMan.buscarPorDescricao(keyword);
        return manutencoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(manutencoes);
    }

	@PutMapping(value = "/atualizar/{id_manutencao}")
	public Manutencao atualizarManutencao(@RequestBody Manutencao manutencao, @PathVariable Long id_manutencao) {

		Optional<Manutencao> op = cacheMan.findById(id_manutencao);

		if (op.isPresent()) {

			Manutencao manutencao_atual = op.get();

			manutencao_atual.setMoto(manutencao.getMoto());
			manutencao_atual.setDs_manutencao(manutencao.getDs_manutencao());
			manutencao_atual.setDt_entrada(manutencao.getDt_entrada());
			manutencao_atual.setDt_saida(manutencao.getDt_saida());
		
			repMan.save(manutencao_atual);
			cacheMan.limparCache();

			return manutencao_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/remover/{id_manutencao}")
	public Manutencao removerManutencao(@PathVariable Long id_manutencao) {

		Optional<Manutencao> op = cacheMan.findById(id_manutencao);

		if (op.isPresent()) {

			Manutencao manutencao = op.get();
			repMan.delete(manutencao);
			cacheMan.limparCache();
			return manutencao;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
}
