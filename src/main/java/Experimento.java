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

    public String realizarExperimento() {
        StringBuilder resultados = new StringBuilder();
        for (int i = 0; i < dias; i++) {
            for (PoblacionBacterias poblacionBacterias : poblacionesBacterias) {
                resultados.append(poblacionBacterias.pasarDia(i, cantidadComida)).append("\n");
            }
            resultados.append("\n"); // Agrega una línea en blanco entre cada día
        }
        return resultados.toString();
    }

    public String getDetalles() {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Nombre del archivo: ").append(nombreArchivo).append("\n");
        detalles.append("Días: ").append(dias).append("\n");
        detalles.append("Cantidad de comida: ").append(cantidadComida).append("\n");
        detalles.append("Poblaciones de bacterias:\n");
        for (PoblacionBacterias poblacion : poblacionesBacterias) {
            detalles.append(poblacion.toString()).append("\n"); // Asegúrate de que la clase PoblacionBacterias tiene un método toString() adecuado
        }
        return detalles.toString();
    }

    @Override
    public String toString() {
        return nombreArchivo; // Devuelve solo el nombre del experimento
    }
}