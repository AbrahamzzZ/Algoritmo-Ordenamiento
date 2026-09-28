package Control;

import Algoritmos.AlgoritmoOrdenamiento;
import Algoritmos.Burbuja;
import Algoritmos.Insercion;
import Algoritmos.QuickSort;
import Algoritmos.Seleccion;
import Vista.PanelAlgoritmo;
import Vista.PanelControles;
import Vista.VentanaPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Controlador {

    /** Fábricas: cada ejecución necesita instancias nuevas (los algoritmos guardan estado). */
    private static final List<Supplier<AlgoritmoOrdenamiento>> FABRICAS = List.of(Burbuja::new, Seleccion::new, Insercion::new, QuickSort::new);
    private final VentanaPrincipal ventana;
    private final PanelControles controles;
    private final ControlEjecucion control = new ControlEjecucion();
    private final Cronometro cronometro = new Cronometro();
    private final Timer reloj;                       // refresca métricas cada 50 ms
    private final List<Thread> hilos = new ArrayList<>();
    private final AtomicInteger terminados = new AtomicInteger();

    private AlgoritmoOrdenamiento[] algoritmos;
    private double[] tiempoFinal;                    // -1 = todavía ordenando
    private int[] datosBase;
    private boolean ejecutando = false;

    public Controlador(VentanaPrincipal ventana) {
        this.ventana = ventana;
        this.controles = ventana.getControles();
        this.reloj = new Timer(50, e -> refrescarMetricas());

        // Eventos de los botones
        controles.getBtnIniciar().addActionListener(e -> iniciar());
        controles.getBtnPausar().addActionListener(e -> alternarPausa());
        controles.getBtnNuevo().addActionListener(e -> nuevoArreglo());
        controles.getBtnMas().addActionListener(e -> {
            control.aumentarVelocidad();
            mostrarVelocidad();
        });
        controles.getBtnMenos().addActionListener(e -> {
            control.disminuirVelocidad();
            mostrarVelocidad();
        });
        controles.getSpnBarras().addChangeListener(e -> {
            if (!ejecutando) nuevoArreglo();
        });

        mostrarVelocidad();
        nuevoArreglo();
    }

    /** Nombres para construir la ventana (la vista no conoce los algoritmos). */
    public static String[] getNombres() {
        String[] nombres = new String[FABRICAS.size()];
        for (int k = 0; k < nombres.length; k++) {
            nombres[k] = FABRICAS.get(k).get().getNombre();
        }
        return nombres;
    }

    private void iniciar() {
        if (ejecutando) return;
        detenerHilos();

        List<PanelAlgoritmo> paneles = ventana.getPaneles();
        int n = FABRICAS.size();
        algoritmos = new AlgoritmoOrdenamiento[n];
        tiempoFinal = new double[n];
        terminados.set(0);

        for (int k = 0; k < n; k++) {
            paneles.get(k).mostrarTiempoReal(Rendimiento.medirMs(FABRICAS.get(k), datosBase, 7));
        }

        ejecutando = true;
        actualizarBotones();
        cronometro.iniciar();

        for (int k = 0; k < n; k++) {
            final int idx = k;
            final PanelAlgoritmo panel = paneles.get(k);
            final AlgoritmoOrdenamiento alg = FABRICAS.get(k).get();
            final int[] copia = datosBase.clone();
            algoritmos[k] = alg;
            tiempoFinal[k] = -1;
            panel.getBarras().setTerminado(false);
            panel.setEstado("Ordenando...");

            Thread hilo = new Thread(() -> {
                try {
                    // Observador: dibujar -> esperar si hay pausa -> dormir según velocidad
                    alg.ejecutar(copia, (arreglo, i, j) -> {
                        panel.getBarras().actualizar(arreglo.clone(), i, j);
                        control.esperarSiPausado();
                        control.dormir();
                    });
                    tiempoFinal[idx] = cronometro.getSegundos();
                    int puesto = terminados.incrementAndGet();
                    SwingUtilities.invokeLater(() -> alTerminar(panel, puesto));
                } catch (InterruptedException ex) {
                    // Se presionó "Nuevo arreglo": el hilo termina sin hacer nada
                }
            });
            hilo.setDaemon(true);          // no impide cerrar la aplicación
            hilos.add(hilo);
        }

        for (Thread h : hilos) h.start();  // arrancan todos juntos
        reloj.start();
    }

    private void alternarPausa() {
        if (!ejecutando) return;
        if (control.isPausado()) {
            control.continuar();
            cronometro.reanudar();
            controles.getBtnPausar().setText("Pausar");
        } else {
            control.pausar();
            cronometro.pausar();
            controles.getBtnPausar().setText("Continuar");
        }
    }

    private void nuevoArreglo() {
        detenerHilos();
        int cantidad = (Integer) controles.getSpnBarras().getValue();
        datosBase = generarAleatorio(cantidad);

        for (PanelAlgoritmo panel : ventana.getPaneles()) {
            panel.getBarras().setTerminado(false);
            panel.getBarras().actualizar(datosBase.clone(), -1, -1);
            panel.mostrarMetricas(0, 0, 0);
            panel.mostrarTiempoReal(-1);
            panel.setEstado("Listo");
        }
    }

    private void alTerminar(PanelAlgoritmo panel, int puesto) {
        panel.getBarras().setTerminado(true);
        panel.setEstado("Terminado #" + puesto);
        refrescarMetricas();

        if (puesto == FABRICAS.size()) {   // terminaron todos
            ejecutando = false;
            reloj.stop();
            actualizarBotones();
        }
    }

    /** Lo llama el Timer (en el hilo de Swing) para actualizar los textos. */
    private void refrescarMetricas() {
        if (algoritmos == null) return;
        double ahora = cronometro.getSegundos();
        List<PanelAlgoritmo> paneles = ventana.getPaneles();

        for (int k = 0; k < algoritmos.length; k++) {
            AlgoritmoOrdenamiento alg = algoritmos[k];
            double t = tiempoFinal[k] >= 0 ? tiempoFinal[k] : ahora;
            paneles.get(k).mostrarMetricas(t, alg.getComparaciones(), alg.getIntercambios());
        }
    }

    /** Detiene los hilos en curso (por ejemplo al generar un nuevo arreglo). */
    private void detenerHilos() {
        control.continuar();                 // por si estaban en pausa
        for (Thread h : hilos) h.interrupt();
        for (Thread h : hilos) {
            try {
                h.join(500);
            } catch (InterruptedException ignorado) { }
        }
        hilos.clear();
        reloj.stop();
        algoritmos = null;
        ejecutando = false;
        controles.getBtnPausar().setText("Pausar");
        actualizarBotones();
    }

    private void actualizarBotones() {
        controles.getBtnIniciar().setEnabled(!ejecutando);
        controles.getBtnPausar().setEnabled(ejecutando);
        controles.getSpnBarras().setEnabled(!ejecutando);
    }

    private void mostrarVelocidad() {
        controles.mostrarVelocidad(control.getNivel(), ControlEjecucion.NIVEL_MAX, control.getRetardoMs());
    }

    /** Genera los números 1..n desordenados (sin repetidos). */
    private static int[] generarAleatorio(int n) {
        int[] v = new int[n];
        for (int i = 0; i < n; i++) v[i] = i + 1;
        Random random = new Random();
        for (int i = n - 1; i > 0; i--) {    // mezcla de Fisher-Yates
            int j = random.nextInt(i + 1);
            int temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
        return v;
    }
}
