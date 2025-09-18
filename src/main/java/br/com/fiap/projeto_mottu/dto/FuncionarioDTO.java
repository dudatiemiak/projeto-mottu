package br.com.fiap.projeto_mottu.dto;

import org.springframework.hateoas.RepresentationModel;

import br.com.fiap.projeto_mottu.model.Filial;
import br.com.fiap.projeto_mottu.model.Funcionario;

public class FuncionarioDTO extends RepresentationModel<FuncionarioDTO>{
	private Long id_funcionario;
	private Filial filial;
	private String nm_funcionario;
	private String nm_email_corporativo;
	private String nm_senha;
	private String nm_cargo;
	 
	public FuncionarioDTO () {}

	public FuncionarioDTO(Long id_funcionario, Filial filial, String nm_funcionario, String nm_email_corporativo,
			String nm_senha, String nm_cargo) {
		super();
		this.id_funcionario = id_funcionario;
		this.filial = filial;
		this.nm_funcionario = nm_funcionario;
		this.nm_email_corporativo = nm_email_corporativo;
		this.nm_senha = nm_senha;
		this.nm_cargo = nm_cargo;
	}
	
	public FuncionarioDTO (Funcionario funcionario) {
		setId_funcionario(funcionario.getId_funcionario());
		setFilial(funcionario.getFilial());
		setNm_funcionario(funcionario.getNm_funcionario());
		setNmEmailCorporativo(funcionario.getNmEmailCorporativo());
		setNm_senha(funcionario.getNm_senha());
		setNm_cargo(funcionario.getNm_cargo());
	}

	public Long getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(Long id_funcionario) {
		this.id_funcionario = id_funcionario;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public String getNm_funcionario() {
		return nm_funcionario;
	}

	public void setNm_funcionario(String nm_funcionario) {
		this.nm_funcionario = nm_funcionario;
	}

	public String getNmEmailCorporativo() {
		return nm_email_corporativo;
	}

	public void setNmEmailCorporativo(String nm_email_corporativo) {
		this.nm_email_corporativo = nm_email_corporativo;
	}

	public String getNm_senha() {
		return nm_senha;
	}

	public void setNm_senha(String nm_senha) {
		this.nm_senha = nm_senha;
	}

	public String getNm_cargo() {
		return nm_cargo;
	}

	public void setNm_cargo(String nm_cargo) {
		this.nm_cargo = nm_cargo;
	}
	
	
}
