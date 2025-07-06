package frames;

import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Panel que muestra de manera gráfica el grafo
 *
 * @author victoria
 */
public class PnlGrafo extends javax.swing.JPanel {

    private Grafo grafo; // Grafo a visualizar
    private Map<String, Point> posiciones; // Coordenadas de cada localidad
    // nombre, coordenada en el panel

    public PnlGrafo() {
        initComponents();
        grafo = GrafoPueblaUtil.getGrafo();
        posiciones = new HashMap<>();
        setBackground(Color.WHITE);
        inicializarPosiciones();
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
        posiciones.put("San Andrés Cholula", new Point((int) (200 * escala), 490));
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
    protected void paintComponent(Graphics g
    ) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar las aristas 
        g2d.setColor(Color.DARK_GRAY);
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
                    // Dibujar la línea entre origen y destino
                    g2d.drawLine(posOrigen.x, posOrigen.y, posDestino.x, posDestino.y);

                    // Dibujar la distancia
                    int midX = (posOrigen.x + posDestino.x) / 2;
                    int midY = (posOrigen.y + posDestino.y) / 2;
                    g2d.drawString(String.valueOf((int) adyacente.getPeso()), midX, midY);
                }
            }
        }

        // Dibujar las localidades
        g2d.setColor(new Color(222, 217, 255)); // Color de los nodos
        for (Map.Entry<String, Point> entry : posiciones.entrySet()) {
            String nombre = entry.getKey();
            Point posicion = entry.getValue();

            // Dibujar el círculo
            int radio = 20;
            int x = posicion.x - (radio / 2);
            int y = posicion.y - (radio / 2);
            g2d.fillOval(x, y, radio, radio);

            // Escribir el nombre de a localidad dentro del círculo
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int nombreAncho = fm.stringWidth(nombre);
            int nombreAlto = fm.getHeight();

            int textoX = posicion.x - (nombreAncho / 2);
            int textoY = posicion.y + (nombreAlto / 4); // centrar verticalmente
            g2d.drawString(nombre, textoX, textoY);

            // Restaurar color
            g2d.setColor(new Color(222, 217, 255));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(null);

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
