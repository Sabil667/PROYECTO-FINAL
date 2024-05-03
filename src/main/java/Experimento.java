public class Experimento {
    private String nombreArchivo;
    private PoblacionBacterias poblacionBacterias;
    private int dias;

    public Experimento(String nombreArchivo, String nombrePoblacion, int numeroBacterias, double temperatura, String luminosidad, double cantidadComida, int dias) {
        this.nombreArchivo = nombreArchivo;
        this.poblacionBacterias = new PoblacionBacterias(nombrePoblacion, numeroBacterias, temperatura, luminosidad, cantidadComida);
        this.dias = dias;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public PoblacionBacterias getPoblacionBacterias() {
        return poblacionBacterias;
    }

    public void setPoblacionBacterias(PoblacionBacterias poblacionBacterias) {
        this.poblacionBacterias = poblacionBacterias;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public void realizarExperimento() {
        for (int i = 0; i < dias; i++) {
            poblacionBacterias.pasarDia(i);
            System.out.println(); // Imprime una línea en blanco
        }
    }

    @Override
    public String toString() {
        return nombreArchivo; // Devuelve solo el nombre del experimento
    }
}