package optiuam.bc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.OTDR;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Controlador del OTDR
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


    public void init(ControladorGeneral controlador, Stage stage, VentanaPrincipal ventana, Pane pane) {
        this.controlador = controlador;
        this.stage = stage;
        this.ventana_principal = ventana;
        this.Pane = pane;
    }

    /**
     * Inicializar el controlador con solo el elemento gráfico
     *
     * @param elemento Elemento gráfico
     */
    public void init2(ElementoGrafico elemento) {
        this.elemento = elemento;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboxConectarA.setVisible(true);
        btnCrear.setVisible(true);

    }

    /**
     * @return
     */
    public void guardarOTDR(OTDR OTDR) {
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

        eventos(elemento);
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
        cerrarVentana(event);
    }

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
                    elemento.getComponente().getLinea().setVisible(false);
                    dibujarLinea(elemento);
                }
            }
        });
        elemento.getDibujo().setOnMouseEntered((MouseEvent event) -> {
            elemento.getDibujo().setStyle("-fx-border-color: darkblue;");
            elemento.getDibujo().setCursor(Cursor.OPEN_HAND);
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
     * Método que permite visualizar la conexión
     *
     * @param elemG Elemento grafico del conector
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line = new Line();
        ElementoGrafico aux = new ElementoGrafico();

        for (int it = 0; it < controlador.getDibujos().size(); it++) {
            if (elemG.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                aux = controlador.getDibujos().get(it);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLACK);
                line.setStartX(aux.getDibujo().getLayoutX() + aux.getDibujo().getWidth());
                line.setStartY(aux.getDibujo().getLayoutY() + 12);
                line.setEndX(elemG.getDibujo().getLayoutX());
                line.setEndY(elemG.getDibujo().getLayoutY() + 23);
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


}