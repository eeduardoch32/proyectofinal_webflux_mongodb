package pe.edu.galaxy.training.java.api.reactive.webflux.business.api.router;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.api.handler.MatriculaHandlerV3;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static pe.edu.galaxy.training.java.api.reactive.webflux.business.api.constants.APIConstants.API_MATRICULAS_V3;

@Configuration
public class MatriculaRouterV3 {

    @Bean
    @RouterOperations({

            @RouterOperation(
                    path = API_MATRICULAS_V3,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "findAllMatriculasV3",
                            summary = "Listar matrículas",
                            description = "Obtiene todas las matrículas registradas",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Lista de matrículas",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = MatriculaDto.class
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = API_MATRICULAS_V3 + "/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "findMatriculaByIdV3",
                            summary = "Buscar matrícula por ID",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            in = ParameterIn.PATH,
                                            required = true
                                    )
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> matriculaRoutesV3(MatriculaHandlerV3 matriculaHandler) {


        return RouterFunctions
                .route(
                        GET(API_MATRICULAS_V3),
                        matriculaHandler::findAll
                )
                .andRoute(
                        GET(API_MATRICULAS_V3+"/{id}"),
                        matriculaHandler::findById
                );
    }
}
