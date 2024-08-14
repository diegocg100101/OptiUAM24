
package optiuam.bc.controller;

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

import optiuam.bc.model.Componente;
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.FBG;
import optiuam.bc.model.Listas;
import optiuam.bc.model.Multiplexor;
import optiuam.bc.model.NumeroComplejo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Clase VentanaEspectroController la cual se encarga de proporcionar la
 * funcionalidad al analizador de espectro
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 * @see ControladorGeneral
 */
public class VentanaEspectroController extends ControladorGeneral implements Initializable {

    /**
     * Controlador del simulador
     */
    ControladorGeneral controlador;
    /**
     * Controlador del espectro
     */
    VentanaEspectroController espectroControl;
    /**
     * Elemento grafico del medidor de espectro
     */
    ElementoGrafico elem;
    /**
     * Escenario en el cual se agregaran los objetos creados
     */
    Stage stage;
    /**
     * Lista de enlaces creados
     */
    LinkedList<LinkedList> listaListas = new LinkedList();
    /**
     * Frecuencia de muestreo
     */
    double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6

    /**
     * Boton que permite visualizar el pulso en frecuencia
     */
    @FXML
    Button btnFFT;
    /**
     * Boton que permite visualizar el pulso en el tiempo
     */
    @FXML
    Button btnMagnitud;
    /**
     * Panel para agregar objetos
     */
    @FXML
    private Pane Pane1;
    /**
     * Espacio en el cual el usuario puede desplazarse
     */
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo el cual inicializa la ventana del medidor de espectro
     *
     * @param url La ubicacion utilizada para resolver rutas relativas para
     *            el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb  Los recursos utilizados para localizar el objeto raiz, o nulo
     *            si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Metodo para cerrar la ventana del medidor de espectro
     *
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }

    /**
     * Metodo que muestra la grafica del pulso en frecuencia
     *
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnPulsoFFT(ActionEvent event) {
        if (VentanaPrincipal.btnStart) {
            try {
                System.out.println("ESPECTRO FFT NORMAL");
                LinkedList<Listas> valores = elem.getComponente().getSeñalEntrada().getValoresMagnitud();
                int NFFT = 4096; // Tamaño de muestra
                ventana_principal.calculosFFT(NFFT, valores, Fsamp);

                XYSeries series = new XYSeries("xy");

                //Introduccion de datos
                int fftL = valores.getLast().getFftseñal().getFft().length;
                for (int i = 0; i < fftL; i++) {
                    NumeroComplejo nAux = valores.getLast().getFftseñal().getFft()[i];
                    series.add((float) valores.get(i).getxFftseñal(), (Math.pow(nAux.magnitud(), 2)) / (Math.pow(valores.size(), 2)));
                }

                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(series);

                JFreeChart chart = ChartFactory.createXYLineChart(
                        "Pulse - FFT", // Título
                        "Frequency", // Etiqueta Coordenada X
                        "FFT", // Etiqueta Coordenada Y
                        dataset, // Datos
                        PlotOrientation.VERTICAL,
                        true, // Muestra la leyenda de los productos (Producto A)
                        false,
                        false
                );
                chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                chart.setBackgroundPaint(new Color(173, 216, 230));
                ChartFrame frame = new ChartFrame("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase(), chart);
                frame.setAlwaysOnTop(true);
                frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());
                frame.pack();
                frame.setVisible(true);
            } catch (Exception e) {
                System.out.println(e);
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "\nLink error",
                        aceptar);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } else {
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nPress the Update Signals button",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        cerrarVentana(event);
    }

    /**
     * Metodo que muestra la grafica del pulso en el tiempo
     *
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnPulsoMagnitud(ActionEvent event) {
        try {
            System.out.println("ESPECTRO MAGNITUD NORMAL");
            LinkedList<Listas> valores = elem.getComponente().getSeñalEntrada().getValoresMagnitud();
            int n = valores.size();
            int NFFT = 4096; //tamaño de muestra
            ventana_principal.calculosFFT(NFFT, valores, Fsamp);

            XYSeries series = new XYSeries("xy");
            //Introduccion de datos
            for (int i = -(n / 2); i < (n / 2); i++) {
                series.add((float) valores.get((n / 2) + i).getxNumComplex(),
                        (float) valores.get((n / 2) + i).getComplejo().magnitud());
            }
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Pulse - Magnitude", // Título
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
            ChartFrame frame = new ChartFrame("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase(), chart);
            frame.setAlwaysOnTop(true);
            frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nLink error",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        cerrarVentana(event);
    }


    public LinkedList verComponentesConectados() {
        LinkedList<Componente> lista = new LinkedList();
        //System.out.println(elem.getComponente().getNombre());
        añadirComponentesConectados(lista, elem.getComponente());
        //sacar componentes de la lista default
        for (int xi = 0; xi < lista.size(); xi++) {
            // System.out.println(lista.get(xi).toString());
        }
        System.out.println("\n");
        //Sacar la lista
        for (int ci = 0; ci < listaListas.size(); ci++) {
            //Sacar componente
            for (int zi = 0; zi < listaListas.get(ci).size(); zi++) {
                //System.out.println(listaListas.get(ci).get(zi).toString());
            }
            System.out.println("\n");
        }
        return lista;
    }

    public void añadirComponentesConectados(LinkedList lista, Componente comp) {
        lista.add(comp);
        if (comp.isConectadoEntrada()) {
            for (int i = 0; i < controlador.getElementos().size(); i++) {
                if (comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(i).getDibujo().getText())) {
                    Componente aux = controlador.getElementos().get(i);
                    añadirComponentesConectados(lista, aux);
                    //System.out.println(controlador.getDibujos().get(i).getDibujo().getText());
                    break;
                }

            }
        }
        if (comp.getNombre().startsWith("mux")) {
            Multiplexor muxi = (Multiplexor) comp;
            for (int n = 0; n < muxi.getEntradas() - 1; n++) {
                if (muxi.getConexionEntradas().get(n).isConectadoEntrada()) {
                    LinkedList auxList = new LinkedList();
                    for (int ju = 0; ju < lista.size() - 1; ju++) {
                        auxList.add(lista.get(ju));
                        Componente w = (Componente) lista.get(ju);
                        if ("mux".equals(w.getNombre())) {
                            break;
                        }
                    }
                    for (int fe = 0; fe < controlador.getDibujos().size(); fe++) {
                        if (muxi.getConexionEntradas().get(n).getElementoConectadoEntrada().equals(controlador.getDibujos().get(fe).getDibujo().getText())) {
                            Componente aux2 = controlador.getElementos().get(fe);
                            //System.out.println("\t"+aux2.getNombre());
                            añadirComponentesConectados(auxList, aux2);
                            listaListas.add(auxList);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a
     * que elemento se refiere
     *
     * @param controlador Controlador del simulador
     * @param stage       Escenario en el cual se agregan los objetos creados
     * @param Pane1       Panel para agregar objetos
     * @param scroll      Espacio en el cual el usuario puede desplazarse
     * @param elem        Elemento grafico
     * @param ventana     Ventana Principal
     */
    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1,
                     ScrollPane scroll, ElementoGrafico elem, VentanaPrincipal ventana) {
        this.controlador = controlador;
        this.stage = stage;
        this.Pane1 = Pane1;
        this.scroll = scroll;
        this.elem = elem;
        this.ventana_principal = ventana;
    }

    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento
     *
     * @param controlador        Controlador del simulador
     * @param stage              Escenario en el cual se agregan los objetos creados
     * @param Pane1              Panel para agregar objetos
     * @param elem               Elemento grafico
     * @param espectroController Controlador del medidor de espectros
     */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,
                      ElementoGrafico elem, VentanaEspectroController espectroController) {
        this.elem = elem;
        this.espectroControl = espectroController;
        this.controlador = controlador;
        this.stage = stage;
        this.Pane1 = Pane1;
        try {
            LinkedList<Componente> ele = verComponentesConectados();
            FBG fbg = new FBG();
            if (ele.get(1).getNombre().contains("fbg")) {
                fbg = (FBG) ele.get(1);
                if (fbg.getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                    btnFFT.setText("See Transmission in Frequency");
                    btnMagnitud.setText("See Transmission in Time");
                } else {
                    btnFFT.setText("See Reflection in Frequency");
                    btnMagnitud.setText("See Reflection in Time");
                }
            } else {
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
