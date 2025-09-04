package br.com.fiap.projeto_mottu.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_funcionario")
@Data
public class Funcionario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_funcionario;
	@ManyToOne
	@JoinColumn(name = "id_filial", nullable = false)
    @NotNull(message = "O funcionário deve estar relacionado a uma filial!")
	private Filial filial;
	@NotEmpty(message = "O nome do funcionário deve ser informado")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	@Column(name = "nm_funcionario")
	private String nm_funcionario;
	@NotEmpty
	@Email(message = "O e-mail deve ser válido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
	@Column(name = "nm_email_corporativo")
	private String nm_email_corporativo;
	@NotEmpty
	@Size(max = 225, message = "A senha deve ter no máximo 225 caracteres")
	@Column(name = "nm_senha")
	private String nm_senha;
	@NotEmpty
	@Column(name = "nm_cargo")
	@Size(max = 50, message = "O nome do cargo deve ter no máximo 50 caracteres")
	private String nm_cargo;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_cm_funcionario_funcao", 
	joinColumns = @JoinColumn(name = "id_funcionario"), 
	inverseJoinColumns = @JoinColumn(name = "id_funcao"))
	private Set<Funcao> funcoes = new HashSet<Funcao>();
}
