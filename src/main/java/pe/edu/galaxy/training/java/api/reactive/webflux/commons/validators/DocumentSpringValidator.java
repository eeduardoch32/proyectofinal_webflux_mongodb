package pe.edu.galaxy.training.java.api.reactive.webflux.commons.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions.CustomValidationException;

@Data
@Component
@RequiredArgsConstructor
public class DocumentSpringValidator {

    private final Validator validator;
    private String name="";
    public <T> T validate(T object) throws CustomValidationException {
    	Errors errors = new BeanPropertyBindingResult(object, name);
        validator.validate(object, errors);
        if (!errors.hasErrors()) {
            return object;
        } else {
            throw new CustomValidationException(errors);
        }
    }
}