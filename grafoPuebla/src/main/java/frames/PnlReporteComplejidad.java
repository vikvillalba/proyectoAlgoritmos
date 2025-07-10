/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frames;

/**
 * <p>
 * Panel que muestra la descripción y las complejidades temporal y espacial
 * (mejor, promedio y peor caso) de un algoritmo de grafos elegido en la
 * aplicación.</p>
 *
 * <p>
 * El panel se compone de un título con el nombre del algoritmo y un bloque
 * <i>HTML</i> incrustado en un {@code JLabel} para permitir formato
 * enriquecido.</p>
 *
 * <p>
 * <b>Uso:</b> la ventana principal crea una instancia pasando su propia
 * referencia y la cadena identificadora del algoritmo, por ejemplo
 * {@code new PnlReporteComplejidad(this, "DIJKSTRA")}.</p>
 *
 */
public class PnlReporteComplejidad extends javax.swing.JPanel {

    /**
     * Ventana principal que invoca al panel (por si se requiere
     * retro‑navegación).
     */
    private final FrmPrincipal inicio;

    /**
     * Identificador del algoritmo (en mayúsculas) usado por el {@code switch}.
     */
    private final String algoritmo;

    /**
     * Construye el panel de reporte.
     *
     * @param inicio referencia a la ventana principal
     * @param algoritmo nombre del algoritmo (no sensible a
     * mayúsculas/minúsculas)
     */
    public PnlReporteComplejidad(FrmPrincipal inicio, String algoritmo) {
        initComponents();
        this.inicio = inicio;
        this.algoritmo = algoritmo;
        setInfo();
    }

    /**
     * Establece el texto del título ({@code algoritmoLabel}) y de la
     * descripción ({@code textoDescripcionLabel}) según el algoritmo
     * seleccionado.
     *
     * <p>
     * La descripción incluye complejidades temporal y espacial en formato
     * <i>HTML</i> para un mejor formato dentro del {@code JLabel}.</p>
     */
    private void setInfo() {
        switch (algoritmo) {
            case "BFS":
                algoritmoLabel.setText("BFS – Breadth - First Search");
                textoDescripcionLabel.setText(
                        html(
                                "El <b>recorrido en anchura (BFS)</b> visita el grafo por niveles "
                                + "usando una cola FIFO. Garantiza encontrar la distancia mínima "
                                + "(en número de aristas) desde el origen hasta cada vértice alcanzable.",
                                "O(V + E)", // peor caso
                                "O(V + E)", // mejor caso
                                "O(V + E)" // caso promedio
                        ));
                areaCodigo.setText("\n"
                        + "    /**\n"
                        + "     * Ejecuta el algoritmo BFS desde un nodo de inicio hasta un nodo destino.\n"
                        + "     *\n"
                        + "     * @param inicio nombre del nodo de origen\n"
                        + "     * @param fin nombre del nodo destino\n"
                        + "     * @return una lista enlazada con los nodos que forman la ruta encontrada, o\n"
                        + "     * una lista vacía si no existe camino entre ambos\n"
                        + "     */\n"
                        + "    public static LinkedList<Nodo> ejecutar(String inicio, String fin) { // 1\n"
                        + "\n"
                        + "        if (panelVisualizacion != null) {                  // 1\n"
                        + "            panelVisualizacion.reiniciarProceso();         // 1\n"
                        + "        }\n"
                        + "\n"
                        + "        Grafo grafo = GrafoPueblaUtil.getGrafo();          // 1\n"
                        + "        Nodo origen = grafo.buscarNodo(inicio);            // 8n + 4 + 1\n"
                        + "        Nodo destino = grafo.buscarNodo(fin);              // 8n + 4 + 1\n"
                        + "\n"
                        + "        if (origen == null || destino == null) {           // 1\n"
                        + "            return new LinkedList<>();                     // 1\n"
                        + "        }\n"
                        + "\n"
                        + "        Queue<Nodo> cola = new ArrayDeque<>();             // 1\n"
                        + "        Set<String> visitados = new HashSet<>();           // 1\n"
                        + "        Map<String, Nodo> antecesor = new HashMap<>();     // 1\n"
                        + "\n"
                        + "        cola.add(origen);                                  // 1\n"
                        + "        visitados.add(origen.getNombre());                 // 1\n"
                        + "        marcarNodo(origen.getNombre());                    // 1\n"
                        + "\n"
                        + "        while (!cola.isEmpty()) {                          // hasta n veces → n\n"
                        + "\n"
                        + "            Nodo actual = cola.poll();                     // n\n"
                        + "\n"
                        + "            for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) { // hasta grado(n) veces → en total 2n^2\n"
                        + "                Nodo vecino = grafo.buscarNodo(ady.getNombre());        // 8n^2 + 5n\n"
                        + "                if (vecino != null && visitados.add(vecino.getNombre())) { // 2n\n"
                        + "\n"
                        + "                    antecesor.put(vecino.getNombre(), actual);          // n\n"
                        + "                    cola.add(vecino);                                   // n\n"
                        + "\n"
                        + "                    marcarArista(actual.getNombre(), vecino.getNombre()); // n\n"
                        + "                    marcarNodo(vecino.getNombre());                     // n\n"
                        + "                    pausar();                                           // n\n"
                        + "                }\n"
                        + "            }\n"
                        + "        }\n"
                        + "\n"
                        + "        LinkedList<Nodo> ruta = reconstruirRuta(origen, destino, antecesor); // 1\n"
                        + "        if (panelVisualizacion != null) {                    // 1\n"
                        + "            panelVisualizacion.setRutaFinal(ruta);           // 1\n"
                        + "        }\n"
                        + "        return ruta;                                          // 1\n"
                        + "    }");
                break;

            case "DFS":
                algoritmoLabel.setText("DFS – Depth - First Search");
                textoDescripcionLabel.setText(
                        html(
                                "El <b>recorrido en profundidad (DFS)</b> explora tan lejos como "
                                + "sea posible por cada rama antes de retroceder, usando recursión "
                                + "o una pila explícita.",
                                "O(V + E)",
                                "O(V + E)",
                                "O(V + E)"
                        ));
                areaCodigo.setText("");
                break;

            case "DIJKSTRA":
                algoritmoLabel.setText("Dijkstra – Ruta más corta");
                textoDescripcionLabel.setText(
                        html(
                                "Calcula la ruta más corta desde un vértice origen hasta un destino específico en grafos con pesos no negativos, usando una cola de prioridad sin optimización.",
                                "O(n²) – debido al uso de PriorityQueue con operaciones costosas como remove() y add()",
                                "Ω(n) – si el destino se encuentra en las primeras iteraciones",
                                "Θ(n²)"
                        ));

                areaCodigo.setText("""
                                       /**
                                        * c\u00e1lculo de la ruta m\u00e1s corta entre un par de nodos implementando el algoritmo Dijkstra.
                                        *
                                        * @param inicio nodo desde el que inicia la trayectoria.
                                        * @param fin nodo hasta al que llega la trayectoria.
                                        * @return lista de nodos que representa la trayectoria de menor peso entre ambos v\u00e9rtices
                                        * T(n) = 20n\u00b2 + 11n + 20
                                        */
                                       public static LinkedList<Nodo> ejecutar(String inicio, String fin) {
                                           //                      1
                                           if (panelVisualizacion != null) {
                                               //                1 
                                               panelVisualizacion.reiniciarProceso();
                                           }
                                           //          1                 1 
                                           Grafo grafo = GrafoPueblaUtil.getGrafo(); 
                                           //          1      1        
                                           Nodo origen = grafo.buscarNodo(inicio);
                                           //           1      1
                                           Nodo destino = grafo.buscarNodo(fin);
                                           //          1       1          1
                                           if (origen == null || destino == null) {
                                               //    1
                                               System.out.println("Alguno de los nodos es nulo o incorrecto.");
                                               //  1   1 
                                               return new LinkedList<>();
                                           }
                                           //    1
                                           System.out.println("Calculando ruta: " + inicio + " -> " + fin);
                                           //                    1      1
                                           List<Nodo> grafoNodos = grafo.getNodosGrafo();
                                           //                       1   1                           1                     1
                                           PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingDouble(Nodo::getDistanciaTemporal));
                                           //  1
                                           cola.addAll(grafoNodos);
                                           //          4n + 3          
                                           RutaMasCorta.initializeSingleSource(grafoNodos, origen);
                                           //                     1
                                           if (panelVisualizacion != null) {
                                               //                            1
                                               actualizarVisualizacion(origen.getNombre());
                                           }
                                           //     2n + 1
                                           while (!cola.isEmpty()) {
                                               //          n     n
                                               Nodo actual = cola.poll();
                                               // n                              
                                               System.out.print("\\nProcesando: " + actual.getNombre());
                                               //                      n
                                               if (panelVisualizacion != null) {
                                                   // n                         n
                                                   actualizarVisualizacion(actual.getNombre());
                                               }
                                               //        n   
                                               if (actual.equals(destino)) {
                                                   // n
                                                   System.out.println("\\nDestino alcanzado");
                                                   //
                                                   // 1
                                                   break;
                                               }
                                               //                n
                                               NodoAdy adyacente = actual.getSiguiente();
                                               //               n\u00b2  n 
                                               while (adyacente != null) {
                                                   //  n\u00b2
                                                   System.out.print("\\nProcesando nodo vecino: " + adyacente.getNombre());
                                                   //                     n\u00b2
                                                   if (panelVisualizacion != null) {
                                                       // n\u00b2                      n\u00b2                     n\u00b2
                                                       marcarAristaVisitada(actual.getNombre(), adyacente.getNombre());
                                                   }
                                                   //          n\u00b2     n\u00b2       
                                                   Nodo vecino = grafo.buscarNodo(adyacente.getNombre());
                                                   //         n\u00b2 
                                                   if (vecino != null) {
                                                       //                       n\u00b2      n\u00b2
                                                       double distanciaAnterior = vecino.getDistanciaTemporal();
                                                       //          9n\u00b2                              n\u00b2
                                                       RutaMasCorta.relax(actual, vecino, adyacente.getPeso());
                                                       //        n\u00b2                      n\u00b2
                                                       if (vecino.getDistanciaTemporal() < distanciaAnterior) {
                                                           //  n\u00b2
                                                           cola.remove(vecino);
                                                           //  n\u00b2
                                                           cola.add(vecino);
                                                       }
                                                   }
                                                   //       n\u00b2          n\u00b2
                                                   adyacente = adyacente.getSiguiente();
                                                   //                     n\u00b2
                                                   if (panelVisualizacion != null) {
                                                       try {
                                                           // n\u00b2
                                                           Thread.sleep(300);
                                                       } catch (InterruptedException e) {
                                                           // n\u00b2
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               }
                                           }
                                           // 1
                                           System.out.println("Procesamiento completado.");
                                           //                    1              1 
                                           LinkedList<Nodo> ruta = RutaMasCorta.obtenerTrayectoria(destino);
                                           //                     1 
                                           if (panelVisualizacion != null) {
                                               //                1
                                               panelVisualizacion.setRutaFinal(ruta);
                                           }
                                           // 1
                                           return ruta;
                                           
                                       }""");
                break;

            case "BELLMANFORD":
            case "BELLMAN-FORD":
                algoritmoLabel.setText("Bellman - Ford – Ruta más corta");
                textoDescripcionLabel.setText(
                        html(
                                "Permite aristas con pesos negativos. Relaja todas las aristas |V|−1 veces "
                                + "y puede finalizar antes si en una pasada no se producen cambios (early stopping).",
                                "O(n³) (peor caso – grafo denso con búsqueda eficiente de nodos)",
                                "Ω(n²) (mejor caso – no cambia ninguna distancia tras la primera iteración)",
                                "Θ(n³) (caso promedio – finalización anticipada en iteraciones)"
                        ));

                areaCodigo.setText("""
                                       /**
                                        * c\u00e1lculo de la ruta m\u00e1s corta entre un par de nodos implementando el
                                        * algoritmo Bellman-Ford.
                                        *
                                        * @param inicio nodo desde el que inicia la trayectoria.
                                        * @param fin nodo hasta al que llega la trayectoria.
                                        * @return lista de nodos que representa la trayectoria de menor peso entre
                                        * ambos v\u00e9rtices
                                        * T(n) = 18n³ + 9² + 11n +45
                                        */
                                       public static LinkedList<Nodo> ejecutar(String inicio, String fin) {
                                           // 1
                                           if (panelVisualizacion != null) {
                                               // 1
                                               panelVisualizacion.reiniciarProceso(); 
                                           }
                                           // 2
                                           Grafo grafo = GrafoPueblaUtil.getGrafo(); 
                                           // 2
                                           Nodo origen = grafo.buscarNodo(inicio);
                                           // 2
                                           Nodo destino = grafo.buscarNodo(fin); 
                                           // 3
                                           if (origen == null || destino == null) {
                                               // 1
                                               System.out.println("Error: Nodos no encontrados");
                                               // 2
                                               return new LinkedList<>();
                                           }
                                           // 2
                                           List<Nodo> nodos = grafo.getNodosGrafo(); 
                                           // 4n + 3 
                                           RutaMasCorta.initializeSingleSource(nodos, origen); 
                                           // 1
                                           if (panelVisualizacion != null) {
                                               // 1
                                               panelVisualizacion.nuevaIteracion(0); 
                                               // 2
                                               panelVisualizacion.marcarNodoVisitado(origen.getNombre()); 
                                           }
                                           // 1    2n + 2  n
                                           for (int i = 1; i < nodos.size(); i++) {
                                               // n
                                               boolean cambios = false; 
                                               // n
                                               if (panelVisualizacion != null) {
                                                   // n
                                                   panelVisualizacion.nuevaIteracion(i); 
                                               }
                                               // n
                                               System.out.println("\\n--- Iteraci\u00f3n " + i + " ---"); 
                                               // 2n\u00b2 + n
                                               for (Nodo actual : nodos) {
                                                   // n\u00b2
                                                   if (panelVisualizacion != null) {
                                                       // 2n\u00b2
                                                       panelVisualizacion.marcarNodoVisitado(actual.getNombre()); 
                                                   }
                                                   //     2n\u00b2                                 n\u00b2+1          n\u00b2      
                                                   for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
                                                       //          n\u00b3     n\u00b3             n\u00b3
                                                       Nodo vecino = grafo.buscarNodo(ady.getNombre()); 
                                                       // n\u00b3
                                                       if (vecino != null) {
                                                           // n\u00b3
                                                           if (panelVisualizacion != null) {
                                                               // 3n\u00b3
                                                               panelVisualizacion.marcarAristaVisitada(actual.getNombre(), vecino.getNombre()); // 1
                                                           }
                                   
                                                           // 2n\u00b3
                                                          
                                                           double distanciaAnterior = vecino.getDistanciaTemporal(); // 1
                                                           // 10n\u00b3
                                                           RutaMasCorta.relax(actual, vecino, ady.getPeso()); // 9
                                   
                                                           // 2n\u00b3
                                                           if (vecino.getDistanciaTemporal() < distanciaAnterior) {
                                                               // n\u00b3
                                                               cambios = true; // 1
                                                           }
                                   
                                                           try {
                                                               // n\u00b3
                                                               Thread.sleep(300); // 1
                                                           } catch (InterruptedException e) {
                                                               // n\u00b3
                                                               e.printStackTrace(); // 1
                                                           }
                                                       }
                                                   }
                                               }
                                   
                                               // n
                                               if (!cambios) {
                                                   // n
                                                   System.out.println("Sin cambios en iteraci\u00f3n " + i + ", terminando procesamiento."); 
                                                   // 1
                                                   break; 
                                               }
                                           }
                                   
                                           // 1
                                           boolean cicloNegativo = false; 
                                           // 2n + 1
                                           for (Nodo actual : nodos) {
                                               // 2n                                     n\u00b2 + n        n\u00b2
                                               for (NodoAdy ady = actual.getSiguiente(); ady != null; ady = ady.getSiguiente()) {
                                                   // 3n\u00b2
                                                   Nodo vecino = grafo.buscarNodo(ady.getNombre()); 
                                                   // 7n\u00b2
                                                   if (vecino != null
                                                           && actual.getDistanciaTemporal() + ady.getPeso() < vecino.getDistanciaTemporal()) {
                                                       // n\u00b2
                                                       cicloNegativo = true; 
                                                       // 1
                                                       break;
                                                   }
                                               }
                                               // n
                                               if (cicloNegativo) {
                                                   // 1
                                                   break; 
                                               }
                                           }
                                   
                                           // 1
                                           if (cicloNegativo) {
                                               // 1
                                               System.out.println("El grafo contiene un ciclo de peso negativo"); 
                                               // 1
                                               if (panelVisualizacion != null) {
                                                   // 1
                                                   panelVisualizacion.mostrarError("Ciclo negativo detectado"); 
                                               }
                                               // 2
                                               return new LinkedList<>(); 
                                           }
                                           // 2
                                           LinkedList<Nodo> ruta = RutaMasCorta.obtenerTrayectoria(destino); // 1
                                           // 1
                                           if (panelVisualizacion != null) {
                                               // 1
                                               panelVisualizacion.setRutaFinal(ruta); // 1
                                           }
                                           // 1
                                           return ruta; // 1
                                       }""");
                break;

            case "KRUSKAL":
                algoritmoLabel.setText("Kruskal – Árbol de Expansión Mínima");
                textoDescripcionLabel.setText(
                        html(
                                "Encuentra el árbol de expansión mínima ordenando las aristas por peso e insertándolas si no forman ciclo, "
                                + "utilizando la estructura <i>Union‑Find</i> sin optimización avanzada.",
                                "O(n²) (peor caso – recorrido de todas las aristas en grafos densos)",
                                "Ω(n log n) (mejor caso – pocas aristas, sin ciclos)",
                                "Θ(n²) (caso promedio – grafos moderadamente densos)"
                        ));

                areaCodigo.setText("    /**\n"
                        + "     * Encuentra el árbol de expansión mínima (MST) usando el algoritmo de\n"
                        + "     * Kruskal.\n"
                        + "     *\n"
                        + "     * @return Lista de aristas que forman el MST\n"
                        + "     */\n"
                        + "    public static List<Arista> encontrarMST() {\n"
                        + "        // 1  1  : 2\n"
                        + "        Grafo grafo = GrafoPueblaUtil.getGrafo();\n"
                        + "        // 1  1  : 2\n"
                        + "        List<String> municipios = GrafoPueblaUtil.getMunicipios();\n"
                        + "        // 1  1  : 2\n"
                        + "        List<Arista> aristas = new ArrayList<>();\n"
                        + "        // 1  1  : 2\n"
                        + "        List<Arista> mst = new ArrayList<>();\n"
                        + "        //  n+1  n  : 2n + 1\n"
                        + "        for (Nodo nodo : grafo.getNodos()) {\n"
                        + "            // n  n  : 2n\n"
                        + "            String origen = nodo.getNombre();\n"
                        + "            //  n²+n  n²  : 2n² + n\n"
                        + "            for (NodoAdy ady : nodo.getAdyacentes()) {\n"
                        + "                // n²\n"
                        + "                if (origen.compareTo(ady.getNombre()) < 0) {\n"
                        + "                    // n²\n"
                        + "                    aristas.add(new Arista(origen, ady.getNombre(), ady.getPeso()));\n"
                        + "                }\n"
                        + "            }\n"
                        + "        }\n"
                        + "        //         nlogn\n"
                        + "        Collections.sort(aristas);\n"
                        + "        //           1        \n"
                        + "        UnionFind uf = new UnionFind(municipios);\n"
                        + "        \n"
                        + "        //          n+1   n           : 2n + 1\n"
                        + "        for (Arista arista : aristas) {\n"
                        + "            //                      n\n"
                        + "            if (panelVisualizacion != null) {\n"
                        + "               //                n                           n               n \n"
                        + "                panelVisualizacion.marcarAristaEvaluada(arista.origen, arista.destino);\n"
                        + "            }\n"
                        + "            //                n    n          n\n"
                        + "            String rootOrigen = uf.find(arista.origen);\n"
                        + "            //                 n   n           n\n"
                        + "            String rootDestino = uf.find(arista.destino);\n"
                        + "            //  n          n\n"
                        + "            if (!rootOrigen.equals(rootDestino)) {\n"
                        + "                // n\n"
                        + "                mst.add(arista); \n"
                        + "                // n           n              n\n"
                        + "                uf.union(arista.origen, arista.destino);\n"
                        + "                //                      n\n"
                        + "                if (panelVisualizacion != null) {\n"
                        + "\n"
                        + "                    //                 n                      n              n               n      \n"
                        + "                    panelVisualizacion.agregarAristaMST(arista.origen, arista.destino, arista.peso);\n"
                        + "                }\n"
                        + "            }\n"
                        + "        }\n"
                        + "        //                     1\n"
                        + "        if (panelVisualizacion != null) {\n"
                        + "            //                 1\n"
                        + "            panelVisualizacion.setProcesoCompleto();\n"
                        + "        }\n"
                        + "        //   1\n"
                        + "        return mst;\n"
                        + "    }");
                break;

            case "PRIM":
                algoritmoLabel.setText("Prim – Árbol de Expansión Mínima");
                textoDescripcionLabel.setText(
                        html(
                                "Inicia desde un vértice y expande el árbol de expansión mínima agregando la arista más ligera hacia un nodo no visitado, usando una cola de prioridad sin heap binario.",
                                "O(n²) (peor caso – debido a exploración lineal en estructuras como la cola y mapas)",
                                "Ω(1) (mejor caso – grafo con un solo nodo y sin vecinos)",
                                "Θ(n²) (caso promedio – grafos con múltiples adyacencias y sin heap optimizado)"
                        )
                );

                areaCodigo.setText("""
                                       /**
                                        * Encuentra el MST usando el algoritmo de Prim
                                        * @param ciudadInicial Nombre de la ciudad donde comenzar\u00e1 el algoritmo
                                        * @return Lista de aristas que forman el MST
                                        * T(n) = 67n + 35
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
                                       }""");
                break;

            case "BORUVKA":
                algoritmoLabel.setText("Borůvka – Árbol de Expansión Mínima");
                textoDescripcionLabel.setText(
                        html(
                                "Busca iterativamente la arista más barata que conecta cada componente, uniendo varios nodos en paralelo por fase. Utiliza estructuras eficientes como Union-Find y mapas de mínima conexión.",
                                "O(log n × (m + n)) (peor caso – múltiples fases uniendo componentes y recorriendo todas las aristas en cada una)",
                                "Ω(m) (mejor caso – grafo ya casi conectado, requiere solo una fase de selección de aristas)",
                                "Θ(log n × (m + n)) (caso promedio – unión progresiva de componentes con aristas dispersas)"
                        )
                );

                areaCodigo.setText("    /** \n"
                        + "\n"
                        + "     * Encuentra el MST usando el algoritmo de Borůvka \n"
                        + "\n"
                        + "     * @return Lista de aristas que forman el MST \n"
                        + "\n"
                        + "     */ \n"
                        + "\n"
                        + "    public static List<Arista> encontrarMST() { \n"
                        + "\n"
                        + "        Grafo grafo = GrafoPueblaUtil.getGrafo();      // 1 op \n"
                        + "\n"
                        + "        List<String> municipios = GrafoPueblaUtil.getMunicipios();         // n elementos \n"
                        + "\n"
                        + "        List<Arista> todasAristas = obtenerTodasAristas(grafo);           // 2 + n + 10m \n"
                        + "\n"
                        + "        List<Arista> mst = new ArrayList<>();                                                     // 1 op \n"
                        + "\n"
                        + "        UnionFind uf = new UnionFind(municipios);                                    // n inicializaciones \n"
                        + "\n"
                        + "         \n"
                        + "\n"
                        + "        while (mst.size() < municipios.size() - 1) {                      // Este ciclo se ejecuta log₂(n) veces                                                                                                   -                                                                                                                                    en el peor caso \n"
                        + "\n"
                        + "            Map<String, Arista> aristasMasBaratas = new HashMap<>();     // 1 \n"
                        + "\n"
                        + "             \n"
                        + "\n"
                        + "            for (Arista arista : todasAristas) {                                       // m iteraciones \n"
                        + "\n"
                        + "                String rootOrigen = uf.find(arista.origen);                      // 1 op \n"
                        + "\n"
                        + "                String rootDestino = uf.find(arista.destino);                   // 1 op \n"
                        + "\n"
                        + "                 \n"
                        + "\n"
                        + "                if (!rootOrigen.equals(rootDestino)) {                                                           //3 \n"
                        + "\n"
                        + "                    // Notificar evaluación de arista \n"
                        + "\n"
                        + "                    if (panelVisualizacion != null) {                                                                     //1 \n"
                        + "\n"
                        + "                        panelVisualizacion.marcarAristaEvaluada(arista.origen, arista.destino); \n"
                        + "\n"
                        + "                    } \n"
                        + "\n"
                        + "                     \n"
                        + "\n"
                        + "                    // Actualizar aristas más baratas \n"
                        + "\n"
                        + "                    if (!aristasMasBaratas.containsKey(rootOrigen) ||  \n"
                        + "\n"
                        + "                        arista.peso < aristasMasBaratas.get(rootOrigen).peso) {       //5 \n"
                        + "\n"
                        + "                        aristasMasBaratas.put(rootOrigen, arista);            //2 \n"
                        + "\n"
                        + "                    } \n"
                        + "\n"
                        + "                     \n"
                        + "\n"
                        + "                    if (!aristasMasBaratas.containsKey(rootDestino) ||  \n"
                        + "\n"
                        + "                        arista.peso < aristasMasBaratas.get(rootDestino).peso) {        //7 \n"
                        + "\n"
                        + "                        aristasMasBaratas.put(rootDestino, arista);  // 1 \n"
                        + "\n"
                        + "                    } \n"
                        + "\n"
                        + "                } \n"
                        + "\n"
                        + "            } \n"
                        + "\n"
                        + "             \n"
                        + "\n"
                        + "            // Agregar aristas al MST \n"
                        + "\n"
                        + "            for (Arista arista : aristasMasBaratas.values()) {             // recorre hasta 2n aristas  \n"
                        + "\n"
                        + "                String rootOrigen = uf.find(arista.origen);                              // 1 op \n"
                        + "\n"
                        + "                String rootDestino = uf.find(arista.destino);                        // 1 op \n"
                        + "\n"
                        + "                 \n"
                        + "\n"
                        + "                if (!rootOrigen.equals(rootDestino)) {                               //2 \n"
                        + "\n"
                        + "                    mst.add(arista);                                                               // 1 op \n"
                        + "\n"
                        + "                    uf.union(arista.origen, arista.destino);             // 1 op \n"
                        + "\n"
                        + "                     \n"
                        + "\n"
                        + "                    // Notificar arista agregada al MST \n"
                        + "\n"
                        + "                    if (panelVisualizacion != null) {                               \n"
                        + "\n"
                        + "                        panelVisualizacion.agregarAristaMST(arista.origen, arista.destino, arista.peso); \n"
                        + "\n"
                        + "                    } \n"
                        + "\n"
                        + " \n"
                        + "\n"
                        + "                } \n"
                        + "\n"
                        + "            } \n"
                        + "\n"
                        + "        } \n"
                        + "\n"
                        + "         \n"
                        + "\n"
                        + "        if (panelVisualizacion != null) { \n"
                        + "\n"
                        + "            panelVisualizacion.setProcesoCompleto(); \n"
                        + "\n"
                        + "        } \n"
                        + "\n"
                        + "         \n"
                        + "\n"
                        + "        return mst; \n"
                        + "\n"
                        + "    } \n"
                        + "\n"
                        + " \n"
                        + "\n"
                        + "T(n) Final de encontrarMST() (incluyendo llamada a obtenerTodasAristas()) \n"
                        + "\n"
                        + "T(n)=4+3n+10m+6logn⋅(m+n)     \n"
                        + "\n"
                        + "    /** \n"
                        + "\n"
                        + "     * Obtiene todas las aristas del grafo sin duplicados \n"
                        + "\n"
                        + "     * @param grafo Grafo de Puebla \n"
                        + "\n"
                        + "     * @return Lista de todas las aristas únicas \n"
                        + "\n"
                        + "     */ \n"
                        + "\n"
                        + "    private static List<Arista> obtenerTodasAristas(Grafo grafo) { \n"
                        + "\n"
                        + "        List<Arista> aristas = new ArrayList<>();      // 1 operación \n"
                        + "\n"
                        + "        Set<String> aristasAgregadas = new HashSet<>();         // 1 operación \n"
                        + "\n"
                        + "         \n"
                        + "\n"
                        + "for (Nodo nodo : grafo.getNodos()) {                              // n iteraciones \n"
                        + "\n"
                        + "    String origen = nodo.getNombre();                             // 1 op \n"
                        + "\n"
                        + "    for (NodoAdy ady : nodo.getAdyacentes()) {                    // depende del grado: sumatorio de grados = 2m \n"
                        + "\n"
                        + "        String destino = ady.getNombre();                         // 1 op \n"
                        + "\n"
                        + "        String clave = ...                                        // 2-3 ops: compare + concat \n"
                        + "\n"
                        + "        if (!aristasAgregadas.contains(clave)) {                  // 1 op \n"
                        + "\n"
                        + "            aristas.add(new Arista(...));                         // 1 op \n"
                        + "\n"
                        + "            aristasAgregadas.add(clave);                          // 1 op \n"
                        + "\n"
                        + "        } \n"
                        + "\n"
                        + "    } \n"
                        + "\n"
                        + "        } \n"
                        + "\n"
                        + "         \n"
                        + "\n"
                        + "        return aristas; \n"
                        + "\n"
                );
            default:
                algoritmoLabel.setText(algoritmo);
                textoDescripcionLabel.setText(
                        "<html><p width=\"1400\">Descripción no disponible.</p></html>");
        }
    }

    /**
     * Construye un bloque de texto en formato <i>HTML</i> para el
     * {@code JLabel} que contiene la descripción y las complejidades.
     *
     * @param descripcion descripción general del algoritmo
     * @param peor complejidad temporal en el peor caso
     * @param mejor complejidad temporal en el mejor caso
     * @param promedio complejidad temporal promedio
     * @param espacio complejidad espacial
     * @return cadena HTML lista para asignar a un {@code JLabel}
     */
    private String html(String descripcion,
            String peor,
            String mejor,
            String promedio) {

        return "<html><p width=\"1400\">" + descripcion + "<br><br>"
                + "• <strong>Tiempo (peor caso):</strong> " + peor + "<br>"
                + "• <strong>Tiempo (mejor caso):</strong> " + mejor + "<br>"
                + "• <strong>Tiempo (promedio):</strong> " + promedio + "<br>"
                + "</p></html>";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoDescripcionLabel = new javax.swing.JLabel();
        descripcionLabel = new javax.swing.JLabel();
        algoritmoLabel = new javax.swing.JLabel();
        scrollPanelCodigo = new javax.swing.JScrollPane();
        areaCodigo = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textoDescripcionLabel.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        add(textoDescripcionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 1000, 150));

        descripcionLabel.setFont(new java.awt.Font("Arial Narrow", 0, 36)); // NOI18N
        descripcionLabel.setText("Descripción");
        add(descripcionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 230, 50));

        algoritmoLabel.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        add(algoritmoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 1000, 60));

        areaCodigo.setEditable(false);
        areaCodigo.setColumns(20);
        areaCodigo.setRows(5);
        scrollPanelCodigo.setViewportView(areaCodigo);

        add(scrollPanelCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, 1000, 400));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel algoritmoLabel;
    private javax.swing.JTextArea areaCodigo;
    private javax.swing.JLabel descripcionLabel;
    private javax.swing.JScrollPane scrollPanelCodigo;
    private javax.swing.JLabel textoDescripcionLabel;
    // End of variables declaration//GEN-END:variables
}
