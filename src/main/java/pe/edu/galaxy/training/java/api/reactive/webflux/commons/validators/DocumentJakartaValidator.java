package pe.edu.galaxy.training.java.api.reactive.webflux.commons.validators;


import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions.CustomValidationException;

@Component
@RequiredArgsConstructor
public class DocumentJakartaValidator {

    private final Validator validator;

    public <T> T validate(T object) throws CustomValidationException {
        var errors = validator.validate(object);
        if (errors.isEmpty()) {
            return object;
        } else {
            String errorDetails = errors.stream().map(er -> er.getMessage()).collect(Collectors.joining(", "));
            throw new CustomValidationException(errorDetails);
        }
    }
}