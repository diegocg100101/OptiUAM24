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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.ExcepcionDivideCero;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Listas;
import optiuam.bc.modelo.Multiplexor;
import optiuam.bc.modelo.NumeroComplejo;
import optiuam.bc.modelo.Señal;
import optiuam.bc.modelo.Splitter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Clase VentanaPulsoController la cual se encarga de proporcionar la
 * funcionalidad a la configuracion del pulso de la fuente
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 * @author Carlos Elizarraras
 * @see ControladorGeneral
 */
public class VentanaPulsoControllerAnterior extends ControladorGeneral implements Initializable {
    
    /**Fuente optica*/
    Fuente fuente;
    /**Tipo de pulso*/
    static String tipo;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Amplitud*/
    static float A0;
    /**Anchura*/
    static float T0;
    /**Frecuencia*/
    static float Fc;
    /**Chirp*/
    static float C;
    /**Pulso gausiano o supergausiano*/
    static float M;
    /**Frecuencia de muestreo*/
    double Fsamp = 2*(236*Math.pow(10, 6)); //2*FcMaxima*10^6
    
    /**Boton que almacena los datos del pulso de la fuente*/
    @FXML
    Button btnAplicar;
    /**Boton que muestra la grafica del pulso de la fuente*/
    @FXML
    Button btnGraficar;
    /**Caja de texto para ingresar la amplitud*/
    @FXML
    TextField txtA0;
    /**Caja de texto para ingresar el chirp*/
    @FXML
    TextField txtC;
    /**Caja de texto para ingresar la anchura*/
    @FXML
    TextField txtT0;
    /**Caja de texto para ingresar la frecuencia*/
    @FXML
    ComboBox cboxFc;
    /**Caja de texto para ingresar el valor que definira el tipo de pulso*/
    @FXML
    TextField txtM;
    
    /**
     * Metodo el cual inicializa la ventana del pulso
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxFc.getItems().removeAll(cboxFc.getItems());
        cboxFc.getItems().addAll("203.80", "201.07", "198.41", "195.81", 
                "193.29", "190.83", "188.43", "186.09");
        cboxFc.getSelectionModel().selectFirst();
       
        if(M > 1){
            tipo="Supergaussian";
        }
        else if(M == 1){
            tipo="Gaussian";
        }
    }  
    
    /**
     * Metodo para cerrar la ventana del pulso
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Metodo que modifica los atributos del pulso de la fuente
     * @param C Chirp del pulso
     * @param A0 Amplitud del pulso
     * @param Fc Frecuencia del pulso
     * @param T0 Anchura del pulso
     * @param M Define si el pulso es Gausiano o Supergausiano
     */
    public void setValores(float C,float A0,float Fc,float T0,float M){
        txtC.setText(String.valueOf(C));   //chirp
        txtA0.setText(String.valueOf(A0)); //amplitud
        cboxFc.getSelectionModel().select(Fc);
        txtT0.setText(String.valueOf(T0)); //anchura
        txtM.setText(String.valueOf(M));   //pulso gausiano o supergausiano
        if(M > 1){
            tipo="Supergaussian";
        }
        else if(M == 1){
            tipo="Gaussian";
        }
    }
    
    /**
     * Metodo encargado de validar los valores ingresados para la configuracion
     * del pulso
     */
    private boolean validarValores(){
        if (txtA0.getText().isEmpty() || txtA0.getText().compareTo("")==0 
                //|| !txtA0.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
                || !txtA0.getText().matches("^[1-9]\\d*(\\.\\d+)?|0\\.[1-9]\\d*$")){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid amplitude value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtA0.setText("0.0");
            return false;
        }
        if (txtT0.getText().isEmpty() || txtT0.getText().compareTo("")==0 
                || !txtT0.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid width value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtT0.setText("0.0");
            return false;
        }
        if (txtC.getText().isEmpty() || txtC.getText().compareTo("")==0 
                || !txtC.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid chirp value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtC.setText("0.0");
            return false;
        }
        if (cboxFc.getSelectionModel().isEmpty() 
                || cboxFc.getSelectionModel().getSelectedItem().toString().contains("0.0")){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid frequency value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            cboxFc.getSelectionModel().selectFirst();
            return false;
        }
        if (txtM.getText().isEmpty() || txtM.getText().compareTo("")==0 
                || !txtM.getText().matches("[0-9]*?\\d*(\\.\\d+)?") 
                 || Float.parseFloat(txtM.getText() ) <1){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid M value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtM.setText("0.0");
            return false;
        }
        return true;
    }
    
    /**
     * Metodo que almacena los valores ingresados para la configuracion
     * del pulso
     */
    @FXML
    public void btnAplicarAction() {
        if(validarValores()){
            A0 = Float.parseFloat(txtA0.getText());
            T0 = Float.parseFloat(txtT0.getText());
            Fc = Float.parseFloat(cboxFc.getSelectionModel().getSelectedItem().toString());
            C = Float.parseFloat(txtC.getText());
            M = Float.parseFloat(txtM.getText());
            
            if(M > 1){
                tipo="Supergaussian";
            }
            else if(M == 1){
                tipo="Gaussian";
            }
            fuente.setPulso(A0, T0, Fc, C, (int) M);
            System.out.println("C:"+C+" A0:"+A0+" Fc:"+Fc+ " T0:"+T0+ " M:"+M);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified pulse!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    /**
     * Metod que calcula el pulso de la fuente
     * @return pulso calculado
     * @throws optiuam.bc.modelo.ExcepcionDivideCero Lanza una excepcion si se
     * divide entre cero
     */
    public LinkedList<Listas> calcularPulso() throws ExcepcionDivideCero{
        float a0 = Float.parseFloat(txtA0.getText());
        float t0 = Float.parseFloat(txtT0.getText());
        float fc = Float.parseFloat(cboxFc.getSelectionModel().getSelectedItem().toString());
        float m = Float.parseFloat(txtM.getText());
        float t0Aux = (float) (t0*Math.pow(10, -6));
        float t_finalsim=(float) (1*Math.pow(10, -6));
        float fcAux = (float) (fc*Math.pow(10, 6)); //MHz
        int NFFT = 4096; //tamaño de muestra
        double Wc = 2*(Math.PI)*(fcAux); //2*pi*Fc*10^6
        
        NumeroComplejo jC = new NumeroComplejo(-1, C); //-1+jC
        NumeroComplejo n1 = NumeroComplejo.cociente(jC, 2); //-1+jC / 2
        
        // t=0:1/Fsamp:10*T0;
        LinkedList t = new LinkedList();
        for(double i = 0; i < 10*t_finalsim; i+=(1/Fsamp)){
            t.add(i);
        }
        LinkedList<Listas> señales= new LinkedList();

        int j=0;
        while(j < t.size()){
            double z = (double) t.get(j); // t
            double aux = z / t0Aux; // t/T0
            double aux2m = Math.pow(aux, 2*m);  // (t/T0)^2M
            NumeroComplejo n1tT0 = NumeroComplejo.producto(n1, (float) aux2m);
            NumeroComplejo n2 = NumeroComplejo.exponencial(n1tT0); //e^(-1+jC / 2)*[(t/T0)^2M]
            NumeroComplejo A = NumeroComplejo.producto(n2, a0); // A0*{e^(-1+jC / 2)*[(t/T0)^2M]}
            
            NumeroComplejo jWc = new NumeroComplejo(0, (float) Wc); //jWc
            NumeroComplejo jWct = NumeroComplejo.producto(jWc, (float) z); // jWc*t
            NumeroComplejo ejWct = NumeroComplejo.exponencial(jWct); // e^(jWc*t)
            NumeroComplejo U = NumeroComplejo.productoComplejos(A, ejWct); // A*[e^(jWc*t)]
            
            Listas listaAUX =new Listas();
            listaAUX.setMagnitud(U.magnitud());
            listaAUX.setComplejo(U);
            listaAUX.setFase(U.getFase());
            listaAUX.setxNumComplex(z);
            señales.add(listaAUX);
            
            j++;
        }
        //FFT DEL NUMERO COMPLEJO
        ventana_principal.calculosFFT(NFFT, señales, Fsamp);
        
        return señales;
    }
    
    /**
     * Metodo que muestra la grafica del pulso de la fuente
     */
    @FXML
    private void btnGraficarAction(ActionEvent event) throws ExcepcionDivideCero{
        btnAplicarAction();
        
        if(fuente.getM() > 1){
            tipo="Supergaussian";
        }
        else if(fuente.getM() == 1){
            tipo="Gaussian";
        }
        LinkedList<Listas> valores = new LinkedList<>();
        valores=calcularPulso();
        Señal valoresMagnitud= new Señal();
        valoresMagnitud.setValoresMagnitud(valores);
        fuente.setSeñalSalida(valoresMagnitud);
        ventana_principal.elemConected(fuente,true);
        
        int n = valores.size();
        
        /*-----------------------------MAGNITUD------------------------------*/
        XYSeries series = new XYSeries("xy");
        
         //Introduccion de datos
        for(int i = (n/2); i >-(n/2) ; i--){
            series.add((float) (-1)*fuente.getSeñalSalida().getValoresMagnitud().get((n/2)-i).getxNumComplex()+fuente.getSeñalSalida().getValoresMagnitud().get(n-1).getxNumComplex(), 
                    (float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)-i).getMagnitud());
            //System.out.println("("+(float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)-i).getxNumComplex()+","+(float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)-i).getMagnitud()+")");
        }
        for(int i = -(n/2); i <(n/2) ; i++){
            series.add((float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)+i).getxNumComplex()+fuente.getSeñalSalida().getValoresMagnitud().get(n-1).getxNumComplex(), 
                    (float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)+i).getMagnitud());
            //System.out.println("("+(float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)+i).getxNumComplex()+","+(float) fuente.getSeñalSalida().getValoresMagnitud().get((n/2)+i).getMagnitud()+")");
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                tipo + " Pulse - Magnitude", // Título
                "Time", // Etiqueta Coordenada X
                "Magnitude", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos (Producto A)
                false,
                false
        );
        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
        chart.setBackgroundPaint(new Color(173, 216, 230));
        ChartFrame frame = new ChartFrame("OptiUAM BC - "+
                fuente.getNombre().toUpperCase()+"_"+fuente.getIdFuente()+
                " - "+tipo + " Pulse", chart);
        frame.setAlwaysOnTop(true);
        frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());
        frame.pack();
        frame.setVisible(true);
        
        
        /*-------------------------------FFT--------------------------------*/
        XYSeries series5 = new XYSeries("xy");
        
         //Introduccion de datos
        int fi=valores.getLast().getFftseñal().getFft().length;
        System.out.println("valor loco es:"+fi);
        for(int i = 0; i <fi; i++){
            NumeroComplejo nAux= fuente.getSeñalSalida().getValoresMagnitud().getLast().getFftseñal().getFft()[i];
            series5.add((float) fuente.getSeñalSalida().getValoresMagnitud().get(i).getxFftseñal(), 
                    Math.pow(nAux.magnitud(), 2)/Math.pow(fuente.getSeñalSalida().getValoresMagnitud().size(), 2));
        }
        
        XYSeriesCollection dataset5 = new XYSeriesCollection();
        dataset5.addSeries(series5);

        JFreeChart chart5 = ChartFactory.createXYLineChart(
                tipo + " Pulse - FFT", // Título
                "Frequency", // Etiqueta Coordenada X
                "FFT", // Etiqueta Coordenada Y
                dataset5, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos (Producto A)
                false,
                false
        );
        chart5.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
        chart5.setBackgroundPaint(new Color(173, 216, 230));
        ChartFrame frame5 = new ChartFrame("OptiUAM BC - "+
                fuente.getNombre().toUpperCase()+"_"+fuente.getIdFuente()+
                " - "+tipo + " Pulse", chart5);
        frame5.setAlwaysOnTop(true);
        frame5.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());
        frame5.pack();
        frame5.setVisible(true);
        
        cerrarVentana(event);
        VentanaPrincipal.btnStart = false;
    }

    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param elemG Elemento grafico
     * @param ventana Ventana principal
     */
    public void init(ElementoGrafico elemG,VentanaPrincipal ventana) {
       this.fuente = (Fuente)elemG.getComponente();
       this.ventana_principal= ventana;
       if(fuente != null){
            txtA0.setText(String.valueOf(fuente.getA0()));
            txtC.setText(String.valueOf(fuente.getC()));
            txtM.setText(String.valueOf(fuente.getM()));
            txtT0.setText(String.valueOf(fuente.getT0()));
            cboxFc.getSelectionModel().select(fuente.getFc());
        }
    }
    
    /**
     * Metodo encargado de generar la señal inicial en la fuente. Si esta 
     * conectado, se realiza el proceso para modificar la señal dependiendo del
     * elemento
     * @param actual Componente actual
     */
    public void elemConected(Componente actual){
        if("source".equals(actual.getNombre())){
            Fuente fue= (Fuente) actual;
            LinkedList<Listas> salidas= new LinkedList<>();
            salidas=fue.getSeñalSalida().getValoresMagnitud();
            Señal señal= new Señal();
            señal.setValoresMagnitud(salidas);
            if(fue.isConectadoSalida()){
                for(int c=0; c<controlador.getElementos().size();c++){
                    if(fue.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())){
                        Componente auxcomp= controlador.getElementos().get(c);
                        auxcomp.setSeñalEntrada(señal);
                        elemConected(auxcomp);
                    }
                }
            }
        }
        if("connector".equals(actual.getNombre())){
            Conector conector= (Conector) actual;
            LinkedList<Listas> salidas= new LinkedList<>();
            salidas=conector.getSeñalEntrada().getValoresMagnitud();
            salidas=conector.valorMagnitudPerdida(salidas);
            Señal señal= new Señal();
            señal.setValoresMagnitud(salidas);
            conector.setSeñalSalida(señal);
            if(conector.isConectadoSalida()){
                Componente auxcomp= new Componente();
                for(int c=0; c<controlador.getElementos().size();c++){
                    if(conector.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())){
                        auxcomp= controlador.getElementos().get(c);
                        if("mux".equals(auxcomp.getNombre())){
                            Multiplexor muxAux= (Multiplexor) auxcomp;
                            if(muxAux.getElementoConectadoEntrada().equals(conector.getNombreid())){
                                muxAux.setSeñalEntrada(señal);
                                break;
                            }
                            else{
                                for(int h=0; h>muxAux.getConexionEntradas().size(); h++){
                                    if (muxAux.getConexionEntradas().get(h).getElementoConectadoEntrada().equals(conector.getNombreid())) {
                                        muxAux.getConexionEntradas().get(h).setSeñalEntrada(señal);
                                        break;
                                    }
                                }
                            }
                        }
                        else{
                            auxcomp.setSeñalEntrada(señal);
                        }
                        elemConected(auxcomp);
                    }
                }
            }
        }
        if("splice".equals(actual.getNombre())){
            Empalme empalme= (Empalme) actual;
            LinkedList<Listas> salidas= new LinkedList<>();
            salidas=empalme.getSeñalEntrada().getValoresMagnitud();
            salidas=empalme.valorMagnitudPerdida(salidas);
            Señal señal= new Señal();
            señal.setValoresMagnitud(salidas);
            empalme.setSeñalSalida(señal);
            if(empalme.isConectadoSalida()){
                for(int c=0; c<controlador.getElementos().size();c++){
                    if(empalme.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())){
                        Componente auxcomp= controlador.getElementos().get(c);
                        auxcomp.setSeñalEntrada(señal);
                        elemConected(auxcomp);
                    }
                }
            }
        }
        if("fiber".equals(actual.getNombre())){
            Fibra fibra= (Fibra) actual;
            LinkedList<Listas> salidas= new LinkedList<>();
            salidas=fibra.getSeñalEntrada().getValoresMagnitud();
            // ATENUACION Y DISPERSION //271122
            //salidas=conector.valorMagnitudPerdida(salidas);
            Señal señal= new Señal();
            señal.setValoresMagnitud(salidas);
            fibra.setSeñalSalida(señal);
            if(fibra.isConectadoSalida()){
                for(int c=0; c<controlador.getElementos().size();c++){
                    if(fibra.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())){
                        Componente auxcomp= controlador.getElementos().get(c);
                        auxcomp.setSeñalEntrada(señal);
                        elemConected(auxcomp);
                    }
                }
            }
        }
        if("mux".equals(actual.getNombre())){
            boolean iniciado=false;
            Multiplexor mux= (Multiplexor) actual;
            LinkedList<Listas> salidas= new LinkedList<>();
            if(mux.isConectadoEntrada()){
                if(!iniciado){
                    salidas=mux.getSeñalSalida().getValoresMagnitud();
                    iniciado=true;
                }
            }
            for(int g=0;g<mux.getConexionEntradas().size();g++){
                if(mux.isConectadoEntrada()){
                    if(!iniciado){
                        salidas=mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud();
                        iniciado=true;
                    }
                    else{
                        salidas = sumarSeñales(salidas, mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud());
                    }
                }
            }
            Señal señal= new Señal();
            señal.setValoresMagnitud(salidas);
            mux.setSeñalSalida(señal);
            if(mux.isConectadoSalida()){
                for(int c=0; c<controlador.getElementos().size();c++){
                    if(mux.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())){
                        Componente auxcomp= controlador.getElementos().get(c);
                        auxcomp.setSeñalEntrada(señal);
                        elemConected(auxcomp);
                    }
                }
            }
        }
        if("splitter".equals(actual.getNombre())){
            Splitter splitter= (Splitter) actual;
            if(splitter.isConectadoSalida()){
                for(int c=0; c<controlador.getElementos().size();c++){
                    if(splitter.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())){
                        Componente auxcomp= controlador.getElementos().get(c);
                        LinkedList<Listas> salidas= new LinkedList<>();
                        salidas=splitter.getSeñalSalida().getValoresMagnitud();
                        //REVISAR FUNCIONAMIENTO DEL SPLITTER
                        salidas=splitter.valorMagnitudPerdida(salidas);
                        Señal señal= new Señal();
                        señal.setValoresMagnitud(salidas);
                        auxcomp.setSeñalEntrada(señal);
                        elemConected(auxcomp);
                    }
                }
            }
        }
    }
    
    /**
     * Metodo encargado de sumar las señales que esten presentes en el 
     * multiplexor cuidando que no se repita la señal que ya se sumo
     * @param lista1 Señal 1
     * @param lista2 Señal 2
     * @return Suma de señales en el multiplexor
     */
    public LinkedList<Listas> sumarSeñales( LinkedList<Listas> lista1,  LinkedList<Listas> lista2){
        LinkedList<Listas> listaAux= new LinkedList<>(); 
        LinkedList<Listas>  sumador; new LinkedList<>();
        if(lista1.size()>=lista2.size()){
            listaAux=lista1;
            sumador=lista2;
        }
        else{
            listaAux=lista2;
            sumador=lista1;
        }
        for(int x=0;x<sumador.size();x++){
            NumeroComplejo complex= new NumeroComplejo(listaAux.get(x).getComplejo().getRealPart()
                    +sumador.get(x).getComplejo().getRealPart(), listaAux.get(x).getComplejo().getImaginaryPart()
                            +sumador.get(x).getComplejo().getImaginaryPart());
            listaAux.get(x).setComplejo(complex);
        } 
        return listaAux;
    }
    
}