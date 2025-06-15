package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class EmpresaRequest {

	@NotNull  (message = "Debe especificar el codigo")
	private String codigo;
	@NotNull  (message = "Debe especificar la razon social")
	private String razonSocial;
	private String descripcion;
	@NotNull  (message = "Debe especificar el cuit")
	private Long cuit;
	@NotNull  (message = "Debe especificar la calle")
	private String calle;
	@NotNull  (message = "Debe especificar el numero de calle")
	private Integer numeroCalle;
	private Integer piso;
	private String depto;
	private Integer codigoPostal;
	@NotNull  (message = "Debe especificar el telefono")
	private Long telFijo;
	@NotNull  (message = "Debe especificar numero de un celular")
	private Long telCelular;
	private String urlWeb;


	public Boolean isValidInput(){
		return !(this.codigo == null
				|| this.codigo.isEmpty()
				|| this.razonSocial == null
				|| this.razonSocial.isEmpty()
				|| this.calle == null
				|| this.calle.isEmpty()
				|| this.cuit == null
				|| this.cuit < 0
				|| this.numeroCalle == null
				|| this.numeroCalle < 0
				|| this.telFijo == null
				|| this.telFijo < 0
				|| this.telCelular == null
				|| this.telCelular < 0
		);
	}
}
