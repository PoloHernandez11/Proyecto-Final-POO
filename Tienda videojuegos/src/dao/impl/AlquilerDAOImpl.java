package dao.impl;

import dao.AlquilerDAO;
import dao.ConexionBD;
import models.Alquiler;
import models.Cliente;
import models.Videojuego;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de {@link AlquilerDAO} sobre la tabla {@code alquiler}.
 * <p>
 * Registrar y finalizar un alquiler cambian dos tablas (alquiler y
 * videojuego). Por eso se hacen dentro de una transacción: o se guardan
 * los dos cambios, o no se guarda ninguno.
 */
public class AlquilerDAOImpl implements AlquilerDAO {

    /** Consulta base: trae el alquiler junto con su cliente y su videojuego. */
    private static final String SELECT_ALQUILER = """
        SELECT a.id, a.fecha_alquiler, a.fecha_devolucion_prevista,
               a.fecha_devolucion_real, a.activo,
               c.id AS cliente_id, c.nombre, c.apellido, c.dni,
               c.telefono, c.email,
               v.id AS videojuego_id, v.titulo, v.genero, v.plataforma,
               v.precio, v.disponible
        FROM alquiler a
        JOIN cliente c ON a.cliente_id = c.id
        JOIN videojuego v ON a.videojuego_id = v.id
        """;

    @Override
    public void registrar(Alquiler alquiler) throws SQLException {

        // Solo se ocupa el videojuego si todavía está disponible.
        String sqlOcuparVideojuego = "UPDATE videojuego "
                + "SET disponible = FALSE "
                + "WHERE id = ? AND disponible = TRUE";

        String sqlInsertarAlquiler = "INSERT INTO alquiler "
                + "(cliente_id, videojuego_id, fecha_alquiler, "
                + "fecha_devolucion_prevista, activo) "
                + "VALUES (?, ?, ?, ?, TRUE)";

        try (Connection conexion = ConexionBD.getConexion()) {

            conexion.setAutoCommit(false);

            try (PreparedStatement ocupar =
                         conexion.prepareStatement(sqlOcuparVideojuego);
                 PreparedStatement insertar = conexion.prepareStatement(
                         sqlInsertarAlquiler,
                         Statement.RETURN_GENERATED_KEYS)) {

                ocupar.setInt(1, alquiler.getVideojuego().getId());

                if (ocupar.executeUpdate() == 0) {
                    throw new SQLException(
                            "El videojuego no está disponible.");
                }

                insertar.setInt(1, alquiler.getCliente().getId());
                insertar.setInt(2, alquiler.getVideojuego().getId());
                insertar.setDate(3, Date.valueOf(alquiler.getFechaAlquiler()));
                insertar.setDate(4,
                        Date.valueOf(alquiler.getFechaDevolucionPrevista()));

                insertar.executeUpdate();

                try (ResultSet rs = insertar.getGeneratedKeys()) {
                    if (rs.next()) {
                        alquiler.setId(rs.getInt(1));
                    }
                }

                conexion.commit();

                alquiler.setActivo(true);
                alquiler.getVideojuego().setDisponible(false);

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    @Override
    public Alquiler buscarPorId(int id) throws SQLException {

        String sql = SELECT_ALQUILER + " WHERE a.id = ?";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAlquiler(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Alquiler> listarTodos() throws SQLException {

        List<Alquiler> alquileres = new ArrayList<>();

        String sql = SELECT_ALQUILER + " ORDER BY a.id DESC";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alquileres.add(mapearAlquiler(rs));
            }
        }

        return alquileres;
    }

    @Override
    public void finalizar(int idAlquiler, LocalDate fechaDevolucion)
            throws SQLException {

        // Solo se finaliza si todavía está activo (evita finalizar dos veces).
        String sqlFinalizar = "UPDATE alquiler "
                + "SET activo = FALSE, fecha_devolucion_real = ? "
                + "WHERE id = ? AND activo = TRUE";

        String sqlLiberarVideojuego = "UPDATE videojuego "
                + "SET disponible = TRUE "
                + "WHERE id = (SELECT videojuego_id FROM alquiler "
                + "WHERE id = ?)";

        try (Connection conexion = ConexionBD.getConexion()) {

            conexion.setAutoCommit(false);

            try (PreparedStatement finalizar =
                         conexion.prepareStatement(sqlFinalizar);
                 PreparedStatement liberar =
                         conexion.prepareStatement(sqlLiberarVideojuego)) {

                finalizar.setDate(1, Date.valueOf(fechaDevolucion));
                finalizar.setInt(2, idAlquiler);

                if (finalizar.executeUpdate() == 0) {
                    throw new SQLException(
                            "El alquiler ya estaba finalizado.");
                }

                liberar.setInt(1, idAlquiler);
                liberar.executeUpdate();

                conexion.commit();

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean tieneAlquilerActivo(int idVideojuego) throws SQLException {

        String sql = "SELECT COUNT(*) FROM alquiler "
                + "WHERE videojuego_id = ? AND activo = TRUE";

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idVideojuego);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private Alquiler mapearAlquiler(ResultSet rs) throws SQLException {

        Cliente cliente = new Cliente(
                rs.getInt("cliente_id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("telefono"),
                rs.getString("email")
        );

        Videojuego videojuego = new Videojuego(
                rs.getInt("videojuego_id"),
                rs.getString("titulo"),
                rs.getString("genero"),
                rs.getString("plataforma"),
                rs.getDouble("precio"),
                rs.getBoolean("disponible")
        );

        Date devolucionReal = rs.getDate("fecha_devolucion_real");

        return new Alquiler(
                rs.getInt("id"),
                cliente,
                videojuego,
                rs.getDate("fecha_alquiler").toLocalDate(),
                rs.getDate("fecha_devolucion_prevista").toLocalDate(),
                devolucionReal == null ? null : devolucionReal.toLocalDate(),
                rs.getBoolean("activo")
        );
    }
}
