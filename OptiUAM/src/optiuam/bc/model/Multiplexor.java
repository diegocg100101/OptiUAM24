
package optiuam.bc.model;

import java.util.ArrayList;
import java.util.LinkedList;



/**
 * Clase Multiplexor la cual contiene los atributos principales de un
 * multiplexor
 *
 * @author Arturo Borja
 * @see Componente
 */
public class Multiplexor extends Componente {

    /**
     * Identificador del multiplexor. Es diferente al identificador del
     * componente
     */
    private int idMux;
    /**
     * String donde se actualiza el contenido de las entradas del multiplexor
     */
    private StringBuilder cEntradas;
    /**
     * Lista de los puertos de entrada del multiplexor
     */
    private LinkedList<PuertoEntrada> conexionEntradas;
    /**
     * Numero de entradas del multiplexor
     */
    private int entradas;
    /**
     * Perdida de insercion del multiplexor
     */
    private double perdidaInsercion;
    /**
     * Lista de las señales en el multiplexor
     */
    private LinkedList<Listas> senalesTotal;

    /**
     * Almacena las señales de las entradas
     */
    public ArrayList<ArrayList<Double>> senales = new ArrayList<>();

    /**
     * Almacena la suma de las señales de entrada
     */
    public ArrayList<Double> senalSalida = new ArrayList<>();

    /**
     * Metodo constructor sin parametros
     */
    public Multiplexor() {
        this.conexionEntradas = new LinkedList();
    }

    /**
     * Metodo constructor con parametros
     *
     * @param nombre            Nombre del componente
     * @param id                Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra
     *                          conectado con el multiplexor
     * @param conectado         Indica si el componente esta conectado
     */
    public Multiplexor(String nombre, int id, String elementoConectado,
                       boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del multiplexor, no el del componente
     *
     * @return idMux
     */
    public int getIdMux() {
        return idMux;
    }

    /**
     * Metodo que modifica el identificador del multiplexor, no el del
     * componente
     *
     * @param idMux Identificador del multiplexor
     */
    public void setIdMux(int idMux) {
        this.idMux = idMux;
    }

    /**
     * Metodo que muestra la conversion de los puertos entrada del multiplexor
     * a un String
     *
     * @return entradas
     */
    public StringBuilder getcEntradas() {
        return cEntradas;
    }

    /**
     * Metodo que modifica la conversion de los puertos entrada del multiplexor
     * a un String
     *
     * @param cEntradas Conversion de puertos entrada
     */
    public void setcEntradas(StringBuilder cEntradas) {
        this.cEntradas = cEntradas;
    }

    /**
     * Metodo que muestra las conexiones del multiplexor
     *
     * @return conexiones
     */
    public LinkedList<PuertoEntrada> getConexionEntradas() {
        return conexionEntradas;
    }

    /**
     * Metodo que modifica las conexiones del multiplexor
     *
     * @param conexionEntradas Conexiones del multiplexor
     */
    public void setConexionEntradas(LinkedList<PuertoEntrada> conexionEntradas) {
        this.conexionEntradas = conexionEntradas;
    }

    /**
     * Metodo que muestra el numero de entradas del multiplexor
     *
     * @return entradas
     */
    public int getEntradas() {
        return entradas;
    }

    /**
     * Metodo que modifica el numero de entradas del multiplexor
     *
     * @param entradas Numero de entradas del multiplexor
     */
    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    /**
     * Metodo que muestra la perdida de insercion del multiplexor
     *
     * @return perdidaInsercion
     */
    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    /**
     * Metodo que modifica la perdida de insercion del multiplexor
     *
     * @param perdidaInsercion Perdida de insercion del multiplexor
     */
    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    /**
     * Metodo que modifica las entardas del multiplexor
     *
     * @param entradas Entradas del multiplexor
     */
    public void modificarEntradas(int entradas) {
        cEntradas = new StringBuilder();
        for (int i = 0; i < entradas - 1; i++) {
            PuertoEntrada puerto = new PuertoEntrada();
            conexionEntradas.add(puerto);
            cEntradas.append(conexionEntradas.get(i).toString());
        }
    }

    public void sumarDatos(ElementoGrafico elemG) {
        Multiplexor mux = new Multiplexor();
        for (int i = 0; i < 8940; i++) {
            double aux=0;
            for (ArrayList<Double> datos : mux.getSenales()){
                aux+=datos.get(i);
            }
            senalSalida.add(aux);
        }
        //            for(int i = 0; i < 8000; i++) {
//                double aux = 0;
//                for(ArrayList<Double> datos : mux.getSenales()){
//                    aux += datos.get(i);
//                }
//                senalSalida.add(aux);
//            }



        //            /*
//            Este es el arreglo de la salida
//            Es la suma de cada una de las componentes de las señales
//             */
//
//            Esta es la señal de salida que se obtiene con mux.getSenalSalida()
//            ArrayList<Double> senalSalida = new ArrayList<>();
//
//            for(int i = 0; i < 8000; i++) {
//                double aux = 0;
//                for(ArrayList<Double> datos : mux.getSenales()){
//                    aux += datos.get(i);
//                }
//                sumaSenal.add(aux);
//            }
    }

    /**
     * Metodo que multiplica los valores de la señal actual con la atenuacion
     * del multiplexor
     *
     * @param valores Valores de la señal actual
     * @return Valores con la atenuacion del multiplexor
     */
    public LinkedList<Listas> valorMagnitudPerdida(LinkedList<Listas> valores) {
        int n = 0;
        while (n < valores.size()) {
            NumeroComplejo aux = valores.get(n).getComplejo();
            aux.producto(aux, (float) Math.pow(10, (-perdidaInsercion / 10)));
            valores.get(n).setComplejo(aux);
            n++;
        }
        return valores;
    }

    /**
     * Metodo que muestra la lista de las señales en el multiplexor
     *
     * @return señalesTotal
     */
    public LinkedList<Listas> getSenalesTotal() {
        return senalesTotal;
    }

    /**
     * Metodo que modifica la lista de las señales en el multiplexor
     *
     * @param senalesTotal Lista de señales que hay en el multiplexor
     */
    public void setSenalesTotal(LinkedList<Listas> senalesTotal) {
        this.senalesTotal = senalesTotal;
    }

    public ArrayList<ArrayList<Double>> getSenales() {
        return senales;
    }

    public void setSenales(ArrayList<ArrayList<Double>> senales) {
        this.senales = senales;
    }

    public ArrayList<Double> getSenalSalida() {
        return senalSalida;
    }

    public void setSenalSalida(ArrayList<Double> senalSalida) {
        this.senalSalida = senalSalida;
    }

    /**
     * Metodo toString que retorna los atributos del multiplexor
     *
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada,
     * conectadoSalida, elementoConectadoSalida, idMux, entradas,
     * perdidaInsercion,cEntradas
     */
    @Override
    public String toString() {
        cEntradas = new StringBuilder();
        for (int i = 0; i < entradas - 1; i++) {
            cEntradas.append(conexionEntradas.get(i).toString());
        }
        return super.toString() + "," + idMux + "," + entradas + "," +
                perdidaInsercion + cEntradas;
    }

}
