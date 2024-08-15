package optiuam.bc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.Multiplexor;
import optiuam.bc.model.Osciloscopio;
import optiuam.bc.model.Splitter;

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

    @FXML
    Pane Pane;

    public void init(ControladorGeneral controlador, Stage stage, VentanaPrincipal ventana, Pane pane) {
        this.controlador = controlador;
        this.stage = stage;
        this.ventana_principal = ventana;
        this.Pane = pane;
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

    /**
     *
     * @param osciloscopio
     */
    public void guardarOsciloscopio(Osciloscopio osciloscopio){
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

        eventos(elemento);
        Pane.getChildren().add(elemento.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
        osciloscopio.setNombreid(osciloscopio.getNombre() + "_" + osciloscopio.getId());
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nConnector created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void crearOsciloscopio(ActionEvent event) throws RuntimeException, InvocationTargetException {
        Osciloscopio osciloscopio = new Osciloscopio();
        osciloscopio.setConectadoEntrada(false);
        osciloscopio.setIdOsciloscopio(idOsciloscopio);
        osciloscopio.setNombre("Osciloscopio");
        idOsciloscopio ++;
        guardarOsciloscopio(osciloscopio);
        cerrarVentana(event);
    }

    public void eventos(ElementoGrafico elemento){
        elemento.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double newX = event.getSceneX();
                double newY = event.getSceneY();
                int j = 0;
                for (int a = 0; a < Pane.getChildren().size(); a++) {
                    if (Pane.getChildren().get(a).toString().contains(elemento.getDibujo().getText())) {
                        j = a;
                        break;
                    }
                }
                if (outSideParentBoundsX(elemento.getDibujo().getLayoutBounds(), newX, newY)) {    //return;
                } else {
                    elemento.getDibujo().setLayoutX(Pane.getChildren().get(j).getLayoutX() + event.getX() + 1);
                }

                if (outSideParentBoundsY(elemento.getDibujo().getLayoutBounds(), newX, newY)) {    //return;
                } else {
                    elemento.getDibujo().setLayoutY(Pane.getChildren().get(j).getLayoutY() + event.getY() + 1);
                }
                if (elemento.getComponente().isConectadoSalida() == true) {
                    elemento.getComponente().getLinea().setVisible(false);
                    dibujarLinea(elemento);
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
    }

    /**
     * Metodo que delimita el movimiento en el eje X en el panel para que el
     * elemento grafico no salga del area de trabajo
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
     * Metodo que delimita el movimiento en el eje Y en el panel para que el
     * elemento grafico no salga del area de trabajo
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
     * Metodo que permite visualizar la conexion hacia delante del conector
     * con otro elemento
     *
     * @param elemG Elemento grafico del conector
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line = new Line();
        line.setStartX(elemG.getDibujo().getLayoutX() + 65);
        line.setStartY(elemG.getDibujo().getLayoutY() + 8);
        ElementoGrafico aux = new ElementoGrafico();
        for (int it = 0; it < controlador.getDibujos().size(); it++) {
            if (controlador.getDibujos().get(it).getDibujo().getText().startsWith("mux")) {
                if (elemG.getComponente().getElementoConectadoSalida().startsWith(controlador.getDibujos().get(it).getDibujo().getText())) {
                    aux = controlador.getDibujos().get(it);
                }
            } else if (elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                aux = controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);

        if (aux.getDibujo().getText().contains("fiber")) {
            line.setEndX(aux.getDibujo().getLayoutX() + 3);
            line.setEndY(aux.getDibujo().getLayoutY() + 20);
        } else if (aux.getDibujo().getText().contains("spectrum")) {
            line.setEndX(aux.getDibujo().getLayoutX() + 8);
            line.setEndY(aux.getDibujo().getLayoutY() + 4);
        } else if (aux.getDibujo().getText().contains("splitter")) {
            Splitter auxsp = (Splitter) aux.getComponente();
            int num = auxsp.getSalidas();
            switch (num) {
                case 2:
                case 4:
                    line.setEndX(aux.getDibujo().getLayoutX());
                    line.setEndY(aux.getDibujo().getLayoutY() + 26);
                    break;
                case 8:
                    line.setEndX(aux.getDibujo().getLayoutX());
                    line.setEndY(aux.getDibujo().getLayoutY() + 41);
                    break;
                default:
                    break;
            }
        } else if (aux.getDibujo().getText().startsWith("mux")) {
            Multiplexor mul = (Multiplexor) aux.getComponente();
            int num = mul.getEntradas();
            if (elemG.getComponente().getElementoConectadoSalida().endsWith("input1")) {
                switch (num) {
                    case 2:
                        line.setEndX(aux.getDibujo().getLayoutX());
                        line.setEndY(aux.getDibujo().getLayoutY() + 20);
                        break;
                    case 4:
                        line.setEndX(aux.getDibujo().getLayoutX());
                        line.setEndY(aux.getDibujo().getLayoutY() + 14);
                        break;
                    case 8:
                        line.setEndX(aux.getDibujo().getLayoutX());
                        line.setEndY(aux.getDibujo().getLayoutY() + 7);
                        break;
                }
            } else {
                int num2 = 0;
                for (int cr = 0; cr < num - 1; cr++) {
                    if (mul.getConexionEntradas().get(cr).getElementoConectadoEntrada().equals(elemG.getDibujo().getText())) {
                        num2 = cr + 1;
                        break;
                    }
                }
                switch (num) {
                    case 2:
                        line.setEndX(aux.getDibujo().getLayoutX());
                        line.setEndY(aux.getDibujo().getLayoutY() + 20 + 13);
                        break;
                    case 4:
                        line.setEndX(aux.getDibujo().getLayoutX());
                        line.setEndY(aux.getDibujo().getLayoutY() + 14 + (num2 * 10));
                        break;
                    case 8:
                        line.setEndX(aux.getDibujo().getLayoutX() + 8);
                        line.setEndY(aux.getDibujo().getLayoutY() + 7 + (num2 * 9.4));
                        break;
                }
            }
        } else {
            line.setEndX(aux.getDibujo().getLayoutX() + 3);
            line.setEndY(aux.getDibujo().getLayoutY() + 4);
        }
        line.setVisible(true);
        Pane.getChildren().add(line);
        elemG.getComponente().setLinea(line);
    }

    /**
     * Metodo para cerrar la ventana del conector
     *
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
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
