package pe.edu.galaxy.training.java.api.reactive.webflux.business.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "El codigo de la matricula es requerido")
    private String codigoMatricula;

    private Alumno alumno;

    private List<Curso> cursos;

    private Pago pago;

    private String estado;

    private Instant fechaMatricula;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Alumno {

        private String codigo;
        private String nombres;
        private String apellidos;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Curso {

        private String codigo;
        private String nombre;
        private Integer creditos;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pago {
        private String numeroOperacion;
        private Double monto;
        private String voucher;

    }
}