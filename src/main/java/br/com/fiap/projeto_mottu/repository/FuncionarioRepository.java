package br.com.fiap.projeto_mottu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.projection.FuncionarioProjection;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

    // Busca por e-mail corporativo mantendo campo nm_email_corporativo em snake_case
    @Query("SELECT f FROM Funcionario f WHERE f.nm_email_corporativo = :email")
    Optional<Funcionario> findByNmEmailCorporativo(@Param("email") String email);
	
	// Busca todos os funcionários de uma filial pelo nome da filial
	@Query("""
	        SELECT 
	            f.nm_funcionario AS nmFuncionario,
	            f.nm_cargo AS nmCargo,
	            f.nm_email_corporativo AS nmEmailCorporativo
	        FROM Funcionario f
	        WHERE LOWER(f.filial.nome_filial) LIKE LOWER(CONCAT('%', :nomeFilial, '%'))
	""")
	List<FuncionarioProjection> buscarFuncionariosPorNomeFilial(@Param("nomeFilial") String nomeFilial);

    // Busca todos os funcionários com determinado cargo e ordena pelo nome dos funcionários
	@Query("""
	        SELECT 
	            f.nm_funcionario AS nmFuncionario,
	            f.nm_email_corporativo AS nmEmailCorporativo,
	            f.filial.nome_filial AS filialNomeFilial
	        FROM Funcionario f
	        WHERE LOWER(f.nm_cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))
	        ORDER BY f.nm_funcionario ASC
	""")
	List<FuncionarioProjection> buscarFuncionariosPorCargoOrdenado(@Param("cargo") String cargo);
    
}
