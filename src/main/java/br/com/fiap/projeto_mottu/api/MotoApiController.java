package br.com.fiap.projeto_mottu.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projeto_mottu.model.Moto;
import br.com.fiap.projeto_mottu.repository.MotoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/motos")
@Tag(name = "Moto API", description = "Operações de CRUD para Moto")
public class MotoApiController {

    @Autowired
    private MotoRepository repMoto;

    @GetMapping
    @Operation(summary = "Listar todas as motos")
    public List<Moto> listarMotos() {
        return repMoto.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moto por ID")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        Optional<Moto> moto = repMoto.findById(id);
        return moto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova moto")
    public Moto cadastrarMoto(@RequestBody Moto moto) {
        return repMoto.save(moto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar moto existente")
    public ResponseEntity<Moto> atualizarMoto(@PathVariable Long id, @RequestBody Moto moto) {
        if (!repMoto.existsById(id)) return ResponseEntity.notFound().build();
        moto.setId_moto(id);
        return ResponseEntity.ok(repMoto.save(moto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir moto")
    public ResponseEntity<Void> excluirMoto(@PathVariable Long id) {
        if (!repMoto.existsById(id)) return ResponseEntity.notFound().build();
        repMoto.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
