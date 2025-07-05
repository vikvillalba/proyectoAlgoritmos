package rutaMasCorta;

import grafo.Grafo;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que contiene algoritmos genéricos que se comparten. Implementaciones de inicialización de una sola fuente y de relajación
 *
 * @author victoria
 */
public class RutaMasCorta {

    /**
     * Inicialización de una sola fuente.
     *
     * @param nodos vértices del grafo
     * @param origen vértice desde el que inicia el procesamiento
     */
    public static void initializeSingleSource(List<Nodo> nodos, Nodo origen) {
        // recorre todos los vértices
        for (Nodo nodo : nodos) {
            nodo.setDistanciaTemporal(Double.POSITIVE_INFINITY); // asigna distancia infinita
            nodo.setAnterior(null); // asigna predecesor como nulo
        }
        origen.setDistanciaTemporal(0); // asigna distancia 0 al nodo fuente
        System.out.println("Nodo origen: " + origen.getNombre());
    }

    /**
     * Relaja una arista entre el nodo u (actual) y v (destino), actualizando la distancia si se encuentra un camino más corto.
     *
     * @param u Nodo actual/origen
     * @param v Nodo destino
     * @param peso Peso de la arista entre u y v
     */
    public static void relax(Nodo u, Nodo v, double peso) {
        double distanciaActual = v.getDistanciaTemporal();
        double distanciaNueva = u.getDistanciaTemporal() + peso;

        if (distanciaActual > distanciaNueva) {  // si encuentra una distancia más corta
            v.setDistanciaTemporal(distanciaNueva);
            v.setAnterior(u);

            System.out.println("\n[Relajación] Distancia actualizada para " + v.getNombre()
                    + "\nDesde: " + u.getNombre()
                    + " (Dist: " + u.getDistanciaTemporal() + " km)"
                    + "\nPeso arista: " + peso + " km"
                    + "\nDistancia anterior: " + distanciaActual + " km"
                    + "\nNueva distancia: " + distanciaNueva + " km \n");
        } else {
            System.out.println("\n[Relajación] No se mejora distancia para " + v.getNombre()
                    + " (Actual: " + distanciaActual + ", Nueva: " + distanciaNueva + " km)");
        }
    }

    /**
     * Obtiene los nodos de un grafo.
     *
     * @param grafo
     * @return los nodos del grafo en una lista
     */
    public static List<Nodo> getNodosGrafo(Grafo grafo) {
        List<Nodo> nodos = new ArrayList<>();
        for (LinkedList<Nodo> lista : grafo.getGrafo()) {
            if (!lista.isEmpty()) {
                nodos.add(lista.getFirst());
            }
        }
        return nodos;
    }

    /**
     * Obtiene y muestra la trayectoria desde el origen hasta el nodo destino
     *
     * @param destino Nodo final de la trayectoria
     * @return Lista de nodos en orden desde el origen al destino
     */
    public static List<Nodo> obtenerTrayectoria(Nodo destino) {
        List<Nodo> trayectoria = new ArrayList<>();

        if (destino == null) {
            System.out.println("No existe trayectoria (nodo destino es nulo)");
            return trayectoria;
        }

        System.out.println("\n--- Trayectoria más corta ---");

        // construye la trayectoria (de final a inicio)
        for (Nodo nodo = destino; nodo != null; nodo = nodo.getAnterior()) {
            trayectoria.add(nodo);
        }

        Collections.reverse(trayectoria); // ordenar de inicio a fin

        if (trayectoria.isEmpty()) {
            System.out.println("No se encontró trayectoria válida");
        } else if (trayectoria.size() == 1) {
            System.out.println("El origen y destino son el mismo: " + destino.getNombre());
        } else {
            System.out.println("Distancia total: " + destino.getDistanciaTemporal() + " km");
            System.out.print("Trayectoria: ");

            for (int i = 0; i < trayectoria.size(); i++) {
                System.out.print(trayectoria.get(i).getNombre());
                if (i < trayectoria.size() - 1) {
                    System.out.print(" → ");
                }
            }
            System.out.println();
        }

        return trayectoria;
    }
}
