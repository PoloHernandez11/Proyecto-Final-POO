package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Operaciones CRUD comunes a todas las entidades que se guardan en la base.
 *
 * @param <T> tipo de entidad (Cliente, Videojuego, ...)
 */
public interface DAO<T> {

    /**
     * Guarda una entidad nueva y le asigna el id generado por la base.
     *
     * @param entity entidad a guardar
     * @throws SQLException si falla la base de datos
     */
    void agregar(T entity) throws SQLException;

    /**
     * Busca una entidad por su id.
     *
     * @param id identificador
     * @return la entidad, o {@code null} si no existe
     * @throws SQLException si falla la base de datos
     */
    T buscarPorId(int id) throws SQLException;

    /**
     * Devuelve todas las entidades guardadas.
     *
     * @return lista (vacía si no hay registros)
     * @throws SQLException si falla la base de datos
     */
    List<T> listarTodos() throws SQLException;

    /**
     * Actualiza los datos de una entidad existente.
     *
     * @param entity entidad con el id y los datos nuevos
     * @throws SQLException si falla la base de datos
     */
    void modificar(T entity) throws SQLException;

    /**
     * Elimina una entidad por su id.
     *
     * @param id identificador
     * @throws SQLException si falla la base de datos
     */
    void eliminar(int id) throws SQLException;
}
