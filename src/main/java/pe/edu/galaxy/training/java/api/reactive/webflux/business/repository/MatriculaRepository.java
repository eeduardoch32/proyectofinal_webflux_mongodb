package pe.edu.galaxy.training.java.api.reactive.webflux.business.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface MatriculaRepository extends ReactiveMongoRepository<MatriculaDocument, String> {

    Flux<MatriculaDocument> findByEstado(String estado);

    Mono<MatriculaDocument> findByCodigoMatricula(String codigoMatricula);

    @Query("{ 'alumno.nombres': { $regex: '(?i)?0' } }")
    Flux<MatriculaDocument> findByAlumnoNombresLike(String nombres);

}