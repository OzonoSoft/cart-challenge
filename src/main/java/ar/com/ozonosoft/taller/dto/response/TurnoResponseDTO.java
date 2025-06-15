package ar.com.ozonosoft.taller.dto.response;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import ar.com.ozonosoft.taller.domain.model.Cliente;
import ar.com.ozonosoft.taller.domain.model.Vehiculo;

/**
 * @author OzonoSoft
 */
@Data
public class TurnoResponseDTO implements Serializable {
	private String id;
	private TurnoEstadoDTO estado;
	private String observaciones;
	private Cliente cliente;
	private String peritajeId;
	private String tallerId;
	private Vehiculo vehiculo;
	private Date fecha;
	private Integer hora;
	private Date createdAt;
	private Date updatedAt;
}
