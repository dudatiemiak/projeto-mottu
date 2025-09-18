package br.com.fiap.projeto_mottu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long>{

	Optional<Telefone> findByCliente(Cliente cliente);
}
