/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author erika
 */


/**
 * Clase que representa un nodo en un grafo implementado con listas de adyacencia.
 * Cada nodo contiene un nombre y una referencia a su primer nodo adyacente.
 */
public class Nodo {
    private String nombre;       // Nombre identificador del nodo
    private NodoAdy siguiente;  // Referencia al primer nodo adyacente en la lista de adyacencia

    /**
     * Constructor que crea un nuevo nodo con el nombre especificado.
     * @param nombre Nombre identificador del nodo
     */
    public Nodo(String nombre) {
        this.nombre = nombre;
        this.siguiente = null;  // Inicialmente no tiene nodos adyacentes
    }

    /**
     * Obtiene el nombre del nodo.
     * @return Nombre del nodo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el nodo.
     * @param nombre Nuevo nombre del nodo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el primer nodo adyacente en la lista de adyacencia.
     * @return Primer nodo adyacente o null si no tiene adyacentes
     */
    public NodoAdy getSiguiente() {
        return siguiente;
    }

    /**
     * Establece/agrega un nuevo nodo adyacente al final de la lista de adyacencia.
     * @param nodo Nodo adyacente a agregar
     */
    public void setSiguiente(NodoAdy nodo) {
        if (this.siguiente == null) {
            // Si no hay adyacentes, este será el primero
            this.siguiente = nodo;
        } else {
            // Si ya hay adyacentes, recorrer hasta el final y agregar el nuevo
            NodoAdy aux = siguiente;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nodo);
            nodo.setAnterior(aux);  // Establecer referencia bidireccional
        }
    }

    /**
     * Obtiene una lista con todos los nodos adyacentes a este nodo.
     * @return Lista de nodos adyacentes (puede estar vacía)
     */
    public List<NodoAdy> getAdyacentes() {
        List<NodoAdy> adyacentes = new ArrayList<>();
        NodoAdy aux = this.siguiente;
        
        // Recorrer toda la lista de adyacencia
        while (aux != null) {
            adyacentes.add(aux);  // Agregar cada adyacente a la lista
            aux = aux.getSiguiente();
        }
        
        return adyacentes;
    }
}