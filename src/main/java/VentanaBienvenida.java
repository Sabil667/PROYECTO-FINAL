import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaBienvenida extends JFrame {
    private CardLayout cardLayout = new CardLayout();

    public VentanaBienvenida() {
        // Configura el título de la ventana
        setTitle("BacterioLab");

        // Configura el tamaño de la ventana
        setSize(400, 300);

        // Centra la ventana en la pantalla
        setLocationRelativeTo(null);

        // Termina la aplicación cuando se cierra la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crea un nuevo panel con CardLayout
        JPanel panel = new JPanel(cardLayout);
        add(panel);

        // Crea el panel de bienvenida
        JPanel bienvenidaPanel = new JPanel();
        panel.add(bienvenidaPanel, "Bienvenida");

        // Crea un nuevo JLabel para la imagen
        JLabel label = new JLabel();

        // Carga la imagen
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/bacteria.png"));

        // Ajusta la imagen al tamaño del JFrame
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        // Añade la imagen al JLabel
        label.setIcon(imageIcon);

        // Añade el JLabel al panel de bienvenida
        bienvenidaPanel.add(label);

        // Crea un botón para acceder al menú
        JButton menuButton = new JButton("Acceder al Menú");
        menuButton.setVisible(false); // El botón inicialmente está oculto
        bienvenidaPanel.add(menuButton);

        // Añade un ActionListener al botón
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se hace clic en el botón, se muestran las demás opciones
                cardLayout.show(panel, "Menu");
            }
        });

        // Muestra el botón después de un tiempo
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuButton.setVisible(true);
            }
        });
        timer.setRepeats(false); // El timer solo se ejecuta una vez
        timer.start();

        // Crea el panel del menú
        JPanel menuPanel = new JPanel(new GridBagLayout());
        panel.add(menuPanel, "Menu");

        // Llama al método placeComponents para llenar el panel del menú
        Main.placeComponents(menuPanel);
    }
}
