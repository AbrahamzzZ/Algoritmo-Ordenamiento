package VentanaBarras;
/**
 * @author Abraham Farfan/Jordan Contreras
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class OrdenamientosDeLasBarras extends JPanel{
    private int anchoBarras ;
    private int alturaBarras = 600;//Alturas de las barras
    private int cantidadBarras;//Cantidad de las barras
    private int animacion = 50;
    private int[] alturaDeBarras;
    private Color[] colorBarras;//El color es aleatorio 
    protected boolean isSorting;
    protected boolean pausas;	    
    protected int currentIndex;
    private int animationSpeed;
    private String [] numeroAltura;
    public int edad = 34;
    /**
     * Constructor de la clase 
     * @param width
     * @param height 
     */
    public OrdenamientosDeLasBarras(int width, int height, int cantidadBarras) {
        this.cantidadBarras = cantidadBarras;
	alturaDeBarras = new int[cantidadBarras];
        colorBarras = new Color[cantidadBarras];
        isSorting = false;
        pausas = false;
        animationSpeed = 1;
        setPreferredSize(new Dimension(width, height));
        generarBarras();
        calcularAnchoBarra();
    }
   /**
    * Metodo que genera el ancho de las barras
    */
    private void calcularAnchoBarra() {
        int availableWidth = getWidth();
        anchoBarras = availableWidth / cantidadBarras;
    }
    /**
     * Metodo que genera los colores de las barras de forma aleatoria
     */
    private void generarBarras() {
        Random random = new Random();
        numeroAltura = new String[cantidadBarras]; // Inicializar el arreglo de numeroAltura
        for (int i = 0; i < cantidadBarras; i++) {
            alturaDeBarras[i] = random.nextInt(alturaBarras) + 1;
            colorBarras[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            numeroAltura[i] = " " + (i + 1); // Generar la etiqueta de la barra
        }
    }
    //Metodo que visualiza las barras graficamente
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calcularAnchoBarra();
        int x = 0;
        int y = getHeight() - alturaBarras;
        for (int i = 0; i < cantidadBarras; i++) {
            g.setColor(colorBarras[i]);
            g.fillRect(x, y + alturaBarras - alturaDeBarras[i], anchoBarras, alturaDeBarras[i]);
             g.drawString(numeroAltura[i], x + 5, y + alturaBarras - alturaDeBarras[i] - 5);
            x += anchoBarras;
        }
    }
    /**
     * Metodo que intercambia las barras
     * @param i
     * @param j 
     */
    private void intercambioDeBarras(int i, int j) {
        int tempHeight = alturaDeBarras[i];
        Color tempColor = colorBarras[i];

        alturaDeBarras[i] = alturaDeBarras[j];
        colorBarras[i] = colorBarras[j];

        alturaDeBarras[j] = tempHeight;
        colorBarras[j] = tempColor;
    }
    /**
     * Metodo que genera el ordenamiento burbuja de forma iterativa
     */
    public void ordenarBurbuja() {
        for (int i = 0; i < cantidadBarras - 1; i++) {
            for (int j = 0; j < cantidadBarras - i - 1; j++) {
                if (alturaDeBarras[j] > alturaDeBarras[j + 1]) {
                    intercambioDeBarras(j, j + 1);
                    repaint();
                    delay();
                }
            }
        }
    }
    /**
     * Metodo que genera el ordenamiento seleccion de forma iterativa
     */
    public void ordenarSeleccion() {
        for (int i = 0; i < cantidadBarras - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < cantidadBarras; j++) {
                if (alturaDeBarras[j] < alturaDeBarras[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                intercambioDeBarras(i, minIndex);
                repaint();
                delay();
            }
        }
    }
    public void ordenarInsercion(int n) {
        if (n <= 1) {
            return;
        }

        ordenarInsercion(n - 1);

        int key = alturaDeBarras[n - 1];
        Color keyColor = colorBarras[n - 1];
        int j = n - 2;

        while (j >= 0 && alturaDeBarras[j] > key) {
            alturaDeBarras[j + 1] = alturaDeBarras[j];
            colorBarras[j + 1] = colorBarras[j];
            j--;
        }

        alturaDeBarras[j + 1] = key;
        colorBarras[j + 1] = keyColor;
        repaint();
        delay();
    }
    public void ordenarInsercion() {
        ordenarInsercion(cantidadBarras);
    }
    
    public void delay() {
        try {
            Thread.sleep(animacion / animationSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo que desordena las barras
     */
    public void desordenarBarra() {
        Random random = new Random();
        for (int i = 0; i < cantidadBarras; i++) {
            int randomIndex = random.nextInt(cantidadBarras);
            intercambioDeBarras(i, randomIndex);
        }
        repaint();
    }
    /**
     * Metodo que genera el ordenamiento quicksort de forma recursiva
     * @param low
     * @param high 
     */
    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }
    //Particion 
    private int partition(int low, int high) {
        int pivot = alturaDeBarras[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (alturaDeBarras[j] < pivot) {
                i++;
                intercambioDeBarras(i, j);
                repaint();
                delay();
            }
        }
        intercambioDeBarras(i + 1, high);
        repaint();
        delay();
        return i + 1;
    }
    public void quickSort() {
        quickSort(0, cantidadBarras - 1);
    }
    /**
     * Metodo que aumenta o disminuye el movimiento de las barras
     */       
    public void ventanaPausa() {
        JFrame frame = new JFrame("Control de Animación");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel velocidadLabel = new JLabel("Velocidad actual: " + animationSpeed);
        JTextField velocidadTextField = new JTextField(String.valueOf(animationSpeed));
        JLabel subirLabel = new JLabel("Subir velocidad: ");
        JLabel bajarLabel = new JLabel("Bajar velocidad: ");
        JLabel pausar = new JLabel("Pausar movimiento");
        JButton subirButton = new JButton("+");
        JButton bajarButton = new JButton("-");
        JButton pausaButton = new JButton("Pausar");
        JButton aceptarButton = new JButton("Aceptar");
        JButton salirButton = new JButton("Salir");

        panel.add(velocidadLabel);
        panel.add(velocidadTextField);
        panel.add(subirLabel);
        panel.add(subirButton);
        panel.add(bajarLabel);
        panel.add(bajarButton);
        panel.add(pausar);
        panel.add(pausaButton);
        panel.add(aceptarButton);
        panel.add(salirButton);

        frame.add(panel, BorderLayout.CENTER);

        // Agregar ActionListener a los botones
        subirButton.addActionListener(e -> {
            int cantidad = Integer.parseInt(velocidadTextField.getText());
            cantidad += 1;
            velocidadTextField.setText(String.valueOf(cantidad));
            setAnimationSpeed(cantidad);
        });

        bajarButton.addActionListener(e -> {
            int cantidad = Integer.parseInt(velocidadTextField.getText());
            if (cantidad > 1) {
                cantidad -= 1;
                velocidadTextField.setText(String.valueOf(cantidad));
                setAnimationSpeed(cantidad);
            }
        });

        pausaButton.addActionListener(e -> {
            if (pausas) {
                pausas = false;
                repaint();
            } else {
                pausas = true;
                // Aquí debes agregar la lógica para pausar la animación
            }
        });

        aceptarButton.addActionListener(e -> {
            int cantidad = Integer.parseInt(velocidadTextField.getText());
            setAnimationSpeed(cantidad);
            frame.dispose();
        });

        salirButton.addActionListener(e -> {
            setAnimationSpeed(1);
            frame.dispose();
        });

        frame.setVisible(true);
    } 
   /**
    * Metodo que muestra el rendimiento de cada ordenamiento
    */
   public void velocidadDeMovimientoDeBarras() {
        long startTime, endTime;
        long [] tiempos = new long[4];
        long [] memoriasInicio = new long[4];
        long [] memoriasFin = new long[4];
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Llamar al recolector de basura para liberar memoria no utilizada
        long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
        
        // Ordenamiento de burbuja
        generarBarras(); // Restaurar el arreglo original
        memoriasInicio[0]=runtime.totalMemory()-runtime.freeMemory();
        startTime = System.nanoTime();
        ordenarBurbuja();
        endTime = System.nanoTime();
        tiempos[0] = endTime - startTime;
        memoriasFin[0]= runtime.totalMemory() - runtime.freeMemory();
        tiempos[0] = endTime - startTime;

        // Ordenamiento de selección
        generarBarras(); // Restaurar el arreglo original
        memoriasInicio[0]=runtime.totalMemory()-runtime.freeMemory();
        startTime = System.nanoTime();
        ordenarSeleccion();
        endTime = System.nanoTime();
        tiempos[1] = endTime - startTime;
        memoriasFin[0]= runtime.totalMemory() - runtime.freeMemory();
        tiempos[0] = endTime - startTime;

        // Ordenamiento de inserción
        generarBarras(); // Restaurar el arreglo original
        memoriasInicio[0]=runtime.totalMemory()-runtime.freeMemory();
        startTime = System.nanoTime();
        ordenarInsercion();
        endTime = System.nanoTime();
        tiempos[2] = endTime - startTime;
        memoriasFin[0]= runtime.totalMemory() - runtime.freeMemory();
        tiempos[0] = endTime - startTime;

        // QuickSort
        generarBarras(); // Restaurar el arreglo original
        memoriasInicio[0]=runtime.totalMemory()-runtime.freeMemory();
        startTime = System.nanoTime();
        quickSort();
        endTime = System.nanoTime();
        tiempos[3] = endTime - startTime;
        memoriasFin[0]= runtime.totalMemory() - runtime.freeMemory();
        tiempos[0] = endTime - startTime;
        
        long memoriaUsada;
        String[] ordenamientos = { "Burbuja", "Selección", "Inserción", "QuickSort" };
        StringBuilder mensaje = new StringBuilder();
        for (int i = 0; i < ordenamientos.length; i++) {
            memoriaUsada = memoriasFin[i] - memoriasInicio[i];
            mensaje.append(ordenamientos[i]).append(": ").append(tiempos[i] / 1_000_000).append(" milisegundos, ");
            mensaje.append("Memoria utilizada: ").append(memoriaUsada / 1024).append(" KB\n");
        }
        /*
        // Encontrar el ordenamiento más eficiente
        String[] ordenamientos = { "Burbuja", "Selección", "Inserción", "QuickSort" };
        long minTiempo = tiempos[0];
        int indiceMinTiempo = 0;
        for (int i = 1; i < tiempos.length; i++) {
            if (tiempos[i] < minTiempo) {
                minTiempo = tiempos[i];
                indiceMinTiempo = i;
            }
        }
        
        // Mostrar los tiempos de cada ordenamiento
        StringBuilder mensaje = new StringBuilder();
        for (int i = 0; i < tiempos.length; i++) {
            mensaje.append(ordenamientos[i]).append(": ").append(tiempos[i] / 1_000_000).append(" milisegundos\n");
        }*/
        // Mostrar el ordenamiento más eficiente
        //mensaje.append("\nEl ordenamiento más eficiente es: ").append(ordenamientos[indiceMinTiempo]);       
        //JOptionPane.showMessageDialog(null, mensaje.toString(), "Eficiencia de los ordenamientos", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Eficiencia de los ordenamientos", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Metodo que muestra la informacion del programa 
     */
    public void mostrarInformacionDelPrograma(){
        JOptionPane.showMessageDialog(null,"Este programa fue creado con la finalidad de poner en practicar todo lo que hemos visto durante este primer parcial.\nEl programa esta compuesto por 4 ordenamientos que son los siguientes: \n1)Ordenamiento Burbuja \n2)Ordenamiento Seleccion \n3)Ordenamiento Inserccion \n4)Ordenamiento Quicksort"+"","Informacion sobre el programa",JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Metodo que muestra la informacion de los autores
     */
    public void mostrarAutores(){
        JOptionPane.showMessageDialog(null,"Creado por: Abraham Andres Farfan Sanchez\n Curso: Software 3-2\n Materia: Estructura de datos\n\n Creado por: Jordan Alexis Contreras Suarez\n Curso: Software 3-2\n Materia: Estructura de datos"+"", "Informacion sobre los autores", JOptionPane.INFORMATION_MESSAGE);
    }
    //Metodos accesores Set
    public void setSorting(boolean sorting) {
        isSorting = sorting;
    }

    public void setPaused(boolean paused) {
        pausas = paused;
    }

    public void setAnimationSpeed(int speed) {
        animationSpeed = speed;
    }
}
