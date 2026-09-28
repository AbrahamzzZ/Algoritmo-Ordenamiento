package Algoritmos;

public class Seleccion extends AlgoritmoOrdenamiento {

    @Override
    public String getNombre() {
        return "Selección";
    }

    @Override
    protected void ordenar() throws InterruptedException {
        int n = arreglo.length;

        for (int i = 0; i < n - 1; i++) {
            int indiceMenor = i;

            // Buscar el menor en la parte desordenada [i+1 .. n-1]
            for (int j = i + 1; j < n; j++) {
                if (comparar(indiceMenor, j)) {   // arreglo[indiceMenor] > arreglo[j]
                    indiceMenor = j;
                }
            }

            // Solo intercambia si el menor no está ya en su lugar
            if (indiceMenor != i) {
                intercambiar(i, indiceMenor);
            }
        }
    }
}
