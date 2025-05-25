/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VentanaBarras;
/**
 * @author Abraham Farfan/Jordan Contreras
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GeneracionDeBarras extends JFrame {
    int ancho;
    int altura;
    private JLabel splashLabel,statusLabel;
    private JPanel contentPane;
    /**
     * Constructor de generacion de barras
     * @param width ancho de las barras
     * @param height alto de las barras
     */
    public GeneracionDeBarras(int width, int height) {		
        this.altura=height;
        this.ancho=width;
        /*iconoImagen();
        showSplashScreen();*/
        int cantidadBarras = solicitarCantidadBarras();
        initCompenents(cantidadBarras);
    }
    /**
     * Metodo que crea y presenta la ventana principal
     */
    public void initCompenents(int cantidadBarras){   
        //Creacion de la ventana principal
        setTitle("Diagrama de Barras");
        OrdenamientosDeLasBarras diagrama = new OrdenamientosDeLasBarras(ancho, altura, cantidadBarras);
        MenuBarras menuBarras = new MenuBarras(diagrama);
        setJMenuBar(menuBarras);
        add(diagrama);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metodo que presenta el logo del proyecto
     */
    /*public void showSplashScreen(){
        splashLabel = new JLabel(new ImageIcon(getClass().getResource("Barras.png")));
        statusLabel = new JLabel("Proyecto de Estructuras de Datos");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        statusLabel.setForeground(Color.BLACK);
        contentPane = new JPanel(new BorderLayout());
        contentPane.add(splashLabel, BorderLayout.CENTER);
        contentPane.add(statusLabel, BorderLayout.SOUTH);       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(contentPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        try {
            Thread.sleep(2000);//Tiene una duracion de 2 segundos
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        getContentPane().remove(contentPane);
        setVisible(false);       
        setVisible(true);     
    }
    public void iconoImagen(){
        setIconImage(new ImageIcon(getClass().getResource("Barras.png")).getImage());
    }*/
    /**
     * Metodo que me muestra un JOption Pane para que pueda ingresar una x cantidad de barras
     * @return 
     */
    public int solicitarCantidadBarras() {
        int cantidadBarras = 0;
        while (true) {
            try {
                cantidadBarras = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de barras que desea visualizar (entre 4 y 100): "));
                if (cantidadBarras >= 4 && cantidadBarras <= 100) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
            }
        }
        return cantidadBarras;
    }
}
