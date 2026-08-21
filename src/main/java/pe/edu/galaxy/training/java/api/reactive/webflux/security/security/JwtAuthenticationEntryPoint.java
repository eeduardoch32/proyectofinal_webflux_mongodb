package pe.edu.galaxy.training.java.api.reactive.webflux.security.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.ResponseDto;


@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {


    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public Mono<Void> commence(
            ServerWebExchange exchange,
            AuthenticationException ex) {


        ResponseDto<Void> response =
                new ResponseDto<>(
                        401,
                        "Token invalido o no enviado",
                        null
                );


        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);


        exchange.getResponse()
                .getHeaders()
                .add(
                        "Content-Type",
                        "application/json"
                );


        try {

            byte[] bytes =
                    mapper.writeValueAsBytes(response);


            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)
                            )
                    );

        } catch (Exception e) {

            return Mono.error(e);
        }
    }
}