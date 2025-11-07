package br.com.fiap.projeto_mottu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_departamento")
@Data
public class Departamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_departamento;
	@NotEmpty
	@Column(name = "nm_departamento")
	@Size(max = 50, message = "{validacao.departamento.nome.tamanho}")
	private String nm_departamento;
	@NotEmpty
	@Size(max = 250, message = "{validacao.departamento.descricao.tamanho}")
	@Column(name = "ds_departamento")
	private String desc_departamento;
}
