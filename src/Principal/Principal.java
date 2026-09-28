package Principal;

import Control.Controlador;
import javax.swing.SwingUtilities;
import Vista.VentanaPrincipal;

public class Principal {

      public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(Controlador.getNombres());
            new Controlador(ventana);
            ventana.setVisible(true);
        });
    }
}