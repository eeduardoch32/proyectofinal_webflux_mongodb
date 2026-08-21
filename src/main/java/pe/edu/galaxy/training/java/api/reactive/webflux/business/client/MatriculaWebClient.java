package pe.edu.galaxy.training.java.api.reactive.webflux.business.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MatriculaWebClient {

    private final WebClient matriculaWebClient;

    // =====================================================
    // GET /api/v2/matriculas
    // =====================================================

    public Flux<MatriculaDocument> findAll() {

        return matriculaWebClient
                .get()
                .retrieve()
                .bodyToFlux(MatriculaDocument.class);
    }

    // =====================================================
    // GET /api/v2/matriculas/{id}
    // =====================================================

    public Mono<MatriculaDocument> findById(String id) {

        return matriculaWebClient
                .get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(MatriculaDocument.class);
    }

    // =====================================================
    // POST /api/v2/matriculas
    // =====================================================

    public Mono<MatriculaDocument> save(
            MatriculaDocument matricula) {

        return matriculaWebClient
                .post()
                .bodyValue(matricula)
                .retrieve()
                .bodyToMono(MatriculaDocument.class);
    }

    // =====================================================
    // PUT /api/v2/matriculas/{id}
    // =====================================================

    public Mono<MatriculaDocument> update(
            String id,
            MatriculaDocument matricula) {

        return matriculaWebClient
                .put()
                .uri("/{id}", id)
                .bodyValue(matricula)
                .retrieve()
                .bodyToMono(MatriculaDocument.class);
    }

    // =====================================================
    // DELETE /api/v2/matriculas/{id}
    // =====================================================

    public Mono<Void> delete(String id) {

        return matriculaWebClient
                .delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}