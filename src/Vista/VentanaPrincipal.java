package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {

    private static final Color FONDO = new Color(30, 31, 38);

    private final List<PanelAlgoritmo> paneles = new ArrayList<>();
    private final PanelControles controles = new PanelControles();

    public VentanaPrincipal(String[] nombresAlgoritmos) {
        super("Algoritmos de Ordenamiento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO);

        JPanel cuadricula = new JPanel(new GridLayout(2, 2, 10, 10));
        cuadricula.setBackground(FONDO);
        cuadricula.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String nombre : nombresAlgoritmos) {
            PanelAlgoritmo panel = new PanelAlgoritmo(nombre);
            paneles.add(panel);
            cuadricula.add(panel);
        }

        add(controles, BorderLayout.NORTH);
        add(cuadricula, BorderLayout.CENTER);

        setSize(1150, 760);
        setLocationRelativeTo(null);
    }

    public PanelControles getControles() {
        return controles;
    }

    public List<PanelAlgoritmo> getPaneles() {
        return paneles;
    }
}