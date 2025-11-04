package br.com.fiap.projeto_mottu.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "t_cm_moto")
@Data
public class Moto{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_moto;
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "A moto deve estar relacionada a um cliente!")
	private Cliente cliente;
    @Size(max = 10, message = "Valor da placa inválido")
	private String nm_placa;
    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    @NotNull(message = "A moto deve estar vinculada a um modelo!")
    private Modelo modelo;
    @ManyToOne
    @JoinColumn(name = "id_filial_departamento", nullable = false)
    @NotNull(message = "A moto deve estar vinculada a uma filial departamento")
	private FilialDepartamento filial_departamento;
    @NotNull(message = "Não é possível inserir uma moto sem sua situação/condição (Manutenção, Funcionamento ou Pátio)")
    @Enumerated(EnumType.STRING)
	private SituacaoEnum st_moto;
    @Column(name = "km_rodado")
    private Double km_rodado;
	
}
