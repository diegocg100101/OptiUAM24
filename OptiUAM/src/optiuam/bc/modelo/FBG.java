
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase FBG la cual contiene los atributos principales de una rejilla de Bragg 
 * (FBG)
 * @author Karen Cruz
 * @see Componente
 */
public class FBG extends Componente {
    
    /**Numero de salidas de la rejilla de Bragg*/                    
    private int salidas;
    /**Puertos salida de la rejilla de Bragg*/
    private StringBuilder cSalidas;
    /**Conexiones de la rejilla de Bragg*/
    private LinkedList<PuertoSalida> conexiones;
    /**Identificador de la rejilla de Bragg. Es diferente al identificador del componente*/
    private int idFBG;
    /**Coeficiente de reflexion de la rejilla de Bragg*/
    private double reflexion;
    /**Coeficiente de transmision de la rejilla de Bragg*/
    private double transmision;
    /**Longitud de la j-esima seccion uniforme de la rejilla de Bragg*/
    private double dz;
    /**Longitud de onda a filtrar de la rejilla de Bragg*/
    private double longitudOnda;
    
    /**
    * Metodo constructor sin parametros
    */
    public FBG() {
        this.conexiones = new LinkedList();
    }
    
    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con la rejilla de Bragg
     * @param conectado Indica si el componente esta conectado
     * @param salidas Numero de salidas del FBG
    */
    public FBG(String nombre, int id,String elementoConectado, boolean conectado, int salidas) {
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra el numero de salidas del FBG
     * @return salidas
     */
    public int getSalidas() {
        return salidas;
    }
    
    /**
     * Metodo que modifica el numero de salidas del FBG
     * @param salidas Numero de salidas del FBG
     */
    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }
    
    /**
     * Metodo que muestra las conexiones del FBG
     * @return conexiones
     */
    public LinkedList<PuertoSalida> getConexiones() {
        return conexiones;
    }

    /**
     * Metodo que modifica las conexiones del FBG
     * @param conexiones Conexiones del FBG
     */
    public void setConexiones(LinkedList<PuertoSalida> conexiones) {
        this.conexiones = conexiones;
    }

    /**
     * Metodo que muestra el identificador de la rejilla de Bragg, no el del componente
     * @return idFBG
     */
    public int getIdFBG() {
        return idFBG;
    }

    /**
     * Metodo que modifica el identificador de la rejilla de Bragg, no el del componente
     * @param idFBG Identificador de la rejilla de Bragg (FBG)
     */
    public void setIdFBG(int idFBG) {
        this.idFBG = idFBG;
    }

    /**
     * Metodo que muestra el coeficiente de reflexion de la rejilla de Bragg
     * @return reflexion
     */
    public double getReflexion() {
        return reflexion;
    }

    /**
     * Metodo que modifica el coeficiente de reflexion de la rejilla de Bragg
     * @param reflexion Coeficiente de reflexión de la FBG
     */
    public void setReflexion(double reflexion) {
        this.reflexion = reflexion;
    }

    /**
     * Metodo que muestra el coeficiente de transmision de la rejilla de Bragg
     * @return transmision
     */
    public double getTransmision() {
        return transmision;
    }

    /**
     * Metodo que modifica el coeficiente de transmision de la rejilla de Bragg
     * @param transmision Coeficiente de trasmision de la FBG
     */
    public void setTransmision(double transmision) {
        this.transmision = transmision;
    }

    /**
     * Metodo que muestra la longitud de la j-esima seccion uniforme de la rejilla de Bragg
     * @return dz
     */
    public double getDz() {
        return dz;
    }

    /**
     * Metodo que modifica la longitud de la j-esima seccion uniforme de la rejilla de Bragg
     * @param dz Longitud de la j-esima seccion uniforme de la FBG
     */
    public void setDz(double dz) {
        this.dz = dz;
    }

    /**
     * Metodo que muestra la longitud de onda a filtrar de la rejilla de Bragg
     * @return longitudOnda
     */
    public double getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda a filtrar de la rejilla de Bragg
     * @param longitudOnda Longitud de onda a filtrar de la FBG
     */
    public void setLongitudOnda(double longitudOnda) {
        this.longitudOnda = longitudOnda;
    }
    
    /**
     * Metodo que muestra la conversion de los puertos salida de la rejilla de Bragg
     * a un String
     * @return salidas
     */
    public StringBuilder getcSalidas() {
        return cSalidas;
    }

    /**
     * Metodo que modifica la conversion de los puertos salida de la rejilla de Bragg
     * a un String
     * @param cSalidas Conversion de puertos salida
     */
    public void setcSalidas(StringBuilder cSalidas) {
        this.cSalidas = cSalidas;
    }
    
    /**
     * Metodo que actualiza las salidas de la rejilla de Bragg
     * @param salidas Salidas del FBG
     */
    public void actuaizarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
    }
    
    /**
     * Metodo toString que retorna los atributos de la rejilla de Bragg
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, idFBG
     */
    @Override
    public String toString() {
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
        return super.toString()+","+idFBG+","+reflexion+
                ","+transmision+","+dz+","+longitudOnda+cSalidas.toString();
    }
    
}
