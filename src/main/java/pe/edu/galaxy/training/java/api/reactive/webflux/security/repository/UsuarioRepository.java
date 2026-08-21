package pe.edu.galaxy.training.java.api.reactive.webflux.security.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.document.UsuarioDocument;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<UsuarioDocument, String>{
		
	Mono<UsuarioDocument> findByUsuario(String usuario);
	
	//Mono<UsuarioDocument> validar(String usuario, String clave); // Clasiva

}
