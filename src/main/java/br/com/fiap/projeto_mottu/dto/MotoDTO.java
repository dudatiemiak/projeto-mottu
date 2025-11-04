package br.com.fiap.projeto_mottu.dto;

import org.springframework.hateoas.RepresentationModel;

import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.model.FilialDepartamento;
import br.com.fiap.projeto_mottu.model.Modelo;
import br.com.fiap.projeto_mottu.model.Moto;
import br.com.fiap.projeto_mottu.model.SituacaoEnum;

public class MotoDTO extends RepresentationModel<MotoDTO>{
	private Long id_moto;
	private Cliente cliente;
	private String nm_placa;
	private Modelo modelo;
	private FilialDepartamento filial_departamento;
	private SituacaoEnum st_moto;
	private Double km_rodado;
	
	public MotoDTO () {}

	public MotoDTO(Long id_moto, Cliente cliente, String nm_placa, Modelo modelo,
			FilialDepartamento filial_departamento, SituacaoEnum st_moto, Double km_rodado) {
		super();
		this.id_moto = id_moto;
		this.cliente = cliente;
		this.nm_placa = nm_placa;
		this.modelo = modelo;
		this.filial_departamento = filial_departamento;
		this.st_moto = st_moto;
		this.km_rodado = km_rodado;
	}
	
	public MotoDTO (Moto moto) {
		setId_moto(moto.getId_moto());
		setCliente(moto.getCliente());
		setNm_placa(moto.getNm_placa());
		setModelo(moto.getModelo());
		setFilial_departamento(moto.getFilial_departamento());
		setSt_moto(moto.getSt_moto());
		setKm_rodado(moto.getKm_rodado());
	}

	public Long getId_moto() {
		return id_moto;
	}

	public void setId_moto(Long id_moto) {
		this.id_moto = id_moto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNm_placa() {
		return nm_placa;
	}

	public void setNm_placa(String nm_placa) {
		this.nm_placa = nm_placa;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public FilialDepartamento getFilial_departamento() {
		return filial_departamento;
	}

	public void setFilial_departamento(FilialDepartamento filial_departamento) {
		this.filial_departamento = filial_departamento;
	}

	public SituacaoEnum getSt_moto() {
		return st_moto;
	}

	public void setSt_moto(SituacaoEnum st_moto) {
		this.st_moto = st_moto;
	}

	public Double getKm_rodado() {
		return km_rodado;
	}

	public void setKm_rodado(Double km_rodado) {
		this.km_rodado = km_rodado;
	}
	
	
	
}
