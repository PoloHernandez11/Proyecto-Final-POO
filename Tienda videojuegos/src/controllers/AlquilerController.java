package controllers;

import dao.AlquilerDAO;
import dao.ClienteDAO;
import dao.VideojuegoDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Alquiler;
import models.Cliente;
import models.Videojuego;
import views.AlquilerView;

/**
 * Controlador de la pantalla de alquileres.
 * <p>
 * Aplica las reglas de negocio del alquiler:
 * <ul>
 *   <li>Solo se alquila un videojuego disponible.</li>
 *   <li>Al alquilar, el videojuego pasa a no disponible.</li>
 *   <li>Cada alquiler tiene un cliente y un videojuego.</li>
 *   <li>Al finalizar, el videojuego vuelve a estar disponible.</li>
 *   <li>Un alquiler no se puede finalizar dos veces.</li>
 *   <li>No se aceptan fechas inválidas.</li>
 * </ul>
 */
public class AlquilerController {

    /** Formato de fecha que usa la pantalla (por ejemplo 25/11/2026). */
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    /** Días que se sugieren por defecto para devolver el videojuego. */
    private static final int DIAS_ALQUILER_POR_DEFECTO = 7;

    private final AlquilerView vista;
    private final AlquilerDAO alquilerDAO;
    private final ClienteDAO clienteDAO;
    private final VideojuegoDAO videojuegoDAO;

    /**
     * Conecta la vista con los DAO y carga los datos iniciales.
     *
     * @param vista         pantalla de alquileres
     * @param alquilerDAO   acceso a datos de alquileres
     * @param clienteDAO    acceso a datos de clientes (para el combo)
     * @param videojuegoDAO acceso a datos de videojuegos (para el combo)
     */
    public AlquilerController(AlquilerView vista, AlquilerDAO alquilerDAO,
                              ClienteDAO clienteDAO,
                              VideojuegoDAO videojuegoDAO) {
        this.vista = vista;
        this.alquilerDAO = alquilerDAO;
        this.clienteDAO = clienteDAO;
        this.videojuegoDAO = videojuegoDAO;

        configurarEventos();
        limpiarFormulario();
        cargarCombos();
        cargarAlquileres();
    }

    private void configurarEventos() {

        vista.agregarListenerRegistrar(e -> registrarAlquiler());

        vista.agregarListenerFinalizar(e -> finalizarAlquiler());

        vista.agregarListenerLimpiar(e -> limpiarFormulario());

        vista.agregarListenerFiltro(e -> cargarAlquileres());
    }

    private void registrarAlquiler() {

        Cliente cliente = vista.getClienteSeleccionado();

        if (cliente == null) {
            vista.mostrarMensaje(
                    "Debe seleccionar un cliente. Si no hay ninguno, "
                    + "regístrelo primero en Clientes.");
            return;
        }

        Videojuego videojuego = vista.getVideojuegoSeleccionado();

        if (videojuego == null) {
            vista.mostrarMensaje(
                    "Debe seleccionar un videojuego. Si la lista está "
                    + "vacía, no hay videojuegos disponibles.");
            return;
        }

        LocalDate fechaAlquiler = leerFecha(
                vista.getFechaAlquilerTexto(), "alquiler");

        if (fechaAlquiler == null) {
            return;
        }

        LocalDate fechaDevolucion = leerFecha(
                vista.getFechaDevolucionTexto(), "devolución prevista");

        if (fechaDevolucion == null) {
            return;
        }

        // Regla: no se permiten fechas inválidas.
        if (fechaDevolucion.isBefore(fechaAlquiler)) {
            vista.mostrarMensaje(
                    "La fecha de devolución prevista no puede ser anterior "
                    + "a la fecha de alquiler.");
            return;
        }

        try {

            // Regla: solo se alquila un videojuego disponible.
            // Se vuelve a consultar por si cambió desde que se abrió la
            // pantalla.
            Videojuego actual = videojuegoDAO.buscarPorId(videojuego.getId());

            if (actual == null || !actual.isDisponible()) {
                vista.mostrarMensaje(
                        "El videojuego seleccionado no está disponible.");
                cargarCombos();
                return;
            }

            Alquiler alquiler = new Alquiler(
                    cliente, actual, fechaAlquiler, fechaDevolucion);

            // Guarda el alquiler y marca el videojuego como no disponible.
            alquilerDAO.registrar(alquiler);

            vista.mostrarMensaje("Alquiler registrado correctamente.");

            limpiarFormulario();
            cargarCombos();
            cargarAlquileres();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al registrar el alquiler: " + e.getMessage()
            );
        }
    }

    private void finalizarAlquiler() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            vista.mostrarMensaje("Debe seleccionar un alquiler de la tabla.");
            return;
        }

        try {

            Alquiler alquiler = alquilerDAO.buscarPorId(id);

            if (alquiler == null) {
                vista.mostrarMensaje("El alquiler ya no existe.");
                cargarAlquileres();
                return;
            }

            // Regla: no se puede finalizar dos veces el mismo alquiler.
            if (!alquiler.isActivo()) {
                vista.mostrarMensaje(
                        "Este alquiler ya fue finalizado el "
                        + formatear(alquiler.getFechaDevolucionReal()) + ".");
                return;
            }

            boolean confirmado = vista.confirmar(
                    "¿Confirma la devolución de \""
                    + alquiler.getVideojuego().getTitulo()
                    + "\" por parte de "
                    + alquiler.getCliente().getNombre() + " "
                    + alquiler.getCliente().getApellido() + "?");

            if (!confirmado) {
                return;
            }

            // Cierra el alquiler y deja el videojuego disponible otra vez.
            alquilerDAO.finalizar(id, LocalDate.now());

            vista.mostrarMensaje(
                    "Alquiler finalizado. El videojuego vuelve a estar "
                    + "disponible.");

            cargarCombos();
            cargarAlquileres();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al finalizar el alquiler: " + e.getMessage()
            );
        }
    }

    /**
     * Carga los clientes y los videojuegos disponibles en los combos.
     */
    private void cargarCombos() {

        try {

            vista.setClientes(clienteDAO.listarTodos());
            vista.setVideojuegos(videojuegoDAO.listarDisponibles());

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al cargar clientes y videojuegos: "
                    + e.getMessage()
            );
        }
    }

    /**
     * Carga la tabla según el filtro elegido (todos, activos o
     * finalizados).
     */
    private void cargarAlquileres() {

        try {

            List<Alquiler> alquileres = alquilerDAO.listarTodos();

            String filtro = vista.getFiltro();

            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[]{
                        "ID",
                        "Cliente",
                        "Videojuego",
                        "Fecha alquiler",
                        "Devolución prevista",
                        "Devolución real",
                        "Estado"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Alquiler alquiler : alquileres) {

                if ("Activos".equals(filtro) && !alquiler.isActivo()) {
                    continue;
                }

                if ("Finalizados".equals(filtro) && alquiler.isActivo()) {
                    continue;
                }

                modelo.addRow(new Object[]{
                    alquiler.getId(),
                    alquiler.getCliente().getNombre() + " "
                            + alquiler.getCliente().getApellido(),
                    alquiler.getVideojuego().getTitulo() + " ("
                            + alquiler.getVideojuego().getPlataforma() + ")",
                    formatear(alquiler.getFechaAlquiler()),
                    formatear(alquiler.getFechaDevolucionPrevista()),
                    formatear(alquiler.getFechaDevolucionReal()),
                    alquiler.isActivo() ? "ACTIVO" : "FINALIZADO"
                });
            }

            vista.setModeloTabla(modelo);

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al cargar alquileres: " + e.getMessage()
            );
        }
    }

    /**
     * Deja las fechas sugeridas: hoy y dentro de una semana.
     */
    private void limpiarFormulario() {

        LocalDate hoy = LocalDate.now();

        vista.setFechaAlquilerTexto(formatear(hoy));
        vista.setFechaDevolucionTexto(
                formatear(hoy.plusDays(DIAS_ALQUILER_POR_DEFECTO)));

        vista.limpiarSeleccion();
    }

    /**
     * Convierte el texto de la pantalla en una fecha.
     *
     * @param texto       fecha escrita por el usuario
     * @param nombreCampo nombre del campo, para el mensaje de error
     * @return la fecha, o {@code null} si el texto no es una fecha válida
     *         (ya se le mostró el mensaje al usuario)
     */
    private LocalDate leerFecha(String texto, String nombreCampo) {

        try {

            return LocalDate.parse(texto, FORMATO_FECHA);

        } catch (DateTimeParseException e) {

            vista.mostrarMensaje(
                    "La fecha de " + nombreCampo + " no es válida.\n"
                    + "Use el formato dd/mm/aaaa, por ejemplo "
                    + formatear(LocalDate.now()) + ".");
            return null;
        }
    }

    private String formatear(LocalDate fecha) {
        return fecha == null ? "-" : fecha.format(FORMATO_FECHA);
    }
}
