package br.com.fiap.projeto_mottu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.projeto_mottu.model.Moto;
import br.com.fiap.projeto_mottu.model.SituacaoEnum;
import br.com.fiap.projeto_mottu.projection.MotoProjection;

public interface MotoRepository extends JpaRepository<Moto, Long>{

	// Busca motos por situação, e ordena os resultados pelo nome do modelo
	@Query("SELECT m FROM Moto m WHERE m.st_moto = :situacao ORDER BY m.modelo.nome ASC")
	List<Moto> buscarPorSituacaoOrdenadoPorModelo(@Param("situacao") SituacaoEnum situacao);

    // Busca moto por placa
	@Query("SELECT m FROM Moto m WHERE m.nm_placa = :placa")
    Optional<Moto> buscarPorPlaca(String placa);

	//Busca moto por nome da filial, ordenada por modelo
	@Query("SELECT m.nm_placa AS nmPlaca, m.modelo.nome AS modelo_Nome, m.st_moto AS stMoto " +
			   "FROM Moto m " +
			   "WHERE LOWER(m.filial_departamento.filial.nome_filial) LIKE LOWER(CONCAT('%', :nomeFilial, '%')) " +
			   "ORDER BY m.modelo.nome")
	List<MotoProjection> buscarMotosPorNomeDaFilialOrdenadoPorModelo(@Param("nomeFilial") String nomeFilial);

}
