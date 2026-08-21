package pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions;

public class CustomNotContentException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomNotContentException() {
		super();
	}
	
	public CustomNotContentException(String msg) {
		super(msg);
	}

}
