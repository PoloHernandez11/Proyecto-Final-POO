package models;

import java.time.LocalDate;

/**
 * Representa el alquiler de un videojuego por parte de un cliente.
 * <p>
 * Cada alquiler está asociado a un {@link Cliente} y a un
 * {@link Videojuego}. Mientras está activo, el videojuego no está
 * disponible. Al finalizarlo se guarda la fecha real de devolución.
 */
public class Alquiler {

    private int id;
    private Cliente cliente;
    private Videojuego videojuego;
    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
    private boolean activo;

    /**
     * Crea un alquiler vacío.
     */
    public Alquiler() {
    }

    /**
     * Crea un alquiler nuevo. Queda activo y sin fecha real de devolución.
     *
     * @param cliente                 cliente que alquila
     * @param videojuego              videojuego alquilado
     * @param fechaAlquiler           fecha en que se alquila
     * @param fechaDevolucionPrevista fecha en que debería devolverse
     */
    public Alquiler(Cliente cliente, Videojuego videojuego,
                    LocalDate fechaAlquiler,
                    LocalDate fechaDevolucionPrevista) {
        this.cliente = cliente;
        this.videojuego = videojuego;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.activo = true;
    }

    /**
     * Crea un alquiler con todos sus datos (por ejemplo, leído de la base).
     *
     * @param id                      identificador en la base de datos
     * @param cliente                 cliente que alquila
     * @param videojuego              videojuego alquilado
     * @param fechaAlquiler           fecha en que se alquiló
     * @param fechaDevolucionPrevista fecha prevista de devolución
     * @param fechaDevolucionReal     fecha en que se devolvió, o
     *                                {@code null} si sigue activo
     * @param activo                  {@code true} si todavía no se devolvió
     */
    public Alquiler(int id, Cliente cliente, Videojuego videojuego,
                    LocalDate fechaAlquiler,
                    LocalDate fechaDevolucionPrevista,
                    LocalDate fechaDevolucionReal, boolean activo) {
        this.id = id;
        this.cliente = cliente;
        this.videojuego = videojuego;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.activo = activo;
    }

    /** @return identificador del alquiler */
    public int getId() {
        return id;
    }

    /** @param id identificador del alquiler */
    public void setId(int id) {
        this.id = id;
    }

    /** @return cliente que alquiló */
    public Cliente getCliente() {
        return cliente;
    }

    /** @param cliente cliente que alquila */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /** @return videojuego alquilado */
    public Videojuego getVideojuego() {
        return videojuego;
    }

    /** @param videojuego videojuego alquilado */
    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    /** @return fecha del alquiler */
    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    /** @param fechaAlquiler fecha del alquiler */
    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    /** @return fecha prevista de devolución */
    public LocalDate getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    /** @param fechaDevolucionPrevista fecha prevista de devolución */
    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) {
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    /** @return fecha real de devolución, o {@code null} si sigue activo */
    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    /** @param fechaDevolucionReal fecha real de devolución */
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    /** @return {@code true} si el videojuego todavía no se devolvió */
    public boolean isActivo() {
        return activo;
    }

    /** @param activo {@code true} si el alquiler sigue activo */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
