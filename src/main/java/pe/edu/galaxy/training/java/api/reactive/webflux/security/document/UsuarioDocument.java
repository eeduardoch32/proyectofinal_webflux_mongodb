package pe.edu.galaxy.training.java.api.reactive.webflux.security.document;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The EmployeeRepository is a simple interface which extend JpaRepository to
 * provide all default methods to your entity/document repository.
 *
 * @author Aristedes Novoa
 * @version 1.0
 * @since 17 Feb, 2024
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class UsuarioDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Field(name = "_id")
	@Builder.Default
	private String id = UUID.randomUUID().toString();

	@Field(name = "usuario")
	private String usuario;

	@Field(name = "clave")
	private String clave;
	
	//@Field(name = "fecha_bloqueo")
	//private String fechBloquea;

	@Field(name = "roles")
	private String[] roles;

	@Field(name = "estado")
	private String estado;

}
