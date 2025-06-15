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
@Table(name = "talleres")
public class Taller implements Serializable {

	private static final long serialVersionUID = -6602243609816768486L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;
	
	@Column(name = "codigo")
	private String codigo;

	@Column(name = "razon_social")
	private String razonSocial;

	@Column(name = "cuit")
	private Long cuit;

	@Column(name = "calle")
	private String calle;

	@Column(name = "numero_calle")
	private Integer numeroCalle;

	@Column(name = "cp_localidad")
	private Integer codigoPostal;

	@Column(name = "tel_fijo")
	private Long telFijo;

	@Column(name = "tel_celular")
	private Long telCelular;

	@Column(name = "url_web")
	private String urlWeb;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(name = "status")
	private Integer status;
}
