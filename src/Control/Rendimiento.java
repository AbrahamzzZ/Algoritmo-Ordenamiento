package Control;

import Algoritmos.AlgoritmoOrdenamiento;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Mide el tiempo REAL de un algoritmo: sin animación y sin retardos.
 * Hace 3 ejecuciones de calentamiento y devuelve la mediana de varias
 * repeticiones (más confiable que una sola medición).
 */
public final class Rendimiento {

    private Rendimiento() { }

    /** @return tiempo en milisegundos */
    public static double medirMs(Supplier<AlgoritmoOrdenamiento> fabrica, int[] datos, int repeticiones) {
        try {
            // Calentamiento: la JVM optimiza el código después de unas ejecuciones
            for (int k = 0; k < 3; k++) {
                fabrica.get().ejecutar(datos.clone(), null);
            }

            long[] tiempos = new long[repeticiones];
            for (int r = 0; r < repeticiones; r++) {
                int[] copia = datos.clone();
                AlgoritmoOrdenamiento alg = fabrica.get();
                long t0 = System.nanoTime();
                alg.ejecutar(copia, null);          // null = sin observador, sin retardos
                tiempos[r] = System.nanoTime() - t0;
            }
            Arrays.sort(tiempos);
            return tiempos[repeticiones / 2] / 1_000_000.0;   // mediana
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }
}
