package pe.edu.galaxy.training.java.api.reactive.webflux.security.security;


import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationManager(JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(
            Authentication authentication) {

        /*
        System.out.println("ENTRO AUTH MANAGER");
        System.out.println("TOKEN: " + authentication.getCredentials());

        System.out.println("=================");
        System.out.println("TOKEN RECIBIDO:");
        System.out.println(authentication.getCredentials());
        System.out.println("=================");

         */

        return Mono.just(authentication)

                .flatMap(auth -> {

                    System.out.println("VALIDANDO JWT");

                    return Mono.fromCallable(() ->
                            jwtTokenProvider.getClaims(
                                    auth.getCredentials().toString()
                            )
                    );
                })

                .map(claims -> {

                    System.out.println("TOKEN VALIDO");


                    List<Map<String, String>> roles =
                            (List<Map<String, String>>)
                                    claims.get("authorities");


                    var authorities = roles.stream()
                            .map(role -> role.get("authority"))
                            .map(SimpleGrantedAuthority::new)
                            .toList();


                    return (Authentication)
                            new UsernamePasswordAuthenticationToken(
                                    claims.getSubject(),
                                    null,
                                    authorities
                            );
                })

                .doOnError(e ->
                        System.out.println(
                                "ERROR JWT: " + e.getMessage()
                        )
                );
    }
}