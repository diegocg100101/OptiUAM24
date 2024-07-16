
package optiuam.bc.modelo;

/**
 * Clase Osciloscopio la cual contiene los atributos principales de un osciloscopio 
 * optico
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class Osciloscopio extends Componente {
    
    /**Identificador del osciloscopio optico. Es diferente al identificador del componente*/
    private int idOsciloscopio;

    /**
     * Metodo constructor sin parametros
     */
    public Osciloscopio(){
    }
    
    /**
     * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el osciloscopio
     * @param conectado Indica si el componente esta conectado
    */
    public Osciloscopio(String nombre, int id, String elementoConectado, 
            boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra el identificador del osciloscopio, no el del componente
     * @return idOsciloscopio
     */
    public int getIdOsciloscopio() {
        return idOsciloscopio;
    }

    /**
     * Metodo que modifica el identificador del osciloscopio, no el del componente
     * @param idOsciloscopio Identificador del osciloscopio
     */
    public void setIdOsciloscopio(int idOsciloscopio) {
        this.idOsciloscopio = idOsciloscopio;
    }

    /**
     * Metodo toString que retorna los atributos del osciloscopio
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, idOsciloscopio
     */
    @Override
    public String toString() {
        return super.toString()+","+idOsciloscopio; 
    }
    
}
