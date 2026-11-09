package dao;

import java.sql.SQLException;
import models.Cliente;

/**
 * Acceso a datos de la tabla {@code cliente}.
 */
public interface ClienteDAO extends DAO<Cliente> {

    /**
     * Busca un cliente por su DNI.
     *
     * @param dni documento a buscar
     * @return el cliente, o {@code null} si no existe
     * @throws SQLException si falla la base de datos
     */
    Cliente buscarPorDni(String dni) throws SQLException;
}
