package pe.edu.galaxy.training.java.api.reactive.webflux.security.service;


import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.galaxy.training.java.api.reactive.webflux.commons.exceptions.CustomNotContentException;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.repository.UsuarioRepository;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

	private final PasswordEncoder passwordEncoder;
	
	private final UsuarioRepository usuarioRepository;

	public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return usuarioRepository.findByUsuario(username).flatMap(usuarioDocument -> {
			UserDetails userDetails = User.withUsername(usuarioDocument.getUsuario())
					.password(passwordEncoder.encode(usuarioDocument.getClave()))
					.roles().authorities(usuarioDocument.getRoles()).build();
			
			Mono<UserDetails> just = Mono.just(userDetails);
			
			return just;
			
		}).switchIfEmpty(Mono.error(new CustomNotContentException("El usuario no se encuentra registrado")));
	}
}