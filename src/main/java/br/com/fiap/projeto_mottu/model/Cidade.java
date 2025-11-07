package br.com.fiap.projeto_mottu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_cidade")
@Data
public class Cidade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cidade;
	@ManyToOne
	@JoinColumn(name = "id_estado", nullable = false)
	@NotNull(message = "{validacao.cidade.estado.presenca}")
	private Estado estado;
	@NotEmpty(message = "{validacao.cidade.nome.obrigatorio}")
	@Size(max = 50, message = "{validacao.cidade.nome.tamanho}")
	@Column(name = "nm_cidade")
	private String nm_cidade;

}
