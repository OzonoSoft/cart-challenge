package ar.com.ozonosoft.taller.dto.response;


import java.io.Serializable;

import lombok.Data;

/**
 * @author OzonoSoft
 */
@Data
public class ClienteDTO implements Serializable {
	private String id;
	private String nombre;
	private String apellido;
	private String razonSocial;
	private Long nroDoc;
	private TipoClienteDTO tipo;
}
