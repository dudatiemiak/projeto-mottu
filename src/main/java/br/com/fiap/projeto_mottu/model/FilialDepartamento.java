package br.com.fiap.projeto_mottu.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Table(name = "t_cm_filial_departamento")
@Data
public class FilialDepartamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_filial_departamento;
	@ManyToOne
	@JoinColumn(name = "id_filial", nullable = false)
    @NotNull(message = "A filial departamento deve estar vinculada a uma filial")
	private Filial filial;
	@ManyToOne
	@JoinColumn(name = "id_departamento", nullable = false)
    @NotNull(message = "A filial departamento deve estar vinculada a um departamento")
	private Departamento departamento;
	@PastOrPresent
	@Column(name = "dt_entrada")
	@NotNull
	private LocalDate dt_entrada;
	@PastOrPresent
	@Column(name = "dt_saida")
	private LocalDate dt_saida;
}
