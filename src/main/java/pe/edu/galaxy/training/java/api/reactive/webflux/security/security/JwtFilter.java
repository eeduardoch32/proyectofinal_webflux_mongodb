package pe.edu.galaxy.training.java.api.reactive.webflux.security.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Component
public class JwtFilter implements WebFilter {


	private final JwtAuthenticationManager jwtAuthenticationManager;


	public JwtFilter(
			JwtAuthenticationManager jwtAuthenticationManager) {

		this.jwtAuthenticationManager = jwtAuthenticationManager;
	}



	@Override
	public Mono<Void> filter(
			ServerWebExchange exchange,
			WebFilterChain chain) {


		String path =
				exchange.getRequest()
						.getPath()
						.value();


		System.out.println("JWT FILTER -> " + path);

		// ==========================================
		// RUTAS QUE NO REQUIEREN JWT
		// ==========================================

		if (path.startsWith("/api/v1/auth/")
				|| path.equals("/api/v1/auth")) {
			return chain.filter(exchange);
		}
/*
		if (path.startsWith("/api/v1/matriculas/public/")) {
			return chain.filter(exchange);
		}

		if (path.startsWith("/api/v2/matriculas/public/")) {
			return chain.filter(exchange);
		}

 */

		// Swagger UI
		if (path.equals("/swagger-ui.html")
				|| path.startsWith("/swagger-ui/")
				|| path.startsWith("/webjars/")) {

			return chain.filter(exchange);
		}

		// OpenAPI / Swagger configuration
		if (path.equals("/v3/api-docs")
				|| path.startsWith("/v3/api-docs/")) {
			return chain.filter(exchange);
		}

		// Favicon
		if (path.equals("/favicon.ico")) {
			return chain.filter(exchange);
		}

		// Chrome DevTools
		if (path.startsWith("/.well-known/")) {
			return chain.filter(exchange);
		}


		String auth = exchange.getRequest()
						.getHeaders()
						.getFirst(HttpHeaders.AUTHORIZATION);


		if(auth == null) {
			return Mono.error(new RuntimeException("Token requerido"));
		}


		if(!auth.startsWith("Bearer ")) {
			return Mono.error(new RuntimeException("Bearer inválido"));
		}



		String token = auth.substring(7);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
						null,
						token
				);



		return jwtAuthenticationManager
				.authenticate(authentication)
				.flatMap(authenticated ->
						chain.filter(exchange)
								.contextWrite(
										ReactiveSecurityContextHolder
												.withAuthentication(authenticated)
								)
				);
	}
}