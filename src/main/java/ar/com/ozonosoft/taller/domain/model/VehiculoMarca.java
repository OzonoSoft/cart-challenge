package ar.com.ozonosoft.taller.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "marcas_vehiculos")
public class VehiculoMarca implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "descripcion")
	private String descripcion;
}
