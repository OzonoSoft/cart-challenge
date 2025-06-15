package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class TallerRequest {

	@NotNull  (message = "Debe especificar un nombre de taller")
	private String codigo;
	@NotNull  (message = "Debe especificar la razon social")
	private String razonSocial;
	@NotNull  (message = "Debe especificar el cuit")
	private Long cuit;
	@NotNull  (message = "Debe especificar la calle")
	private String calle;
	@NotNull  (message = "Debe especificar el numero")
	private Integer numeroCalle;
	private Integer codigoPostal;
	private Long telFijo;
	@NotNull  (message = "Debe especificar el numero de celular")
	private Long telCelular;
	private String urlWeb;

	public Boolean isValidInput(){
		return !(this.codigo == null
				|| this.codigo.isEmpty()
				|| this.razonSocial == null
				|| this.razonSocial.isEmpty()
				|| this.calle == null
				|| this.calle.isEmpty()
				|| this.numeroCalle == null
				|| this.numeroCalle < 0
				|| this.cuit == null
				|| this.cuit < 0
				|| this.telCelular == null
				|| this.telCelular < 0
		);
	}
}
