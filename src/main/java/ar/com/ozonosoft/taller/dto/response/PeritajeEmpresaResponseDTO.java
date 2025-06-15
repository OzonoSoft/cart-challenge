package ar.com.ozonosoft.taller.dto.response;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class PeritajeEmpresaResponseDTO implements Serializable {
	private Long id;
	private PeritajeEstadoDTO estado;
	private BigDecimal presupuesto;
	private String descripcion;
	private ClienteDTO cliente;
	private VehiculoDTO vehiculo;
	private Integer nroSiniestro;
	private Date fecha;
}
