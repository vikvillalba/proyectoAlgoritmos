/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolDeExpansionMinima;

import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author erika
 */
public class Prim {
    /**
     * Encuentra el MST usando el algoritmo de Prim
     * @param ciudadInicial Nombre de la ciudad donde comenzará el algoritmo
     * @return Lista de aristas que forman el MST
     */
    public static List<Arista> encontrarMST(String ciudadInicial) {
        Grafo grafo = GrafoPueblaUtil.getGrafo();
        List<Arista> mst = new ArrayList<>();
        
        // Verificar que la ciudad inicial existe
        if (grafo.buscarNodo(ciudadInicial) == null) {
            throw new IllegalArgumentException("La ciudad inicial no existe en el grafo");
        }
        
        // Estructuras para el algoritmo
        PriorityQueue<NodoAdy> colaPrioridad = new PriorityQueue<>(
            Comparator.comparingDouble(NodoAdy::getPeso)
        );
        Set<String> enMST = new HashSet<>();
        Map<String, String> conexionMinima = new HashMap<>();
        Map<String, Double> pesoMinimo = new HashMap<>();
        
        // Inicializar estructuras
        for (String municipio : GrafoPueblaUtil.getMunicipios()) {
            pesoMinimo.put(municipio, Double.POSITIVE_INFINITY);
        }
        
        // Comenzar con la ciudad inicial
        pesoMinimo.put(ciudadInicial, 0.0);
        colaPrioridad.add(new NodoAdy(ciudadInicial, 0));
        
        while (!colaPrioridad.isEmpty()) {
            String actual = colaPrioridad.poll().getNombre();
            
            // Evitar procesar nodos ya incluidos
            if (enMST.contains(actual)) continue;
            
            enMST.add(actual);
            
            // Agregar arista al MST (excepto para el nodo inicial)
            if (conexionMinima.containsKey(actual)) {
                mst.add(new Arista(
                    conexionMinima.get(actual), 
                    actual, 
                    pesoMinimo.get(actual)
                ));
            }
            
            // Explorar vecinos
            Nodo nodoActual = grafo.buscarNodo(actual);
            for (NodoAdy vecino : nodoActual.getAdyacentes()) {
                String nombreVecino = vecino.getNombre();
                double peso = vecino.getPeso();
                
                if (!enMST.contains(nombreVecino) && peso < pesoMinimo.get(nombreVecino)) {
                    pesoMinimo.put(nombreVecino, peso);
                    conexionMinima.put(nombreVecino, actual);
                    colaPrioridad.add(new NodoAdy(nombreVecino, peso));
                }
            }
        }
        
        return mst;
    }
    
    /**
     * Muestra el MST encontrado por Prim
     * @param ciudadInicial Ciudad donde comenzó el algoritmo
     */
    public static void mostrarMST(String ciudadInicial) {
        List<Arista> mst = encontrarMST(ciudadInicial);
        double pesoTotal = 0;
        
        System.out.println("\nÁrbol de Expansión Mínima (Prim) comenzando en " + ciudadInicial);
        System.out.println("===========================================================");
        System.out.println("Orden de conexión de municipios:");
        System.out.println("-----------------------------------------------------------");
        
        for (Arista arista : mst) {
            System.out.printf("Conectar %-25s con %-25s (%.1f km)\n", 
                            arista.origen, arista.destino, arista.peso);
            pesoTotal += arista.peso;
        }
        
        System.out.println("-----------------------------------------------------------");
        System.out.printf("Distancia total requerida: %.1f km\n", pesoTotal);
        System.out.println("===========================================================");
    }
}
