package pe.edu.galaxy.training.java.api.reactive.webflux.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions.CustomNotContentException;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.LoginRequestDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.TokenDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.UsuarioDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.mapper.UsuarioMapper;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.repository.UsuarioRepository;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.security.JwtTokenProvider;
import reactor.core.publisher.Mono;

@Component
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passwordEncoder;
	private final UsuarioRepository usuarioRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public UsuarioServiceImpl(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository,
			JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.usuarioMapper = usuarioMapper;
		this.usuarioRepository = usuarioRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Mono<UsuarioDto> findByUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario).flatMap(e -> {
			return Mono.just(usuarioMapper.toDto(e));
		}).switchIfEmpty(Mono.error(new CustomNotContentException("El usuario no se encuentra registrado")));
	}

	@Override
	public Mono<TokenDto> login(LoginRequestDto loginRequestDto) {
		return usuarioRepository.findByUsuario(loginRequestDto.usuario())
				.filter(usuarioDocument -> passwordEncoder.matches(loginRequestDto.clave(), usuarioDocument.getClave()))
				.map(usuarioDocument -> new TokenDto(jwtTokenProvider.generateToken(usuarioMapper.toDto(usuarioDocument))))
				.switchIfEmpty(Mono.error(new CustomNotContentException("El usuario no se encuentra registrado")));
	}

}
