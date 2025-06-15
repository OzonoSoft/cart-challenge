package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class ClienteRequest {

	@NotNull  (message = "Debe especificar un nombre")
	private String nombre;
	@NotNull  (message = "Debe especificar un apellido")
	private String apellido;
	private String razonSocial;
	@NotNull  (message = "Debe especificar un numero de documento")
	private Long nroDoc;
	@NotNull  (message = "Debe especificar el tipo de cliente")
	private Integer tipo;
	@NotNull  (message = "Debe especificar un taller")
	private String tallerId;

	public Boolean isValidInput(){
		return !(this.nombre == null
				|| this.nombre.isEmpty()
				|| this.apellido == null
				|| this.apellido.isEmpty()
				|| this.razonSocial == null
				|| this.razonSocial.isEmpty()
				|| this.tallerId == null
				|| this.tallerId.isEmpty()
				|| this.nroDoc == null
				|| this.nroDoc < 0
				|| this.tipo == null
				|| this.tipo < 0
		);
	}
}
