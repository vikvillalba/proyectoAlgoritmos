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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author erika
 */
public class Boruvka {
    /**
     * Encuentra el MST usando el algoritmo de Borůvka
     * @return Lista de aristas que forman el MST
     */
    public static List<Arista> encontrarMST() {
        Grafo grafo = GrafoPueblaUtil.getGrafo();
        List<String> municipios = GrafoPueblaUtil.getMunicipios();
        List<Arista> todasAristas = obtenerTodasAristas(grafo);
        List<Arista> mst = new ArrayList<>();
        UnionFind uf = new UnionFind(municipios);
        
        // Continuar hasta que todos los nodos estén conectados
        while (mst.size() < municipios.size() - 1) {
            // Mapa para guardar la arista más barata para cada componente
            Map<String, Arista> aristasMasBaratas = new HashMap<>();
            
            // Encontrar la arista más barata para cada componente
            for (Arista arista : todasAristas) {
                String rootOrigen = uf.find(arista.origen);
                String rootDestino = uf.find(arista.destino);
                
                if (!rootOrigen.equals(rootDestino)) {
                    // Actualizar arista más barata para el componente de origen
                    if (!aristasMasBaratas.containsKey(rootOrigen) || 
                        arista.peso < aristasMasBaratas.get(rootOrigen).peso) {
                        aristasMasBaratas.put(rootOrigen, arista);
                    }
                    
                    // Actualizar arista más barata para el componente de destino
                    if (!aristasMasBaratas.containsKey(rootDestino) || 
                        arista.peso < aristasMasBaratas.get(rootDestino).peso) {
                        aristasMasBaratas.put(rootDestino, arista);
                    }
                }
            }
            
            // Agregar las aristas encontradas al MST
            for (Arista arista : aristasMasBaratas.values()) {
                String rootOrigen = uf.find(arista.origen);
                String rootDestino = uf.find(arista.destino);
                
                if (!rootOrigen.equals(rootDestino)) {
                    mst.add(arista);
                    uf.union(arista.origen, arista.destino);
                }
            }
        }
        
        return mst;
    }
    
    /**
     * Obtiene todas las aristas del grafo sin duplicados
     * @param grafo Grafo de Puebla
     * @return Lista de todas las aristas únicas
     */
    private static List<Arista> obtenerTodasAristas(Grafo grafo) {
        List<Arista> aristas = new ArrayList<>();
        Set<String> aristasAgregadas = new HashSet<>();
        
        for (Nodo nodo : grafo.getNodos()) {
            String origen = nodo.getNombre();
            for (NodoAdy ady : nodo.getAdyacentes()) {
                String destino = ady.getNombre();
                // Crear clave única para evitar duplicados
                String claveArista = origen.compareTo(destino) < 0 ? 
                    origen + ":" + destino : destino + ":" + origen;
                
                if (!aristasAgregadas.contains(claveArista)) {
                    aristas.add(new Arista(origen, destino, ady.getPeso()));
                    aristasAgregadas.add(claveArista);
                }
            }
        }
        
        return aristas;
    }
    
    /**
     * Muestra el MST encontrado por Borůvka
     */
    public static void mostrarMST() {
        List<Arista> mst = encontrarMST();
        double pesoTotal = 0;
        
        System.out.println("\nÁrbol de Expansión Mínima (Borůvka) para el estado de Puebla");
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
