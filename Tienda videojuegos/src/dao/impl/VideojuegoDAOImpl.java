package dao.impl;

import dao.ConexionBD;
import dao.VideojuegoDAO;
import models.Videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de {@link VideojuegoDAO} sobre la tabla
 * {@code videojuego}.
 *
 * @author agus_
 */
public class VideojuegoDAOImpl implements VideojuegoDAO {

    private static final String COLUMNAS =
            "id, titulo, genero, plataforma, precio, disponible";

    /**
     * Guarda un videojuego nuevo. Siempre se registra como disponible.
     */
    @Override
    public void agregar(Videojuego videojuego) throws SQLException {

        String sql = "INSERT INTO videojuego "
                + "(titulo, genero, plataforma, precio, disponible) "
                + "VALUES (?, ?, ?, ?, TRUE)";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, videojuego.getTitulo());
            stmt.setString(2, videojuego.getGenero());
            stmt.setString(3, videojuego.getPlataforma());
            stmt.setDouble(4, videojuego.getPrecio());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    videojuego.setId(rs.getInt(1));
                }
            }

            videojuego.setDisponible(true);
        }
    }

    @Override
    public Videojuego buscarPorId(int id) throws SQLException {

        String sql = "SELECT " + COLUMNAS + " FROM videojuego WHERE id = ?";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearVideojuego(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Videojuego> listarTodos() throws SQLException {

        String sql = "SELECT " + COLUMNAS + " FROM videojuego";

        return listar(sql);
    }

    @Override
    public List<Videojuego> listarDisponibles() throws SQLException {

        String sql = "SELECT " + COLUMNAS
                + " FROM videojuego WHERE disponible = TRUE";

        return listar(sql);
    }

    /**
     * Modifica los datos del videojuego. La disponibilidad no se toca acá:
     * solo cambia al registrar o finalizar un alquiler.
     */
    @Override
    public void modificar(Videojuego videojuego) throws SQLException {

        String sql = "UPDATE videojuego SET "
                + "titulo = ?, "
                + "genero = ?, "
                + "plataforma = ?, "
                + "precio = ? "
                + "WHERE id = ?";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, videojuego.getTitulo());
            stmt.setString(2, videojuego.getGenero());
            stmt.setString(3, videojuego.getPlataforma());
            stmt.setDouble(4, videojuego.getPrecio());
            stmt.setInt(5, videojuego.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM videojuego WHERE id = ?";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    private List<Videojuego> listar(String sql) throws SQLException {

        List<Videojuego> videojuegos = new ArrayList<>();

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                videojuegos.add(mapearVideojuego(rs));
            }
        }

        return videojuegos;
    }

    private Videojuego mapearVideojuego(ResultSet rs) throws SQLException {

        return new Videojuego(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("genero"),
                rs.getString("plataforma"),
                rs.getDouble("precio"),
                rs.getBoolean("disponible")
        );
    }
}
