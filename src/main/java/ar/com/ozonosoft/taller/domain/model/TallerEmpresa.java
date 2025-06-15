package ar.com.ozonosoft.taller.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author OzonoSoft
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "talleres_empresas")
public class TallerEmpresa implements Serializable {

	private static final long serialVersionUID = -6602243609816768486L;

	@EmbeddedId
	private TallerEmpresaId id;

}
