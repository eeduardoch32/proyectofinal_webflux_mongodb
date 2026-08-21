package pe.edu.galaxy.training.java.api.reactive.webflux.business.mappers;

import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MatriculaMapper {

	MatriculaDocument copy(MatriculaDocument taller);
	
	void copy(MatriculaDocument source, MatriculaDocument target);
	
	void copy(MatriculaDto source, MatriculaDocument target);

	MatriculaDocument toDocument(MatriculaDto tallerDto);

	MatriculaDto toDto(MatriculaDocument tallerDocument);
	
	Mono<MatriculaDto> toMonoDto(MatriculaDocument tallerDocument);
	
	Flux<MatriculaDto> toFluxDto(Flux<MatriculaDocument> fluxTallerDocument);

	MatriculaDocument.Alumno toDto(MatriculaDto.Alumno alumno);

	MatriculaDocument.Pago toDto(MatriculaDto.Pago pago);

}
