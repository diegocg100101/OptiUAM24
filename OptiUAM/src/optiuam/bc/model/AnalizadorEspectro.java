
package optiuam.bc.model;

/**
 * Clase AnalizadorEspectros la cual contiene los atributos principales de un analizador de espectros ópticos
 *
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Ilse López
 * @author Diego Cantoral
 * @see Componente
 */
public class AnalizadorEspectro extends Componente {


    /**
     * Identificador del osciloscopio optico. Es diferente al identificador del componente
     */
    private int idAnalizador;

    /**
     * Posicion en el eje X del conector
     */
    private double posX;

    /**
     * Posicion en el eje Y del conector
     */
    private double posY;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    private int tipo;


    /**
     * Método constructor sin parametros
     */
    public AnalizadorEspectro() {
    }

    /**
     * Metodo constructor con parametros
     *
     * @param nombre            Nombre del componente
     * @param id                Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el osciloscopio
     * @param conectado         Indica si el componente esta conectado
     */
    public AnalizadorEspectro(String nombre, int id, String elementoConectado,
                              boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del osciloscopio, no el del componente
     *
     * @return idOsciloscopio
     */
    public int getIdAnalizador() {
        return idAnalizador;
    }

    /**
     * Metodo que modifica el identificador del osciloscopio, no el del componente
     *
     * @param idAnalizador Identificador del osciloscopio
     */
    public void setIdAnalizador(int idAnalizador) {
        this.idAnalizador = idAnalizador;
    }

    /**
     * Método que muestra la posición del osciloscopio en el eje x
     * @return Regresa la posición en el eje x
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Método que modifica la posición del osciloscopio en el eje x
     * @param posX
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Método que muestra la posición del osciloscopio en el eje y
     * @return Regresa la posición en el eje y
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Método que modifica la posición del osciloscopio en el eje y
     * @param posY
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Método que retorna los atributos del osciloscopio
     *
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada,
     * conectadoSalida, elementoConectadoSalida, idOsciloscopio
     */
    @Override
    public String toString() {
        return super.toString() + "," + idAnalizador + "," + Fc;
    }

}
