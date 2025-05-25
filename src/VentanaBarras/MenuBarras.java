/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VentanaBarras;
/**
 *@author Abraham Farfan/Jordan Contreras
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
public class MenuBarras extends JMenuBar{
    public MenuBarras(OrdenamientosDeLasBarras diagrama) {
        //Menu del programa
        JMenu sortMenu = new JMenu("Agoritmo");
        JMenuItem ordenarBurbujaItem = new JMenuItem("Ordenar por Burbuja");
        ordenarBurbujaItem.setToolTipText("El ordenamiento por burbuja funciona comparando pares de elementos adyacentes y intercambiándolos si están en el orden incorrecto.");
        JMenuItem ordenarSeleccionItem = new JMenuItem("Ordenar por Selección");
        ordenarSeleccionItem.setToolTipText("El ordenamiento por seleccion encuentra el elemento mas pequeño en cada iteracion y lo coloca en su posicion correcta.");
        JMenuItem ordenarInsercionItem = new JMenuItem("Ordenar por Inserción");
        ordenarInsercionItem.setToolTipText("El ordenamiento por insercion se basa en la idea de construrir una lista ordenada a medidad que se recorre la lista original.");
        JMenuItem quickSortItem = new JMenuItem("Ordenar QuickSort");
        quickSortItem.setToolTipText("El ordenamienro quickSort selecciona un elemento pivote de la lista y particionar los elementos restantes de dos sublistas, una con valor menores que el  pivote y otra con valores mayores.");
        JMenuItem desordenarItem = new JMenuItem("Desordenar",new ImageIcon(getClass().getResource("barrasdesordenadas.jpg")));
        desordenarItem.setToolTipText("Permite desordenar las barras de manera aleatoria");
        JMenuItem pausaItem= new JMenuItem("Pausa",new ImageIcon(getClass().getResource("pausa.png")));
        pausaItem.setToolTipText("Permite cambiar el intervalo de tiempo de las barras");
        JMenu controlMenu = new JMenu("Control");
        JMenu ayuda = new JMenu("Ayuda");
        JMenuItem acercaDelPrograma = new JMenuItem("Acerca de ...",new ImageIcon(getClass().getResource("Ayuda.png")));
        acercaDelPrograma.setToolTipText("Da informacion sobre el programa");        
        JMenu rendimiento= new JMenu("Rendimiento");
        JMenuItem rendimientoItem= new JMenuItem("Rendimiento de ordenamientos");
        rendimientoItem.setToolTipText("Permite ver el rendimiento de los 4 ordenamientos.");
       //Evento del ordenamiento por burbuja
        ordenarBurbujaItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        if (!diagrama.isSorting && !diagrama.pausas) {
                            diagrama.setSorting(true);
                            diagrama.ordenarBurbuja();
                            diagrama.setSorting(false);
                        }
                        return null;
                    }
                };
               worker.execute();
            }
        });
        //Evento del ordenamiento por seleccion 
        ordenarSeleccionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        if (!diagrama.isSorting && !diagrama.pausas) {
                            diagrama.setSorting(true);
                            diagrama.ordenarSeleccion();
                            diagrama.setSorting(false);
                        }
                        return null;
                    }
                };
               worker.execute();
            }
        });
        //Evento del ordenamiento por inserccion 
        ordenarInsercionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        if (!diagrama.isSorting && !diagrama.pausas) {
                            diagrama.setSorting(true);
                            diagrama.ordenarInsercion();
                            diagrama.setSorting(false);
                        }
                        return null;
                    }
                };
               worker.execute();
            }
        });
        //Evento del ordenamiento por Quicksort
        quickSortItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws Exception {
                        if (!diagrama.isSorting && !diagrama.pausas) {
                            diagrama.setSorting(true);
                            diagrama.quickSort();
                            diagrama.setSorting(false);
                        }
                        return null;
                    }
                };
               worker.execute();
            }
        });
        //Evento que desordena las barras
        desordenarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diagrama.desordenarBarra();
            }
        });
        
        desordenarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diagrama.desordenarBarra();
            }
        });
        //Evento que aumenta o disminuye el movimiento de las barras
        pausaItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	
                diagrama.ventanaPausa();
            }
        });
        rendimientoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	
                diagrama.velocidadDeMovimientoDeBarras();
            }
        });
        
        //Informacion del programa y de los autores
        acercaDelPrograma.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){    
                String[]options={"Informacion del programa","Mostrar autores","Salir"};
                int opciones = JOptionPane.showOptionDialog(null,"Desea ver la informacion de los autores?","Informacion sobre los autores",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                if(opciones==0){
                    diagrama.mostrarInformacionDelPrograma();
                }else
                    if(opciones==1){
                        diagrama.mostrarAutores();
                    }
            }
        });
        
        sortMenu.add(ordenarBurbujaItem);
        sortMenu.addSeparator();
        sortMenu.add(ordenarSeleccionItem);
        sortMenu.addSeparator();
        sortMenu.add(ordenarInsercionItem);
        sortMenu.addSeparator();
        sortMenu.add(quickSortItem);
        add(sortMenu);
        controlMenu.add(desordenarItem);
        controlMenu.addSeparator();
        controlMenu.add(pausaItem);
        add(controlMenu);      
        ayuda.add(acercaDelPrograma);
        rendimiento.add(rendimientoItem);
        add(rendimiento);
        add(ayuda);
    }
}
