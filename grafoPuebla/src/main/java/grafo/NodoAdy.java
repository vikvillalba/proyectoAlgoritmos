/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo;

/**
 *
 * @author erika
 */
/**
 * Clase que representa un nodo adyacente en un grafo implementado con listas de adyacencia.
 * Cada instancia contiene información sobre una conexión (arista) entre nodos,
 * incluyendo el nodo destino, peso de la arista y referencias a nodos adyacentes previos/siguientes.
 */
public class NodoAdy {
    private String nombre;    // Nombre del nodo destino de esta arista
    private double peso;     // Peso o distancia de la arista
    private Nodo origen;     // Referencia al nodo origen de esta arista (opcional)
    private NodoAdy anterior; // Referencia al nodo adyacente anterior en la lista
    private NodoAdy siguiente; // Referencia al nodo adyacente siguiente en la lista

    /**
     * Constructor que crea un nuevo nodo adyacente con nombre y peso especificados.
     * @param nombre Nombre del nodo destino de la arista
     * @param peso Peso o distancia de la arista
     */
    public NodoAdy(String nombre, double peso) {
        this.nombre = nombre;
        this.peso = peso;
        this.origen = null;      // Se establece posteriormente si es necesario
        this.anterior = null;    // Inicialmente no tiene nodo anterior
        this.siguiente = null;   // Inicialmente no tiene nodo siguiente
    }

    // Métodos de acceso (getters) y modificación (setters) con documentación:

    /**
     * Obtiene el nombre del nodo destino de la arista.
     * @return Nombre del nodo destino
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el nodo destino de la arista.
     * @param nombre Nuevo nombre del nodo destino
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el peso/distancia de la arista.
     * @return Valor del peso de la arista
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Establece un nuevo peso/distancia para la arista.
     * @param peso Nuevo valor de peso
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Obtiene el nodo origen de esta arista.
     * @return Referencia al nodo origen (puede ser null)
     */
    public Nodo getOrigen() {
        return origen;
    }

    /**
     * Establece el nodo origen de esta arista.
     * @param origen Nodo origen de la arista
     */
    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }

    /**
     * Obtiene el nodo adyacente anterior en la lista.
     * @return Nodo adyacente anterior o null si es el primero
     */
    public NodoAdy getAnterior() {
        return anterior;
    }

    /**
     * Establece el nodo adyacente anterior en la lista.
     * @param anterior Nodo adyacente anterior
     */
    public void setAnterior(NodoAdy anterior) {
        this.anterior = anterior;
    }

    /**
     * Obtiene el nodo adyacente siguiente en la lista.
     * @return Nodo adyacente siguiente o null si es el último
     */
    public NodoAdy getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el nodo adyacente siguiente en la lista.
     * @param siguiente Nodo adyacente siguiente
     */
    public void setSiguiente(NodoAdy siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Método alternativo para obtener el nombre del nodo destino.
     * Equivalente a getNombre(), proporciona semántica más clara para aristas.
     * @return Nombre del nodo destino de la arista
     */
    public String getDestino() {
        return this.nombre;
    }
}