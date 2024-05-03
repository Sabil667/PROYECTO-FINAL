import java.util.Random;

public class PoblacionBacterias {
    private String nombre;
    private int numeroBacterias;
    private double temperatura;
    private String luminosidad;
    private double cantidadComida;

    private static final double COMIDA_POR_BACTERIA = 0.1; // Cantidad de comida que consume cada bacteria por día
    private static final double REPRODUCCION_POR_BACTERIA = 0.2; // Cantidad de nuevas bacterias que se generan por cada bacteria existente por día, si hay suficiente comida
    private static final int MAX_CRECIMIENTO_DIARIO = 10; // Máximo crecimiento diario de las bacterias
    private static final int MAX_DECREMENTO_DIARIO = 10; // Máximo decremento diario de las bacterias

    private Random random = new Random();

    public PoblacionBacterias(String nombre, int numeroBacterias, double temperatura, String luminosidad, double cantidadComida) {
        this.nombre = nombre;
        this.numeroBacterias = numeroBacterias;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
        this.cantidadComida = cantidadComida;
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

    public void pasarDia(int dia) {
        // Las bacterias consumen comida
        double comidaConsumida = Math.min(cantidadComida, numeroBacterias * COMIDA_POR_BACTERIA);
        cantidadComida -= comidaConsumida;

        // Redondea la cantidad de comida a dos decimales
        cantidadComida = Math.round(cantidadComida * 100.0) / 100.0;

        // Si hay suficiente comida, las bacterias se reproducen
        if (comidaConsumida == numeroBacterias * COMIDA_POR_BACTERIA) {
            // La tasa de reproducción disminuye a medida que pasan los días
            double tasaReproduccion = REPRODUCCION_POR_BACTERIA / (dia + 1);

            // Ajusta la tasa de reproducción en función de la luminosidad
            switch (luminosidad) {
                case "alta":
                    tasaReproduccion *= 1.5; // Las bacterias crecen un 50% más rápido
                    break;
                case "normal":
                    // No se hace ningún ajuste
                    break;
                case "baja":
                    tasaReproduccion *= 0.5; // Las bacterias crecen un 50% más lento
                    break;
            }

            numeroBacterias += numeroBacterias * tasaReproduccion;
        }

        // Si la comida es cero, las bacterias comienzan a disminuir
        if (cantidadComida == 0) {
            int decrementoDiario = random.nextInt(MAX_DECREMENTO_DIARIO + 1);
            numeroBacterias -= decrementoDiario;
            // Asegurarse de que el número de bacterias no sea negativo
            if (numeroBacterias < 0) {
                numeroBacterias = 0;
            }
        } else {
            // Incrementa el número de bacterias de manera aleatoria cada día
            int crecimientoDiario = random.nextInt(MAX_CRECIMIENTO_DIARIO + 1);
            numeroBacterias += crecimientoDiario;
        }

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