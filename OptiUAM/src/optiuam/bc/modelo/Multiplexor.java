
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Multiplexor la cual contiene los atributos principales de un multiplexor
 * @author Arturo Borja
 * @see Componente
 */
public class Multiplexor extends Componente {
    
    /**Identificador del multiplexor. Es diferente al identificador del componente*/
    private int idMux;
    /**string donde se actualiza el contenido de las entradas del elemento*/
    private StringBuilder cEntradas;
    /**lista de las entradas del elemento*/
    private LinkedList<PuertoEntrada> conexionEntradas;
    /**Numero entradas del elemento*/
    private int entradas;
    /**Perdida de insercion del multiplexor*/
    private double perdidaInsercion;

    /**
    * Metodo constructor sin parametros
    */
    public Multiplexor() {
        this.conexionEntradas=new LinkedList();
    }
    
    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el multiplexor
     * @param conectado Indica si el componente esta conectado
    */
    public Multiplexor(String nombre, int id,String elementoConectado, boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del multiplexor, no el del componente
     * @return idMux
     */
    public int getIdMux() {
        return idMux;
    }

    /**
     * Metodo que modifica el identificador del multiplexor, no el del componente
     * @param idMux Identificador del multiplexor
     */
    public void setIdMux(int idMux) {
        this.idMux = idMux;
    }

    /**
     * Metodo que muestra la conversion de los puertos entrada del multiplexor
     * a un String
     * @return entradas
     */
    public StringBuilder getcEntradas() {
        return cEntradas;
    }

    /**
     * Metodo que modifica la conversion de los puertos entrada del multiplexor
     * a un String
     * @param cEntradas Conversion de puertos entrada
     */
    public void setcEntradas(StringBuilder cEntradas) {
        this.cEntradas = cEntradas;
    }

    /**
     * Metodo que muestra las conexiones del multiplexor
     * @return conexiones
     */
    public LinkedList<PuertoEntrada> getConexionEntradas() {
        return conexionEntradas;
    }

    /**
     * Metodo que modifica las conexiones del multiplexor
     * @param conexionEntradas Conexiones del multiplexor
     */
    public void setConexionEntradas(LinkedList<PuertoEntrada> conexionEntradas) {
        this.conexionEntradas = conexionEntradas;
    }

    /**
     * Metodo que muestra el numero de entradas del multiplexor
     * @return entradas
     */
    public int getEntradas() {
        return entradas;
    }

    /**
     * Metodo que modifica el numero de entradas del multiplexor
     * @param entradas Numero de entradas del multiplexor
     */
    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    /**
     * Metodo que muestra la perdida de insercion del multiplexor
     * @return perdidaInsercion
     */
    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    /**
     * Metodo que modifica la perdida de insercion del multiplexor
     * @param perdidaInsercion Perdida de insercion del multiplexor
     */
    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    /**
     * Metodo que modifica las entardas del multiplexor
     * @param entradas Entradas del multiplexor
     */
    public void modificarEntradas(int entradas){
        cEntradas=new StringBuilder();
        for(int i=0;i<entradas-1; i++){
            PuertoEntrada puerto= new PuertoEntrada();
            conexionEntradas.add(puerto);
            cEntradas.append(conexionEntradas.get(i).toString());
        }
    }
    
    /**
     * Metodo toString que retorna los atributos del multiplexor
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, idMux, entradas, perdidaInsercion,
     * cEntradas
     */
    @Override
    public String toString() {
        cEntradas=new StringBuilder();
        for(int i=0;i<entradas-1; i++){
            cEntradas.append(conexionEntradas.get(i).toString());
        }
        return super.toString()+","+ idMux + "," + entradas + "," + perdidaInsercion + cEntradas;
    }
    
}
