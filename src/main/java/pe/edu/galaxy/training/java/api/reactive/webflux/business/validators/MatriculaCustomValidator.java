package pe.edu.galaxy.training.java.api.reactive.webflux.business.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;

@Component
public class MatriculaCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		 return MatriculaDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tallerId", "field.required","El tallerId es requerido");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tallerNombre", "field.required","El nombre del taller es requerido");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "estado", "field.required","El estado es requerido");
	        */
		MatriculaDto request = (MatriculaDto) target;
	        if (request.getEstado().trim().length()!=1 || request.getEstado().matches("[^123]")) {
	            errors.rejectValue("estado", "valores permitidos",null, "Valores permitidos : [1,2,3]");
	        }
	}

}
