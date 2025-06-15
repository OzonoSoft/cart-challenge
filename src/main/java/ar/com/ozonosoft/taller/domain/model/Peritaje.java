package ar.com.ozonosoft.taller.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "peritajes")
public class Peritaje implements Serializable {

	private static final long serialVersionUID = -6602243609816768486L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;

	@ManyToOne
	@JoinColumn(name="estado_id", referencedColumnName="id")
	private PeritajeEstado estado;
	
	@Column(name = "presupuesto")
	private BigDecimal presupuesto;

	@Column(name = "descripcion")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name="empresa_id", referencedColumnName="empresa_id")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="taller_id", referencedColumnName="id")
	private Taller taller;

	@ManyToOne
	@JoinColumn(name="vehiculo_id", referencedColumnName="ID")
	private Vehiculo vehiculo;

	@Column(name = "nro_siniestro")
	private Long nroSiniestro;

	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
}
