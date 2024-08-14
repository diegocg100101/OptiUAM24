
package optiuam.bc.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.model.Componente;
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.ExcepcionDivideCero;
import optiuam.bc.model.Fuente;

/**
 * Clase VentanaFuenteController la cual se encarga de instanciar una fuente
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 * @author Carlos Elizarraras
 * @see ControladorGeneral
 */
public class VentanaFuenteController extends ControladorGeneral implements Initializable {

    /**
     * Identificador de la fuente
     */
    static int idFuente = 0;
    /**
     * Controlador del simulador
     */
    ControladorGeneral controlador;
    /**
     * Escenario en el cual se agregaran los objetos creados
     */
    Stage stage;
    /**
     * Elemento grafico de la fuente
     */
    ElementoGrafico elemG;
    /**
     * Controlador de la fuente
     */
    VentanaFuenteController fuenteControl;
    /**
     * Posicion de la fuente en el eje X
     */
    static double posX;
    /**
     * Posicion de la fuente en el eje Y
     */
    static double posY;

    /**
     * RadioButton para el tipo Laser de la fuente
     */
    @FXML
    RadioButton rbtnLaser;
    /**
     * RadioButton para el tipo LED de la fuente
     */
    @FXML
    RadioButton rbtnLed;
    /**
     * RadioButton para la longitud de onda de 1310 nm
     */
    @FXML
    RadioButton rbtnThz;
    /**
     * RadioButton para la longitud de onda de 1550 nm
     */
    @FXML
    RadioButton rbtnNm;
    /**
     * RadioButton para leer la potencia en dBm
     */
    @FXML
    RadioButton rbtnDbm;
    /**
     * RadioButton para leer la potencia en mW
     */
    @FXML
    RadioButton rbtnMw;
    /**
     * RadioButton para un pulso de tipo Gaussiano
     */
    @FXML
    RadioButton rbtnGaussian;
    /**
     * RadioButton para un pulso de tipo Supergaussiano
     */
    @FXML
    RadioButton rbtnSupergaussian;
    /**
     * RadioButton para ninguna modulacion
     */
    @FXML
    RadioButton rbtnNone;
    /**
     * RadioButton para la modulacion OOK
     */
    @FXML
    RadioButton rbtnOOK;
    /**
     * Caja de texto para ingresar la potencia de la fuente
     */
    @FXML
    TextField txtPotencia;
    /**
     * Caja de texto para ingresar la anchura espectral de la fuente
     */
    @FXML
    TextField txtAnchuraEspectro;
    /**
     * Caja de texto para ingresar el chirp de la fuente
     */
    @FXML
    TextField txtChirp;
    /**
     * Caja de texto para ingresar la velocidad de transmision de la fuente
     */
    @FXML
    TextField txtVelocidad;
    /**
     * Caja de texto para ingresar el span de pulsos de la señal de la fuente
     */
    @FXML
    TextField txtSpan;
    /**
     * Boton para abrir mas informacion de la fuente
     */
    @FXML
    Button btnInfo;
    /**
     * Boton para crear una fuente
     */
    @FXML
    Button btnCrear;
    /**
     * Boton para desconectar la fuente
     */
    @FXML
    Button btnDesconectar;
    /**
     * Boton para modificar la fuente
     */
    @FXML
    Button btnModificar;
    /**
     * Etiqueta para la velocidad de la fuente
     */
    @FXML
    Label lblVelocidad;
    /**
     * Etiqueta para la uniad de velocidad de la fuente
     */
    @FXML
    Label lblVelocidadU;
    /**
     * Etiqueta para el span de pulsos de la señal de la fuente
     */
    @FXML
    Label lblSpan;
    /**
     * Etiqueta para inidicar el span en segundos
     */
    @FXML
    Label lblSpanS;
    /**
     * Etiqueta de la lista desplegable de elementos disponibles para conectar
     * la fuente
     */
    @FXML
    Label lblConectarA;
    /**
     * Lista desplegable de elementos disponibles para conectar la fuente
     */
    @FXML
    ComboBox cboxConectarA;
    /**
     * Lista desplegable de las Frecuencias centrales para la señal de la fuente
     */
    @FXML
    ComboBox cboxFc;
    /**
     * Separador de la ventana fuente
     */
    @FXML
    Separator separator;
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
     * Metodo que muestra el identificador de la fuente
     *
     * @return idFuente
     */
    public static int getIdFuente() {
        return idFuente;
    }

    /**
     * Metodo que modifica el identificador de la fuente
     *
     * @param idFuente Identificador de la fuente
     */
    public static void setIdFuente(int idFuente) {
        VentanaFuenteController.idFuente = idFuente;
    }

    /**
     * Metodo que muestra la posicion de la fuente en el eje X
     *
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion de la fuente en el eje X
     *
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaFuenteController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion de la fuente en el eje Y
     *
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion de la fuente en el eje Y
     *
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaFuenteController.posY = posY;
    }

    /**
     * Metodo el cual inicializa la ventana de la fuente
     *
     * @param url La ubicacion utilizada para resolver rutas relativas para
     *            el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb  Los recursos utilizados para localizar el objeto raiz, o nulo
     *            si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Mensajes al pasar el cursor
        Tooltip power = new Tooltip();
        power.setText("mW: The power value must be between (0.001,10000)"
                + "\ndBm: The power value must be between (-30,40)");
        txtPotencia.setTooltip(power);

        Tooltip perdida = new Tooltip();
        perdida.setText("Laser: The width value must be max 1.0 nm"
                + "\nLed: The width value must be between (0.01,1.0) nm");
        txtAnchuraEspectro.setTooltip(perdida);
        //Siguientes mensajes son para evitar exceso en tiempo de computo
        Tooltip bitrate = new Tooltip();
        bitrate.setText("The bitrate value must be between (0.001,1)");
        txtVelocidad.setTooltip(bitrate);

        Tooltip span = new Tooltip();
        span.setText("The span value must be max 1.3");
        txtSpan.setTooltip(span);

        //Da la lista de valores de frecuencia central
        cboxFc.getItems().removeAll(cboxFc.getItems());
        cboxFc.getItems().addAll("228.84920458", "203.80", "201.07", "198.41",
                "195.81", "193.29", "190.83", "188.43", "186.09");
        cboxFc.getSelectionModel().selectFirst();

        //Elementos para modificar
        separator.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnDesconectar.setVisible(false);
        btnModificar.setVisible(false);

        //Elementos para modulacion OOK
        lblVelocidad.setVisible(false);
        txtVelocidad.setVisible(false);
        lblVelocidadU.setVisible(false);
        lblSpan.setVisible(false);
        txtSpan.setVisible(false);
        lblSpanS.setVisible(false);
    }

    /**
     * Metodo que recibe la frecuencia central en THz
     * y la devuelve en nm
     *
     * @param entrada
     * @return
     */
    public double ThzToNm(double entrada) {
        if (entrada >= 228.84920458) {
            return 1310.0;
        } else if (entrada >= 203.80) {
            return 1471.0130422;
        } else if (entrada >= 201.07) {
            return 1490.9855175;
        } else if (entrada >= 198.41) {
            return 1510.9745376;
        } else if (entrada >= 195.81) {
            return 1531.0375262;
        } else if (entrada >= 193.41448903) {
            return 1550.0;
        } else if (entrada >= 193.29) {
            return 1550.9982824;
        } else if (entrada >= 190.83) {
            return 1570.9922863;
        } else if (entrada >= 188.43) {
            return 1591.0017407;
        } else {//entrada >= 186.09
            return 1611.0078887;
        }
    }

    /**
     * Metodo que recibe la frecuencia central en nm
     * y la devuelve en THz
     *
     * @param entrada
     * @return
     */
    public double NmToThz(double entrada) {
        if (entrada >= 1611.0078887) {
            return 186.09;
        } else if (entrada >= 1591.0017407) {
            return 188.43;
        } else if (entrada >= 1570.9922863) {
            return 190.83;
        } else if (entrada >= 1550.9982824) {
            return 193.29;
        } else if (entrada >= 1550.0) {
            return 193.41448903;
        } else if (entrada >= 1531.0375262) {
            return 195.81;
        } else if (entrada >= 1510.9745376) {
            return 198.41;
        } else if (entrada >= 1490.9855175) {
            return 201.07;
        } else if (entrada >= 1471.0130422) {
            return 203.80;
        } else {//entrada >= 1310.0
            return 228.84920458;
        }
    }

    /**
     * Metodo el cual captura los datos obtenidos de la ventana de la fuente y
     * crea una
     *
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes
     *                                                     excepciones lanzadas bajo el paquete java lang
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException, ExcepcionDivideCero {
        int tipo = 0, unidadLongitudOnda = 0, unidadPotencia = 0, tipoPulso = 0, modulacion = 0;
        double longitudOnda = 0, potencia, anchura, chirp, velocidad = 0, span = 0;
        if (rbtnLaser.isSelected()) {
            tipo = 0;
        } else if (rbtnLed.isSelected()) {
            tipo = 1;
        }
        if (rbtnThz.isSelected()) {
            unidadLongitudOnda = 0;
        } else if (rbtnNm.isSelected()) {
            unidadLongitudOnda = 1;
        }
        if (rbtnMw.isSelected()) {
            unidadPotencia = 0;
        } else if (rbtnDbm.isSelected()) {
            unidadPotencia = 1;
        }
        if (rbtnGaussian.isSelected()) {
            tipoPulso = 1;
        } else if (rbtnSupergaussian.isSelected()) {
            tipoPulso = 10;
        }
        if (rbtnNone.isSelected()) {
            modulacion = 0;
        } else if (rbtnOOK.isSelected()) {
            modulacion = 1;
        }
        //Se corrobora potencia en mW
        if (unidadPotencia == 0 && (txtPotencia.getText().isEmpty() ||
                txtPotencia.getText().compareTo("") == 0 ||
                !txtPotencia.getText().matches("^(?!0\\\\.000)[0-9]+(\\\\.\\\\d{1,3})?$|^[1-9]\\\\d{0,3}(\\\\.\\\\d{1,3})?$"))) {
            System.out.println("Invalid mW power value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid mW power value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPotencia.setText("");
        }
        //Se corrobora potencia en dBm
        else if (unidadPotencia == 1 && (txtPotencia.getText().isEmpty() ||
                txtPotencia.getText().compareTo("") == 0 ||
                !txtPotencia.getText().matches("^-?((3[0-9]|[0-2]?[0-9])(\\\\.\\\\d+)?|40(\\\\.0+)?)$"))) {
            System.out.println("Invalid dBm power value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid dBm power value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPotencia.setText("");
        }
        //Se corrobora Spectral Width
        else if (txtAnchuraEspectro.getText().isEmpty() ||
                txtAnchuraEspectro.getText().compareTo("") == 0 ||
                !txtAnchuraEspectro.getText().matches("[0-9]*?\\d*(\\.\\d+)?")) {
            System.out.println("Invalid spectral width value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid spectral width value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        //Se corrobora Chirp
        else if (txtChirp.getText().isEmpty() ||
                txtChirp.getText().compareTo("") == 0 ||
                !txtChirp.getText().matches("^(0|[1-9]\\d*)(\\.\\d+)?$")) {
            System.out.println("Invalid chirp value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid chirp value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtChirp.setText("");
        }
        //Se corrobora Bitrate
        else if (modulacion == 1 && (txtVelocidad.getText().isEmpty() ||
                txtVelocidad.getText().compareTo("") == 0 ||
                !txtVelocidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?"))) {
            System.out.println("Invalid bitrate value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid bitrate value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtVelocidad.setText("");
        }
        //Se corrobora el span
        else if (modulacion == 1 && (txtSpan.getText().isEmpty() ||
                txtSpan.getText().compareTo("") == 0 ||
                !txtSpan.getText().matches("^[1-9]\\d*$"))) {
            System.out.println("Invalid span value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid span value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtSpan.setText("");
        }
        //Laser
        else if ((tipo == 0 && Double.parseDouble(txtAnchuraEspectro.getText()) <= 0) ||
                (tipo == 0 && Double.parseDouble(txtAnchuraEspectro.getText()) > 1)) {
            System.out.println("\nThe width value must be max 1 nm");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe width value must be max 1 nm", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        //LED
        else if ((tipo == 1 && Double.parseDouble(txtAnchuraEspectro.getText()) < (double) (0.01)) ||
                (tipo == 1 && Double.parseDouble(txtAnchuraEspectro.getText()) > 1)) {
            System.out.println("\nThe width value must be min: 0.01 nm  max: 1.0 nm");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe width value must be min: 0.01 nm  max: 1.0 nm", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        } else {
            //Spectral Width
            anchura = Double.parseDouble(txtAnchuraEspectro.getText());
            //Chirp
            chirp = Double.parseDouble(txtChirp.getText());
            if (modulacion == 1) {
                //BitRate
                velocidad = Double.parseDouble(txtVelocidad.getText());
                //Span
                span = Double.parseDouble(txtSpan.getText());
            }

            Fuente fuente = new Fuente();
            fuente.setIdFuente(idFuente);
            fuente.setNombre("source");
            fuente.setTipo(tipo);
            if (unidadLongitudOnda == 0) {
                longitudOnda = Double.parseDouble(cboxFc.getSelectionModel().getSelectedItem().toString());
            } else if (unidadLongitudOnda == 1) {
                longitudOnda = NmToThz(Double.parseDouble(cboxFc.getSelectionModel().getSelectedItem().toString()));
            }
            fuente.setLongitudOnda(longitudOnda);
            fuente.setUnidadLongitudOnda(unidadLongitudOnda);
            fuente.setIndexLongitudOnda(cboxFc.getSelectionModel().getSelectedIndex());
            if (unidadPotencia == 0) {//Deja la potencia en mW
                potencia = Double.parseDouble(txtPotencia.getText());
            } else {//Pasa la potencia de dBm a mW (unidadPotencia == 1)
                potencia = Math.pow(10, Double.parseDouble(txtPotencia.getText()) / 10);
            }
            fuente.setPotencia(potencia);
            fuente.setUnidadPotencia(unidadPotencia);
            fuente.setAnchura(anchura);
            fuente.setTipoPulso(tipoPulso);
            fuente.setChirp(chirp);
            fuente.setModulacion(modulacion);
            if (modulacion == 1) {
                fuente.setVelocidad(velocidad);
                fuente.setSpan(span);
            }
            fuente.setConectadoEntrada(false);
            fuente.setConectadoSalida(false);
            guardarFuente(fuente);
            idFuente++;
            cerrarVentana(event);
            // Aqui se manda a graficar la señal modulada
            // fuente.setPulso(Math.sqrt(2*potencia),1/(anchura/1e9),longitudOnda*1e12,chirp,tipoPulso);
            fuente.setPulso(Math.sqrt(2 * potencia), anchura * 1e-3, longitudOnda * 1e3, chirp, tipoPulso);
            fuente.graficas();

            // Calcula el buffer de datos con todos los datos previamente especificados
            fuente.calcularBuffer();
        }
    }

    /**
     * Metodo que guarda la fuente en el panel
     *
     * @param fuente Fuente con valores almacenados
     */
    public void guardarFuente(Fuente fuente) {
        fuente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fuente);
        Label dibujo = new Label();
        ElementoGrafico elem = new ElementoGrafico();

        fuente.setPosX(dibujo.getLayoutX());
        fuente.setPosY(dibujo.getLayoutY());
        setPosX(fuente.getPosX());
        setPosY(fuente.getPosY());

        elem.setComponente(fuente);
        elem.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
        dibujo.setText(fuente.getNombre() + "_" + fuente.getIdFuente());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);

        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nSource created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que duplica una fuente
     *
     * @param fuente Fuente a duplicar
     * @param el     Elemento grafico de la fuente a duplicar
     */
    public void duplicarFuente(Fuente fuente, ElementoGrafico el) {
        fuente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fuente);
        Label dibujo = new Label();
        ElementoGrafico elem = new ElementoGrafico();

        fuente.setPosX(dibujo.getLayoutX());
        fuente.setPosY(dibujo.getLayoutY());
        setPosX(fuente.getPosX());
        setPosY(fuente.getPosY());

        elem.setComponente(fuente);
        elem.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
        dibujo.setText(fuente.getNombre() + "_" + fuente.getIdFuente());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        dibujo.setLayoutX(el.getDibujo().getLayoutX() + 35);
        dibujo.setLayoutY(el.getDibujo().getLayoutY() + 20);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);

        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDuplicate source!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo el cual le proporciona eventos a la fuente tales como movimiento,
     * abrir ventana para modificarlo o mostrar un menu de acciones
     *
     * @param elem Elemento grafico de la fuente
     */
    public void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double newX = event.getSceneX();
                double newY = event.getSceneY();
                int j = 0;
                for (int a = 0; a < Pane1.getChildren().size(); a++) {
                    if (Pane1.getChildren().get(a).toString().contains(elem.getDibujo().getText())) {
                        j = a;
                        break;
                    }
                }
                if (outSideParentBoundsX(elem.getDibujo().getLayoutBounds(), newX, newY)) {    //return;
                } else {
                    elem.getDibujo().setLayoutX(Pane1.getChildren().get(j).getLayoutX() + event.getX() + 1);
                }

                if (outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY)) {    //return;
                } else {
                    elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY() + event.getY() + 1);
                }
                if (elem.getComponente().isConectadoSalida() == true) {
                    elem.getComponente().getLinea().setVisible(false);
                    dibujarLinea(elem);
                }
            }
        });
        elem.getDibujo().setOnMouseEntered((MouseEvent event) -> {
            elem.getDibujo().setStyle("-fx-border-color: darkblue;");
            elem.getDibujo().setCursor(Cursor.OPEN_HAND);
        });
        elem.getDibujo().setOnMouseExited((MouseEvent event) -> {
            elem.getDibujo().setStyle("");
        });
        elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    Stage stage1 = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaFuente.fxml"));
                    Parent root = loader.load();
                    VentanaFuenteController fuenteController = (VentanaFuenteController) loader.getController();
                    fuenteController.init(controlador, this.stage, this.Pane1, this.scroll, ventana_principal);
                    fuenteController.init2(elem, fuenteController);
                    fuenteController.btnCrear.setVisible(false);
                    fuenteController.separator.setVisible(true);
                    fuenteController.btnDesconectar.setVisible(true);
                    fuenteController.lblConectarA.setVisible(true);
                    fuenteController.cboxConectarA.setVisible(true);
                    fuenteController.btnModificar.setVisible(true);

                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    stage1.getIcons().add(ico);
                    stage1.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
                    stage1.initModality(Modality.APPLICATION_MODAL);
                    stage1.setScene(scene);
                    stage1.setResizable(false);
                    stage1.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elem);
            }
        });
    }

    /**
     * Metodo que  eventos al seleccionar modulacion,
     * Mostrar u ocultar las opciones si hay modulacion seleccionada
     *
     * @param event
     */
    public void eventosFrecuenciaCentral(ActionEvent event) {
        if (rbtnLaser.isSelected() && rbtnThz.isSelected()) {
            cboxFc.getItems().removeAll(cboxFc.getItems());
            cboxFc.getItems().addAll("228.84920458", "203.80", "201.07", "198.41",
                    "195.81", "193.29", "190.83", "188.43", "186.09");
            cboxFc.getSelectionModel().selectFirst();
        } else if (rbtnLaser.isSelected() && rbtnNm.isSelected()) {
            cboxFc.getItems().removeAll(cboxFc.getItems());
            cboxFc.getItems().addAll("1310", "1471.0130422", "1490.9855175", "1510.9745376",
                    "1531.0375262", "1550.9982824", "1570.9922863", "1591.0017407", "1611.0078887");
            cboxFc.getSelectionModel().selectFirst();
        } else if (rbtnLed.isSelected() && rbtnThz.isSelected()) {
            cboxFc.getItems().removeAll(cboxFc.getItems());
            cboxFc.getItems().addAll("228.84920458", "193.41448903");
            cboxFc.getSelectionModel().selectFirst();
        } else if (rbtnLed.isSelected() && rbtnNm.isSelected()) {
            cboxFc.getItems().removeAll(cboxFc.getItems());
            cboxFc.getItems().addAll("1310", "1550");
            cboxFc.getSelectionModel().selectFirst();
        }
    }

    /**
     * Metodo que proporciona eventos al seleccionar modulacion,
     * Mostrar u ocultar las opciones si hay modulacion seleccionada
     *
     * @param event
     */
    public void eventosModulacion(ActionEvent event) {
        if (rbtnNone.isSelected()) {
            lblVelocidad.setVisible(false);
            txtVelocidad.setVisible(false);
            lblVelocidadU.setVisible(false);
            lblSpan.setVisible(false);
            txtSpan.setVisible(false);
            lblSpanS.setVisible(false);
        } else if (rbtnOOK.isSelected()) {
            lblVelocidad.setVisible(true);
            txtVelocidad.setVisible(true);
            lblVelocidadU.setVisible(true);
            lblSpan.setVisible(true);
            txtSpan.setVisible(true);
            lblSpanS.setVisible(true);
        }
    }

    /**
     * Metodo el cual muestra un menu de acciones para duplicar, eliminar o
     * ver propiedades de la fuente
     *
     * @param dibujo Elemento grafico de la fuente
     */
    public void mostrarMenu(ElementoGrafico dibujo) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e -> {
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (dibujo.getId() == controlador.getElementos().get(elemento).getId()) {
                    Fuente fuenteAux = new Fuente();
                    Fuente fuenteAux1 = (Fuente) controlador.getElementos().get(elemento);
                    fuenteAux.setIdFuente(idFuente);
                    fuenteAux.setNombre("source");
                    fuenteAux.setTipo(fuenteAux1.getTipo());
                    fuenteAux.setLongitudOnda(fuenteAux1.getLongitudOnda());
                    fuenteAux.setUnidadLongitudOnda(fuenteAux1.getUnidadLongitudOnda());
                    fuenteAux.setIndexLongitudOnda(fuenteAux1.getIndexLongitudOnda());
                    fuenteAux.setPotencia(fuenteAux1.getPotencia());
                    fuenteAux.setUnidadPotencia(fuenteAux1.getUnidadPotencia());
                    fuenteAux.setAnchura(fuenteAux1.getAnchura());
                    fuenteAux.setTipoPulso(fuenteAux1.getTipoPulso());
                    fuenteAux.setChirp(fuenteAux1.getChirp());
                    fuenteAux.setModulacion(fuenteAux1.getModulacion());
                    if (fuenteAux1.getModulacion() == 1) {
                        fuenteAux.setSpan(fuenteAux1.getSpan());
                        fuenteAux.setVelocidad(fuenteAux1.getVelocidad());
                    }
                    fuenteAux.setConectadoEntrada(false);
                    fuenteAux.setConectadoSalida(false);
                    fuenteAux.setPulso(fuenteAux1.getA0(), fuenteAux1.getT0(), fuenteAux1.getFc(), fuenteAux1.getC(), fuenteAux1.getM());
                    duplicarFuente(fuenteAux, dibujo);
                    idFuente++;
                    break;
                }
            }
        });

        /**Eliminar*/
        menuItem3.setOnAction(e -> {
            if (dibujo.getComponente().isConectadoSalida() == true) {
                for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                    if (dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())) {
                        Componente aux = controlador.getElementos().get(elemento);
                        aux.setConectadoEntrada(false);
                        aux.setElementoConectadoEntrada("");
                        dibujo.getComponente().getLinea().setVisible(false);
                    }
                }
            }
            if (dibujo.getComponente().isConectadoEntrada() == true) {
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
                    Fuente aux = (Fuente) controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux);
                }
            }
            dibujo.getDibujo().setVisible(false);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nRemoved source!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
        });

        /*Propiedades*/
        menuItem4.setOnAction(e -> {
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (dibujo.getId() == controlador.getElementos().get(elemento).getId()) {
                    Stage s = new Stage(StageStyle.DECORATED);
                    Image ico = new Image("images/dibujo_fuente.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Fuente aux = (Fuente) controlador.getElementos().get(elemento);
                    String PrTipo, PrTipoPulso;
                    Label label;
                    if (aux.getModulacion() == 0) {
                        if (aux.getTipo() == 1) {
                            PrTipo = "LED";
                        } else {
                            PrTipo = "Laser";
                        }
                        if (aux.getTipoPulso() == 1) {
                            PrTipoPulso = "Gaussian";
                        } else {
                            PrTipoPulso = "Supergaussian";
                        }
                        label = new Label("  Name: " + aux.getNombre() +
                                "\n  Id: " + aux.getIdFuente() +
                                "\n  Input: " + aux.getElementoConectadoEntrada() +
                                "\n  Output :" + aux.getElementoConectadoSalida() +
                                "\n  Type: " + PrTipo +
                                "\n  Wavelenght: " + aux.getLongitudOnda() + " THz" +
                                "\n  Potency: " + aux.getPotencia() + " mW" +
                                "\n  Spectral Width: " + aux.getAnchura() + " nm" +
                                "\n  Pulse Type: " + PrTipoPulso +
                                "\n  Chirp: " + aux.getChirp() +
                                "\n  Modulation: None" +
                                "\n  ---------------Pulse---------------" +
                                "\n  A0: " + aux.getA0() + "\n  C: " + aux.getC() +
                                "\n  T0: " + aux.getT0() + "\n  Fc: " + aux.getFc() +
                                "\n  M: " + aux.getM());
                        Scene scene = new Scene(label, 225, 375);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    } else if (aux.getModulacion() == 1) {
                        if (aux.getTipo() == 1) {
                            PrTipo = "LED";
                        } else {
                            PrTipo = "Laser";
                        }
                        if (aux.getTipoPulso() == 1) {
                            PrTipoPulso = "Gaussian";
                        } else {
                            PrTipoPulso = "Supergaussian";
                        }
                        label = new Label("  Name: " + aux.getNombre() +
                                "\n  Id: " + aux.getIdFuente() +
                                "\n  Input: " + aux.getElementoConectadoEntrada() +
                                "\n  Output :" + aux.getElementoConectadoSalida() +
                                "\n  Type: " + PrTipo +
                                "\n  Wavelenght: " + aux.getLongitudOnda() + " THz" +
                                "\n  Potency: " + aux.getPotencia() + " mW" +
                                "\n  Spectral Width: " + aux.getAnchura() + " nm" +
                                "\n  Pulse Type: " + PrTipoPulso +
                                "\n  Chirp: " + aux.getChirp() +
                                "\n  Modulation: OOK" +
                                "\n  Transmission Speed: " + aux.getVelocidad() + " Gbit/s" +
                                "\n  Span: " + aux.getSpan() + " s" +
                                "\n  ---------------Pulse---------------" +
                                "\n  A0: " + aux.getA0() + "\n  C: " + aux.getC() +
                                "\n  T0: " + aux.getT0() + "\n  Fc: " + aux.getFc() +
                                "\n  M: " + aux.getM());
                        Scene scene = new Scene(label, 225, 375);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                }
            }
        });
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }

    /**
     * Metodo para cerrar la ventana de la fuente
     *
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }

    /**
     * Metodo para desconectar la fuente
     *
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    private void Desconectar(ActionEvent event) {
        for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
            if (fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                Componente comp = controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                break;
            }
        }
        fuenteControl.cboxConectarA.getSelectionModel().select(0);
        if (elemG.getComponente().isConectadoSalida()) {
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected source!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }

    /**
     * Metodo para modificar la fuente
     *
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException
     */
    @FXML
    private void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException, ExcepcionDivideCero {
        Fuente aux = (Fuente) elemG.getComponente();
        int tipo = 0, unidadLongitudOnda = 0, unidadPotencia = 0, tipoPulso = 0, modulacion = 0;
        double longitudOnda = 0, potencia, anchura, chirp, velocidad = 0, span = 0;

        if (rbtnLaser.isSelected()) {
            tipo = 0;
        } else if (rbtnLed.isSelected()) {
            tipo = 1;
        }
        if (rbtnThz.isSelected()) {
            unidadLongitudOnda = 0;
        } else if (rbtnNm.isSelected()) {
            unidadLongitudOnda = 1;
        }
        if (rbtnMw.isSelected()) {
            unidadPotencia = 0;
        } else if (rbtnDbm.isSelected()) {
            unidadPotencia = 1;
        }
        if (rbtnGaussian.isSelected()) {
            tipoPulso = 1;
        } else if (rbtnSupergaussian.isSelected()) {
            tipoPulso = 5;
        }
        if (rbtnNone.isSelected()) {
            modulacion = 0;
        } else if (rbtnOOK.isSelected()) {
            modulacion = 1;
        }
        if ((fuenteControl.cboxConectarA.getSelectionModel().getSelectedIndex()) == 0) {
            Desconectar(event);
        } else {
            if (aux.isConectadoSalida()) {
                elemG.getComponente().getLinea().setVisible(false);
            }
            aux.setConectadoSalida(true);

            for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
                if (fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                    ElementoGrafico eg = controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(eg.getDibujo().getText());
                    aux.setConectadoSalida(true);
                    eg.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    eg.getComponente().setConectadoEntrada(true);
                    break;
                }
            }
            dibujarLinea(elemG);
            if (elemG.getComponente().getSeñalSalida() != null) {
                elemG.getComponente().setSeñalSalida(null);
                ventana_principal.elemConected(elemG.getComponente(), false);
            }
        }
        //Se corrobora potencia en mW
        if (unidadPotencia == 0 && (txtPotencia.getText().isEmpty() ||
                txtPotencia.getText().compareTo("") == 0 ||
                !txtPotencia.getText().matches("^(?!0\\\\.000)[0-9]+(\\\\.\\\\d{1,3})?$|^[1-9]\\\\d{0,3}(\\\\.\\\\d{1,3})?$"))) {
            System.out.println("Invalid mW power value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid mW power value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPotencia.setText("");
        }
        //Se corrobora potencia en dBm
        else if (unidadPotencia == 1 && (txtPotencia.getText().isEmpty() ||
                txtPotencia.getText().compareTo("") == 0 ||
                !txtPotencia.getText().matches("^-?((3[0-9]|[0-2]?[0-9])(\\\\.\\\\d+)?|40(\\\\.0+)?)$"))) {
            System.out.println("Invalid dBm power value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid dBm power value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPotencia.setText("");
        }
        //Se corrobora Spectral Width
        else if (txtAnchuraEspectro.getText().isEmpty() ||
                txtAnchuraEspectro.getText().compareTo("") == 0 ||
                !txtAnchuraEspectro.getText().matches("[0-9]*?\\d*(\\.\\d+)?")) {
            System.out.println("Invalid spectral width value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid spectral width value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        //Se corrobora Chirp
        else if (txtChirp.getText().isEmpty() ||
                txtChirp.getText().compareTo("") == 0 ||
                !txtChirp.getText().matches("^(0|[1-9]\\d*)(\\.\\d+)?$")) {
            System.out.println("Invalid chirp value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid chirp value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtChirp.setText("");
        }
        //Se corrobora Bitrate
        else if (modulacion == 1 && (txtVelocidad.getText().isEmpty() ||
                txtVelocidad.getText().compareTo("") == 0 ||
                !txtVelocidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?"))) {
            System.out.println("Invalid bitrate value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid bitrate value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtVelocidad.setText("");
        }
        //Se corrobora el span
        else if (modulacion == 1 && (txtSpan.getText().isEmpty() ||
                txtSpan.getText().compareTo("") == 0 ||
                !txtSpan.getText().matches("^[1-9]\\d*$"))) {
            System.out.println("Invalid span value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid span value", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtSpan.setText("");
        }
        //Laser
        else if ((tipo == 0 && Double.parseDouble(txtAnchuraEspectro.getText()) <= 0) ||
                (tipo == 0 && Double.parseDouble(txtAnchuraEspectro.getText()) > 1)) {
            System.out.println("\nThe width value must be max 1 nm");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe width value must be max 1 nm", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        //LED
        else if ((tipo == 1 && Double.parseDouble(txtAnchuraEspectro.getText()) < (double) (0.01)) ||
                (tipo == 1 && Double.parseDouble(txtAnchuraEspectro.getText()) > 1)) {
            System.out.println("\nThe width value must be min: 0.01 nm  max: 1.0 nm");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe width value must be min: 0.01 nm  max: 1.0 nm", aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        } else {
            //Spectral Width
            anchura = Double.parseDouble(txtAnchuraEspectro.getText());
            //Chirp
            chirp = Double.parseDouble(txtChirp.getText());
            if (modulacion == 1) {
                //BitRate
                velocidad = Double.parseDouble(txtVelocidad.getText());
                //Span
                span = Double.parseDouble(txtSpan.getText());
            }
            aux.setNombre("source");
            aux.setTipo(tipo);
            if (unidadLongitudOnda == 0) {
                longitudOnda = Double.parseDouble(cboxFc.getSelectionModel().getSelectedItem().toString());
            } else if (unidadLongitudOnda == 1) {
                longitudOnda = NmToThz(Double.parseDouble(cboxFc.getSelectionModel().getSelectedItem().toString()));
            }
            aux.setLongitudOnda(longitudOnda);
            aux.setUnidadLongitudOnda(unidadLongitudOnda);
            aux.setIndexLongitudOnda(cboxFc.getSelectionModel().getSelectedIndex());
            if (unidadPotencia == 0) {//Deja la potencia en mW
                potencia = Double.parseDouble(txtPotencia.getText());
            } else {//Pasa la potencia de dBm a mW (unidadPotencia == 1)
                potencia = Math.pow(10, Double.parseDouble(txtPotencia.getText()) / 10);
            }
            aux.setPotencia(potencia);
            aux.setUnidadPotencia(unidadPotencia);
            aux.setAnchura(anchura);
            aux.setTipoPulso(tipoPulso);
            aux.setChirp(chirp);
            aux.setModulacion(modulacion);
            if (modulacion == 1) {
                aux.setSpan(span);
                aux.setVelocidad(velocidad);
            }
            //aux.setPulso(Math.sqrt(2*potencia),1/(anchura/1e9),longitudOnda*1e12,chirp,tipoPulso);
            aux.setPulso(Math.sqrt(2 * potencia), anchura * 1e-3, longitudOnda * 1e3, chirp, tipoPulso);
            aux.graficas();
            cerrarVentana(event);

            VentanaPrincipal.btnStart = false;
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified source!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    /**
     * Metodo para mostrar informacion de la fuente
     *
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void configurarPulso(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaPulso.fxml"));
            Parent root = loader.load();
            VentanaPulsoController pulControl = loader.getController();

            Stage st = new Stage(StageStyle.UTILITY);
            st.setTitle("Signal Information");
            st.setScene(new Scene(root));
            st.setResizable(false);

            // Guardar la referencia al Stage en el controlador de la ventana
            pulControl.setStage(st);

            st.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param ventana     Ventana principal
     */
    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1,
                     ScrollPane scroll, VentanaPrincipal ventana) {
        this.controlador = controlador;
        this.stage = stage;
        this.Pane1 = Pane1;
        this.scroll = scroll;
        this.ventana_principal = ventana;
    }

    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento
     *
     * @param elem             Elemento grafico
     * @param fuenteController Controlador de ls fuente
     */
    public void init2(ElementoGrafico elem, VentanaFuenteController fuenteController) {
        this.elemG = elem;
        this.fuenteControl = fuenteController;

        if (elemG.getComponente().isConectadoSalida() == true) {
            fuenteControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        } else {
            fuenteControl.cboxConectarA.getItems().add("Desconected");
            fuenteControl.cboxConectarA.getSelectionModel().select(0);
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if ("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "spectrum".equals(controlador.getElementos().get(elemento).getNombre())
                        ||
                        "demux".equals(controlador.getElementos().get(elemento).getNombre())) {
                    if (!controlador.getElementos().get(elemento).isConectadoEntrada()) {
                        fuenteControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
            }
        }
        for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
            if (elem.getId() == controlador.getElementos().get(elemento).getId()) {
                Fuente fue = (Fuente) controlador.getElementos().get(elemento);
                if (fue.getTipo() == 0) {
                    fuenteControl.rbtnLaser.setSelected(true);
                } else if (fue.getTipo() == 1) {
                    fuenteControl.rbtnLed.setSelected(true);
                }
                if (fue.getUnidadLongitudOnda() == 0) {
                    fuenteControl.rbtnThz.setSelected(true);
                } else if (fue.getUnidadLongitudOnda() == 1) {
                    fuenteControl.rbtnNm.setSelected(true);
                }
                eventosFrecuenciaCentral(null);
                fuenteControl.cboxFc.getSelectionModel().select(fue.getIndexLongitudOnda());
                if (fue.getUnidadPotencia() == 0) {//Deja la potencia en mW
                    fuenteControl.rbtnMw.setSelected(true);
                    fuenteControl.txtPotencia.setText(String.valueOf(fue.getPotencia()));
                } else if (fue.getUnidadPotencia() == 1) {//Pasa la potencia de mW a dBm (unidadPotencia == 1)
                    fuenteControl.rbtnDbm.setSelected(true);
                    fuenteControl.txtPotencia.setText(String.valueOf((int) (10 * Math.log10(fue.getPotencia()))));
                }
                fuenteControl.txtAnchuraEspectro.setText(String.valueOf(fue.getAnchura()));
                if (fue.getTipoPulso() == 1) {
                    fuenteControl.rbtnGaussian.setSelected(true);
                } else {
                    fuenteControl.rbtnSupergaussian.setSelected(true);
                }
                fuenteControl.txtChirp.setText(String.valueOf(fue.getChirp()));
                if (fue.getModulacion() == 0) {
                    fuenteControl.rbtnNone.setSelected(true);
                    lblVelocidad.setVisible(false);
                    txtVelocidad.setVisible(false);
                    lblVelocidadU.setVisible(false);
                    lblSpan.setVisible(false);
                    txtSpan.setVisible(false);
                    lblSpanS.setVisible(false);
                } else if (fue.getModulacion() == 1) {
                    fuenteControl.rbtnOOK.setSelected(true);
                    fuenteControl.txtVelocidad.setText(String.valueOf(fue.getVelocidad()));
                    fuenteControl.txtSpan.setText(String.valueOf((int) fue.getSpan()));
                    lblVelocidad.setVisible(true);
                    txtVelocidad.setVisible(true);
                    lblVelocidadU.setVisible(true);
                    lblSpan.setVisible(true);
                    txtSpan.setVisible(true);
                    lblSpanS.setVisible(true);
                }
            }
        }
    }

    /**
     * Metodo que permite visualizar la conexion hacia delante de la fuente
     * con otro elemento
     *
     * @param elemG Elemento grafico de la fuente
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line = new Line();
        line.setStartX(elemG.getDibujo().getLayoutX() + 45);
        line.setStartY(elemG.getDibujo().getLayoutY() + 7);
        ElementoGrafico aux = new ElementoGrafico();
        for (int it = 0; it < controlador.getDibujos().size(); it++) {
            if (elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                aux = controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY() + 8);
        line.setVisible(true);
        Pane1.getChildren().add(line);
        elemG.getComponente().setLinea(line);

    }

    /**
     * Metodo que delimita el movimiento en el eje X en el panel para que el
     * elemento grafico no salga del area de trabajo
     */
    private boolean outSideParentBoundsX(Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = Pane1.getLayoutBounds();
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
        Bounds parentBounds = Pane1.getLayoutBounds();
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

}
