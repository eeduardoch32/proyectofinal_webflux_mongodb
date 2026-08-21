package pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions;

public class CustomBadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomBadRequestException(String id) {
		super(String.format("El id %s debe tener 36 caracteres!",id));
	}

	public CustomBadRequestException(Long id) {
		super(String.format("El id %s debe tener 36 caracteres!",id));
	}
	
	public CustomBadRequestException(String msg, Boolean sw) {
		super(msg);
	}
}
