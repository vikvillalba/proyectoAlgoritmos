package frames;

import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import rutaMasCorta.BellmanFord;

/**
 * Panel que muestra de manera gráfica el procesamiento del algoritmo bellman-ford
 *
 * @author victoria
 */
public class PnlBellmanFord extends javax.swing.JPanel {

    private Grafo grafo; // Grafo a visualizar
    private Map<String, Point> posiciones; // Coordenadas de cada localidad
    // nombre, coordenada en el panel

    private String inicio; // localidad desde la que inicia la trayectoria
    private String fin; // localidad destino
    private Set<String> nodosVisitados = new HashSet<>();
    private Set<String> aristasVisitadas = new HashSet<>();
    private LinkedList<Nodo> rutaFinal = new LinkedList<>();
    private boolean procesoCompleto = false;

    private int iteracionActual = 0;
    private Set<String> nodosProcesadosIteracion = new HashSet<>();
    private Set<String> aristasProcesadasIteracion = new HashSet<>();

    public PnlBellmanFord(String inicio, String fin) {
        initComponents();
        this.inicio = inicio;
        this.fin = fin;
        grafo = GrafoPueblaUtil.getGrafo();
        posiciones = new HashMap<>();
        setBackground(Color.WHITE);
        inicializarPosiciones();

        BellmanFord.setPanelVisualizacion(this);
        // inicializa el hilo de ejecución
        new Thread(() -> {
            try {
                Thread.sleep(300);
                LinkedList<Nodo> ruta = BellmanFord.ejecutar(inicio, fin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Reinicia el proceso para obtener la ruta final.
     */
    public void reiniciarProceso() {
        nodosVisitados.clear();
        aristasVisitadas.clear();
        rutaFinal.clear();
        procesoCompleto = false;
        repaint();
    }

    public void nuevaIteracion(int numeroIteracion) {
        this.iteracionActual = numeroIteracion;
        this.nodosProcesadosIteracion.clear();
        this.aristasProcesadasIteracion.clear();
        repaint();
    }

    public void marcarNodoVisitado(String nombreNodo) {
        nodosProcesadosIteracion.add(nombreNodo);
        nodosVisitados.add(nombreNodo);
        repaint();
    }

    public void marcarAristaVisitada(String origen, String destino) {
        String key = origen.compareTo(destino) < 0 ? origen + "-" + destino : destino + "-" + origen;
        aristasProcesadasIteracion.add(key);
        aristasVisitadas.add(key);
        repaint();
    }

    /**
     * Guarda la ruta resultante al final del procesamiento
     *
     * @param ruta trayectoria de nodos de un origen a destino con el menor peso.
     */
    public void setRutaFinal(LinkedList<Nodo> ruta) {
        this.rutaFinal = ruta;
        this.procesoCompleto = true;
        repaint();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar todas las aristas en gris 
        g2d.setColor(new Color(200, 200, 200));
        dibujarAristas(g2d, false);

        // Dibujar aristas visitadas en iteraciones anteriores
        g2d.setColor(new Color(255, 211, 135));
        dibujarAristas(g2d, true);

        // Dibujar aristas de la iteración actual en rojo
        g2d.setColor(Color.RED);
        dibujarAristasIteracionActual(g2d);

        // Dibujar ruta final si está completa
        if (procesoCompleto && !rutaFinal.isEmpty()) {
            g2d.setColor(new Color(120, 187, 150)); 
            g2d.setStroke(new BasicStroke(3)); // Hacer la ruta final más gruesa
            dibujarRutaFinal(g2d);
            g2d.setStroke(new BasicStroke(1));
        }

        dibujarNodos(g2d);

        // Mostrar número de iteración
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Iteración: " + iteracionActual, 20, 30);
    }

    private void dibujarAristasIteracionActual(Graphics2D g2d) {
        for (String aristaKey : aristasProcesadasIteracion) {
            String[] nodos = aristaKey.split("-");
            Point p1 = posiciones.get(nodos[0]);
            Point p2 = posiciones.get(nodos[1]);

            if (p1 != null && p2 != null) {
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    /**
     * Dibuja las aristas del grafo.
     */
    private void dibujarAristas(Graphics2D g2d, boolean soloVisitadas) {
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        for (LinkedList<Nodo> lista : grafo.getGrafo()) {
            if (lista.isEmpty()) {
                continue;
            }

            Nodo origen = lista.getFirst();
            Point posOrigen = posiciones.get(origen.getNombre());

            for (NodoAdy adyacente = origen.getSiguiente(); adyacente != null; adyacente = adyacente.getSiguiente()) {
                Point posDestino = posiciones.get(adyacente.getNombre());

                if (posOrigen != null && posDestino != null) {
                    String key = origen.getNombre().compareTo(adyacente.getNombre()) < 0
                            ? origen.getNombre() + "-" + adyacente.getNombre()
                            : adyacente.getNombre() + "-" + origen.getNombre();

                    boolean dibujar = soloVisitadas ? aristasVisitadas.contains(key) : true;

                    if (dibujar) {
                        g2d.drawLine(posOrigen.x, posOrigen.y, posDestino.x, posDestino.y);

                        // Dibujar la distancia
                        int midX = (posOrigen.x + posDestino.x) / 2;
                        int midY = (posOrigen.y + posDestino.y) / 2;
                        g2d.drawString(String.valueOf((int) adyacente.getPeso()), midX, midY);
                    }
                }
            }
        }
    }

    /**
     * Dibuja la ruta final del grafo.
     */
    private void dibujarRutaFinal(Graphics2D g2d) {
        for (int i = 0; i < rutaFinal.size() - 1; i++) {
            Nodo actual = rutaFinal.get(i);
            Nodo siguiente = rutaFinal.get(i + 1);

            Point posActual = posiciones.get(actual.getNombre());
            Point posSiguiente = posiciones.get(siguiente.getNombre());

            if (posActual != null && posSiguiente != null) {
                g2d.drawLine(posActual.x, posActual.y, posSiguiente.x, posSiguiente.y);
            }
        }
    }

    /**
     * Dibuja nodos del grafo.
     */
    private void dibujarNodos(Graphics2D g2d) {
        for (Map.Entry<String, Point> entry : posiciones.entrySet()) {
            String nombre = entry.getKey();
            Point posicion = entry.getValue();

            // Determinar color del nodo
            Color colorNodo;
            if (procesoCompleto) {
                // Después de completar
                boolean enRuta = rutaFinal.stream().anyMatch(n -> n.getNombre().equals(nombre));
                colorNodo = enRuta ? new Color(151, 224, 184) : new Color(200, 200, 200); // Verde para ruta, gris para otros
            } else if (nodosProcesadosIteracion.contains(nombre)) {
                // Nodos de la iteración actual
                colorNodo = new Color(241, 192, 192); 
            } else if (nodosVisitados.contains(nombre)) {
                
                // Nodos visitados en iteraciones anteriores
                colorNodo = new Color(255, 200, 100);
            } else {
                
                // Nodos no visitados
                colorNodo = new Color(220, 220, 220); // Gris 
            }

            // Dibujar nodo 
            int radio = 20;
            g2d.setColor(colorNodo);
            g2d.fillOval(posicion.x - radio / 2, posicion.y - radio / 2, radio, radio);

            // Dibujar nombre del nodo
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int textoX = posicion.x - fm.stringWidth(nombre) / 2;
            int textoY = posicion.y + fm.getAscent() / 2;
            g2d.drawString(nombre, textoX, textoY);
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
