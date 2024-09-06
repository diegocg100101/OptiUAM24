package optiuam.bc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import optiuam.bc.model.Demultiplexor;
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.Osciloscopio;
import optiuam.bc.model.Splitter;

import static optiuam.bc.model.Componente.tiempo;

/**
 * Clase que se encarga de instanciar un osciloscopio
 *
 * @author Ilse López
 * @author Diego Cantoral
 * @see ControladorGeneral
 */
public class VentanaOsciloscopioController extends ControladorGeneral implements Initializable {

    /**
     * Identificador del osciloscopio
     */
    static int idOsciloscopio = 0;

    /**
     * Posición del conector en el eje X
     */
    static double posX;

    /**
     * Posición del conector en el eje Y
     */
    static double posY;

    /**
     * Botón para confirmar unidades
     */
    public Button btnUnidades;

    /**
     * Caja con las unidades
     */
    public ComboBox cboxUnidades;

    /**
     * Slider para modificar el centro de X
     */
    public Slider centroX;

    /**
     * Slider para modificar el centro de Y
     */
    public Slider centroY;

    /**
     * Lista desplegable de elementos para conectar
     */
    @FXML
    ComboBox cboxConectarA;

    /**
     * Botón para crear una instancia
     */
    @FXML
    Button btnCrear;

    @FXML
    Pane Pane;

    /**
     * Instancia de LineChart para graficar
     */
    @FXML
    private LineChart<Number, Number> grafica;

    /**
     * Valores para eje "y" (amplitud)
     */
    @FXML
    private NumberAxis y = new NumberAxis();

    /**
     * Valores para eje "x" (tiempo)
     */
    @FXML
    private NumberAxis x = new NumberAxis();

    /**
     * Etiqueta de "Connect to:"
     */
    public Label connect;

    /**
     * Botón para modificar
     */
    public Button btnModify;

    /**
     * Botón para graficar después de abrir la ventana nuevamente
     */
    public Button btnGraficar;

    /**
     * Bara para el zoom del eje "y" (amplitud)
     */
    public Slider zoomY;

    /**
     * Bara para el zoom del eje "x" (tiempo)
     */
    public Slider zoomX;

    /**
     * Controlador del simulador
     */
    ControladorGeneral controlador;

    /**
     * Escenario en el cual se agregan los objetos creados
     */
    Stage stage;

    /**
     * Elemento gráfico del osciloscopio
     */
    ElementoGrafico elemento;

    /**
     * Controlador del osciloscopio
     */
    VentanaOsciloscopioController osciloscopioControl;


    /**
     * Inicializa el controlador
     * @param controlador Controlador general
     * @param stage
     * @param ventana
     * @param pane
     * @param osciloscopioController
     */
    public void init(ControladorGeneral controlador, Stage stage, VentanaPrincipal ventana, Pane pane,
                     VentanaOsciloscopioController osciloscopioController) {
        this.controlador = controlador;
        this.stage = stage;
        this.ventana_principal = ventana;
        this.Pane = pane;
        this.osciloscopioControl = osciloscopioController;

        // Caja de conexión
        osciloscopioControl.cboxConectarA.getItems().add("Desconnected");
        osciloscopioController.cboxConectarA.getSelectionModel().select(0);

        // Caja de unidades a graficar
        osciloscopioController.cboxUnidades.getItems().add("mW");
        osciloscopioController.cboxUnidades.getItems().add("dBm");
        osciloscopioController.cboxUnidades.getSelectionModel().select(0);

        for (int elemento1 = 0; elemento1 < controlador.getElementos().size(); elemento1++) {
            if ("connector".equals(controlador.getElementos().get(elemento1).getNombre()) ||
                    "source".equals(controlador.getElementos().get(elemento1).getNombre())
                    ||
                    "mux".equals(controlador.getElementos().get(elemento1).getNombre())) {
                if (!controlador.getElementos().get(elemento1).isConectadoEntrada()) {
                    osciloscopioControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento1).getDibujo().getText());
                }
            }
        }
    }

    /**
     * Inicializar el controlador con solo el elemento gráfico
     * @param elemento Elemento gráfico
     */
    public void init2(ElementoGrafico elemento) {
        this.elemento = elemento;
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboxConectarA.setVisible(true);
        btnCrear.setVisible(true);
        btnModify.setVisible(false);
        btnGraficar.setVisible(false);
        zoomX.setMin(1);
        zoomX.setMax(100);
        zoomX.setValue(1);
        zoomY.setMin(0.1);
        zoomY.setMax(2);
        zoomY.setValue(1);
    }

    /**
     * @param osciloscopio Objeto de tipo osciloscopio
     */
    public void guardarOsciloscopio(Osciloscopio osciloscopio) {
        osciloscopio.setId(controlador.getContadorElemento());
        controlador.getElementos().add(osciloscopio);

        Label dibujo = new Label();
        ElementoGrafico elemento = new ElementoGrafico();

        osciloscopio.setPosX(dibujo.getLayoutX());
        osciloscopio.setPosY(dibujo.getLayoutY());
        setPosX(osciloscopio.getPosX());
        setPosY(osciloscopio.getPosY());

        elemento.setComponente(osciloscopio);
        elemento.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_osciloscopio2.png")));
        dibujo.setText(osciloscopio.getNombre() + "_" + osciloscopio.getIdOsciloscopio());

        dibujo.setContentDisplay(ContentDisplay.TOP);
        elemento.setDibujo(dibujo);
        controlador.getDibujos().add(elemento);

        init2(elemento);
        Pane.getChildren().add(elemento.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
        osciloscopio.setNombreid(osciloscopio.getNombre() + "_" + osciloscopio.getId());
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nOscilloscope created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Inicializa una instancia del osciloscopio
     * @param event Eventos del sistema
     * @throws RuntimeException
     * @throws InvocationTargetException
     */
    public void crearOsciloscopio(ActionEvent event) throws RuntimeException, InvocationTargetException {
        Osciloscopio osciloscopio = new Osciloscopio();
        osciloscopio.setConectadoEntrada(false);
        osciloscopio.setIdOsciloscopio(idOsciloscopio);
        osciloscopio.setNombre("oscilloscope");
        guardarOsciloscopio(osciloscopio);

        // Conexión inicial
        for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
            if (osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                ElementoGrafico eg = controlador.getDibujos().get(elemento2);
                osciloscopio.setElementoConectadoEntrada(eg.getDibujo().getText());
                osciloscopio.setConectadoEntrada(true);
                eg.getComponente().setElementoConectadoSalida(elemento.getDibujo().getText());
                eg.getComponente().setConectadoSalida(true);

                // Pasa el buffer al elemento conectado
                osciloscopio.setDatos(eg.getComponente().getDatos());
                anadirGraficaWatts();
                dibujarLineaAtras(elemento);
                break;
            }
        }
        eventos(elemento);

        idOsciloscopio++;

        // Solo cierra la ventana si hay algo conectado al componente
        if(!elemento.getComponente().isConectadoEntrada()){
            cerrarVentana(event);
        }
    }

    /**
     * Método para modifica la conexión del osciloscopio
     * @param event
     * @throws RuntimeException
     * @throws InvocationTargetException
     */
    public void modicarOsciloscopio(ActionEvent event) throws RuntimeException, InvocationTargetException{
        Osciloscopio osciloscopio = (Osciloscopio) elemento.getComponente();
        for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
            if (osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                ElementoGrafico eg = controlador.getDibujos().get(elemento2);
                osciloscopio.setElementoConectadoEntrada(eg.getDibujo().getText());
                osciloscopio.setConectadoEntrada(true);
                eg.getComponente().setElementoConectadoSalida(elemento.getDibujo().getText());
                eg.getComponente().setConectadoSalida(true);

                // Pasa el buffer al elemento conectado
                osciloscopio.setDatos(eg.getComponente().getDatos());

                // Realiza la gráfica
                anadirGraficaWatts();
                dibujarLineaAtras(elemento);
                break;
            }
        }
    }

    /**
     * Método para gestionar eventos del osciloscopio
     * @param elemento
     */
    public void eventos(ElementoGrafico elemento) {
        elemento.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double newX = event.getSceneX();
                double newY = event.getSceneY();
                int index = 0;
                for (int a = 0; a < Pane.getChildren().size(); a++) {
                    if (Pane.getChildren().get(a).toString().contains(elemento.getDibujo().getText())) {
                        index = a;
                        break;
                    }
                }
                if (!outSideParentBoundsX(elemento.getDibujo().getLayoutBounds(), newX, newY)) {
                    elemento.getDibujo().setLayoutX(Pane.getChildren().get(index).getLayoutX() + event.getX() + 1);
                }

                if (!outSideParentBoundsY(elemento.getDibujo().getLayoutBounds(), newX, newY)) {
                    elemento.getDibujo().setLayoutY(Pane.getChildren().get(index).getLayoutY() + event.getY() + 1);
                }
                if (elemento.getComponente().isConectadoEntrada()) {
                    ElementoGrafico aux;
                    for (int it = 0; it < controlador.getDibujos().size(); it++) {
                        aux = controlador.getDibujos().get(it);
                        if (elemento.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                            aux.getComponente().getLinea().setVisible(false);
                            dibujarLineaAtras(elemento);
                        }
                    }
                }
            }
        });
        elemento.getDibujo().setOnMouseEntered((MouseEvent event) -> {
            elemento.getDibujo().setStyle("-fx-border-color: darkblue;");
            elemento.getDibujo().setCursor(Cursor.OPEN_HAND);
        });
        elemento.getDibujo().setOnMouseExited((MouseEvent event) -> {
            elemento.getDibujo().setStyle("");
        });
        elemento.getDibujo().setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    Stage stage1 = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaOsciloscopio.fxml"));
                    Parent root = loader.load();
                    VentanaOsciloscopioController osciloscopioController = loader.getController();

                    osciloscopioController.init(controlador, stage, ventana_principal, Pane, osciloscopioController);
                    osciloscopioController.init2(elemento);

                    osciloscopioController.btnCrear.setVisible(false);

                    if (elemento.getComponente().isConectadoEntrada()) {
                        osciloscopioController.cboxConectarA.setVisible(false);
                        osciloscopioController.connect.setVisible(false);
                    }
                    osciloscopioController.btnGraficar.setVisible(true);

                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    stage1.getIcons().add(ico);
                    stage1.setTitle("OptiUAM BC - " + elemento.getDibujo().getText().toUpperCase());
                    stage1.initModality(Modality.APPLICATION_MODAL);
                    stage1.setScene(scene);
                    stage1.setResizable(false);
                    stage1.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Método que delimita el movimiento en el eje X en el panel para que el
     * elemento gráfico no salga del área de trabajo
     */
    private boolean outSideParentBoundsX(Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = Pane.getLayoutBounds();
        //check if too left
        if (parentBounds.getMaxX() <= (newX + childBounds.getMaxX())) {
            return true;
        }
        //check if too right
        if (parentBounds.getMinX() >= (newX + childBounds.getMinX())) {
            return true;
        }
        return false;
    }

    /**
     * Método que delimita el movimiento en el eje Y en el panel para que el
     * elemento gráfico no salga del área de trabajo
     */
    private boolean outSideParentBoundsY(Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = Pane.getLayoutBounds();
        //check if too down
        if (parentBounds.getMaxY() <= (newY + childBounds.getMaxY())) {
            return true;
        }
        //check if too up
        if (parentBounds.getMinY() + 179 >= (newY + childBounds.getMinY())) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que permite visualizar la conexion hacia atras del conector
     * con otro elemento
     *
     * @param elem Elemento grafico del conector
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line = new Line();
        ElementoGrafico aux = new ElementoGrafico();
        for (int it = 0; it < controlador.getDibujos().size(); it++) {
            if (elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                aux = controlador.getDibujos().get(it);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLACK);

                if (aux.getDibujo().getText().contains("splitter")) {
                    Splitter splitter = (Splitter) aux.getComponente();
                    switch (splitter.getSalidas()) {
                        case 2:
                            line.setStartX(aux.getDibujo().getLayoutX() + 50);
                            line.setStartY(aux.getDibujo().getLayoutY() + (24));
                            break;
                        case 4:
                            line.setStartX(aux.getDibujo().getLayoutX() + 50);
                            line.setStartY(aux.getDibujo().getLayoutY() + (18));
                            break;
                        case 8:
                            line.setStartX(aux.getDibujo().getLayoutX() + 80);
                            line.setStartY(aux.getDibujo().getLayoutY() + (10));
                            break;
                        default:
                            break;
                    }
                } else if (aux.getDibujo().getText().contains("demux")) {
                    Demultiplexor de = (Demultiplexor) aux.getComponente();
                    switch (de.getSalidas()) {
                        case 2:
                            line.setStartX(aux.getDibujo().getLayoutX() + 50);
                            line.setStartY(aux.getDibujo().getLayoutY() + (24));
                            break;
                        case 4:
                            line.setStartX(aux.getDibujo().getLayoutX() + 50);
                            line.setStartY(aux.getDibujo().getLayoutY() + (18));
                            break;
                        case 8:
                            line.setStartX(aux.getDibujo().getLayoutX() + 65);
                            line.setStartY(aux.getDibujo().getLayoutY() + (10));
                            break;

                        default:
                            break;
                    }
                } else {
                    line.setStartX(aux.getDibujo().getLayoutX() + aux.getDibujo().getWidth() - 3);
                    line.setStartY(aux.getDibujo().getLayoutY() + 20);
                }
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY() + 10);
                line.setVisible(true);
                Pane.getChildren().add(line);
                aux.getComponente().setLinea(line);
            }
        }
    }

    /**
     * Método para cerrar la ventana del conector
     *
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }

    /**
     * Método para inicializar los valores de la gráfica en mW, graficar y mapear los Sliders con los ejes
     */
    public void anadirGraficaWatts(){
        grafica.getData().clear();
        x.setLabel("Tiempo [s]");
        y.setLabel("Potencia [mW]");
        grafica.getStylesheets().add(getClass().getResource("/Static/CSS/style.css").toExternalForm());
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < elemento.getComponente().getDatos().size(); i++) {
            series.getData().add(new XYChart.Data<>(tiempo.get(i), elemento.getComponente().getDatos().get(i) ));
        }

        x.setAutoRanging(false);
        y.setAutoRanging(false);
        x.setLowerBound(Collections.min(tiempo));
        x.setUpperBound(Collections.max(tiempo));
        y.setLowerBound(Collections.min(elemento.getComponente().getDatos()));
        y.setUpperBound(Collections.max(elemento.getComponente().getDatos()));

        centroX.setMin(x.getLowerBound());
        centroX.setMax(x.getUpperBound());
        centroX.setValue((x.getLowerBound() + x.getUpperBound()) / 2 );

        centroY.setMin(y.getLowerBound());
        centroY.setMax(y.getUpperBound());
        centroY.setValue((y.getLowerBound() + y.getUpperBound()) / 2 );

        centroX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroX());
        centroY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroY());
        zoomX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomX(newVal.doubleValue()));
        zoomY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomYmW(newVal.doubleValue()));
        grafica.getData().add(series);
    }

    /**
     * Método para inicializar los valores de la gráfica en dBm, graficar y mapear los Sliders con los ejes
     */
    public void anadirGraficadBm(){
        grafica.getData().clear();
        double dato;
        x.setLabel("Tiempo [s]");
        y.setLabel("Potencia [dBm]");
        grafica.getStylesheets().add(getClass().getResource("/Static/CSS/style.css").toExternalForm());
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < elemento.getComponente().getDatos().size(); i++) {
            dato = calculaDecibeles(elemento.getComponente().getDatos().get(i));
            series.getData().add(new XYChart.Data<>(tiempo.get(i), (dato < -60) ? -60 : dato));
        }

        x.setAutoRanging(false);
        y.setAutoRanging(false);
        x.setLowerBound(Collections.min(tiempo));
        x.setUpperBound(Collections.max(tiempo));
        y.setLowerBound(-60);
        y.setUpperBound(calculaDecibeles(Collections.max(elemento.getComponente().getDatos())));

        centroX.setMin(x.getLowerBound());
        centroX.setMax(x.getUpperBound());
        centroX.setValue((x.getLowerBound() + x.getUpperBound()) / 2 );

        centroY.setMin(y.getLowerBound());
        centroY.setMax(y.getUpperBound());
        centroY.setValue((y.getLowerBound() + y.getUpperBound()) / 2 );

        centroX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroX());
        centroY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroY());
        zoomX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomX(newVal.doubleValue()));
        zoomY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomYdBm(newVal.doubleValue()));
        grafica.getData().add(series);
    }

    /**
     * Método para transformar la potencia de miliWatts a decibeles
     * @param miliWatts Valor en miliWatts
     * @return
     */
    public double calculaDecibeles(double miliWatts){
        double decibeles = 0;
        decibeles = 10 * Math.log10(miliWatts);
        return decibeles;
    }

    /**
     * Método para ajustar los límites del eje "x" y permitir el zoom
     * @param factor Factor por el que se dividirá la ventana
     */
    private void ajustarZoomX(double factor){
        double center = (x.getUpperBound() + x.getLowerBound()) / 2;
        double nuevoRango = Collections.max(tiempo) / factor;
        x.setLowerBound(center - nuevoRango / 2);
        x.setUpperBound(center + nuevoRango / 2);
        x.setTickUnit(nuevoRango / 10);
    }

    /**
     * Método para ajustar los límites del eje "y" y permitir el zoom en la gráfica de miliWatts
     * @param factor Factor por el que se dividirá la ventana
     */
    private void ajustarZoomYmW(double factor){
        double centro = (y.getUpperBound() + y.getLowerBound()) / 2;
        double nuevoRango = Collections.max(elemento.getComponente().getDatos()) / factor;
        y.setLowerBound(centro - nuevoRango / 2);
        y.setUpperBound(centro + nuevoRango / 2);
        y.setTickUnit(nuevoRango / 10);
    }

    /**
     * Método para ajustar los límites del eje "y" y permitir el zoom en la gráfica de decibeles
     * @param factor Factor por el que se dividirá la ventana
     */
    private void ajustarZoomYdBm(double factor){
        double centro = (y.getUpperBound() + y.getLowerBound()) / 2;
        double nuevoRango = calculaDecibeles(Collections.max(elemento.getComponente().getDatos())) / factor;
        y.setLowerBound(centro - nuevoRango / 2);
        y.setUpperBound(centro + nuevoRango / 2);
        y.setTickUnit(nuevoRango / 10);
    }

    /**
     * Método para ajustar los límites dependiendo el centro en el eje "x" indicado con el Slider
     */
    private void ajustarCentroX(){
        double diferencia = (x.getUpperBound() - x.getLowerBound()) / 2;
        x.setLowerBound(centroX.getValue() - diferencia);
        x.setUpperBound(centroX.getValue() + diferencia);
    }

    /**
     * Método para ajustar los límites dependiendo el centro en el eje "y" indicado con el Slider
     */
    private void ajustarCentroY(){
        double diferencia = (y.getUpperBound() - y.getLowerBound()) / 2;
        y.setLowerBound(centroY.getValue() - diferencia);
        y.setUpperBound(centroY.getValue() + diferencia);
    }

    /**
     * Grafica nuevamente dependiendo de las unidades que se seleccionen en el ComboBox
     */
    public void seleccionarUnidades(){
        if(osciloscopioControl.cboxUnidades.getSelectionModel().getSelectedItem().toString().equals("mW")){
            anadirGraficaWatts();
        } else if(osciloscopioControl.cboxUnidades.getSelectionModel().getSelectedItem().toString().equals("dBm")){
            anadirGraficadBm();
        }
    }

    // Getters y Setters

    public static int getIdOsciloscopio() {
        return idOsciloscopio;
    }

    public static void setIdOsciloscopio(int idOsciloscopio) {
        VentanaOsciloscopioController.idOsciloscopio = idOsciloscopio;
    }

    public ControladorGeneral getControlador() {
        return controlador;
    }

    public void setControlador(ControladorGeneral controlador) {
        this.controlador = controlador;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ElementoGrafico getElemento() {
        return elemento;
    }

    public void setElemento(ElementoGrafico elemento) {
        this.elemento = elemento;
    }

    public VentanaOsciloscopioController getOsciloscopioControl() {
        return osciloscopioControl;
    }

    public void setOsciloscopioControl(VentanaOsciloscopioController osciloscopioControl) {
        this.osciloscopioControl = osciloscopioControl;
    }

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaOsciloscopioController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaOsciloscopioController.posY = posY;
    }
}
