/*  BFS.java  */
package busqueda;

import frames.PnlBFS;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.*;
import javax.swing.SwingUtilities;

/**
 * Clase que implementa el algoritmo de Búsqueda en Anchura (BFS). Permite
 * encontrar la ruta más corta (en número de aristas) entre dos nodos de un
 * grafo no ponderado. También puede actualizar visualmente el proceso en un
 * panel vinculado.
 *
 * El algoritmo explora los nodos por capas, utilizando una cola FIFO.
 */
public class BFS {

    /**
     * Tiempo de espera (en milisegundos) entre cada paso del algoritmo, usado
     * para la visualización animada del recorrido.
     */
    private static final int DELAY_MS = 250;

    /**
     * Panel gráfico encargado de visualizar el recorrido del algoritmo en
     * pantalla.
     */
    private static PnlBFS panelVisualizacion;

    /**
     * Establece el panel de visualización que se usará para mostrar el proceso
     * del algoritmo.
     *
     * @param panel el panel que se usará para visualizar nodos y aristas
     * visitadas
     */
    public static void setPanelVisualizacion(PnlBFS panel) {
        panelVisualizacion = panel;
    }

    /**
     * Ejecuta el algoritmo BFS desde un nodo de inicio hasta un nodo destino.
     *
     * @param inicio nombre del nodo de origen
     * @param fin nombre del nodo destino
     * @return una lista enlazada con los nodos que forman la ruta encontrada, o
     * una lista vacía si no existe camino entre ambos
     */
    public static LinkedList<Nodo> ejecutar(String inicio, String fin) {

        if (panelVisualizacion != null) {
            panelVisualizacion.reiniciarProceso();
        }

        Grafo grafo = GrafoPueblaUtil.getGrafo();
        Nodo origen = grafo.buscarNodo(inicio);
        Nodo destino = grafo.buscarNodo(fin);

        if (origen == null || destino == null) {
            return new LinkedList<>();
        }

        Queue<Nodo> cola = new ArrayDeque<>();
        Set<String> visitados = new HashSet<>();
        Map<String, Nodo> antecesor = new HashMap<>();

        cola.add(origen);
        visitados.add(origen.getNombre());
        marcarNodo(origen.getNombre());

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
                Nodo vecino = grafo.buscarNodo(ady.getNombre());
                if (vecino != null && visitados.add(vecino.getNombre())) {

                    antecesor.put(vecino.getNombre(), actual);
                    cola.add(vecino);

                    marcarArista(actual.getNombre(), vecino.getNombre());
                    marcarNodo(vecino.getNombre());
                    pausar();
                }
            }
        }

        LinkedList<Nodo> ruta = reconstruirRuta(origen, destino, antecesor);
        if (panelVisualizacion != null) {
            panelVisualizacion.setRutaFinal(ruta);
        }
        return ruta;
    }

    /**
     * Marca un nodo como visitado en el panel de visualización.
     *
     * @param n nombre del nodo a marcar
     */
    private static void marcarNodo(String n) {
        if (panelVisualizacion == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> panelVisualizacion.marcarNodoVisitado(n));
    }

    /**
     * Marca una arista como visitada en el panel de visualización.
     *
     * @param o nodo de origen
     * @param d nodo de destino
     */
    private static void marcarArista(String o, String d) {
        if (panelVisualizacion == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> panelVisualizacion.marcarAristaVisitada(o, d));
    }

    /**
     * Pausa la ejecución del hilo actual durante {@code DELAY_MS} milisegundos.
     */
    private static void pausar() {
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Reconstruye la ruta desde el nodo origen al destino usando el mapa de
     * antecesores.
     *
     * @param o nodo de origen
     * @param d nodo destino
     * @param ant mapa que contiene el nodo anterior para cada nodo alcanzado
     * @return la ruta como lista enlazada desde origen hasta destino
     */
    private static LinkedList<Nodo> reconstruirRuta(Nodo o, Nodo d, Map<String, Nodo> ant) {
        LinkedList<Nodo> ruta = new LinkedList<>();
        if (!o.equals(d) && !ant.containsKey(d.getNombre())) {
            return ruta;   // sin camino
        }
        for (Nodo n = d; n != null; n = ant.get(n.getNombre())) {
            ruta.addFirst(n);
            if (n.equals(o)) {
                break;
            }
        }
        return ruta;
    }
}
