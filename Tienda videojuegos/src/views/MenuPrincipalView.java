package views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Menú principal: desde acá se abren las pantallas de clientes,
 * videojuegos y alquileres. Al cerrarlo se cierra el programa.
 */
public class MenuPrincipalView extends JFrame {

    private final JButton buttonClientes = new JButton("Clientes");
    private final JButton buttonVideojuegos = new JButton("Videojuegos");
    private final JButton buttonAlquileres = new JButton("Alquileres");

    /**
     * Crea y arma el menú principal.
     */
    public MenuPrincipalView() {
        setTitle("Tienda de Videojuegos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel labelTitulo = new JLabel("TIENDA DE VIDEOJUEGOS",
                SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.add(buttonClientes);
        panelBotones.add(buttonVideojuegos);
        panelBotones.add(buttonAlquileres);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.add(labelTitulo, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);

        setContentPane(panel);
        setSize(360, 280);
    }

    /** @param listener acción del botón Clientes */
    public void agregarListenerClientes(ActionListener listener) {
        buttonClientes.addActionListener(listener);
    }

    /** @param listener acción del botón Videojuegos */
    public void agregarListenerVideojuegos(ActionListener listener) {
        buttonVideojuegos.addActionListener(listener);
    }

    /** @param listener acción del botón Alquileres */
    public void agregarListenerAlquileres(ActionListener listener) {
        buttonAlquileres.addActionListener(listener);
    }
}
