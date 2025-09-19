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

import br.com.fiap.projeto_mottu.dto.FilialDepartamentoDTO;
import br.com.fiap.projeto_mottu.model.FilialDepartamento;
import br.com.fiap.projeto_mottu.repository.FilialDepartamentoRepository;
import br.com.fiap.projeto_mottu.service.FilialDepartamentoCachingService;
import br.com.fiap.projeto_mottu.service.FilialDepartamentoService;

@Controller
@RequestMapping(value = "/filiais_departamentos")
public class FilialDepartamentoController {

	@Autowired
	private FilialDepartamentoRepository repFD;
	
	@Autowired
	private FilialDepartamentoService servFD;
	
	@Autowired
	private FilialDepartamentoCachingService cacheFD;
	
	@GetMapping(value = "/todos")
	public List<FilialDepartamento> retornaTodosFilialDepartamento(){
		return repFD.findAll();
	}
	
	@GetMapping(value = "/todos_cacheable")
	public List<FilialDepartamento> retornaTodosFilialDepartamentoCacheable(){
		return cacheFD.findAll();
	}
	
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<FilialDepartamentoDTO>> paginarFilialDepartamento(
			@RequestParam(value = "pagina", defaultValue = "0") Integer page, 
			@RequestParam(value = "tamanho", defaultValue = "2") Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		Page<FilialDepartamentoDTO> paginas_fd_dto = servFD.paginar(pr);
		
		return ResponseEntity.ok(paginas_fd_dto);
		
	}
	
	@GetMapping(value = "/{id_filial_departamento}")
	public FilialDepartamento retornaFilialDepartamentoPorID(@PathVariable Long id_filial_departamento) {
		
		Optional<FilialDepartamento> op = cacheFD.findById(id_filial_departamento);
		
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value = "/inserir")
	public FilialDepartamento inserirFilialDepartamento(@RequestBody FilialDepartamento filialDepartamento) {
		repFD.save(filialDepartamento);
		cacheFD.limparCache();
		return filialDepartamento;
	}
	
	@PutMapping(value = "/atualizar/{id_filial_departamento}")
	public FilialDepartamento atualizarFilialDepartamento(@RequestBody FilialDepartamento filialDepartamento, @PathVariable Long id_filial_departamento) {

		Optional<FilialDepartamento> op = cacheFD.findById(id_filial_departamento);

		if (op.isPresent()) {

			FilialDepartamento filial_departamento_atual = op.get();

			filial_departamento_atual.setDt_entrada(filialDepartamento.getDt_entrada());
			filial_departamento_atual.setDt_saida(filialDepartamento.getDt_saida());

			repFD.save(filial_departamento_atual);
			cacheFD.limparCache();

			return filial_departamento_atual;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/remover/{id_filial_departamento}")
	public FilialDepartamento removerFilialDepartamento(@PathVariable Long id_filial_departamento) {

		Optional<FilialDepartamento> op = cacheFD.findById(id_filial_departamento);

		if (op.isPresent()) {

			FilialDepartamento filialDepartamento = op.get();
			repFD.delete(filialDepartamento);
			cacheFD.limparCache();
			return filialDepartamento;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
}
