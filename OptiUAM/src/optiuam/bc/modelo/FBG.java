
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase FBG la cual contiene los atributos principales de una rejilla de Bragg 
 * (FBG) asi como el filtro elaborado a partir de esta
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class FBG extends Componente {
    
    /**Numero de salidas del FBG*/                    
    private int salidas;
    /**Puertos salida del FBG*/
    private StringBuilder cSalidas;
    /**Conexiones del FBG*/
    private LinkedList<PuertoSalida> conexiones;
    /**Identificador de la rejilla de Bragg. Es diferente al identificador del 
     * componente*/
    private int idFBG;
    /**Coeficiente de reflexion de la FBG*/
    private double reflexion;
    /**Coeficiente de transmision de la FBG*/
    private double transmision;
    /**Longitud de la j-esima seccion uniforme*/
    private double dz;
    /**Longitud de onda de la rejilla de Bragg*/
    private double longitudOnda;
    /**Frecuencia de muestreo*/
    double Fsamp = 2*(236*Math.pow(10, 6)); //2*FcMaxima*10^6
    
    /**
    * Metodo constructor sin parametros
    */
    public FBG() {
        this.conexiones = new LinkedList();
    }
    
    /**
     * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra 
     * conectado con la rejilla de Bragg
     * @param conectado Indica si el componente esta conectado
     * @param salidas Numero de salidas del FBG
    */
    public FBG(String nombre, int id,String elementoConectado, boolean conectado, 
            int salidas) {
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra el numero de salidas del FBG
     * @return salidas
     */
    public int getSalidas() {
        return salidas;
    }
    
    /**
     * Metodo que modifica el numero de salidas del FBG
     * @param salidas Numero de salidas del FBG
     */
    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }
    
    /**
     * Metodo que muestra las conexiones del FBG
     * @return conexiones
     */
    public LinkedList<PuertoSalida> getConexiones() {
        return conexiones;
    }

    /**
     * Metodo que modifica las conexiones del FBG
     * @param conexiones Conexiones del FBG
     */
    public void setConexiones(LinkedList<PuertoSalida> conexiones) {
        this.conexiones = conexiones;
    }

    /**
     * Metodo que muestra el identificador de la rejilla de Bragg, no el del 
     * componente
     * @return idFBG
     */
    public int getIdFBG() {
        return idFBG;
    }

    /**
     * Metodo que modifica el identificador de la rejilla de Bragg, no el del
     * componente
     * @param idFBG Identificador de la rejilla de Bragg (FBG)
     */
    public void setIdFBG(int idFBG) {
        this.idFBG = idFBG;
    }

    /**
     * Metodo que muestra el coeficiente de reflexion de la rejilla de Bragg
     * @return reflexion
     */
    public double getReflexion() {
        return reflexion;
    }

    /**
     * Metodo que modifica el coeficiente de reflexion de la rejilla de Bragg
     * @param reflexion Coeficiente de reflexión de la FBG
     */
    public void setReflexion(double reflexion) {
        this.reflexion = reflexion;
    }

    /**
     * Metodo que muestra el coeficiente de transmision de la rejilla de Bragg
     * @return transmision
     */
    public double getTransmision() {
        return transmision;
    }

    /**
     * Metodo que modifica el coeficiente de transmision de la rejilla de Bragg
     * @param transmision Coeficiente de trasmision de la FBG
     */
    public void setTransmision(double transmision) {
        this.transmision = transmision;
    }

    /**
     * Metodo que muestra la longitud de la j-esima seccion uniforme de la
     * rejilla de Bragg
     * @return dz
     */
    public double getDz() {
        return dz;
    }

    /**
     * Metodo que modifica la longitud de la j-esima seccion uniforme de la 
     * rejilla de Bragg
     * @param dz Longitud de la j-esima seccion uniforme de la FBG
     */
    public void setDz(double dz) {
        this.dz = dz;
    }

    /**
     * Metodo que muestra la longitud de onda a filtrar de la rejilla de Bragg
     * @return longitudOnda
     */
    public double getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda a filtrar de la rejilla de Bragg
     * @param longitudOnda Longitud de onda a filtrar de la FBG
     */
    public void setLongitudOnda(double longitudOnda) {
        this.longitudOnda = longitudOnda;
    }
    
    /**
     * Metodo que muestra la conversion de los puertos salida del FBG
     * a un String
     * @return salidas
     */
    public StringBuilder getcSalidas() {
        return cSalidas;
    }

    /**
     * Metodo que modifica la conversion de los puertos salida del FBG
     * a un String
     * @param cSalidas Conversion de puertos salida
     */
    public void setcSalidas(StringBuilder cSalidas) {
        this.cSalidas = cSalidas;
    }
    
    /**
     * Metodo que actualiza las salidas del FBG
     * @param salidas Salidas del FBG
     */
    public void actuaizarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
    }
    
    /**
     * Metodo que realiza la filtracion mediante Rejillas de Bragg utilizando el
     * Metodo de Matriz de Transferencia
     * @param OndaL Longitud de onda a filtrar
     * @param dz Longitud de la j-esima seccion uniforme
     * @return Señal filtrada
     */
    public LinkedList filtro(double OndaL, double dz){
        double l1 =  (OndaL-1)*Math.pow(10, -6);
        double l2 = (OndaL+1)*Math.pow(10, -6);

        LinkedList lam = new LinkedList();
        //lam=l1:0.001e-9:l2
        for(double i = l1; i < l2; i+=(1/Fsamp)){
            lam.add(i);
        }
       
        double neff=1.46; //Indice de refraccion efectivo
        double lonOnda= (OndaL)*Math.pow(10, -6); 
        //double per= hd/2*neff; //periodo
        double L=dz; //Longitud de la fibra
        double dneff=1e-3;
        
        //k=(pi./lam)*dneff;
        LinkedList k = new LinkedList();
        LinkedList vd = new LinkedList();
        for(int i = 0; i < lam.size(); i++){
            k.add((Math.PI/(double)lam.get(i))*dneff);
            vd.add((2*Math.PI*neff)*((1/(double)lam.get(i))-(1/lonOnda)));
        }
        
        //dcgen=vd; % COEFICIENTE GENERAL DE ACOPLAMIENTO
        LinkedList dcgen = vd;
        
        //DCgen=dcgen.*dcgen; %dcgen ^2
        LinkedList DCgen = new LinkedList();
        LinkedList K = new LinkedList();
        for(int i = 0; i < dcgen.size(); i++){
            DCgen.add((double)dcgen.get(i)*(double)dcgen.get(i));
            K.add((double)k.get(i)*(double)k.get(i));
        }
        
        //R=(sinh(L*sqrt(K-DCgen)).^2) ./(cosh(L*sqrt(K-DCgen)).^2-DCgen./K); %REFLEXION 
        LinkedList omega = new LinkedList();
        LinkedList R = new LinkedList();
        LinkedList R2 = new LinkedList();
        LinkedList R4 = new LinkedList();
        LinkedList R3 = new LinkedList();
        LinkedList R5 = new LinkedList();
        
        for(int i = 0; i < K.size(); i++){
            if((double)K.get(i)-(double)DCgen.get(i)<=0){
                omega.add(0.0);
            }
            else{
                omega.add(Math.sqrt(((double)K.get(i)-(double)DCgen.get(i))));
            }
            R.add((Math.pow(Math.sinh(L*(double)omega.get(i)), 2)));
            R2.add(Math.pow(Math.cosh(L*(double)omega.get(i)), 2));
           
            R4.add((double)DCgen.get(i)/(double)K.get(i));
            R5.add((double)R2.get(i)-(double)R4.get(i));
            R3.add((double)R.get(i)/(double)R5.get(i));
        }
        double l11 =  (299792458.0e-3/(OndaL-7)*Math.pow(10, 6));
        double l22 = (299792458.0e-3/(OndaL+7)*Math.pow(10, 6));
        LinkedList Rreal = new LinkedList();
        //System.out.println("l1=  "+l11);
        //System.out.println("l2=  "+l22);
        
        for(double i = 0; i <4096; i++){
            double longitud=(i)*(Fsamp/4096);
            if(longitud<l22||l11<longitud){
                Rreal.add(0.0);
            }
            else{
                Rreal.add(1.0);
            }
        }
        /*(Math.pow(Math.cosh(L*Math.sqrt((double)K.get(i)-(double)DCgen.get(i))), 2) 
        - ((double)DCgen.get(i)/(double)K.get(i))))*/
       return Rreal;
    }
    
    /**
     * Metodo que multiplica los valores de la señal actual con la atenuacion del
     * conector
     * @param valores Valores de la señal actual
     * @return Valores con la atenuacion del conector
     */
    public LinkedList<Listas> valorMagnitudPerdida(LinkedList<Listas> valores){
        int n=0;
        while(n<valores.size()){
            NumeroComplejo aux= valores.get(n).getComplejo();
            aux=aux.producto(aux, (float) Math.pow(10, (-0.001/10)));
            valores.get(n).setComplejo(aux);
            valores.get(n).setMagnitud(aux.magnitud());
            n++;
        }
        return valores; 
    }
    
    /**
     * Metodo toString que retorna los atributos de la rejilla de Bragg
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, idFBG, reflexion, transmision, 
     * dz, longitudOnda
     */
    @Override
    public String toString() {
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
        return super.toString()+","+idFBG+","+reflexion+
                ","+transmision+","+dz+","+longitudOnda+cSalidas.toString();
    }
    
}
