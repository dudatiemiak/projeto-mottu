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
@Table(name = "t_cm_bairro")
@Data
public class Bairro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_bairro;
	@ManyToOne
	 @JoinColumn(name = "id_cidade", nullable = false)
	 @NotNull(message = "{validacao.bairro.cidade.presenca}")
	private Cidade cidade;
	 @NotEmpty(message = "{validacao.bairro.nome.obrigatorio}")
	 @Size(max = 100, message = "{validacao.bairro.nome.tamanho}")
	@Column(name = "nm_bairro")
	private String nm_bairro;
	
}
