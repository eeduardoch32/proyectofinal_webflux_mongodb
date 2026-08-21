package pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

//import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

//@Slf4j
@Component
@Order(Integer.MIN_VALUE)
class CustomGlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

	public CustomGlobalExceptionHandler(final ErrorAttributes errorAttributes, final WebProperties.Resources resources,
			final ApplicationContext applicationContext, final ServerCodecConfigurer configurer) {
		super(errorAttributes, resources, applicationContext);
		setMessageReaders(configurer.getReaders());
		setMessageWriters(configurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

		ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
		Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);
		Throwable throwable = getError(request);
		HttpStatusCode httpStatus = determineHttpStatus(throwable);
		errorPropertiesMap.put("status", httpStatus.value());
		// errorPropertiesMap.put("val", "xxxxx");
		errorPropertiesMap.remove("error");

		if (throwable instanceof CustomValidationException) {
			CustomValidationException cve = (CustomValidationException) throwable;
			Map<String, String> errors = new HashMap<>();
			if (cve.getErrors()!=null) {
				cve.getErrors().getFieldErrors().stream().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
				errorPropertiesMap.put("message", errors);
			}
			
			//errorPropertiesMap.remove("message");
		}

		return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(Mono.just(errorPropertiesMap), Map.class));
	}

	private HttpStatusCode determineHttpStatus(Throwable throwable) {

		if (throwable instanceof ResponseStatusException) {
			return ((ResponseStatusException) throwable).getStatusCode();
		} else if (throwable instanceof CustomNotFoundException) {
			return HttpStatus.NOT_FOUND;
		} else if (throwable instanceof CustomBadRequestException || throwable instanceof CustomValidationException) {
			return HttpStatus.BAD_REQUEST;
		} else if (throwable instanceof CustomNotContentException) {
			return HttpStatus.NO_CONTENT;
		} else {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}