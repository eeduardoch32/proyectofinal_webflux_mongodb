package pe.edu.galaxy.training.java.api.reactive.webflux.security.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.document.UsuarioDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.TokenDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.UsuarioDto;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

	public final ModelMapper modelMapper;
	
	public UsuarioMapperImpl() {
		this.modelMapper = new ModelMapper();
	}	
	@Override
	public UsuarioDto toDto(UsuarioDocument usuarioDocument) {
		return modelMapper.map(usuarioDocument, UsuarioDto.class);
	}
	@Override
	public TokenDto toDto(String token) {

		return new TokenDto(token);
	}

}
