package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author OzonoSoft
 */
@Data
public class CreateTurnoRequest {

	private String observaciones;
	@NotNull  (message = "Debe especificar el Cliente")
	private String clienteId;
	private String peritajeaId;
	@NotNull  (message = "Debe especificar un taller")
	private String tallerId;
	@NotNull  (message = "Debe especificar el id del vehiculo")
	private String vehiculoId;
	@NotNull  (message = "Debe especificar una fecha")
	private Date fecha;
	private Integer hora;

	public Boolean isValidInput(){
		return !(this.clienteId == null || this.clienteId.isEmpty()
				|| this.fecha == null || fecha.before(new Date())
				|| this.vehiculoId == null || this.vehiculoId.isEmpty()
				|| this.tallerId == null || this.tallerId.isEmpty()
		);
	}
}
