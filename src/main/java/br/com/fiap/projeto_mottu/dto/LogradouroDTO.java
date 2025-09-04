package br.com.fiap.projeto_mottu.dto;

import org.springframework.hateoas.RepresentationModel;

import br.com.fiap.projeto_mottu.model.Bairro;
import br.com.fiap.projeto_mottu.model.Logradouro;

public class LogradouroDTO extends RepresentationModel<LogradouroDTO>{
	private Long id_logradouro;
	private Bairro bairro;
	private String nm_logradouro;
	private Integer nr_logradouro;
	private String complemento;
	
	public LogradouroDTO() {}

	public LogradouroDTO(Long id_logradouro, Bairro bairro, String nm_logradouro, Integer nr_logradouro,
			String complemento) {
		super();
		this.id_logradouro = id_logradouro;
		this.bairro = bairro;
		this.nm_logradouro = nm_logradouro;
		this.nr_logradouro = nr_logradouro;
		this.complemento = complemento;
	}
	
	public LogradouroDTO(Logradouro logradouro) {
		setId_logradouro(logradouro.getId_logradouro());
		setBairro(logradouro.getBairro());
		setNm_logradouro(logradouro.getNm_logradouro());
		setNr_logradouro(logradouro.getNr_logradouro());
		setComplemento(logradouro.getComplemento());
		
	}

	public Long getId_logradouro() {
		return id_logradouro;
	}

	public void setId_logradouro(Long id_logradouro) {
		this.id_logradouro = id_logradouro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getNm_logradouro() {
		return nm_logradouro;
	}

	public void setNm_logradouro(String nm_logradouro) {
		this.nm_logradouro = nm_logradouro;
	}

	public Integer getNr_logradouro() {
		return nr_logradouro;
	}

	public void setNr_logradouro(Integer nr_logradouro) {
		this.nr_logradouro = nr_logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	
}
