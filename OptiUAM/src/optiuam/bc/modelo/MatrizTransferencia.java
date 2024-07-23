
package optiuam.bc.modelo;

/**
 * Clase MatrizTransferencia la cual contiene la implementación de la matriz de 
 * transferencia para obtener el comportamiento de la rejilla de Bragg en la 
 * fibra optica.
 * @author Karen Cruz
 */
public class MatrizTransferencia {
    
    /**Periodo de la rejilla de Bragg*/
    private double periodo;
    /**Cambio de magnitud del indice de refraccion*/
    private double cambioIndRed = Math.pow(10, -5);
    /**Indice de refraccion efectivo*/
    private double indRef = 1.46;
    /**Relacion de dispersion*/
    private double omega;
    /**Constante de acoplamiento*/
    private double acoplamiento;
    /**Factor de desintonia*/
    private double desintonia;
    /**Coeficiente de reflexion*/
    public  double reflexion;
    /**Coeficiente de transmision*/
    public double transmision;

    /**
     * Metodo que muestra el coeficiente de reflexion de la FBG
     * @return reflexion
     */
    public double getReflexion() {
        return reflexion;
    }

    /**
     * Metodo que modifica el coeficiente de reflexion de la FBG
     * @param reflexion Coeficiente de reflexion de la FBG
     */
    public void setReflexion(double reflexion) {
        this.reflexion = reflexion;
    }

    /**
     * Metodo que muestra el coeficiente de transmision de la FBG
     * @return transmision
     */
    public double getTransmision() {
        return transmision;
    }

    /**
     * Metodo que modifica el coeficiente de transmision de la FBG
     * @param transmision Coeficiente de transmision de la FBG
     */
    public void setTransmision(double transmision) {
        this.transmision = transmision;
    }

    /**
     * Metodo que calcula la matriz de transferencia para obtener los coeficientes 
     * de reflexion y transmision
     * @param dz Longitud de la FBG
     * @param longitudOnda Longitud de onda de la FBG
     * @return reflexion+transmision
     */
    public double calculoMatriz(double dz, int longitudOnda){
        //dz=1;
        //longitudOnda=1550;
        periodo = longitudOnda / (2*indRef);
        acoplamiento = (Math.PI*cambioIndRed*indRef) / longitudOnda;
        desintonia = ((2*Math.PI*indRef)/longitudOnda) - (Math.PI/periodo);
        omega = Math.sqrt(Math.pow(acoplamiento, 2) - Math.pow(desintonia, 2));

        double m11 = (Math.cosh(omega*dz) - 1*((desintonia*Math.sinh(omega*dz))/omega));
        double m12 = -1*(acoplamiento*Math.sinh(omega*dz)/omega);
        double m21 = 1*(acoplamiento*Math.sinh(omega*dz)/omega);
        double m22 = (Math.cosh(omega*dz) + 1*((desintonia*Math.sinh(omega*dz))/omega));
        
        reflexion = m21 / m11;
        transmision = (m11-m21) / m11;
        return reflexion + transmision;
    }
    
}
