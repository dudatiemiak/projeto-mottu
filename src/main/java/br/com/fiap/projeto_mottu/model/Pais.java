package br.com.fiap.projeto_mottu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_pais")
@Data
public class Pais {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pais;
	@NotEmpty(message = "O nome do país deve ser informado")
    @Size(max = 50, message = "O estado deve ter no máximo 50 caracteres")
	@Column(name = "nm_pais")
	private String nm_pais;
	
}
