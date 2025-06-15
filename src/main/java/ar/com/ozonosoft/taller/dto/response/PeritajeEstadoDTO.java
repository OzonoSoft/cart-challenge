package ar.com.ozonosoft.taller.dto.response;


import java.io.Serializable;

import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class PeritajeEstadoDTO implements Serializable {
	private Integer id;
	private String descripcion;
}
