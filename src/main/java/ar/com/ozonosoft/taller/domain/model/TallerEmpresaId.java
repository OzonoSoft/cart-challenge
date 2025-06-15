package ar.com.ozonosoft.taller.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author OzonoSoft
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TallerEmpresaId implements Serializable {

	@Column(name = "taller_id")
	private String tallerId;

	@Column(name = "empresa_id")
	private String empresaId;


}
