package br.com.fiap.projeto_mottu.model;

import java.util.HashSet;
import java.util.Objects;
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


@Entity
@Table(name = "t_cm_funcionario")
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

    public Funcionario() {}

    public Funcionario(Long id_funcionario, Filial filial, String nm_funcionario, String nm_email_corporativo, String nm_senha, String nm_cargo, Set<Funcao> funcoes) {
        this.id_funcionario = id_funcionario;
        this.filial = filial;
        this.nm_funcionario = nm_funcionario;
        this.nm_email_corporativo = nm_email_corporativo;
        this.nm_senha = nm_senha;
        this.nm_cargo = nm_cargo;
        this.funcoes = funcoes;
    }

    public Long getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Long id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public String getNm_funcionario() {
        return nm_funcionario;
    }

    public void setNm_funcionario(String nm_funcionario) {
        this.nm_funcionario = nm_funcionario;
    }

    public String getNmEmailCorporativo() {
        return this.nm_email_corporativo;
    }

    public void setNmEmailCorporativo(String nm_email_corporativo) {
        this.nm_email_corporativo = nm_email_corporativo;
    }

    public String getNm_senha() {
        return nm_senha;
    }

    public void setNm_senha(String nm_senha) {
        this.nm_senha = nm_senha;
    }

    public String getNm_cargo() {
        return nm_cargo;
    }

    public void setNm_cargo(String nm_cargo) {
        this.nm_cargo = nm_cargo;
    }

    public Set<Funcao> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(Set<Funcao> funcoes) {
        this.funcoes = funcoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(id_funcionario, that.id_funcionario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_funcionario);
    }
}
