package mx.unam.aragon.ico.pooker.persistence;

import mx.unam.aragon.ico.pooker.model.UsuarioCasino;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de guardar y cargar
 * los usuarios registrados del sistema
 * POOKER Casino.
 *
 * Utiliza persistencia mediante archivo CSV.
 */
public class ArchivoManager {

    // Nombre del archivo donde se guardarán usuarios
    private static final String ARCHIVO = "usuarios.csv";

    /**
     * Metodo para guardar usuarios
     * dentro de un archivo CSV.
     *
     * @param usuarios Lista de usuarios
     */
    public static void guardarUsuarios(List<UsuarioCasino> usuarios) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (UsuarioCasino usuario : usuarios) {

                writer.write(
                        usuario.getId() + "," +
                                usuario.getNombre() + "," +
                                usuario.getApellido() + "," +
                                usuario.getEdad() + "," +
                                usuario.getJuegoFavorito() + "," +
                                usuario.getExperiencia() + "," +
                                usuario.getHorario() + "," +
                                usuario.getServicios() + "," +
                                usuario.getNotas()
                );

                writer.newLine();
            }

            System.out.println("Usuarios guardados correctamente.");

        } catch (IOException e) {

            System.out.println("Error al guardar archivo.");
            e.printStackTrace();
        }
    }

    /**
     * Metodo encargado de cargar
     * usuarios desde el archivo CSV.
     *
     * @return Lista de usuarios recuperados
     */
    public static List<UsuarioCasino> cargarUsuarios() {

        List<UsuarioCasino> usuarios = new ArrayList<>();

        File archivo = new File(ARCHIVO);

        // Si el archivo no existe, devolver lista vacía
        if (!archivo.exists()) {
            return usuarios;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] datos = linea.split(",");

                String notas = "";
                String servicios = "";
                String experiencia = "";
                String horario = "";

                if (datos.length > 5) {
                    experiencia = datos[5];
                }

                if (datos.length > 6) {
                    horario = datos[6];
                }

                if (datos.length > 7) {
                    servicios = datos[7];
                }

                if (datos.length > 8) {
                    notas = datos[8];
                }

                UsuarioCasino usuario =
                        new UsuarioCasino(
                                Integer.parseInt(datos[0]),
                                datos[1],
                                datos[2],
                                Integer.parseInt(datos[3]),
                                datos[4],
                                experiencia,
                                horario,
                                servicios,
                                notas
                        );

                usuarios.add(usuario);
            }

            System.out.println("Usuarios cargados correctamente.");

        } catch (IOException e) {

            System.out.println("Error al cargar usuarios.");
            e.printStackTrace();
        }

        return usuarios;
    }
}