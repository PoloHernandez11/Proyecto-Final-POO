package controllers;

import dao.ClienteDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Cliente;
import views.ClienteView;

/**
 * Controlador de la pantalla de clientes.
 * <p>
 * Recibe las acciones de {@link ClienteView}, valida los datos y usa
 * {@link ClienteDAO} para guardarlos en la base.
 */
public class ClienteController {

    private final ClienteView vista;
    private final ClienteDAO dao;

    /**
     * Conecta la vista con el DAO y carga la tabla de clientes.
     *
     * @param vista pantalla de clientes
     * @param dao   acceso a datos de clientes
     */
    public ClienteController(ClienteView vista, ClienteDAO dao) {
        this.vista = vista;
        this.dao = dao;

        configurarEventos();
        cargarClientes();
    }

    private void configurarEventos() {

        vista.agregarListenerAgregar(e -> agregarCliente());

        vista.agregarListenerModificar(e -> modificarCliente());

        vista.agregarListenerEliminar(e -> eliminarCliente());

        vista.agregarListenerCancelar(e -> vista.limpiarCampos());

        vista.agregarListenerTabla(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarClienteSeleccionado();
            }
        });
    }

    private void agregarCliente() {

        if (!validarCampos()) {
            return;
        }

        Cliente cliente = new Cliente(
                vista.getNombreCliente(),
                vista.getApellidoCliente(),
                vista.getDniCliente(),
                vista.getTelefonoCliente(),
                vista.getEmailCliente()
        );

        try {

            dao.agregar(cliente);

            vista.mostrarMensaje("Cliente agregado correctamente.");

            cargarClientes();

            vista.limpiarCampos();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al agregar cliente: " + e.getMessage()
            );
        }
    }

    private void modificarCliente() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            vista.mostrarMensaje("Debe seleccionar un cliente.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Cliente cliente = new Cliente(
                id,
                vista.getNombreCliente(),
                vista.getApellidoCliente(),
                vista.getDniCliente(),
                vista.getTelefonoCliente(),
                vista.getEmailCliente()
        );

        try {

            dao.modificar(cliente);

            vista.mostrarMensaje("Cliente modificado correctamente.");

            cargarClientes();

            vista.limpiarCampos();

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al modificar cliente: " + e.getMessage()
            );
        }
    }

    private void eliminarCliente() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            vista.mostrarMensaje("Debe seleccionar un cliente.");
            return;
        }

        if (!vista.confirmarEliminacion()) {
            return;
        }

        try {

            dao.eliminar(id);

            vista.mostrarMensaje("Cliente eliminado correctamente.");

            cargarClientes();

            vista.limpiarCampos();

        } catch (SQLIntegrityConstraintViolationException e) {

            // La base no deja borrar un cliente que tiene alquileres.
            vista.mostrarMensaje(
                    "No se puede eliminar el cliente porque tiene "
                    + "alquileres registrados."
            );

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al eliminar cliente: " + e.getMessage()
            );
        }
    }

    private void cargarClientes() {

        try {

            List<Cliente> clientes = dao.listarTodos();

            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[]{
                        "ID",
                        "Nombre",
                        "Apellido",
                        "DNI",
                        "Teléfono",
                        "Email"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Cliente cliente : clientes) {

                modelo.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getDni(),
                    cliente.getTelefono(),
                    cliente.getEmail()
                });
            }

            vista.setModeloTabla(modelo);

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al cargar clientes: " + e.getMessage()
            );
        }
    }

    private void cargarClienteSeleccionado() {

        int id = vista.getIdSeleccionado();

        if (id == -1) {
            return;
        }

        try {

            Cliente cliente = dao.buscarPorId(id);

            if (cliente != null) {

                vista.setNombreCliente(cliente.getNombre());
                vista.setApellidoCliente(cliente.getApellido());
                vista.setDniCliente(cliente.getDni());
                vista.setTelefonoCliente(cliente.getTelefono());
                vista.setEmailCliente(cliente.getEmail());
            }

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al buscar cliente: " + e.getMessage()
            );
        }
    }

    private boolean validarCampos() {

        if (vista.getNombreCliente().isEmpty()) {
            vista.mostrarMensaje("Debe ingresar un nombre.");
            return false;
        }

        if (vista.getApellidoCliente().isEmpty()) {
            vista.mostrarMensaje("Debe ingresar un apellido.");
            return false;
        }

        if (vista.getDniCliente().isEmpty()) {
            vista.mostrarMensaje("Debe ingresar un DNI.");
            return false;
        }

        return true;
    }
}