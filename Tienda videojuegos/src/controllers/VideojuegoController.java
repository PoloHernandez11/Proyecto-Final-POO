package controllers;

import dao.AlquilerDAO;
import dao.VideojuegoDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Videojuego;
import views.VideojuegoView;

/**
 * Controlador de la pantalla de videojuegos.
 * <p>
 * Recibe las acciones de {@link VideojuegoView}, valida los datos y usa
 * {@link VideojuegoDAO} para guardarlos. Antes de eliminar consulta
 * {@link AlquilerDAO} para no borrar un videojuego que está alquilado.
 */
public class VideojuegoController {

    private final VideojuegoView vista;
    private final VideojuegoDAO dao;
    private final AlquilerDAO alquilerDAO;

    /**
     * Conecta la vista con los DAO y carga la tabla de videojuegos.
     *
     * @param vista       pantalla de videojuegos
     * @param dao         acceso a datos de videojuegos
     * @param alquilerDAO acceso a datos de alquileres (para validar bajas)
     */
    public VideojuegoController(VideojuegoView vista, VideojuegoDAO dao,
                                AlquilerDAO alquilerDAO) {
        this.vista = vista;
        this.dao = dao;
        this.alquilerDAO = alquilerDAO;

        configurarEventos();
        cargarVideojuegos();
    }

    private void configurarEventos() {

        vista.agregarListenerAgregar(e -> agregarVideojuego());

        vista.agregarListenerModificar(e -> modificarVideojuego());

        vista.agregarListenerEliminar(e -> eliminarVideojuego());

        vista.agregarListenerCancelar(e -> vista.limpiarCampos());

        vista.agregarListenerTabla(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarVideojuegoSeleccionado();
            }
        });
    }

    private void agregarVideojuego() {

        Double precio = validarCampos();

        if (precio == null) {
            return;
        }

        Videojuego videojuego = new Videojuego(
                vista.getTitulo(),
                vista.getGenero(),
                vista.getPlataforma(),
                precio
        );

        try {

            dao.agregar(videojuego);

            vista.mostrarMensaje("Videojuego agregado correctamente.");

            cargarVideojuegos();

            vista.limpiarCampos();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al agregar videojuego: " + e.getMessage()
            );
        }
    }

    private void modificarVideojuego() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            vista.mostrarMensaje("Debe seleccionar un videojuego.");
            return;
        }

        Double precio = validarCampos();

        if (precio == null) {
            return;
        }

        Videojuego videojuego = new Videojuego(
                vista.getTitulo(),
                vista.getGenero(),
                vista.getPlataforma(),
                precio
        );
        videojuego.setId(id);

        try {

            dao.modificar(videojuego);

            vista.mostrarMensaje("Videojuego modificado correctamente.");

            cargarVideojuegos();

            vista.limpiarCampos();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al modificar videojuego: " + e.getMessage()
            );
        }
    }

    private void eliminarVideojuego() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            vista.mostrarMensaje("Debe seleccionar un videojuego.");
            return;
        }

        try {

            // Regla: no se puede eliminar un videojuego con alquiler activo.
            if (alquilerDAO.tieneAlquilerActivo(id)) {
                vista.mostrarMensaje(
                        "No se puede eliminar: el videojuego tiene un "
                        + "alquiler activo.\nPrimero finalice el alquiler."
                );
                return;
            }

            if (!vista.confirmarEliminacion()) {
                return;
            }

            dao.eliminar(id);

            vista.mostrarMensaje("Videojuego eliminado correctamente.");

            cargarVideojuegos();

            vista.limpiarCampos();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al eliminar videojuego: " + e.getMessage()
            );
        }
    }

    private void cargarVideojuegos() {

        try {

            List<Videojuego> videojuegos = dao.listarTodos();

            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[]{
                        "ID",
                        "Título",
                        "Género",
                        "Plataforma",
                        "Precio",
                        "Disponible"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Videojuego videojuego : videojuegos) {

                modelo.addRow(new Object[]{
                    videojuego.getId(),
                    videojuego.getTitulo(),
                    videojuego.getGenero(),
                    videojuego.getPlataforma(),
                    videojuego.getPrecio(),
                    videojuego.isDisponible() ? "SI" : "NO"
                });
            }

            vista.setModeloTabla(modelo);

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al cargar videojuegos: " + e.getMessage()
            );
        }
    }

    private void cargarVideojuegoSeleccionado() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            return;
        }

        try {

            Videojuego videojuego = dao.buscarPorId(id);

            if (videojuego != null) {

                vista.setTitulo(videojuego.getTitulo());
                vista.setGenero(videojuego.getGenero());
                vista.setPrecioTexto(String.valueOf(videojuego.getPrecio()));
                vista.setPlataforma(videojuego.getPlataforma());
                vista.setDisponible(videojuego.isDisponible());
            }

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al buscar videojuego: " + e.getMessage()
            );
        }
    }

    /**
     * Valida los campos del formulario.
     *
     * @return el precio ingresado si todo es válido, o {@code null} si hay
     *         algún error (ya se le mostró el mensaje al usuario)
     */
    private Double validarCampos() {

        if (vista.getTitulo().isEmpty()) {
            vista.mostrarMensaje("Debe ingresar un título.");
            return null;
        }

        if (vista.getGenero().isEmpty()) {
            vista.mostrarMensaje("Debe ingresar un género.");
            return null;
        }

        try {

            // Se acepta coma o punto como separador decimal.
            double precio = Double.parseDouble(
                    vista.getPrecioTexto().replace(',', '.'));

            if (precio < 0) {
                vista.mostrarMensaje("El precio no puede ser negativo.");
                return null;
            }

            return precio;

        } catch (NumberFormatException e) {

            vista.mostrarMensaje("Debe ingresar un precio numérico válido.");
            return null;
        }
    }
}
