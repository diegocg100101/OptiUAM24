
package optiuam.bc.model;

/**
 * Clase ExcepcionDivideCero la cual lanza una excepcion al dividir entre cero
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class ExcepcionDivideCero extends Exception {
    
    public ExcepcionDivideCero() {
            super();
     }

     public ExcepcionDivideCero(String s) {
            super(s);
     }
     
}
