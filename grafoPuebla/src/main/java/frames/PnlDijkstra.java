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
import rutaMasCorta.Dijkstra;

/**
 * Panel que muestra de manera gráfica el procesamiento del algoritmo dijkstra
 *
 * @author victoria
 */
public class PnlDijkstra extends javax.swing.JPanel {

    private Grafo grafo; // Grafo a visualizar
    private Map<String, Point> posiciones; // Coordenadas de cada localidad
    // nombre, coordenada en el panel

    private String inicio; // localidad desde la que inicia la trayectoria
    private String fin; // localidad destino
    private Set<String> nodosVisitados = new HashSet<>();
    private Set<String> aristasVisitadas = new HashSet<>();
    private LinkedList<Nodo> rutaFinal = new LinkedList<>();
    private boolean procesoCompleto = false;

    public PnlDijkstra(String inicio, String fin) {
        initComponents();
        this.inicio = inicio;
        this.fin = fin;
        grafo = GrafoPueblaUtil.getGrafo();
        posiciones = new HashMap<>();
        setBackground(Color.WHITE);
        inicializarPosiciones();

        Dijkstra.setPanelVisualizacion(this);
        new Thread(() -> {
            try {
                Thread.sleep(300);
                LinkedList<Nodo> ruta = Dijkstra.ejecutar(inicio, fin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void reiniciarProceso() {
        nodosVisitados.clear();
        aristasVisitadas.clear();
        rutaFinal.clear();
        procesoCompleto = false;
        repaint();
    }

    public void marcarNodoVisitado(String nombreNodo) {
        nodosVisitados.add(nombreNodo);
        repaint();
    }

    public void marcarAristaVisitada(String origen, String destino) {
        String key = origen.compareTo(destino) < 0 // ordena para evitar aristas duplicadas
                ? origen + "-" + destino : destino + "-" + origen;
        aristasVisitadas.add(key);
        repaint();
    }

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

        // Dibuja las aristas en gris claro
        g2d.setColor(new Color(200, 200, 200));
        dibujarAristas(g2d, false);

        // Dibuja las aristas que se van visitando
        g2d.setColor(Color.ORANGE);
        dibujarAristas(g2d, true);

        // Dibujar la ruta final si el proceso está completo
        if (procesoCompleto && !rutaFinal.isEmpty()) {
            g2d.setColor(new Color(208, 104, 119));
            g2d.setStroke(new BasicStroke(3));
            dibujarRutaFinal(g2d);
            g2d.setStroke(new BasicStroke(1));
        }

        // Dibujar todos los nodos
        dibujarNodos(g2d);
    }

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

    private void dibujarNodos(Graphics2D g2d) {
        for (Map.Entry<String, Point> entry : posiciones.entrySet()) {
            String nombre = entry.getKey();
            Point posicion = entry.getValue();

            Color colorNodo;
            if (procesoCompleto) {
                boolean estaEnRuta = false;
                for (Nodo nodo : rutaFinal) {
                    if (nodo.getNombre().equals(nombre)) {
                        estaEnRuta = true;
                        break;
                    }
                }

                // Si está en la ruta, color rojo; sino, gris
                if (estaEnRuta) {
                    colorNodo = new Color( 241, 192, 192 );  // rojo para nodos de la ruta
                } else {
                    colorNodo = new Color(200, 200, 200); // gris para otros nodos
                }

            } else {

                if (nodosVisitados.contains(nombre)) {
                    colorNodo = new Color(255, 211, 135); // amarillo para nodos visitados
                } else {
                    colorNodo = new Color(200, 200, 200); // gris para nodos no visitados
                }
            }

            // Dibujar el círculo
            int radio = 20;
            int x = posicion.x - (radio / 2);
            int y = posicion.y - (radio / 2);
            g2d.setColor(colorNodo);
            g2d.fillOval(x, y, radio, radio);

            // Escribir el nombre de la localidad dentro del círculo
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int nombreAncho = fm.stringWidth(nombre);
            int nombreAlto = fm.getHeight();

            int textoX = posicion.x - (nombreAncho / 2);
            int textoY = posicion.y + (nombreAlto / 4);
            g2d.drawString(nombre, textoX, textoY);
        }
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
