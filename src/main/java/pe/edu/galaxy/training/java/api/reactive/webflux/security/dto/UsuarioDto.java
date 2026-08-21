package pe.edu.galaxy.training.java.api.reactive.webflux.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto{

	private String id;

	private String usuario;

	private String clave;
	
	private String[] roles;

	private String estado;

}
