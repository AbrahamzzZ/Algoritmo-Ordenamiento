package Algoritmos;

public class Insercion extends AlgoritmoOrdenamiento {

    @Override
    public String getNombre() {
        return "Inserción";
    }

    @Override
    protected void ordenar() throws InterruptedException {
        int n = arreglo.length;

        for (int i = 1; i < n; i++) {
            int j = i;

            // Mientras el de la izquierda sea mayor, se intercambian
            while (j > 0 && comparar(j - 1, j)) {  // arreglo[j-1] > arreglo[j]
                intercambiar(j - 1, j);
                j--;
            }
        }
    }
}
