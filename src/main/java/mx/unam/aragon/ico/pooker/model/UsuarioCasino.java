package mx.unam.aragon.ico.pooker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de usuario del sistema POOKER Casino.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCasino {

    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String juegoFavorito;
    private String experiencia;
    private String horario;
    private String servicios;
    private String notas;
}