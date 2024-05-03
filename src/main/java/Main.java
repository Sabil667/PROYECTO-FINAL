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
                        int dias = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número de días que durará el experimento:"));
                        Experimento experimento = new Experimento(nombreArchivo, nombrePoblacion, numeroBacterias, temperatura, luminosidad, cantidadComida, dias);
                        experimentos.add(experimento);
                        JOptionPane.showMessageDialog(null, "Experimento creado con éxito!");
                        break;
                    case "Ver Experimentos":
                        Experimento experimentoSeleccionado = (Experimento) JOptionPane.showInputDialog(null, "Selecciona un experimento", "Experimentos", JOptionPane.QUESTION_MESSAGE, null, experimentos.toArray(), experimentos.get(0));
                        if (experimentoSeleccionado != null) {
                            experimentoSeleccionado.realizarExperimento();
                            mostrarOpcionesPostExperimento(experimentoSeleccionado);
                        }
                        break;
                }
            }
        });
    }

    public static void mostrarOpcionesPostExperimento(Experimento experimento) {
        String[] opciones = {"Añadir población", "Eliminar población"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Qué te gustaría hacer?", "Opciones post-experimento",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        switch (seleccion) {
            case 0: // Añadir población
                String nombrePoblacion = JOptionPane.showInputDialog("Introduce el nombre de la nueva población de bacterias:");
                int numeroBacterias = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número inicial de bacterias:"));
                double temperatura = Double.parseDouble(JOptionPane.showInputDialog("Introduce la temperatura:"));
                String luminosidad = JOptionPane.showInputDialog("Introduce la condición de luminosidad:");
                experimento.agregarPoblacion(nombrePoblacion, numeroBacterias, temperatura, luminosidad);
                JOptionPane.showMessageDialog(null, "Población añadida con éxito!");
                break;
            case 1: // Eliminar población
                PoblacionBacterias poblacionBacterias = (PoblacionBacterias) JOptionPane.showInputDialog(null, "Selecciona una población para eliminar", "Eliminar población", JOptionPane.QUESTION_MESSAGE, null, experimento.getPoblacionesBacterias().toArray(), experimento.getPoblacionesBacterias().get(0));
                if (poblacionBacterias != null) {
                    experimento.getPoblacionesBacterias().remove(poblacionBacterias);
                    JOptionPane.showMessageDialog(null, "Población eliminada con éxito!");
                }
                break;
        }
    }
}