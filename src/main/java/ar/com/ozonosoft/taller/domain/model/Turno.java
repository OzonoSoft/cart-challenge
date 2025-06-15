package ar.com.ozonosoft.taller.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author OzonoSoft
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno implements Serializable {

	private static final long serialVersionUID = -6602243609816768486L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;

	@ManyToOne
	@JoinColumn(name="estado_id", referencedColumnName="id")
	private TurnoEstado estado;

	@Column(name = "observaciones")
	private String observaciones;

	@ManyToOne
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	private Cliente cliente;

	@Column(name = "peritaje_id")
	private String peritajeId;

	@Column(name = "taller_id")
	private String tallerId;

	@ManyToOne
	@JoinColumn(name="vehiculo_id", referencedColumnName="id")
	private Vehiculo vehiculo;

	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name = "hora")
	private Integer hora;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
}
