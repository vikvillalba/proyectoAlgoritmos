
/*  DFS.java  */
package busqueda;

import frames.PnlDFS;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.*;
import javax.swing.SwingUtilities;

public class DFS {

    private static final int DELAY_MS = 250;          // ← pausa entre visitas
    private static PnlDFS panelVisualizacion;

    public static void setPanelVisualizacion(PnlDFS panel) {
        panelVisualizacion = panel;
    }

    public static LinkedList<Nodo> ejecutar(String inicio, String fin) {

        if (panelVisualizacion != null) panelVisualizacion.reiniciarProceso();

        Grafo grafo   = GrafoPueblaUtil.getGrafo();
        Nodo  origen  = grafo.buscarNodo(inicio);
        Nodo  destino = grafo.buscarNodo(fin);

        if (origen == null || destino == null) return new LinkedList<>();

        Set<String>       visitados  = new HashSet<>();
        Map<String,Nodo>  antecesor  = new HashMap<>();

        dfsRec(grafo, origen, visitados, antecesor);      // recorre TODO

        LinkedList<Nodo> ruta = reconstruirRuta(origen, destino, antecesor);
        if (panelVisualizacion != null) panelVisualizacion.setRutaFinal(ruta);
        return ruta;
    }

    /* ---------- DFS recursivo que visita todo ---------- */
    private static void dfsRec(Grafo grafo, Nodo actual,
                               Set<String> visitados, Map<String,Nodo> ant) {

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

    /* ---------- Helpers ---------- */
    private static void marcarNodo(String n) {
        if (panelVisualizacion == null) return;
        SwingUtilities.invokeLater(() -> panelVisualizacion.marcarNodoVisitado(n));
    }
    private static void marcarArista(String o, String d) {
        if (panelVisualizacion == null) return;
        SwingUtilities.invokeLater(() -> panelVisualizacion.marcarAristaVisitada(o, d));
    }
    private static void pausar() {
        try { Thread.sleep(DELAY_MS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    private static LinkedList<Nodo> reconstruirRuta(Nodo o, Nodo d, Map<String,Nodo> ant) {
        LinkedList<Nodo> ruta = new LinkedList<>();
        if (!o.equals(d) && !ant.containsKey(d.getNombre())) return ruta;   // sin camino
        for (Nodo n = d; n != null; n = ant.get(n.getNombre())) {
            ruta.addFirst(n);
            if (n.equals(o)) break;
        }
        return ruta;
    }
}