package br.com.fiap.projeto_mottu.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
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
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_manutencao")
@Data
public class Manutencao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_manutencao;
	@ManyToOne
	@JoinColumn(name = "id_moto", nullable = false)
    @NotNull(message = "A manutenção tem que estar vinculada a uma moto!")
	private Moto moto;
	@NotNull
	@PastOrPresent(message = "Data inválida")
	@Column(name = "dt_entrada")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dt_entrada;
	@PastOrPresent(message = "Data inválida")
	@Column(name = "dt_saida")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dt_saida;
	@NotEmpty(message = "A descrição da manutenção da moto deve ser informada!")
    @Size(max = 300, message = "A descrição da manutenção deve ter no máximo 300 caracteres")
	@Column(name = "ds_manutencao")
	private String ds_manutencao;
	
}
