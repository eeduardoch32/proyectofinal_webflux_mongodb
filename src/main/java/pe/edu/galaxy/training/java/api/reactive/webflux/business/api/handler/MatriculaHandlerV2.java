package pe.edu.galaxy.training.java.api.reactive.webflux.business.api.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.service.MatriculaServiceV2;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class MatriculaHandlerV2 {

    private final MatriculaServiceV2 matriculaService;

    // =====================================================
    // GET /api/v2/matriculas
    // =====================================================

    public Mono<ServerResponse> findAll(ServerRequest request) {

        return matriculaService.findAll()
                .collectList()
                .flatMap(lista ->
                        ok().bodyValue(lista)
                );
    }


    // =====================================================
    // GET /api/v2/matriculas/{id}
    // =====================================================

    public Mono<ServerResponse> findById(ServerRequest request) {

        String id = request.pathVariable("id");

        return matriculaService.findById(id)
                .flatMap(matricula ->
                        ok().bodyValue(matricula)
                );
    }


    // =====================================================
    // GET /api/v2/matriculas/codigo/{codigoMatricula}
    // =====================================================

    public Mono<ServerResponse> findByCodigoMatricula(
            ServerRequest request) {

        String codigoMatricula =
                request.pathVariable("codigoMatricula");

        return matriculaService
                .findByCodigoMatricula(codigoMatricula)
                .flatMap(matricula ->
                        ok().bodyValue(matricula)
                );
    }


    // =====================================================
    // POST /api/v2/matriculas
    // =====================================================

    public Mono<ServerResponse> add(ServerRequest request) {

        return request
                .bodyToMono(MatriculaDocument.class)
                .flatMap(matriculaService::save)
                .flatMap(matricula ->
                        created(request.uri())
                                .bodyValue(matricula)
                );
    }


    // =====================================================
    // PUT /api/v2/matriculas/{id}
    // =====================================================

    public Mono<ServerResponse> update(ServerRequest request) {

        String id = request.pathVariable("id");

        return request
                .bodyToMono(MatriculaDocument.class)
                .doOnNext(matricula ->
                        matricula.setId(id)
                )
                .flatMap(matriculaService::update)
                .flatMap(matricula ->
                        ok().bodyValue(matricula)
                );
    }


    // =====================================================
    // DELETE /api/v2/matriculas/{id}
    // =====================================================

    public Mono<ServerResponse> delete(ServerRequest request) {

        String id = request.pathVariable("id");

        return matriculaService
                .delete(id)
                .then(ok().build());
    }
}