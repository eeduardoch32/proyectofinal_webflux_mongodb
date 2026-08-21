package pe.edu.galaxy.training.java.api.reactive.webflux.security.dto;

public record ResponseDto<T>(
        Integer status,
        String message,
        T data
) {}