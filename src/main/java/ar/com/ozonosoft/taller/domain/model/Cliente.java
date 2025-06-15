package ar.com.ozonosoft.taller.domain.model;

import java.io.Serializable;
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
@Table(name = "clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = -6602243609816768486L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "razon_social")
	private String razonSocial;
	
	@Column(name = "nro_doc")
	private Long nroDoc;

	@ManyToOne
	@JoinColumn(name="tipo", referencedColumnName="id")
	private TipoCliente tipo;

	@Column(name = "taller_id")
	private String tallerId;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
}
