package Control;

public class ControlEjecucion {

    /**
     * Milisegundos de espera por paso. Nivel 1 = más lento, nivel 10 = más rápido.
     * El mínimo es 1 ms (no 0): con 0 los hilos terminan casi a la vez
     * y el orden de llegada deja de reflejar la cantidad de pasos.
     */
    private static final int[] RETARDOS_MS = {250, 150, 100, 60, 35, 20, 10, 5, 2, 1};
    public static final int NIVEL_MIN = 1;
    public static final int NIVEL_MAX = RETARDOS_MS.length;

    private volatile int nivel = 5;
    private boolean pausado = false;

    public synchronized void pausar() {
        pausado = true;
    }

    public synchronized void continuar() {
        pausado = false;
        notifyAll();                 // despierta a los hilos que esperan
    }

    public synchronized boolean isPausado() {
        return pausado;
    }

    /** El hilo que lo llama se queda bloqueado mientras esté en pausa. */
    public synchronized void esperarSiPausado() throws InterruptedException {
        while (pausado) {
            wait();
        }
    }

    // ---------------- Velocidad ----------------

    public void aumentarVelocidad() {
        if (nivel < NIVEL_MAX) nivel++;
    }

    public void disminuirVelocidad() {
        if (nivel > NIVEL_MIN) nivel--;
    }

    public int getNivel() {
        return nivel;
    }

    public int getRetardoMs() {
        return RETARDOS_MS[nivel - 1];
    }

    /** Espera entre pasos según la velocidad actual. */
    public void dormir() throws InterruptedException {
        Thread.sleep(getRetardoMs());
    }
}
