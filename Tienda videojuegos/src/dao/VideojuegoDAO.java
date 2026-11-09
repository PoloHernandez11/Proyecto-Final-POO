package dao;

import java.sql.SQLException;
import java.util.List;
import models.Videojuego;

/**
 * Acceso a datos de la tabla {@code videojuego}.
 *
 * @author agus_
 */
public interface VideojuegoDAO extends DAO<Videojuego> {

    /**
     * Devuelve solo los videojuegos que se pueden alquilar.
     *
     * @return videojuegos con {@code disponible = true}
     * @throws SQLException si falla la base de datos
     */
    List<Videojuego> listarDisponibles() throws SQLException;
}
