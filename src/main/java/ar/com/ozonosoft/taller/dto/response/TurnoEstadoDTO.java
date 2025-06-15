package ar.com.ozonosoft.taller.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author OzonoSoft
 */
@Data
public class TurnoEstadoDTO implements Serializable {
	private Integer id;
	private String descripcion;
}
