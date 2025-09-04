package br.com.fiap.projeto_mottu.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_filial")
@Data
public class Filial{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_filial;
	@OneToOne
    @JoinColumn(name = "id_logradouro", nullable = false)
    @NotNull(message = "A filial deve estar vinculada a um logradouro")
	private Logradouro logradouro;
	@NotEmpty(message = "Não é possível inserir uma filial sem nome")
	@Size(min = 0, max = 100, message = "Valor de nome inválido")
	@Column(name = "nm_filial")
	private String nome_filial;
	
}
