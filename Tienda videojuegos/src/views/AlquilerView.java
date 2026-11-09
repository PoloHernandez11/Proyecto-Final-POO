package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import models.Cliente;
import models.Videojuego;

/**
 * Pantalla de alquileres: registrar un alquiler, ver los alquileres
 * (activos y finalizados) y finalizar un alquiler cuando se devuelve
 * el videojuego.
 * <p>
 * Solo muestra y lee datos: la lógica está en
 * {@link controllers.AlquilerController}.
 */
public class AlquilerView extends JFrame {

    private final JComboBox<Cliente> comboBoxCliente = new JComboBox<>();
    private final JComboBox<Videojuego> comboBoxVideojuego = new JComboBox<>();
    private final JTextField textFieldFechaAlquiler = new JTextField();
    private final JTextField textFieldFechaDevolucion = new JTextField();

    private final JButton buttonRegistrar = new JButton("REGISTRAR");
    private final JButton buttonFinalizar = new JButton("FINALIZAR");
    private final JButton buttonLimpiar = new JButton("LIMPIAR");

    private final JComboBox<String> comboBoxFiltro = new JComboBox<>(
            new String[]{"Todos", "Activos", "Finalizados"});

    private final JTable tablaAlquileres = new JTable();

    /**
     * Crea y arma la pantalla de alquileres.
     */
    public AlquilerView() {
        setTitle("Gestion de Alquileres");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);

        setSize(820, 560);
    }

    private JPanel crearPanelSuperior() {

        JLabel labelTitulo = new JLabel("GESTION DE ALQUILERES",
                SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 18));

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));
        panelFormulario.add(new JLabel("Cliente:"));
        panelFormulario.add(comboBoxCliente);
        panelFormulario.add(new JLabel("Videojuego (solo disponibles):"));
        panelFormulario.add(comboBoxVideojuego);
        panelFormulario.add(new JLabel("Fecha de alquiler (dd/mm/aaaa):"));
        panelFormulario.add(textFieldFechaAlquiler);
        panelFormulario.add(new JLabel("Devolución prevista (dd/mm/aaaa):"));
        panelFormulario.add(textFieldFechaDevolucion);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(buttonRegistrar);
        panelBotones.add(buttonFinalizar);
        panelBotones.add(buttonLimpiar);
        panelBotones.add(new JLabel("     Mostrar:"));
        panelBotones.add(comboBoxFiltro);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panel.add(labelTitulo, BorderLayout.NORTH);
        panel.add(panelFormulario, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane crearPanelTabla() {

        tablaAlquileres.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaAlquileres);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 10, 10),
                BorderFactory.createTitledBorder(
                        "Alquileres (seleccione uno para finalizarlo)")));

        return scroll;
    }

    /** @param clientes clientes que se ofrecen en el combo */
    public void setClientes(List<Cliente> clientes) {
        comboBoxCliente.setModel(
                new DefaultComboBoxModel<>(clientes.toArray(new Cliente[0])));
    }

    /** @param videojuegos videojuegos disponibles que se ofrecen en el combo */
    public void setVideojuegos(List<Videojuego> videojuegos) {
        comboBoxVideojuego.setModel(new DefaultComboBoxModel<>(
                videojuegos.toArray(new Videojuego[0])));
    }

    /** @return cliente elegido, o {@code null} si no hay ninguno */
    public Cliente getClienteSeleccionado() {
        return (Cliente) comboBoxCliente.getSelectedItem();
    }

    /** @return videojuego elegido, o {@code null} si no hay ninguno */
    public Videojuego getVideojuegoSeleccionado() {
        return (Videojuego) comboBoxVideojuego.getSelectedItem();
    }

    /** @return fecha de alquiler tal como la escribió el usuario */
    public String getFechaAlquilerTexto() {
        return textFieldFechaAlquiler.getText().trim();
    }

    /** @param fecha fecha de alquiler a mostrar */
    public void setFechaAlquilerTexto(String fecha) {
        textFieldFechaAlquiler.setText(fecha);
    }

    /** @return fecha prevista de devolución tal como la escribió el usuario */
    public String getFechaDevolucionTexto() {
        return textFieldFechaDevolucion.getText().trim();
    }

    /** @param fecha fecha prevista de devolución a mostrar */
    public void setFechaDevolucionTexto(String fecha) {
        textFieldFechaDevolucion.setText(fecha);
    }

    /** @return filtro elegido: "Todos", "Activos" o "Finalizados" */
    public String getFiltro() {
        return (String) comboBoxFiltro.getSelectedItem();
    }

    /**
     * @return id del alquiler seleccionado en la tabla, o -1 si no hay
     *         ninguno seleccionado
     */
    public int getIdSeleccionado() {

        int fila = tablaAlquileres.getSelectedRow();

        if (fila == -1) {
            return -1;
        }

        return Integer.parseInt(
                tablaAlquileres.getValueAt(fila, 0).toString());
    }

    /** @param listener acción del botón REGISTRAR */
    public void agregarListenerRegistrar(ActionListener listener) {
        buttonRegistrar.addActionListener(listener);
    }

    /** @param listener acción del botón FINALIZAR */
    public void agregarListenerFinalizar(ActionListener listener) {
        buttonFinalizar.addActionListener(listener);
    }

    /** @param listener acción del botón LIMPIAR */
    public void agregarListenerLimpiar(ActionListener listener) {
        buttonLimpiar.addActionListener(listener);
    }

    /** @param listener acción al cambiar el filtro de la tabla */
    public void agregarListenerFiltro(ActionListener listener) {
        comboBoxFiltro.addActionListener(listener);
    }

    /** @param modelo datos a mostrar en la tabla */
    public void setModeloTabla(DefaultTableModel modelo) {
        tablaAlquileres.setModel(modelo);
    }

    /** Quita la selección de la tabla. */
    public void limpiarSeleccion() {
        tablaAlquileres.clearSelection();
    }

    /** @param mensaje texto a mostrar en un cuadro de diálogo */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Pide confirmación al usuario.
     *
     * @param mensaje pregunta a mostrar
     * @return {@code true} si el usuario eligió "Sí"
     */
    public boolean confirmar(String mensaje) {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        return opcion == JOptionPane.YES_OPTION;
    }
}
