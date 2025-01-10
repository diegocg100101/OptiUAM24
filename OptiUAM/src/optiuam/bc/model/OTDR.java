package optiuam.bc.model;

/**
 * Clase la cual contiene los atributos especiales del OTDR
 * @author Ilse López
 * @author Diego Cantoral
 */

public class OTDR extends Componente{
    /**
     * Identificador del OTDR
     */
    private int idOTDR;

    /**
     * Posicion en el eje X del conector
     */
    private double posX;

    /**
     * Posicion en el eje Y del conector
     */
    private double posY;

    /**
     * Método constructor sin parametros
     */
    public OTDR(){
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    private int tipo;


    /**
     * Metodo constructor con parametros
     *
     * @param nombre            Nombre del componente
     * @param id                Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el osciloscopio
     * @param conectado         Indica si el componente esta conectado
     */
    public OTDR(String nombre, int id, String elementoConectado, boolean conectado){
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del OTDR
     *
     * @return idOTDR
     */
    public int getIdOTDR() {return idOTDR;}

    /**
     * Metodo que modifica el identificador del OTDR
     *
     * @param idOTDR Identificador del osciloscopio
     */

    public void setIdOTDR(int idOTDR){this.idOTDR = idOTDR;}

    /**
     * Método que muestra la posición del OTDR en el eje x
     * @return Regresa la posición en el eje x
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Método que modifica la posición del OTDR en el eje x
     * @param posX
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Método que muestra la posición del OTDR en el eje y
     * @return Regresa la posición en el eje y
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Método que modifica la posición del OTDR en el eje y
     * @param posY
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Método que retorna los atributos del OTDR
     *
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada,
     * conectadoSalida, elementoConectadoSalida, idOsciloscopio
     */
    @Override
    public String toString() {
        return super.toString() + "," + idOTDR;
    }


}

