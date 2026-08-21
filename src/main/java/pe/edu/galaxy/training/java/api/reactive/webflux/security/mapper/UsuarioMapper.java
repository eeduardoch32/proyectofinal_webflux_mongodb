package pe.edu.galaxy.training.java.api.reactive.webflux.security.mapper;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.document.UsuarioDocument;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.TokenDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.UsuarioDto;

public interface UsuarioMapper {

	UsuarioDto toDto(UsuarioDocument usuarioDocument);
	
	TokenDto toDto(String token);
}
