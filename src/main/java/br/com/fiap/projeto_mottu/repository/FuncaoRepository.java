package br.com.fiap.projeto_mottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.projeto_mottu.model.Funcao;


@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

}
