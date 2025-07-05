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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del algoritmo de Kruskal para encontrar el Árbol de Expansión Mínima (MST)
 * @author erika
 */
public class Kruskal {
    
    /**
     * Clase interna que representa una arista del grafo con su origen, destino y peso.
     * Implementa Comparable para permitir ordenamiento por peso.
     */
    private static class Arista implements Comparable<Arista> {
        String origen;
        String destino;
        double peso;
         
        /**
         * Constructor de la arista.
         * @param origen Nombre del municipio origen
         * @param destino Nombre del municipio destino
         * @param peso Distancia entre los municipios en km
         */
        public Arista(String origen, String destino, double peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }
        
        /**
         * Compara esta arista con otra basándose en su peso.
         * @param otra Otra arista a comparar
         * @return -1, 0 o 1 si esta arista es menor, igual o mayor que la otra
         */
        @Override
        public int compareTo(Arista otra) {
            return Double.compare(this.peso, otra.peso);
        }
    }
    
    /**
     * Implementación de la estructura Union-Find para manejar conjuntos disjuntos.
     * Permite unir conjuntos y encontrar el representante de un conjunto eficientemente.
     */
    private static class UnionFind {
        private Map<String, String> parent;
        private Map<String, Integer> rank;
        
        /**
         * Inicializa la estructura con todos los municipios como conjuntos disjuntos.
         * @param municipios Lista de los municipios
         */
        public UnionFind(List<String> municipios) {
            parent = new HashMap<>();
            rank = new HashMap<>();
            
            for (String municipio : municipios) {
                parent.put(municipio, municipio);
                rank.put(municipio, 0);
            }
        }
        
        /**
         * Encuentra el representante (raíz) del conjunto al que pertenece un municipio.
         * @param item Municipio a buscar
         * @return El representante del conjunto
         */
        public String find(String item) {
            if (!parent.get(item).equals(item)) {
                parent.put(item, find(parent.get(item))); 
            }
            return parent.get(item);
        }
        
        /**
         * Une dos conjuntos usando el criterio de unión por rango.
         * @param set1 Primer municipio a unir
         * @param set2 Segundo municipio a unir
         */
        public void union(String set1, String set2) {
            String root1 = find(set1);
            String root2 = find(set2);
            // Si ya estan en el mismo conjunto no hace nada
            if (root1.equals(root2)) return;
            // Union por rango para mantener el arbol balanceado
            if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
    /**
     * Encuentra el arbol de expansion minima (MST) usando el algoritmo de Kruskal.
     * @return Lista de aristas que forman el MST
     */
    public static List<Arista> encontrarMST() {
        Grafo grafo = GrafoPueblaUtil.getGrafo();
        List<String> municipios = GrafoPueblaUtil.getMunicipios();
        List<Arista> aristas = new ArrayList<>();
        List<Arista> mst = new ArrayList<>();
        
        // Recoger todas las aristas del grafo
        for (Nodo nodo : grafo.getNodos()) {
            String origen = nodo.getNombre();
            for (NodoAdy ady : nodo.getAdyacentes()) {
                // Para evitar duplicados (grafo no dirigido)
                if (origen.compareTo(ady.getNombre()) < 0) {
                    aristas.add(new Arista(origen, ady.getNombre(), ady.getPeso()));
                }
            }
        }
        
        // Ordenar aristas por peso
        Collections.sort(aristas);
        
        UnionFind uf = new UnionFind(municipios);
        
        // Algoritmo de Kruskal
        for (Arista arista : aristas) {
            String rootOrigen = uf.find(arista.origen);
            String rootDestino = uf.find(arista.destino);
            
            if (!rootOrigen.equals(rootDestino)) {
                mst.add(arista);
                uf.union(arista.origen, arista.destino);
            }
        }
        
        return mst;
    }
    /**
     * Muestra por consola el arbol de expansion minima con formato.
     * Incluye todas las conexiones esenciales y la distancia total requerida.
     */
    public static void mostrarMST() {
        List<Arista> mst = encontrarMST();
        double pesoTotal = 0;
        
        System.out.println("\nÁrbol de Expansión Mínima (Kruskal) para el estado de Puebla");
        System.out.println("===========================================================");
        System.out.println("Conexiones esenciales para conectar todos los municipios:");
        System.out.println("-----------------------------------------------------------");
        
        for (Arista arista : mst) {
            System.out.printf("%-25s <---> %-25s (%.1f km)\n", 
                            arista.origen, arista.destino, arista.peso);
            pesoTotal += arista.peso;
        }
        
        System.out.println("-----------------------------------------------------------");
        System.out.printf("Distancia total requerida: %.1f km\n", pesoTotal);
        System.out.println("===========================================================");
    }
    
   
}
