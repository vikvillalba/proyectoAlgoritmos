package rutaMasCorta;

import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * implementación del algoritmo Dijkstra para calcular la ruta más corta de un nodo origen a un nodo destino.
 *
 * @author victoria
 */
public class Dijkstra {

    /**
     * cálculo de la ruta más corta entre un par de nodos implementando el algoritmo Dijkstra.
     *
     * @param inicio nodo desde el que inicia la trayectoria.
     * @param fin nodo hasta al que llega la trayectoria.
     * @return lista de nodos que representa la trayectoria de menor peso entre ambos vértices
     */
    public static LinkedList<Nodo> ejecutar(String inicio, String fin) {
        Grafo grafo = GrafoPueblaUtil.getGrafo(); // obtener el grafo
        Nodo origen = grafo.buscarNodo(inicio);
        Nodo destino = grafo.buscarNodo(fin);

        // si alguno de los nodos no existe
        if (origen == null || destino == null) {
            System.out.println("Alguno de los nodos es nulo o incorrecto.");
            return new LinkedList<>();
        }

        System.out.println("Calculando ruta: " + inicio + " -> " + fin);

        // obtener los nodos del grafo
        List<Nodo> grafoNodos = grafo.getNodosGrafo();
        // PriorityQueue organiza los vértices del grafo de menor a mayor
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingDouble(Nodo::getDistanciaTemporal));
        cola.addAll(grafoNodos);

        // configura los nodos e inicializa la fuente
        RutaMasCorta.initializeSingleSource(grafoNodos, origen);

        // mientras la cola tenga nodos
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll(); // extrae nodo actual
            System.out.print("\nProcesando: " + actual.getNombre());
            // si ya llegó al destino
            if (actual.equals(destino)) {
                System.out.println("\nDestino alcanzado");
                break;
            }

            NodoAdy adyacente = actual.getSiguiente(); // obtiene nodo adyacente como NodoAdy
            while (adyacente != null) {
                // obtiene el nodo 
                System.out.print("\nProcesando nodo vecino: " + adyacente.getNombre());
                Nodo vecino = grafo.buscarNodo(adyacente.getNombre());
                if (vecino != null) {
                    double distanciaAnterior = vecino.getDistanciaTemporal();
                    // relaja la arista
                    RutaMasCorta.relax(actual, vecino, adyacente.getPeso());
                    // actualiza la posición en la cola
                    if (vecino.getDistanciaTemporal() < distanciaAnterior) {
                        cola.remove(vecino);
                        cola.add(vecino);
                    }
                }

                adyacente = adyacente.getSiguiente();
            }
        }
        // obtiene la trayectoria
        System.out.println("Procesamiento completado.");
        return RutaMasCorta.obtenerTrayectoria(destino);

    }

}
