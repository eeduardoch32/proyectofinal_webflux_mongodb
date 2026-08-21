package pe.edu.galaxy.training.java.api.reactive.webflux.business.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.service.MatriculaServiceV1;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Map;

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

        return matriculaService
                .findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Matrícula no encontrada"
                        )
                ))
                .flatMap(actual -> {

                    actual.setCodigoMatricula(dto.getCodigoMatricula());
                    actual.setEstado(dto.getEstado());
                    actual.setFechaMatricula(dto.getFechaMatricula());

                    return matriculaService.update(actual);
                });
    }

    @DeleteMapping("/{id}")
    public  Mono<Map<String, String>> delete(@PathVariable String id) {
        return matriculaService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Matrícula no encontrada"
                        )
                ))
                .flatMap(matricula -> matriculaService.delete(id))
                .thenReturn(
                        Map.of(
                                "mensaje", "Matrícula eliminada correctamente",
                                "id", id
                        )
                );
    }
}
