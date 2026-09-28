package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelBarras extends JPanel {

    // Colores
    private static final Color FONDO      = new Color(30, 31, 38);
    private static final Color NORMAL     = new Color(79, 142, 247);   // azul
    private static final Color MARCA_I    = new Color(245, 197, 66);   // amarillo
    private static final Color MARCA_J    = new Color(242, 85, 90);    // rojo
    private static final Color TERMINADO  = new Color(61, 214, 140);   // verde

    // Estado que se dibuja
    private int[] datos = new int[0];
    private int marcaI = -1;
    private int marcaJ = -1;
    private boolean terminado = false;

    public PanelBarras() {
        setBackground(FONDO);
        setPreferredSize(new Dimension(400, 220));
    }

    /**
     * Actualiza lo que se dibuja y pide repintar.
     * Es synchronized porque más adelante se llamará desde otro hilo.
     * @param nuevosDatos arreglo a dibujar
     * @param i índice a resaltar en amarillo (-1 = ninguno)
     * @param j índice a resaltar en rojo (-1 = ninguno)
     */
    public synchronized void actualizar(int[] nuevosDatos, int i, int j) {
        this.datos = nuevosDatos;
        this.marcaI = i;
        this.marcaJ = j;
        repaint();
    }

    /** Pinta todas las barras de verde cuando el algoritmo termina. */
    public synchronized void setTerminado(boolean terminado) {
        this.terminado = terminado;
        this.marcaI = -1;
        this.marcaJ = -1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Copia local del estado para no leerlo a medias
        int[] d;
        int mi, mj;
        boolean fin;
        synchronized (this) {
            d = datos;
            mi = marcaI;
            mj = marcaJ;
            fin = terminado;
        }
        if (d.length == 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // Valor máximo para escalar las alturas
        int maximo = 1;
        for (int valor : d) {
            maximo = Math.max(maximo, valor);
        }

        double anchoBarra = (double) ancho / d.length;
        int espacio = anchoBarra > 4 ? 1 : 0;   // separación solo si hay espacio

        for (int k = 0; k < d.length; k++) {
            int altura = (int) ((double) d[k] / maximo * (alto - 5));
            int x = (int) (k * anchoBarra);
            int w = Math.max(1, (int) ((k + 1) * anchoBarra) - x - espacio);
            int y = alto - altura;

            if (fin) {
                g2.setColor(TERMINADO);
            } else if (k == mi) {
                g2.setColor(MARCA_I);
            } else if (k == mj) {
                g2.setColor(MARCA_J);
            } else {
                g2.setColor(NORMAL);
            }
            g2.fillRect(x, y, w, altura);
        }
    }
}