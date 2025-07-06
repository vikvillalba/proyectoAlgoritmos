/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolDeExpansionMinima;

/**
 *Clase que representa una arista del grafo con su origen, destino y peso.
 * Implementa Comparable para permitir ordenamiento por peso.
 * @author erika
 */
public class Arista implements Comparable<Arista> {
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
        
        @Override
        public String toString() {
            return origen + " -- " + destino + " (" + peso + " km)";
        }
    }