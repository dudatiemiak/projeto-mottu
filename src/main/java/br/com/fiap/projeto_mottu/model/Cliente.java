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
	@NotNull(message = "{validacao.logradouro.presenca}")
	private Logradouro logradouro;
	
	@NotEmpty(message = "{validacao.cliente.nome.obrigatorio}")
	@Size(max = 100, message = "{validacao.cliente.nome.tamanho}")
	@Column(name = "nm_cliente")
	private String nm_cliente;
	@NotEmpty(message = "{validacao.cpf.obrigatorio}")
	@CPF(message = "{validacao.cpf.invalido}")
	@Column(name = "nr_cpf")
	private String nr_cpf;
	@NotEmpty(message = "{validacao.email.obrigatorio}")
	@Email(message = "{validacao.email.invalido}")
	@Size(max = 100, message = "{validacao.email.tamanho}")
	@Column(name = "nm_email")
	private String nm_email;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Telefone> telefones = new ArrayList<>();
	
}
