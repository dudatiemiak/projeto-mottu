package br.com.fiap.projeto_mottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.projeto_mottu.model.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
}
