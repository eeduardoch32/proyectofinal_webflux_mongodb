package pe.edu.galaxy.training.java.api.reactive.webflux.security.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.api.handler.AuthHandler;
import static pe.edu.galaxy.training.java.api.reactive.webflux.security.api.constants.APIConstants.API_AUTH;

@Configuration
public class AuthRouter {
    @Bean
    public RouterFunction<ServerResponse> authRoute(AuthHandler authHandler) {
        return RouterFunctions.route()
                //.POST(API_AUTH + "signup", authHandler::signUp)
                .POST(API_AUTH + "/login", authHandler::logIn)
                .build();
    }
}
