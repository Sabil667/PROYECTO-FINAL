import java.util.ArrayList;
import java.util.List;

public class Experimento {
    private String nombreArchivo;
    private List<PoblacionBacterias> poblacionesBacterias;
    private int dias;
    private double cantidadComida;

    public Experimento(String nombreArchivo, String nombrePoblacion, int numeroBacterias, double temperatura, String luminosidad, double cantidadComida, int dias) {
        this.nombreArchivo = nombreArchivo;
        this.poblacionesBacterias = new ArrayList<>();
        this.poblacionesBacterias.add(new PoblacionBacterias(nombrePoblacion, numeroBacterias, temperatura, luminosidad));
        this.dias = dias;
        this.cantidadComida = cantidadComida;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<PoblacionBacterias> getPoblacionesBacterias() {
        return poblacionesBacterias;
    }

    public void agregarPoblacion(String nombrePoblacion, int numeroBacterias, double temperatura, String luminosidad) {
        this.poblacionesBacterias.add(new PoblacionBacterias(nombrePoblacion, numeroBacterias, temperatura, luminosidad));
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public void realizarExperimento() {
        for (int i = 0; i < dias; i++) {
            for (PoblacionBacterias poblacionBacterias : poblacionesBacterias) {
                poblacionBacterias.pasarDia(i, cantidadComida);
            }
            System.out.println(); // Imprime una línea en blanco
        }
    }

    @Override
    public String toString() {
        return nombreArchivo; // Devuelve solo el nombre del experimento
    }
}