package pe.edu.galaxy.training.java.api.reactive.webflux.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.mappers.MatriculaMapper;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.repository.MatriculaRepository;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.service.MatriculaServiceV3;

import pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions.CustomNotContentException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MatriculaServiceV3Impl implements MatriculaServiceV3 {

    private final MatriculaRepository matriculaRepository;
    private final MatriculaMapper matriculaMapper;

    public MatriculaServiceV3Impl(final MatriculaRepository matriculaRepository, final MatriculaMapper matriculaMapper) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
    }


@Override
public Flux<MatriculaDto> findAll() {

    System.out.println("========== ENTRE AL SERVICE FIND ALL ==========");

    return matriculaMapper.toFluxDto(matriculaRepository.findByEstado("REGISTRADO").switchIfEmpty(Flux.error(new CustomNotContentException())));

}




    @Override
    public Mono<MatriculaDto> findById(String id) {
        return matriculaRepository.findById(id).map(matriculaMapper::toDto).switchIfEmpty(Mono.error(new CustomNotContentException()));
    }


}