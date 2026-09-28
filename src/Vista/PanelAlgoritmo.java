package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAlgoritmo extends JPanel {

    private static final Color FONDO = new Color(42, 44, 54);
    private static final Color TEXTO = new Color(232, 232, 238);
    private static final Color TEXTO_ESTADO = new Color(245, 197, 66);

    private final PanelBarras barras = new PanelBarras();
    private final JLabel lblTitulo = new JLabel();
    private final JLabel lblEstado = new JLabel("Listo");
    private final JLabel lblTiempo = new JLabel("Tiempo animación: 0.00 s");
    private final JLabel lblTiempoReal = new JLabel("Tiempo real: -");
    private final JLabel lblComparaciones = new JLabel("Comparaciones: 0");
    private final JLabel lblIntercambios = new JLabel("Intercambios: 0");

    public PanelAlgoritmo(String nombre) {
        setLayout(new BorderLayout(0, 8));
        setBackground(FONDO);
        setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        // Cabecera: nombre a la izquierda, estado a la derecha
        lblTitulo.setText(nombre);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(TEXTO);
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstado.setForeground(TEXTO_ESTADO);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        cabecera.add(lblTitulo, BorderLayout.WEST);
        cabecera.add(lblEstado, BorderLayout.EAST);

        // Métricas en 2 filas x 2 columnas
        JPanel panelMetricas = new JPanel(new GridLayout(2, 2, 10, 2));
        panelMetricas.setOpaque(false);
        for (JLabel lbl : new JLabel[]{lblTiempo, lblComparaciones, lblTiempoReal, lblIntercambios}) {
            lbl.setForeground(TEXTO);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
            panelMetricas.add(lbl);
        }

        add(cabecera, BorderLayout.NORTH);
        add(barras, BorderLayout.CENTER);
        add(panelMetricas, BorderLayout.SOUTH);
    }

    public PanelBarras getBarras() {
        return barras;
    }

    /** Tiempo de la animación y contadores (se actualiza en vivo). */
    public void mostrarMetricas(double segundos, int comparaciones, int intercambios) {
        lblTiempo.setText(String.format("Tiempo animación: %.2f s", segundos));
        lblComparaciones.setText(String.format("Comparaciones: %,d", comparaciones));
        lblIntercambios.setText(String.format("Intercambios: %,d", intercambios));
    }

    /** Tiempo real del algoritmo sin animación (-1 = sin medir). */
    public void mostrarTiempoReal(double ms) {
        lblTiempoReal.setText(ms < 0 ? "Tiempo real: -" : String.format("Tiempo real: %.3f ms", ms));
    }

    public void setEstado(String texto) {
        lblEstado.setText(texto);
    }
}