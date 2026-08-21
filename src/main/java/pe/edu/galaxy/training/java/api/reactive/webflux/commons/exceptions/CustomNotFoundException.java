package pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions;

public class CustomNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomNotFoundException(String id) {
		super("Registro con el id => " + id + " no encontrado.");
	}
	public CustomNotFoundException(Long id) {
		super("Registro con el => " + id + " no encontrado.");
	}
}
