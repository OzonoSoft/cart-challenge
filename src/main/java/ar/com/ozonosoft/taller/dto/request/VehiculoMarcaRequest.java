package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class VehiculoMarcaRequest {
	@NotNull  (message = "Debe especificar la descripcion")
	private String descripcion;

	public Boolean isValidInput(){
		return !(this.descripcion == null
				|| this.descripcion.isEmpty()
		);
	}
}
