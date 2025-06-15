package ar.com.ozonosoft.taller.dto.request;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class PeritajeRequest {

	@NotNull  (message = "Debe especificar el presupuesto")
	private BigDecimal presupuesto;
	@NotNull  (message = "Debe cargar una descripcion")
	private String desdripcion;
	@NotNull  (message = "Debe especificar el Cliente")
	private String clienteId;
    @NotNull  (message = "Debe especificar la Empresa")
	private String empresaId;
	@NotNull  (message = "Debe especificar un numero de siniestro")
	private Long nroSiniestro;
	@NotNull  (message = "Debe especificar el id del vehiculo")
	private String vehiculoId;
	@NotNull  (message = "Debe especificar un taller")
	private String tallerId;

	public Boolean isValidInput(){
		return !(this.clienteId == null || this.clienteId.isEmpty()
				|| this.presupuesto == null || this.presupuesto.compareTo(BigDecimal.ZERO) < 0
				|| this.empresaId == null || this.empresaId.isEmpty()
				|| this.desdripcion == null || this.desdripcion.isEmpty()
				|| this.vehiculoId == null || this.vehiculoId.isEmpty()
				|| this.tallerId == null || this.tallerId.isEmpty()
				|| this.nroSiniestro == null || this.nroSiniestro < 0
		);
	}
}
