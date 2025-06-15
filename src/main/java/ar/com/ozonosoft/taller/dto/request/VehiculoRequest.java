package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class VehiculoRequest {
	@NotNull  (message = "Debe especificar la marca")
	private Integer marcaId;
	@NotNull  (message = "Debe especificar la patente")
	private String patente;
    @NotNull  (message = "Debe especificar el año")
	private Integer anio;
	@NotNull  (message = "Debe especificar el modelo")
	private String modelo;
	@NotNull  (message = "Debe especificar la version")
	private String version;
	@NotNull  (message = "Debe especificar un propietario")
	private String clienteId;
	@NotNull  (message = "Debe especificar un taller")
	private String tallerId;


	public Boolean isValidInput(){
		return !(this.patente == null
				|| this.patente.isEmpty()
				|| this.modelo == null
				|| this.modelo.isEmpty()
				|| this.clienteId == null
				|| this.clienteId.isEmpty()
				|| this.tallerId == null
				|| this.tallerId.isEmpty()
				|| this.version == null
				|| this.version.isEmpty()
				|| this.marcaId == null
				|| this.marcaId < 0
				|| this.anio == null
				|| this.anio < 1900
		);
	}
}
