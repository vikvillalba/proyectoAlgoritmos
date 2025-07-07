/*  BFS.java  */
package busqueda;

import frames.PnlBFS;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.*;
import javax.swing.SwingUtilities;

public class BFS {

    private static final int DELAY_MS = 250;          // ← pausa entre visitas
    private static PnlBFS panelVisualizacion;

    public static void setPanelVisualizacion(PnlBFS panel) {
        panelVisualizacion = panel;
    }

    public static LinkedList<Nodo> ejecutar(String inicio, String fin) {

        if (panelVisualizacion != null) panelVisualizacion.reiniciarProceso();

        Grafo grafo   = GrafoPueblaUtil.getGrafo();
        Nodo  origen  = grafo.buscarNodo(inicio);
        Nodo  destino = grafo.buscarNodo(fin);

        if (origen == null || destino == null) return new LinkedList<>();

        Queue<Nodo>           cola      = new ArrayDeque<>();
        Set<String>           visitados = new HashSet<>();
        Map<String, Nodo>     antecesor = new HashMap<>();

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
        if (panelVisualizacion != null) panelVisualizacion.setRutaFinal(ruta);
        return ruta;
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