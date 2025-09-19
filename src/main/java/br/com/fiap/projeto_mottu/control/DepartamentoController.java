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

import br.com.fiap.projeto_mottu.dto.DepartamentoDTO;
import br.com.fiap.projeto_mottu.model.Departamento;
import br.com.fiap.projeto_mottu.projection.DepartamentoProjection;
import br.com.fiap.projeto_mottu.repository.DepartamentoRepository;
import br.com.fiap.projeto_mottu.service.DepartamentoCachingService;
import br.com.fiap.projeto_mottu.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
    private DepartamentoRepository repD;

    @Autowired
    private DepartamentoCachingService cacheD;

    @Autowired
    private DepartamentoService servD;
    
    @GetMapping(value = "/todos")
    public List<Departamento> listarTodos() {
        return cacheD.findAll();
    }
    
    @GetMapping(value = "/todos_cacheable")
	public List<Departamento> retornaTodosDepartamentosCacheable(){
		return cacheD.findAll();
	}
    
    @GetMapping("/buscar_por_nome")
    public ResponseEntity<List<DepartamentoProjection>> buscarDepartamentoPorNome(
            @RequestParam("nmDepartamento") String nmDepartamento) {
        
        List<DepartamentoProjection> resultado = repD.buscarDepartamentoPorNome(nmDepartamento);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping(value = "/paginados")
	public ResponseEntity<Page<DepartamentoDTO>> paginarDepartamentos(
			@RequestParam(value = "pagina", defaultValue = "0") Integer page, 
			@RequestParam(value = "tamanho", defaultValue = "2") Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		Page<DepartamentoDTO> paginas_departamentos_dto = servD.paginar(pr);
		
		return ResponseEntity.ok(paginas_departamentos_dto);
		
	}
    
    @GetMapping(value = "/{id_departamento}")
	public Departamento retornaDepartamentoPorID(@PathVariable Long id_departamento) {
		
		Optional<Departamento> op = cacheD.findById(id_departamento);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
    
    @PostMapping(value = "/inserir")
	public Departamento inserirDepartamento(@RequestBody Departamento departamento) {
		repD.save(departamento);
		cacheD.limparCache();
		return departamento;
	}
    
    @PutMapping(value = "/atualizar/{id_departamento}")
	public Departamento atualizarDepartamento(@RequestBody Departamento departamento, @PathVariable Long id_departamento) {

		Optional<Departamento> op = cacheD.findById(id_departamento);

		if (op.isPresent()) {

			Departamento departamento_atual = op.get();

			departamento_atual.setNm_departamento(departamento.getNm_departamento());
			departamento_atual.setDesc_departamento(departamento.getDesc_departamento());

			repD.save(departamento_atual);
			cacheD.limparCache();

			return departamento_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
    
    @DeleteMapping(value = "/remover/{id_departamento}")
	public Departamento removerDepartamento(@PathVariable Long id_departamento) {

		Optional<Departamento> op = cacheD.findById(id_departamento);

		if (op.isPresent()) {

			Departamento departamento = op.get();
			repD.delete(departamento);
			cacheD.limparCache();
			return departamento;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
}
