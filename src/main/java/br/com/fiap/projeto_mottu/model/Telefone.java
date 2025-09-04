package br.com.fiap.projeto_mottu.model;

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
@Table(name = "t_cm_telefone")
@Data
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_telefone;
	@ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "O telefone deve estar vinculado a um cliente")
	private Cliente cliente;
	@NotEmpty(message = "O número de telefone não pode estar em branco")
    @Size(max = 15, message = "O número de telefone deve ter no máximo 15 caracteres")
	private String nr_telefone;
	@NotEmpty(message = "O DDI não pode estar em branco")
    @Size(max = 4, message = "O DDI deve ter no máximo 4 caracteres")
	private String nr_ddi;
	@NotEmpty(message = "O DDD não pode estar em branco")
    @Size(max = 5, message = "O DDD deve ter no máximo 5 caracteres")
	private String nr_ddd;
	
}
