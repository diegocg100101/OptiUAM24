package optiuam.bc.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.model.ElementoGrafico;

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
     * Controlador del simulador
     */
    ControladorGeneral controlador;

    /**
     * Escenario en el cual se agregan los objetos creados
     */
    Stage stage;

    /**
     *
     */
    ElementoGrafico elemento;

    /**
     * Controlador del osciloscopio
     */
    VentanaOsciloscopioController osciloscopioControl;

    /**
     * Posición del conector en el eje X
     */
    static double posX;

    /**
     * Posición del conector en el eje Y
     */
    static double posY;

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

    public void init(ControladorGeneral controlador, Stage stage, VentanaPrincipal ventana) {
        this.controlador = controlador;
        this.stage = stage;
        this.ventana_principal = ventana;
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboxConectarA.setVisible(true);
        btnCrear.setVisible(true);
    }

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
