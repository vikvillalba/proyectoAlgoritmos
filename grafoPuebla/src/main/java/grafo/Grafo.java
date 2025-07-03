/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo;


/**
 *
 * @author erika
 */
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un grafo no dirigido implementado con listas de adyacencia.
 * Cada nodo del grafo tiene una lista de nodos adyacentes con sus respectivas distancias.
 */
public class Grafo {
    private LinkedList<Nodo>[] grafo; // Array de listas enlazadas para representar el grafo
    private int cantNodos;           // Cantidad actual de nodos en el grafo
    private int size;                // Tamaño máximo del grafo

    /**
     * Constructor que inicializa el grafo con un tamaño específico.
     * @param size Tamaño máximo del grafo (número máximo de nodos)
     */
    public Grafo(int size) {
        this.grafo = new LinkedList[size];
        this.size = size;
        this.cantNodos = 0;
        
        // Inicializar cada posición del array con una nueva LinkedList
        for(int i = 0; i < size; i++) {
            grafo[i] = new LinkedList<>();
        }
    }
    
    /**
     * Obtiene la estructura completa del grafo.
     * @return Array de LinkedList que representa el grafo
     */
    public LinkedList<Nodo>[] getGrafo() {
        return grafo; 
    }
    
    /**
     * Agrega un nuevo nodo al grafo si no existe previamente.
     * @param nombre Nombre del nodo a agregar
     */
    public void agregarNodo(String nombre) {
        // Verificar si el nodo ya existe
        for (LinkedList<Nodo> lista : grafo) {
            if (!lista.isEmpty() && lista.getFirst().getNombre().equals(nombre)) {
                System.out.println("Nodo ya existe: " + nombre);
                return;
            }
        }

        // Verificar si hay espacio para más nodos
        if (cantNodos >= size) {
            System.out.println("No se pueden agregar más nodos, grafo lleno");
            return;
        }

        // Agregar nodo si no existe y hay espacio
        Nodo localidad = new Nodo(nombre);
        grafo[cantNodos].add(localidad);
        cantNodos++;
    }
    
    /**
     * Elimina un nodo del grafo y todas sus aristas relacionadas.
     * @param nombre Nombre del nodo a eliminar
     */
    public void eliminarNodo(String nombre) {
        boolean encontrado = false;
        
        // Buscar y eliminar el nodo principal
        for (int i = 0; i < cantNodos; i++) {
            if (!grafo[i].isEmpty() && grafo[i].getFirst().getNombre().equals(nombre)) {
                // Eliminar todas las aristas de este nodo
                NodoAdy adyacente = grafo[i].getFirst().getSiguiente();
                while (adyacente != null) {
                    eliminarArista(grafo[i].getFirst(), adyacente);
                    eliminarAristaDeNodo(adyacente.getNombre(), nombre);
                    adyacente = adyacente.getSiguiente();
                }
                
                // Eliminar el nodo
                grafo[i].clear();
                encontrado = true;
                
                // Reorganizar los nodos para no dejar espacios vacíos
                if (i < cantNodos - 1) {
                    grafo[i] = grafo[cantNodos - 1];
                    grafo[cantNodos - 1] = new LinkedList<>();
                }
                cantNodos--;
                break;
            }
        }
        
        if (!encontrado) {
            System.out.println("Nodo no encontrado: " + nombre);
        }
    }
    
    /**
     * Busca un nodo en el grafo por su nombre.
     * @param nombre Nombre del nodo a buscar
     * @return El nodo encontrado o null si no existe
     */
    public Nodo buscarNodo(String nombre) {
        for(int i = 0; i < cantNodos; i++) {
            if (!grafo[i].isEmpty() && grafo[i].getFirst().getNombre().equals(nombre)) {
                return grafo[i].getFirst();
            }
        }
        return null;
    }
    
    /**
     * Agrega un nodo adyacente a la lista de adyacencia de un nodo.
     * @param nodo Nodo al que se le agregará el adyacente
     * @param nuevoAdyacente Nodo adyacente a agregar
     */
    private void agregarNodoAdyacente(Nodo nodo, NodoAdy nuevoAdyacente) {
        // Verificar si la arista ya existe
        NodoAdy aux = nodo.getSiguiente();
        while (aux != null) {
            if (aux.getNombre().equals(nuevoAdyacente.getNombre())) {
                System.out.println("La arista ya existe");
                return;
            }
            aux = aux.getSiguiente();
        }

        // Agregar el nuevo nodo adyacente al final de la lista
        if (nodo.getSiguiente() == null) {
            nodo.setSiguiente(nuevoAdyacente);
        } else {
            aux = nodo.getSiguiente();
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevoAdyacente);
        }
    }
    
    /**
     * Agrega una arista no dirigida entre dos nodos.
     * @param origen Nombre del nodo origen
     * @param destino Nombre del nodo destino
     * @param distancia Distancia entre los nodos
     */
    public void agregarArista(String origen, String destino, double distancia) {
        Nodo nodoOrigen = buscarNodo(origen);
        Nodo nodoDestino = buscarNodo(destino);

        if (nodoOrigen == null || nodoDestino == null) {
            System.out.println("Origen o destino inválido");
            return;
        }

        // Agregar arista en ambas direcciones (grafo no dirigido)
        NodoAdy nodoAdyOrigen = new NodoAdy(destino, distancia);
        agregarNodoAdyacente(nodoOrigen, nodoAdyOrigen);

        NodoAdy nodoAdyDestino = new NodoAdy(origen, distancia);
        agregarNodoAdyacente(nodoDestino, nodoAdyDestino);
    }
    
    /**
     * Elimina una arista del grafo (en ambas direcciones).
     * @param origen Nodo origen de la arista
     * @param arista Nodo adyacente que representa la arista a eliminar
     */
    public void eliminarArista(Nodo origen, NodoAdy arista) {
        if (origen == null || arista == null) {
            System.out.println("Parámetros inválidos");
            return;
        }
        
        NodoAdy prev = null;
        NodoAdy actual = origen.getSiguiente();
        
        // Buscar y eliminar la arista en el nodo origen
        while (actual != null) {
            if (actual.getNombre().equals(arista.getNombre())) {
                if (prev == null) {
                    origen.setSiguiente(actual.getSiguiente());
                } else {
                    prev.setSiguiente(actual.getSiguiente());
                }
                break;
            }
            prev = actual;
            actual = actual.getSiguiente();
        }
        
        // Eliminar la arista en el nodo destino (grafo no dirigido)
        Nodo nodoDestino = buscarNodo(arista.getNombre());
        if (nodoDestino != null) {
            eliminarAristaDeNodo(arista.getNombre(), origen.getNombre());
        }
    }
    
    /**
     * Método auxiliar para eliminar una arista de un nodo específico.
     * @param nombreNodo Nombre del nodo del que se eliminará la arista
     * @param nombreArista Nombre del nodo adyacente a eliminar
     */
    private void eliminarAristaDeNodo(String nombreNodo, String nombreArista) {
        Nodo nodo = buscarNodo(nombreNodo);
        if (nodo == null) return;
        
        NodoAdy prev = null;
        NodoAdy actual = nodo.getSiguiente();
        
        while (actual != null) {
            if (actual.getNombre().equals(nombreArista)) {
                if (prev == null) {
                    nodo.setSiguiente(actual.getSiguiente());
                } else {
                    prev.setSiguiente(actual.getSiguiente());
                }
                break;
            }
            prev = actual;
            actual = actual.getSiguiente();
        }
    }
    
    /**
     * Obtiene una lista iterable de todos los nodos del grafo.
     * @return Lista de nodos del grafo
     */
    public Iterable<Nodo> getNodos() {
        List<Nodo> nodos = new ArrayList<>();
        for (int i = 0; i < cantNodos; i++) {
            if (!grafo[i].isEmpty()) {
                nodos.add(grafo[i].getFirst());
            }
        }
        return nodos;
    }


}
