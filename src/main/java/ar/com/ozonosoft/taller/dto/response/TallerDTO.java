package ar.com.ozonosoft.taller.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author OzonoSoft
 */
@Data
public class TallerDTO implements Serializable {
	private String id;
	private String codigo;
	private String razonSocial;
	private String descripcion;
	private Long cuit;
	private String calle;
	private Integer numeroCalle;
	private Integer codigoPostal;
	private Long telFijo;
	private Long telCelular;
	private String urlWeb;
	private Date createdAt;
	private Date updatedAt;
	private Integer status;
}
