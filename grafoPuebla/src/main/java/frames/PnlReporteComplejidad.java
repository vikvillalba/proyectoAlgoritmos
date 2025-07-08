/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frames;

/**
 * <p>
 * Panel que muestra la descripción y las complejidades temporal y espacial
 * (mejor, promedio y peor caso) de un algoritmo de grafos elegido en la
 * aplicación.</p>
 *
 * <p>
 * El panel se compone de un título con el nombre del algoritmo y un bloque
 * <i>HTML</i> incrustado en un {@code JLabel} para permitir formato
 * enriquecido.</p>
 *
 * <p>
 * <b>Uso:</b> la ventana principal crea una instancia pasando su propia
 * referencia y la cadena identificadora del algoritmo, por ejemplo
 * {@code new PnlReporteComplejidad(this, "DIJKSTRA")}.</p>
 *
 */
public class PnlReporteComplejidad extends javax.swing.JPanel {

    /**
     * Ventana principal que invoca al panel (por si se requiere
     * retro‑navegación).
     */
    private final FrmPrincipal inicio;

    /**
     * Identificador del algoritmo (en mayúsculas) usado por el {@code switch}.
     */
    private final String algoritmo;

    /**
     * Construye el panel de reporte.
     *
     * @param inicio referencia a la ventana principal
     * @param algoritmo nombre del algoritmo (no sensible a
     * mayúsculas/minúsculas)
     */
    public PnlReporteComplejidad(FrmPrincipal inicio, String algoritmo) {
        initComponents();
        this.inicio = inicio;
        this.algoritmo = algoritmo;
        setInfo();
    }

    /**
     * Establece el texto del título ({@code algoritmoLabel}) y de la
     * descripción ({@code textoDescripcionLabel}) según el algoritmo
     * seleccionado.
     *
     * <p>
     * La descripción incluye complejidades temporal y espacial en formato
     * <i>HTML</i> para un mejor formato dentro del {@code JLabel}.</p>
     */
    private void setInfo() {
        switch (algoritmo) {
            case "BFS":
                algoritmoLabel.setText("BFS – Breadth - First Search");
                textoDescripcionLabel.setText(
                        html(
                                "El <b>recorrido en anchura (BFS)</b> visita el grafo por niveles "
                                + "usando una cola FIFO. Garantiza encontrar la distancia mínima "
                                + "(en número de aristas) desde el origen hasta cada vértice alcanzable.",
                                "O(V + E)", // peor caso
                                "O(V + E)", // mejor caso
                                "O(V + E)", // promedio
                                "O(V)" // espacio
                        ));
                break;

            case "DFS":
                algoritmoLabel.setText("DFS – Depth - First Search");
                textoDescripcionLabel.setText(
                        html(
                                "El <b>recorrido en profundidad (DFS)</b> explora tan lejos como "
                                + "sea posible por cada rama antes de retroceder, usando recursión "
                                + "o una pila explícita.",
                                "O(V + E)",
                                "O(V + E)",
                                "O(V + E)",
                                "O(V) (recursión) / O(V) (pila)"
                        ));
                break;

            case "DIJKSTRA":
                algoritmoLabel.setText("Dijkstra – Ruta más corta");
                textoDescripcionLabel.setText(
                        html(
                                "Calcula la ruta más corta desde un vértice origen a todos los demás "
                                + "con pesos no negativos, usando una cola de prioridad.",
                                "O(E log V) con <i>heap</i> binario",
                                "O(E) – cuando la extracción es O(1) (por ejemplo, con buckets y pesos pequeños)",
                                "≈ O(E log V)",
                                "O(V + E)"
                        ));
                break;

            case "BELLMANFORD":
            case "BELLMAN-FORD":
                algoritmoLabel.setText("Bellman - Ford – Ruta más corta");
                textoDescripcionLabel.setText(
                        html(
                                "Permite aristas con pesos negativos. Relaja todas las aristas |V|‑1 veces "
                                + "y puede finalizar antes si en una pasada no se producen cambios.",
                                "O(V × E)",
                                "O(E) – si no cambia ninguna distancia tras la primera iteración",
                                "≈ O((V/2) × E)",
                                "O(V)"
                        ));
                break;

            case "KRUSKAL":
                algoritmoLabel.setText("Kruskal – Árbol de Expansión Mínima");
                textoDescripcionLabel.setText(
                        html(
                                "Ordena las aristas por peso y las añade si no forman ciclo, "
                                + "utilizando la estructura <i>Union‑Find</i>.",
                                "O(E log V) (ordenación dominante)",
                                "O(E α(V)) – si ya están ordenadas",
                                "O(E log V)",
                                "O(V)"
                        ));
                break;

            case "PRIM":
                algoritmoLabel.setText("Prim – Árbol de Expansión Mínima");
                textoDescripcionLabel.setText(
                        html(
                                "Comienza en un vértice y añade iterativamente la arista más ligera "
                                + "que expande el árbol parcial, usando cola de prioridad.",
                                "O(E log V) con <i>heap</i> binario",
                                "O(E) – si el grafo ya es un árbol (E = V‑1)",
                                "≈ O(E log V)",
                                "O(V)"
                        ));
                break;

            case "BORUVKA":
                algoritmoLabel.setText("Borůvka – Árbol de Expansión Mínima");
                textoDescripcionLabel.setText(
                        html(
                                "Opera por fases paralelas: cada componente elige la arista más ligera "
                                + "hacia otra componente y todas se añaden simultáneamente.",
                                "O(E log V)",
                                "O(E) – si V es potencia de dos y se fusiona en una sola fase",
                                "≈ O(E log V)",
                                "O(E)"
                        ));
                break;

            default:
                algoritmoLabel.setText(algoritmo);
                textoDescripcionLabel.setText(
                        "<html><p width=\"1400\">Descripción no disponible.</p></html>");
        }
    }

    /**
     * Construye un bloque de texto en formato <i>HTML</i> para el
     * {@code JLabel} que contiene la descripción y las complejidades.
     *
     * @param descripcion descripción general del algoritmo
     * @param peor complejidad temporal en el peor caso
     * @param mejor complejidad temporal en el mejor caso
     * @param promedio complejidad temporal promedio
     * @param espacio complejidad espacial
     * @return cadena HTML lista para asignar a un {@code JLabel}
     */
    private String html(String descripcion,
            String peor,
            String mejor,
            String promedio,
            String espacio) {

        return "<html><p width=\"1400\">" + descripcion + "<br><br>"
                + "• <strong>Tiempo (peor caso):</strong> " + peor + "<br>"
                + "• <strong>Tiempo (mejor caso):</strong> " + mejor + "<br>"
                + "• <strong>Tiempo (promedio):</strong> " + promedio + "<br>"
                + "• <strong>Espacio:</strong> " + espacio
                + "</p></html>";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoDescripcionLabel = new javax.swing.JLabel();
        descripcionLabel = new javax.swing.JLabel();
        algoritmoLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textoDescripcionLabel.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        add(textoDescripcionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 1000, 150));

        descripcionLabel.setFont(new java.awt.Font("Arial Narrow", 0, 36)); // NOI18N
        descripcionLabel.setText("Descripción");
        add(descripcionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 230, 50));

        algoritmoLabel.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        add(algoritmoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 1000, 60));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel algoritmoLabel;
    private javax.swing.JLabel descripcionLabel;
    private javax.swing.JLabel textoDescripcionLabel;
    // End of variables declaration//GEN-END:variables
}
