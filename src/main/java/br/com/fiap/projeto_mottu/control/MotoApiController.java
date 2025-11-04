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

import br.com.fiap.projeto_mottu.dto.MotoDTO;
import br.com.fiap.projeto_mottu.model.Moto;
import br.com.fiap.projeto_mottu.model.SituacaoEnum;
import br.com.fiap.projeto_mottu.projection.MotoProjection;
import br.com.fiap.projeto_mottu.repository.MotoRepository;
import br.com.fiap.projeto_mottu.service.MotoCachingService;
import br.com.fiap.projeto_mottu.service.MotoService;

@Controller
@RequestMapping(value = "/motos")
public class MotoApiController {

	@Autowired
	private MotoRepository repM;
	
	@Autowired
	private MotoService servM;
	
	@Autowired
	private MotoCachingService cacheM;
	
	@GetMapping(value = "/todas")
	public List<Moto> retornaTodasMotos(){
		return repM.findAll();
	}
	
	@GetMapping(value = "/todas_cacheable")
	public List<Moto> retornaTodasMotosCacheable(){
		return cacheM.findAll();
	}
	
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<MotoDTO>> paginarMotos(
			@RequestParam(value = "pagina", defaultValue = "0") Integer page, 
			@RequestParam(value = "tamanho", defaultValue = "2") Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		Page<MotoDTO> paginas_motos_dto = servM.paginar(pr);
		
		return ResponseEntity.ok(paginas_motos_dto);
		
	}
	
	@GetMapping(value = "/{id_moto}")
	public Moto retornaMotoPorID(@PathVariable Long id_moto) {
		
		Optional<Moto> op = cacheM.findById(id_moto);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public Moto inserirMoto(@RequestBody Moto moto) {
		repM.save(moto);
		cacheM.limparCache();
		return moto;
	}
	
	@GetMapping("/buscar_por_situacao")
	public ResponseEntity<List<Moto>> buscarPorSituacaoOrdenadoPorModelo(
            @RequestParam("situacao") SituacaoEnum situacao) {
        List<Moto> motos = repM.buscarPorSituacaoOrdenadoPorModelo(situacao);
        return ResponseEntity.ok(motos);
    }
	
	 @GetMapping("/buscar_por_placa")
	 public ResponseEntity<Moto> buscarPorPlaca(@RequestParam("placa") String placa) {
	        Optional<Moto> moto = repM.buscarPorPlaca(placa);
	        return moto.map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	 }
	
	@GetMapping("/buscar_por_filial_ordenado")
	public ResponseEntity<List<MotoProjection>> buscarMotosPorFilial(@RequestParam("nomeFilial") String nomeFilial) {
	    List<MotoProjection> resultado = repM.buscarMotosPorNomeDaFilialOrdenadoPorModelo(nomeFilial);
	    return ResponseEntity.ok(resultado);
	}
	
	@PutMapping(value = "/atualizar/{id_moto}")
	public Moto atualizarMoto(@RequestBody Moto moto, @PathVariable Long id_moto) {

		Optional<Moto> op = cacheM.findById(id_moto);

		if (op.isPresent()) {

			Moto moto_atual = op.get();

			moto_atual.setCliente(moto.getCliente());
			moto_atual.setFilial_departamento(moto.getFilial_departamento());
			moto_atual.setNm_placa(moto.getNm_placa());
			moto_atual.setSt_moto(moto.getSt_moto());
			moto_atual.setKm_rodado(moto.getKm_rodado());
	
			repM.save(moto_atual);
			cacheM.limparCache();

			return moto_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/remover/{id_moto}")
	public Moto removerMoto(@PathVariable Long id_moto) {

		Optional<Moto> op = cacheM.findById(id_moto);

		if (op.isPresent()) {

			Moto moto = op.get();
			repM.delete(moto);
			cacheM.limparCache();
			return moto;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
}
