package Algoritmos;

public abstract class AlgoritmoOrdenamiento {

    protected int[] arreglo;
    private int comparaciones;
    private int intercambios;
    private ObservadorPaso observador;

    /** Nombre del algoritmo (para mostrarlo en pantalla). */
    public abstract String getNombre();

    /** Lógica propia de cada algoritmo. */
    protected abstract void ordenar() throws InterruptedException;

    /**
     * Punto de entrada público: prepara todo y ejecuta el ordenamiento.
     * @param datos arreglo a ordenar (se modifica en el mismo arreglo)
     * @param obs   observador que recibe cada paso (puede ser null)
     */
    public void ejecutar(int[] datos, ObservadorPaso obs) throws InterruptedException {
        this.arreglo = datos;
        this.observador = obs;
        this.comparaciones = 0;
        this.intercambios = 0;
        ordenar();
    }

    /** Devuelve true si arreglo[i] > arreglo[j]. Cuenta y avisa. */
    protected boolean comparar(int i, int j) throws InterruptedException {
        comparaciones++;
        notificar(i, j);
        return arreglo[i] > arreglo[j];
    }

    /** Intercambia arreglo[i] y arreglo[j]. Cuenta y avisa. */
    protected void intercambiar(int i, int j) throws InterruptedException {
        int temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
        intercambios++;
        notificar(i, j);
    }

    /** Avisa al observador solo si existe. */
    private void notificar(int i, int j) throws InterruptedException {
        if (observador != null) {
            observador.onPaso(arreglo, i, j);
        }
    }

    public int getComparaciones() {
        return comparaciones;
    }

    public int getIntercambios() {
        return intercambios;
    }
}