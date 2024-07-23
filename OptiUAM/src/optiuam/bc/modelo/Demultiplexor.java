
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Demultiplexor la cual contiene los atributos principales de un 
 * demultiplexor
 * @author Arturo Borja
 * @see Componente
 */
public class Demultiplexor extends Componente {
    
    /**Identificador del demultiplexor. Es diferente al identificador del 
     * componente*/
    private int idDemux;
    /**Numero de salidas del demultiplexor*/                    
    private int salidas;
    /**Puertos salida del demultiplexor*/
    private StringBuilder cSalidas;
    /**Perdiad de insercion*/
    private double perdidaInsercion;
    /**Longitud de onda del demultiplexor*/
    private int longitudOnda;
    /**Conexiones del demultiplexor*/
    private LinkedList<PuertoSalida> conexiones;
    /**Rejilla de Bragg para los filtros del demultiplexor*/
    private FBG fbg;
    /**Longitudes de onda del demultiplexor*/
    static String lOnda[] = {"1470", "1490", "1510", "1530", "1550", "1570", 
        "1590", "1610"}; 
    
    /**
    * Metodo constructor sin parametros
    */
    public Demultiplexor() {
        this.conexiones=new LinkedList<>();
        this.fbg=new FBG();
    }
    
    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra 
     * conectado con el demultiplexor
     * @param conectado Indica si el componente esta conectado
    */
    public Demultiplexor(String nombre, int id,String elementoConectado, 
            boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del demultiplexor, no el del 
     * componente
     * @return idDemux
     */
    public int getIdDemux() {
        return idDemux;
    }

    /**
     * Metodo que modifica el identificador del demultiplexor, no el del 
     * componente
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
     * Metodo que modifica las salidas del demultiplexor
     * @param salidas Salidas del demultiplexor
     */
    public void modificarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            PuertoSalida puerto= new PuertoSalida();
            switch (salidas) {
                case 2:
                    //{"1470", "1490", "1510", "1530", "1550", "1570", "1590", "1610"};
                    puerto.setLongitudOnda(Integer.parseInt(lOnda[i+4]));
                    break;
                case 4:
                    //{"1470", "1490", "1510", "1530", "1550", "1570", "1590", "1610"};
                    puerto.setLongitudOnda(Integer.parseInt(lOnda[i+3]));
                    break;
                case 8:
                    //{"1470", "1490", "1510", "1530", "1550", "1570", "1590", "1610"};
                    puerto.setLongitudOnda(Integer.parseInt(lOnda[i+1]));
                    break;
                default:
                    break;
            }
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
     * Metodo que multiplica los valores de la señal actual con la atenuacion del
     * demultiplexor
     * @param valores Valores de la señal actual
     * @return Valores con la atenuacion del demultiplexor
     */
    public LinkedList valorMagnitudPerdida(LinkedList valores){
        int n=0;
        while(n<valores.size()){
            Float aux= (Float) valores.get(n);
            valores.set(n,Math.pow(10, (-perdidaInsercion/10))*aux);
            n++;
        }
        return valores; 
    }

    /**
     * Metodo que muestra la Rejilla de Bragg para el filtro del demultiplexor
     * @return fbg
     */
    public FBG getFbg() {
        return fbg;
    }

    /**
     * Metodo que modifica la Rejilla de Bragg para el filtro del demultiplexor
     * @param fbg Rejilla de Bragg para el filtro del demultiplexor
     */
    public void setFbg(FBG fbg) {
        this.fbg = fbg;
    }
    
    /**
     * Metodo toString que retorna los atributos del demultiplexor
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, idDemux, salidas, 
     * perdidaInsercion
     */
    @Override
    public String toString() {
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
        return super.toString()+","+idDemux+","+salidas+","+perdidaInsercion+
                ","+cSalidas.toString();
    }
    
}
