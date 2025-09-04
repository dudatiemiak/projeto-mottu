package br.com.fiap.projeto_mottu.dto;

import br.com.fiap.projeto_mottu.model.Estado;
import br.com.fiap.projeto_mottu.model.Pais;

public class EstadoDTO{
	private Long id_estado;
	private Pais pais;
	private String nm_estado;
	
	public EstadoDTO () {}

	public EstadoDTO(Long id_estado, Pais pais, String nm_estado) {
		super();
		this.id_estado = id_estado;
		this.pais = pais;
		this.nm_estado = nm_estado;
	}
	
	public EstadoDTO (Estado estado) {
		setId_estado(estado.getId_estado());
		setPais(estado.getPais());
		setNm_estado(estado.getNm_estado());
	}

	public Long getId_estado() {
		return id_estado;
	}

	public void setId_estado(Long id_estado) {
		this.id_estado = id_estado;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getNm_estado() {
		return nm_estado;
	}

	public void setNm_estado(String nm_estado) {
		this.nm_estado = nm_estado;
	}
	
	
}
