package pe.edu.galaxy.training.java.api.reactive.webflux.business.service;

import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MatriculaServiceV2 {

    Flux<MatriculaDocument> findAll();

    Mono<MatriculaDocument> findById(String id);

    Mono<MatriculaDocument> save(MatriculaDocument matricula);

    Mono<MatriculaDocument> update(MatriculaDocument matricula);

    Mono<Void> delete(String id);

    Mono<MatriculaDocument> findByCodigoMatricula(String codigoMatricula);
}
