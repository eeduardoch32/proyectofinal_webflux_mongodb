package pe.edu.galaxy.training.java.api.reactive.webflux.business.mappers.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.document.MatriculaDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.mappers.MatriculaMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MatriculaMapperImpl implements MatriculaMapper {

	public final ModelMapper modelMapper;

	public MatriculaMapperImpl(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		
		Provider<MatriculaDto> providerTallerDto = new AbstractProvider<MatriculaDto>() {

			@Override
			protected MatriculaDto get() {
				MatriculaDto matriculaDto = new MatriculaDto();
				MatriculaDto.Alumno alumno = new  MatriculaDto.Alumno();
				matriculaDto.setAlumno(alumno);
				MatriculaDto.Pago pago = new MatriculaDto.Pago();
				matriculaDto.setPago(pago);
				return matriculaDto;
			}
		};
		
		Provider<MatriculaDocument> providerTallerDocument = new AbstractProvider<MatriculaDocument>() {

			@Override
			protected MatriculaDocument get() {
				MatriculaDocument matriculaDocument = new MatriculaDocument();
				MatriculaDocument.Alumno alumno = new MatriculaDocument.Alumno(null, null, null);
				matriculaDocument.setAlumno(alumno);
				MatriculaDocument.Pago pago = new MatriculaDocument.Pago(null, null, null);
				matriculaDocument.setPago(pago);
				return matriculaDocument;
			}
		};
		
		Converter<MatriculaDocument, MatriculaDto> converterDocumentToDto = new Converter<MatriculaDocument, MatriculaDto>() {
		    @Override
		    public MatriculaDto convert(MappingContext<MatriculaDocument, MatriculaDto> context) {
				MatriculaDocument matriculaDocument =  context.getSource();
				MatriculaDocument.Alumno alumnoDocument=matriculaDocument.getAlumno();
				MatriculaDocument.Pago pagoDocument=matriculaDocument.getPago();
		    	
		    	MatriculaDto matriculaDto = new MatriculaDto();

				matriculaDto.setId(matriculaDocument.getId());
				matriculaDto.setCodigoMatricula(matriculaDocument.getCodigoMatricula());

				MatriculaDto.Alumno alumno = new MatriculaDto.Alumno();
				alumno.setCodigo(alumnoDocument.getCodigo());
				alumno.setNombres(alumnoDocument.getNombres());
				alumno.setApellidos(alumnoDocument.getApellidos());
				matriculaDto.setAlumno(alumno);
				MatriculaDto.Pago pago = new MatriculaDto.Pago();
				pago.setNumeroOperacion(pagoDocument.getNumeroOperacion());
				pago.setMonto(pagoDocument.getMonto());
				pago.setVoucher(pagoDocument.getVoucher());
				matriculaDto.setPago(pago);
				matriculaDto.setEstado(matriculaDocument.getEstado());



				// -------------------------------------------------
				// CURSOS
				// -------------------------------------------------

				if (matriculaDocument.getCursos() != null) {

					matriculaDto.setCursos(
							matriculaDocument.getCursos()
									.stream()
									.map(cursoDocument -> {

										MatriculaDto.Curso curso =
												new MatriculaDto.Curso();

										curso.setCodigo(
												cursoDocument.getCodigo()
										);

										curso.setNombre(
												cursoDocument.getNombre()
										);

										curso.setCreditos(
												cursoDocument.getCreditos()
										);

										return curso;

									})
									.toList()
					);
				}


				return matriculaDto;
		    }
		};
		
		Converter<MatriculaDto, MatriculaDocument> converterDtoToDocument = new Converter<MatriculaDto, MatriculaDocument>() {
		    @Override
		    public MatriculaDocument convert(MappingContext<MatriculaDto, MatriculaDocument> context) {
				MatriculaDto tallerDto =  context.getSource();
				MatriculaDto.Alumno alumnoDto=tallerDto.getAlumno();
				MatriculaDto.Pago pagoDto=tallerDto.getPago();

				MatriculaDocument matriculaDocument = new MatriculaDocument();

				matriculaDocument.setId(tallerDto.getId());
				matriculaDocument.setCodigoMatricula(tallerDto.getCodigoMatricula());
				matriculaDocument.setEstado(tallerDto.getEstado());

				MatriculaDocument.Alumno alumno  = new MatriculaDocument.Alumno(alumnoDto.getCodigo(),
						alumnoDto.getNombres(),alumnoDto.getApellidos());
				matriculaDocument.setAlumno(alumno);

				MatriculaDocument.Pago pago = new MatriculaDocument.Pago(pagoDto.getNumeroOperacion(),
						pagoDto.getMonto(),pagoDto.getVoucher());
				matriculaDocument.setPago(pago);

		        return matriculaDocument;
		    
		    }
		};
		
		this.modelMapper.getConfiguration().setProvider(providerTallerDto);
		this.modelMapper.getConfiguration().setProvider(providerTallerDocument);		
		this.modelMapper.addConverter(converterDocumentToDto);
		this.modelMapper.addConverter(converterDtoToDocument);
		
	}

	@Override
	public MatriculaDocument toDocument(MatriculaDto matriculaDto) {
		return modelMapper.map(matriculaDto, MatriculaDocument.class);
	}

	@Override
	public MatriculaDto toDto(MatriculaDocument matriculaDocument) {
		log.info("matriculaDocument {}",matriculaDocument);
		MatriculaDto  tallerDto=modelMapper.map(matriculaDocument, MatriculaDto.class);
		log.info("matriculaDto {}",tallerDto);
				
		return tallerDto;
	}

	@Override
	public MatriculaDocument copy(MatriculaDocument taller) {
		return modelMapper.map(taller, MatriculaDocument.class);
	}

	@Override
	public Flux<MatriculaDto> toFluxDto(Flux<MatriculaDocument> fluxTallerDocument) {
		return fluxTallerDocument.map(this::toDto);
	}


	@Override
	public Mono<MatriculaDto> toMonoDto(MatriculaDocument tallerDocument) {
		return Mono.just(tallerDocument).map(this::toDto);
	}

	@Override
	public void copy(MatriculaDocument source, MatriculaDocument target) {
		target.setCodigoMatricula(source.getCodigoMatricula());
		target.setAlumno(source.getAlumno());
		target.setCursos(source.getCursos());
		target.setPago(source.getPago());
		target.setEstado(source.getEstado());
	}

	@Override
	public void copy(MatriculaDto source, MatriculaDocument target) {
		target.setCodigoMatricula(source.getCodigoMatricula());
		target.setAlumno(this.toDto(source.getAlumno()));
		target.setPago(this.toDto(source.getPago()));
		target.setEstado(source.getEstado());
	}

	@Override
	public MatriculaDocument.Alumno toDto(MatriculaDto.Alumno alumno) {
		return modelMapper.map(alumno, MatriculaDocument.Alumno.class);
	}

	@Override
	public MatriculaDocument.Pago toDto(MatriculaDto.Pago pago) {
		return modelMapper.map(pago, MatriculaDocument.Pago.class);
	}

}
