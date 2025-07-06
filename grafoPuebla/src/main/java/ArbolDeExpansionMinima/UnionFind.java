/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolDeExpansionMinima;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author erika
 */
public class UnionFind {
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
         * Encuentra el representante del conjunto al que pertenece un municipio.
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
         * Une dos conjuntos
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
