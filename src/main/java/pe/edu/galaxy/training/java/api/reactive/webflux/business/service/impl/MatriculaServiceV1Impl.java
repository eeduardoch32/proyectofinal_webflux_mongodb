package pe.edu.galaxy.training.java.api.reactive.webflux.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.mappers.MatriculaMapper;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.repository.MatriculaRepository;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.service.MatriculaServiceV1;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MatriculaServiceV1Impl implements MatriculaServiceV1 {

    private final MatriculaRepository matriculaRepository;

    public MatriculaServiceV1Impl(final MatriculaRepository matriculaRepository, final MatriculaMapper matriculaMapper) {
        this.matriculaRepository = matriculaRepository;
    }

    @Override
    public Flux<MatriculaDocument> findAll() {

        return matriculaRepository.findAll();
    }


    @Override
    public Mono<MatriculaDocument> findById(String id) {

        return matriculaRepository.findById(id);
    }


    @Override
    public Mono<MatriculaDocument> save(MatriculaDocument matricula) {

        return matriculaRepository.save(matricula);
    }


    @Override
    public Mono<MatriculaDocument> update(MatriculaDocument matricula) {

        return matriculaRepository
                .findById(matricula.getId())
                .flatMap(actual -> {

                    // Aquí puedes actualizar los campos
                    actual.setCodigoMatricula(matricula.getCodigoMatricula());
                    actual.setEstado(matricula.getEstado());

                    return matriculaRepository.save(actual);
                });
    }


    @Override
    public Mono<Void> delete(String id) {

        return matriculaRepository.deleteById(id);
    }


    @Override
    public Flux<MatriculaDocument> findByEstado(String estado) {

        return matriculaRepository.findByEstado(estado);
    }
}
