/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo;

/**
 *
 * @author erika
 */


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GrafoPueblaUtil {
    private static final Grafo grafo;
    
    static {
        grafo = new Grafo(50);
        inicializarGrafo();
    }
    
    private GrafoPueblaUtil() {
        // Constructor privado para evitar instanciación
    }
    
    private static void inicializarGrafo() {
        // Agregar todos los municipios (vértices)
        String[] municipios = {
            "Tlahuapan", "San Martín Texmelucan", "Huejotzingo", "Puebla", "Amozoc",
            "Acajete", "Tepeaca", "Acatzingo", "Quecholac", "Tecamachalco",
            "Palmar de Bravo", "Tehuacán", "Cuautlancingo", "Coronango", "San Pedro Cholula",
            "San Andrés Cholula", "Atlixco", "Izúcar de Matamoros", "Chietla", "Acatlán",
            "Ajalpan", "Tlacotepec de Benito Juárez", "Zacatlán", "Chignahuapan", "Huauchinango",
            "Xicotepec", "Venustiano Carranza", "Libres", "Tlatlauquitepec", "Teziutlán",
            "Zacapoaxtla", "Cuetzalan del Progreso", "Chignautla", "Xiutetelco", "Hueytamalco",
            "San Salvador el Verde", "San Salvador el Seco", "Chalchicomula de Sesma", "Tlachichuca", "Nopalucan"
        };
        
        for (String municipio : municipios) {
            grafo.agregarNodo(municipio);
        }
        
        // Agregar las conexiones (aristas) con sus distancias
        agregarConexiones();
    }
    
    private static void agregarConexiones() {
        // Conexiones principales
        grafo.agregarArista("Tlahuapan", "San Martín Texmelucan", 10);
        grafo.agregarArista("San Martín Texmelucan", "Huejotzingo", 15);
        grafo.agregarArista("Huejotzingo", "Puebla", 29);
        grafo.agregarArista("Puebla", "Amozoc", 19);
        grafo.agregarArista("Amozoc", "Acajete", 13);
        grafo.agregarArista("Acajete", "Tepeaca", 19);
        grafo.agregarArista("Tepeaca", "Acatzingo", 20);
        grafo.agregarArista("Acatzingo", "Quecholac", 15);
        grafo.agregarArista("Quecholac", "Tecamachalco", 12);
        grafo.agregarArista("Tecamachalco", "Palmar de Bravo", 22);
        grafo.agregarArista("Palmar de Bravo", "Tehuacán", 53);
        
        // Conexiones desde Puebla
        grafo.agregarArista("Puebla", "Cuautlancingo", 12);
        grafo.agregarArista("Cuautlancingo", "Coronango", 5);
        grafo.agregarArista("Puebla", "San Pedro Cholula", 14);
        grafo.agregarArista("San Pedro Cholula", "San Andrés Cholula", 4);
        grafo.agregarArista("Puebla", "Atlixco", 30);
        grafo.agregarArista("Puebla", "Zacatlán", 121);
        
        // Conexiones desde Atlixco
        grafo.agregarArista("Atlixco", "Izúcar de Matamoros", 40);
        grafo.agregarArista("Izúcar de Matamoros", "Chietla", 20);
        grafo.agregarArista("Izúcar de Matamoros", "Acatlán", 70);
        
        // Conexiones desde Tehuacán
        grafo.agregarArista("Tehuacán", "Ajalpan", 21);
        grafo.agregarArista("Tehuacán", "Tlacotepec de Benito Juárez", 38);
        
        // Conexiones desde Zacatlán
        grafo.agregarArista("Zacatlán", "Chignahuapan", 15);
        grafo.agregarArista("Zacatlán", "Huauchinango", 45);
        grafo.agregarArista("Huauchinango", "Xicotepec", 21);
        grafo.agregarArista("Xicotepec", "Venustiano Carranza", 58);
        
        // Conexiones desde Amozoc
        grafo.agregarArista("Amozoc", "Libres", 74);
        grafo.agregarArista("Libres", "Tlatlauquitepec", 45);
        grafo.agregarArista("Tlatlauquitepec", "Teziutlán", 27);
        
        // Conexiones desde Teziutlán
        grafo.agregarArista("Teziutlán", "Zacapoaxtla", 39);
        grafo.agregarArista("Zacapoaxtla", "Cuetzalan del Progreso", 18);
        grafo.agregarArista("Teziutlán", "Chignautla", 6);
        grafo.agregarArista("Teziutlán", "Xiutetelco", 17);
        grafo.agregarArista("Teziutlán", "Hueytamalco", 25);
        
        // Otras conexiones
        grafo.agregarArista("San Martín Texmelucan", "San Salvador el Verde", 7);
        grafo.agregarArista("Acatzingo", "San Salvador el Seco", 22);
        grafo.agregarArista("San Salvador el Seco", "Chalchicomula de Sesma", 29);
        grafo.agregarArista("Chalchicomula de Sesma", "Tlachichuca", 20);
        grafo.agregarArista("Acajete", "Nopalucan", 21);
    }
    
    public static Grafo getGrafo() {
        return grafo;
    }
    
    public static List<String> getMunicipios() {
        List<String> listaMunicipios = new ArrayList<>();
        for (Nodo nodo : grafo.getNodos()) {
            listaMunicipios.add(nodo.getNombre());
        }
        return listaMunicipios;
    }
    
    public static List<String> getConexiones(String municipio) {
        Nodo nodo = grafo.buscarNodo(municipio);
        if (nodo == null) {
            return new ArrayList<>();
        }
        
        return nodo.getAdyacentes().stream()
                   .map(ady -> String.format("%s (%.1f km)", ady.getNombre(), ady.getPeso()))
                   .collect(Collectors.toList());
    }
    
    public static double calcularDistancia(String origen, String destino) {
        if (origen.equals(destino)) {
            return 0;
        }
        
        Nodo nodoOrigen = grafo.buscarNodo(origen);
        if (nodoOrigen == null) {
            return -1;
        }
        
        return nodoOrigen.getAdyacentes().stream()
                .filter(ady -> ady.getNombre().equals(destino))
                .findFirst()
                .map(NodoAdy::getPeso)
                .orElse(-1.0);
    }
    
    public static boolean existeConexion(String origen, String destino) {
        return calcularDistancia(origen, destino) >= 0;
    }
    
    
    
}
