package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza los datos de conexión a la base de datos MySQL.
 * <p>
 * Todos los DAO obtienen la conexión desde esta clase, así la
 * configuración (URL, usuario y contraseña) se cambia en un solo lugar.
 */
public final class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/tienda_videojuegos";

    private static final String USUARIO = "root";

    /** Cada integrante pone acá la contraseña de su MySQL local. */
    private static final String PASSWORD = "polo";

    private ConexionBD() {
    }

    /**
     * Abre una nueva conexión con la base de datos.
     *
     * @return conexión abierta; quien la usa debe cerrarla
     * @throws SQLException si no se puede conectar con MySQL
     */
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
