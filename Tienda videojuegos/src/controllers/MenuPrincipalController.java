package controllers;

import dao.AlquilerDAO;
import dao.ClienteDAO;
import dao.VideojuegoDAO;
import javax.swing.JFrame;
import views.AlquilerView;
import views.ClienteView;
import views.MenuPrincipalView;
import views.VideojuegoView;

/**
 * Controlador del menú principal. Cada botón abre una pantalla nueva
 * con su controlador, así los datos se cargan actualizados.
 */
public class MenuPrincipalController {

    private final MenuPrincipalView vista;
    private final ClienteDAO clienteDAO;
    private final VideojuegoDAO videojuegoDAO;
    private final AlquilerDAO alquilerDAO;

    /**
     * Conecta los botones del menú con las pantallas.
     *
     * @param vista         menú principal
     * @param clienteDAO    acceso a datos de clientes
     * @param videojuegoDAO acceso a datos de videojuegos
     * @param alquilerDAO   acceso a datos de alquileres
     */
    public MenuPrincipalController(MenuPrincipalView vista,
                                   ClienteDAO clienteDAO,
                                   VideojuegoDAO videojuegoDAO,
                                   AlquilerDAO alquilerDAO) {
        this.vista = vista;
        this.clienteDAO = clienteDAO;
        this.videojuegoDAO = videojuegoDAO;
        this.alquilerDAO = alquilerDAO;

        vista.agregarListenerClientes(e -> abrirClientes());
        vista.agregarListenerVideojuegos(e -> abrirVideojuegos());
        vista.agregarListenerAlquileres(e -> abrirAlquileres());
    }

    private void abrirClientes() {
        ClienteView pantalla = new ClienteView();
        new ClienteController(pantalla, clienteDAO);
        mostrar(pantalla);
    }

    private void abrirVideojuegos() {
        VideojuegoView pantalla = new VideojuegoView();
        new VideojuegoController(pantalla, videojuegoDAO, alquilerDAO);
        mostrar(pantalla);
    }

    private void abrirAlquileres() {
        AlquilerView pantalla = new AlquilerView();
        new AlquilerController(pantalla, alquilerDAO, clienteDAO,
                videojuegoDAO);
        mostrar(pantalla);
    }

    private void mostrar(JFrame pantalla) {
        pantalla.setLocationRelativeTo(vista);
        pantalla.setVisible(true);
    }
}
