import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Experimento {
    private String nombreArchivo;
    private List<PoblacionBacterias> poblaciones;

    public Experimento(String nombreArchivo, String nombrePoblacion, int numeroBacterias, double temperatura, String luminosidad, double cantidadComida) {
        this.nombreArchivo = nombreArchivo;
        this.poblaciones = new ArrayList<>();
        this.poblaciones.add(new PoblacionBacterias(nombrePoblacion, numeroBacterias, temperatura, luminosidad, cantidadComida));
    }

    public void guardar() {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void realizarExperimento() {
        for (int dia = 0; dia < 30; dia++) {
            System.out.println("Dia" + (dia + 1));
            for (PoblacionBacterias poblacion : poblaciones) {
                poblacion.pasarDia(dia);
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Experimento ").append(nombreArchivo).append("\n");
        for (PoblacionBacterias poblacion : poblaciones) {
            sb.append("Población de bacterias: ").append(poblacion.getNombre()).append("\n");
            sb.append("Número de bacterias al final del experimento: ").append(poblacion.getNumeroBacterias()).append("\n");
            sb.append("Cantidad de comida al final del experimento: ").append(poblacion.getCantidadComida()).append("\n");
        }
        return sb.toString();
    }
}