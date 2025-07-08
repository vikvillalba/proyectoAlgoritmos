package busqueda;

import frames.PnlDFS;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.*;
import javax.swing.SwingUtilities;

/**
 * Implementa el algoritmo de Búsqueda en Profundidad (DFS) para grafos no
 * ponderados. Recorre exhaustivamente los vértices alcanzables desde un origen
 * siguiendo un esquema de pila implícita (recursión) y, con ayuda de un
 * {@link frames.PnlDFS}, puede mostrar animado el recorrido. También
 * reconstruye la ruta desde el vértice inicial hasta el destino, si este es
 * alcanzable.
 */
public class DFS {

    /**
     * Pausa (ms) entre pasos cuando se muestra animación.
     */
    private static final int DELAY_MS = 200;

    /**
     * Panel encargado de la visualización en tiempo real (puede ser
     * {@code null}).
     */
    private static PnlDFS panelVisualizacion;

    /**
     * Enlaza un panel de visualización para mostrar el progreso del algoritmo.
     *
     * @param panel panel de tipo {@link PnlDFS}; puede ser {@code null} para
     * desactivar la visualización.
     */
    public static void setPanelVisualizacion(PnlDFS panel) {
        panelVisualizacion = panel;
    }

    /**
     * Ejecuta DFS desde {@code inicio} hasta {@code fin}.
     *
     * @param inicio nombre del nodo de partida
     * @param fin nombre del nodo de destino
     * @return ruta (lista enlazada) desde inicio hasta fin; vacía si no hay
     * camino
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

        Set<String> visitados = new HashSet<>();
        Map<String, Nodo> antecesor = new HashMap<>();

        dfsRec(grafo, origen, visitados, antecesor);      // recorre TODO

        LinkedList<Nodo> ruta = reconstruirRuta(origen, destino, antecesor);
        if (panelVisualizacion != null) {
            panelVisualizacion.setRutaFinal(ruta);
        }
        return ruta;
    }

    /**
     * DFS recursivo que visita todo el componente conectado que parte de
     * {@code actual}.
     *
     * @param grafo grafo donde se realiza la búsqueda
     * @param actual nodo actualmente procesado
     * @param visitados conjunto de nombres de nodos ya visitados
     * @param ant mapa de antecesores para reconstruir caminos
     */
    private static void dfsRec(Grafo grafo, Nodo actual,
            Set<String> visitados, Map<String, Nodo> ant) {

        visitados.add(actual.getNombre());
        marcarNodo(actual.getNombre());
        pausar();

        for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
            Nodo vecino = grafo.buscarNodo(ady.getNombre());
            if (vecino != null && visitados.add(vecino.getNombre())) {

                ant.put(vecino.getNombre(), actual);
                marcarArista(actual.getNombre(), vecino.getNombre());
                pausar();

                dfsRec(grafo, vecino, visitados, ant);    // sigue profundidad
            }
        }
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
