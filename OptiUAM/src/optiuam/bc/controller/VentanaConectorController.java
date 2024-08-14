
package optiuam.bc.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
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
import javafx.stage.StageStyle;
import optiuam.bc.model.Componente;
import optiuam.bc.model.Conector;
import optiuam.bc.model.Demultiplexor;
import optiuam.bc.model.ElementoGrafico;
import optiuam.bc.model.Multiplexor;
import optiuam.bc.model.Splitter;

/**
 * Clase VentanaConectorController la cual se encarga de instanciar un conector
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 * @see ControladorGeneral
 */
public class VentanaConectorController extends ControladorGeneral implements Initializable {

    /**
     * Identificador del conector
     */
    static int idConector = 0;
    /**
     * Controlador del simulador
     */
    ControladorGeneral controlador;
    /**
     * Escenario en el cual se agregaran los objetos creados
     */
    Stage stage;
    /**
     * Elemento grafico del conector
     */
    ElementoGrafico elemG;
    /**
     * Controlador del conector
     */
    VentanaConectorController conectorControl;
    /**
     * Posicion del conector en el eje X
     */
    static double posX;
    /**
     * Posicion del conector en el eje Y
     */
    static double posY;
    /**
     * Longitudes de onda del multiplexor
     */
    static String longitudesOnda[] = {"1470", "1490", "1510", "1530", "1550",
            "1570", "1590", "1610"};

    /**
     * Panel para agregar objetos
     */
    @FXML
    private Pane Pane1;
    /**
     * Etiqueta de la lista desplegable de elementos disponibles para conectar
     * el conector
     */
    @FXML
    Label lblConectarA;
    /**
     * RadioButton para la longitud de onda de 1310 nm
     */
    @FXML
    RadioButton rbtn1310;
    /**
     * RadioButton para la longitud de onda de 1550 nm
     */
    @FXML
    RadioButton rbtn1550;
    /**
     * RadioButton para el modo Monomodo del conector
     */
    @FXML
    RadioButton rbtnMono;
    /**
     * RadioButton para el modo Multimodo del conector
     */
    @FXML
    RadioButton rbtnMulti;
    /**
     * Caja de texto para ingresar la perdida de insercion del conector
     */
    @FXML
    TextField txtPerdida;
    /**
     * Boton para desconectar el conector
     */
    @FXML
    Button btnDesconectar;
    /**
     * Boton para cancelar la operacion
     */
    @FXML
    Button btnCancelar;
    /**
     * Boton para crear un conector
     */
    @FXML
    Button btnCrear;
    /**
     * Boton para modificar el conector
     */
    @FXML
    Button btnModificar;
    /**
     * Lista desplegable de elementos disponibles para conectar el conector
     */
    @FXML
    ComboBox cboxConectarA;
    /**
     * Espacio en el cual el usuario puede desplazarse
     */
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo que muestra el identificador del conector
     *
     * @return idConector
     */
    public static int getIdConector() {
        return idConector;
    }

    /**
     * Metodo que modifica el identificador del conector
     *
     * @param idConector Identificador del conector
     */
    public static void setIdConector(int idConector) {
        VentanaConectorController.idConector = idConector;
    }

    /**
     * Metodo que muestra la posicion del conector en el eje X
     *
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion del conector en el eje X
     *
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaConectorController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion del conector en el eje Y
     *
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion del conector en el eje Y
     *
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaConectorController.posY = posY;
    }

    /**
     * Metodo el cual inicializa la ventana del conector
     *
     * @param url La ubicacion utilizada para resolver rutas relativas para
     *            el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb  Los recursos utilizados para localizar el objeto raiz, o nulo
     *            si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip perdida = new Tooltip();
        perdida.setText("Monomode: The loss must be" + " min: 0" + " max: 0.5"
                + "\nMultimode: The loss must be" + " min: 0" + " max: 1.0");
        txtPerdida.setTooltip(perdida);

        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }

    /**
     * Metodo el cual captura los datos obtenidos de la ventana del conector y
     * crea uno
     *
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona
     *                                                     diferentes excepciones lanzadas bajo el paquete java lang
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException {
        int modo = 0, longitudOnda = 0;
        double perdidaInsercion, perdidaMax = 0.5;

        if (rbtnMono.isSelected()) {
            modo = 0;
        } else if (rbtnMulti.isSelected()) {
            perdidaMax = 1.0;
            modo = 1;
        }
        if (rbtn1310.isSelected()) {
            longitudOnda = 1310;
        } else if (rbtn1550.isSelected()) {
            longitudOnda = 1550;
        }
        if (txtPerdida.getText().isEmpty() || txtPerdida.getText().compareTo("") == 0
                || !txtPerdida.getText().matches("[0-9]*?\\d*(\\.\\d+)?")) {
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        } else if (Double.parseDouble(txtPerdida.getText()) > perdidaMax
                || Double.parseDouble(txtPerdida.getText()) < 0) {
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + perdidaMax,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        } else {
            perdidaInsercion = Double.parseDouble(txtPerdida.getText());
            Conector con = new Conector();
            con.setConectadoEntrada(false);
            con.setConectadoSalida(false);
            con.setIdConector(idConector);
            con.setLongitudOnda(longitudOnda);
            con.setNombre("connector");
            con.setPerdidaInsercion(perdidaInsercion);
            con.setModo(modo);
            guardarConector(con);
            idConector++;
            cerrarVentana(event);
        }
    }

    /**
     * Metodo que guarda el conector en el panel
     *
     * @param conector Conector con valores almacenados
     */
    public void guardarConector(Conector conector) {
        conector.setId(controlador.getContadorElemento());
        controlador.getElementos().add(conector);
        Label dibujo = new Label();
        ElementoGrafico elem = new ElementoGrafico();

        conector.setPosX(dibujo.getLayoutX());
        conector.setPosY(dibujo.getLayoutY());
        setPosX(conector.getPosX());
        setPosY(conector.getPosY());

        elem.setComponente(conector);
        elem.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
        dibujo.setText(conector.getNombre() + "_" + conector.getIdConector());

        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);

        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
        conector.setNombreid(conector.getNombre() + "_" + conector.getIdConector());
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nConnector created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que duplica un conector
     *
     * @param conector Conector a duplicar
     * @param elemGraf Elemento grafico del conector a duplicar
     */
    public void duplicarConector(Conector conector, ElementoGrafico elemGraf) {
        conector.setId(controlador.getContadorElemento());
        controlador.getElementos().add(conector);
        Label dibujo = new Label();
        ElementoGrafico elem = new ElementoGrafico();

        conector.setPosX(dibujo.getLayoutX());
        conector.setPosY(dibujo.getLayoutY());
        setPosX(conector.getPosX());
        setPosY(conector.getPosY());

        elem.setComponente(conector);
        elem.setId(controlador.getContadorElemento());

        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
        dibujo.setText(conector.getNombre() + "_" + conector.getIdConector());
        conector.setNombreid(conector.getNombre() + "_" + conector.getIdConector());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        dibujo.setLayoutX(elemGraf.getDibujo().getLayoutX() + 35);
        dibujo.setLayoutY(elemGraf.getDibujo().getLayoutY() + 20);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDuplicate connector!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo el cual le proporciona eventos al conector tales como movimiento,
     * abrir ventana para modificarlo o mostrar un menu de acciones
     *
     * @param elem Elemento grafico del conector
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
                if (elem.getComponente().isConectadoEntrada()) {
                    ElementoGrafico aux;
                    for (int it = 0; it < controlador.getDibujos().size(); it++) {
                        if (elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                            aux = controlador.getDibujos().get(it);
                            /*Aqui es que ya reviso que esta conectado a un elemento, se va a revisar que sea un splitter
                            y no esta conectado a su puerto dafault*/
                            if ((elem.getComponente().getElementoConectadoEntrada().contains("splitter") || elem.getComponente().getElementoConectadoEntrada().contains("demux")) && !aux.getComponente().getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                if (elem.getComponente().getElementoConectadoEntrada().contains("splitter")) {
                                    Splitter sp = (Splitter) aux.getComponente();
                                    for (int on = 0; on < sp.getConexiones().size(); on++) {
                                        if (sp.getConexiones().get(on).isConectadoSalida()) {
                                            if (sp.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                                dibujarLineaAtrasSplitter(elem, aux, on);
                                            }
                                        }
                                    }
                                } else {
                                    Demultiplexor sp = (Demultiplexor) aux.getComponente();
                                    for (int on = 0; on < sp.getConexiones().size(); on++) {
                                        if (sp.getConexiones().get(on).isConectadoSalida()) {
                                            if (sp.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                                dibujarLineaAtrasSplitter(elem, aux, on);
                                            }
                                        }
                                    }
                                }
                            } else {
                                aux.getComponente().getLinea().setVisible(false);
                                dibujarLineaAtras(elem);
                            }
                        }
                    }
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaConector.fxml"));
                    Parent root = loader.load();
                    VentanaConectorController conectorController = (VentanaConectorController) loader.getController();
                    /*Se necesito usar otro init (init2) de forma que el controller sepa cual es el elemento
                    con el que se esta trabajando ademas de que se manda el mismo controller para 
                    iniciar con los valores del elemento mandado.*/
                    conectorController.init(controlador, stage, Pane1, scroll, ventana_principal);
                    conectorController.init2(controlador, stage, Pane1, elem, conectorController);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage stage1 = new Stage();
                    stage1.getIcons().add(ico);
                    stage1.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
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
     * Metodo el cual muestra un menu de acciones para duplicar, eliminar o
     * ver propiedades del conector
     *
     * @param dibujo Elemento grafico del conector
     */
    public void mostrarMenu(ElementoGrafico dibujo) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicate");
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e -> {
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (dibujo.getId() == controlador.getElementos().get(elemento).getId()) {
                    Conector aux = new Conector();
                    Conector aux1 = (Conector) controlador.getElementos().get(elemento);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setIdConector(idConector);
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setModo(aux1.getModo());
                    aux.setNombre("connector");
                    aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    duplicarConector(aux, dibujo);
                    System.out.println(aux);
                    idConector++;
                    break;
                }
            }
        });

        /*Eliminar*/
        menuItem3.setOnAction(e -> {
            if (dibujo.getComponente().isConectadoSalida() == true) {
                for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                    if (dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())) {
                        Componente aux = controlador.getElementos().get(elemento);
                        System.out.println();
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
                    Conector aux = (Conector) controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux);
                }
            }
            dibujo.getDibujo().setVisible(false);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nRemoved connector!",
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
                    Image ico = new Image("images/dibujo_conectorR.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Conector aux = (Conector) controlador.getElementos().get(elemento);
                    String mode;
                    Label label;
                    if (aux.getModo() == 0) {
                        mode = "Monomode";
                        label = new Label("  Name: " + aux.getNombre() +
                                "\n  Id: " + aux.getIdConector() +
                                "\n  Input: " + aux.getElementoConectadoEntrada() +
                                "\n  Output :" + aux.getElementoConectadoSalida() +
                                "\n  Wavelenght: " + aux.getLongitudOnda() + " nm" +
                                "\n  Mode: " + mode +
                                "\n  Insertion Loss: " + aux.getPerdidaInsercion() + " dB");
                        Scene scene = new Scene(label, 190, 130);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    } else if (aux.getModo() == 1) {
                        mode = "Multimode";
                        label = new Label("  Name: " + aux.getNombre() +
                                "\n  Id: " + aux.getIdConector() +
                                "\n  Input: " + aux.getElementoConectadoEntrada() +
                                "\n  Output :" + aux.getElementoConectadoSalida() +
                                "\n  Wavelenght: " + aux.getLongitudOnda() + " nm" +
                                "\n  Mode: " + mode +
                                "\n  Insertion Loss: " + aux.getPerdidaInsercion() + " dB");
                        Scene scene = new Scene(label, 190, 130);
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
     * Metodo para cerrar la ventana del conector
     *
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }

    /**
     * Metodo para desconectar el conector
     *
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event) {
        for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
            if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().startsWith(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                Componente comp = controlador.getElementos().get(elemento2);
                if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().startsWith("mux") &&
                        conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().endsWith("input1")) {
                    comp.setConectadoEntrada(false);
                    comp.setElementoConectadoEntrada("");
                    break;
                } else if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().startsWith("mux")) {
                    Multiplexor m = (Multiplexor) comp;
                    for (int j = 0; j < m.getEntradas(); j++) {
                        if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().endsWith(String.valueOf(j + 2))) {
                            m.getConexionEntradas().get(j).setConectadoEntrada(false);
                            m.getConexionEntradas().get(j).setElementoConectadoEntrada("");
                        }
                    }
                } else {
                    comp.setConectadoEntrada(false);
                    comp.setElementoConectadoEntrada("");
                    break;
                }
            }
        }
        conectorControl.cboxConectarA.getSelectionModel().select(0);
        if (elemG.getComponente().isConectadoSalida()) {
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected connector!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }

    /**
     * Metodo para modificar el conector
     *
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona
     *                                                     diferentes excepciones lanzadas bajo el paquete java lang
     */
    @FXML
    public void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException {
        Conector aux = (Conector) elemG.getComponente();
        int modo = 0, longitudOnda = 0;
        double perdidaInsercion, perdidaMax = 0.5;

        if (rbtnMono.isSelected()) {
            modo = 0;
        } else if (rbtnMulti.isSelected()) {
            perdidaMax = 1.0;
            modo = 1;
        }
        if (rbtn1310.isSelected()) {
            longitudOnda = 1310;
        } else if (rbtn1550.isSelected()) {
            longitudOnda = 1550;
        }
        if ((conectorControl.cboxConectarA.getSelectionModel().getSelectedIndex()) == 0) {
            if (elemG.getComponente().isConectadoSalida()) {
                Desconectar(event);
            }
        } else {
            if (aux.isConectadoSalida()) {
                elemG.getComponente().getLinea().setVisible(false);
            }
            aux.setConectadoSalida(true);

            for (int elemento2 = 0; elemento2 < controlador.getDibujos().size(); elemento2++) {
                if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().startsWith(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                    ElementoGrafico eg = controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
                    aux.setConectadoSalida(true);
                    if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().startsWith("mux")
                            && conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().endsWith("input1")) {
                        eg.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                        eg.getComponente().setConectadoEntrada(true);
                    } else if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().startsWith("mux")) {
                        Multiplexor m = (Multiplexor) controlador.getElementos().get(elemento2);
                        for (int j = 0; j < m.getEntradas(); j++) {
                            if (conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().endsWith(String.valueOf(j + 2))) {
                                m.getConexionEntradas().get(j).setConectadoEntrada(true);
                                m.getConexionEntradas().get(j).setElementoConectadoEntrada(elemG.getDibujo().getText());
                            }
                        }
                    } else {
                        eg.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                        eg.getComponente().setConectadoEntrada(true);
                    }
                    if (elemG.getComponente().getSeñalEntrada() != null) {
                        elemG.getComponente().setSeñalSalida(null);
                        ventana_principal.elemConected(aux, false);
                    }
                }
            }
            dibujarLinea(elemG);
        }
        if (txtPerdida.getText().isEmpty() || txtPerdida.getText().compareTo("") == 0
                || !txtPerdida.getText().matches("[0-9]*?\\d*(\\.\\d+)?")) {
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        } else if (Double.parseDouble(txtPerdida.getText()) > perdidaMax
                || Double.parseDouble(txtPerdida.getText()) < 0) {
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + perdidaMax,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        } else {
            perdidaInsercion = Double.parseDouble(txtPerdida.getText());
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("connector");
            aux.setPerdidaInsercion(perdidaInsercion);
            aux.setModo(modo);
            cerrarVentana(event);
            if (elemG.getComponente().getSeñalEntrada() != null) {
                elemG.getComponente().setSeñalSalida(null);
                ventana_principal.elemConected(aux, true);
            }
            VentanaPrincipal.btnStart = false;
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified connector!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
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
     * @param controlador        Controlador del simulador
     * @param stage              Escenario en el cual se agregan los objetos creados
     * @param Pane1              Panel para agregar objetos
     * @param elem               Elemento grafico
     * @param conectorController Controlador del conector
     */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,
                      ElementoGrafico elem, VentanaConectorController conectorController) {
        this.elemG = elem;
        this.conectorControl = conectorController;
        this.controlador = controlador;
        this.stage = stage;
        this.Pane1 = Pane1;
        conectorController.btnCrear.setVisible(false);
        conectorController.btnDesconectar.setVisible(true);
        conectorController.lblConectarA.setVisible(true);
        conectorController.cboxConectarA.setVisible(true);
        conectorController.btnModificar.setVisible(true);

        if (elemG.getComponente().isConectadoSalida() == true) {
            if (elemG.getComponente().getElementoConectadoSalida().startsWith("mux")) {
                conectorControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
            } else {
                conectorControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
            }
        } else {
            conectorControl.cboxConectarA.getItems().add("Desconected");
            conectorControl.cboxConectarA.getSelectionModel().select(0);
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (elem.getComponente().getElementoConectadoEntrada().contains("source")) {
                    if ("fiber".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "spectrum".equals(controlador.getElementos().get(elemento).getNombre())) {
                        if (!controlador.getElementos().get(elemento).isConectadoEntrada()) {
                            conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                        }
                    }
                } else {
                    if ("fiber".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "splitter".contains(controlador.getElementos().get(elemento).getNombre()) ||
                            "power".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "spectrum".equals(controlador.getElementos().get(elemento).getNombre())) {
                        if (!controlador.getElementos().get(elemento).isConectadoEntrada()) {
                            conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                        }
                    }
                    if ("mux".equals(controlador.getElementos().get(elemento).getNombre())) {
                        Multiplexor mAux = (Multiplexor) controlador.getElementos().get(elemento);
                        switch (mAux.getEntradas()) {
                            case 2:
                                int n = 3;
                                if (!controlador.getElementos().get(elemento).isConectadoEntrada()) {
                                    conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText() + "_W_" + longitudesOnda[n] + "_input1");
                                }
                                for (int m = 0; m < mAux.getEntradas() - 1; m++) {
                                    if (!mAux.getConexionEntradas().get(m).isConectadoEntrada()) {
                                        String puertodisponible = "_W_" + longitudesOnda[4] + "_input" + String.valueOf(m + 2);
                                        conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText() + puertodisponible);
                                    }
                                }
                                break;
                            case 4:
                                int n1 = 3;
                                if (!controlador.getElementos().get(elemento).isConectadoEntrada()) {
                                    conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText() + "_W_" + longitudesOnda[n1 - 1] + "_input1");
                                }
                                for (int m = 0; m < mAux.getEntradas() - 1; m++) {
                                    if (!mAux.getConexionEntradas().get(m).isConectadoEntrada()) {
                                        String puertodisponible = "_W_" + longitudesOnda[n1] + "_input" + String.valueOf(m + 2);
                                        conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText() + puertodisponible);
                                    }
                                    n1++;
                                }
                                break;
                            case 8:
                                int n2 = 1;
                                if (!controlador.getElementos().get(elemento).isConectadoEntrada()) {
                                    conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText() + "_W_" + longitudesOnda[n2 - 1] + "_input1");
                                }
                                for (int m = 0; m < mAux.getEntradas() - 1; m++) {
                                    if (!mAux.getConexionEntradas().get(m).isConectadoEntrada()) {
                                        String puertodisponible = "_W_" + longitudesOnda[n2] + "_input" + String.valueOf(m + 2);
                                        conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText() + puertodisponible);
                                    }
                                    n2++;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
            if (elem.getId() == controlador.getElementos().get(elemento).getId()) {
                Conector con = (Conector) controlador.getElementos().get(elemento);
                if (con.getModo() == 0) {
                    conectorControl.rbtnMono.setSelected(true);
                } else if (con.getModo() == 1) {
                    conectorControl.rbtnMulti.setSelected(true);
                }
                if (con.getLongitudOnda() == 1310) {
                    conectorControl.rbtn1310.setSelected(true);
                } else if (con.getLongitudOnda() == 1550) {
                    conectorControl.rbtn1550.setSelected(true);
                }
                conectorControl.txtPerdida.setText(String.valueOf(con.getPerdidaInsercion()));
            }
        }
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
        Pane1.getChildren().add(line);
        elemG.getComponente().setLinea(line);
    }

    /**
     * Metodo que permite visualizar la conexion hacia atras del divisor optico
     * o demultiplexor con el conector
     *
     * @param elem   Elemento grafico del conector
     * @param aux    Elemento grafico del divisor optico
     * @param puerto Puerto salida del divisor optico
     */
    public void dibujarLineaAtrasSplitter(ElementoGrafico elem, ElementoGrafico aux, int puerto) {
        Line line = new Line();
        if ("splitter".equals(aux.getComponente().getNombre())) {
            Splitter sptt = (Splitter) aux.getComponente();
            sptt.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);

            switch (sptt.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX() + 50);
                    line.setStartY(aux.getDibujo().getLayoutY() + (24 + (5 * (puerto + 1))));
                    break;
                case 4:
                    line.setStartX(aux.getDibujo().getLayoutX() + 50);
                    line.setStartY(aux.getDibujo().getLayoutY() + (18 + (5 * (puerto + 1))));
                    break;
                case 8:
                    line.setStartX(aux.getDibujo().getLayoutX() + 80);
                    line.setStartY(aux.getDibujo().getLayoutY() + (10 + (9 * (puerto + 1))));
                    break;
                default:
                    break;
            }
            line.setEndX(elem.getDibujo().getLayoutX() + 1);
            line.setEndY(elem.getDibujo().getLayoutY() + 7);
            line.setVisible(true);
            Pane1.getChildren().add(line);
            sptt.getConexiones().get(puerto).setLinea(line);
        } else {
            Demultiplexor sptt = (Demultiplexor) aux.getComponente();
            sptt.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);

            switch (sptt.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX() + 45);
                    line.setStartY(aux.getDibujo().getLayoutY() + (24 + (10 * (puerto + 1))));
                    break;
                case 4:
                    line.setStartX(aux.getDibujo().getLayoutX() + 45);
                    line.setStartY(aux.getDibujo().getLayoutY() + (18 + (10 * (puerto + 1))));
                    break;
                case 8:
                    line.setStartX(aux.getDibujo().getLayoutX() + 65);
                    line.setStartY(aux.getDibujo().getLayoutY() + (10 + (9 * (puerto + 1))));
                    break;
                default:
                    break;
            }
            line.setEndX(elem.getDibujo().getLayoutX() + 1);
            line.setEndY(elem.getDibujo().getLayoutY() + 7);
            line.setVisible(true);
            Pane1.getChildren().add(line);
            sptt.getConexiones().get(puerto).setLinea(line);
        }
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
                Pane1.getChildren().add(line);
                aux.getComponente().setLinea(line);
            }
        }
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
