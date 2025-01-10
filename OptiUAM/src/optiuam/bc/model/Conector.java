
package optiuam.bc.model;

import java.util.LinkedList;

/**
 * Clase Conector la cual contiene los atributos principales de un conector
 * optico
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 * @see Componente
 */
public class Conector extends Componente {

    /**
     * Perdida de insercion
     */
    private double perdidaInsercion;

    /**
     * Longitud de onda
     */
    private int longitudOnda;

    /*Modo del conector. Puede ser monomodo o multimodo*/
    private int modo;

    /**
     * Identificador del conector. Es diferente al identificador del componente
     */
    private int idConector;

    /**
     * Posicion en el eje X del conector
     */
    private double posX;

    /**
     * Posicion en el eje Y del conector
     */
    private double posY;

    /**
     * Valor de la reflectancia dependiendo del tipo
     */
    private double reflectancia;

    /**
     * Metodo constructor sin parametros
     */
    public Conector() {
    }

    /**
     * Metodo constructor con parametros
     *
     * @param nombre            Nombre del componente
     * @param id                Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra
     *                          conectado con el conector
     * @param conectado         Indica si el componente esta conectado
     * @param longitudOnda      Longitud de onda del conector
     * @param modo              Modo del conector
     * @param perdidaInsercion  Perdida de insercion del conector
     */
    public Conector(String nombre, int id, String elementoConectado,
                    boolean conectado, int longitudOnda, int modo,
                    double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
        this.longitudOnda = longitudOnda;
        this.modo = modo;
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra la perdida de insercion del conector
     *
     * @return perdidaInsercion
     */
    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    /**
     * Metodo que modifica la perdida de insercion del conector
     *
     * @param perdidaInsercion Perdida de insercion
     */
    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    /**
     * Metodo que muestra la longitud de onda del conector
     *
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda del conector
     *
     * @param longitudOnda Longitud de onda
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    /**
     * Metodo que muestra el modo del conector
     *
     * @return modo
     */
    public int getModo() {
        return modo;
    }

    /**
     * Metodo que modifica el modo del conector
     *
     * @param modo Modo del conector. Puede ser monomodo o multimodo
     */
    public void setModo(int modo) {
        this.modo = modo;
    }

    /**
     * Metodo que muestra el identificador del conector, no el del componente
     *
     * @return idConector
     */
    public int getIdConector() {
        return idConector;
    }

    /**
     * Metodo que modifica el identificador del conector, no el del componente
     *
     * @param idConector Identificador del conector
     */
    public void setIdConector(int idConector) {
        this.idConector = idConector;
    }

    /**
     * Metodo que muestra la posicion en el eje X del conector
     *
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X del conector
     *
     * @param posX Posicion en el eje X del conector
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y del conector
     *
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que muestra la posicion en el eje Y del conector
     *
     * @param posY Posicion en el eje Y del conector
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getReflectancia() {
        return reflectancia;
    }

    public void setReflectancia(double reflectancia) {
        this.reflectancia = reflectancia;
    }

    /**
     * Metodo que multiplica los valores de la señal actual con la atenuacion del
     * conector
     *
     * @param valores Valores de la señal actual
     * @return Valores con la atenuacion del conector
     */
    public LinkedList<Listas> valorMagnitudPerdida(LinkedList<Listas> valores) {
        int n = 0;
        while (n < valores.size()) {
            NumeroComplejo aux = valores.get(n).getComplejo();
            aux = aux.producto(aux, (float) Math.pow(10, (-perdidaInsercion / 10)));
            valores.get(n).setComplejo(aux);
            valores.get(n).setMagnitud(aux.magnitud());
            n++;
        }
        return valores;
    }

    /**
     * Metodo toString que retorna los atributos de un conector
     *
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada,
     * conectadoSalida, elementoConectadoSalida, longitudOnda,
     * modo, perdidaInsercion, idConector
     */
    @Override
    public String toString() {
        return super.toString() + "," + longitudOnda +
                "," + modo + "," + perdidaInsercion + "," + idConector + "," + Fc;
    }

}