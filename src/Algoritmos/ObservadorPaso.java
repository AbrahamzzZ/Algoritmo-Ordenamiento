package Algoritmos;

@FunctionalInterface
public interface ObservadorPaso {
    void onPaso(int[] arreglo, int i, int j) throws InterruptedException;
}