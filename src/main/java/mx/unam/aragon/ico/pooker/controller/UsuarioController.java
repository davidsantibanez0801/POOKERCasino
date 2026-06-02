package mx.unam.aragon.ico.pooker.controller;

import mx.unam.aragon.ico.pooker.model.UsuarioCasino;
import mx.unam.aragon.ico.pooker.persistence.ArchivoManager;

import java.util.List;

/**
 * Controlador del sistema POOKER Casino.
 *
 * Coordina la comunicación entre la Vista
 * y el Modelo, gestionando la lógica
 * de negocio de los usuarios.
 */
public class UsuarioController {

    // Lista de usuarios en memoria
    private List<UsuarioCasino> listaUsuarios;

    // ID autoincremental
    private int idActual;

    /**
     * Constructor: carga usuarios desde archivo
     * y calcula el siguiente ID disponible.
     */
    public UsuarioController() {

        listaUsuarios = ArchivoManager.cargarUsuarios();

        if (!listaUsuarios.isEmpty()) {
            idActual = listaUsuarios
                    .get(listaUsuarios.size() - 1)
                    .getId() + 1;
        } else {
            idActual = 1;
        }
    }

    /**
     * Valida y agrega un nuevo usuario.
     *
     * @param nombre       Nombre del usuario
     * @param apellido     Apellido del usuario
     * @param edad         Edad del usuario
     * @param juego        Juego favorito
     * @param experiencia  Nivel de experiencia
     * @param horario      Horario preferido
     * @param servicios    Servicios seleccionados
     * @param notas        Notas adicionales
     * @return Mensaje de resultado
     */
    public String registrarUsuario(
            String nombre,
            String apellido,
            int edad,
            String juego,
            String experiencia,
            String horario,
            String servicios,
            String notas) {

        if (nombre.isBlank() || apellido.isBlank()) {
            return "ERROR:Nombre y apellido son obligatorios.";
        }

        if (edad < 18) {
            return "ERROR:El usuario debe ser mayor de 18 años.";
        }

        if (edad > 120) {
            return "ERROR:Edad no válida.";
        }

        UsuarioCasino usuario = new UsuarioCasino(
                idActual++,
                nombre,
                apellido,
                edad,
                juego,
                experiencia,
                horario,
                servicios,
                notas
        );

        listaUsuarios.add(usuario);
        ArchivoManager.guardarUsuarios(listaUsuarios);

        return "OK:Usuario registrado correctamente.";
    }

    /**
     * Valida y modifica un usuario existente.
     *
     * @param indice       Posición en la lista
     * @param nombre       Nuevo nombre
     * @param apellido     Nuevo apellido
     * @param edad         Nueva edad
     * @param juego        Nuevo juego favorito
     * @param experiencia  Nuevo nivel
     * @param horario      Nuevo horario
     * @param servicios    Nuevos servicios
     * @param notas        Nuevas notas
     * @return Mensaje de resultado
     */
    public String modificarUsuario(
            int indice,
            String nombre,
            String apellido,
            int edad,
            String juego,
            String experiencia,
            String horario,
            String servicios,
            String notas) {

        if (indice < 0 || indice >= listaUsuarios.size()) {
            return "ERROR:Selecciona un usuario válido.";
        }

        if (nombre.isBlank() || apellido.isBlank()) {
            return "ERROR:Nombre y apellido son obligatorios.";
        }

        if (edad < 18) {
            return "ERROR:El usuario debe ser mayor de 18 años.";
        }

        if (edad > 120) {
            return "ERROR:Edad no válida.";
        }

        UsuarioCasino usuario = listaUsuarios.get(indice);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEdad(edad);
        usuario.setJuegoFavorito(juego);
        usuario.setExperiencia(experiencia);
        usuario.setHorario(horario);
        usuario.setServicios(servicios);
        usuario.setNotas(notas);

        ArchivoManager.guardarUsuarios(listaUsuarios);

        return "OK:Usuario modificado correctamente.";
    }

    /**
     * Elimina un usuario de la lista.
     *
     * @param indice Posición en la lista
     * @return Mensaje de resultado
     */
    public String eliminarUsuario(int indice) {

        if (indice < 0 || indice >= listaUsuarios.size()) {
            return "ERROR:Selecciona un usuario válido.";
        }

        listaUsuarios.remove(indice);
        ArchivoManager.guardarUsuarios(listaUsuarios);

        return "OK:Usuario eliminado correctamente.";
    }

    /**
     * Devuelve la lista de usuarios.
     *
     * @return Lista de usuarios
     */
    public List<UsuarioCasino> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Carga usuarios desde una ruta personalizada
     * elegida por el usuario con JFileChooser.
     *
     * @param ruta Ruta del archivo CSV
     */
    public void cargarDesde(String ruta) {

        List<UsuarioCasino> cargados =
                ArchivoManager.cargarUsuariosDesde(ruta);

        listaUsuarios.clear();
        listaUsuarios.addAll(cargados);

        if (!listaUsuarios.isEmpty()) {
            idActual = listaUsuarios
                    .get(listaUsuarios.size() - 1)
                    .getId() + 1;
        } else {
            idActual = 1;
        }
    }

    /**
     * Guarda los usuarios en el archivo.
     * Útil para llamar al cerrar la aplicación.
     */
    public void guardar() {
        ArchivoManager.guardarUsuarios(listaUsuarios);
    }
}