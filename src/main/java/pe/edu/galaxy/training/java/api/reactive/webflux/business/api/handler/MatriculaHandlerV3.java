package pe.edu.galaxy.training.java.api.reactive.webflux.business.api.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.service.MatriculaServiceV3;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MatriculaHandlerV3 {

    private final MatriculaServiceV3 matriculaService;

    public Mono<ServerResponse> findAll(ServerRequest request) {

        return matriculaService.findAll()
                .collectList()
                .flatMap(lista ->
                        ServerResponse.ok()
                                .bodyValue(lista)
                );
    }

    public Mono<ServerResponse> findById(ServerRequest request) {

        String id = request.pathVariable("id");

        return matriculaService.findById(id)
                .flatMap(dto ->
                        ServerResponse.ok()
                                .bodyValue(dto)
                );
    }
}
