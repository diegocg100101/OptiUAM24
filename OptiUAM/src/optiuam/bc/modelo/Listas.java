
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Listas la cual lleva un control de elementos agregados en el simulador 
 * y multiples enlaces
 * @author Arturo Borja
 */
public class Listas implements Cloneable{
    
    /**Lista de elementos*/
    private LinkedList lista;
    /**Longitud de onda de la lista*/
    private int n;
    /**Numero complejo de una señal**/
    private NumeroComplejo complejo;
    /**Valores de x numero complejo**/
    private double xNumComplex;
    /**Magnitud de una señal*/
    private float magnitud;
    /**Valores de x magnitud**/
    private float xMagnitud;
    /**Fase de una señal**/
    private float fase;
    /**Valores de x fase**/
    private float xFase;
    /**FFT de un numero complejo**/
    private NumeroComplejo fftcomplejo;
    /**FFT de una señal**/
    private Señal fftseñal;
    /**Valores de x FFT de una señal**/
    private double xFftseñal;
    
     /**
     * Metodo constructor sin parametros
     */
    public Listas() {
    }

    /**
     * Metodo que muestra la lista de elementos creados en el simulador
     * @return lista
     */
    public LinkedList getLista() {
        return lista;
    }

    /**
     * Metodo que modifica la lista de elementos creados en el simulador
     * @param lista Lista de elementos
     */
    public void setLista(LinkedList lista) {
        this.lista = lista;
    }

    /**
     * Metodo que muestra la longitud de onda correspondiente a una lista de 
     * elementos
     * @return n
     */
    public int getN() {
        return n;
    }

    /**
     * Metodo que modifica la longitud de onda correspondiente a una lista de 
     * elementos
     * @param n Longitud de onda de una lista de elementos
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Metodo que muestra el numero complejo de una señal correspondiente a una 
     * lista de elementos
     * @return complejo
     */
    public NumeroComplejo getComplejo() {
        return complejo;
    }

    /**
     * Metodo que modifica el numero complejo de una señal correspondiente a una 
     * lista de elementos
     * @param complejo Numero complejo de una señal
     */
    public void setComplejo(NumeroComplejo complejo) {
        this.complejo = complejo;
    }

    /**
     * Metodo que muestra la magnitud del numero complejo de una señal 
     * correspondiente a una lista de elementos
     * @return magnitud
     */
    public float getMagnitud() {
        return magnitud;
    }

    /**
     * Metodo que modifica la magnitud del numero complejo de una señal 
     * correspondiente a una lista de elementos
     * @param magnitud Magnitud de una señal
     */
    public void setMagnitud(float magnitud) {
        this.magnitud = magnitud;
    }

    /**
     * Metodo que muestra la fase del numero complejo de una señal 
     * correspondiente a una lista de elementos
     * @return fase
     */
    public float getFase() {
        return fase;
    }

    /**
     * Metodo que modifica la fase del numero complejo de una señal 
     * correspondiente a una lista de elementos
     * @param fase Fase de una señal
     */
    public void setFase(float fase) {
        this.fase = fase;
    }

    /**
     * Metodo que muestra la Transformada Rapida de Fourier del numero complejo 
     * correspondiente a una lista de elementos
     * @return fftcomplejo
     */
    public NumeroComplejo getFftcomplejo() {
        return fftcomplejo;
    }

    /**
     * Metodo que modifica la Transformada Rapida de Fourier del numero complejo 
     * correspondiente a una lista de elementos
     * @param fftcomplejo Transformada Rapida de Fourier del numero complejo
     */
    public void setFftcomplejo(NumeroComplejo fftcomplejo) {
        this.fftcomplejo = fftcomplejo;
    }

    /**
     * Metodo que muestra la Transformada Rapida de Fourier de una señal  
     * correspondiente a una lista de elementos
     * @return fftseñal
     */
    public Señal getFftseñal() {
        return fftseñal;
    }

    /**
     * Metodo que modifica la Transformada Rapida de Fourier de una señal  
     * correspondiente a una lista de elementos
     * @param fftseñal Transformada Rapida de Fourier de una señal
     */
    public void setFftseñal(Señal fftseñal) {
        this.fftseñal = fftseñal;
    }

    /**
     * Metodo que muestra los valores de X numero complejo
     * @return xNumComplex
     */
    public double getxNumComplex() {
        return xNumComplex;
    }

    /**
     * Metodo que modifica los valores de X numero complejo
     * @param xNumComplex Valores de X numero complejo
     */
    public void setxNumComplex(double xNumComplex) {
        this.xNumComplex = xNumComplex;
    }

    /**
     * Metodo que muestra los valores de X magnitud del numero complejo
     * @return xMagnitud
     */
    public float getxMagnitud() {
        return xMagnitud;
    }

    /**
     * Metodo que modifica los valores de X magnitud del numero complejo
     * @param xMagnitud Valores de X magnitud
     */
    public void setxMagnitud(float xMagnitud) {
        this.xMagnitud = xMagnitud;
    }

    /**
     * Metodo que muestra los valores de X fase del numero complejo
     * @return xFase
     */
    public float getxFase() {
        return xFase;
    }

    /**
     * Metodo que modifica los valores de X fase del numero complejo
     * @param xFase Valores de X fase
     */
    public void setxFase(float xFase) {
        this.xFase = xFase;
    }

    /**
     * Metodo que muestra los valores de X Transformada Rapida de Fourier de una 
     * señal
     * @return xFftseñal
     */
    public double getxFftseñal() {
        return xFftseñal;
    }

    /**
     * Metodo que modifica los valores de X Transformada Rapida de Fourier de 
     * una señal
     * @param xFftseñal Valores de X Transformada Rapida de Fourier de una señal
     */
    public void setxFftseñal(double xFftseñal) {
        this.xFftseñal = xFftseñal;
    }
    
    /**
     * Metodo que duplica una lista de elementos
     * @return Lista de elementos duplicada
     * @throws java.lang.CloneNotSupportedException Proporciona diferentes excepciones lanzadas 
     * bajo el paquete java lang
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        Object obj=null;
        try{
            obj=super.clone();
        }
        catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
    
}
