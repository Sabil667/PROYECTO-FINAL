import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static List<Experimento> experimentos = new ArrayList<>();
    private static final String DIRECTORY = "saved_files";

    public static void main(String[] args) {
        // Crear un experimento por defecto
        Experimento experimentoPorDefecto = new Experimento("Experimento1", "BAC1", 100, 37.0, "alta", 1000, 30);
        experimentos.add(experimentoPorDefecto);

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

        String[] opciones = {"Crear Experimento", "Ver información de Población", "Ver nombre de las poblaciones", "Abrir archivo", "Ver archivos"};
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
                    case "Ver información de Población":
                        Experimento experimentoSeleccionado = (Experimento) JOptionPane.showInputDialog(null, "Selecciona un experimento", "Experimentos", JOptionPane.QUESTION_MESSAGE, null, experimentos.toArray(), experimentos.get(0));
                        if (experimentoSeleccionado != null) {
                            String resultadosExperimento = experimentoSeleccionado.realizarExperimento();

                            // Crea una nueva ventana para mostrar los resultados del experimento
                            JFrame resultadosFrame = new JFrame("Resultados del Experimento");
                            resultadosFrame.setSize(500, 300);
                            resultadosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                            // Crea un JTextArea para mostrar los resultados
                            JTextArea textArea = new JTextArea();
                            textArea.setText(resultadosExperimento);
                            textArea.setEditable(false);

                            // Añade el JTextArea al JFrame
                            resultadosFrame.add(new JScrollPane(textArea));

                            // Muestra la ventana
                            resultadosFrame.setVisible(true);

                            mostrarOpcionesPostExperimento(experimentoSeleccionado, textArea);
                        }
                        break;
                    case "Ver nombre de las poblaciones":
                        experimentoSeleccionado = (Experimento) JOptionPane.showInputDialog(null, "Selecciona un experimento", "Experimentos", JOptionPane.QUESTION_MESSAGE, null, experimentos.toArray(), experimentos.get(0));
                        if (experimentoSeleccionado != null) {
                            String nombresPoblaciones = experimentoSeleccionado.getPoblacionesBacterias().stream()
                                    .map(PoblacionBacterias::getNombre)
                                    .collect(Collectors.joining("\n"));
                            JOptionPane.showMessageDialog(null, nombresPoblaciones, "Nombres de Poblaciones de Bacterias", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case "Abrir archivo":
                        JFileChooser fileChooser = new JFileChooser();
                        int returnValue = fileChooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            try {
                                // Crea un BufferedReader para leer el archivo
                                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));

                                // Lee el contenido del archivo
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();

                                // Crea un nuevo JFrame para mostrar el contenido del archivo
                                JFrame fileFrame = new JFrame("Archivo: " + fileChooser.getSelectedFile().getName());
                                fileFrame.setSize(500, 300);
                                fileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                // Crea un JTextArea para mostrar el contenido del archivo
                                JTextArea textArea = new JTextArea();
                                textArea.setText(content.toString());

                                // Añade el JTextArea al JFrame
                                fileFrame.add(new JScrollPane(textArea));

                                // Crea un menú para las opciones de guardar
                                JMenuBar menuBar = new JMenuBar();
                                JMenu menu = new JMenu("Archivo");
                                JMenuItem saveItem = new JMenuItem("Guardar");
                                JMenuItem saveAsItem = new JMenuItem("Guardar como");
                                menu.add(saveItem);
                                menu.add(saveAsItem);
                                menuBar.add(menu);
                                fileFrame.setJMenuBar(menuBar);

                                // Añade un ActionListener al ítem de guardar
                                saveItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            // Genera un nombre de archivo aleatorio
                                            String fileName = DIRECTORY + "/file_" + new Random().nextInt(1000) + ".txt";

                                            // Crea un FileWriter para escribir en el archivo
                                            FileWriter writer = new FileWriter(fileName);

                                            // Escribe el contenido del JTextArea en el archivo
                                            writer.write(textArea.getText());
                                            writer.close();
                                        } catch (IOException ioException) {
                                            ioException.printStackTrace();
                                        }
                                    }
                                });

                                // Añade un ActionListener al ítem de guardar como
                                saveAsItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JFileChooser saveFileChooser = new JFileChooser();
                                        saveFileChooser.setCurrentDirectory(new File(DIRECTORY));
                                        int returnValue = saveFileChooser.showSaveDialog(null);
                                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                                            try {
                                                // Crea un FileWriter para escribir en el archivo
                                                FileWriter writer = new FileWriter(saveFileChooser.getSelectedFile().getAbsolutePath());

                                                // Escribe el contenido del JTextArea en el archivo
                                                writer.write(textArea.getText());
                                                writer.close();
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                        }
                                    }
                                });

                                // Muestra la ventana
                                fileFrame.setVisible(true);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        break;
                    case "Ver archivos":
                        JFileChooser directoryChooser = new JFileChooser();
                        directoryChooser.setCurrentDirectory(new File(DIRECTORY));
                        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int returnValue = directoryChooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File directory = directoryChooser.getSelectedFile();
                            File[] files = directory.listFiles();
                            if (files != null) {
                                StringBuilder fileList = new StringBuilder();
                                for (File file : files) {
                                    fileList.append(file.getName()).append("\n");
                                }

                                JTextArea textArea = new JTextArea(fileList.toString());
                                textArea.setEditable(false);

                                JFrame fileFrame = new JFrame("Archivos en: " + directory.getName());
                                fileFrame.setSize(500, 300);
                                fileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                fileFrame.add(new JScrollPane(textArea));
                                fileFrame.setVisible(true);
                            }
                        }
                        break;
                }
            }
        });
    }

    public static void mostrarOpcionesPostExperimento(Experimento experimento, JTextArea textArea) {
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
                textArea.setText(experimento.getDetalles()); // Actualiza los detalles del experimento
                break;
            case 1: // Eliminar población
                String[] nombresPoblaciones = experimento.getPoblacionesBacterias().stream()
                        .map(PoblacionBacterias::getNombre)
                        .toArray(String[]::new);
                String nombrePoblacionSeleccionada = (String) JOptionPane.showInputDialog(null, "Selecciona una población para eliminar", "Eliminar población", JOptionPane.QUESTION_MESSAGE, null, nombresPoblaciones, nombresPoblaciones[0]);
                if (nombrePoblacionSeleccionada != null) {
                    PoblacionBacterias poblacionBacterias = experimento.getPoblacionesBacterias().stream()
                            .filter(p -> p.getNombre().equals(nombrePoblacionSeleccionada))
                            .findFirst()
                            .orElse(null);
                    if (poblacionBacterias != null) {
                        experimento.getPoblacionesBacterias().remove(poblacionBacterias);
                        JOptionPane.showMessageDialog(null, "Población eliminada con éxito!");
                        textArea.setText(experimento.getDetalles()); // Actualiza los detalles del experimento
                    }
                }
                break;
        }
    }
}