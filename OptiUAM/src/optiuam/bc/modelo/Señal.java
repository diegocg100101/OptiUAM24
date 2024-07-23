
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Señal la cual contiene los atributos principales de una señal
 * @author Arturo Borja
 */
public class Señal implements Cloneable{
    
    /**Para el pulso de la Señal*/
    /**Chirp*/
    private float C = 0;
    /**Amplitud*/
    private float A0 = 0;
    /**Frecuencia*/
    private float Fc = 0;
    /**Anchura*/
    private float T0 = 0;
    /**Pulso gausiano o supergausiano*/
    private float M = 0;
    /**Valores del vector de magnitud**/
    private LinkedList<Listas> valoresMagnitud;
    /**Indica si se ha sumado la señal*/
    private boolean sumado;
    /**Valores del vector de FFT de numeros reales**/
    private float[] fftvaloresReales;
    /**Valores del vector de FFT de numeros imaginarios**/
    private float[] fftvaloresImag;
    /**Valores del vector de FFT de numeros complejos*/
    private NumeroComplejo[] fft;
    
    /**
    * Metodo constructor sin parametros
    */
    public Señal() {
    }

    /**
     * Metodo que muestra si se ha sumado la señal
     * @return sumado
     */
    public boolean isSumado() {
        return sumado;
    }

    /**
     * Metodo que modifica si se ha sumado la señal
     * @param sumado Indica si se ha sumado la señal
     */
    public void setSumado(boolean sumado) {
        this.sumado = sumado;
    }
    
    /**
     * Metodo que muestra la amplitud del pulso de la fuente
     * @return A0
     */
    public float getA0() {
        return A0;
    }

    /**
     * Metodo que modifica la amplitud del pulso de la fuente
     * @param A0 Amplitud
     */
    public void setA0(float A0) {
        this.A0 = A0;
    }

    /**
     * Metodo que muestra la frecuencia del pulso de la fuente
     * @return Fc
     */
    public float getFc() {
        return Fc;
    }

    /**
     * Metodo que modifica la frecuencia del pulso de la fuente
     * @param Fc Frecuencia central
     */
    public void setFc(float Fc) {
        this.Fc = Fc;
    }

    /**
     * Metodo que muestra la anchura del pulso de la fuente
     * @return T0
     */
    public float getT0() {
        return T0;
    }

    /**
     * Metodo que modifica la anchura del pulso de la fuente
     * @param T0 Anchura
     */
    public void setT0(float T0) {
        this.T0 = T0;
    }

    /**
     * Metodo que muestra si el pulso de la fuente es gausiano o supergausiano
     * @return M
     */
    public float getM() {
        return M;
    }

    /**
     * Metodo que modifica si el pulso de la fuente es gausiano o supergausiano
     * @param M Pulso gausiano o supergausiano
     */
    public void setM(float M) {
        this.M = M;
    }

    /**
     * Metodo que muestra el chirp del pulso de la fuente
     * @return C
     */
    public float getC() {
        return C;
    }

    /**
     * Metodo que modifica el chirp del pulso de la fuente
     * @param C Chirp
     */
    public void setC(float C) {
        this.C = C;
    }

    /**
     * Metodo que muestra los valores del vector de magnitud
     * @return valoresMagnitud
     */
    public LinkedList<Listas> getValoresMagnitud() {
        return valoresMagnitud;
    }

    /**
     * Metodo que modifica los valores del vector de magnitud
     * @param valoresMagnitud Valores del vector de magnitud
     */
    public void setValoresMagnitud(LinkedList valoresMagnitud) {
        this.valoresMagnitud = valoresMagnitud;
    }
    
    /**
     * Metodo que muestra los valores del vector la Transformada Rapida de 
     * Fourier de numeros reales
     * @return fftvaloresReales
     */
    public float[] getFftvaloresReales() {
        return fftvaloresReales;
    }

    /**
     * Metodo que modifica los valores del vector la Transformada Rapida de 
     * Fourier de numeros reales
     * @param fftvaloresReales Valores del vector de FFT de numeros reales
     */
    public void setFftvaloresReales(float[] fftvaloresReales) {
        this.fftvaloresReales = fftvaloresReales;
    }

    /**
     * Metodo que muestra los valores del vector la Transformada Rapida de 
     * Fourier de numeros imaginarios
     * @return fftvaloresImag
     */
    public float[] getFftvaloresImag() {
        return fftvaloresImag;
    }

    /**
     * Metodo que modifica los valores del vector la Transformada Rapida de 
     * Fourier de numeros imaginarios
     * @param fftvaloresImag Valores del vector de FFT de numeros imaginarios
     */
    public void setFftvaloresImag(float[] fftvaloresImag) {
        this.fftvaloresImag = fftvaloresImag;
    }

    /**
     * Metodo que muestra los valores del vector la Transformada Rapida de 
     * Fourier de numeros complejos
     * @return fft
     */
    public NumeroComplejo[] getFft() {
        return fft;
    }

    /**
     * Metodo que modifica los valores del vector la Transformada Rapida de 
     * Fourier de numeros complejos
     * @param fft Valores del vector de FFT de numeros complejos
     */
    public void setFft(NumeroComplejo[] fft) {
        this.fft = fft;
    }
    
    /**
     * Metodo que duplica una señal
     * @return Señal duplicada
     * @throws java.lang.CloneNotSupportedException Proporciona diferentes excepciones lanzadas 
     * bajo el paquete java lang
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
    
    /**
     * Metodo toString que retorna los atributos de una señal
     * @return A0, Fc, T0, M, C
     */
    @Override
    public String toString() {
        return "Señal{" + "A0=" + A0 + ", Fc=" + Fc + ", T0=" + T0 + ", M=" + M + '}';
    }
    
}
