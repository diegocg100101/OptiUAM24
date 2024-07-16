
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Demultiplexor la cual contiene los atributos principales de un demultiplexor
 * @author Arturo Borja
 * @see Componente
 */
public class Demultiplexor extends Componente {
    
    /**Identificador del demultiplexor. Es diferente al identificador del componente*/
    private int idDemux;
    /**Numero de salidas del demultiplexor*/                    
    private int salidas;
    /**Puertos salida del demultiplexor*/
    private StringBuilder cSalidas;
    /**Perdida de insercion del demultiplexor*/
    private double perdidaInsercion;
    /**Longitud de onda del demultiplexor*/
    private int longitudOnda;
    /**Conexiones del demultiplexor*/
    private LinkedList<PuertoSalida> conexiones;
    /**Posicion en el eje X del demultiplexor*/
    private double posX; 
    /**Posicion en el eje Y del demultiplexor*/
    private double posY;
    
    /**
    * Metodo constructor sin parametros
    */
    public Demultiplexor() {
        this.conexiones=new LinkedList<>();
    }
    
    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el demultiplexor
     * @param conectado Indica si el componente esta conectado
    */
    public Demultiplexor(String nombre, int id,String elementoConectado, boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del demultiplexor, no el del componente
     * @return idDemux
     */
    public int getIdDemux() {
        return idDemux;
    }

    /**
     * Metodo que modifica el identificador del demultiplexor, no el del componente
     * @param idDemux Identificador del demultiplexor
     */
    public void setIdDemux(int idDemux) {
        this.idDemux = idDemux;
    }

    /**
     * Metodo que muestra el numero de salidas del demultiplexor
     * @return salidas
     */
    public int getSalidas() {
        return salidas;
    }
    
    /**
     * Metodo que modifica el numero de salidas del demultiplexor
     * @param salidas Numero de salidas del demultiplexor
     */
    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    /**
     * Metodo que muestra la perdida de insercion del demultiplexor
     * @return perdidaInsercion
     */
    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }
    
    /**
     * Metodo que modifica la perdida de insercion del demultiplexor
     * @param perdidaInsercion Perdida de insercion del demultiplexor
     */
    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    /**
     * Metodo que muestra la longitud de onda del demultiplexor
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

     /**
     * Metodo que modifica la longitud de onda del demultiplexor
     * @param longitudOnda Longitud de onda del demultiplexor
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }
   
    /**
     * Metodo que muestra la posicion en el eje X del demultiplexor
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X del demultiplexor
     * @param posX Posicion en el eje X del demultiplexor
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y del demultiplexor
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion en el eje Y del demultiplexor
     * @param posY Posicion en el eje Y del demultiplexor
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Metodo que muestra las conexiones del demultiplexor
     * @return conexiones
     */
    public LinkedList<PuertoSalida> getConexiones() {
        return conexiones;
    }

    /**
     * Metodo que modifica las conexiones del demultiplexor
     * @param conexiones Conexiones del demultiplexor
     */
    public void setConexiones(LinkedList<PuertoSalida> conexiones) {
        this.conexiones = conexiones;
    }

    /**
     * Metodo que muestra la conversion de los puertos salida del demultiplexor
     * a un String
     * @return salidas
     */
    public StringBuilder getcSalidas() {
        return cSalidas;
    }

    /**
     * Metodo que modifica la conversion de los puertos salida del demultiplexor
     * a un String
     * @param cSalidas Conversion de puertos salida
     */
    public void setcSalidas(StringBuilder cSalidas) {
        this.cSalidas = cSalidas;
    }
    
    /**
     * Metodo que modifica las salidas del demultiplexor
     * @param salidas Salidas del demultiplexor
     */
    public void modificarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            PuertoSalida puerto= new PuertoSalida();
            conexiones.add(puerto);
            cSalidas.append(conexiones.get(i).toString());
        }
    }
    
    /**
     * Metodo que actualiza las salidas del demultiplexor
     * @param salidas Salidas del demultiplexor
     */
    public void actuaizarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
    } 
    
    /**
     * Metodo toString que retorna los atributos del demultiplexor
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, idDemux
     */
    @Override
    public String toString() {
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            //cSalidas.append(conexiones.get(i).toString());
        }
        return super.toString()+","+salidas+","+perdidaInsercion+","+longitudOnda+","+idDemux+cSalidas.toString();
    }
    
}
