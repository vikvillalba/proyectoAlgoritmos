/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frames;

import ArbolDeExpansionMinima.Boruvka;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Panel que muestra gráficamente el procesamiento del algoritmo de Borůvka
 * @author erika
 */
public class PnlBoruvka extends javax.swing.JPanel {
    
    private Grafo grafo; //Grafo a visualizar
    private Map<String, Point> posiciones; // coordenadas de cada localidad
    private Set<String> aristasMST = new HashSet<>(); //aristas del mst
    private Map<String, String> componentes = new HashMap<>(); // Para mostrar las componentes
    private boolean procesoCompleto = false;
    private double pesoTotal = 0;
    private int pasoActual = 0;
    /**
     * Creates new form PnlBoruvka
     */
    public PnlBoruvka() {
        initComponents();
        
        grafo = GrafoPueblaUtil.getGrafo();
        posiciones = new HashMap<>();
        setBackground(Color.WHITE);
        inicializarPosiciones();
        inicializarComponentes();

        // Iniciar el algoritmo en un hilo separado
        new Thread(() -> {
            try {
                Thread.sleep(500); //pausa para que se vea la ejecucion
                Boruvka.setPanelVisualizacion(this);
                Boruvka.mostrarMST();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    /**
     * Agrega al mapa de posiciones las coordenadas de cada localidad.
     */
    private void inicializarPosiciones() {
        double escala = 2;

        posiciones.put("Acajete", new Point((int) (345 * escala), 414));
        posiciones.put("Acajete", new Point((int) (345 * escala), 444));
        posiciones.put("Acatlán", new Point((int) (307 * escala), 640));
        posiciones.put("Acatzingo", new Point((int) (394 * escala), 465));
        posiciones.put("Ajalpan", new Point((int) (580 * escala), 650));
        posiciones.put("Amozoc", new Point((int) (300 * escala), 460));
        posiciones.put("Atlixco", new Point((int) (150 * escala), 520));
        posiciones.put("Chalchicomula de Sesma", new Point((int) (495 * escala), 475));
        posiciones.put("Chignahuapan", new Point((int) (285 * escala), 230));
        posiciones.put("Chignautla", new Point((int) (470 * escala), 260));
        posiciones.put("Chietla", new Point((int) (140 * escala), 610));
        posiciones.put("Coronango", new Point((int) (260 * escala), 290));
        posiciones.put("Cuautlancingo", new Point((int) (250 * escala), 390));
        posiciones.put("Cuetzalan del Progreso", new Point((int) (470 * escala), 140));
        posiciones.put("Huejotzingo", new Point((int) (170 * escala), 460));
        posiciones.put("Hueytamalco", new Point((int) (545 * escala), 150));
        posiciones.put("Huauchinango", new Point((int) (260 * escala), 129));
        posiciones.put("Izúcar de Matamoros", new Point((int) (195 * escala), 675));
        posiciones.put("Libres", new Point((int) (420 * escala), 335));
        posiciones.put("Nopalucan", new Point((int) (395 * escala), 420));
        posiciones.put("Palmar de Bravo", new Point((int) (470 * escala), 520));
        posiciones.put("Puebla", new Point((int) (270 * escala), 560));
        posiciones.put("Quecholac", new Point((int) (430 * escala), 490));
        posiciones.put("San Andrés Cholula", new Point((int) (200 * escala), 450));
        posiciones.put("San Martín Texmelucan", new Point((int) (200 * escala), 350));
        posiciones.put("San Pedro Cholula", new Point((int) (225 * escala), 425));
        posiciones.put("San Salvador el Seco", new Point((int) (440 * escala), 400));
        posiciones.put("San Salvador el Verde", new Point((int) (136 * escala), 406));
        posiciones.put("Tecamachalco", new Point((int) (390 * escala), 554));
        posiciones.put("Tehuacán", new Point((int) (500 * escala), 630));
        posiciones.put("Tepeaca", new Point((int) (355 * escala), 520));
        posiciones.put("Teziutlán", new Point((int) (510 * escala), 220));
        posiciones.put("Tlachichuca", new Point((int) (515 * escala), 435));
        posiciones.put("Tlacotepec de Benito Juárez", new Point((int) (435 * escala), 570));
        posiciones.put("Tlahuapan", new Point((int) (150 * escala), 300));
        posiciones.put("Tlatlauquitepec", new Point((int) (330 * escala), 70));
        posiciones.put("Venustiano Carranza", new Point((int) (420 * escala), 30));
        posiciones.put("Xiutetelco", new Point((int) (515 * escala), 260));
        posiciones.put("Xicotepec", new Point((int) (225 * escala), 570));
        posiciones.put("Zacapoaxtla", new Point((int) (450 * escala), 220));
        posiciones.put("Zacatlán", new Point((int) (330 * escala), 190));
    }
    
    private void inicializarComponentes() {
        for (String municipio : GrafoPueblaUtil.getMunicipios()) {
            componentes.put(municipio, municipio); // Cada nodo es su propio componente inicialmente
        }
    }
    /**
     * Método llamado por el algoritmo para agregar una arista al MST
     * @param origen Nodo origen
     * @param destino Nodo destino
     * @param peso Peso de la arista
     */
     public void agregarAristaMST(String origen, String destino, double peso) {
        String key = origen.compareTo(destino) < 0 ? origen + "-" + destino : destino + "-" + origen;
        aristasMST.add(key);
        pesoTotal += peso;
        
        // Actualizar componentes conectadas
        String raizOrigen = encontrarRaiz(origen);
        String raizDestino = encontrarRaiz(destino);
        componentes.put(raizDestino, raizOrigen);
        
        pasoActual++;
        repaint();
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
    /**
     * Metodo para para marcar una arista como evaluada
     * @param origen Nodo origen
     * @param destino Nodo destino
     */
    public void marcarAristaEvaluada(String origen, String destino) {
        repaint();
        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }
    /**
     * Marca el proceso como completo
     */
    public void setProcesoCompleto() {
        this.procesoCompleto = true;
        repaint();
    }
    
    private String encontrarRaiz(String nodo) {
        while (!componentes.get(nodo).equals(nodo)) {
            nodo = componentes.get(nodo);
        }
        return nodo;
    }
    /**
     * Para dibujar el grafo y que este vaya cambiando de color a como se va ejecutando
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar todas las aristas en gris
        g2d.setColor(new Color(200, 200, 200));
        dibujarAristas(g2d, false, false);

        // Dibujar aristas del MST en rojo
        g2d.setColor(new Color(208, 104, 119));
        g2d.setStroke(new BasicStroke(3));
        dibujarAristas(g2d, false, true);
        g2d.setStroke(new BasicStroke(1));

        // Dibujar nodos coloreados por componente
        dibujarNodos(g2d);

        // Dibujar información
        dibujarInformacion(g2d);
    }
    /**
     * Metodo para dibujar las aristas graficamente en el panel
     * @param g2d
     * @param soloEvaluadas aristas que fueron evaluadas pero no forman parte del mst
     * @param soloMST aristas que forman parte del mst
     */
    private void dibujarAristas(Graphics2D g2d, boolean soloEvaluadas, boolean soloMST) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        for (LinkedList<Nodo> lista : grafo.getGrafo()) {
            if (lista.isEmpty()) continue;

            Nodo origen = lista.getFirst();
            Point posOrigen = posiciones.get(origen.getNombre());

            for (NodoAdy adyacente = origen.getSiguiente(); adyacente != null; adyacente = adyacente.getSiguiente()) {
                Point posDestino = posiciones.get(adyacente.getNombre());

                if (posOrigen != null && posDestino != null) {
                    String key = origen.getNombre().compareTo(adyacente.getNombre()) < 0
                            ? origen.getNombre() + "-" + adyacente.getNombre()
                            : adyacente.getNombre() + "-" + origen.getNombre();

                    boolean dibujar = soloMST ? aristasMST.contains(key) : true;

                    if (dibujar) {
                        g2d.drawLine(posOrigen.x, posOrigen.y, posDestino.x, posDestino.y);

                        // Dibujar peso
                        int midX = (posOrigen.x + posDestino.x) / 2;
                        int midY = (posOrigen.y + posDestino.y) / 2;
                        g2d.drawString(String.valueOf((int) adyacente.getPeso()), midX, midY);
                    }
                }
            }
        }
    }
    /**
     * metodo para mostrar los nodos graficamente
     * @param g2d 
     */
    private void dibujarNodos(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        

        for (Map.Entry<String, Point> entry : posiciones.entrySet()) {
            String nombre = entry.getKey();
            Point posicion = entry.getValue();
            String raiz = encontrarRaiz(nombre);

           // Asignar colores únicos a cada componente
        // Color del nodo
        Color colorNodo = new Color(200, 200, 200); // Gris por defecto
        if (procesoCompleto) {
            // Después de completar, nodos conectados en el MST
            if (estaEnMST(nombre)) {
                colorNodo = new Color(241, 192, 192); // Rojo claro
            }
         }
            

            // Dibujar el círculo
            int radio = 20;
            g2d.setColor(colorNodo);
            g2d.fillOval(posicion.x - (radio / 2), posicion.y - (radio / 2), radio, radio);

            // Borde
            g2d.setColor(Color.BLACK);
            g2d.drawOval(posicion.x - (radio / 2), posicion.y - (radio / 2), radio, radio);

            // Nombre
            FontMetrics fm = g2d.getFontMetrics();
            int textoX = posicion.x - (fm.stringWidth(nombre) / 2);
            int textoY = posicion.y + (fm.getHeight() / 4);
            g2d.drawString(nombre, textoX, textoY);
        }
    }
    
    private boolean estaEnMST(String nombreNodo) {
        for (String arista : aristasMST) {
            if (arista.contains(nombreNodo)) {
                return true;
            }
        }
        return false;
    }
    /**
     * metodo para mostrar la informacion en pantalla a como se va ejecutando el algoritmo
     * @param g2d 
     */
    private void dibujarInformacion(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);

        String estado = procesoCompleto ? "COMPLETO" : "PASO " + pasoActual;
        g2d.drawString("Estado: " + estado, 20, 30);
        g2d.drawString("Aristas en MST: " + aristasMST.size(), 20, 50);
        g2d.drawString("Peso total: " + (int) pesoTotal + " km", 20, 70);
        
        // Mostrar componentes conectadas
        Set<String> componentesUnicas = new HashSet<>();
        for (String municipio : componentes.keySet()) {
            componentesUnicas.add(encontrarRaiz(municipio));
        }
        g2d.drawString("Componentes: " + componentesUnicas.size(), 20, 90);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(2000, 900));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
