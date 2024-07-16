
package optiuam.bc.controlador;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.FBG;
import optiuam.bc.modelo.FFT;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Multiplexor;
import optiuam.bc.modelo.NumeroComplejo;
import optiuam.bc.modelo.Listas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Clase VentanaEspectroController la cual se encarga de proporcionar la
 * funcionalidad al analizador de espectro
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class VentanaEspectroController implements Initializable {
    
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Controlador del espectro*/
    VentanaEspectroController espectroControl;
    /**Elemento grafico del medidor de espectro*/
    ElementoGrafico elem;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Lista de enlaces creados*/
    LinkedList<Listas> listaListas= new LinkedList();
    
    /**Boton que permite visualizar el pulso de forma lineal*/
    @FXML
    Button btnPulsoEntrada;
    /**Boton que permite visualizar el pulso de forma logaritmica*/
    @FXML
    Button btnLogaritmo;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo el cual inicializa la ventana del medidor de espectro
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    /**
     * Metodo para cerrar la ventana del medidor de espectro
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
      
    /**
     * Metodo que realiza los calculos para poder visualizar el pulso a la 
     * entrada correctamente
     * @param A0 Amplitud del pulso de la fuente
     * @param T0 Anchura del pulso de la fuente
     * @param W0 Frecuencia del pulso de la fuente
     * @param C Chirp del pulso de la fuente
     * @param M Pulso de la fuente gausiano o supergausiano
     * @return corrimiento logico a la derecha
     */
    public float[] pulsoEntrada(float A0,float T0,float W0,float C,float M){
        int n = 512;
        if(T0 <=35)
            n=256;
        else if(T0 >35 && T0< 100)
            n= 512;
        else
            n=1024;
     
        NumeroComplejo[] Et= calcularEnvolvente(n,false,A0,T0,W0,C,M);

        //se separa de la envolvente las partes reales y las imaginarias
        //ya que al ser un numero complejo se debe obtener la fft de cada parte tratandola como real
        //y despues crear bien el numero complejo resultante
        float[] valoresReales = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresReales[(n/2)+t]= Et[(n/2)+t].getRealPart();
        }
        
        float[] valoresImaginarios = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresImaginarios[(n/2)+t]= Et[(n/2)+t].getImaginaryPart();
        }
        
        //calcular la fft de las partes reales y de las imaginarias
        valoresReales=calcularFFT_real(valoresReales, n);
	valoresImaginarios=calcularFFT_real(valoresImaginarios, n);
        
        //obtner el abs(numero complejo)
        float [] absFFT= new float[n];
              for(int i = -(n/2); i <(n/2) ; i++){
                        absFFT[(n/2)+i]= new NumeroComplejo(valoresReales[(n/2)+i],valoresImaginarios[(n/2)+i]).getAmplitud();
                        //System.out.println((n/2)+i+1+" "+absFFT[(n/2)+i]);
                }
              
        //hacer el corriemiento a la derecha fftshift en Matlab
        return fftShift_float(absFFT, n);
    }
    
    /**
     * Metodo que calcula la envolvente del pulso
     * @param n Para calcular la envolvente
     * @param salida Precision para el pulso a la salida
     * @param A0 Amplitud del pulso de la fuente
     * @param T0 Anchura del pulso de la fuente
     * @param W0 Frecuencia del pulso de la fuente
     * @param C Chirp del pulso de la fuente
     * @param M Pulso de la fuente gausiano o supergausiano
     * @return arreglo de numeros complejos
     */
    public NumeroComplejo [] calcularEnvolvente(int n,boolean salida,float A0,float T0,float W0,float C,float M){
        //si es para el pulso de salida se necesita mas presicion
        NumeroComplejo[] Et= null;
        if(salida){
                    //n=n/10;
            NumeroComplejo complejo = new NumeroComplejo(0, 1); // i o j
            NumeroComplejo chirpXi= complejo.multiplicar(C, true);// i*C
            chirpXi.sumar(new NumeroComplejo(1, 0), false); //1 + iC
            chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)

            //System.out.println(chirpXi.toString());
            Et= new NumeroComplejo[n];
            NumeroComplejo aux=null;

            double naux = n;
            double t = -((naux/2)/10);

            for(int i=0; i<n;i++){
                t= Math.floor(t * 10) / 10;
                aux = new NumeroComplejo(chirpXi.getRealPart(), chirpXi.getImaginaryPart());
                aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
                Et[i]=aux;
                t = t + 0.1F;
                //System.out.println((t+1+256)+""+aux.toString());
            }

            for(int i=0; i<n;i++){
                 NumeroComplejo aux2 = new NumeroComplejo(Et[i].getRealPart(),Et[i].getImaginaryPart());
                //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 float x = aux2.getRealPart();
                 float y = aux2.getImaginaryPart();


                 aux2.setRealPart((float) (Math.exp(x)*Math.cos(y)));
                 aux2.setImaginaryPart((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
                 //System.out.println(aux2.getParteReal()+"\t"+aux2.getParteImaginaria());
                 //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 aux2.multiplicar(A0,false);//A0*U(0,T)
                 Et[i]=aux2;
                 //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());

            }
        }else{
            NumeroComplejo complejo = new NumeroComplejo(0, 1); // i o j
            NumeroComplejo chirpXi= complejo.multiplicar(C, true);// i*C
            chirpXi.sumar(new NumeroComplejo(1, 0), false); //1 + iC
            chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)

            //System.out.println(chirpXi.toString());
            Et= new NumeroComplejo[n];
            NumeroComplejo aux=null;
            for(int t=-(n/2); t<(n/2);t++){
                aux = new NumeroComplejo(chirpXi.getRealPart(), chirpXi.getImaginaryPart());
                aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
                Et[(n/2)+t]=aux;
                //System.out.println((t+1+256)+""+aux.toString());
            }
            NumeroComplejo aux2=null;
            for(int t=-(n/2); t<(n/2);t++){
                 aux2 = new NumeroComplejo(Et[(n/2)+t].getRealPart(),Et[(n/2)+t].getImaginaryPart());
                //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 float x = aux2.getRealPart();
                 float y = aux2.getImaginaryPart();

                 aux2.setRealPart((float) (Math.exp(x)*Math.cos(y)));
                 aux2.setImaginaryPart((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
                 //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 aux2.multiplicar(A0,false);//A0*U(0,T)
                 Et[(n/2)+t]=aux2;
                 //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());
            }
        }
        return Et;
    } 
    
    /**
     * Metodo que aplica un corrimiento logico a la derecha desde la mitad 
     * del arreglo de valores flotantes
     * @param valores Arreglo de valores flotantes
     * @param n Para realizar el corrimiento
     * @return corrimiento logico a la derecha
     */
    public float[] fftShift_float(float [] valores,int n){
        float [] fftshift = new float[n];
        for(int i = (n/2); i < n; i++){
            fftshift[i]=valores[i-(n/2)];
        }
        for(int j = 0; j < (n/2); j++){
            fftshift[j]=valores[j+(n/2)];
        }
        return fftshift;        
    }
    
    /**
     * Metodo que calcula la Transformada Rapida de Fourier y retorna solo la
     * parte real
     * @param datos Arreglo de datos para calcular la FFT
     * @param n Tamaño
     * @return parte real de la Transformada Rapida de Fourier
     */
    public float[] calcularFFT_real(float [] datos, int n){
        FFT fft = new FFT(n,0);	
        FFT.NumeroComplejoArray cna = fft.fft(datos);
        return cna.getPartesReales();
    }
    
    /**
     * Metodo que muestra la grafica del pulso de forma lineal
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnPulsoEntradaAction(ActionEvent event){
        LinkedList<Componente> ele=verComponentesConectados();
        Fuente f;
        try{
            if(ele.get(1).getNombre().contains("fbg")){
                FBG fbg = (FBG) ele.get(1);
                if(fbg.getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                    System.out.println("TRANSMISION");
                    System.out.println("LO transmision: "+VentanaFBGController.getTransmisionS());
                    XYSeries series = new XYSeries("xy");
                    if(VentanaFBGController.getTransmisionS() != null && fbg.getTransmision() != 0){
                        float valores[]=new float[0];
                        int k=0;
                        for(int yu=0; yu<listaListas.size()-1; yu++){
                            if(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS()){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoReflexion = (float) fbg.getReflexion();
                                    float valores2[] = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());

                                    if(valores2 != null){
                                        //Introduccion de datos: señal filtrada
                                        for(int i = -(valores2.length/2); i <(valores2.length/2) ; i++){
                                            series.add(i+(valores2.length*k),(resultadoReflexion*valores2[i+(valores2.length/2)]));
                                        }
                                    }
                                }
                            }
                            if(!(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS())){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoTransmision = (float) fbg.getTransmision();

                                    valores = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());
                                    if(valores != null){
                                        //Introduccion de datos: todas las señales menos la filtrada
                                        for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                            series.add(i+(valores.length*k),(resultadoTransmision*valores[i+(valores.length/2)]));
                                        }
                                    }
                                }
                            }
                            k++;
                        }

                        XYSeriesCollection dataset = new XYSeriesCollection();
                        dataset.addSeries(series);

                        JFreeChart chart = ChartFactory.createXYLineChart(
                            "Transmission in Linear Form", // Título
                            "Frequency (w)", // Etiqueta Coordenada X
                            "U(0,w)", // Etiqueta Coordenada Y
                            dataset, // Datos
                            PlotOrientation.VERTICAL,
                            true, // Muestra la leyenda de los productos (Producto A)
                            false,
                            false
                        );

                        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                        chart.setBackgroundPaint(new Color(173, 216, 230));
                        //Mostramos la grafica en pantalla
                        ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                        //frame.getChartPanel().hasFocus();
                        frame.setAlwaysOnTop(true);
                        //frame.setUndecorated(true);
                        // Define el icono
                        frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                        frame.pack();
                        frame.setVisible(true);

                        cerrarVentana(event);
                    }
                }
                else{
                    System.out.println("REFLEXION");
                    System.out.println("LO reflexion: "+VentanaFBGController.getReflexionS());
                    XYSeries series = new XYSeries("xy");
                    if(VentanaFBGController.getReflexionS()!= 0 && fbg.getReflexion()!= 0){
                        float valores[]=new float[0];
                        int k=0;
                        for(int yu=0; yu<listaListas.size()-1; yu++){
                            if(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS()){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoTransmision = (float) fbg.getTransmision();
                                    float valores2[] = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());

                                    if(valores2 != null){
                                        //Introduccion de datos: señal filtrada
                                        for(int i = -(valores2.length/2); i <(valores2.length/2) ; i++){
                                            series.add(i+(valores2.length*k),(resultadoTransmision*valores2[i+(valores2.length/2)]));
                                        }
                                    }
                                }
                            }
                            if(!(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS())){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoReflexion = (float) fbg.getReflexion();

                                    valores = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());
                                    if(valores != null){
                                        //Introduccion de datos: todas las señales menos la filtrada
                                        for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                            series.add(i+(valores.length*k),(resultadoReflexion*valores[i+(valores.length/2)]));
                                        }
                                    }
                                }
                            }
                            k++;
                        }

                        XYSeriesCollection dataset = new XYSeriesCollection();
                        dataset.addSeries(series);

                        JFreeChart chart = ChartFactory.createXYLineChart(
                            "Reflection in Linear Form", // Título
                            "Frequency (w)", // Etiqueta Coordenada X
                            "U(0,w)", // Etiqueta Coordenada Y
                            dataset, // Datos
                            PlotOrientation.VERTICAL,
                            true, // Muestra la leyenda de los productos (Producto A)
                            false,
                            false
                        );

                        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                        chart.setBackgroundPaint(new Color(173, 216, 230));
                        //Mostramos la grafica en pantalla
                        ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                        //frame.getChartPanel().hasFocus();
                        frame.setAlwaysOnTop(true);
                        //frame.setUndecorated(true);
                        // Define el icono
                        frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                        frame.pack();
                        frame.setVisible(true);

                        cerrarVentana(event);
                    }
                }
            }
            else{
                if(listaListas.isEmpty()){
                    if(ele.getLast().getNombre().contains("source")){
                        f= (Fuente)ele.getLast();
                        float valores[] = pulsoEntrada(f.getA0(), f.getT0(),
                            f.getW0(), f.getC(), f.getM());

                        if(valores != null){
                            XYSeries series = new XYSeries("xy");

                             //Introduccion de datos
                            for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                series.add(i,valores[i+(valores.length/2)]);
                            }

                            XYSeriesCollection dataset = new XYSeriesCollection();
                            dataset.addSeries(series);

                            JFreeChart chart = ChartFactory.createXYLineChart(
                                    "Pulse in Linear Form", // Título
                                    "Frequency (w)", // Etiqueta Coordenada X
                                    "U(0,w)", // Etiqueta Coordenada Y
                                    dataset, // Datos
                                    PlotOrientation.VERTICAL,
                                    true, // Muestra la leyenda de los productos (Producto A)
                                    false,
                                    false
                            );

                            chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                            chart.setBackgroundPaint(new Color(173, 216, 230));
                              //Mostramos la grafica en pantalla
                            ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                            //frame.getChartPanel().hasFocus();
                            frame.setAlwaysOnTop(true);
                            //frame.setUndecorated(true);
                            // Define el icono
                            frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                            frame.pack();
                            frame.setVisible(true);
                        }
                        cerrarVentana(event);
                    }
                }
                if(listaListas.size()>0){
                    XYSeries series = new XYSeries("xy");
                    float valores[]=new float[0];
                    int k=0;
                    for(int yu=0; yu<listaListas.size()-1; yu++){
                        ele=listaListas.get(yu).getLista();
                        if(ele.getLast().getNombre().contains("source")){
                            f = (Fuente)ele.getLast();
                            valores = pulsoEntrada(f.getA0(), f.getT0(),
                                f.getW0(), f.getC(), f.getM());
                            if(valores != null){
                                //Introduccion de datos
                                for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                    series.add(i+(valores.length*k),valores[i+(valores.length/2)]);
                                }
                            }
                        }
                        k++;
                    }

                    XYSeriesCollection dataset = new XYSeriesCollection();
                    dataset.addSeries(series);

                    JFreeChart chart = ChartFactory.createXYLineChart(
                        "Pulse in Linear Form", // Título
                        "Frequency (w)", // Etiqueta Coordenada X
                        "U(0,w)", // Etiqueta Coordenada Y
                        dataset, // Datos
                        PlotOrientation.VERTICAL,
                        true, // Muestra la leyenda de los productos (Producto A)
                        false,
                        false
                    );

                    chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                    chart.setBackgroundPaint(new Color(173, 216, 230));
                    //Mostramos la grafica en pantalla
                    ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                    //frame.getChartPanel().hasFocus();
                    frame.setAlwaysOnTop(true);
                    //frame.setUndecorated(true);
                    // Define el icono
                    frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                    frame.pack();
                    frame.setVisible(true);

                    cerrarVentana(event);
                }
            }
            if(!ele.getLast().getNombre().contains("source")&&listaListas.isEmpty()){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "\nLink error",
                        aceptar);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        catch(Exception e){
            System.out.println(e);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
               Alert alert = new Alert(Alert.AlertType.ERROR,
                       "\nLink error",
                       aceptar);
               alert.setTitle("Error");
               alert.setHeaderText(null);
               alert.showAndWait();
        }
    }
    
     /**
     * Metodo que muestra la grafica del pulso de forma logaritmica
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnLogaritmo(ActionEvent event){
        LinkedList<Componente> ele=verComponentesConectados();
        Fuente f;
        try{
            if(ele.get(1).getNombre().contains("fbg")){
                FBG fbg = (FBG) ele.get(1);
                if(fbg.getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                    System.out.println("TRANSMISION");
                    System.out.println("LO transmision: "+VentanaFBGController.getTransmisionS());
                    XYSeries series = new XYSeries("xy");
                    if(VentanaFBGController.getTransmisionS() != null && fbg.getTransmision() != 0){
                        float valores[]=new float[0];
                        int k=0;
                        for(int yu=0; yu<listaListas.size()-1; yu++){
                            if(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS()){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoReflexion = (float) fbg.getReflexion();
                                    float valores2[] = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());

                                    if(valores2 != null){
                                        //Introduccion de datos
                                        for(int i = -(valores2.length/2); i <(valores2.length/2) ; i++){
                                            series.add(i+(valores2.length*k),(20*Math.log10(resultadoReflexion*valores2[i+(valores2.length/2)])));
                                        }
                                    }
                                }
                            }
                            if(!(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS())){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoTransmision = (float) fbg.getTransmision();

                                    valores = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());
                                    if(valores != null){
                                        //Introduccion de datos
                                        for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                            series.add(i+(valores.length*k),(20*Math.log10(resultadoTransmision*valores[i+(valores.length/2)])));
                                        }
                                    }
                                }
                            }
                            k++;
                        }

                        XYSeriesCollection dataset = new XYSeriesCollection();
                        dataset.addSeries(series);

                        JFreeChart chart = ChartFactory.createXYLineChart(
                            "Transmission in Logarithmic Form", // Título
                            "Frequency (w)", // Etiqueta Coordenada X
                            "U(0,w)", // Etiqueta Coordenada Y
                            dataset, // Datos
                            PlotOrientation.VERTICAL,
                            true, // Muestra la leyenda de los productos (Producto A)
                            false,
                            false
                        );

                        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                        chart.setBackgroundPaint(new Color(173, 216, 230));
                        //Mostramos la grafica en pantalla
                        ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                        //frame.getChartPanel().hasFocus();
                        frame.setAlwaysOnTop(true);
                        //frame.setUndecorated(true);
                        // Define el icono
                        frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                        frame.pack();
                        frame.setVisible(true);

                        cerrarVentana(event);
                    }
                }
                else{
                    System.out.println("REFLEXION");
                    System.out.println("LO reflexion: "+VentanaFBGController.getReflexionS());
                    XYSeries series = new XYSeries("xy");
                    if(VentanaFBGController.getReflexionS()!= 0 && fbg.getReflexion()!= 0){
                        float valores[]=new float[0];
                        int k=0;
                        for(int yu=0; yu<listaListas.size()-1; yu++){
                            if(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS()){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoTransmision = (float) fbg.getTransmision();
                                    float valores2[] = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());

                                    if(valores2 != null){
                                        //Introduccion de datos
                                        for(int i = -(valores2.length/2); i <(valores2.length/2) ; i++){
                                            series.add(i+(valores2.length*k),(20*Math.log10(resultadoTransmision*valores2[i+(valores2.length/2)])));
                                        }
                                    }
                                }
                            }
                            if(!(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS())){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    f = (Fuente)ele.getLast();
                                    float resultadoReflexion = (float) fbg.getReflexion();

                                    valores = pulsoEntrada(f.getA0(), f.getT0(),
                                        f.getW0(), f.getC(), f.getM());
                                    if(valores != null){
                                        //Introduccion de datos
                                        for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                            series.add(i+(valores.length*k),(20*Math.log10(resultadoReflexion*valores[i+(valores.length/2)])));
                                        }
                                    }
                                }
                            }
                            k++;
                        }

                        XYSeriesCollection dataset = new XYSeriesCollection();
                        dataset.addSeries(series);

                        JFreeChart chart = ChartFactory.createXYLineChart(
                            "Reflection in Logarithmic Form", // Título
                            "Frequency (w)", // Etiqueta Coordenada X
                            "U(0,w)", // Etiqueta Coordenada Y
                            dataset, // Datos
                            PlotOrientation.VERTICAL,
                            true, // Muestra la leyenda de los productos (Producto A)
                            false,
                            false
                        );

                        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                        chart.setBackgroundPaint(new Color(173, 216, 230));
                        //Mostramos la grafica en pantalla
                        ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                        //frame.getChartPanel().hasFocus();
                        frame.setAlwaysOnTop(true);
                        //frame.setUndecorated(true);
                        // Define el icono
                        frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                        frame.pack();
                        frame.setVisible(true);

                        cerrarVentana(event);
                    }
                }
            }
            else{
                if(listaListas.isEmpty()){
                    if(ele.getLast().getNombre().contains("source")){
                        f= (Fuente)ele.getLast();
                        float valores[] = pulsoEntrada(f.getA0(), f.getT0(),
                            f.getW0(), f.getC(), f.getM());

                        if(valores != null){
                            XYSeries series = new XYSeries("xy");

                             //Introduccion de datos
                            for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                //series.add(i,valores[i+(valores.length/2)]);
                                series.add(i,20*Math.log10(valores[i+(valores.length/2)]));
                            }

                            XYSeriesCollection dataset = new XYSeriesCollection();
                            dataset.addSeries(series);

                            JFreeChart chart = ChartFactory.createXYLineChart(
                                    "Pulse in Logarithmic Form", // Título
                                    "Frequency (w)", // Etiqueta Coordenada X
                                    "U(0,w)", // Etiqueta Coordenada Y
                                    dataset, // Datos
                                    PlotOrientation.VERTICAL,
                                    true, // Muestra la leyenda de los productos (Producto A)
                                    false,
                                    false
                            );

                            chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                            chart.setBackgroundPaint(new Color(173, 216, 230));
                              //Mostramos la grafica en pantalla
                            ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                            //frame.getChartPanel().hasFocus();
                            frame.setAlwaysOnTop(true);
                            //frame.setUndecorated(true);
                            // Define el icono
                            frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                            frame.pack();
                            frame.setVisible(true);
                        }
                        cerrarVentana(event);
                    }
                }
                if(listaListas.size()>0){
                    XYSeries series = new XYSeries("xy");
                    float valores[]=new float[0];
                    int k=0;
                    for(int yu=0; yu<listaListas.size()-1; yu++){
                        ele=listaListas.get(yu).getLista();
                        if(ele.getLast().getNombre().contains("source")){
                            f = (Fuente)ele.getLast();
                            valores = pulsoEntrada(f.getA0(), f.getT0(),
                                f.getW0(), f.getC(), f.getM());
                            if(valores != null){
                                //Introduccion de datos
                                for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                                    series.add(i+(valores.length*k),20*Math.log10(valores[i+(valores.length/2)]));
                                    //series.add(i,Math.log10(valores[i+(valores.length/2)]));
                                }
                            }
                        }
                        k++;
                    }

                    XYSeriesCollection dataset = new XYSeriesCollection();
                    dataset.addSeries(series);

                    JFreeChart chart = ChartFactory.createXYLineChart(
                        "Pulse in Logarithmic Form", // Título
                        "Frequency (w)", // Etiqueta Coordenada X
                        "U(0,w)", // Etiqueta Coordenada Y
                        dataset, // Datos
                        PlotOrientation.VERTICAL,
                        true, // Muestra la leyenda de los productos (Producto A)
                        false,
                        false
                    );

                    chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                    chart.setBackgroundPaint(new Color(173, 216, 230));
                    //Mostramos la grafica en pantalla
                    ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                    //frame.getChartPanel().hasFocus();
                    frame.setAlwaysOnTop(true);
                    //frame.setUndecorated(true);
                    // Define el icono
                    frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());

                    frame.pack();
                    frame.setVisible(true);

                    cerrarVentana(event);
                }
            }
            if(!ele.getLast().getNombre().contains("source")&&listaListas.isEmpty()){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "\nLink error",
                        aceptar);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        catch(Exception e){
            System.out.println(e);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
               Alert alert = new Alert(Alert.AlertType.ERROR,
                       "\nLink error",
                       aceptar);
               alert.setTitle("Error");
               alert.setHeaderText(null);
               alert.showAndWait();
        }
    }
    
    /**
     * Metodo que permite ver los componentes conectados antes del medidor de espectro
     * @return Componentes
     */
    public LinkedList verComponentesConectados(){
        LinkedList<Componente> lista=new LinkedList();
        añadirComponentesConectados(lista, elem.getComponente());
        //sacar componentes de la lista default
        for(int xi=0; xi<lista.size(); xi++){
                //System.out.println(lista.get(xi).toString());
        }
        //System.out.println("\n");
        //Sacar la lista
        for(int ci=0; ci<listaListas.size(); ci++){
            //Sacar componente
                Listas xd= listaListas.get(ci);
                System.out.println(xd.getN()+"\t"+xd.getLista().getLast().toString());
            System.out.println("\n");
        }
        return lista;
    }
    
    /**
     * Metodo que agrega a una lista los componentes conectados antes del medidor de espectro
     * @param lista Lista de componentes
     * @param comp Componentes
     * @return Lista de componentes conectados
     */
    public LinkedList añadirComponentesConectados(LinkedList lista, Componente comp){
        lista.add(comp);
        if(comp.isConectadoEntrada()){
            for(int i=0; i<controlador.getElementos().size(); i++){
                if(comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(i).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(i);
                    lista=añadirComponentesConectados(lista, aux);
                    if("mux".equals(aux.getNombre())){
                            break;
                    }
                }
            }
        }
        if(comp.getNombre().startsWith("mux")){
            Multiplexor muxi= (Multiplexor) comp;
            if(comp.isConectadoEntrada()){
                Listas listaxd= new Listas();
                for(int fe=0; fe<controlador.getDibujos().size();fe++){
                    if(muxi.getElementoConectadoEntrada().equals(controlador.getDibujos().get(fe).getDibujo().getText())){
                        Componente aux2= controlador.getElementos().get(fe);
                        LinkedList auxList= new LinkedList();
                        for(int ju=0; ju<lista.size()-1; ju++){
                            auxList.add(lista.get(ju));
                            Componente w= (Componente) lista.get(ju);
                            if("mux".equals(w.getNombre())){
                                 break;
                            }
                        }
                        añadirComponentesConectados(auxList, aux2);
                        switch(muxi.getEntradas()){
                            case 2:
                                listaxd.setN(1530);
                                break;
                                
                            case 4:
                                listaxd.setN(1510);
                                break;

                            case 8:
                                listaxd.setN(1470);
                                break;

                        }
                        listaxd.setLista(auxList);
                        Boolean flag=false;
                        for(int j=0; j<listaListas.size()-1; j++){
                            if(listaxd.getN()==listaListas.get(j).getN()){
                                flag=true;
                                break;
                            }
                        }
                        if(!flag){
                            listaListas.add(listaxd);
                        }
                        break;
                    }   
                }
            }
            for(int n=0; n<muxi.getEntradas()-1; n++){
                if(muxi.getConexionEntradas().get(n).isConectadoEntrada()){
                    Listas listaxd= new Listas();
                    for(int fe=0; fe<controlador.getDibujos().size();fe++){
                        if(muxi.getConexionEntradas().get(n).getElementoConectadoEntrada().equals(controlador.getDibujos().get(fe).getDibujo().getText())){
                            Componente aux2= controlador.getElementos().get(fe);
                            LinkedList auxList= new LinkedList();
                            for(int ju=0; ju<lista.size()-1; ju++){
                                auxList.add(lista.get(ju));
                                Componente w= (Componente) lista.get(ju);
                                if("mux".equals(w.getNombre())){
                                     break;
                                }
                            }
                            añadirComponentesConectados(auxList, aux2);
                            switch(muxi.getEntradas()){
                                case 2:
                                    if(n==0){
                                       listaxd.setN(1550); 
                                       break;
                                    }
                                    
                                case 4:
                                    if(n==0){
                                       listaxd.setN(1530); 
                                       break;
                                    }
                                    else if(n==1){
                                       listaxd.setN(1550);
                                       break;
                                    }
                                    else if(n==2){
                                       listaxd.setN(1570); 
                                       break;
                                    }
                                    
                                case 8:
                                    if(n==0){
                                       listaxd.setN(1490); 
                                       break;
                                    }
                                    else if(n==1){
                                       listaxd.setN(1510);
                                       break;
                                    }
                                    else if(n==2){
                                       listaxd.setN(1530);
                                       break;
                                    }
                                    else if(n==3){
                                       listaxd.setN(1550); 
                                    }
                                    else if(n==4){
                                       listaxd.setN(1570);
                                       break;
                                    }
                                    else if(n==5){
                                       listaxd.setN(1590);
                                       break;
                                    }
                                    else if(n==6){
                                       listaxd.setN(1610); 
                                       break;
                                    }  
                            }
                            listaxd.setLista(auxList);
                            Boolean flag=false;
                            for(int j=0; j<listaListas.size()-1; j++){
                                if(listaxd.getN()==listaListas.get(j).getN()){
                                    flag=true;
                                    break;
                                }
                            }
                            if(!flag){
                                listaListas.add(listaxd);
                            }
                            break;
                        }   
                    }
                }       
            } 
        }
        return lista;
    }

    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param scroll Espacio en el cual el usuario puede desplazarse
     * @param elem Elemento grafico
     */
    void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll, ElementoGrafico elem) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
        this.elem=elem;
    } 
    
    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento 
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param elem Elemento grafico
     * @param conectorController Controlador del conector
     */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,ElementoGrafico elem, VentanaEspectroController conectorController) {
        this.elem=elem;
        this.espectroControl=conectorController;
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        try{
            LinkedList<Componente> ele=verComponentesConectados();
            FBG fbg = new FBG();
            if(ele.get(1).getNombre().contains("fbg")){
                fbg = (FBG) ele.get(1);
                if(fbg.getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                    btnPulsoEntrada.setText("See the Transmission in Linear Form");
                    btnLogaritmo.setText("See Transmission in Logarithmic Form");
                }
                else{
                    btnPulsoEntrada.setText("See the Reflection in Linear Form");
                    btnLogaritmo.setText("See Reflection in Logarithmic Form");
                }
            }
            else{
                btnPulsoEntrada.setText("See the Pulse in Linear Form");
                btnLogaritmo.setText("See the Pulse in Logarithmic Form");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
