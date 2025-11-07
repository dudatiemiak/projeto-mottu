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
@Table(name = "t_cm_estado")
@Data
public class Estado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_estado;
	@ManyToOne
	@JoinColumn(name = "id_pais", nullable = false)
	@NotNull(message = "{validacao.estado.pais.presenca}")
	private Pais pais;
	@NotEmpty(message = "{validacao.estado.nome.obrigatorio}")
	@Size(min = 2, max = 2, message = "{validacao.estado.uf.formato}")
	@Column(name = "nm_estado")
	private String nm_estado;
	
}
