/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolDeExpansionMinima;

import frames.PnlPrim;
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
    
    private static PnlPrim panelVisualizacion;
    
    public static void setPanelVisualizacion(PnlPrim panel) {
        panelVisualizacion = panel;
    }
    /**
     * Encuentra el MST usando el algoritmo de Prim
     * @param ciudadInicial Nombre de la ciudad donde comenzará el algoritmo
     * @return Lista de aristas que forman el MST
     */
    public static List<Arista> encontrarMST(String ciudadInicial) {
        Grafo grafo = GrafoPueblaUtil.getGrafo(); // 1 + 1
        List<Arista> mst = new ArrayList<>(); // 1 + 1
        
        // Verificar que la ciudad inicial existe
        if (grafo.buscarNodo(ciudadInicial) == null) { // 8n + 4 + 1
            throw new IllegalArgumentException("La ciudad inicial no existe en el grafo"); // 2
        }
        
        // Estructuras para el algoritmo
        PriorityQueue<NodoAdy> colaPrioridad = new PriorityQueue<>( // 2
            Comparator.comparingDouble(NodoAdy::getPeso) // 2
        );
        Set<String> enMST = new HashSet<>(); // 2
        Map<String, String> conexionMinima = new HashMap<>(); // 2
        Map<String, Double> pesoMinimo = new HashMap<>(); // 2
        
        // Inicializar estructuras
        for (String municipio : GrafoPueblaUtil.getMunicipios()) { // n + n
            pesoMinimo.put(municipio, Double.POSITIVE_INFINITY); // n
        }
        
         // Comenzar con la ciudad inicial
        pesoMinimo.put(ciudadInicial, 0.0); // 1
        colaPrioridad.add(new NodoAdy(ciudadInicial, 0)); // 6
        
        if (panelVisualizacion != null) { // 1
            panelVisualizacion.agregarNodoAMST(ciudadInicial); // 4
        }

        while (!colaPrioridad.isEmpty()) { // n + 1
            String actual = colaPrioridad.poll().getNombre(); // 3n 
            
            if (enMST.contains(actual)) continue; // 2n
            
            enMST.add(actual); //n
            
            // Notificar al panel
            if (panelVisualizacion != null && !actual.equals(ciudadInicial)) { //1
                panelVisualizacion.agregarNodoAMST(actual); // 4
            }

            // Agregar arista al MST (excepto para el nodo inicial)
            if (conexionMinima.containsKey(actual)) { //2n
                mst.add(new Arista(
                    conexionMinima.get(actual), 
                    actual, 
                    pesoMinimo.get(actual))
                ); // 4n
                
                if (panelVisualizacion != null) { // n
                    panelVisualizacion.agregarAristaAMST( // 7n
                        conexionMinima.get(actual), 
                        actual, 
                        pesoMinimo.get(actual)
                    );
                }
            }

            // Explorar vecinos
            Nodo nodoActual = grafo.buscarNodo(actual); // 8n + 5
            for (NodoAdy vecino : nodoActual.getAdyacentes()) { // n + 4n + 5
                String nombreVecino = vecino.getNombre(); // 2n
                double peso = vecino.getPeso(); // 2n
                
                if (!enMST.contains(nombreVecino) && peso < pesoMinimo.get(nombreVecino)) { // 2n
                    pesoMinimo.put(nombreVecino, peso); //n
                    conexionMinima.put(nombreVecino, actual); //n
                    colaPrioridad.add(new NodoAdy(nombreVecino, peso)); // 6n
                    
                    if (panelVisualizacion != null) { // n
                        panelVisualizacion.marcarAristaEvaluada(actual, nombreVecino); // 4n
                    }
                }
            }
        }
        
        if (panelVisualizacion != null) { // 1
            panelVisualizacion.setProcesoCompleto(); // 2
        }
        
        return mst; // 1
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
