package frames;

import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import busqueda.BFS;
import java.awt.*;
import java.util.*;
import javax.swing.SwingUtilities;

public class PnlBFS extends javax.swing.JPanel {


    /* ------------------ Estado interno ------------------ */
    private final Grafo grafo;
    private final Map<String, Point> posiciones = new HashMap<>();

    private final String inicio;
    private final String fin;

    private final Set<String> nodosVisitados = new HashSet<>();
    private final Set<String> aristasVisitadas = new HashSet<>();
    private LinkedList<Nodo> rutaFinal = new LinkedList<>();
    private boolean procesoCompleto = false;

    /* ---------------------------------------------------- */

    public PnlBFS(String inicio, String fin) {
        this.inicio = inicio;
        this.fin = fin;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(2000, 900));

        grafo = GrafoPueblaUtil.getGrafo();
        inicializarPosiciones();

        /* Conecta el panel con el algoritmo */
        BFS.setPanelVisualizacion(this);

        /* Lanza la ejecución en un hilo aparte */
        new Thread(() -> {
            try {
                Thread.sleep(300);
                BFS.ejecutar(inicio, fin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /* ------------------ Métodos que el algoritmo invoca ------------------ */
    public void reiniciarProceso() {
        SwingUtilities.invokeLater(() -> {
            nodosVisitados.clear();
            aristasVisitadas.clear();
            rutaFinal.clear();
            procesoCompleto = false;
            repaint();
        });
    }

    public void marcarNodoVisitado(String nombreNodo) {
        nodosVisitados.add(nombreNodo);
        repaint();
    }

    public void marcarAristaVisitada(String origen, String destino) {
        String key = origen.compareTo(destino) < 0 ? origen + "-" + destino
                : destino + "-" + origen;
        aristasVisitadas.add(key);
        repaint();
    }

    public void setRutaFinal(LinkedList<Nodo> ruta) {
        SwingUtilities.invokeLater(() -> {
            this.rutaFinal = ruta;
            this.procesoCompleto = true;
            repaint();
        });
    }

    /* -------------------------------------------------------------------- */

 /* ------------------------ Dibujo ------------------------------------ */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Aristas “de fondo” */
        g2d.setColor(new Color(200, 200, 200));
        dibujarAristas(g2d, false);

        /* Aristas visitadas */
        g2d.setColor(Color.ORANGE);
        dibujarAristas(g2d, true);

        /* Ruta final */
        if (procesoCompleto && !rutaFinal.isEmpty()) {
            g2d.setColor(new Color(208, 104, 119));
            g2d.setStroke(new BasicStroke(3));
            dibujarRutaFinal(g2d);
            g2d.setStroke(new BasicStroke(1));
        }

        /* Nodos */
        dibujarNodos(g2d);
    }

    private void dibujarAristas(Graphics2D g2d, boolean soloVisitadas) {
        for (LinkedList<Nodo> lista : grafo.getGrafo()) {
            if (lista.isEmpty()) {
                continue;
            }

            Nodo origen = lista.getFirst();
            Point pO = posiciones.get(origen.getNombre());

            for (NodoAdy ady = origen.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
                Point pD = posiciones.get(ady.getNombre());
                if (pO == null || pD == null) {
                    continue;
                }

                String key = origen.getNombre().compareTo(ady.getNombre()) < 0
                        ? origen.getNombre() + "-" + ady.getNombre()
                        : ady.getNombre() + "-" + origen.getNombre();

                boolean dib = !soloVisitadas || aristasVisitadas.contains(key);
                if (dib) {
                    g2d.drawLine(pO.x, pO.y, pD.x, pD.y);
                }
            }
        }
    }

    private void dibujarRutaFinal(Graphics2D g2d) {
        for (int i = 0; i < rutaFinal.size() - 1; i++) {
            Point a = posiciones.get(rutaFinal.get(i).getNombre());
            Point b = posiciones.get(rutaFinal.get(i + 1).getNombre());
            if (a != null && b != null) {
                g2d.drawLine(a.x, a.y, b.x, b.y);
            }
        }
    }

    private void dibujarNodos(Graphics2D g2d) {
        for (var entry : posiciones.entrySet()) {
            String nombre = entry.getKey();
            Point pos = entry.getValue();

            Color c;
            if (procesoCompleto && rutaContiene(nombre)) {
                c = new Color(241, 192, 192);
            } else if (nodosVisitados.contains(nombre)) {
                c = new Color(255, 211, 135);
            } else {
                c = new Color(200, 200, 200);
            }

            int r = 20, x = pos.x - r / 2, y = pos.y - r / 2;
            g2d.setColor(c);
            g2d.fillOval(x, y, r, r);

            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(nombre, pos.x - fm.stringWidth(nombre) / 2,
                    pos.y + fm.getAscent() / 2);
        }
    }

    private boolean rutaContiene(String nombre) {
        return rutaFinal.stream().anyMatch(n -> n.getNombre().equals(nombre));
    }

    /* -------------------------------------------------------------------- */

 /* ---------------- Posiciones de nodos ---------------- */
    private void inicializarPosiciones() {
        double k = 2;  // escala  (ajusta si necesitas mover el dibujo)
        posiciones.put("Acajete", new Point((int) (345 * k), 444));
        posiciones.put("Acatlán", new Point((int) (307 * k), 640));
        posiciones.put("Acatzingo", new Point((int) (394 * k), 465));
        posiciones.put("Ajalpan", new Point((int) (580 * k), 650));
        posiciones.put("Amozoc", new Point((int) (300 * k), 460));
        posiciones.put("Atlixco", new Point((int) (150 * k), 520));
        posiciones.put("Chalchicomula de Sesma", new Point((int) (495 * k), 475));
        posiciones.put("Chignahuapan", new Point((int) (285 * k), 230));
        posiciones.put("Chignautla", new Point((int) (470 * k), 260));
        posiciones.put("Chietla", new Point((int) (140 * k), 610));
        posiciones.put("Coronango", new Point((int) (260 * k), 290));
        posiciones.put("Cuautlancingo", new Point((int) (250 * k), 390));
        posiciones.put("Cuetzalan del Progreso", new Point((int) (470 * k), 140));
        posiciones.put("Huejotzingo", new Point((int) (170 * k), 460));
        posiciones.put("Hueytamalco", new Point((int) (545 * k), 150));
        posiciones.put("Huauchinango", new Point((int) (260 * k), 129));
        posiciones.put("Izúcar de Matamoros", new Point((int) (195 * k), 675));
        posiciones.put("Libres", new Point((int) (420 * k), 335));
        posiciones.put("Nopalucan", new Point((int) (395 * k), 420));
        posiciones.put("Palmar de Bravo", new Point((int) (470 * k), 520));
        posiciones.put("Puebla", new Point((int) (270 * k), 560));
        posiciones.put("Quecholac", new Point((int) (430 * k), 490));
        posiciones.put("San Andrés Cholula", new Point((int) (200 * k), 450));
        posiciones.put("San Martín Texmelucan", new Point((int) (200 * k), 350));
        posiciones.put("San Pedro Cholula", new Point((int) (225 * k), 425));
        posiciones.put("San Salvador el Seco", new Point((int) (440 * k), 400));
        posiciones.put("San Salvador el Verde", new Point((int) (136 * k), 406));
        posiciones.put("Tecamachalco", new Point((int) (390 * k), 554));
        posiciones.put("Tehuacán", new Point((int) (500 * k), 630));
        posiciones.put("Tepeaca", new Point((int) (355 * k), 520));
        posiciones.put("Teziutlán", new Point((int) (510 * k), 220));
        posiciones.put("Tlachichuca", new Point((int) (515 * k), 435));
        posiciones.put("Tlacotepec de Benito Juárez", new Point((int) (435 * k), 570));
        posiciones.put("Tlahuapan", new Point((int) (150 * k), 300));
        posiciones.put("Tlatlauquitepec", new Point((int) (330 * k), 70));
        posiciones.put("Venustiano Carranza", new Point((int) (420 * k), 30));
        posiciones.put("Xiutetelco", new Point((int) (515 * k), 260));
        posiciones.put("Xicotepec", new Point((int) (225 * k), 570));
        posiciones.put("Zacapoaxtla", new Point((int) (450 * k), 220));
        posiciones.put("Zacatlán", new Point((int) (330 * k), 190));
    }

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
