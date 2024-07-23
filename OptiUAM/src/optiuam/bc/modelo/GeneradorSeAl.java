
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase GeneradorSeAl aqui se producira la señal aleatoria con distibucion de Bernoulli
 * @author Carlos Elizarraras
 */
public class GeneradorSeAl{
    
    /**Para generar la señal*/
    /**Probabilidad de que el resultado sea 1*/
    private static final float P = 0.5f;
    
    /**Cantidad de pulsos de la señal*/
    //public int CP = 1;
    
    /**Para Generador Lineal Congruencial*/
    /**Modulo*/
    private static final int m = 2147483647;
    
    /**Multiplicador*/
    private static final int a = 65521;
    
    /**Incremento*/
    private static final int c = 1;
    
    /**Semilla*/
    private long b = (long) System.currentTimeMillis();
    
    /**
    * Metodo constructor sin parametros
    */
    public GeneradorSeAl() {
    }
    
    /**
     * Metodo del Generador Lineal Congruencial
     * @return 0 o 1
     */
    private int GLC(){
        b = (b * a + c) % m;
        if((float)((float)b/(float)m)<P)
            return 1;
        else
            return 0;
    }
    
    /**
     * Metodo para generar la señal
     * @param CP
     * @return sennal
     */
    public LinkedList generar(int CP){
        LinkedList<Double> sennal = new LinkedList<>();
        for (int i = 0; i < CP; i++){
            if(i==0||i==CP-1)
                sennal.add((double)1);
            else
                sennal.add((double)GLC());
        }
        return sennal;
    }
}