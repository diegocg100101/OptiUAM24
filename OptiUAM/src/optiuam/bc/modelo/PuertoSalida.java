
package optiuam.bc.modelo;

import javafx.scene.shape.Line;

/**
 * Clase PuertoSalida la cual contiene los atributos principales del puerto
 * salida para un divisor optico, una fbg y un demultiplexor
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class PuertoSalida {
    
    /**Indica si el componente esta conectado por la salida*/
    private boolean conectadoSalida;
    /**Nombre del componente el cual se encuentra conectado por la salida*/
    private String elementoConectadoSalida;
    /**Conexion (linea) del componente*/
    private Line linea;
    /**Longitud de onda del puerto n del demultiplexor*/
    private int longitudOnda;
    /**Filtro del puerto n del demultiplexor*/
    private FBG fbg;

    /**Metodo constructor sin parametros*/
    public PuertoSalida() {
        this.conectadoSalida=false;
        this.elementoConectadoSalida="";
        this.fbg=new FBG();
    }
    
    /**
     * Metodo que muestra si el componente esta conectado por la salida
     * @return conectadoSalida
     */
    public boolean isConectadoSalida() {
        return conectadoSalida;
    }

    /**
     * Metodo que modifica si el componente esta conectado por la salida
     * @param conectadoSalida Indica si el componente esta conectado por la 
     * salida
     */
    public void setConectadoSalida(boolean conectadoSalida) {
        this.conectadoSalida = conectadoSalida;
    }

    /**
     * Metodo que muestra el nombre del componente el cual se encuentra 
     * conectado por la salida
     * @return elementoConectadoSalida
     */
    public String getElementoConectadoSalida() {
        return elementoConectadoSalida;
    }

    /**
     * Metodo que modifica el nombre del componente el cual se encuentra 
     * conectado por la salida
     * @param elementoConectadoSalida Indica si el componente esta conectado 
     * por la salida
     */
    public void setElementoConectadoSalida(String elementoConectadoSalida) {
        this.elementoConectadoSalida = elementoConectadoSalida;
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
     * Metodo que muestra la longitud de onda del puerto n del demultiplexor
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda del puerto n del demultiplexor
     * @param longitudOnda Longitud de onda del puerto n del demultiplexor
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    /**
     * Metodo que muestra el filtro del puerto n del demultiplexor
     * @return fbg
     */
    public FBG getFbg() {
        return fbg;
    }

    /**
     * Metodo que modifica el filtro del puerto n del demultiplexor
     * @param fbg Filtro del puerto n del demultiplexor
     */
    public void setFbg(FBG fbg) {
        this.fbg = fbg;
    }
    
    /**
     * Metodo toString que retorna los atributos del puerto salida
     * @return conectadoSalida, elementoConectadoSalida
     */
    @Override
    public String toString() {
        return ","+conectadoSalida + "," + elementoConectadoSalida;
    }
    
}
