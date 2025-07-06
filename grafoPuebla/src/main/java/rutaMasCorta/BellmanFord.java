package rutaMasCorta;

import frames.PnlBellmanFord;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * implementación del algoritmo Bellman-Ford para calcular la ruta más corta de un nodo origen a un nodo destino.
 *
 * @author victoria
 */
public class BellmanFord {

    private static PnlBellmanFord panelVisualizacion;

    public static void setPanelVisualizacion(PnlBellmanFord panel) {
        panelVisualizacion = panel;
    }

    /**
     * cálculo de la ruta más corta entre un par de nodos implementando el algoritmo Bellman-Ford.
     *
     * @param inicio nodo desde el que inicia la trayectoria.
     * @param fin nodo hasta al que llega la trayectoria.
     * @return lista de nodos que representa la trayectoria de menor peso entre ambos vértices
     */
    public static LinkedList<Nodo> ejecutar(String inicio, String fin) {
        if (panelVisualizacion != null) {
            panelVisualizacion.reiniciarProceso();
        }

        Grafo grafo = GrafoPueblaUtil.getGrafo(); // obtener el grafo
        // obtener los nodos de origen y destino
        Nodo origen = grafo.buscarNodo(inicio);
        Nodo destino = grafo.buscarNodo(fin);

        if (origen == null || destino == null) {
            System.out.println("Error: Nodos no encontrados");
            return new LinkedList<>();
        }

        List<Nodo> nodos = grafo.getNodosGrafo(); // obtener todos los nodos del grafo
        RutaMasCorta.initializeSingleSource(nodos, origen); // inicializar una sola fuente

        if (panelVisualizacion != null) {
            panelVisualizacion.nuevaIteracion(0);
            panelVisualizacion.marcarNodoVisitado(origen.getNombre());
        }

        // Recorre todas las aristas iterando con un contador
        for (int i = 1; i < nodos.size(); i++) {
            boolean cambios = false;

            if (panelVisualizacion != null) {
                panelVisualizacion.nuevaIteracion(i);
            }
            System.out.println("\n--- Iteración " + i + " ---");

            for (Nodo actual : nodos) {
                if (panelVisualizacion != null) {
                    panelVisualizacion.marcarNodoVisitado(actual.getNombre());
                }

                for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
                    Nodo vecino = grafo.buscarNodo(ady.getNombre()); 
                    if (vecino != null) {
                        if (panelVisualizacion != null) {
                            panelVisualizacion.marcarAristaVisitada(actual.getNombre(), vecino.getNombre());
                        }

                        double distanciaAnterior = vecino.getDistanciaTemporal();
                        RutaMasCorta.relax(actual, vecino, ady.getPeso()); // relaja la arista

                        if (vecino.getDistanciaTemporal() < distanciaAnterior) {
                            cambios = true;
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (!cambios) {
                System.out.println("Sin cambios en iteración " + i + ", terminando procesamiento.");
                break;
            }
        }

        // Verificar ciclos negativos (después de completar todas las iteraciones)
        boolean cicloNegativo = false;
        for (Nodo actual : nodos) {
            for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
                Nodo vecino = grafo.buscarNodo(ady.getNombre());
                if (vecino != null
                        && actual.getDistanciaTemporal() + ady.getPeso() < vecino.getDistanciaTemporal()) {
                    cicloNegativo = true;
                    break;
                }
            }
            if (cicloNegativo) {
                break;
            }
        }

        if (cicloNegativo) {
            System.out.println("El grafo contiene un ciclo de peso negativo");
            if (panelVisualizacion != null) {
                panelVisualizacion.mostrarError("Ciclo negativo detectado");
            }
            return new LinkedList<>();
        }

        LinkedList<Nodo> ruta = RutaMasCorta.obtenerTrayectoria(destino);
        if (panelVisualizacion != null) {
            panelVisualizacion.setRutaFinal(ruta);
        }
        

        return ruta;
    }
}
