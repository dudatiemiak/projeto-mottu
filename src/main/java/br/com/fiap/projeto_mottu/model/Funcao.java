package br.com.fiap.projeto_mottu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_cm_funcao")
public class Funcao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_funcao;
	@Enumerated(EnumType.STRING)
	@Column(name = "nm_funcao")
	private FuncaoEnum nm_funcao;

	public Funcao() {

	}

	public Funcao(Long id_funcao, FuncaoEnum nm_funcao) {
		super();
		this.id_funcao = id_funcao;
		this.nm_funcao = nm_funcao;
	}

	public Long getId_funcao() {
		return id_funcao;
	}

	public void setId_funcao(Long id_funcao) {
		this.id_funcao = id_funcao;
	}

	public FuncaoEnum getNm_funcao() {
		return nm_funcao;
	}

	public void setNm_funcao(FuncaoEnum nm_funcao) {
		this.nm_funcao = nm_funcao;
	}

	

}