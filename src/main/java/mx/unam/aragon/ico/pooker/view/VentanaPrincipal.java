package mx.unam.aragon.ico.pooker.view;
import mx.unam.aragon.ico.pooker.persistence.ArchivoManager;
import mx.unam.aragon.ico.pooker.model.UsuarioCasino;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal del sistema
 * POOKER Casino.
 *
 * Contiene el formulario de registro,
 * tabla de usuarios y menú principal.
 */
public class VentanaPrincipal extends JFrame {

    // ==========================
    // COMPONENTES DEL FORMULARIO
    // ==========================

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEdad;

    private JComboBox<String> comboJuego;
    private JComboBox<String> comboHorario;

    private JRadioButton rbPrincipiante;
    private JRadioButton rbIntermedio;
    private JRadioButton rbExperto;

    private JCheckBox cbVip;
    private JCheckBox cbBebidas;
    private JCheckBox cbTorneos;

    private JTextArea txtNotas;

    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    // ==========================
    // TABLA
    // ==========================

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    // ==========================
    // USUARIO ACTUAL
    // ==========================

    private UsuarioCasino usuarioActual;
    // ==========================
    // LISTA DE USUARIOS
    // ==========================

    private List<UsuarioCasino> listaUsuarios;

    private int idActual = 1;
    private int filaSeleccionada = -1;

    /**
     * Constructor principal.
     *
     * @param usuario usuario seleccionado
     * desde LoginView
     */
    public VentanaPrincipal(UsuarioCasino usuario) {

        this.usuarioActual = usuario;

        // Cargar usuarios guardados
        listaUsuarios =
                ArchivoManager.cargarUsuarios();

        // Ajustar ID automático
        if (!listaUsuarios.isEmpty()) {

            idActual =
                    listaUsuarios
                            .get(listaUsuarios.size() - 1)
                            .getId() + 1;
        }

        configurarVentana();
        inicializarComponentes();

        // Cargar usuarios en tabla
        cargarTabla();

        setVisible(true);
    }

    /**
     * Configuración general
     * de la ventana.
     */
    private void configurarVentana() {

        setTitle("POOKER Casino");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Método encargado
     * de crear toda la interfaz.
     */
    private void inicializarComponentes() {

        // ==========================
        // PANEL PRINCIPAL
        // ==========================

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(25, 25, 25));

        // ==========================
        // TÍTULO
        // ==========================

        JLabel lblTitulo = new JLabel(
                "♠ POOKER Casino ♠",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        lblTitulo.setForeground(Color.YELLOW);

        panelPrincipal.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        // ==========================
        // PANEL CENTRAL
        // ==========================

        JPanel panelCentro = new JPanel(
                new GridLayout(1,2)
        );

        panelCentro.setBackground(
                new Color(25,25,25)
        );

        // ==========================
        // PANEL FORMULARIO
        // ==========================

        JPanel panelFormulario = new JPanel();

        panelFormulario.setLayout(
                new GridLayout(15,2,10,10)
        );

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Registro de Usuario"
                )
        );

        // Campos de texto
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtEdad = new JTextField();

        // ComboBox
        comboJuego = new JComboBox<>(
                new String[]{
                        "Poker",
                        "Blackjack",
                        "Ruleta",
                        "Slots",
                        "Baccarat"
                }
        );

        comboHorario = new JComboBox<>(
                new String[]{
                        "Mañana",
                        "Tarde",
                        "Noche"
                }
        );

        // Radio Buttons
        rbPrincipiante =
                new JRadioButton("Principiante");

        rbIntermedio =
                new JRadioButton("Intermedio");

        rbExperto =
                new JRadioButton("Experto");

        ButtonGroup grupoExperiencia =
                new ButtonGroup();

        grupoExperiencia.add(rbPrincipiante);
        grupoExperiencia.add(rbIntermedio);
        grupoExperiencia.add(rbExperto);

        // Checkboxes
        cbVip =
                new JCheckBox("Sala VIP");

        cbBebidas =
                new JCheckBox("Bebidas");

        cbTorneos =
                new JCheckBox("Torneos");

        // Área de notas
        txtNotas = new JTextArea(4,20);

        JScrollPane scrollNotas =
                new JScrollPane(txtNotas);

        // Botones
        btnRegistrar =
                new JButton("Registrar");

        btnModificar =
                new JButton("Modificar");

        btnEliminar =
                new JButton("Eliminar");

        btnLimpiar =
                new JButton("Limpiar");

        btnModificar.addActionListener(
                e -> modificarUsuario()
        );

        btnEliminar.addActionListener(
                e -> eliminarUsuario()
        );


        // ==========================
        // EVENTOS BOTONES
        // ==========================

        btnRegistrar.addActionListener(
                e -> registrarUsuario()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        // Agregar componentes
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Edad:"));
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Juego Favorito:"));
        panelFormulario.add(comboJuego);

        panelFormulario.add(new JLabel("Horario:"));
        panelFormulario.add(comboHorario);

        panelFormulario.add(new JLabel("Experiencia:"));
        panelFormulario.add(rbPrincipiante);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(rbIntermedio);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(rbExperto);

        panelFormulario.add(new JLabel("Servicios:"));
        panelFormulario.add(cbVip);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(cbBebidas);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(cbTorneos);

        panelFormulario.add(new JLabel("Notas:"));
        panelFormulario.add(scrollNotas);

        panelFormulario.add(btnRegistrar);
        panelFormulario.add(btnModificar);

        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnLimpiar);

        // ==========================
        // TABLA
        // ==========================

        String[] columnas = {
                "ID",
                "Nombre",
                "Apellido",
                "Juego",
                "Horario"
        };

        modeloTabla =
                new DefaultTableModel(
                        columnas,
                        0
                );

        tablaUsuarios =
                new JTable(modeloTabla);

        tablaUsuarios.getSelectionModel()
                .addListSelectionListener(
                        e -> seleccionarUsuario()
                );

        JScrollPane scrollTabla =
                new JScrollPane(tablaUsuarios);

        // ==========================
        // AGREGAR PANELES
        // ==========================

        panelCentro.add(panelFormulario);
        panelCentro.add(scrollTabla);

        panelPrincipal.add(
                panelCentro,
                BorderLayout.CENTER
        );

        // ==========================
        // MENÚ
        // ==========================

        JMenuBar barraMenu =
                new JMenuBar();

        JMenu menuArchivo =
                new JMenu("Archivo");

        JMenuItem itemSalir =
                new JMenuItem("Salir");

        itemSalir.addActionListener(
                e -> System.exit(0)
        );

        menuArchivo.add(itemSalir);

        barraMenu.add(menuArchivo);

        setJMenuBar(barraMenu);

        add(panelPrincipal);
    }

    /**
     * Registra un nuevo usuario.
     */
    private void registrarUsuario() {

        try {

            String nombre =
                    txtNombre.getText();

            String apellido =
                    txtApellido.getText();

            int edad =
                    Integer.parseInt(
                            txtEdad.getText()
                    );

            String juego =
                    comboJuego
                            .getSelectedItem()
                            .toString();

            String horario =
                    comboHorario
                            .getSelectedItem()
                            .toString();

            String experiencia = "";

            if (rbPrincipiante.isSelected()) {
                experiencia = "Principiante";
            }

            if (rbIntermedio.isSelected()) {
                experiencia = "Intermedio";
            }

            if (rbExperto.isSelected()) {
                experiencia = "Experto";
            }

            String servicios = "";

            if (cbVip.isSelected()) {
                servicios += "VIP ";
            }

            if (cbBebidas.isSelected()) {
                servicios += "Bebidas ";
            }

            if (cbTorneos.isSelected()) {
                servicios += "Torneos";
            }

            String notas =
                    txtNotas.getText();

            UsuarioCasino usuario =
                    new UsuarioCasino(
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

            ArchivoManager
                    .guardarUsuarios(
                            listaUsuarios
                    );

            cargarTabla();

            limpiarFormulario();

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario registrado."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Datos inválidos."
            );
        }
    }

    /**
     * Actualiza JTable.
     */
    private void cargarTabla() {

        modeloTabla.setRowCount(0);

        for (UsuarioCasino usuario :
                listaUsuarios) {

            modeloTabla.addRow(
                    new Object[]{
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getApellido(),
                            usuario.getJuegoFavorito(),
                            usuario.getHorario()
                    }
            );
        }
    }

    /**
     * Limpia formulario.
     */
    private void limpiarFormulario() {

        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");

        txtNotas.setText("");

        rbPrincipiante.setSelected(false);
        rbIntermedio.setSelected(false);
        rbExperto.setSelected(false);

        cbVip.setSelected(false);
        cbBebidas.setSelected(false);
        cbTorneos.setSelected(false);

        comboJuego.setSelectedIndex(0);
        comboHorario.setSelectedIndex(0);

        filaSeleccionada = -1;
    }

    /**
     * Carga usuario seleccionado
     * desde JTable al formulario.
     */
    private void seleccionarUsuario() {

        filaSeleccionada =
                tablaUsuarios.getSelectedRow();

        if (filaSeleccionada >= 0) {

            UsuarioCasino usuario =
                    listaUsuarios.get(
                            filaSeleccionada
                    );

            txtNombre.setText(
                    usuario.getNombre()
            );

            txtApellido.setText(
                    usuario.getApellido()
            );

            txtEdad.setText(
                    String.valueOf(
                            usuario.getEdad()
                    )
            );

            comboJuego.setSelectedItem(
                    usuario.getJuegoFavorito()
            );

            comboHorario.setSelectedItem(
                    usuario.getHorario()
            );

            // Experiencia
            rbPrincipiante.setSelected(false);
            rbIntermedio.setSelected(false);
            rbExperto.setSelected(false);

            switch (
                    usuario.getExperiencia()
            ) {

                case "Principiante":
                    rbPrincipiante.setSelected(true);
                    break;

                case "Intermedio":
                    rbIntermedio.setSelected(true);
                    break;

                case "Experto":
                    rbExperto.setSelected(true);
                    break;
            }

            // Servicios
            cbVip.setSelected(false);
            cbBebidas.setSelected(false);
            cbTorneos.setSelected(false);

            String servicios =
                    usuario.getServicios();

            if (servicios.contains("VIP")) {
                cbVip.setSelected(true);
            }

            if (servicios.contains("Bebidas")) {
                cbBebidas.setSelected(true);
            }

            if (servicios.contains("Torneos")) {
                cbTorneos.setSelected(true);
            }

            txtNotas.setText(
                    usuario.getNotas()
            );
        }
    }

    /**
     * Modifica usuario seleccionado.
     */
    private void modificarUsuario() {

        if (filaSeleccionada < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un usuario."
            );

            return;
        }

        try {

            UsuarioCasino usuario =
                    listaUsuarios.get(
                            filaSeleccionada
                    );

            usuario.setNombre(
                    txtNombre.getText()
            );

            usuario.setApellido(
                    txtApellido.getText()
            );

            usuario.setEdad(
                    Integer.parseInt(
                            txtEdad.getText()
                    )
            );

            usuario.setJuegoFavorito(
                    comboJuego.getSelectedItem()
                            .toString()
            );

            usuario.setHorario(
                    comboHorario.getSelectedItem()
                            .toString()
            );

            // Experiencia
            if (rbPrincipiante.isSelected()) {
                usuario.setExperiencia(
                        "Principiante"
                );
            }

            if (rbIntermedio.isSelected()) {
                usuario.setExperiencia(
                        "Intermedio"
                );
            }

            if (rbExperto.isSelected()) {
                usuario.setExperiencia(
                        "Experto"
                );
            }

            String servicios = "";

            if (cbVip.isSelected()) {
                servicios += "VIP ";
            }

            if (cbBebidas.isSelected()) {
                servicios += "Bebidas ";
            }

            if (cbTorneos.isSelected()) {
                servicios += "Torneos";
            }

            usuario.setServicios(
                    servicios
            );

            usuario.setNotas(
                    txtNotas.getText()
            );

            ArchivoManager
                    .guardarUsuarios(
                            listaUsuarios
                    );

            cargarTabla();

            limpiarFormulario();

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario modificado."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al modificar."
            );
        }
    }

    /**
     * Elimina usuario.
     */
    private void eliminarUsuario() {

        if (filaSeleccionada < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un usuario."
            );

            return;
        }

        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Eliminar usuario?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion ==
                JOptionPane.YES_OPTION) {

            listaUsuarios.remove(
                    filaSeleccionada
            );

            ArchivoManager
                    .guardarUsuarios(
                            listaUsuarios
                    );

            cargarTabla();

            limpiarFormulario();

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario eliminado."
            );
        }
    }

}