package pe.edu.galaxy.training.java.api.reactive.webflux.security.api.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.LoginRequestDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.ResponseDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.service.UsuarioService;

import reactor.core.publisher.Mono;


@Component
public class AuthHandler {


    private final UsuarioService usuarioService;


    public AuthHandler(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    public Mono<ServerResponse> logIn(ServerRequest request) {


        return request.bodyToMono(LoginRequestDto.class)

                .flatMap(dto ->

                        usuarioService.login(dto)

                                .flatMap(token ->

                                        ServerResponse.ok()
                                                .bodyValue(
                                                        new ResponseDto<>(
                                                                200,
                                                                "Login correcto",
                                                                token
                                                        )
                                                )
                                )
                )


                .onErrorResume(e ->

                        ServerResponse.status(401)

                                .bodyValue(

                                        new ResponseDto<>(
                                                401,
                                                "Credenciales incorrectas",
                                                null
                                        )
                                )
                );
    }
}