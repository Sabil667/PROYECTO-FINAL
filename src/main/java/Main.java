import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.nio.file.Files;

public class Main {
    public static List<Experimento> experimentos = new ArrayList<>();
    private static final String DIRECTORY = "saved_files";
    private static List<File> savedFiles = new ArrayList<>(); // Lista para guardar los archivos

    public static void main(String[] args) {
        // Crear un experimento por defecto
        Experimento experimentoPorDefecto = new Experimento("Experimento1", "BAC1", 100, 37.0, "alta", 1000, 30);
        experimentos.add(experimentoPorDefecto);

        // Crear la ventana de bienvenida
        VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
        ventanaBienvenida.setVisible(true);
    }

    public static void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Menú de BacterioLab");
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
                        JFrame editorFrame = new JFrame("Editor de texto");
                        editorFrame.setSize(500, 300);
                        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        JTextArea textArea = new JTextArea();
                        editorFrame.add(new JScrollPane(textArea));

                        JMenuBar menuBar = new JMenuBar();
                        JMenu menu = new JMenu("Archivo \u25BC ");
                        JMenuItem saveItem = new JMenuItem("Guardar");
                        JMenuItem saveAsItem = new JMenuItem("Guardar como");
                        menu.add(saveItem);
                        menu.add(saveAsItem);
                        menuBar.add(menu);
                        editorFrame.setJMenuBar(menuBar);

                        saveItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    File directory = new File(DIRECTORY);
                                    if (!directory.exists()) {
                                        directory.mkdir(); // Crea el directorio si no existe
                                    }
                                    String fileName = "file_" + new Random().nextInt(1000) + ".txt";
                                    File file = new File(DIRECTORY, fileName);
                                    FileWriter writer = new FileWriter(file);
                                    writer.write(textArea.getText());
                                    writer.close();
                                    savedFiles.add(file); // Añade el archivo a la lista de archivos guardados
                                    JOptionPane.showMessageDialog(null, "Archivo guardado con el nombre: " + fileName, "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });

                        saveAsItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser saveFileChooser = new JFileChooser();
                                saveFileChooser.setCurrentDirectory(new File(DIRECTORY));
                                int returnValue = saveFileChooser.showSaveDialog(null);
                                if (returnValue == JFileChooser.APPROVE_OPTION) {
                                    try {
                                        FileWriter writer = new FileWriter(saveFileChooser.getSelectedFile().getAbsolutePath());
                                        writer.write(textArea.getText());
                                        writer.close();
                                        savedFiles.add(saveFileChooser.getSelectedFile()); // Añade el archivo a la lista de archivos guardados
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
                            }
                        });

                        editorFrame.setVisible(true);
                        break;
                    case "Ver archivos":
                        // Muestra los archivos guardados en lugar de abrir un JFileChooser
                        if (savedFiles.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No hay archivos guardados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Crea una lista de nombres de archivos
                            List<String> fileNames = savedFiles.stream()
                                    .map(File::getName) // Usa el método getName() para obtener solo el nombre del archivo
                                    .collect(Collectors.toList());

                            String fileNameToOpen = (String) JOptionPane.showInputDialog(null, "Selecciona un archivo", "Archivos guardados", JOptionPane.QUESTION_MESSAGE, null, fileNames.toArray(), fileNames.get(0));
                            if (fileNameToOpen != null) {
                                // Encuentra el archivo correspondiente en la lista de archivos guardados
                                File fileToOpen = savedFiles.stream()
                                        .filter(file -> file.getName().equals(fileNameToOpen))
                                        .findFirst()
                                        .orElse(null);

                                if (fileToOpen != null) {
                                    try {
                                        // Lee el contenido del archivo
                                        String content = new String(Files.readAllBytes(fileToOpen.toPath()));

                                        // Crea un nuevo JFrame para mostrar el contenido
                                        JFrame contentFrame = new JFrame("Contenido del archivo");
                                        contentFrame.setSize(500, 300);
                                        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                        // Crea un JTextArea para mostrar el contenido
                                        JTextArea contentTextArea = new JTextArea();
                                        contentTextArea.setText(content);
                                        contentTextArea.setEditable(false);

                                        // Añade el JTextArea al JFrame
                                        contentFrame.add(new JScrollPane(contentTextArea));

                                        // Muestra la ventana
                                        contentFrame.setVisible(true);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
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