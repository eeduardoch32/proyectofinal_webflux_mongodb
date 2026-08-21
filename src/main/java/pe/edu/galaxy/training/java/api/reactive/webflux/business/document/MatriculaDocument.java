package pe.edu.galaxy.training.java.api.reactive.webflux.business.document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "matriculas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDocument implements Serializable{

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String codigoMatricula;

    private Alumno alumno;

    private List<Curso> cursos;

    private Pago pago;

    private String estado;

    private Instant fechaMatricula;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Alumno {

        private String codigo;
        private String nombres;
        private String apellidos;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Curso {

        private String codigo;
        private String nombre;
        private Integer creditos;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pago {

        private String numeroOperacion;
        private Double monto;
        private String voucher;
    }
}