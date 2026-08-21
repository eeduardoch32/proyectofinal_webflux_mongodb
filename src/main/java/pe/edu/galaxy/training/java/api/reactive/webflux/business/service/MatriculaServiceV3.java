package pe.edu.galaxy.training.java.api.reactive.webflux.business.service;

import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MatriculaServiceV3 {

    Flux<MatriculaDto> findAll();

    Mono<MatriculaDto> findById(String id);




}