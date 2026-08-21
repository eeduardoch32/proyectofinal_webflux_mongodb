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
import pe.edu.galaxy.training.java.api.reactive.webflux.business.api.handler.MatriculaHandlerV2;
import pe.edu.galaxy.training.java.api.reactive.webflux.business.dto.MatriculaDto;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static pe.edu.galaxy.training.java.api.reactive.webflux.business.api.constants.APIConstants.API_MATRICULAS_V2;


@Configuration
public class MatriculaRouterV2 {



    @Bean
    @RouterOperations({

            @RouterOperation(
                    path = API_MATRICULAS_V2,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "findAllMatriculas",
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
                    path = API_MATRICULAS_V2 + "/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "findMatriculaById",
                            summary = "Buscar matrícula por ID",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            in = ParameterIn.PATH,
                                            required = true
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = API_MATRICULAS_V2 + "/codigo/{codigoMatricula}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "findMatriculaByCodigo",
                            summary = "Buscar matrícula por código",
                            parameters = {
                                    @Parameter(
                                            name = "codigoMatricula",
                                            in = ParameterIn.PATH,
                                            required = true
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = API_MATRICULAS_V2,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createMatricula",
                            summary = "Registrar matrícula"
                    )
            ),

            @RouterOperation(
                    path = API_MATRICULAS_V2 + "/{id}",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "updateMatricula",
                            summary = "Actualizar matrícula",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            in = ParameterIn.PATH,
                                            required = true
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = API_MATRICULAS_V2 + "/{id}",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "deleteMatricula",
                            summary = "Eliminar matrícula",
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

    public RouterFunction<ServerResponse> matriculaRoutesV2(MatriculaHandlerV2 matriculaHandler) {

        return RouterFunctions

                .route(GET(API_MATRICULAS_V2),matriculaHandler::findAll)

                .andRoute(GET(API_MATRICULAS_V2+"/{id}"), matriculaHandler::findById)

                .andRoute(GET(API_MATRICULAS_V2+"/codigo/{codigoMatricula}"),
                        matriculaHandler::findByCodigoMatricula)


                .andRoute(POST(API_MATRICULAS_V2), matriculaHandler::add)

                .andRoute(PUT(API_MATRICULAS_V2+"/{id}"), matriculaHandler::update)

                .andRoute(DELETE(API_MATRICULAS_V2+"/{id}"), matriculaHandler::delete);
    }


}