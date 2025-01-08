
package optiuam.bc.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Clase Fuente la cual contiene los atributos principales de una fuente optica
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 * @author Carlos Elizarraras
 * @see Componente
 */
public class Fuente extends Componente {

    /**
     * Tipo de fuente. 0 laser | 1 led
     */
    private int tipo;
    /**
     * Longitud de onda de la fuente
     */
    private double longitudOnda;
    /**
     * Unidad de la longitud de onda de la fuente
     */
    private int unidadLongitudOnda;
    /**
     * Index de la longitud de onda de la fuente
     */
    private int indexLongitudOnda;
    /**
     * Potencia de la fuente. 50 max na min mecanico | 50 max na min fusion
     */
    private double potencia;
    /**
     * Unidad de la potencia de la fuente
     */
    private int unidadPotencia;
    /**
     * Anchura espectral de la fuente
     */
    private double anchura;
    /**
     * Pulso gaussiano o supergaussiano de la fuente
     */
    private double tipoPulso;
    /**
     * Chirp de la fuente
     */
    private double chirp;

    /**Para la modulacion*/
    /**
     * Tipo de modulacion
     */
    private int modulacion;
    /**
     * Span de transmision de la fuente
     */
    private double span;
    /**
     * Velocidad de transmision de la fuente
     */
    private double velocidad;
    /**
     * Posicion en el eje X de la fuente
     */
    private double posX;
    /**
     * Posicion en el eje Y de la fuente
     */
    private double posY;
    /**
     * Identificador de la fuente. Es diferente al identificador del componente
     */
    private int idFuente;

    /**Para el pulso de la fuente*/
    /**
     * Chirp
     */
    private double C = 0;
    /**
     * Amplitud
     */
    private double A0 = 0;
    /**
     * Frecuencia
     */
    private double Fc = 0;
    /**
     * Anchura
     */
    private double T0 = 0;
    /**
     * Pulso gausiano (M=1) o supergausiano (M>1)
     */
    private int M = 0;

    /**
     * Metodo constructor sin parametros
     */
    public Fuente() {
    }

    /**
     * Metodo constructor con parametros
     *
     * @param nombre            Nombre del componente
     * @param id                Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra
     *                          conectado la fuente
     * @param conectado         Indica si el componente esta conectado
     * @param tipo              Tipo de fuente
     * @param potencia          Potencia de la fuente
     * @param anchura           Anchura espectral de la fuente
     * @param velocidad         Velocidad de transmision de la fuente
     * @param longitudOnda      Longitud de onda de la fuente
     */
    public Fuente(String nombre, int id, String elementoConectado,
                  boolean conectado, int tipo, double potencia, double anchura,
                  double velocidad, double longitudOnda) {
        this.tipo = tipo;
        this.potencia = potencia;
        this.anchura = anchura;
        this.velocidad = velocidad;
        this.longitudOnda = longitudOnda;
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el tipo de la fuente
     *
     * @return tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Metodo que modifica el tipo de la fuente
     *
     * @param tipo Tipo de fuente. 0 laser | 1 led
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo que muestra la longitud de onda de la fuente
     *
     * @return longitudOnda
     */
    public double getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda de la fuente
     *
     * @param longitudOnda Longitud de onda de la fuente
     */
    public void setLongitudOnda(double longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    /**
     * Metodo que muestra la unidad de la longitud de onda de la fuente
     *
     * @return longitudOnda
     */
    public int getUnidadLongitudOnda() {
        return unidadLongitudOnda;
    }

    /**
     * Metodo que modifica la unidad de la longitud de onda de la fuente
     *
     * @param unidadLongitudOnda
     */
    public void setUnidadLongitudOnda(int unidadLongitudOnda) {
        this.unidadLongitudOnda = unidadLongitudOnda;
    }

    /**
     * Metodo que muestra el index de la longitud de onda de la fuente
     *
     * @return longitudOnda
     */
    public int getIndexLongitudOnda() {
        return indexLongitudOnda;
    }

    /**
     * Metodo que modifica el index de la longitud de onda de la fuente
     *
     * @param indexLongitudOnda
     */
    public void setIndexLongitudOnda(int indexLongitudOnda) {
        this.indexLongitudOnda = indexLongitudOnda;
    }

    /**
     * Metodo que muestra la potencia de la fuente
     *
     * @return potencia
     */
    public double getPotencia() {
        return potencia;
    }

    /**
     * Metodo que modifica la potencia de la fuente
     *
     * @param potencia 50 max na min mecanico | 50 max na min fusion
     */
    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    /**
     * Metodo que muestra la unidad de la potencia de la fuente
     *
     * @return potencia
     */
    public int getUnidadPotencia() {
        return unidadPotencia;
    }

    /**
     * Metodo que muestra la unidad de la potencia de la fuente
     *
     * @param unidadPotencia
     */
    public void setUnidadPotencia(int unidadPotencia) {
        this.unidadPotencia = unidadPotencia;
    }

    /**
     * Metodo que muestra la anchura espectral de la fuente
     *
     * @return anchura
     */
    public double getAnchura() {
        return anchura;
    }

    /**
     * Metodo que modifica la anchura espectral de la fuente
     *
     * @param anchura Anchura espectral de la fuente
     */
    public void setAnchura(double anchura) {
        this.anchura = anchura;
    }

    /**
     * Metodo que muestra el tipo de pulso de la fuente
     *
     * @return anchura
     */
    public double getTipoPulso() {
        return tipoPulso;
    }

    /**
     * Metodo que modifica el tipo de pulso de la fuente
     *
     * @param tipoPulso
     */
    public void setTipoPulso(double tipoPulso) {
        this.tipoPulso = tipoPulso;
    }

    /**
     * Metodo que muestra el chirp de la fuente
     *
     * @return anchura
     */
    public double getChirp() {
        return chirp;
    }

    /**
     * Metodo que modifica el tipo de pulso de la fuente
     *
     * @param chirp
     */
    public void setChirp(double chirp) {
        this.chirp = chirp;
    }

    /**
     * Metodo que muestra el tipo de modulacion de la fuente
     *
     * @return modulacion
     */
    public int getModulacion() {
        return modulacion;
    }

    /**
     * Metodo que modifica el tipo de modulacion de la fuente
     *
     * @param modulacion
     */
    public void setModulacion(int modulacion) {
        this.modulacion = modulacion;
    }

    /**
     * Metodo que muestra el span de la señal a modular
     *
     * @return modulacion
     */
    public double getSpan() {
        return span;
    }

    /**
     * Metodo que modifica el span de la señal a modular
     *
     * @param span
     */
    public void setSpan(double span) {
        this.span = span;
    }

    /**
     * Metodo que muestra la velocidad de transmision de la fuente
     *
     * @return velocidad
     */
    public double getVelocidad() {
        return velocidad;
    }

    /**
     * Metodo que modifica la velocidad de transmision de la fuente
     *
     * @param velocidad Velocidad de transmision de la fuente
     */
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Metodo que muestra el chirp del pulso de la fuente
     *
     * @return C
     */
    public double getC() {
        return C;
    }

    /**
     * Metodo que muestra la amplitud del pulso de la fuente
     *
     * @return A0
     */
    public double getA0() {
        return A0;
    }

    /**
     * Metodo que muestra la frecuencia del pulso de la fuente
     *
     * @return Fc
     */
    public double getFc() {
        return Fc;
    }

    /**
     * Metodo que muestra la anchura del pulso de la fuente
     *
     * @return T0
     */
    public double getT0() {
        return T0;
    }

    /**
     * Metodo que muestra si el pulso de la fuente es gausiano o supergausiano
     *
     * @return M
     */
    public int getM() {
        return M;
    }

    /**
     * Metodo que modifica los parametros del pulso de la fuente
     *
     * @param A0 Amplitud
     * @param T0 Anchura
     * @param Fc Frecuencia central
     * @param C  Chirp
     * @param M  Pulso gausiano o supergausiano
     */
    public void setPulso(double A0, double T0, double Fc, double C, int M) {
        this.A0 = A0;
        this.T0 = T0;
        this.Fc = Fc;
        this.C = C;
        this.M = M;
    }

    /**
     * Metodo que muestra el identificador de la fuente, no el del componente
     *
     * @return idFuente
     */
    public int getIdFuente() {
        return idFuente;
    }

    /**
     * Metodo que modifica el identificador de la fuente, no el del componente
     *
     * @param idFuente Identificador de la fuente
     */
    public void setIdFuente(int idFuente) {
        this.idFuente = idFuente;
    }

    /**
     * Metodo que muestra la posicion en el eje X de la fuente
     *
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X de la fuente
     *
     * @param posX Posicion en el eje X de la fuente
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y de la fuente
     *
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion en el eje Y de la fuente
     *
     * @param posY posicion en el eje Y de la fuente
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Metodo que calcula el pulso inicial de la fuente
     *
     * @return Pulso
     * @throws optiuam.bc.model.ExcepcionDivideCero Lanza una excepcion si
     *                                              se divide entre cero
     */
    public LinkedList<Listas> calcularPulso2() throws ExcepcionDivideCero {
        double Fsamp = 2 * (236 * Math.pow(10, 6)); // 2*FcMaxima*10^6
        float t0Aux = (float) (T0 * Math.pow(10, -6));
        float t_finalsim = (float) (1 * Math.pow(10, -6));
        float fcAux = (float) (Fc * Math.pow(10, 6)); // MHz

        int NFFT = 4096; // tamaño de muestra

        double Wc = 2 * (Math.PI) * (fcAux); // 2*pi*Fc*10^6

        NumeroComplejo jC = new NumeroComplejo(-1, (float) C); //-1+jC
        NumeroComplejo n1 = NumeroComplejo.cociente(jC, 2); //-1+jC / 2

        // t=0:1/Fsamp:10*T0;
        LinkedList t = new LinkedList();
        for (double i = 0; i < 10 * t_finalsim; i += (1 / Fsamp)) {
            t.add(i);
        }
        LinkedList<Listas> señales = new LinkedList();

        int j = 0;
        while (j < t.size()) {
            double z = (double) t.get(j); // t
            double aux = z / t0Aux; // t/T0
            double aux2m = Math.pow(aux, 2 * M);  // (t/T0)^2M
            NumeroComplejo n1tT0 = NumeroComplejo.producto(n1, (float) aux2m);
            NumeroComplejo n2 = NumeroComplejo.exponencial(n1tT0); //e^(-1+jC / 2)*[(t/T0)^2M]
            NumeroComplejo A = NumeroComplejo.producto(n2, (float) A0); // A0*{e^(-1+jC / 2)*[(t/T0)^2M]}

            NumeroComplejo jWc = new NumeroComplejo(0, (float) Wc); //jWc
            NumeroComplejo jWct = NumeroComplejo.producto(jWc, (float) z); // jWc*t
            NumeroComplejo ejWct = NumeroComplejo.exponencial(jWct); // e^(jWc*t)
            NumeroComplejo U = NumeroComplejo.productoComplejos(A, ejWct); // A*[e^(jWc*t)]

            Listas listaAUX = new Listas();
            listaAUX.setMagnitud(U.magnitud());
            listaAUX.setComplejo(U);
            listaAUX.setFase(U.getFase());
            listaAUX.setxNumComplex(z);

            señales.add(listaAUX);

            j++;
        }
        //FFT DEL NUMERO COMPLEJO
        //ventana_principal.calculosFFT(NFFT, señales, Fsamp);

        return señales;
    }

    //------------------------------------------------------------------------------
    private LinkedList<Double> aleatorio = new LinkedList<>();

    /**
     * Metodo que calcula la señal de la fuente
     *
     * @return Pulso
     */
    private void calculaGraficaSennal() {
        double aux, centro, longitudCentro = 0.005, w0 = 1.0;

        // Crear la serie de datos
        XYSeries series = new XYSeries("xy");
        //XYSeries series2 = new XYSeries("x_y");

        // Calcula y agrega los valores a la serie de datos
        for (double t = 0; t <= (longitudCentro) * aleatorio.size(); t += (longitudCentro / 10)) {
            aux = 0;
            centro = 0;
            for (int i = 0; i < aleatorio.size(); i++) {
                aux += aleatorio.get(i) * A0 * Math.exp(-Math.pow((t - centro), (2 * tipoPulso)) / (2 * Math.pow(T0, (2 * tipoPulso))));
                centro += longitudCentro;
            }
            //series2.add(t,aux);
            series.add(t, aux * Math.cos(2 * Math.PI * Fc * t));
        }
        System.out.println("" + series.getItemCount());

        // Crear el conjunto de datos
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        //dataset.addSeries(series2);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Signal - Magnitude",
                "Time (\u03BCs)",
                "Magnitude",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
        chart.setBackgroundPaint(new Color(173, 216, 230));

        // Personalizar el renderizador para que la línea sea una curva
        XYPlot plot = chart.getXYPlot();
        XYSplineRenderer splineRenderer = new XYSplineRenderer();
        splineRenderer.setSeriesShapesVisible(0, false); // No mostrar nodos
        //splineRenderer.setSeriesShapesVisible(1, false); // No mostrar nodos
        plot.setRenderer(splineRenderer);

        // Crear el panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));

        // Crear el JFrame
        JFrame frame = new JFrame();
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        // Mostrar el JFrame
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metodo que calcula la señal de la fuente
     *
     * @return Pulso
     */
    private LinkedList<Double> calcularSennal() {
        LinkedList<Double> sennal = new LinkedList<>();
        double aux, centro, longitudCentro = 0.005;
        sennal.add(longitudCentro / 4);
        for (double t = 0; t <= (longitudCentro) * aleatorio.size(); t += (longitudCentro / 4)) {
            aux = 0;
            centro = 0;
            for (int i = 0; i < aleatorio.size(); i++) {
                aux += aleatorio.get(i) * A0 * Math.exp(-Math.pow((t - centro), 2) / (2 * Math.pow(T0, 2)));
                centro += longitudCentro;
            }
            sennal.add(aux);
        }
        return sennal;
    }

    /**
     * Metodo que calcula la señal de la fuente usando un buffer circular
     *
     * @return Pulso
     */
    private XYSeries calcularSennalBuffer() {
        LinkedList<Double> sennal = new LinkedList<>();
        double[] elementosX = new double[2000];
        double[] elementosY = new double[2000];
        XYSeries series = new XYSeries("xy");
        BufferCircular bufferX = new BufferCircular(2000);
        BufferCircular bufferY = new BufferCircular(2000);
        double aux, centro, longitudCentro = 0.005, Fs = Fc / 128;
        int contBuffer = 1;
        for (double t = 0; t <= (longitudCentro) * aleatorio.size(); t += (1 / Fs)) {
            aux = 0;
            centro = 0;
            for (int i = 0; i < aleatorio.size(); i++) {
                aux += aleatorio.get(i) * A0 * Math.exp(-Math.pow((t - centro), 2) / (2 * Math.pow(T0, 2)));
                centro += longitudCentro;
            }
            if (contBuffer != 2000) {
                bufferX.agregar(t);
                bufferY.agregar(aux);
                contBuffer++;
            } else {
                contBuffer = 1;
                elementosX = bufferX.leerBuffer();
                elementosY = bufferY.leerBuffer();
                for (int i = 0; i < elementosX.length; i++) {
                    series.add(elementosX[i], elementosY[i]);
                }
            }
        }
        return series;
    }

    /**
     * Método para obtener el Buffer de la fuente
     */
    public void calcularDatos() {
        ArrayList<Double> datos = new ArrayList<>();

        double aux, centro, longitudCentro = 0.005, Fs = Fc / 128;
        for (double t = 0; t <= (longitudCentro) * aleatorio.size(); t += (1 / Fs)) {
            aux = 0;
            centro = 0;
            for (int i = 0; i < aleatorio.size(); i++) {
                aux += aleatorio.get(i) * A0 * Math.exp(-Math.pow((t - centro), 2) / (2 * Math.pow(T0, 2)));
                centro += longitudCentro;
            }
            datos.add(aux);
            tiempo.add(t);
        }
        setDatos(datos);
    }

    /**
     * Método que gestiona las graficas de la fuente
     */
    public void graficas() {
        System.out.println("C:" + C + " A0:" + A0 + " Fc:" + Fc + " T0:" + T0 + " M:" + M);
        GeneradorSeAl portadora = new GeneradorSeAl();
        aleatorio = portadora.generar((int) (1000 * velocidad * span));
    }

    /**
     * Método que grafica la señal de la fuente
     */
    private void graficarSennal() {
        LinkedList<Double> sennal = new LinkedList<>();
        sennal = calcularSennal();
        System.out.println("" + sennal.size());
        // Crear la serie de datos
        XYSeries series = new XYSeries("xy");

        // Agregar los valores de las listas a la serie de datos
        for (int i = 1; i < sennal.size(); i++) {
            series.add(sennal.get(0) * (i - 1), sennal.get(i));
        }

        // Crear el conjunto de datos
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Signal - Magnitude",
                "Time (\u03BCs)",
                "Magnitude",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
        chart.setBackgroundPaint(new Color(173, 216, 230));
        // Crear el panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));

        // Crear el JFrame
        JFrame frame = new JFrame();
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        // Mostrar el JFrame
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metodo que grafica la FFT de la fuente
     */
    public void graficaFFT() {

    }

    /**
     * Metodo que grafica la señal de la fuente usando un buffer circular
     */
    private void graficarSennalBuffer() {
        // Crear la serie de datos
        XYSeries series = calcularSennalBuffer();
        System.out.println("" + series.getItemCount());

        // Crear el conjunto de datos
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Signal - Magnitude",
                "Time (\u03BCs)",
                "Magnitude",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
        chart.setBackgroundPaint(new Color(173, 216, 230));
        // Crear el panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));

        // Crear el JFrame
        JFrame frame = new JFrame();
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        // Mostrar el JFrame
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metodo toString que retorna los atributos de una fuente
     *
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada,
     * conectadoSalida, elementoConectadoSalida, tipo, potencia,
     * anchura, velocidad, longitudOnda, idFuente
     */
    @Override
    public String toString() {
        return super.toString() + "," + tipo + "," + potencia + "," + anchura + ","
                + velocidad + "," + longitudOnda + "," + A0 + "," + T0 + ","
                + Fc + "," + C + "," + M + "," + idFuente + "," + span;
    }

}