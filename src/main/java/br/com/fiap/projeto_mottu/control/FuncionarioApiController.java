package br.com.fiap.projeto_mottu.control;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_mottu.dto.FuncionarioDTO;
import br.com.fiap.projeto_mottu.model.Funcao;
import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.projection.FuncionarioProjection;
import br.com.fiap.projeto_mottu.repository.FuncaoRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;
import br.com.fiap.projeto_mottu.service.FuncionarioCachingService;
import br.com.fiap.projeto_mottu.service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioApiController {
    @Autowired
    private FuncionarioRepository repFunc;
    @Autowired
    private FuncionarioCachingService cacheFunc;
    @Autowired
    private FuncionarioService servFunc;
    @Autowired
    private FuncaoRepository repFc;

    @GetMapping("/todos")
    public List<Funcionario> retornaTodosFuncionarios(){
        return repFunc.findAll();
    }

    @GetMapping("/todos_cacheable")
    public List<Funcionario> retornaTodosFuncionariosCacheable(){
        return cacheFunc.findAll();
    }

    @GetMapping("/paginados")
    public ResponseEntity<?> paginarFuncionarios(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size){
        return ResponseEntity.ok(servFunc.paginar(org.springframework.data.domain.PageRequest.of(page, size)));
    }

    @GetMapping("/{id_funcionario}")
    public Funcionario retornaFuncionarioPorID(@PathVariable Long id_funcionario) {
        Optional<Funcionario> op = cacheFunc.findById(id_funcionario);
        if(op.isPresent()) {
            return op.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/inserir")
    public Funcionario inserirFuncionario(@RequestBody Funcionario funcionario) {
        repFunc.save(funcionario);
        cacheFunc.limparCache();
        return funcionario;
    }

    @PutMapping("/atualizar/{id_funcionario}")
    public Funcionario atualizarFuncionario(@RequestBody Funcionario funcionario, @PathVariable Long id_funcionario) {
        Optional<Funcionario> op = cacheFunc.findById(id_funcionario);
        if (op.isPresent()) {
            Funcionario funcionario_atual = op.get();
            funcionario_atual.setNm_funcionario(funcionario.getNm_funcionario());
            funcionario_atual.setNm_cargo(funcionario.getNm_cargo());
            funcionario_atual.setNmEmailCorporativo(funcionario.getNmEmailCorporativo());
            funcionario_atual.setNm_senha(funcionario.getNm_senha());
            repFunc.save(funcionario_atual);
            cacheFunc.limparCache();
            return funcionario_atual;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remover/{id_funcionario}")
    public Funcionario removerFuncionario(@PathVariable Long id_funcionario) {
        Optional<Funcionario> op = cacheFunc.findById(id_funcionario);
        if (op.isPresent()) {
            Funcionario funcionario = op.get();
            repFunc.delete(funcionario);
            cacheFunc.limparCache();
            return funcionario;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar_por_filial")
    public ResponseEntity<List<FuncionarioProjection>> buscarPorFilial(@RequestParam String nomeFilial) {
        List<FuncionarioProjection> funcionarios = repFunc.buscarFuncionariosPorNomeFilial(nomeFilial);
        if (funcionarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscar_por_cargo")
    public ResponseEntity<List<FuncionarioProjection>> buscarPorCargo(@RequestParam String cargo) {
        List<FuncionarioProjection> funcionarios = repFunc.buscarFuncionariosPorCargoOrdenado(cargo);
        if (funcionarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionarios);
    }
}
