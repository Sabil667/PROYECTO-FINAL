import javax.swing.*;
import java.awt.*;

public class VentanaBienvenida extends JFrame {

    public VentanaBienvenida() {
        // Configura el título de la ventana
        setTitle("BacterioLab");

        // Configura el tamaño de la ventana
        setSize(400, 300);

        // Centra la ventana en la pantalla
        setLocationRelativeTo(null);

        // Termina la aplicación cuando se cierra la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crea un nuevo panel
        JPanel panel = new JPanel();
        add(panel);

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

        // Añade el JLabel al panel
        panel.add(label);
    }
}