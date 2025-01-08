
package optiuam.bc.controller;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import optiuam.bc.model.*;

/**
 * Clase VentanaPrincipal la cual se encarga de proporcionar la funcionalidad
 * al simulador
 *
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 */
public class VentanaPrincipal implements Initializable {

    /**
     * Escenario en el cual se agregaran los objetos creados
     */
    static Stage stage;
    /**
     * Controlador del simulador
     */
    static ControladorGeneral controlador = new ControladorGeneral();
    /**
     * Identificador del medidor de potencia
     */
    static int idEspectro = 0;
    /**
     * Identificador del analizador de espectro
     */
    static int idPotencia = 0;
    /**
     * Identificador de la rejilla de Bragg (FBG)
     */
    static int idFBG = 0;

    static int idOsciloscopio = 0;
    /**
     * Conexion (linea) entre componentes
     */
    static Line linea;
    /**
     * Indica si el boton para actualizar señales fue presionado
     */
    static boolean btnStart;
    /**
     * Icono de la fibra optica
     */
    Image fibraI;
    /**
     * Icono de la fuente optica
     */
    Image fuenteI;
    /**
     * Icono del conector
     */
    Image conectorI;
    /**
     * Icono del divisor optico
     */
    Image splitterI;
    /**
     * Icono del empalme
     */
    Image empalmeI;
    /**
     * Icono del medidor de potencia
     */
    Image potenciaI;
    /**
     * Icono del analizador de espectro
     */
    Image espectroI;
    /**
     * Icono del multiplexor
     */
    Image multiplexorI;
    /**
     * Icono del demultiplexor
     */
    Image demultiplexorI;
    /**
     * Icono de la rejilla de Bragg (FBG)
     */
    Image fbgI;
    /**
     * Fondo del panel de trabajo
     */
    Image fondo;
    /**
     * Icono de osciloscopio
     */
    Image osciloscopio;
    /**
     * Icono de OTDR
     */
    Image OTDR;

    /**
     * Permite visualizar el icono de la fibra
     */
    @FXML
    ImageView viewFibra;
    /**
     * Permite visualizar el icono de la fuente
     */
    @FXML
    ImageView viewFuente;
    /**
     * Permite visualizar el icono del conector
     */
    @FXML
    ImageView viewConector;
    /**
     * Permite visualizar el icono del divisor optico
     */
    @FXML
    ImageView viewSplitter;
    /**
     * Permite visualizar el icono del empalme
     */
    @FXML
    ImageView viewEmpalme;
    /**
     * Permite visualizar el icono del medidor de potencia
     */
    @FXML
    ImageView viewPotencia;
    /**
     * Permite visualizar el icono del analizador de espectro
     */
    @FXML
    ImageView viewEspectro;
    /**
     * Permite visualizar el icono del multiplexor
     */
    @FXML
    ImageView viewMux;
    /**
     * Permite visualizar el icono del demultiplexor
     */
    @FXML
    ImageView viewDemux;
    /**
     * Permite visualizar el icono de la rejilla de Bragg (FBG)
     */
    @FXML
    ImageView viewFBG;
    /**
     * Permite visualizar el icono del osciloscopio
     */
    @FXML
    ImageView viewOsciloscopio;
    /**
     * Permite visualizar el icono del OTDR
     */
    @FXML
    ImageView viewOTDR;
    /**
     * Boton para abrir la ventana de la fibra y crear una
     */
    @FXML
    Button btnFibra;
    /**
     * Boton para abrir la ventana de la fuente y crear una
     */
    @FXML
    Button btnFuente;
    /**
     * Boton para abrir la ventana del conector y crear uno
     */
    @FXML
    Button btnConector;
    /**
     * Boton para abrir la ventana del divisor optico y crear uno
     */
    @FXML
    Button btnSplitter;
    /**
     * Boton para abrir la ventana del empalme y crear uno
     */
    @FXML
    Button btnEmpalme;
    /**
     * Boton para abrir la ventana del medidor de potencia y crear uno
     */
    @FXML
    Button btnPotencia;
    /**
     * Boton para abrir la ventana del analizador de espectro y crear uno
     */
    @FXML
    Button btnEspectro;
    /**
     * Boton para abrir la ventana del multiplexor y crear uno
     */
    @FXML
    Button btnMux;
    /**
     * Boton para abrir la ventana del demultiplexor y crear uno
     */
    @FXML
    Button btnDemux;
    /**
     * Boton para abrir la ventana de la rejilla de Bragg (FBG) y crear una
     */
    @FXML
    Button btnFBG;
    /**
     * Boton para actualizar las señales creadas
     */
    @FXML
    Button btnRun;
    /**
     * Panel para agregar objetos
     */
    @FXML
    public Pane Pane1;
    /**
     * Espacio en el cual el usuario puede desplazarse
     */
    @FXML
    public ScrollPane scroll;
    /**
     * Panel de componentes
     */
    @FXML
    AnchorPane desplegable;
    /**
     * Barra de componentes
     */
    @FXML
    TitledPane componentMenu;
    /**
     * Permtite crear un nuevo trabajo
     */
    @FXML
    MenuItem menuItemNew;
    /**
     * Permite guardar un trabajo
     */
    @FXML
    MenuItem menuItemSave;
    /**
     * Permite abrir un trabajo
     */
    @FXML
    MenuItem menuItemOpen;
    /**
     * Proporciona el documento de ayuda del simulador
     */
    @FXML
    Menu menuHelp;
    /**
     * Proporciona informacion acerca de la elaboracion del proyecto
     */
    @FXML
    Menu menuAbout;

    /**
     * Método que muestra el escenario en el cual se agregaran los objetos
     * creados
     *
     * @return stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Método que modifica el escenario en el cual se agregaran los objetos
     * creados
     *
     * @param stage Escenario en el cual se agregan los componentes creados
     */
    public static void setStage(Stage stage) {
        VentanaPrincipal.stage = stage;
    }

    /**
     * Método que muestra el panel para agregar objetos
     *
     * @return panel
     */
    public Pane getPane1() {
        return Pane1;
    }

    /**
     * Método que muestra el controlador del simulador
     *
     * @return controlador
     */
    public static ControladorGeneral getControlador() {
        return controlador;
    }

    /**
     * Metodo que modifica el controlador del simulador
     *
     * @param controlador Controlador del simulador
     */
    public void setControlador(ControladorGeneral controlador) {
        VentanaPrincipal.controlador = controlador;

    }

    /**
     * Metodo que muestra la conexion (linea) entre los componentes
     *
     * @return linea
     */
    public static Line getLinea() {
        return linea;
    }

    /**
     * Metodo que modifica la conexion (linea) entre los componentes
     *
     * @param linea Conexion entre componentes
     */
    public static void setLinea(Line linea) {
        VentanaPrincipal.linea = linea;
    }

    /**
     * Metodo el cual inicializa la ventana principal del simulador
     *
     * @param url La ubicacion utilizada para resolver rutas relativas para
     *            el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb  Los recursos utilizados para localizar el objeto raiz, o nulo
     *            si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fibraI = new Image("images/ico_fibra.png");
        fuenteI = new Image("images/ico_fuente.png");
        conectorI = new Image("images/ico_conector.png");
        potenciaI = new Image("images/ico_potencia.png");
        espectroI = new Image("images/ico_espectro.png");
        empalmeI = new Image("images/ico_empalme.png");
        splitterI = new Image("images/ico_splitter.png");
        multiplexorI = new Image("images/ico_mux.png");
        demultiplexorI = new Image("images/ico_demux.png");
        fbgI = new Image("images/ico_fbg.png");
        osciloscopio = new Image("images/ico_osciloscopio.png");
        OTDR = new Image("images/ico_otdr.png");

        viewFibra.setImage(fibraI);
        viewFuente.setImage(fuenteI);
        viewConector.setImage(conectorI);
        viewPotencia.setImage(potenciaI);
        viewEspectro.setImage(espectroI);
        viewEmpalme.setImage(empalmeI);
        viewSplitter.setImage(splitterI);
        viewMux.setImage(multiplexorI);
        viewDemux.setImage(demultiplexorI);
        viewFBG.setImage(fbgI);
        viewOsciloscopio.setImage(osciloscopio);
        viewOTDR.setImage(OTDR);

        componentMenu.setCollapsible(false);
    }

    /**
     * Metodo que abre la ventana para crear una fibra optica
     */
    @FXML
    public void abrirVentanaFibra() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaFibra.fxml"));
            Parent root = loader.load();
            VentanaFibraController fibraController = (VentanaFibraController) loader.getController();
            fibraController.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Fiber");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana para crear una fuente optica
     */
    @FXML
    public void abrirVentanaFuente() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaFuente.fxml"));
            Parent root = loader.load();
            VentanaFuenteController fuenteControl = loader.getController();
            fuenteControl.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Source");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana para crear un divisor optico
     */
    @FXML
    public void abrirVentanaSplitter() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaSplitter.fxml"));
            Parent root = loader.load();
            VentanaSplitterController splitterControl = loader.getController();
            splitterControl.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Splitter");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana para crear un conector optico
     */
    @FXML
    public void abrirVentanaConector() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaConector.fxml"));
            Parent root = loader.load();
            VentanaConectorController conectorControl = loader.getController();
            conectorControl.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Connector");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.show();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana para crear un empalme optico
     */
    @FXML
    public void abrirVentanaEmpalme() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaEmpalme.fxml"));
            Parent root = loader.load();
            VentanaEmpalmeController empalmeControl = loader.getController();
            empalmeControl.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Splice");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana para crear un multiplexor
     */
    @FXML
    public void abrirVentanaMux() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaMultiplexor.fxml"));
            Parent root = loader.load();
            VentanaMultiplexorController muxControl = loader.getController();
            muxControl.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Multiplexer");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana para crear un demultiplexor
     */
    @FXML
    public void abrirVentanaDemux() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaDemultiplexor.fxml"));
            Parent root = loader.load();
            VentanaDemultiplexorController demuxControl = loader.getController();
            demuxControl.init(controlador, VentanaPrincipal.stage, Pane1, this.scroll, this);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Demultiplexer");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Método que abre la ventana del osciloscopio
     */
    public void abrirVentanaOsciloscopio() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaOsciloscopio.fxml"));
            Parent root = loader.load();
            VentanaOsciloscopioController osciloscopioController = loader.getController();
            osciloscopioController.init(controlador, VentanaPrincipal.stage, this, Pane1, osciloscopioController);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/Static/CSS/grafica.css");
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - Oscilloscope");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Método que abre la ventana del osciloscopio
     */
    public void abrirVentanaAnalizador() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaAnalizador.fxml"));
            Parent root = loader.load();
            VentanaAnalizadorController analizadorController = loader.getController();
            analizadorController.init(controlador, VentanaPrincipal.stage, this, Pane1, analizadorController);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/Static/CSS/grafica.css");
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - Spectrum Analyzer");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Metodo que abre la ventana del OTDR
     */

    public void abrirVentanaOTDR(){
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaOTDR.fxml"));
            Parent root = loader.load();
            VentanaOTDRController OTDRController = loader.getController();
            OTDRController.init(controlador, VentanaPrincipal.stage,this, Pane1, OTDRController);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - OTDR");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que crea un medidor de potencia
     */
    @FXML
    public void crearPotencia() {
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nPower meter created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();

        MedidorPotencia potencia = new MedidorPotencia();
        potencia.setNombre("power");
        potencia.setConectadoEntrada(false);
        potencia.setConectadoSalida(false);
        potencia.setIdPotencia(idPotencia);
        idPotencia++;
        potencia.setId(controlador.getContadorElemento());
        controlador.getElementos().add(potencia);

        Label dibujo = new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
        dibujo.setText("power" + "_" + potencia.getIdPotencia());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(potencia);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);

        eventos(elem);

        dibujo.setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaPotencia.fxml"));
                    Parent root = loader.load();
                    VentanaPotenciaController potControl = loader.getController();
                    potControl.init(controlador, stage, Pane1, scroll, elem);
                    potControl.init2(controlador, stage, Pane1, elem, potControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.initModality(Modality.APPLICATION_MODAL);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elem);
            }
        });
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
    }

    /**
     * Metodo que crea un analizador de espectro

    @FXML
    public void crearEspectro() {
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nSpectrum meter created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();

        AnalizadorEspectro espectro = new AnalizadorEspectro();
        espectro.setNombre("spectrum");
        espectro.setConectadoEntrada(false);
        espectro.setConectadoSalida(false);
        espectro.setIdEspectro(idEspectro);
        idEspectro++;
        espectro.setId(controlador.getContadorElemento());
        controlador.getElementos().add(espectro);

        Label dibujo = new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
        dibujo.setText("spectrum" + "_" + espectro.getIdEspectro());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(espectro);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);

        eventos(elem);

        elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaEspectro.fxml"));
                    Parent root = loader.load();
                    VentanaEspectroController espcControl = loader.getController();
                    espcControl.init(controlador, stage, Pane1, scroll, elem, this);
                    espcControl.init2(controlador, stage, Pane1, elem, espcControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.initModality(Modality.APPLICATION_MODAL);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elem);
            }
        });
        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
    }
+/

    /**
     * Metodo que crea una rejilla de Bragg (FBG)
     */
    @FXML
    public void crearFBG() {
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nFBG created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();

        FBG fbg = new FBG();
        fbg.setNombre("fbg");
        fbg.setConectadoEntrada(false);
        fbg.setConectadoSalida(false);
        fbg.setIdFBG(idFBG);
        idFBG++;
        fbg.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fbg);

        Label dibujo = new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fbg.png")));
        dibujo.setText("fbg" + "_" + fbg.getIdFBG());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        PuertoSalida p = new PuertoSalida();
        fbg.getConexiones().add(p);
        fbg.setSalidas(2);
        elem.setComponente(fbg);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);

        eventosFBG(elem);

        controlador.setContadorElemento(controlador.getContadorElemento() + 1);
    }

    private void eventosOsciloscopio(ElementoGrafico elemento) {
        elemento.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double newX = event.getSceneX();
                double newY = event.getSceneY();
                int index = 0;
                for (int a = 0; a < Pane1.getChildren().size(); a++) {
                    if (Pane1.getChildren().get(a).toString().contains(elemento.getDibujo().getText())) {
                        index = a;
                        break;
                    }
                }
                if (!outSideParentBoundsX(elemento.getDibujo().getLayoutBounds(), newX, newY)) {
                    elemento.getDibujo().setLayoutX(Pane1.getChildren().get(index).getLayoutX() + event.getX() + 1);
                }

                if (!outSideParentBoundsY(elemento.getDibujo().getLayoutBounds(), newX, newY)) {
                    elemento.getDibujo().setLayoutY(Pane1.getChildren().get(index).getLayoutY() + event.getY() + 1);
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

                    osciloscopioController.init(controlador, stage, this, Pane1, osciloscopioController);
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
     * Metodo el cual le proporciona eventos al medidor de potencia o al
     * analizador de espectro tales como movimiento, abrir ventana para
     * realizar su funcion o mostrar un menu de acciones
     *
     * @param elem Elemento grafico del medidor de potencia o del analizador de
     *             espectro
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
                            if (elem.getComponente().getElementoConectadoEntrada().contains("splitter") && !aux.getComponente().getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                Splitter sp = (Splitter) aux.getComponente();
                                for (int on = 0; on < sp.getConexiones().size(); on++) {
                                    if (sp.getConexiones().get(on).isConectadoSalida()) {
                                        if (sp.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                            dibujarLineaAtrasElem(elem, aux, on);
                                        }
                                    }
                                }
                            } else if (elem.getComponente().getElementoConectadoEntrada().contains("demux") && !aux.getComponente().getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                Demultiplexor dem = (Demultiplexor) aux.getComponente();
                                for (int on = 0; on < dem.getConexiones().size(); on++) {
                                    if (dem.getConexiones().get(on).isConectadoSalida()) {
                                        if (dem.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                            dibujarLineaAtrasElem(elem, aux, on);
                                        }
                                    }
                                }
                            }
                            //Aqui es que ya reviso que esta conectado a un elemento, se va a revisar que sea un fbg
                            //y no esta conectado a su puerto default
                            else if (elem.getComponente().getElementoConectadoEntrada().contains("fbg") && !aux.getComponente().getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                FBG fb = (FBG) aux.getComponente();
                                for (int on = 0; on < fb.getConexiones().size(); on++) {
                                    if (fb.getConexiones().get(on).isConectadoSalida()) {
                                        if (fb.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())) {
                                            dibujarLineaAtrasElem(elem, aux, on);
                                            fb.getConexiones().get(0).getLinea().setVisible(false);
                                            Line li = new Line();
                                            fb.getConexiones().get(0).getLinea().setStartX(aux.getDibujo().getLayoutX() + 25);
                                            fb.getConexiones().get(0).getLinea().setStartY(aux.getDibujo().getLayoutY() + 36);
                                            fb.getConexiones().get(0).getLinea().setStrokeWidth(3);
                                            fb.getConexiones().get(0).getLinea().setStroke(javafx.scene.paint.Color.BLACK);
                                            fb.getConexiones().get(0).getLinea().setEndX(elem.getDibujo().getLayoutX());
                                            fb.getConexiones().get(0).getLinea().setEndY(elem.getDibujo().getLayoutY() + 26);
                                            fb.getConexiones().get(0).getLinea().setVisible(true);
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
    }

    /**
     * Metodo el cual le proporciona eventos a la rejilla de Bragg  tales como movimiento,
     * abrir ventana para modificarlo o mostrar un menu de acciones
     *
     * @param elem Elemento grafico del empalme
     */
    public void eventosFBG(ElementoGrafico elem) {
        FBG fbg = (FBG) elem.getComponente();
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
                    dibujarLineaFBG(elem);
                }
                if (elem.getComponente().isConectadoEntrada()) {
                    ElementoGrafico aux;
                    for (int it = 0; it < controlador.getDibujos().size(); it++) {
                        if (elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                            aux = controlador.getDibujos().get(it);
                            aux.getComponente().getLinea().setVisible(false);
                        }
                    }
                    dibujarLineaAtras(elem);
                }
                if (fbg.getConexiones().get(0).isConectadoSalida() == true) {
                    fbg.getConexiones().get(0).getLinea().setVisible(false);
                    dibujarLineaFBG(elem);
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaFBG.fxml"));
                    Parent root = loader.load();
                    VentanaFBGController fbgControl = loader.getController();
                    fbgControl.init(controlador, stage, Pane1, scroll, elem, this);
                    fbgControl.init2(controlador, stage, Pane1, elem, fbgControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.initModality(Modality.APPLICATION_MODAL);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elem);
            }
        });
    }

    /**
     * Metodo que le proporciona eventos al medidor de potencia despues de abrir
     * un trabajo
     *
     * @param dibujo Etiqueta donde sera colocado el medidor de potencia
     * @param elem   Elemento grafico del medidor de potencia
     */
    public void eventosPotencia(Label dibujo, ElementoGrafico elem) {
        eventos(elem);
        dibujo.setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaPotencia.fxml"));
                    Parent root = loader.load();
                    VentanaPotenciaController potControl = loader.getController();
                    potControl.init(controlador, stage, Pane1, scroll, elem);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elem);
            }
        });
    }

    /**
     * Metodo que le proporciona eventos al analizador de espectro despues de abrir
     * un trabajo
     *
     * @param dibujo Etiqueta donde sera colocado el analizador de espectro
     * @param elem   Elemento grafico del analizador de espectro
     */
    public void eventosEspectro(Label dibujo, ElementoGrafico elem) {
        eventos(elem);

        elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VentanaEspectro.fxml"));
                    Parent root = loader.load();
                    VentanaEspectroController espcControl = loader.getController();
                    espcControl.init(controlador, stage, Pane1, scroll, elem, this);
                    espcControl.init2(controlador, stage, Pane1, elem, espcControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - " + elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.initModality(Modality.APPLICATION_MODAL);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenu(elem);
            }
        });
    }

    /**
     * Metodo el cual muestra un menu de acciones para eliminar o
     * ver propiedades del medidor de potencia, del analizador de espectro o
     * de la rejilla de Bragg
     *
     * @param dibujo Elemento grafico del medidor de potencia, del analizador de espectro
     *               o de la rejilla de Bragg
     */
    public void mostrarMenu(ElementoGrafico dibujo) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Eliminar*/
        menuItem3.setOnAction(e -> {
            if (dibujo.getComponente().isConectadoSalida() == true) {
                for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                    if (dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())) {
                        Componente aux = controlador.getElementos().get(elemento);
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
                    if (dibujo.getDibujo().getText().contains("power")) {
                        MedidorPotencia aux = (MedidorPotencia) controlador.getElementos().get(elemento);
                        controlador.getDibujos().remove(dibujo);
                        controlador.getElementos().remove(aux);
                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "\nRemoved power meter!",
                                aceptar);
                        alert.setTitle("Succes");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    } else if (dibujo.getDibujo().getText().contains("fbg")) {
                        if (dibujo.getComponente().isConectadoSalida() == true) {
                            for (int elemento1 = 0; elemento1 < controlador.getElementos().size(); elemento1++) {
                                if (dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento1).getDibujo().getText())) {
                                    Componente aux1 = controlador.getElementos().get(elemento1);
                                    aux1.setConectadoEntrada(false);
                                    aux1.setElementoConectadoEntrada("-");
                                    dibujo.getComponente().getLinea().setVisible(false);
                                }
                            }
                        }
                        if (dibujo.getComponente().isConectadoEntrada() == true) {
                            for (int elemento2 = 0; elemento2 < controlador.getElementos().size(); elemento2++) {
                                if (dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())) {
                                    Componente aux2 = controlador.getElementos().get(elemento2);
                                    aux2.setConectadoSalida(false);
                                    aux2.setElementoConectadoSalida("-");
                                    aux2.getLinea().setVisible(false);
                                }
                            }
                        }
                        FBG fbg = (FBG) dibujo.getComponente();
                        for (int cz = 0; cz < fbg.getConexiones().size(); cz++) {
                            if (fbg.getConexiones().get(cz).isConectadoSalida()) {
                                for (int elemento3 = 0; elemento3 < controlador.getElementos().size(); elemento3++) {
                                    if (fbg.getConexiones().get(cz).getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento3).getDibujo().getText())) {
                                        Componente aux3 = controlador.getElementos().get(elemento3);
                                        aux3.setConectadoEntrada(false);
                                        aux3.setElementoConectadoEntrada("-");
                                        fbg.getConexiones().get(cz).getLinea().setVisible(false);
                                    }
                                }
                            }
                        }
                        for (int elemento4 = 0; elemento4 < controlador.getElementos().size(); elemento4++) {
                            if (dibujo.getId() == controlador.getElementos().get(elemento4).getId()) {
                                FBG aux = (FBG) controlador.getElementos().get(elemento4);
                                controlador.getDibujos().remove(dibujo);
                                controlador.getElementos().remove(aux);
                            }
                        }
                        dibujo.getDibujo().setVisible(false);
                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "\nRemoved FBG!",
                                aceptar);
                        alert.setTitle("Succes");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                }
            }
            dibujo.getDibujo().setVisible(false);
        });

        /*Propiedades*/
        menuItem4.setOnAction(e -> {
            for (int elemento = 0; elemento < controlador.getElementos().size(); elemento++) {
                if (dibujo.getId() == controlador.getElementos().get(elemento).getId()) {
                    if (dibujo.getDibujo().getText().contains("power")) {
                        Stage s = new Stage(StageStyle.DECORATED);
                        Image ico = new Image("images/dibujo_potencia.png");
                        s.getIcons().add(ico);
                        s.setTitle("OptiUAM BC - Properties");
                        s.initModality(Modality.APPLICATION_MODAL);
                        MedidorPotencia aux = (MedidorPotencia) controlador.getElementos().get(elemento);
                        Label label = new Label("  Name: " + aux.getNombre() +
                                "\n  Id: " + aux.getIdPotencia() +
                                "\n  Input: " + aux.getElementoConectadoEntrada()/*+
                                "\n  Output :"+aux.getElementoConectadoSalida()*/);
                        Scene scene = new Scene(label, 190, 80);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    } else if (dibujo.getDibujo().getText().contains("fbg")) {
                        Stage s = new Stage(StageStyle.DECORATED);
                        Image ico = new Image("images/dibujo_fbg.png");
                        s.getIcons().add(ico);
                        s.setTitle("OptiUAM BC - Properties");
                        s.initModality(Modality.APPLICATION_MODAL);
                        FBG aux = (FBG) controlador.getElementos().get(elemento);
                        Label label = new Label("  Name: " + aux.getNombre() +
                                "\n  Id: " + aux.getIdFBG() +
                                "\n  Input: " + aux.getElementoConectadoEntrada() +
                                "\n  Output :" + aux.getElementoConectadoSalida());
                        Scene scene = new Scene(label, 190, 80);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                }
            }
        });
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }

    /**
     * Metodo para cerrar la ventana
     *
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }

    /**
     * Metodo que proporciona informacion acerca de la elaboracion del proyecto
     */
    @FXML
    public void menuAboutAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/VentanaAcercaDe.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage st = new Stage(StageStyle.UTILITY);
            st.getIcons().add(ico);
            st.setTitle("OptiUAM BC - About");
            st.setScene(scene);
            st.showAndWait();
            st.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que proporciona el documento de ayuda del simulador
     */
    @FXML
    public void menuHelpAction() {
        try {
            File objetofile = new File("ayuda.pdf");
            Desktop.getDesktop().open(objetofile);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Metodo utilizado para iniciar un nuevo trabajo en el simulador
     */
    public void nuevoTrabajo() {
        Node node = Pane1.getChildren().get(0);
        Pane1.getChildren().clear();
        Pane1.getChildren().add(node);
        controlador.getElementos().clear();
        controlador.getDibujos().clear();
        controlador.reset();
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nNew work!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que permtite crear un nuevo trabajo
     */
    @FXML
    public void menuItemNewAction() {
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Do you want to create a new work?",
                aceptar,
                cancelar);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(cancelar) == aceptar) {
            nuevoTrabajo();
        } else {
        }
    }

    /**
     * Metodo que permite guardar un trabajo
     */
    @FXML
    public void menuItemSaveAction() {
        JFileChooser manejador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("opt Files (*.opt)", "*.opt");
        manejador.setDialogTitle("OptiUAM BC - Save");
        manejador.setBackground(Color.CYAN);
        manejador.setFileFilter(filtro);
        manejador.showSaveDialog(null);
        String ruta_archivo = "";
        try {
            ruta_archivo = manejador.getSelectedFile().getPath();
            controlador.guardarTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardar trabajo
        }
    }

    /**
     * Metodo utilizado para abrir un trabajo a partir de un archivo
     *
     * @param ruta Nombre del archivo
     * @throws java.lang.InstantiationException Proporciona diferentes excepciones lanzadas
     *                                          bajo el paquete java lang
     * @throws java.lang.IllegalAccessException Proporciona diferentes excepciones lanzadas
     *                                          bajo el paquete java lang
     */
    public void abrirTrabajo(String ruta) throws InstantiationException, IllegalAccessException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File(ruta);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            ControladorGeneral con = new ControladorGeneral();

            Node node = Pane1.getChildren().get(0);
            Pane1.getChildren().clear();
            Pane1.getChildren().add(node);
            controlador.getElementos().clear();
            controlador.getDibujos().clear();
            String linea = "";

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String nombre = partes[0];
                switch (nombre) {
                    case "connector":
                        Conector conector = new Conector();
                        conector.setId(Integer.valueOf(partes[1]));
                        conector.setNombre(nombre);
                        conector.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        conector.setElementoConectadoEntrada(partes[3]);
                        conector.setConectadoSalida(Boolean.valueOf(partes[4]));
                        conector.setElementoConectadoSalida(partes[5]);
                        conector.setLongitudOnda(Integer.valueOf(partes[6]));
                        conector.setModo(Integer.valueOf(partes[7]));
                        conector.setPerdidaInsercion(Double.valueOf(partes[8]));
                        conector.setIdConector(Integer.valueOf(partes[9]));
                        con.getElementos().add(conector);

                        Label dibujo = new Label();
                        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
                        dibujo.setText(conector.getNombre() + "_" + conector.getIdConector());
                        conector.setNombreid(conector.getNombre() + "_" + conector.getIdConector());
                        dibujo.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem = new ElementoGrafico();
                        elem.setComponente(conector);
                        elem.setDibujo(dibujo);
                        elem.setId(Integer.valueOf(partes[1]));
                        VentanaConectorController aux = new VentanaConectorController();
                        aux.init(con, stage, Pane1, scroll, this);
                        con.getDibujos().add(elem);
                        dibujo.setVisible(true);

                        Pane1.getChildren().add(dibujo);
                        aux.eventos(elem);
                        aux.setIdConector(conector.getIdConector() + 1);
                        break;

                    case "splice":
                        Empalme empalme = new Empalme();
                        empalme.setId(Integer.valueOf(partes[1]));
                        empalme.setNombre(nombre);
                        empalme.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        empalme.setElementoConectadoEntrada(partes[3]);
                        empalme.setConectadoSalida(Boolean.valueOf(partes[4]));
                        empalme.setElementoConectadoSalida(partes[5]);
                        empalme.setTipo(Integer.valueOf(partes[6]));
                        empalme.setPerdidaInsercion(Double.valueOf(partes[7]));
                        empalme.setLongitudOnda(Integer.valueOf(partes[8]));
                        empalme.setIdEmpalme(Integer.valueOf(partes[9]));
                        con.getElementos().add(empalme);

                        Label dibujo1 = new Label();
                        dibujo1.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                        dibujo1.setText(empalme.getNombre() + "_" + empalme.getIdEmpalme());
                        dibujo1.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo1.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo1.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem1 = new ElementoGrafico();
                        elem1.setComponente(empalme);
                        elem1.setDibujo(dibujo1);
                        elem1.setId(Integer.valueOf(partes[1]));
                        VentanaEmpalmeController aux1 = new VentanaEmpalmeController();
                        aux1.init(con, stage, Pane1, scroll, this);
                        con.getDibujos().add(elem1);
                        dibujo1.setVisible(true);

                        Pane1.getChildren().add(dibujo1);
                        aux1.eventos(elem1);
                        aux1.setIdEmpalme(Integer.valueOf(partes[9] + 1));
                        break;

                    case "fiber":
                        Fibra fibra = new Fibra();
                        fibra.setId(Integer.valueOf(partes[1]));
                        fibra.setNombre(nombre);
                        fibra.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fibra.setElementoConectadoEntrada(partes[3]);
                        fibra.setConectadoSalida(Boolean.valueOf(partes[4]));
                        fibra.setElementoConectadoSalida(partes[5]);
                        fibra.setLongitudOnda(Integer.valueOf(partes[6]));
                        fibra.setModo(Integer.valueOf(partes[7]));
                        fibra.setTipo(Integer.valueOf(partes[8]));
                        fibra.setLongitud_km(Double.valueOf(partes[9]));
                        fibra.setDispersion(Double.valueOf(partes[10]));
                        fibra.setAtenuacion(Double.valueOf(partes[11]));
                        fibra.setIdFibra(Integer.valueOf(partes[12]));
                        con.getElementos().add(fibra);

                        Label dibujo2 = new Label();
                        dibujo2.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                        dibujo2.setText(fibra.getNombre() + "_" + fibra.getIdFibra());
                        dibujo2.setLayoutX(Double.parseDouble(partes[13]));
                        dibujo2.setLayoutY(Double.parseDouble(partes[14]));
                        dibujo2.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem2 = new ElementoGrafico();
                        elem2.setComponente(fibra);
                        elem2.setDibujo(dibujo2);
                        elem2.setId(Integer.valueOf(partes[1]));
                        VentanaFibraController aux2 = new VentanaFibraController();
                        aux2.init(con, stage, Pane1, scroll, this);
                        con.getDibujos().add(elem2);
                        dibujo2.setVisible(true);

                        Pane1.getChildren().add(dibujo2);
                        aux2.eventos(elem2);
                        aux2.setIdFibra(fibra.getIdFibra() + 1);
                        break;

                    case "splitter":
                        Splitter splitter = new Splitter();
                        splitter.setId(Integer.valueOf(partes[1]));
                        splitter.setNombre(nombre);
                        splitter.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        splitter.setElementoConectadoEntrada(partes[3]);
                        splitter.setConectadoSalida(Boolean.valueOf(partes[4]));
                        splitter.setElementoConectadoSalida(partes[5]);
                        splitter.setSalidas(Integer.valueOf(partes[6]));
                        splitter.setPerdidaInsercion(Double.valueOf(partes[7]));
                        splitter.setLongitudOnda(Integer.valueOf(partes[8]));
                        splitter.setIdS(Integer.valueOf(partes[9]));
                        con.getElementos().add(splitter);

                        Label dibujo3 = new Label();

                        switch (splitter.getSalidas()) {
                            case 2:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter2.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[13]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[14]));
                                break;
                            case 4:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter4.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[17]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[18]));
                                break;
                            case 8:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter8.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[25]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[26]));
                                break;
                            default:
                                break;
                        }

                        dibujo3.setText(splitter.getNombre() + "_" + splitter.getIdS());
                        dibujo3.setContentDisplay(ContentDisplay.TOP);

                        for (int i = 1; i < splitter.getSalidas(); i++) {
                            PuertoSalida puerto = new PuertoSalida();
                            puerto.setConectadoSalida(Boolean.parseBoolean(partes[(9 + (2 * i) - 1)]));
                            puerto.setElementoConectadoSalida(partes[(9 + (2 * i))]);
                            splitter.getConexiones().add(puerto);
                        }
                        ElementoGrafico elem3 = new ElementoGrafico();
                        elem3.setComponente(splitter);
                        elem3.setDibujo(dibujo3);
                        elem3.setId(Integer.valueOf(partes[1]));
                        VentanaSplitterController aux3 = new VentanaSplitterController();
                        aux3.init(con, stage, Pane1, scroll, this);

                        con.getDibujos().add(elem3);
                        dibujo3.setVisible(true);

                        Pane1.getChildren().add(dibujo3);
                        aux3.eventos(elem3);
                        aux3.setIdS(splitter.getIdS() + 1);
                        break;

                    case "source":
                        Fuente fuente = new Fuente();
                        fuente.setId(Integer.valueOf(partes[1]));
                        fuente.setNombre(nombre);
                        fuente.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fuente.setElementoConectadoEntrada(partes[3]);
                        fuente.setConectadoSalida(Boolean.valueOf(partes[4]));
                        fuente.setElementoConectadoSalida(partes[5]);
                        fuente.setTipo(Integer.parseInt(partes[6]));
                        fuente.setPotencia(Double.parseDouble(partes[7]));
                        fuente.setAnchura(Double.parseDouble(partes[8]));
                        fuente.setVelocidad(Double.parseDouble(partes[9]));
                        fuente.setLongitudOnda(Double.parseDouble(partes[10]));
                        fuente.setPulso(Float.parseFloat(partes[11]), Float.parseFloat(partes[12]), Float.parseFloat(partes[13]), Float.parseFloat(partes[14]), (int) Float.parseFloat(partes[15]));
                        fuente.setIdFuente(Integer.parseInt(partes[16]));
                        fuente.setSpan(Double.parseDouble(partes[17]));
                        con.getElementos().add(fuente);

                        fuente.setPulso(Math.sqrt(2 * fuente.getPotencia()), fuente.getAnchura() * 1e-3, fuente.getLongitudOnda() * 1e3, fuente.getChirp(), fuente.getTipo());
                        fuente.graficas();
                        fuente.calcularDatos();

                        Label dibujo4 = new Label();
                        dibujo4.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
                        dibujo4.setText(fuente.getNombre() + "_" + fuente.getIdFuente());
                        dibujo4.setLayoutX(Double.parseDouble(partes[17]));
                        dibujo4.setLayoutY(Double.parseDouble(partes[18]));
                        dibujo4.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem4 = new ElementoGrafico();
                        elem4.setComponente(fuente);
                        elem4.setDibujo(dibujo4);
                        elem4.setId(Integer.valueOf(partes[1]));
                        VentanaFuenteController aux4 = new VentanaFuenteController();
                        aux4.init(con, stage, Pane1, scroll, this);

                        con.getDibujos().add(elem4);
                        dibujo4.setVisible(true);
                        Pane1.getChildren().add(dibujo4);
                        aux4.eventos(elem4);
                        aux4.setIdFuente(fuente.getIdFuente() + 1);
                        break;

                    case "power":
                        MedidorPotencia potencia = new MedidorPotencia();
                        potencia.setId(Integer.valueOf(partes[1]));
                        potencia.setNombre(nombre);
                        potencia.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        potencia.setElementoConectadoEntrada(partes[3]);
                        potencia.setConectadoSalida(Boolean.valueOf(partes[4]));
                        potencia.setElementoConectadoSalida(partes[5]);
                        potencia.setIdPotencia(Integer.valueOf(partes[6]));
                        con.getElementos().add(potencia);

                        Label dibujo5 = new Label();
                        dibujo5.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
                        dibujo5.setText(potencia.getNombre() + "_" + potencia.getIdPotencia());
                        dibujo5.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo5.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo5.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem5 = new ElementoGrafico();
                        elem5.setComponente(potencia);
                        elem5.setDibujo(dibujo5);
                        elem5.setId(Integer.valueOf(partes[1]));
                        con.getDibujos().add(elem5);
                        dibujo5.setVisible(true);

                        Pane1.getChildren().add(dibujo5);
                        eventosPotencia(dibujo5, elem5);
                        idPotencia = potencia.getIdPotencia() + 1;
                        break;

                    case "spectrum":
                        AnalizadorEspectro espectro = new AnalizadorEspectro();
                        espectro.setId(Integer.valueOf(partes[1]));
                        espectro.setNombre(nombre);
                        espectro.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        espectro.setElementoConectadoEntrada(partes[3]);
                        espectro.setConectadoSalida(Boolean.valueOf(partes[4]));
                        espectro.setElementoConectadoSalida(partes[5]);
                        espectro.setIdAnalizador(Integer.valueOf(partes[6]));
                        con.getElementos().add(espectro);

                        Label dibujo6 = new Label();
                        dibujo6.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
                        dibujo6.setText(espectro.getNombre() + "_" + espectro.getIdAnalizador());
                        dibujo6.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo6.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo6.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem6 = new ElementoGrafico();
                        elem6.setComponente(espectro);
                        elem6.setDibujo(dibujo6);
                        elem6.setId(Integer.valueOf(partes[1]));
                        con.getDibujos().add(elem6);
                        dibujo6.setVisible(true);

                        Pane1.getChildren().add(dibujo6);
                        eventosEspectro(dibujo6, elem6);
                        idEspectro = espectro.getIdAnalizador() + 1;
                        break;

                    case "fbg":
                        FBG fbg = new FBG();
                        fbg.setId(Integer.valueOf(partes[1]));
                        fbg.setNombre(nombre);
                        fbg.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fbg.setElementoConectadoEntrada(partes[3]);
                        fbg.setConectadoSalida(Boolean.valueOf(partes[4]));
                        fbg.setElementoConectadoSalida(partes[5]);
                        fbg.setIdFBG(Integer.valueOf(partes[6]));
                        fbg.setReflexion(Double.parseDouble(partes[7]));
                        fbg.setTransmision(Double.parseDouble(partes[8]));
                        fbg.setDz(Double.parseDouble(partes[9]));
                        fbg.setLongitudOnda(Double.parseDouble(partes[10]));
                        con.getElementos().add(fbg);

                        PuertoSalida ps = new PuertoSalida();
                        ps.setConectadoSalida(Boolean.parseBoolean(partes[11]));
                        ps.setElementoConectadoSalida(partes[12]);
                        fbg.getConexiones().add(ps);

                        Label dibujo7 = new Label();
                        dibujo7.setGraphic(new ImageView(new Image("images/dibujo_fbg.png")));
                        dibujo7.setText(fbg.getNombre() + "_" + fbg.getIdFBG());
                        dibujo7.setLayoutX(Double.parseDouble(partes[13]));
                        dibujo7.setLayoutY(Double.parseDouble(partes[14]));
                        dibujo7.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem7 = new ElementoGrafico();
                        elem7.setComponente(fbg);
                        elem7.setDibujo(dibujo7);
                        elem7.setId(Integer.valueOf(partes[1]));
                        con.getDibujos().add(elem7);
                        dibujo7.setVisible(true);

                        Pane1.getChildren().add(dibujo7);
                        eventosFBG(elem7);
                        idFBG = fbg.getIdFBG() + 1;
                        break;

                    case "mux":
                        Multiplexor mux = new Multiplexor();
                        mux.setId(Integer.valueOf(partes[1]));
                        mux.setNombre(nombre);
                        mux.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        mux.setElementoConectadoEntrada(partes[3]);
                        mux.setConectadoSalida(Boolean.valueOf(partes[4]));
                        mux.setElementoConectadoSalida(partes[5]);
                        mux.setIdMux(Integer.valueOf(partes[6]));
                        mux.setEntradas(Integer.valueOf(partes[7]));
                        mux.setPerdidaInsercion(Double.valueOf(partes[8]));
                        con.getElementos().add(mux);

                        Label dibujo9 = new Label();

                        switch (mux.getEntradas()) {
                            case 2:
                                dibujo9.setGraphic(new ImageView(new Image("images/dibujo_mux2.png")));
                                dibujo9.setLayoutX(Double.parseDouble(partes[11]));
                                dibujo9.setLayoutY(Double.parseDouble(partes[12]));
                                break;
                            case 4:
                                dibujo9.setGraphic(new ImageView(new Image("images/dibujo_mux4.png")));
                                dibujo9.setLayoutX(Double.parseDouble(partes[15]));
                                dibujo9.setLayoutY(Double.parseDouble(partes[16]));
                                break;
                            case 8:
                                dibujo9.setGraphic(new ImageView(new Image("images/dibujo_mux8.png")));
                                dibujo9.setLayoutX(Double.parseDouble(partes[23]));
                                dibujo9.setLayoutY(Double.parseDouble(partes[24]));
                                break;
                            default:
                                break;
                        }

                        dibujo9.setText(mux.getNombre() + "_" + mux.getIdMux());
                        dibujo9.setContentDisplay(ContentDisplay.TOP);

                        for (int i = 1; i < mux.getEntradas(); i++) {
                            PuertoEntrada puerto = new PuertoEntrada();
                            puerto.setConectadoEntrada(Boolean.parseBoolean(partes[(8 + (2 * i) - 1)]));
                            puerto.setElementoConectadoEntrada(partes[(8 + (2 * i))]);
                            mux.getConexionEntradas().add(puerto);
                        }
                        ElementoGrafico elem9 = new ElementoGrafico();
                        elem9.setComponente(mux);
                        elem9.setDibujo(dibujo9);
                        elem9.setId(Integer.valueOf(partes[1]));
                        VentanaMultiplexorController aux9 = new VentanaMultiplexorController();
                        aux9.init(con, stage, Pane1, scroll, this);

                        con.getDibujos().add(elem9);
                        dibujo9.setVisible(true);

                        Pane1.getChildren().add(dibujo9);
                        aux9.eventos(elem9);
                        aux9.setIdMux(mux.getIdMux() + 1);
                        break;

                    case "demux":
                        Demultiplexor demux = new Demultiplexor();
                        demux.setId(Integer.valueOf(partes[1]));
                        demux.setNombre(nombre);
                        demux.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        demux.setElementoConectadoEntrada(partes[3]);
                        demux.setConectadoSalida(Boolean.valueOf(partes[4]));
                        demux.setElementoConectadoSalida(partes[5]);
                        demux.setIdDemux(Integer.valueOf(partes[6]));
                        demux.setSalidas(Integer.valueOf(partes[7]));
                        demux.setPerdidaInsercion(Double.valueOf(partes[8]));

                        con.getElementos().add(demux);

                        Label dibujo10 = new Label();

                        switch (demux.getSalidas()) {
                            case 2:
                                dibujo10.setGraphic(new ImageView(new Image("images/dibujo_demux2.png")));
                                dibujo10.setLayoutX(Double.parseDouble(partes[12]));
                                dibujo10.setLayoutY(Double.parseDouble(partes[13]));
                                break;
                            case 4:
                                dibujo10.setGraphic(new ImageView(new Image("images/dibujo_demux4.png")));
                                dibujo10.setLayoutX(Double.parseDouble(partes[16]));
                                dibujo10.setLayoutY(Double.parseDouble(partes[17]));
                                break;

                            case 8:
                                dibujo10.setGraphic(new ImageView(new Image("images/dibujo_demux8.png")));
                                dibujo10.setLayoutX(Double.parseDouble(partes[24]));
                                dibujo10.setLayoutY(Double.parseDouble(partes[25]));
                                break;
                            default:
                                break;
                        }

                        dibujo10.setText(demux.getNombre() + "_" + demux.getIdDemux());
                        dibujo10.setContentDisplay(ContentDisplay.TOP);

                        for (int i = 1; i < demux.getSalidas(); i++) {
                            PuertoSalida puerto = new PuertoSalida();
                            puerto.setConectadoSalida(Boolean.parseBoolean(partes[(8 + (2 * i) - 1)]));
                            puerto.setElementoConectadoSalida(partes[(8 + (2 * i))]);
                            demux.getConexiones().add(puerto);
                        }
                        ElementoGrafico elem10 = new ElementoGrafico();
                        elem10.setComponente(demux);
                        elem10.setDibujo(dibujo10);
                        elem10.setId(Integer.valueOf(partes[1]));
                        VentanaDemultiplexorController aux10 = new VentanaDemultiplexorController();
                        aux10.init(con, stage, Pane1, scroll, this);

                        con.getDibujos().add(elem10);
                        dibujo10.setVisible(true);

                        Pane1.getChildren().add(dibujo10);
                        aux10.eventos(elem10);
                        aux10.setIdDemux(demux.getIdDemux() + 1);
                        break;

                    case "oscilloscope":
                        Osciloscopio osciloscopio = new Osciloscopio();
                        osciloscopio.setId(Integer.valueOf(partes[1]));
                        osciloscopio.setNombre(nombre);
                        osciloscopio.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        osciloscopio.setElementoConectadoEntrada(partes[3]);
                        osciloscopio.setConectadoSalida(Boolean.valueOf(partes[4]));
                        osciloscopio.setElementoConectadoSalida(partes[5]);
                        osciloscopio.setIdOsciloscopio(Integer.valueOf(partes[6]));
                        con.getElementos().add(osciloscopio);

                        Label dibujo8 = new Label();
                        dibujo8.setGraphic(new ImageView(new Image("images/dibujo_osciloscopio2.png")));
                        dibujo8.setText(osciloscopio.getNombre() + "_" + osciloscopio.getIdOsciloscopio());
                        dibujo8.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo8.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo8.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem8 = new ElementoGrafico();
                        elem8.setComponente(osciloscopio);
                        elem8.setDibujo(dibujo8);
                        elem8.setId(Integer.valueOf(partes[1]));
                        con.getDibujos().add(elem8);
                        dibujo8.setVisible(true);

                        Pane1.getChildren().add(dibujo8);
                        eventosOsciloscopio(elem8);
                        idOsciloscopio = osciloscopio.getIdOsciloscopio() + 1;
                        break;
                    case "OTDR":
                        OTDR otdr = new OTDR();
                        otdr.setId(Integer.valueOf(partes[1]));
                        otdr.setNombre(nombre);
                        otdr.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        otdr.setElementoConectadoEntrada(partes[3]);
                        otdr.setConectadoSalida(Boolean.valueOf(partes[4]));
                        otdr.setElementoConectadoSalida(partes[5]);
                        otdr.setIdOTDR(Integer.valueOf(partes[6]));
                        con.getElementos().add(otdr);

                        Label dibujo11 = new Label();
                        dibujo11.setGraphic(new ImageView(new Image("images/dibujo_otdr.png")));
                        dibujo11.setText(otdr.getNombre() + "_" + otdr.getIdOTDR());
                        dibujo11.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo11.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo11.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem11 = new ElementoGrafico();
                        elem11.setComponente(otdr);
                        elem11.setDibujo(dibujo11);
                        elem11.setId(Integer.valueOf(partes[1]));
                        con.getDibujos().add(elem11);
                        dibujo11.setVisible(true);

                        Pane1.getChildren().add(dibujo11);
                        eventosOsciloscopio(elem11);
                        idOsciloscopio = otdr.getIdOTDR() + 1;
                        break;
                    case "Spectrum_Analyzer":
                        AnalizadorEspectro analizadorEspectro = new AnalizadorEspectro();
                        analizadorEspectro.setId(Integer.valueOf(partes[1]));
                        analizadorEspectro.setNombre(nombre);
                        analizadorEspectro.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        analizadorEspectro.setElementoConectadoEntrada(partes[3]);
                        analizadorEspectro.setConectadoSalida(Boolean.valueOf(partes[4]));
                        analizadorEspectro.setElementoConectadoSalida(partes[5]);
                        analizadorEspectro.setIdAnalizador(Integer.valueOf(partes[6]));
                        con.getElementos().add(analizadorEspectro);

                        Label dibujo12 = new Label();
                        dibujo12.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
                        dibujo12.setText(analizadorEspectro.getNombre() + "_" + analizadorEspectro.getIdAnalizador());
                        dibujo12.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo12.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo12.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem12 = new ElementoGrafico();
                        elem12.setComponente(analizadorEspectro);
                        elem12.setDibujo(dibujo12);
                        elem12.setId(Integer.valueOf(partes[1]));
                        con.getDibujos().add(elem12);
                        dibujo12.setVisible(true);

                        Pane1.getChildren().add(dibujo12);
                        eventosOsciloscopio(elem12);
                        idOsciloscopio = analizadorEspectro.getIdAnalizador() + 1;
                        break;
                    default:
                        con.setContadorElemento(Integer.valueOf(partes[0]));
                }
            }
            controlador = con;


            // Pasa los datos
            for(ElementoGrafico element : controlador.getDibujos()) {
                if (element.getComponente().getNombre().equals("source")){
                    Fuente fuente = (Fuente) element.getComponente();
                    for (ElementoGrafico e : controlador.getDibujos()) {
                        if (!e.getComponente().getNombre().equals("source") && fuente.getElementoConectadoSalida().equals(e.getDibujo().getText())) {
                            e.getComponente().setDatos(fuente.getDatos());
                        } else {
                            for (ElementoGrafico eg : controlador.getDibujos()) {
                                if (e.getComponente().isConectadoSalida() && !e.getComponente().getNombre().equals("source") &&
                                        e.getComponente().getElementoConectadoSalida().equals(eg.getDibujo().getText())) {
                                    eg.getComponente().setDatos(e.getComponente().getDatos());
                                }
                            }
                        }
                    }
                }
            }

            redibujarLinea();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Metodo que permite abrir un trabajo
     */
    @FXML
    public void menuItemOpenAction() {
        JFileChooser manejador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("opt files", "opt");
        manejador.setDialogTitle("OptiUAM BC - Open");
        manejador.setFileFilter(filtro);
        manejador.setBackground(Color.CYAN);
        manejador.showOpenDialog(null);
        String ruta_archivo = "";
        try {
            ruta_archivo = manejador.getSelectedFile().getPath();
            abrirTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardar trabajo
        }
    }

    /**
     * Metodo que permite visualizar la conexion hacia delante del medidor de
     * potencia o del analizador de espectro con otro elemento
     *
     * @param elemG Elemento grafico del medidor de potencia o del analizador
     *              de espectro
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line = new Line();
        line.setStartX(elemG.getDibujo().getLayoutX() + elemG.getDibujo().getWidth());
        line.setStartY(elemG.getDibujo().getLayoutY() + 7);
        ElementoGrafico aux = new ElementoGrafico();
        for (int it = 0; it < controlador.getDibujos().size(); it++) {
            if (elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                aux = controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(javafx.scene.paint.Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
        setLinea(line);
        line.setVisible(true);
        Pane1.getChildren().add(line);
        elemG.getComponente().setLinea(line);
    }

    /**
     * Metodo que permite visualizar la conexion hacia delante del FBG
     * con otro elemento
     *
     * @param elemG Elemento grafico del divisor optico
     */
    public void dibujarLineaFBG(ElementoGrafico elemG) {
        FBG fbg = (FBG) elemG.getComponente();

        for (int lap = 0; lap < fbg.getSalidas() - 1; lap++) {
            if (fbg.getConexiones().get(lap).isConectadoSalida()) {
                Line line = new Line();

                switch (fbg.getSalidas()) {
                    default:
                        line.setStartX(elemG.getDibujo().getLayoutX() + 22);
                        line.setStartY(elemG.getDibujo().getLayoutY() + (30 + (5 * (lap + 1))));
                        break;
                }
                ElementoGrafico aux = new ElementoGrafico();
                for (int ir = 0; ir < controlador.getDibujos().size(); ir++) {
                    if (fbg.getConexiones().get(lap).getElementoConectadoSalida().equals(controlador.getDibujos().get(ir).getDibujo().getText())) {
                        aux = controlador.getDibujos().get(ir);
                        line.setStrokeWidth(2);
                        line.setStroke(javafx.scene.paint.Color.BLACK);
                        line.setEndX(aux.getDibujo().getLayoutX() + 12);
                        line.setEndY(aux.getDibujo().getLayoutY() + 8);
                        line.setVisible(true);
                        Pane1.getChildren().add(line);
                        fbg.getConexiones().get(lap).setLinea(line);
                    }
                }
            }
        }
    }

    /**
     * Metodo que permite visualizar la conexion hacia atras del medidor de
     * potencia o del analizador de espectro con otro elemento
     *
     * @param elem Elemento grafico del medidor de potencia o del analizador
     *             de espectro
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line = new Line();
        ElementoGrafico aux = new ElementoGrafico();
        for (int it = 0; it < controlador.getDibujos().size(); it++) {
            if (elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())) {
                aux = controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(javafx.scene.paint.Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX() + aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY() + 10);
        line.setEndX(elem.getDibujo().getLayoutX() + 12);
        line.setEndY(elem.getDibujo().getLayoutY() + 6);

        if (elem.getDibujo().getText().contains("spectrum")) {
            AnalizadorEspectro esp = (AnalizadorEspectro) elem.getComponente();
            if (esp.isConectadoEntrada() && esp.getElementoConectadoEntrada().startsWith("demux")) {
                Demultiplexor dem = (Demultiplexor) aux.getComponente();
                if (dem.isConectadoSalida()) {
                    line.setStartX(aux.getDibujo().getLayoutX() + aux.getDibujo().getWidth() - 5);
                    line.setStartY(aux.getDibujo().getLayoutY() + 20);
                } else {
                    for (int lap = 0; lap < dem.getSalidas() - 1; lap++) {
                        if (dem.getConexiones().get(lap).isConectadoSalida()) {
                            switch (dem.getSalidas()) {
                                case 2:
                                    line.setStartX(aux.getDibujo().getLayoutX() + 40);
                                    line.setStartY(aux.getDibujo().getLayoutY() + 20 + 13);
                                    break;
                                case 4:
                                    line.setStartX(aux.getDibujo().getLayoutX() + 40);
                                    line.setStartY(aux.getDibujo().getLayoutY() + 14 + ((lap + 1) * 10));
                                    break;
                                case 8:
                                    line.setStartX(aux.getDibujo().getLayoutX() + 8);
                                    line.setStartY(aux.getDibujo().getLayoutY() + 7 + ((lap + 1) * 9.4));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            } else if (esp.isConectadoEntrada() && esp.getElementoConectadoEntrada().startsWith("mux")) {
                line.setStartX(aux.getDibujo().getLayoutX() + aux.getDibujo().getWidth());
                line.setStartY(aux.getDibujo().getLayoutY() + 25);
            } else {
                line.setStartX(aux.getDibujo().getLayoutX() + aux.getDibujo().getWidth());
                line.setStartY(aux.getDibujo().getLayoutY() + 10);
            }
        } else if (elem.getDibujo().getText().contains("power")) {
            line.setEndX(elem.getDibujo().getLayoutX() + 5);
            line.setEndY(elem.getDibujo().getLayoutY() + 6);
        } else {
            line.setEndX(elem.getDibujo().getLayoutX() + 18);
            line.setEndY(elem.getDibujo().getLayoutY() + 8);
        }
        setLinea(line);
        line.setVisible(true);
        Pane1.getChildren().add(line);
        aux.getComponente().setLinea(line);
    }

    /**
     * Metodo utilizado para hacer visible la conexion entre elementos al abrir
     * un tabajo
     */
    public void redibujarLinea() {
        for (int w = 0; w < controlador.getDibujos().size(); w++) {
            ElementoGrafico elem = controlador.getDibujos().get(w);
            if (elem.getComponente().isConectadoSalida()) {
                if (elem.getDibujo().getText().contains("connector")) {
                    VentanaConectorController con = new VentanaConectorController();
                    con.init(controlador, stage, Pane1, scroll, this);
                    con.dibujarLinea(elem);
                } else if (elem.getDibujo().getText().contains("source")) {
                    VentanaFuenteController fue = new VentanaFuenteController();
                    fue.init(controlador, stage, Pane1, scroll, this);
                    fue.dibujarLinea(elem);
                } else if (elem.getDibujo().getText().contains("fiber")) {
                    VentanaFibraController fib = new VentanaFibraController();
                    fib.init(controlador, stage, Pane1, scroll, this);
                    fib.dibujarLinea(elem);
                } else if (elem.getDibujo().getText().contains("splice")) {
                    VentanaEmpalmeController emp = new VentanaEmpalmeController();
                    emp.init(controlador, stage, Pane1, scroll, this);
                    emp.dibujarLinea(elem);

                } else if (elem.getDibujo().getText().contains("splitter")) {
                    VentanaSplitterController spl = new VentanaSplitterController();
                    spl.init(controlador, stage, Pane1, scroll, this);
                    spl.dibujarLineaSp(elem);
                } else if (elem.getDibujo().getText().contains("mux")) {
                    VentanaMultiplexorController mux = new VentanaMultiplexorController();
                    mux.init(controlador, stage, Pane1, scroll, this);
                    mux.dibujarLinea(elem);
                } else if (elem.getDibujo().getText().contains("fbg")) {
                    VentanaFBGController fbg = new VentanaFBGController();
                    fbg.init(controlador, stage, Pane1, scroll, elem, this);
                    fbg.dibujarLineaFBG(elem);
                } else if (elem.getDibujo().getText().contains("demux")) {
                    VentanaDemultiplexorController demux = new VentanaDemultiplexorController();
                    demux.init(controlador, stage, Pane1, scroll, this);
                    demux.dibujarLineaDemux(elem);
                }
            }
        }
    }

    /**
     * Metodo que permite visualizar la conexion hacia atras del divisor optico,
     * demultiplexor o fbg
     *
     * @param elem   Elemento grafico del medidor de potencia
     * @param aux    Elemento grafico del divisor optico
     * @param puerto Puerto salida del divisor optico
     */
    public void dibujarLineaAtrasElem(ElementoGrafico elem, ElementoGrafico aux, int puerto) {
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
        } else if ("fbg".equals(aux.getComponente().getNombre())) {
            FBG fbg = (FBG) aux.getComponente();
            fbg.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);

            switch (fbg.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX() + 50);
                    line.setStartY(aux.getDibujo().getLayoutY() + (24 + (5 * (puerto + 1))));
                    break;
            }
        } else {
            Demultiplexor demux = (Demultiplexor) aux.getComponente();
            demux.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);

            switch (demux.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX() + 40);
                    line.setStartY(aux.getDibujo().getLayoutY() + 20 + 13);
                    break;
                case 4:
                    line.setStartX(aux.getDibujo().getLayoutX() + 40);
                    line.setStartY(aux.getDibujo().getLayoutY() + (14 + (10 * (puerto + 1))));
                    break;
                case 8:
                    line.setStartX(aux.getDibujo().getLayoutX() + 60);
                    line.setStartY(aux.getDibujo().getLayoutY() + (7 + (9.4 * (puerto + 1))));
                    break;
                default:
                    break;
            }
            line.setEndX(elem.getDibujo().getLayoutX() + 1);
            line.setEndY(elem.getDibujo().getLayoutY() + 7);
            line.setVisible(true);
            Pane1.getChildren().add(line);
            demux.getConexiones().get(puerto).setLinea(line);
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

    /**
     * Metodo el cual inicializa y actualiza las señales creadas
     *
     * @throws java.lang.InterruptedException Proporciona diferentes excepciones lanzadas
     *                                        bajo el paquete java lang
     */
    @FXML
    public void start() throws InterruptedException {
        btnStart = true;
        for (int a = 0; a < controlador.getElementos().size(); a++) {
            if (controlador.getElementos().get(a).getNombre().startsWith("source")) {
                Fuente fuente = (Fuente) controlador.getElementos().get(a);
                elemConected(fuente, true);
                System.out.println("adios");
            }
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nSignals Updated!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que agrega a una lista los componentes conectados antes de un componente
     *
     * @param lista Lista de componentes
     * @param comp  Componentes
     * @return lista de componentes
     */
    public LinkedList añadirComponentesConectados(LinkedList lista, Componente comp) {
        lista.add(comp);
        if (comp.isConectadoEntrada()) {
            for (int i = 0; i < controlador.getElementos().size(); i++) {
                if (comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(i).getDibujo().getText())) {
                    Componente aux = controlador.getElementos().get(i);
                    añadirComponentesConectados(lista, aux);
                    break;
                }
            }
        }
        return lista;
    }

    /**
     * Metodo encargado de generar la señal inicial en la fuente. Si esta
     * conectado, se realiza el proceso para modificar la señal dependiendo del
     * elemento
     *
     * @param actual Componente actual
     * @param tu     Indica si el elemento paso sus valores
     */
    public void elemConected(Componente actual, boolean tu) {
        if (actual.getNombre().startsWith("source")) {
            Fuente fue = (Fuente) actual;
            LinkedList<Listas> salidas = new LinkedList<>();
            Señal señal = new Señal();
            if (fue.getSeñalSalida() != null) {
                señal = fue.getSeñalSalida();
                salidas = señal.getValoresMagnitud();
            } else {
                try {
                    salidas = fue.calcularPulso2();
                    señal.setValoresMagnitud(salidas);
                    fue.setSeñalSalida(señal);
                } catch (ExcepcionDivideCero ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            fue.setSeñalSalida(señal);
            if (fue.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (fue.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        Señal señal2 = new Señal();
                        señal2 = clonarSeñal(señal);
                        auxcomp.setSeñalEntrada(señal2);
                        System.out.println("la fuente paso sus valores");
                        elemConected(auxcomp, tu);
                        break;
                    }
                }
            }
        } else if (actual.getNombre().startsWith("connector")) {
            Conector conector = (Conector) actual;
            LinkedList<Listas> salidas = new LinkedList<>();
            Señal señal = new Señal();
            señal = clonarSeñal(conector.getSeñalEntrada());
            salidas = señal.getValoresMagnitud();
            salidas = conector.valorMagnitudPerdida(salidas);
            señal.setValoresMagnitud(salidas);
            conector.setSeñalSalida(señal);

            if (conector.isConectadoSalida()) {
                Componente auxcomp = new Componente();
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (conector.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c).getDibujo().getText())) {
                        auxcomp = controlador.getElementos().get(c);
                        if ("mux".equals(auxcomp.getNombre())) {
                            Multiplexor muxAux = (Multiplexor) auxcomp;
                            if (muxAux.getElementoConectadoEntrada().startsWith(conector.getNombreid())) {
                                Señal señal2 = new Señal();
                                señal2 = clonarSeñal(señal);
                                muxAux.setSeñalEntrada(señal2);
                                muxAux.getSeñalEntrada().setSumado(false);
                            }
                            for (int h = 0; h < muxAux.getConexionEntradas().size(); h++) {
                                if (muxAux.getConexionEntradas().get(h).getElementoConectadoEntrada().startsWith(conector.getNombreid())) {
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    muxAux.getConexionEntradas().get(h).setSeñalEntrada(señal2);
                                    //muxAux.getConexionEntradas().get(h).getSeñalEntrada().setSumado(false);
                                    break;
                                }
                            }
                        } else {
                            Señal señal2 = new Señal();
                            señal2 = clonarSeñal(señal);
                            auxcomp.setSeñalEntrada(señal2);
                            System.out.println("conector paso sus valores");
                        }
                        elemConected(auxcomp, tu);
                    }
                }
            }
        } else if (actual.getNombre().startsWith("splice")) {
            Empalme empalme = (Empalme) actual;
            LinkedList<Listas> salidas = new LinkedList<>();
            Señal señal = new Señal();
            señal = clonarSeñal(empalme.getSeñalEntrada());
            salidas = empalme.valorMagnitudPerdida(salidas);
            señal.setValoresMagnitud(salidas);
            empalme.setSeñalSalida(señal);
            if (empalme.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (empalme.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        Señal señal2 = new Señal();
                        señal2 = clonarSeñal(señal);
                        auxcomp.setSeñalEntrada(señal2);
                        System.out.println("empalme paso sus valores");
                        elemConected(auxcomp, tu);
                    }
                }
            }
        } else if (actual.getNombre().startsWith("fiber")) {
            Fibra fibra = (Fibra) actual;
            LinkedList<Listas> salidas = new LinkedList<>();
            Señal señal = new Señal();
            señal = clonarSeñal(fibra.getSeñalEntrada());
            salidas = señal.getValoresMagnitud();
            salidas = fibra.valorMagnitudPerdida(salidas);
            señal.setValoresMagnitud(salidas);
            fibra.setSeñalSalida(señal);
            if (fibra.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (fibra.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        Señal señal2 = new Señal();
                        señal2 = clonarSeñal(señal);
                        auxcomp.setSeñalEntrada(señal2);
                        System.out.println("fibra paso sus valores");
                        elemConected(auxcomp, tu);
                    }
                }
            }
        } else if (actual.getNombre().startsWith("mux")) {
            boolean iniciado = false;
            Multiplexor mux = (Multiplexor) actual;
            if (mux.isConectadoEntrada() && mux.getSeñalEntrada() != null) {
                if (!iniciado) {
                    mux.setSenalesTotal(mux.getSeñalEntrada().getValoresMagnitud());
                    mux.getSeñalEntrada().setSumado(true);
                    iniciado = true;
                } else {
                    if (!mux.getSeñalEntrada().isSumado()) {
                        if (mux.getSeñalEntrada().getValoresMagnitud().size() <= mux.getSenalesTotal().size()) {
                            for (int j = 0; j < mux.getSeñalEntrada().getValoresMagnitud().size(); j++) {
                                NumeroComplejo complex = new NumeroComplejo(mux.getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getRealPart(), mux.getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getImaginaryPart());
                                mux.getSenalesTotal().get(j).getComplejo().sumar(complex, false);
                            }
                        } else {
                            for (int j = 0; j < mux.getSenalesTotal().size(); j++) {
                                NumeroComplejo complex2 = new NumeroComplejo(mux.getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getRealPart(), mux.getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getImaginaryPart());
                                mux.getSenalesTotal().get(j).getComplejo().sumar(complex2, false);
                            }
                        }
                        mux.getSeñalEntrada().setSumado(true);
                    }
                }
            }
            for (int g = 0; g < mux.getConexionEntradas().size(); g++) {
                if (mux.getConexionEntradas().get(g).isConectadoEntrada() && mux.getConexionEntradas().get(g).getSeñalEntrada() != null) {
                    if (!iniciado) {
                        mux.setSenalesTotal(mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud());
                        iniciado = true;
                        mux.getConexionEntradas().get(g).getSeñalEntrada().setSumado(true);
                    } else {
                        if (!mux.getConexionEntradas().get(g).getSeñalEntrada().isSumado()) {
                            if (mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud().size() <= mux.getSenalesTotal().size()) {
                                for (int j = 0; j < mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud().size(); j++) {
                                    NumeroComplejo complex = new NumeroComplejo(mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getRealPart(), mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getImaginaryPart());
                                    NumeroComplejo complex2 = new NumeroComplejo(mux.getSenalesTotal().get(j).getComplejo().getRealPart(), mux.getSenalesTotal().get(j).getComplejo().getImaginaryPart());
                                    mux.getSenalesTotal().get(j).setComplejo(complex.sumar(complex2, true));
                                }
                            } else {
                                for (int j = 0; j < mux.getSenalesTotal().size(); j++) {
                                    NumeroComplejo complex2 = new NumeroComplejo(mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getRealPart(), mux.getConexionEntradas().get(g).getSeñalEntrada().getValoresMagnitud().get(j).getComplejo().getImaginaryPart());
                                    NumeroComplejo complex = new NumeroComplejo(mux.getSenalesTotal().get(j).getComplejo().getRealPart(), mux.getSenalesTotal().get(j).getComplejo().getImaginaryPart());
                                    mux.getSenalesTotal().get(j).setComplejo(complex2.sumar(complex, true));
                                }
                            }
                            mux.getConexionEntradas().get(g).getSeñalEntrada().setSumado(true);
                        }
                    }
                }
            }
            Señal señal = new Señal();
            señal.setValoresMagnitud(mux.getSenalesTotal());
            mux.setSeñalSalida(señal);
            if (mux.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (mux.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        Señal señal2 = new Señal();
                        señal2 = clonarSeñal(señal);
                        auxcomp.setSeñalEntrada(señal2);
                        System.out.println("mux paso sus valores");
                        elemConected(auxcomp, tu);
                    }
                }
            }
        } else if (actual.getNombre().startsWith("splitter")) {
            Splitter splitter = (Splitter) actual;
            if (splitter.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (splitter.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        LinkedList<Listas> salidas = new LinkedList<>();
                        salidas = splitter.getSeñalEntrada().getValoresMagnitud();
                        for (int x = 0; x < salidas.size(); x++) {
                            NumeroComplejo nc = new NumeroComplejo((float) (salidas.get(x).getComplejo().getRealPart() / Math.sqrt(splitter.getSalidas())),
                                    (float) (salidas.get(x).getComplejo().getImaginaryPart() / Math.sqrt(splitter.getSalidas())));
                            salidas.get(x).setComplejo(nc);
                        }
                        double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6
                        calculosFFT(4096, salidas, Fsamp);

                        Señal señal = new Señal();
                        señal.setValoresMagnitud(salidas);
                        if (splitter.isConectadoSalida()) {
                            for (int c2 = 0; c2 < controlador.getElementos().size(); c2++) {
                                if (splitter.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c2).getDibujo().getText())) {
                                    Componente auxcomp2 = controlador.getElementos().get(c2);
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    auxcomp2.setSeñalEntrada(señal2);
                                    elemConected(auxcomp, tu);
                                }
                            }
                        }
                    }
                }
            }
            for (int f = 0; f < splitter.getConexiones().size(); f++) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (splitter.getConexiones().get(f).getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        LinkedList<Listas> salidas = new LinkedList<>();
                        salidas = splitter.getSeñalEntrada().getValoresMagnitud();

                        double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6
                        calculosFFT(4096, salidas, Fsamp);

                        Señal señal = new Señal();
                        señal.setValoresMagnitud(salidas);
                        if (splitter.getConexiones().get(f).isConectadoSalida()) {
                            for (int c2 = 0; c2 < controlador.getElementos().size(); c2++) {
                                if (splitter.getConexiones().get(f).getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c2).getDibujo().getText())) {
                                    Componente auxcomp2 = controlador.getElementos().get(c2);
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    auxcomp2.setSeñalEntrada(señal2);
                                    elemConected(auxcomp, tu);
                                }
                            }
                        }
                    }
                }
            }
        } else if (actual.getNombre().startsWith("fbg")) {
            FBG fbg = (FBG) actual;
            //TRANSMISION
            if (fbg.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (fbg.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        LinkedList<Listas> salidas = new LinkedList<>();
                        salidas = fbg.getSeñalEntrada().getValoresMagnitud();
                        for (int x = 0; x < salidas.size(); x++) {
                            NumeroComplejo nc = new NumeroComplejo((float) (salidas.get(x).getComplejo().getRealPart() / Math.sqrt(fbg.getSalidas())),
                                    (float) (salidas.get(x).getComplejo().getImaginaryPart() / Math.sqrt(fbg.getSalidas())));
                            salidas.get(x).setComplejo(nc);
                        }

                        double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6
                        calculosFFT(4096, salidas, Fsamp);

                        Señal señal = new Señal();
                        LinkedList xd = VentanaFBGController.transmision;

                        NumeroComplejo[] numeroifft = new NumeroComplejo[4096];
                        for (int z = 0; z < 4096; z++) {
                            NumeroComplejo numero = salidas.getLast().getFftseñal().getFft()[z].multiplicar((float) (double) xd.get(z), true);
                            numeroifft[z] = numero;
                        }
                        FFT2 fft = new FFT2();
                        NumeroComplejo[] Numfinal = fft.ifft(numeroifft);
                        LinkedList<Listas> listaAux = new LinkedList<>();

                        for (int a = 0; a < Numfinal.length; a++) {
                            Listas lista = new Listas();
                            lista.setComplejo(Numfinal[a]);
                            lista.setMagnitud(Numfinal[a].magnitud());
                            lista.setxNumComplex(fbg.getSeñalEntrada().getValoresMagnitud().get(a).getxNumComplex());
                            listaAux.add(lista);
                        }
                        señal.setValoresMagnitud(listaAux);
                        if (fbg.isConectadoSalida()) {
                            for (int c2 = 0; c2 < controlador.getElementos().size(); c2++) {
                                if (fbg.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c2).getDibujo().getText())) {
                                    Componente auxcomp2 = controlador.getElementos().get(c2);
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    auxcomp2.setSeñalEntrada(señal2);
                                    elemConected(auxcomp, tu);
                                }
                            }
                        }
                    }
                }
            }
            //REFLEXION
            for (int f = 0; f < fbg.getConexiones().size(); f++) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (fbg.getConexiones().get(f).getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        LinkedList<Listas> salidas = new LinkedList<>();
                        salidas = fbg.getSeñalEntrada().getValoresMagnitud();

                        double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6
                        calculosFFT(4096, salidas, Fsamp);

                        Señal señal = new Señal();
                        LinkedList xd = VentanaFBGController.reflexion;

                        NumeroComplejo[] numeroifft = new NumeroComplejo[4096];
                        for (int z = 0; z < 4096; z++) {
                            NumeroComplejo numero = salidas.getLast().getFftseñal().getFft()[z].multiplicar((float) (double) xd.get(z), true);
                            numeroifft[z] = numero;
                        }
                        FFT2 fft = new FFT2();
                        NumeroComplejo[] Numfinal = fft.ifft(numeroifft);
                        LinkedList<Listas> listaAux = new LinkedList<>();

                        for (int a = 0; a < Numfinal.length; a++) {
                            Listas lista = new Listas();
                            lista.setComplejo(Numfinal[a]);
                            lista.setMagnitud(Numfinal[a].magnitud());
                            lista.setxNumComplex(fbg.getSeñalEntrada().getValoresMagnitud().get(a).getxNumComplex());
                            listaAux.add(lista);
                        }
                        señal.setValoresMagnitud(listaAux);
                        if (fbg.getConexiones().get(f).isConectadoSalida()) {
                            for (int c2 = 0; c2 < controlador.getElementos().size(); c2++) {
                                if (fbg.getConexiones().get(f).getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c2).getDibujo().getText())) {
                                    Componente auxcomp2 = controlador.getElementos().get(c2);
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    auxcomp2.setSeñalEntrada(señal2);
                                    elemConected(auxcomp, tu);
                                }
                            }
                        }
                    }
                }
            }
        } else if (actual.getNombre().startsWith("demux")) {
            Demultiplexor demux = (Demultiplexor) actual;
            if (demux.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (demux.getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        LinkedList<Listas> salidas = new LinkedList<>();
                        salidas = demux.getSeñalEntrada().getValoresMagnitud();
                        for (int x = 0; x < salidas.size(); x++) {
                            NumeroComplejo nc = new NumeroComplejo((float) (salidas.get(x).getComplejo().getRealPart() / Math.sqrt(demux.getSalidas())),
                                    (float) (salidas.get(x).getComplejo().getImaginaryPart() / Math.sqrt(demux.getSalidas())));
                            salidas.get(x).setComplejo(nc);
                        }

                        double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6
                        calculosFFT(4096, salidas, Fsamp);

                        Señal señal = new Señal();
                        LinkedList xd = demux.getFbg().filtro(demux.getLongitudOnda(), 1.9);

                        NumeroComplejo[] numeroifft = new NumeroComplejo[4096];
                        for (int z = 0; z < 4096; z++) {
                            NumeroComplejo numero = salidas.getLast().getFftseñal().getFft()[z].multiplicar((float) (double) xd.get(z), true);
                            numeroifft[z] = numero;
                        }
                        FFT2 fft = new FFT2();
                        NumeroComplejo[] Numfinal = fft.ifft(numeroifft);
                        LinkedList<Listas> listaAux = new LinkedList<>();

                        for (int a = 0; a < Numfinal.length; a++) {
                            Listas lista = new Listas();
                            lista.setComplejo(Numfinal[a]);
                            lista.setMagnitud(Numfinal[a].magnitud());
                            lista.setxNumComplex(demux.getSeñalEntrada().getValoresMagnitud().get(a).getxNumComplex());
                            listaAux.add(lista);
                        }
                        señal.setValoresMagnitud(listaAux);
                        if (demux.isConectadoSalida()) {
                            for (int c2 = 0; c2 < controlador.getElementos().size(); c2++) {
                                if (demux.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c2).getDibujo().getText())) {
                                    Componente auxcomp2 = controlador.getElementos().get(c2);
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    auxcomp2.setSeñalEntrada(señal2);
                                    elemConected(auxcomp, tu);
                                }
                            }
                        }
                    }
                }
            }
            for (int f = 0; f < demux.getConexiones().size(); f++) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (demux.getConexiones().get(f).getElementoConectadoSalida().equals(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        LinkedList<Listas> salidas = new LinkedList<>();
                        salidas = demux.getSeñalEntrada().getValoresMagnitud();

                        double Fsamp = 2 * (236 * Math.pow(10, 6)); //2*FcMaxima*10^6
                        calculosFFT(4096, salidas, Fsamp);

                        Señal señal = new Señal();
                        LinkedList xd = demux.getConexiones().get(f).getFbg().filtro(demux.getConexiones().get(f).getLongitudOnda(), 1.9);

                        NumeroComplejo[] numeroifft = new NumeroComplejo[4096];
                        for (int z = 0; z < 4096; z++) {
                            NumeroComplejo numero = salidas.getLast().getFftseñal().getFft()[z].multiplicar((float) (double) xd.get(z), true);
                            numeroifft[z] = numero;
                        }
                        FFT2 fft = new FFT2();
                        NumeroComplejo[] Numfinal = fft.ifft(numeroifft);
                        LinkedList<Listas> listaAux = new LinkedList<>();

                        for (int a = 0; a < Numfinal.length; a++) {
                            Listas lista = new Listas();
                            lista.setComplejo(Numfinal[a]);
                            lista.setMagnitud(Numfinal[a].magnitud());
                            lista.setxNumComplex(demux.getSeñalEntrada().getValoresMagnitud().get(a).getxNumComplex());
                            listaAux.add(lista);
                        }
                        señal.setValoresMagnitud(listaAux);
                        if (demux.getConexiones().get(f).isConectadoSalida()) {
                            for (int c2 = 0; c2 < controlador.getElementos().size(); c2++) {
                                if (demux.getConexiones().get(f).getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c2).getDibujo().getText())) {
                                    Componente auxcomp2 = controlador.getElementos().get(c2);
                                    Señal señal2 = new Señal();
                                    señal2 = clonarSeñal(señal);
                                    auxcomp2.setSeñalEntrada(señal2);
                                    elemConected(auxcomp, tu);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Señal señal = new Señal();
            LinkedList<Listas> salidas = new LinkedList<>();
            señal = actual.getSeñalEntrada();
            actual.setSeñalSalida(señal);
            if (actual.isConectadoSalida()) {
                for (int c = 0; c < controlador.getElementos().size(); c++) {
                    if (actual.getElementoConectadoSalida().startsWith(controlador.getDibujos().get(c).getDibujo().getText())) {
                        Componente auxcomp = controlador.getElementos().get(c);
                        Señal señal2 = new Señal();
                        señal2 = clonarSeñal(señal);
                        auxcomp.setSeñalEntrada(señal2);
                        elemConected(auxcomp, tu);
                    }
                }
            }
        }
    }

    /**
     * Metodo encargado de sumar las señales que esten presentes en el
     * multiplexor cuidando que no se repita la señal que ya se sumo
     *
     * @param lista1 Señal 1
     * @param lista2 Señal 2
     * @return Suma de señales en el multiplexor
     */
    public LinkedList<Listas> sumarSeñales(LinkedList<Listas> lista1, LinkedList<Listas> lista2) {
        LinkedList<Listas> listaAux = new LinkedList<>();
        LinkedList<Listas> sumador;
        new LinkedList<>();

        if (lista1.size() >= lista2.size()) {
            listaAux = lista1;
            sumador = lista2;
        } else {
            listaAux = lista2;
            sumador = lista1;
        }
        for (int x = 0; x < sumador.size(); x++) {
            NumeroComplejo complex = new NumeroComplejo(listaAux.get(x).getComplejo().getRealPart(), listaAux.get(x).getComplejo().getImaginaryPart());
            NumeroComplejo complex2 = new NumeroComplejo(sumador.get(x).getComplejo().getRealPart(), sumador.get(x).getComplejo().getImaginaryPart());
            complex.sumar(complex2, false);
            listaAux.get(x).setComplejo(complex);
        }
        System.out.println("la señal..." + lista1.toString() + "se sumo con la señal..." + lista2.toString());
        return listaAux;
    }

    /**
     * Metodo el cual calcula la Transformada Rapida de Fourier de la señal
     *
     * @param NFFT    Tamaño de muestra
     * @param señales Lista de señales
     * @param Fsamp   Frecuencia de muestreo
     */
    public void calculosFFT(int NFFT, LinkedList<Listas> señales, double Fsamp) {
        int n = NFFT;

        NumeroComplejo[] nc = new NumeroComplejo[n];
        for (int a = -(n / 2); a < (n / 2); a++) {
            nc[(n / 2) + a] = señales.get((n / 2) + a).getComplejo();
            señales.get((n / 2) + a).setxFftseñal(((n / 2) + a) * (Fsamp / n));
        }
        Señal s = new Señal();
        FFT2 fftAux = new FFT2();
        NumeroComplejo[] fftValores = fftAux.fft(nc);

        s.setFft(fftValores);

        señales.getLast().setFftseñal(s);
    }

    /**
     * Metodo el cual clona la señal actual
     *
     * @param señal Señal actual
     * @return Señal clonada
     */
    public Señal clonarSeñal(Señal señal) {
        Señal señalxd = new Señal();
        LinkedList<Listas> xd = new LinkedList<>();
        señalxd.setValoresMagnitud(xd);
        for (int f = 0; f < señal.getValoresMagnitud().size(); f++) {
            NumeroComplejo compl = new NumeroComplejo(señal.getValoresMagnitud().get(f).getComplejo().getRealPart(), señal.getValoresMagnitud().get(f).getComplejo().getImaginaryPart());
            Listas lista = new Listas();
            lista.setComplejo(compl);
            lista.setxNumComplex(señal.getValoresMagnitud().get(f).getxNumComplex());
            señalxd.getValoresMagnitud().add(lista);
        }
        return señalxd;
    }

}
