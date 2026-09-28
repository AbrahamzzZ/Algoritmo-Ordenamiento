package Algoritmos;

public class QuickSort extends AlgoritmoOrdenamiento {

    @Override
    public String getNombre() {
        return "QuickSort";
    }

    @Override
    protected void ordenar() throws InterruptedException {
        quickSort(0, arreglo.length - 1);
    }

    private void quickSort(int bajo, int alto) throws InterruptedException {
        if (bajo < alto) {
            int posPivote = particionar(bajo, alto);
            quickSort(bajo, posPivote - 1);   // mitad izquierda
            quickSort(posPivote + 1, alto);   // mitad derecha
        }
    }

    /** Coloca el pivote en su posición final y devuelve ese índice. */
    private int particionar(int bajo, int alto) throws InterruptedException {
        int pivote = alto;     // índice del pivote
        int i = bajo - 1;      // frontera de los elementos menores al pivote

        for (int j = bajo; j < alto; j++) {
            if (comparar(pivote, j)) {        // arreglo[pivote] > arreglo[j]
                i++;
                if (i != j) {
                    intercambiar(i, j);
                }
            }
        }

        // Llevar el pivote a su posición final
        if (i + 1 != alto) {
            intercambiar(i + 1, alto);
        }
        return i + 1;
    }
}
