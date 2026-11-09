package models;

/**
 * Representa a un cliente de la tienda que puede alquilar videojuegos.
 */
public class Cliente {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;

    /**
     * Crea un cliente vacío.
     */
    public Cliente() {
    }

    /**
     * Crea un cliente nuevo (todavía sin id).
     *
     * @param nombre   nombre
     * @param apellido apellido
     * @param dni      documento
     * @param telefono teléfono de contacto
     * @param email    correo electrónico
     */
    public Cliente(String nombre, String apellido, String dni,
                   String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    /**
     * Crea un cliente con todos sus datos (por ejemplo, leído de la base).
     *
     * @param id       identificador en la base de datos
     * @param nombre   nombre
     * @param apellido apellido
     * @param dni      documento
     * @param telefono teléfono de contacto
     * @param email    correo electrónico
     */
    public Cliente(int id, String nombre, String apellido, String dni,
                   String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    /** @return identificador del cliente */
    public int getId() {
        return id;
    }

    /** @param id identificador del cliente */
    public void setId(int id) {
        this.id = id;
    }

    /** @return nombre del cliente */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nombre del cliente */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return apellido del cliente */
    public String getApellido() {
        return apellido;
    }

    /** @param apellido apellido del cliente */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /** @return DNI del cliente */
    public String getDni() {
        return dni;
    }

    /** @param dni DNI del cliente */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /** @return teléfono del cliente */
    public String getTelefono() {
        return telefono;
    }

    /** @param telefono teléfono del cliente */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /** @return email del cliente */
    public String getEmail() {
        return email;
    }

    /** @param email email del cliente */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Texto que se muestra en los combos de la pantalla de alquileres.
     *
     * @return id, nombre y apellido
     */
    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellido;
    }
}
