import java.util.Random;

public class PoblacionBacterias {
    private String nombre;
    private int numeroBacterias;
    private double temperatura;
    private String luminosidad;
    private double cantidadComida;

    private static final double COMIDA_POR_BACTERIA = 0.1; // Cantidad de comida que consume cada bacteria por día
    private static final double REPRODUCCION_POR_BACTERIA = 0.2; // Cantidad de nuevas bacterias que se generan por cada bacteria existente por día, si hay suficiente comida

    private double comidaInicial;

    public PoblacionBacterias(String nombre, int numeroBacterias, double temperatura, String luminosidad, double cantidadComida) {
        this.nombre = nombre;
        this.numeroBacterias = numeroBacterias;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
        this.cantidadComida = cantidadComida;
        this.comidaInicial = cantidadComida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroBacterias() {
        return numeroBacterias;
    }

    public void setNumeroBacterias(int numeroBacterias) {
        this.numeroBacterias = numeroBacterias;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public double getCantidadComida() {
        return cantidadComida;
    }

    public void setCantidadComida(double cantidadComida) {
        this.cantidadComida = cantidadComida;
    }

    public void incrementarComida(double cantidad) {
        this.cantidadComida += cantidad;
    }

    public void disminuirComida(double cantidad) {
        this.cantidadComida -= cantidad;
    }

    public void pasarDia(int dia) {
        // Las bacterias consumen comida
        double comidaConsumida = Math.min(cantidadComida, numeroBacterias * COMIDA_POR_BACTERIA);
        cantidadComida -= comidaConsumida;

        // Si hay suficiente comida, las bacterias se reproducen
        if (comidaConsumida == numeroBacterias * COMIDA_POR_BACTERIA) {
            // La tasa de reproducción disminuye a medida que pasan los días
            double tasaReproduccion = REPRODUCCION_POR_BACTERIA / (dia + 1);
            numeroBacterias += numeroBacterias * tasaReproduccion;
        }

        // Incrementa el número de bacterias cada día
        numeroBacterias++;

        // Imprime el estado de la población de bacterias
        System.out.println(toString(dia));
    }

    public String toString(int dia) {
        return "Día: " + (dia + 1) +
                "\nNombre de la población de bacterias: " + nombre +
                "\nNúmero de bacterias: " + numeroBacterias +
                "\nCantidad de comida: " + cantidadComida;
    }
}