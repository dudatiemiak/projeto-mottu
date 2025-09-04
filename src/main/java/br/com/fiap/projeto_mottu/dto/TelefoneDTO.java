package br.com.fiap.projeto_mottu.dto;

import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.model.Telefone;

public class TelefoneDTO{
	private Long id_telefone;
	private Cliente cliente;
	private String nr_telefone;
	private String nr_ddi;
	private String nr_ddd;
	
	public TelefoneDTO() {}

	public TelefoneDTO(Long id_telefone, Cliente cliente, String nr_telefone, String nr_ddi, String nr_ddd) {
		super();
		this.id_telefone = id_telefone;
		this.cliente = cliente;
		this.nr_telefone = nr_telefone;
		this.nr_ddi = nr_ddi;
		this.nr_ddd = nr_ddd;
	}
	
	public TelefoneDTO(Telefone telefone) {
		setId_telefone(telefone.getId_telefone());
		setCliente(telefone.getCliente());
		setNr_telefone(telefone.getNr_telefone());
		setNr_ddi(telefone.getNr_ddi());
		setNr_ddd(telefone.getNr_ddd());
	}

	public Long getId_telefone() {
		return id_telefone;
	}

	public void setId_telefone(Long id_telefone) {
		this.id_telefone = id_telefone;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNr_telefone() {
		return nr_telefone;
	}

	public void setNr_telefone(String nr_telefone) {
		this.nr_telefone = nr_telefone;
	}

	public String getNr_ddi() {
		return nr_ddi;
	}

	public void setNr_ddi(String nr_ddi) {
		this.nr_ddi = nr_ddi;
	}

	public String getNr_ddd() {
		return nr_ddd;
	}

	public void setNr_ddd(String nr_ddd) {
		this.nr_ddd = nr_ddd;
	}
	
	
}
