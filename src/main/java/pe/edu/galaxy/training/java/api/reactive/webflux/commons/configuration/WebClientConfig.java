package pe.edu.galaxy.training.java.api.reactive.webflux.commons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient matriculaClient(
            WebClient.Builder builder) {

        return builder
                .baseUrl("http://localhost:8082/api/v2/matriculas")
                .build();
    }
}