package pe.edu.galaxy.training.java.api.reactive.webflux.business.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatriculaWebClientTest {

    @Autowired
    private MatriculaWebClient matriculaWebClient;

    @Test
    void consultarMatriculas() {

        matriculaWebClient
                .findAll()
                .doOnNext(System.out::println)
                .blockLast();
    }
}