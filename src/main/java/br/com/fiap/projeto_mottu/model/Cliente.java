package br.com.fiap.projeto_mottu.model;

import org.hibernate.validator.constraints.br.CPF;
// import removido: org.springframework.hateoas.RepresentationModel

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
// import removido: jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "t_cm_cliente")
@Data
public class Cliente{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cliente;
	
	@OneToOne
    @JoinColumn(name = "id_logradouro", nullable = false)
    @NotNull(message = "O cliente deve estar relacionado a um logradouro")
	private Logradouro logradouro;
	
	@NotEmpty(message = "O nome do cliente deve ser informado!")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres")
	@Column(name = "nm_cliente")
	private String nm_cliente;
	@NotEmpty(message = "O CPF deve ser informado!")
	@CPF(message = "O CPF informado é inválido")
	@Column(name = "nr_cpf")
	private String nr_cpf;
	@NotEmpty(message = "O email deve ser informado!")
	@Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
	@Column(name = "nm_email")
	private String nm_email;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Telefone> telefones = new ArrayList<>();
	
}
