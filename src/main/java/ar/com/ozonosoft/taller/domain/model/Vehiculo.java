package ar.com.ozonosoft.taller.domain.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OzonoSoft
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculos")
public class Vehiculo implements Serializable {

	private static final long serialVersionUID = -6602243609816768486L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;

	@ManyToOne
	@JoinColumn(name="marca_id", referencedColumnName="id")
	private VehiculoMarca marca;

	@Column(name = "cliente_id")
	private String titular;

	@Column(name = "taller_id")
	private String tallerId;
	
	@Column(name = "patente")
	private String patente;

	@Column(name = "anio")
	private Integer anio;
	
	@Column(name = "modelo")
	private String modelo;
	
	@Column(name = "version")
	private String version;
}
