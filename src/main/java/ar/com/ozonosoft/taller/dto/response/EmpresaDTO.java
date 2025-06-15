package ar.com.ozonosoft.taller.dto.response;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class EmpresaDTO implements Serializable {
	private String id;
	private String codigo;
	private String razonSocial;
	private String descripcion;
	private Long cuit;
	private String calle;
	private Integer numeroCalle;
	private Integer piso;
	private String depto;
	private Integer codigoPostal;
	private Long telFijo;
	private Long telCelular;
	private String urlWeb;
	private Date createdAt;
	private Date updatedAt;
	private Integer status;
}
