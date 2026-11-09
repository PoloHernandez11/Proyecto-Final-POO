package dao.impl;

import dao.ClienteDAO;
import dao.ConexionBD;
import models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de {@link ClienteDAO} sobre la tabla {@code cliente}.
 */
public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void agregar(Cliente cliente) throws SQLException {

        String sql = "INSERT INTO cliente "
                + "(nombre, apellido, dni, telefono, email) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDni());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getEmail());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws SQLException {

        String sql = """
            SELECT id, nombre, apellido, dni, telefono, email
            FROM cliente
            WHERE id = ?
            """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Cliente> listarTodos() throws SQLException {

        List<Cliente> clientes = new ArrayList<>();

        String sql = """
            SELECT id, nombre, apellido, dni, telefono, email
            FROM cliente
            """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        }

        return clientes;
    }

    @Override
    public void modificar(Cliente cliente) throws SQLException {

        String sql = "UPDATE cliente SET "
                + "nombre = ?, "
                + "apellido = ?, "
                + "dni = ?, "
                + "telefono = ?, "
                + "email = ? "
                + "WHERE id = ?";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDni());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getEmail());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Cliente buscarPorDni(String dni) throws SQLException {

        String sql = """
            SELECT id, nombre, apellido, dni, telefono, email
            FROM cliente
            WHERE dni = ?
            """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }

        return null;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {

        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("telefono"),
                rs.getString("email")
        );
    }
}
