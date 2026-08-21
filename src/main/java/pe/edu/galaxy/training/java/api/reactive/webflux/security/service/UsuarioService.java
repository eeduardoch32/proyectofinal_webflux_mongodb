package pe.edu.galaxy.training.java.api.reactive.webflux.security.service;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.LoginRequestDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.TokenDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.UsuarioDto;
import reactor.core.publisher.Mono;

public interface UsuarioService {

	 Mono<UsuarioDto> findByUsuario(String usuario);
	 
	 Mono<TokenDto> login(LoginRequestDto loginRequestDto);

}
