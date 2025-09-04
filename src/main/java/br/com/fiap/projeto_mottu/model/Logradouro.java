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
@Table(name = "t_cm_logradouro")
@Data
public class Logradouro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_logradouro;
	@ManyToOne
    @JoinColumn(name = "id_bairro", nullable = false)
    @NotNull(message = "O logradouro deve estar vinculado a um bairro")
	private Bairro bairro;
	@NotEmpty(message = "O nome do logradouro deve ser informado")
    @Size(max = 100, message = "O logradouro deve ter no máximo 100 caracteres")
	@Column(name = "nm_logradouro")
	private String nm_logradouro;
	@NotNull(message = "O número do logradouro deve ser informado")
	@Column(name = "nr_logradouro")
	private Integer nr_logradouro;
	@Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
	@Column(name = "nm_complemento")
	private String complemento;
	
}
