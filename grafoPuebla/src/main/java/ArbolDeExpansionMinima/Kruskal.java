/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolDeExpansionMinima;

import frames.PnlKruskal;
import grafo.Grafo;
import grafo.GrafoPueblaUtil;
import grafo.Nodo;
import grafo.NodoAdy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementación del algoritmo de Kruskal para encontrar el Árbol de Expansión Mínima (MST)
 * @author erika
 */
public class Kruskal {
    
    private static PnlKruskal panelVisualizacion;
    
    public static void setPanelVisualizacion(PnlKruskal panel) {
        panelVisualizacion = panel;
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
            // Notificar al panel que esta arista está siendo evaluada
            if (panelVisualizacion != null) {
                panelVisualizacion.marcarAristaEvaluada(arista.origen, arista.destino);
            }
            
            String rootOrigen = uf.find(arista.origen);
            String rootDestino = uf.find(arista.destino);
            
            if (!rootOrigen.equals(rootDestino)) {
                mst.add(arista);
                uf.union(arista.origen, arista.destino);
                
                // Notificar al panel que esta arista fue agregada al MST
                if (panelVisualizacion != null) {
                    panelVisualizacion.agregarAristaMST(arista.origen, arista.destino, arista.peso);
                }
            }
        }
        
        if (panelVisualizacion != null) {
            panelVisualizacion.setProcesoCompleto();
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
