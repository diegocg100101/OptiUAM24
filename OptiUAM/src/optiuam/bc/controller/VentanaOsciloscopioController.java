package optiuam.bc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.model.*;

import static optiuam.bc.controller.VentanaConectorController.afectarDatos;
import static optiuam.bc.controller.VentanaMultiplexorController.datos;
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
     * Boton para refrescar la grafica
     */
    public Button btnReset;
    public Button btnDesconectado;
    public Button btnGraficar2;
    public TextField lowerBoundX;
    public TextField upperBoundX;
    public TextField lowerBoundY;
    public TextField upperBoundY;
    public Button btnLimitesY;
    public Button btnLimitesX;
    public Label yLabel;
    public Label xLabel;
    public Button btnApuntador;

    /**
     * Lista desplegable de elementos para conectar
     */
    @FXML
    ComboBox cboxConectarA;

    /**
     * Botón para crear una instancia
     */
    @FXML
    Button btnConectar;

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

    private ArrayList<Double> limitesX = new ArrayList<>();

    private ArrayList<Double> limitesY = new ArrayList<>();
    private Rectangle zoomRect;
    private Point2D dragStart;

    /**
     * Inicializa el controlador
     *
     * @param controlador            Controlador general
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
        osciloscopioControl.cboxConectarA.getItems().add("Disconnected");
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
                if (!controlador.getElementos().get(elemento1).isConectadoSalida()) {
                    osciloscopioControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento1).getDibujo().getText());
                }
            }
        }
    }

    /**
     * Inicializar el controlador con solo el elemento gráfico
     *
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
        btnConectar.setVisible(false);
        btnGraficar.setVisible(true);
        btnReset.setVisible(true);
        btnUnidades.setVisible(false);
        btnDesconectado.setVisible(false);
        btnGraficar2.setVisible(false);
        btnLimitesX.setVisible(false);
        btnLimitesY.setVisible(false);

        lowerBoundX.setVisible(false);
        lowerBoundY.setVisible(false);

        upperBoundX.setVisible(false);
        upperBoundY.setVisible(false);

        xLabel.setVisible(false);
        yLabel.setVisible(false);

        zoomX.setMin(1);
        zoomX.setMax(100);
        zoomX.setValue(1);

        // Iniciar implementación de zoom por recuadro
        // Crear rectángulo de selección
        zoomRect = new Rectangle();
        zoomRect.setFill(Color.LIGHTBLUE.deriveColor(0, 1, 1, 0.3));
        zoomRect.setStroke(Color.BLUE);
        zoomRect.setVisible(false);

        Pane pane = (Pane) grafica.getParent();
        pane.getChildren().add(zoomRect);  // Añadir el rectángulo al pane


        // Eventos de clic y arrastre
        grafica.setOnMousePressed(this::iniciarArrastreZoom);
        grafica.setOnMouseDragged(this::arrastrarZoom);
        grafica.setOnMouseReleased(this::soltarArrastreZoom);

        //Iniciar punteros
        // Crear el puntero 
        puntero = new Circle(5); // Un círculo de radio 5
        puntero.setFill(Color.RED); // Color del puntero

        // Crear el texto para los valores
        textoPuntero = new Text();
        textoPuntero.setFill(Color.BLACK);
        textoPuntero.setStyle("-fx-font-size: 12px;");

        // Añadir el puntero y el texto al contenedor de la gráfica
        pane.getChildren().addAll(puntero, textoPuntero);

        // Configurar el botón
        btnApuntador.setOnAction(event -> {
            punteroActivo = !punteroActivo;  // Cambiar el estado del puntero
            if (punteroActivo) {
                activarPuntero();  // Activar el puntero
            } else {
                desactivarPuntero();  // Desactivar el puntero
            }
        });


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
     *
     * @param event Eventos del sistema
     * @throws RuntimeException
     * @throws InvocationTargetException
     */
    public void crearOsciloscopio(ActionEvent event) throws RuntimeException, InvocationTargetException {
        // Si no hay nada conectado, se crea una instancia y se agrega al panel
        if(osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().equals("Disconnected")){
            Osciloscopio osciloscopio = new Osciloscopio();
            osciloscopio.setConectadoEntrada(false);
            osciloscopio.setIdOsciloscopio(idOsciloscopio);
            osciloscopio.setNombre("oscilloscope");
            guardarOsciloscopio(osciloscopio);
            eventos(elemento);
            idOsciloscopio++;
            cerrarVentana(event);
        } else {
            // Si hay lago conectado, lo busca, verifica que el elemento al que se quiere conectar no tenga otro componente en la salida
            for (int elemento2 = 0; elemento2 < controlador.getElementos().size(); elemento2++) {
                if(!controlador.getElementos().get(elemento2).isConectadoSalida()) {
                    if (osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().toString()
                            .equals(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                        Osciloscopio osciloscopio = new Osciloscopio();
                        osciloscopio.setConectadoEntrada(false);
                        osciloscopio.setIdOsciloscopio(idOsciloscopio);
                        osciloscopio.setNombre("oscilloscope");
                        guardarOsciloscopio(osciloscopio);
                        conexion();
                        btnGraficar.setVisible(false);
                        btnUnidades.setVisible(true);
                        seleccionarUnidades();
                        eventos(elemento);
                        idOsciloscopio++;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Método para la conexión del osciloscopio
     */
    public void conexion() {
        for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
            if (osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().toString()
                    .equals(controlador.getDibujos().get(elemento2).getDibujo().getText())
                    && controlador.getElementos().get(elemento2).getNombre().equals("connector")) {
                ElementoGrafico eg = controlador.getDibujos().get(elemento2);
                elemento.getComponente().setElementoConectadoEntrada(eg.getDibujo().getText());
                elemento.getComponente().setConectadoEntrada(true);
                eg.getComponente().setElementoConectadoSalida(elemento.getDibujo().getText());
                eg.getComponente().setConectadoSalida(true);

                // Pasa el buffer al elemento conectado
                afectarDatos(eg);
                elemento.getComponente().setDatos(eg.getComponente().getDatos());
                dibujarLineaAtras(elemento);
                btnDesconectado.setVisible(true);
                break;
            } else if (osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().toString()
                    .equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                ElementoGrafico eg = controlador.getDibujos().get(elemento2);
                elemento.getComponente().setElementoConectadoEntrada(eg.getDibujo().getText());
                elemento.getComponente().setConectadoEntrada(true);
                eg.getComponente().setElementoConectadoSalida(elemento.getDibujo().getText());
                eg.getComponente().setConectadoSalida(true);

                // Pasa el buffer al elemento conectado
                elemento.getComponente().setDatos(eg.getComponente().getDatos());
                dibujarLineaAtras(elemento);
                btnDesconectado.setVisible(true);
                break;
            }else if (osciloscopioControl.cboxConectarA.getSelectionModel().getSelectedItem().toString()
                    .equals(controlador.getDibujos().get(elemento2).getDibujo().getText())
                    && controlador.getElementos().get(elemento2).getNombre().equals("mux") ){
                // eg = mux
                ElementoGrafico eg = controlador.getDibujos().get(elemento2);

                // elemento = osciloscopio
                elemento.getComponente().setElementoConectadoEntrada(eg.getDibujo().getText());
                elemento.getComponente().setConectadoEntrada(true);
                eg.getComponente().setElementoConectadoSalida(elemento.getDibujo().getText());
                eg.getComponente().setConectadoSalida(true);


                Multiplexor mux = (Multiplexor) eg.getComponente();
                mux.sumarDatos();

                elemento.getComponente().setDatos(mux.getSenalSalida());
                dibujarLineaAtras(elemento);
                btnDesconectado.setVisible(true);
                break;
            }
        }
    }

    public void conectarNuevamente() {
        conexion();
        seleccionarUnidades();
        btnConectar.setVisible(false);
    }

    /**
     * Método para gestionar eventos del osciloscopio
     *
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
                        if (elemento.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos()
                                .get(it).getDibujo().getText())) {
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

                    osciloscopioController.btnGraficar.setVisible(false);

                    if (elemento.getComponente().isConectadoEntrada()) {
                        osciloscopioController.connect.setVisible(false);
                        osciloscopioController.btnGraficar2.setVisible(true);
                        osciloscopioController.btnDesconectado.setVisible(true);
                        osciloscopioController.cboxConectarA.setVisible(false);
                    } else {
                        osciloscopioController.connect.setVisible(true);
                        osciloscopioController.cboxConectarA.setVisible(true);
                        osciloscopioController.btnConectar.setVisible(true);
                    }

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
            } else if (event.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elemento);
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
    public void anadirGraficaWatts() {
        limitesX.clear();
        limitesY.clear();

        zoomY.setMin(0.01);
        zoomY.setMax(2);
        zoomY.setValue(1);
        grafica.getData().clear();
        x.setLabel("Tiempo [s]");
        y.setLabel("Potencia [mW]");
        grafica.getStylesheets().add(getClass().getResource("/Static/CSS/style.css").toExternalForm());
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < elemento.getComponente().getDatos().size(); i++) {
            series.getData().add(new XYChart.Data<>(tiempo.get(i), elemento.getComponente().getDatos().get(i)));
        }

        x.setAutoRanging(false);
        y.setAutoRanging(false);
        x.setLowerBound(Collections.min(tiempo));
        x.setUpperBound(Collections.max(tiempo));
        y.setLowerBound(Collections.min(elemento.getComponente().getDatos()));
        y.setUpperBound(Collections.max(elemento.getComponente().getDatos()));

        limitesX.add(x.getLowerBound());
        limitesX.add(x.getUpperBound());

        limitesY.add(y.getLowerBound());
        limitesY.add(y.getUpperBound());


        centroX.setMin(x.getLowerBound());
        centroX.setMax(x.getUpperBound());
        centroX.setValue((x.getLowerBound() + x.getUpperBound()) / 2);

        centroY.setMin(y.getLowerBound());
        centroY.setMax(y.getUpperBound());
        centroY.setValue((y.getLowerBound() + y.getUpperBound()) / 2);

        centroX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroX());
        centroY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroY());
        zoomX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomX(newVal.doubleValue()));
        zoomY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomYmW(newVal.doubleValue()));
        grafica.getData().add(series);
    }

    private void iniciarArrastreZoom(MouseEvent event) {
        dragStart = new Point2D(event.getX(), event.getY());
        zoomRect.setX(dragStart.getX());
        zoomRect.setY(dragStart.getY());
        zoomRect.setWidth(0);
        zoomRect.setHeight(0);
        zoomRect.setVisible(true);
    }

    private void arrastrarZoom(MouseEvent event) {
        double ancho = event.getX() - dragStart.getX();
        double alto = event.getY() - dragStart.getY();
        zoomRect.setWidth(Math.abs(ancho));
        zoomRect.setHeight(Math.abs(alto));
        zoomRect.setX(Math.min(dragStart.getX(), event.getX()));
        zoomRect.setY(Math.min(dragStart.getY(), event.getY()));
    }

    private void soltarArrastreZoom(MouseEvent event) {
        if (zoomRect.getWidth() > 0 && zoomRect.getHeight() > 0) {
            double xZoomMin = x.getValueForDisplay(zoomRect.getX()).doubleValue();
            double xZoomMax = x.getValueForDisplay(zoomRect.getX() + zoomRect.getWidth()).doubleValue();
            double yZoomMin = y.getValueForDisplay(zoomRect.getY() + zoomRect.getHeight()).doubleValue();
            double yZoomMax = y.getValueForDisplay(zoomRect.getY()).doubleValue();

            // Ajustar límites de los ejes
            x.setLowerBound(xZoomMin);
            x.setUpperBound(xZoomMax);
            y.setLowerBound(yZoomMin);
            y.setUpperBound(yZoomMax);
        }
        zoomRect.setVisible(false);
    }

    /**
     * Método para inicializar los valores de la gráfica en dBm, graficar y mapear los Sliders con los ejes
     */
    public void anadirGraficadBm() {
        limitesX.clear();
        limitesY.clear();

        zoomY.setMin(0.01);
        zoomY.setMax(0.1);
        zoomY.setValue(0.05);
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

        limitesX.add(x.getLowerBound());
        limitesX.add(x.getUpperBound());

        limitesY.add(y.getLowerBound());
        limitesY.add(y.getUpperBound());

        centroX.setMin(x.getLowerBound());
        centroX.setMax(x.getUpperBound());
        centroX.setValue((x.getLowerBound() + x.getUpperBound()) / 2);

        centroY.setMin(y.getLowerBound());
        centroY.setMax(y.getUpperBound());
        centroY.setValue((y.getLowerBound() + y.getUpperBound()) / 2);

        centroX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroX());
        centroY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarCentroY());
        zoomX.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomX(newVal.doubleValue()));
        zoomY.valueProperty().addListener((obs, oldVal, newVal) -> ajustarZoomYdBm(newVal.doubleValue()));
        grafica.getData().add(series);
    }

    /**
     * Método para restablecer los limites
     */
    public void resetLimits() {
        x.setLowerBound(limitesX.get(0));
        x.setUpperBound(limitesX.get(1));

        y.setLowerBound(limitesY.get(0));
        y.setUpperBound(limitesY.get(1));
    }

    /**
     * Método para transformar la potencia de miliWatts a decibeles
     *
     * @param miliWatts Valor en miliWatts
     * @return
     */
    public double calculaDecibeles(double miliWatts) {
        return 10 * Math.log10(miliWatts);
    }

    /**
     * Método para ajustar los límites del eje "x" y permitir el zoom
     *
     * @param factor Factor por el que se dividirá la ventana
     */
    private void ajustarZoomX(double factor) {
        double center = (x.getUpperBound() + x.getLowerBound()) / 2;
        double nuevoRango = Collections.max(tiempo) / factor;
        x.setLowerBound(center - nuevoRango);
        x.setUpperBound(center + nuevoRango);
        x.setTickUnit(nuevoRango / 10);
    }

    /**
     * Método para ajustar los límites del eje "y" y permitir el zoom en la gráfica de miliWatts
     *
     * @param factor Factor por el que se dividirá la ventana
     */
    private void ajustarZoomYmW(double factor) {
        double centro = (y.getUpperBound() + y.getLowerBound()) / 2;
        double nuevoRango = Collections.max(elemento.getComponente().getDatos()) / factor;
        y.setLowerBound(centro - nuevoRango);
        y.setUpperBound(centro + nuevoRango);
        y.setTickUnit(nuevoRango / 10);
    }

    /**
     * Método para ajustar los límites del eje "y" y permitir el zoom en la gráfica de decibeles
     *
     * @param factor Factor por el que se dividirá la ventana
     */
    private void ajustarZoomYdBm(double factor) {
        double centro = (y.getUpperBound() + y.getLowerBound()) / 2;
        double nuevoRango = calculaDecibeles(Collections.max(elemento.getComponente().getDatos())) / factor;
        y.setLowerBound(centro - nuevoRango);
        y.setUpperBound(centro + nuevoRango);
        y.setTickUnit(nuevoRango / 10);
    }

    /**
     * Método para ajustar los límites dependiendo el centro en el eje "x" indicado con el Slider
     */
    private void ajustarCentroX() {
        double diferencia = (x.getUpperBound() - x.getLowerBound()) / 2;
        x.setLowerBound(centroX.getValue() - diferencia);
        x.setUpperBound(centroX.getValue() + diferencia);
    }

    /**
     * Método para ajustar los límites dependiendo el centro en el eje "y" indicado con el Slider
     */
    private void ajustarCentroY() {
        double diferencia = (y.getUpperBound() - y.getLowerBound()) / 2;
        y.setLowerBound(centroY.getValue() - diferencia);
        y.setUpperBound(centroY.getValue() + diferencia);
    }

    /**
     * Grafica nuevamente dependiendo de las unidades que se seleccionen en el ComboBox
     */
    public void seleccionarUnidades() {
        if (osciloscopioControl.cboxUnidades.getSelectionModel().getSelectedItem().toString().equals("mW")) {
            anadirGraficaWatts();
            lowerBoundX.setVisible(true);
            lowerBoundY.setVisible(true);

            upperBoundX.setVisible(true);
            upperBoundY.setVisible(true);

            xLabel.setVisible(true);
            yLabel.setVisible(true);

            btnLimitesX.setVisible(true);
            btnLimitesY.setVisible(true);
        } else if (osciloscopioControl.cboxUnidades.getSelectionModel().getSelectedItem().toString().equals("dBm")) {
            anadirGraficadBm();
            lowerBoundX.setVisible(true);
            lowerBoundY.setVisible(true);

            upperBoundX.setVisible(true);
            upperBoundY.setVisible(true);

            xLabel.setVisible(true);
            yLabel.setVisible(true);

            btnLimitesX.setVisible(true);
            btnLimitesY.setVisible(true);
        }
    }

    /**
     * Método para cambiar los límites de la ventana en el eje X
     */
    public void ajustarLimitesTextFieldX() {
        x.setLowerBound(Double.parseDouble(lowerBoundX.getText()));
        x.setUpperBound(Double.parseDouble(upperBoundX.getText()));
    }

    /**
     * Método para cambiar los límites de la ventana en el eje Y
     */
    public void ajustarLimitesTextFieldY() {
        y.setLowerBound(Double.parseDouble(lowerBoundY.getText()));
        y.setUpperBound(Double.parseDouble(upperBoundY.getText()));
    }

    /**
     * Método para desconectar la fuente
     *
     * @param event Representa cualquier tipo de acción
     */
    @FXML
    private void desconectar(ActionEvent event) {
        for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
            if (elemento.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento2)
                    .getDibujo().getText())) {
                Componente comp = controlador.getElementos().get(elemento2);
                comp.setConectadoSalida(false);
                comp.setElementoConectadoSalida("");
                comp.getLinea().setVisible(false);
                break;
            }
        }
        osciloscopioControl.cboxConectarA.getSelectionModel().select(0);
        if (elemento.getComponente().isConectadoEntrada()) {
            elemento.getComponente().setConectadoEntrada(false);
            elemento.getComponente().setElementoConectadoEntrada("");
        }

        grafica.getData().clear();

        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected oscilloscope!",
                aceptar);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }

    public void mostrarMenu(ElementoGrafico dibujo) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicate");
        MenuItem menuItem3 = new MenuItem("-Delete");

        /*Duplicar*/
        menuItem1.setOnAction(e -> {
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (dibujo.getId() == controlador.getElementos().get(elemento).getId()) {
                    Osciloscopio osciloscopioAux = new Osciloscopio();
                    Osciloscopio osciloscopioAux1 = (Osciloscopio) controlador.getElementos().get(elemento);
                    osciloscopioAux.setIdOsciloscopio(idOsciloscopio);
                    osciloscopioAux.setNombre("osciloscopio");
                    osciloscopioAux.setTipo(osciloscopioAux1.getTipo());


                    duplicarOsciloscopio(osciloscopioAux, dibujo);
                    idOsciloscopio++;
                    break;
                }
            }
        });

        /**Eliminar*/
        menuItem3.setOnAction(e -> {
            if (dibujo.getComponente().isConectadoEntrada()) {
                for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                    if (dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())) {
                        Componente aux = controlador.getElementos().get(elemento);
                        aux.setConectadoSalida(false);
                        aux.setElementoConectadoSalida("");
                        aux.getLinea().setVisible(false);
                    }
                }
            }
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (dibujo.getId() == controlador.getElementos().get(elemento).getId()) {
                    Osciloscopio aux = (Osciloscopio) controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux);
                }
            }
            dibujo.getDibujo().setVisible(false);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nOscilloscope removed!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
        });

        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem3);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }

    /**
     * Metodo que duplica una fuente
     *
     * @param osciloscopio Fuente a duplicar
     * @param el     Elemento grafico de la fuente a duplicar
     */
    public void duplicarOsciloscopio(Osciloscopio osciloscopio, ElementoGrafico el) {
        osciloscopio.setId(controlador.getContadorElemento());
        controlador.getElementos().add(osciloscopio);
        Label dibujo = new Label();
        ElementoGrafico elem = new ElementoGrafico();

        osciloscopio.setPosX(dibujo.getLayoutX());
        osciloscopio.setPosY(dibujo.getLayoutY());
        setPosX(osciloscopio.getPosX());
        setPosY(osciloscopio.getPosY());

        elem.setComponente(osciloscopio);
        elem.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_osciloscopio2.png")));
        dibujo.setText(osciloscopio.getNombre() + "_" + osciloscopio.getIdOsciloscopio());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        dibujo.setLayoutX(el.getDibujo().getLayoutX() + 35);
        dibujo.setLayoutY(el.getDibujo().getLayoutY() + 20);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);

        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nOscilloscope duplicated!",
                aceptar);
        alert.setTitle("Osiloscopio");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Variable para controlar el estado del puntero
    private Circle puntero; // El puntero
    private Text textoPuntero; // El texto para mostrar las coordenadas
    private boolean punteroActivo = false; // Estado del puntero


    // Activar la actualización del puntero
    private void activarPuntero() {
        // Hacer visible el puntero
        puntero.setVisible(true);
        textoPuntero.setVisible(true);

        // Activar el movimiento del ratón para actualizar la posición del puntero
        grafica.setOnMouseMoved(this::moverPuntero);
    }

    // Desactivar el puntero
    private void desactivarPuntero() {
        // Ocultar el puntero y el texto
        puntero.setVisible(false);
        textoPuntero.setVisible(false);

        // Desactivar el movimiento del ratón
        grafica.setOnMouseMoved(null);
    }

    // Manejar el movimiento del ratón
    private void moverPuntero(MouseEvent event) {
        if (punteroActivo) {
            // Obtener la posición del ratón
            double xPos = event.getX();
            double yPos = event.getY();

            // Establecer la posición del puntero (alfiler)
            puntero.setCenterX(xPos);
            puntero.setCenterY(yPos);

            // Mostrar el texto con los valores exactos
            double valorX = x.getValueForDisplay(xPos).doubleValue(); // Convertir la coordenada x a valor de la gráfica
            double valorY = y.getValueForDisplay(yPos).doubleValue(); // Convertir la coordenada y a valor de la gráfica

            // Actualizar el texto del puntero
            textoPuntero.setText(String.format("X: %.2f, Y: %.2f", valorX, valorY));
            textoPuntero.setX(xPos + 10); // Ajusta la posición del texto
            textoPuntero.setY(yPos - 10);
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
