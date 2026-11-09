package tienda.videojuegos;

import controllers.MenuPrincipalController;
import dao.AlquilerDAO;
import dao.ClienteDAO;
import dao.VideojuegoDAO;
import dao.impl.AlquilerDAOImpl;
import dao.impl.ClienteDAOImpl;
import dao.impl.VideojuegoDAOImpl;
import java.awt.EventQueue;
import views.MenuPrincipalView;

/**
 * Punto de entrada del sistema de alquiler de videojuegos.
 * <p>
 * Crea los DAO (acceso a datos) y abre el menú principal.
 */
public class TiendaVideojuegos {

    /**
     * Inicia la aplicación.
     *
     * @param args no se usan
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            ClienteDAO clienteDAO = new ClienteDAOImpl();
            VideojuegoDAO videojuegoDAO = new VideojuegoDAOImpl();
            AlquilerDAO alquilerDAO = new AlquilerDAOImpl();

            MenuPrincipalView menu = new MenuPrincipalView();

            new MenuPrincipalController(
                    menu, clienteDAO, videojuegoDAO, alquilerDAO);

            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }
}
