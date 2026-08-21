package pe.edu.galaxy.training.java.api.reactive.webflux.business.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.service.MatriculaServiceV1;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pe.edu.galaxy.training.java.api.reactive.webflux.business.api.constants.APIConstants.API_MATRICULAS;

@RestController
@RequestMapping(API_MATRICULAS)
@RequiredArgsConstructor
public class MatriculaControllerV1 {
    private final MatriculaServiceV1 matriculaService;

    @GetMapping
    public Flux<MatriculaDocument> findAll() {
        return matriculaService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<MatriculaDocument> findById(@PathVariable String id) {
        return matriculaService.findById(id);
    }

    @PostMapping
    public Mono<MatriculaDocument> save(@RequestBody MatriculaDocument dto) {
        return matriculaService.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<MatriculaDocument> update(
            @PathVariable String id,
            @RequestBody MatriculaDocument dto) {

        dto.setId(id);
        return matriculaService.update(dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return matriculaService.delete(id);
    }
}
