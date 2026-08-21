package pe.edu.galaxy.training.java.api.reactive.webflux.security.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.security.JwtAuthenticationEntryPoint;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.security.JwtFilter;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.security.SecurityContextRepository;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityFilterConfiguration {


	private final JwtAuthenticationEntryPoint entryPoint;

	private final SecurityContextRepository securityContextRepository;


	public SecurityFilterConfiguration(
			SecurityContextRepository securityContextRepository,
			JwtAuthenticationEntryPoint entryPoint) {

		this.securityContextRepository = securityContextRepository;
		this.entryPoint = entryPoint;
	}



	@Bean
	public SecurityWebFilterChain filterChain(
			ServerHttpSecurity http,
			JwtFilter jwtFilter) {


		return http

				.csrf(csrf -> csrf.disable())


				.authorizeExchange(exchange ->

						exchange
								// ==========================================
								// RUTAS PÚBLICAS
								// ==========================================

								.pathMatchers(
										"/api/v1/auth/**",
										"/api/v1/matriculas/public/**",


										// Swagger UI
										"/swagger-ui.html",
										"/swagger-ui/**",
										"/webjars/**",

										// OpenAPI
										"/v3/api-docs",
										"/v3/api-docs/**",

										// Favicon
										"/favicon.ico",

										// Chrome DevTools
										"/.well-known/**"

								)
								.permitAll()

								// ==========================================
								// TODAS LAS DEMÁS RUTAS REQUIEREN JWT
								// ==========================================




/*
								.pathMatchers(
										"/api/v1/auth/**",
										"/api/v1/matriculas/public/**"
								)
								.permitAll()

 */

								.anyExchange()
								.authenticated()
				)


				.addFilterAt(
						jwtFilter,
						SecurityWebFiltersOrder.AUTHENTICATION
				)


				.securityContextRepository(
						securityContextRepository
				)


				.exceptionHandling(exception ->

						exception
								.authenticationEntryPoint(
										entryPoint
								)
				)


				.httpBasic(h -> h.disable())
				.formLogin(h -> h.disable())
				.logout(l -> l.disable())

				.build();
	}
}