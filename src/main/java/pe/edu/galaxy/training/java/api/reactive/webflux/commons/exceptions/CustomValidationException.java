package pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions;

import org.springframework.validation.Errors;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Errors errors;

	public CustomValidationException() {
		super();
	}

	public CustomValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomValidationException(String message) {
		super(message);
	}

	public CustomValidationException(Throwable cause) {
		super(cause);
	}
	
	public CustomValidationException(Errors errors) {
		this.errors=errors;
	}

}
