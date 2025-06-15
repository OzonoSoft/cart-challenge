package ar.com.ozonosoft.taller.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author OzonoSoft
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turnos_estados")
public class TurnoEstado implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "descripcion")
	private String descripcion;
}
