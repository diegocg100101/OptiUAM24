
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Listas la cual lleva un control de elementos agregados en el simulador y
 * multiples enlaces
 * @author Arturo Borja
 */
public class Listas {
    /**Lista de elementos*/
    private LinkedList lista;
    /**Longitud de onda de la lista*/
    private int n;
    
     /**
     * Metodo constructor sin parametros
     */
    public Listas() {
    }

    /**
     * Metodo que muestra la lista de elementos creados en el simulador
     * @return lista
     */
    public LinkedList getLista() {
        return lista;
    }

    /**
     * Metodo que modifica la lista de elementos creados en el simulador
     * @param lista Lista de elementos
     */
    public void setLista(LinkedList lista) {
        this.lista = lista;
    }

    /**
     * Metodo que muestra la longitud de onda correspondiente a una lista de elementos
     * @return n
     */
    public int getN() {
        return n;
    }

    /**
     * Metodo que modifica la longitud de onda correspondiente a una lista de elementos
     * @param n Longitud de onda de una lista de elementos
     */
    public void setN(int n) {
        this.n = n;
    }

}
