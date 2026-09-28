package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class PanelControles extends JPanel {

    private static final Color FONDO = new Color(42, 44, 54);
    private static final Color TEXTO = new Color(232, 232, 238);

    private final JButton btnIniciar = new JButton("Iniciar");
    private final JButton btnPausar  = new JButton("Pausar");
    private final JButton btnNuevo   = new JButton("Nuevo arreglo");
    private final JButton btnMenos   = new JButton("- Velocidad");
    private final JButton btnMas     = new JButton("+ Velocidad");
    private final JLabel lblVelocidad = new JLabel();
    // valor inicial 50, mínimo 10, máximo 300, de 10 en 10
    private final JSpinner spnBarras = new JSpinner(new SpinnerNumberModel(50, 10, 300, 10));

    public PanelControles() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
        setBackground(FONDO);
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JLabel lblBarras = new JLabel("Barras:");
        lblBarras.setForeground(TEXTO);
        lblVelocidad.setForeground(TEXTO);
        lblVelocidad.setFont(new Font("SansSerif", Font.BOLD, 12));

        add(btnIniciar);
        add(btnPausar);
        add(btnNuevo);
        add(separador());
        add(lblBarras);
        add(spnBarras);
        add(separador());
        add(btnMenos);
        add(lblVelocidad);
        add(btnMas);

        btnPausar.setEnabled(false);
    }

    public void mostrarVelocidad(int nivel, int maximo, int retardoMs) {
        lblVelocidad.setText("Velocidad " + nivel + "/" + maximo + " (" + retardoMs + " ms/paso)");
    }

    private JSeparator separador() {
        JSeparator s = new JSeparator(SwingConstants.VERTICAL);
        s.setPreferredSize(new Dimension(2, 24));
        return s;
    }

    public JButton getBtnIniciar() { return btnIniciar; }
    public JButton getBtnPausar()  { return btnPausar; }
    public JButton getBtnNuevo()   { return btnNuevo; }
    public JButton getBtnMenos()   { return btnMenos; }
    public JButton getBtnMas()     { return btnMas; }
    public JSpinner getSpnBarras() { return spnBarras; }
}