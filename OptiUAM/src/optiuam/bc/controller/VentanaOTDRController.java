package optiuam.bc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.OTDR;
import optiuam.bc.model.Osciloscopio;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de instanciar el OTDR
 *
 * @author Ilse López
 * @author Diego Cantoral
 * @see ControladorGeneral
 */

public class VentanaOTDRController extends ControladorGeneral implements Initializable {

    /**
     * Identificador del OTDR
     */
    static int idOTDR = 0;


    /**
     * Controlador del simulador
     */
    ControladorGeneral controlador;

    /**
     * Escenario en el cual se agregan los objetos creados
     */
    Stage stage;

    /**
     * Posición del conector en el eje X
     */
    static double posX;

    /**
     * Posición del conector en el eje Y
     */
    static double posY;

    /**
     *
     */
    ElementoGrafico elemento;

    /**
     * Controlador del osciloscopio
     */
    VentanaOTDRController OTDRControl;

    /**
     * Lista desplegable de elementos para conectar
     */
    @FXML
    public ComboBox cboxConectarA;

    /**
     * Botón para crear instancia
     */
    @FXML
    public Button btnCrear;

    @FXML
    Pane Pane;

    public void init(ControladorGeneral controlador, Stage stage, VentanaPrincipal ventana, Pane pane){
        this.controlador = controlador;
        this.stage = stage;
        this.ventana_principal = ventana;
        this.Pane = pane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboxConectarA.setVisible(true);
        btnCrear.setVisible(true);

    }

    /**
     *
     * @return
     */
    public void guardarOTDR(OTDR OTDR){
        OTDR.setId(controlador.getContadorElemento());
        controlador.getElementos().add(OTDR);

        Label dibujo = new Label();
        ElementoGrafico elemento = new ElementoGrafico();

        OTDR.setPosX(dibujo.getLayoutX());
        OTDR.setPosY(dibujo.getLayoutY());
        setPosX(OTDR.getPosX());
        setPosY(OTDR.getPosY());

        elemento.setComponente(OTDR);
        elemento.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_otdr.png")));
        dibujo.setText(OTDR.getNombre() + "_" + OTDR.getIdOTDR());

        dibujo.setContentDisplay(ContentDisplay.TOP);
        elemento.setDibujo(dibujo);
        controlador.getDibujos().add(elemento);


        Pane.getChildren().add(elemento.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
        OTDR.setNombreid(OTDR.getNombre() + "_" + OTDR.getId());
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nOTDR created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();

    }

    public void crearOTDR(ActionEvent event) throws RuntimeException, InvocationTargetException {
        OTDR otdr = new OTDR();
        otdr.setConectadoEntrada(false);
        otdr.setIdOTDR(idOTDR);
        otdr.setNombre("OTDR");
        idOTDR++;
        guardarOTDR(otdr);
    }

    public static int getIdOTDR() {
        return idOTDR;
    }

    public static void setIdOTDR(int idOTDR) {
        VentanaOTDRController.idOTDR = idOTDR;
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

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaOTDRController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaOTDRController.posY = posY;
    }

    public ElementoGrafico getElemento() {
        return elemento;
    }

    public void setElemento(ElementoGrafico elemento) {
        this.elemento = elemento;
    }

    public VentanaOTDRController getOTDRControl() {
        return OTDRControl;
    }

    public void setOTDRControl(VentanaOTDRController OTDRControl) {
        this.OTDRControl = OTDRControl;
    }
}
