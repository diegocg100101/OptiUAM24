
package optiuam.bc.modelo;

import javafx.scene.shape.Line;

/**
 * Clase PuertoEntrada la cual contiene los atributos principales del puerto
 * entrada para un multiplexor
 * @author Arturo Borja
 */
public class PuertoEntrada {
    
    /**Indica si el componente esta conectado por la entrada*/
    private boolean conectadoEntrada;
    /**Nombre del componente el cual se encuentra conectado por la entrada*/
    private String elementoConectadoEntrada;
    /**Conexion (linea) del componente*/
    private Line linea;

    /**Metodo constructor sin parametros*/
    public PuertoEntrada() {
        this.conectadoEntrada=false;
        this.elementoConectadoEntrada="";
    }
    
    /**
     * Metodo que muestra si el componente esta conectado por la entrada
     * @return conectadoEntrada
     */
    public boolean isConectadoEntrada() {
        return conectadoEntrada;
    }

    /**
     * Metodo que modifica si el componente esta conectado por la entrada
     * @param conectadoEntrada Indica si el componente esta conectado por la entrada
     */
    public void setConectadoEntrada(boolean conectadoEntrada) {
        this.conectadoEntrada = conectadoEntrada;
    }

    /**
     * Metodo que muestra el nombre del componente el cual se encuentra 
     * conectado por la entrada
     * @return elementoConectadoEntrada
     */
    public String getElementoConectadoEntrada() {
        return elementoConectadoEntrada;
    }

    /**
     * Metodo que modifica el nombre del componente el cual se encuentra 
     * conectado por la entrada
     * @param elementoConectadoEntrada Indica si el componente esta conectado por la entrada
     */
    public void setElementoConectadoEntrada(String elementoConectadoEntrada) {
        this.elementoConectadoEntrada = elementoConectadoEntrada;
    }
    
    /**
     * Metodo que muestra la conexion (linea) del componente
     * @return linea
     */
    public Line getLinea() {
        return linea;
    }

    /**
     * Metodo que modifica la conexion (linea) del componente
     * @param linea Conexion (linea) del componente
     */
    public void setLinea(Line linea) {
        this.linea = linea;
    }
    
    /**
     * Metodo toString que retorna los atributos del puerto entrada
     * @return conectadoEntrada, elementoConectadoEntrada
     */
    @Override
    public String toString() {
        return ","+conectadoEntrada + "," + elementoConectadoEntrada;
    }
    
}
