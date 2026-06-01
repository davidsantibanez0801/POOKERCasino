package mx.unam.aragon.ico.pooker.view;

import mx.unam.aragon.ico.pooker.model.UsuarioCasino;
import mx.unam.aragon.ico.pooker.persistence.ArchivoManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana inicial del sistema POOKER Casino.
 *
 * Permite seleccionar un usuario ya registrado
 * o ingresar al sistema para registrarse.
 */
public class LoginView extends JFrame {

    // Componentes
    private JComboBox<String> comboUsuarios;
    private JButton btnEntrar;
    private JButton btnRegistrarse;

    // Lista de usuarios cargados
    private List<UsuarioCasino> usuarios;

    /**
     * Constructor principal.
     */
    public LoginView() {

        setTitle("POOKER Casino");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();

        setVisible(true);
    }

    /**
     * Metodo encargado de construir
     * la interfaz gráfica.
     */
    private void inicializarComponentes() {

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel(
                "♠ POOKER Casino ♠",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(titulo, BorderLayout.NORTH);

        // Panel central
        JPanel centro = new JPanel();
        centro.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblUsuarios =
                new JLabel("Selecciona un usuario:");

        comboUsuarios = new JComboBox<>();

        // Cargar usuarios guardados
        usuarios = ArchivoManager.cargarUsuarios();

        for (UsuarioCasino usuario : usuarios) {

            comboUsuarios.addItem(
                    usuario.getNombre() +
                            " " +
                            usuario.getApellido()
            );
        }

        btnEntrar = new JButton("Entrar");
        btnRegistrarse = new JButton("Registrarse");

        centro.add(lblUsuarios);
        centro.add(comboUsuarios);
        centro.add(btnEntrar);
        centro.add(new JLabel("¿Nuevo usuario?"));
        centro.add(btnRegistrarse);

        panel.add(centro, BorderLayout.CENTER);

        add(panel);

        // ==========================
        // EVENTOS
        // ==========================

        // Entrar con usuario existente
        btnEntrar.addActionListener(e -> {

            int index =
                    comboUsuarios.getSelectedIndex();

            if (index >= 0) {

                UsuarioCasino usuario =
                        usuarios.get(index);

                JOptionPane.showMessageDialog(
                        null,
                        "Bienvenido "
                                + usuario.getNombre()
                );

                new VentanaPrincipal(usuario);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "No hay usuarios registrados."
                );
            }
        });

        // Registrarse
        btnRegistrarse.addActionListener(e -> {

            new VentanaPrincipal(null);

            dispose();
        });
    }
}