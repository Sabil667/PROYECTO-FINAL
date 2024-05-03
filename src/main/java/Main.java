import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Experimento> experimentos = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Menú de Gestión de Datos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titulo, gbc);

        String[] opciones = {"Crear Experimento", "Ver Experimentos"};
        JComboBox<String> comboBox = new JComboBox<>(opciones);

        panel.add(comboBox, gbc);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcionSeleccionada = (String) comboBox.getSelectedItem();
                switch (opcionSeleccionada) {
                    case "Crear Experimento":
                        String nombreArchivo = JOptionPane.showInputDialog("Introduce el nombre del archivo para el experimento:");
                        String nombrePoblacion = JOptionPane.showInputDialog("Introduce el nombre de la población de bacterias:");
                        int numeroBacterias = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número inicial de bacterias:"));
                        double temperatura = Double.parseDouble(JOptionPane.showInputDialog("Introduce la temperatura:"));
                        String luminosidad = JOptionPane.showInputDialog("Introduce la condición de luminosidad:");
                        double cantidadComida = Double.parseDouble(JOptionPane.showInputDialog("Introduce la cantidad inicial de comida:"));
                        Experimento experimento = new Experimento(nombreArchivo, nombrePoblacion, numeroBacterias, temperatura, luminosidad, cantidadComida);
                        experimentos.add(experimento);
                        JOptionPane.showMessageDialog(null, "Experimento creado con éxito!");
                        break;
                    case "Ver Experimentos":
                        Experimento experimentoSeleccionado = (Experimento) JOptionPane.showInputDialog(null, "Selecciona un experimento", "Experimentos", JOptionPane.QUESTION_MESSAGE, null, experimentos.toArray(), experimentos.get(0));
                        if (experimentoSeleccionado != null) {
                            experimentoSeleccionado.realizarExperimento();
                        }
                        break;
                }
            }
        });
    }
}