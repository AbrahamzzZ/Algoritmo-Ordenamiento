package Algoritmos;

public class Burbuja extends AlgoritmoOrdenamiento {

    @Override
    public String getNombre() {
        return "Burbuja";
    }

    @Override
    protected void ordenar() throws InterruptedException {
        int n = arreglo.length;

        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;

            // Los últimos i elementos ya están en su lugar
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparar(j, j + 1)) {
                    intercambiar(j, j + 1);
                    huboIntercambio = true;
                }
            }

            // Si en una pasada no hubo cambios, ya está ordenado
            if (!huboIntercambio) {
                break;
            }
        }
    }
}