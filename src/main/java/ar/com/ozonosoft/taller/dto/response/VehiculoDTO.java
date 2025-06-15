package ar.com.ozonosoft.taller.dto.response;


import java.io.Serializable;

import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class VehiculoDTO implements Serializable {
	private String id;
	private VehiculoMarcaDTO marca;
	private String patente;
	private Integer anio;
	private String modelo;
	private String version;
}
