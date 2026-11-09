package models;

/**
 * Representa un videojuego que la tienda ofrece en alquiler.
 * <p>
 * El atributo {@code disponible} indica si el videojuego se puede alquilar.
 * Pasa a {@code false} al registrar un alquiler y vuelve a {@code true}
 * cuando ese alquiler se finaliza.
 *
 * @author agus_
 */
public class Videojuego {

    private int id;
    private String titulo;
    private String genero;
    private String plataforma;
    private double precio;
    private boolean disponible;

    /**
     * Crea un videojuego vacío.
     */
    public Videojuego() {
    }

    /**
     * Crea un videojuego nuevo (todavía sin id). Por defecto queda disponible.
     *
     * @param titulo     título del juego
     * @param genero     género (acción, deportes, etc.)
     * @param plataforma consola o PC
     * @param precio     precio del alquiler
     */
    public Videojuego(String titulo, String genero, String plataforma,
                      double precio) {
        this.titulo = titulo;
        this.genero = genero;
        this.plataforma = plataforma;
        this.precio = precio;
        this.disponible = true;
    }

    /**
     * Crea un videojuego con todos sus datos (por ejemplo, leído de la base).
     *
     * @param id         identificador en la base de datos
     * @param titulo     título del juego
     * @param genero     género
     * @param plataforma consola o PC
     * @param precio     precio del alquiler
     * @param disponible {@code true} si se puede alquilar
     */
    public Videojuego(int id, String titulo, String genero, String plataforma,
                      double precio, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.plataforma = plataforma;
        this.precio = precio;
        this.disponible = disponible;
    }

    /** @return identificador del videojuego */
    public int getId() {
        return id;
    }

    /** @param id identificador del videojuego */
    public void setId(int id) {
        this.id = id;
    }

    /** @return título del videojuego */
    public String getTitulo() {
        return titulo;
    }

    /** @param titulo título del videojuego */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /** @return género del videojuego */
    public String getGenero() {
        return genero;
    }

    /** @param genero género del videojuego */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /** @return plataforma del videojuego */
    public String getPlataforma() {
        return plataforma;
    }

    /** @param plataforma plataforma del videojuego */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    /** @return precio del alquiler */
    public double getPrecio() {
        return precio;
    }

    /** @param precio precio del alquiler */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /** @return {@code true} si el videojuego se puede alquilar */
    public boolean isDisponible() {
        return disponible;
    }

    /** @param disponible {@code true} si el videojuego se puede alquilar */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Texto que se muestra en los combos de la pantalla de alquileres.
     *
     * @return id, título y plataforma
     */
    @Override
    public String toString() {
        return id + " - " + titulo + " (" + plataforma + ")";
    }
}
