package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import models.Alquiler;

/**
 * Acceso a datos de la tabla {@code alquiler}.
 * <p>
 * Un alquiler no se modifica ni se borra desde el sistema: se registra y,
 * cuando se devuelve el videojuego, se finaliza. Por eso esta interfaz no
 * extiende {@link DAO}.
 */
public interface AlquilerDAO {

    /**
     * Registra un alquiler nuevo y marca el videojuego como no disponible.
     * Las dos operaciones se hacen en una sola transacción.
     *
     * @param alquiler alquiler a registrar (con cliente y videojuego)
     * @throws SQLException si falla la base o el videojuego ya no estaba
     *                      disponible
     */
    void registrar(Alquiler alquiler) throws SQLException;

    /**
     * Busca un alquiler por su id, con su cliente y su videojuego.
     *
     * @param id identificador del alquiler
     * @return el alquiler, o {@code null} si no existe
     * @throws SQLException si falla la base de datos
     */
    Alquiler buscarPorId(int id) throws SQLException;

    /**
     * Devuelve todos los alquileres (activos y finalizados).
     *
     * @return lista ordenada del más reciente al más antiguo
     * @throws SQLException si falla la base de datos
     */
    List<Alquiler> listarTodos() throws SQLException;

    /**
     * Finaliza un alquiler activo y deja el videojuego disponible otra vez.
     * Las dos operaciones se hacen en una sola transacción.
     *
     * @param idAlquiler         alquiler a finalizar
     * @param fechaDevolucion    fecha real en que se devolvió el videojuego
     * @throws SQLException si falla la base o el alquiler ya estaba finalizado
     */
    void finalizar(int idAlquiler, LocalDate fechaDevolucion)
            throws SQLException;

    /**
     * Indica si un videojuego tiene un alquiler activo.
     *
     * @param idVideojuego videojuego a consultar
     * @return {@code true} si está alquilado en este momento
     * @throws SQLException si falla la base de datos
     */
    boolean tieneAlquilerActivo(int idVideojuego) throws SQLException;
}
