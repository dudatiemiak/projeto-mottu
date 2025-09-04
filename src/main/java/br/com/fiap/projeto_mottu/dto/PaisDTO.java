package br.com.fiap.projeto_mottu.dto;

import br.com.fiap.projeto_mottu.model.Pais;

public class PaisDTO{
	private Long id_pais;
	private String nm_pais;
	
	public PaisDTO() {}

	public PaisDTO(Long id_pais, String nm_pais) {
		super();
		this.id_pais = id_pais;
		this.nm_pais = nm_pais;
	}
	
	public PaisDTO(Pais pais) {
		setId_pais(pais.getId_pais());
		setNm_pais(pais.getNm_pais());
	}

	public Long getId_pais() {
		return id_pais;
	}

	public void setId_pais(Long id_pais) {
		this.id_pais = id_pais;
	}

	public String getNm_pais() {
		return nm_pais;
	}

	public void setNm_pais(String nm_pais) {
		this.nm_pais = nm_pais;
	}
	
	
}
