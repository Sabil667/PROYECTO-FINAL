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
        bienvenidaPanel.setLayout(new BorderLayout());
        panel.add(bienvenidaPanel, "Bienvenida");

        // Crea un nuevo JLabel para la imagen
        JLabel label = new JLabel();

        // Carga la imagen
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/IMAGEN/bacteria.png"));
        // Ajusta la imagen al tamaño del JFrame
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        // Añade la imagen al JLabel
        label.setIcon(imageIcon);

        // Añade el JLabel al panel de bienvenida
        bienvenidaPanel.add(label, BorderLayout.CENTER);

        // Crea un botón para acceder al menú
        JButton menuButton = new JButton("Acceder al Menú");
        bienvenidaPanel.add(menuButton, BorderLayout.SOUTH);

        // Añade un ActionListener al botón
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se hace clic en el botón, se muestran las demás opciones
                cardLayout.show(panel, "Menu");
            }
        });

        // Crea un JLabel para el título
        JLabel titleLabel = new JLabel("BacterioLab", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenidaPanel.add(titleLabel, BorderLayout.NORTH);

        // Crea el panel del menú
        JPanel menuPanel = new JPanel(new GridBagLayout());
        panel.add(menuPanel, "Menu");

        // Llama al método placeComponents para llenar el panel del menú
        Main.placeComponents(menuPanel);
    }
}