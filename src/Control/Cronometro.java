package Control;

public class Cronometro {

    private long inicio;            // nanoTime al iniciar
    private long pausaAcumulada;    // total de nanos en pausa
    private long inicioPausa;       // nanoTime cuando empezó la pausa actual
    private boolean enPausa;

    public synchronized void iniciar() {
        inicio = System.nanoTime();
        pausaAcumulada = 0;
        enPausa = false;
    }

    public synchronized void pausar() {
        if (!enPausa) {
            enPausa = true;
            inicioPausa = System.nanoTime();
        }
    }

    public synchronized void reanudar() {
        if (enPausa) {
            enPausa = false;
            pausaAcumulada += System.nanoTime() - inicioPausa;
        }
    }

    /** Tiempo transcurrido en segundos, descontando las pausas. */
    public synchronized double getSegundos() {
        long ahora = enPausa ? inicioPausa : System.nanoTime();
        return (ahora - inicio - pausaAcumulada) / 1_000_000_000.0;
    }
}