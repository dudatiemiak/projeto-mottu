package br.com.fiap.projeto_mottu.dto;


import br.com.fiap.projeto_mottu.model.Bairro;
import br.com.fiap.projeto_mottu.model.Cidade;

public class BairroDTO {
	private Long id_bairro;
	private Cidade cidade;
	private String nm_bairro;
	
	public BairroDTO() {}

	public BairroDTO(Long id_bairro, Cidade cidade, String nm_bairro) {
		super();
		this.id_bairro = id_bairro;
		this.cidade = cidade;
		this.nm_bairro = nm_bairro;
	}
	
	public BairroDTO(Bairro bairro) {
		setId_bairro(bairro.getId_bairro());
		setCidade(bairro.getCidade());
		setNm_bairro(bairro.getNm_bairro());
	}

	public Long getId_bairro() {
		return id_bairro;
	}

	public void setId_bairro(Long id_bairro) {
		this.id_bairro = id_bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getNm_bairro() {
		return nm_bairro;
	}

	public void setNm_bairro(String nm_bairro) {
		this.nm_bairro = nm_bairro;
	}
	
	
}
