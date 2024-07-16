
package optiuam.bc.controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.Demultiplexor;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.FBG;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.MedidorEspectro;
import optiuam.bc.modelo.MedidorPotencia;
import optiuam.bc.modelo.Multiplexor;
import optiuam.bc.modelo.Osciloscopio;
import optiuam.bc.modelo.PuertoEntrada;
import optiuam.bc.modelo.PuertoSalida;
import optiuam.bc.modelo.Splitter;

/**
 * Clase VentanaPrincipal la cual se encarga de proporcionar la funcionalidad 
 * al simulador
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class VentanaPrincipal implements Initializable {

    /**Escenario en el cual se agregaran los objetos creados*/
    static Stage stage;
    /**Controlador del simulador*/
    static ControladorGeneral controlador= new ControladorGeneral();
    /**Identificador del medidor de potencia*/
    static int idEspectro = 0;
    /**Identificador del analizador de espectro*/
    static int idPotencia = 0;
    /**Identificador de la rejilla de Bragg (FBG)*/
    static int idFBG = 0;
    /**Identificador del osciloscopio optico*/
    static int idOsciloscopio = 0;
    /**Conexion (linea) entre componentes*/
    static Line linea;
    /**Icono de la fibra optica*/
    Image fibraI;
    /**Icono de la fuente optica*/
    Image fuenteI;
    /**Icono del conector*/
    Image conectorI;
    /**Icono del divisor optico*/
    Image splitterI;
    /**Icono del empalme*/
    Image empalmeI;
    /**Icono del medidor de potencia*/
    Image potenciaI;
    /**Icono del analizador de espectro*/
    Image espectroI;
    /**Icono del analizador de espectro*/
    Image osciloscopioI;
    /**Icono del multiplexor*/
    Image multiplexorI;
    /**Icono del demultiplexor*/
    Image demultiplexorI;
    /**Icono de la rejilla de Bragg (FBG)*/
    Image fbgI;
    /**Fondo del panel de trabajo*/
    Image fondo;
    /**Permite visualizar el icono de la fibra*/
    @FXML
    ImageView viewFibra;
    /**Permite visualizar el icono de la fuente*/
    @FXML
    ImageView viewFuente;
    /**Permite visualizar el icono del conector*/
    @FXML
    ImageView viewConector;
    /**Permite visualizar el icono del divisor optico*/
    @FXML
    ImageView viewSplitter;
    /**Permite visualizar el icono del empalme*/
    @FXML
    ImageView viewEmpalme;
    /**Permite visualizar el icono del medidor de potencia*/
    @FXML
    ImageView viewPotencia;
    /**Permite visualizar el icono del analizador de espectro*/
    @FXML
    ImageView viewEspectro;
    /**Permite visualizar el icono del osciloscopio*/
    @FXML
    ImageView viewOsciloscopio;
    /**Permite visualizar el icono del multiplexor*/
    @FXML
    ImageView viewMux;
    /**Permite visualizar el icono del demultiplexor*/
    @FXML
    ImageView viewDemux;
    /**Permite visualizar el icono de la rejilla de Bragg (FBG)*/
    @FXML
    ImageView viewFBG;
    /**Boton para abrir la ventana de la fibra y crear una*/
    @FXML
    Button btnFibra;
    /**Boton para abrir la ventana de la fuente y crear una*/
    @FXML
    Button btnFuente;
    /**Boton para abrir la ventana del conector y crear uno*/
    @FXML
    Button btnConector;
    /**Boton para abrir la ventana del divisor optico y crear uno*/
    @FXML
    Button btnSplitter;
    /**Boton para abrir la ventana del empalme y crear uno*/
    @FXML
    Button btnEmpalme;
    /**Boton para abrir la ventana del medidor de potencia y crear uno*/
    @FXML
    Button btnPotencia;
    /**Boton para abrir la ventana del analizador de espectro y crear uno*/
    @FXML
    Button btnEspectro;
    /**Boton para abrir la ventana del osciloscopio y crear uno*/
    @FXML
    Button btnOsciloscopio;
    /**Boton para abrir la ventana del multiplexor y crear uno*/
    @FXML
    Button btnMux;
    /**Boton para abrir la ventana del demultiplexor y crear uno*/
    @FXML
    Button btnDemux;
    /**Boton para abrir la ventana de la rejilla de Bragg (FBG) y crear una*/
    @FXML
    Button btnFBG;
    /**Panel para agregar objetos*/    
    @FXML
    public Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    public ScrollPane scroll;
    /**Panel de componentes*/
    @FXML
    AnchorPane desplegable;
    /**Barra de componentes*/
    @FXML
    TitledPane componentMenu;
    /**Permtite crear un nuevo trabajo*/
    @FXML
    MenuItem menuItemNew;
    /**Permite guardar un trabajo*/
    @FXML
    MenuItem menuItemSave;
    /**Permite abrir un trabajo*/
    @FXML
    MenuItem menuItemOpen;
    /**Proporciona el documento de ayuda del simulador*/
    @FXML
    Menu menuHelp;
    /**Proporciona informacion acerca de la elaboracion del proyecto*/
    @FXML
    Menu menuAbout;
    
    /**
     * Metodo que muestra el escenario en el cual se agregaran los objetos 
     * creados
     * @return stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Metodo que modifica el escenario en el cual se agregaran los objetos 
     * creados
     * @param stage Escenario en el cual se agregan los componentes creados
     */
    public static void setStage(Stage stage) {
        VentanaPrincipal.stage = stage;
    }

    /**
     * Metodo que muestra el panel para agregar objetos
     * @return panel
     */
    public Pane getPane1() {
        return Pane1;
    }
    
    /**
     * Metodo que muestra el controlador del simulador
     * @return controlador
     */
    public static ControladorGeneral getControlador() {
        return controlador;
    }

    /**
     * Metodo que modifica el controlador del simulador
     * @param controlador Controlador del simulador
     */
    public void setControlador(ControladorGeneral controlador) {
        VentanaPrincipal.controlador = controlador;

    }
    
    /**
     * Metodo que muestra la conexion (linea) entre los componentes
     * @return linea
     */
    public static Line getLinea() {
        return linea;
    }
    
    /**
     * Metodo que modifica la conexion (linea) entre los componentes
     * @param linea Conexion entre componentes
     */
    public static void setLinea(Line linea) {
        VentanaPrincipal.linea = linea;
    }
    
    /**
     * Metodo el cual inicializa la ventana principal del simulador
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fibraI=new Image("images/ico_fibra.png"); 
        fuenteI=new Image("images/ico_fuente.png"); 
        conectorI=new Image("images/ico_conector.png"); 
        potenciaI=new Image("images/ico_potencia.png"); 
        espectroI=new Image("images/ico_espectro.png"); 
        osciloscopioI=new Image("images/ico_osciloscopio.png"); 
        empalmeI=new Image("images/ico_empalme.png"); 
        splitterI=new Image("images/ico_splitter.png"); 
        multiplexorI=new Image("images/ico_mux.png"); 
        demultiplexorI=new Image("images/ico_demux.png"); 
        fbgI=new Image("images/ico_fbg.png"); 
        
        viewFibra.setImage(fibraI);
        viewFuente.setImage(fuenteI);
        viewConector.setImage(conectorI);
        viewPotencia.setImage(potenciaI);
        viewEspectro.setImage(espectroI);
        viewOsciloscopio.setImage(osciloscopioI);
        viewEmpalme.setImage(empalmeI);
        viewSplitter.setImage(splitterI);
        viewMux.setImage(multiplexorI);
        viewDemux.setImage(demultiplexorI);
        viewFBG.setImage(fbgI);
        
        componentMenu.setCollapsible(false);
    }    
    
    /**
     * Metodo que abre la ventana para crear una fibra optica
     */
    @FXML
    public void abrirVentanaFibra() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaFibra.fxml"));
            Parent root = loader.load();
            //Se crea una instancia del controlador de fibra.
            VentanaFibraController fibraController= (VentanaFibraController) loader.getController();
            fibraController.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Fiber");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
            //System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaFuente.fxml"));
            Parent root =loader.load();
            VentanaFuenteController fuenteControl=loader.getController();
            fuenteControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Source");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
            
            //System.out.print(controlador.getContadorElemento());
            /*for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).toString());
            }*/
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaSplitter.fxml"));
            Parent root =loader.load();
            VentanaSplitterController splitterControl=loader.getController();
            splitterControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Splitter");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
            //System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
            Parent root =loader.load();
            VentanaConectorController conectorControl=loader.getController();
            conectorControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Connector");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.show();
            //System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaEmpalme.fxml"));
            Parent root =loader.load();
            VentanaEmpalmeController empalmeControl=loader.getController();
            empalmeControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Splice");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
            //System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaMultiplexor.fxml"));
            Parent root =loader.load();
            VentanaMultiplexorController muxControl=loader.getController();
            muxControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Multiplexer");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
            //System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que abre la ventana para crear un demultiplexor
     */
    /*
    @FXML
    public void abrirVentanaDemux() {
        try {
            Stage s = new Stage(StageStyle.UTILITY);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaDemultiplexor.fxml"));
            Parent root =loader.load();
            VentanaDemultiplexorController demuxControl=loader.getController();
            demuxControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            s.getIcons().add(ico);
            s.setTitle("OptiUAM BC - New Demultiplexer");
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(scene);
            s.setResizable(false);
            s.showAndWait();
            //System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    
    /**
     * Metodo que crea un medidor de potencia
     */
    @FXML
    public void crearPotencia(){
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
        
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
        dibujo.setText("power" + "_"+ potencia.getIdPotencia());
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
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaPotencia.fxml"));
                    Parent root= loader.load();
                    VentanaPotenciaController potControl= loader.getController();
                    potControl.init(controlador, stage, Pane1, scroll,elem);
                    potControl.init2(controlador, stage, Pane1, elem, potControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
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
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    /**
     * Metodo que crea un analizador de espectro
     */
    @FXML
    public void crearEspectro() {
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nSpectrum meter created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        
        MedidorEspectro espectro = new MedidorEspectro();
        espectro.setNombre("spectrum");
        espectro.setConectadoEntrada(false);
        espectro.setConectadoSalida(false);
        espectro.setIdEspectro(idEspectro);
        idEspectro++;
        espectro.setId(controlador.getContadorElemento());
        controlador.getElementos().add(espectro);
        
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
        dibujo.setText("spectrum" + "_"+ espectro.getIdEspectro());
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEspectro.fxml"));
                    Parent root= loader.load();
                    VentanaEspectroController espcControl= loader.getController();
                    espcControl.init(controlador, stage, Pane1, scroll,elem);
                    espcControl.init2(controlador, stage, Pane1, elem, espcControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    
                    //s.initOwner(VentanaPrincipal.getStage());
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
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    /**
     * Metodo que crea un osciloscopio optico
     */
    @FXML
    public void crearOsciloscopio() {
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nOscilloscope created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        
        Osciloscopio osciloscopio = new Osciloscopio();
        osciloscopio.setNombre("oscilloscope");
        osciloscopio.setConectadoEntrada(false);
        osciloscopio.setConectadoSalida(false);
        osciloscopio.setIdOsciloscopio(idOsciloscopio);
        idOsciloscopio++;
        osciloscopio.setId(controlador.getContadorElemento());
        controlador.getElementos().add(osciloscopio);
        
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_osciloscopio.png")));
        dibujo.setText("oscilloscope" + "_"+ osciloscopio.getIdOsciloscopio());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(osciloscopio);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
        eventos(elem);

        elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaOsciloscopio.fxml"));
                    Parent root= loader.load();
                    VentanaOsciloscopioController osciControl= loader.getController();
                    osciControl.init(controlador, stage, Pane1, scroll,elem);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    
                    //s.initOwner(VentanaPrincipal.getStage());
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
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    /**
     * Metodo que crea una rejilla de Bragg (FBG)
     */
    @FXML
    public void crearFBG(){
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nFBG created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        
        FBG rejilla = new FBG();
        rejilla.setNombre("fbg"); 
        rejilla.setConectadoEntrada(false);
        rejilla.setConectadoSalida(false);
        rejilla.setIdFBG(idFBG);
        idFBG++;
        rejilla.setId(controlador.getContadorElemento());
        controlador.getElementos().add(rejilla);
        
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fbg.png")));
        dibujo.setText("fbg" + "_"+ rejilla.getIdFBG());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        PuertoSalida p= new PuertoSalida();
        rejilla.getConexiones().add(p);
        rejilla.setSalidas(2);
        elem.setComponente(rejilla);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
        eventosFBG(elem);

        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    /**
     * Metodo el cual le proporciona eventos al medidor de potencia o al  
     * analizador de espectro tales como movimiento, abrir ventana para 
     * realizar su funcion o mostrar un menu de acciones
     * @param elem Elemento grafico del medidor de potencia o del analizador de 
     * espectro
     */
    public void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if(event.getButton()==MouseButton.PRIMARY){
                double newX=event.getSceneX();
                double newY=event.getSceneY();
                int j=0;
                for(int a=0; a<Pane1.getChildren().size();a++){
                    if(Pane1.getChildren().get(a).toString().contains(elem.getDibujo().getText())){
                        j=a;
                        break;
                    }
                }
                if( outSideParentBoundsX(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                    elem.getDibujo().setLayoutX(Pane1.getChildren().get(j).getLayoutX()+event.getX()+1);
                }

                if(outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                    elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY()+event.getY()+1);
                }
                if(elem.getComponente().isConectadoSalida()==true){
                    elem.getComponente().getLinea().setVisible(false);
                    dibujarLinea(elem);
                }
                if(elem.getComponente().isConectadoEntrada()){
                    ElementoGrafico aux;
                    for(int it=0; it<controlador.getDibujos().size();it++){
                        if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                            aux=controlador.getDibujos().get(it);
                            //Aqui es que ya reviso que esta conectado a un elemento, se va a revisar que sea un fbg
                            //y no esta conectado a su puerto default
                            if(elem.getComponente().getElementoConectadoEntrada().contains("splitter")||elem.getComponente().getElementoConectadoEntrada().contains("demux")&&!aux.getComponente().getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                                Splitter sp=(Splitter)aux.getComponente();
                                for(int on=0; on<sp.getConexiones().size(); on++){
                                    if(sp.getConexiones().get(on).isConectadoSalida()){
                                        if(sp.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                                            dibujarLineaAtrasSplitter(elem, aux, on);
                                        }
                                    }
                                }
                            }else if(elem.getComponente().getElementoConectadoEntrada().contains("fbg")&&!aux.getComponente().getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                                FBG sp=(FBG)aux.getComponente();
                                for(int on=0; on<sp.getConexiones().size(); on++){
                                    if(sp.getConexiones().get(on).isConectadoSalida()){
                                        if(sp.getConexiones().get(on).getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                                            dibujarLineaAtrasSplitter(elem, aux, on);
                                            sp.getConexiones().get(0).getLinea().setVisible(false);
                                            Line li=new Line();
                                            sp.getConexiones().get(0).getLinea().setStartX(aux.getDibujo().getLayoutX()+25);
                                            sp.getConexiones().get(0).getLinea().setStartY(aux.getDibujo().getLayoutY()+36);
                                            sp.getConexiones().get(0).getLinea().setStrokeWidth(3);
                                            sp.getConexiones().get(0).getLinea().setStroke(javafx.scene.paint.Color.BLACK);
                                            sp.getConexiones().get(0).getLinea().setEndX(elem.getDibujo().getLayoutX());
                                            sp.getConexiones().get(0).getLinea().setEndY(elem.getDibujo().getLayoutY()+26);
                                            sp.getConexiones().get(0).getLinea().setVisible(true);
                                            
                                            //Pane1.getChildren().add(li); 
                                            //aux.getComponente().setLinea(li);
                                            //System.out.println("hola");
                                        }
                                    }
                                }
                            }else{
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
     * @param elem Elemento grafico del empalme
     */
    public void eventosFBG(ElementoGrafico elem) {
        FBG fbg= (FBG) elem.getComponente();
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if(event.getButton()==MouseButton.PRIMARY){
                double newX=event.getSceneX();
                double newY=event.getSceneY();
                int j=0;
                for(int a=0; a<Pane1.getChildren().size();a++){
                    if(Pane1.getChildren().get(a).toString().contains(elem.getDibujo().getText())){
                        j=a;
                        break;
                    }
                }
                if( outSideParentBoundsX(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                    elem.getDibujo().setLayoutX(Pane1.getChildren().get(j).getLayoutX()+event.getX()+1);
                }

                if(outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY()+event.getY()+1);}
                if(elem.getComponente().isConectadoSalida()==true){
                    elem.getComponente().getLinea().setVisible(false);
                    dibujarLinea(elem);
                }
                if(elem.getComponente().isConectadoEntrada()){
                    ElementoGrafico aux;
                    for(int it=0; it<controlador.getDibujos().size();it++){
                        if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                            aux=controlador.getDibujos().get(it);
                            aux.getComponente().getLinea().setVisible(false);
                        }
                    }
                    dibujarLineaAtras(elem);
                }
                if(fbg.getConexiones().get(0).isConectadoSalida()==true){
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
            if(event.getButton()==MouseButton.PRIMARY){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFBG.fxml"));
                    Parent root= loader.load();
                    VentanaFBGController fbgControl = loader.getController();
                    fbgControl.init(controlador, stage, Pane1, scroll, elem);
                    fbgControl.init2(controlador, stage, Pane1,elem,fbgControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.initModality(Modality.APPLICATION_MODAL);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(event.getButton()==MouseButton.SECONDARY){
                mostrarMenu(elem);
            }
        });
    }
    
    /**
     * Metodo que le proporciona eventos al medidor de potencia despues de abrir 
     * un trabajo
     * @param dibujo Etiqueta donde sera colocado el medidor de potencia
     * @param elem Elemento grafico del medidor de potencia
     */
    public void eventosPotencia(Label dibujo, ElementoGrafico elem){
        eventos(elem);
        dibujo.setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaPotencia.fxml"));
                    Parent root= loader.load();
                    VentanaPotenciaController potControl= loader.getController();
                    potControl.init(controlador, stage, Pane1, scroll,elem);
                    potControl.init2(controlador, stage, Pane1, elem, potControl);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
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
     * @param dibujo Etiqueta donde sera colocado el analizador de espectro
     * @param elem Elemento grafico del analizador de espectro
     */
    public void eventosEspectro(Label dibujo, ElementoGrafico elem){
        eventos(elem);
            
        elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
        if (event1.getButton() == MouseButton.PRIMARY) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEspectro.fxml"));
                Parent root= loader.load();
                VentanaEspectroController espcControl= loader.getController();
                espcControl.init(controlador, stage, Pane1, scroll,elem);
                espcControl.init2(controlador, stage, Pane1, elem, espcControl);
                Scene scene = new Scene(root);
                Image ico = new Image("images/acercaDe.png");
                Stage s = new Stage(StageStyle.UTILITY);
                s.getIcons().add(ico);
                s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
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
     * Metodo que le proporciona eventos al osciloscopio despues de abrir 
     * un trabajo
     * @param dibujo Etiqueta donde sera colocado el osciloscopio
     * @param elem Elemento grafico del osciloscopio
     */
    public void eventosOsciloscopio(Label dibujo, ElementoGrafico elem){
        eventos(elem);
            
        elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
        if (event1.getButton() == MouseButton.PRIMARY) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaOsciloscopio.fxml"));
                Parent root= loader.load();
                VentanaOsciloscopioController osciControl= loader.getController();
                osciControl.init(controlador, stage, Pane1, scroll,elem);
                Scene scene = new Scene(root);
                Image ico = new Image("images/acercaDe.png");
                Stage s = new Stage(StageStyle.UTILITY);
                s.getIcons().add(ico);
                s.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
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
     * @param dibujo Elemento grafico del medidor de potencia, del analizador de espectro
     * o de la rejilla de Bragg
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Eliminar*/
        menuItem3.setOnAction(e ->{
            if(dibujo.getComponente().isConectadoSalida()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        System.out.println();
                        aux.setElementoConectadoEntrada("");
                        dibujo.getComponente().getLinea().setVisible(false);
                    }
                }   
            }
            if(dibujo.getComponente().isConectadoEntrada()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        aux.setConectadoSalida(false);
                        aux.setElementoConectadoSalida("");
                        aux.getLinea().setVisible(false);
                    }
                }
            }
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    if(dibujo.getDibujo().getText().contains("power")){ 
                        MedidorPotencia aux= (MedidorPotencia)controlador.getElementos().get(elemento);
                        controlador.getDibujos().remove(dibujo);
                        controlador.getElementos().remove(aux); 
                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "\nRemoved power meter!",
                                aceptar);
                        alert.setTitle("Succes");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                    else if(dibujo.getDibujo().getText().contains("oscilloscope")){ 
                        Osciloscopio aux= (Osciloscopio)controlador.getElementos().get(elemento);
                        controlador.getDibujos().remove(dibujo);
                        controlador.getElementos().remove(aux); 
                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "\nRemoved Oscilloscope!",
                                aceptar);
                        alert.setTitle("Succes");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                    else if(dibujo.getDibujo().getText().contains("fbg")){ 
                        //FBG aux= (FBG)controlador.getElementos().get(elemento);
                        if(dibujo.getComponente().isConectadoSalida()==true){
                            for(int elemento1=0; elemento1<controlador.getElementos().size(); elemento1++){
                                if(dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento1).getDibujo().getText())){
                                    Componente aux1= controlador.getElementos().get(elemento1);
                                    //System.out.println();
                                    aux1.setConectadoEntrada(false);
                                    aux1.setElementoConectadoEntrada("-");
                                    dibujo.getComponente().getLinea().setVisible(false);
                                }
                            }   
                        }
                        if(dibujo.getComponente().isConectadoEntrada()==true){
                            for(int elemento2=0; elemento2<controlador.getElementos().size(); elemento2++){
                                if(dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                                    Componente aux2= controlador.getElementos().get(elemento2);
                                    aux2.setConectadoSalida(false);
                                    aux2.setElementoConectadoSalida("-");
                                    aux2.getLinea().setVisible(false);
                                }
                            }
                        }
                        FBG sp=(FBG)dibujo.getComponente();
                        for(int cz=0; cz<sp.getConexiones().size(); cz++){
                            if(sp.getConexiones().get(cz).isConectadoSalida()){
                                for(int elemento3=0; elemento3<controlador.getElementos().size(); elemento3++){
                                    if(sp.getConexiones().get(cz).getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento3).getDibujo().getText())){
                                        Componente aux3= controlador.getElementos().get(elemento3);
                                        //System.out.println();
                                        aux3.setConectadoEntrada(false);
                                        aux3.setElementoConectadoEntrada("-");
                                        sp.getConexiones().get(cz).getLinea().setVisible(false);
                                    }
                                }
                            }
                        }

                        for(int elemento4=0; elemento4<controlador.getElementos().size(); elemento4++){
                            if(dibujo.getId()==controlador.getElementos().get(elemento4).getId()){
                                FBG aux= (FBG)controlador.getElementos().get(elemento4);
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
                    else{
                        MedidorEspectro aux= (MedidorEspectro)controlador.getElementos().get(elemento);
                        controlador.getDibujos().remove(dibujo);
                        controlador.getElementos().remove(aux); 
                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "\nRemoved spectrum meter!",
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
        menuItem4.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    if(dibujo.getDibujo().getText().contains("power")){ 
                        Stage s = new Stage(StageStyle.DECORATED);
                        Image ico = new Image("images/dibujo_potencia.png");
                        s.getIcons().add(ico);
                        s.setTitle("OptiUAM BC - Properties");
                        s.initModality(Modality.APPLICATION_MODAL);
                        MedidorPotencia aux= (MedidorPotencia)controlador.getElementos().get(elemento);
                        Label label = new Label("  Name: "+aux.getNombre()+
                                "\n  Id: "+aux.getIdPotencia()+
                                "\n  Input: "+aux.getElementoConectadoEntrada()/*+
                                "\n  Output :"+aux.getElementoConectadoSalida()*/);
                        //label.setStyle("-fx-background-color: lavender;");
                        Scene scene = new Scene(label, 190, 80);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                    else if(dibujo.getDibujo().getText().contains("oscilloscope")){ 
                        Stage s = new Stage(StageStyle.DECORATED);
                        Image ico = new Image("images/dibujo_osciloscopio.png");
                        s.getIcons().add(ico);
                        s.setTitle("OptiUAM BC - Properties");
                        s.initModality(Modality.APPLICATION_MODAL);
                        Osciloscopio aux= (Osciloscopio)controlador.getElementos().get(elemento);
                        Label label = new Label("  Name: "+aux.getNombre()+
                                "\n  Id: "+aux.getIdOsciloscopio()+
                                "\n  Input: "+aux.getElementoConectadoEntrada()/*+
                                "\n  Output :"+aux.getElementoConectadoSalida()*/);
                        //label.setStyle("-fx-background-color: lavender;");
                        Scene scene = new Scene(label, 190, 80);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                    else if(dibujo.getDibujo().getText().contains("fbg")){ 
                        Stage s = new Stage(StageStyle.DECORATED);
                        Image ico = new Image("images/dibujo_fbg.png");
                        s.getIcons().add(ico);
                        s.setTitle("OptiUAM BC - Properties");
                        s.initModality(Modality.APPLICATION_MODAL);
                        FBG aux= (FBG)controlador.getElementos().get(elemento);
                        Label label = new Label("  Name: "+aux.getNombre()+
                                "\n  Id: "+aux.getIdFBG()+
                                "\n  Input: "+aux.getElementoConectadoEntrada()+
                                "\n  Output :"+aux.getElementoConectadoSalida());
                        //label.setStyle("-fx-background-color: lavender;");
                        Scene scene = new Scene(label, 190, 80);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                    else{
                        Stage s = new Stage(StageStyle.DECORATED);
                        Image ico = new Image("images/dibujo_espectro.png");
                        s.getIcons().add(ico);
                        s.setTitle("OptiUAM BC - Properties");
                        s.initModality(Modality.APPLICATION_MODAL);
                        MedidorEspectro aux= (MedidorEspectro)controlador.getElementos().get(elemento);
                        Label label = new Label("  Name: "+aux.getNombre()+
                                "\n  Id: "+aux.getIdEspectro()+
                                "\n  Input: "+aux.getElementoConectadoEntrada()/*+
                                "\n  Output :"+aux.getElementoConectadoSalida()*/);
                        //label.setStyle("-fx-background-color: lavender;");
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
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo que proporciona informacion acerca de la elaboracion del proyecto
     */
    @FXML
    public void menuAboutAction(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaAcercaDe.fxml"));
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
    public void menuHelpAction(){
        try {
            File objetofile = new File ("ayuda.pdf");
            Desktop.getDesktop().open(objetofile);
        } catch (IOException ex) {
               System.out.println(ex);
        }
    }
    
    /**
     * Metodo utilizado para iniciar un nuevo trabajo en el simulador
     */
    public void nuevoTrabajo(){
        Node node= Pane1.getChildren().get(0);
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

        if(result.orElse(cancelar) == aceptar) {
            nuevoTrabajo();
        }
        else{}
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
        String ruta_archivo="";
        try {
            ruta_archivo = manejador.getSelectedFile().getPath();
            //System.out.println(ruta_archivo);
            controlador.guardarTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardar trabajo
        }
    } 
    
    /**
     * Metodo utilizado para abrir un trabajo a partir de un archivo
     * @param ruta Nombre del archivo
     * @throws java.lang.InstantiationException Proporciona diferentes excepciones lanzadas 
     * bajo el paquete java lang
     * @throws java.lang.IllegalAccessException Proporciona diferentes excepciones lanzadas 
     * bajo el paquete java lang
     */
    public void abrirTrabajo(String ruta) throws InstantiationException, IllegalAccessException{
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
             // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (ruta);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            
            ControladorGeneral con = new ControladorGeneral();
                        
           Node node= Pane1.getChildren().get(0);
           Pane1.getChildren().clear();
           Pane1.getChildren().add(node);
           controlador.getElementos().clear();
           controlador.getDibujos().clear();
           // Lectura del fichero
            String linea="";
            
            while((linea=br.readLine())!=null){
                String [] partes = linea.split(",");
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
                        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
                        dibujo.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem = new ElementoGrafico();
                        elem.setComponente(conector);
                        elem.setDibujo(dibujo);
                        elem.setId(Integer.valueOf(partes[1]));
                        VentanaConectorController aux= new VentanaConectorController();
                        aux.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem);
                        dibujo.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo);
                        aux.eventos(elem);
                        aux.setIdConector(conector.getIdConector()+1);
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
                        //System.out.println(empalme.getIdEmpalme());
                        con.getElementos().add(empalme);
                        
                        Label dibujo1 = new Label();
                        dibujo1.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                        dibujo1.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
                        dibujo1.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo1.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo1.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem1 = new ElementoGrafico();
                        elem1.setComponente(empalme);
                        elem1.setDibujo(dibujo1);
                        elem1.setId(Integer.valueOf(partes[1]));
                        VentanaEmpalmeController aux1= new VentanaEmpalmeController();
                        aux1.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem1);
                        dibujo1.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo1);
                        aux1.eventos(elem1);
                        aux1.setIdEmpalme(Integer.valueOf(partes[9]+1));
                        
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
                        //System.out.println(fibra.getIdFibra());
                        con.getElementos().add(fibra);
                        
                        Label dibujo2 = new Label();
                        dibujo2.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                        dibujo2.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
                        dibujo2.setLayoutX(Double.parseDouble(partes[13]));
                        dibujo2.setLayoutY(Double.parseDouble(partes[14]));
                        dibujo2.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem2 = new ElementoGrafico();
                        elem2.setComponente(fibra);
                        elem2.setDibujo(dibujo2);
                        elem2.setId(Integer.valueOf(partes[1]));
                        VentanaFibraController aux2= new VentanaFibraController();
                        aux2.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem2);
                        dibujo2.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo2);
                        aux2.eventos(elem2);
                        aux2.setIdFibra(fibra.getIdFibra()+1);
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
                        //System.out.println(fbg.getIdS());
                        con.getElementos().add(splitter);
                        
                        Label dibujo3 = new Label();
                        
                        switch (splitter.getSalidas()) {
                            case 2:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter2.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[12]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[13]));
                                break;
                            case 4:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter4.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[16]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[17]));
                                break;
                                
                            case 8:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter8.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[24]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[25]));
                                break;
                            case 16:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
                                dibujo3.setLayoutX(Double.parseDouble(partes[40]));
                                dibujo3.setLayoutY(Double.parseDouble(partes[41]));
                                break;
                            case 32:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
                                break;
                            case 64:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
                                break;
                            default:
                                break;
                        }
                        
                        dibujo3.setText(splitter.getNombre() + "_"+ splitter.getIdS());
                        dibujo3.setContentDisplay(ContentDisplay.TOP);
                        
                        //String [] conexiones = linea.split(",");
                        for(int i=1;i<splitter.getSalidas(); i++){
                            PuertoSalida puerto= new PuertoSalida();
                            puerto.setConectadoSalida(Boolean.parseBoolean(partes[(9+(2*i)-1)]));
                            puerto.setElementoConectadoSalida(partes[(9+(2*i))]);
                            splitter.getConexiones().add(puerto);
                        }
                        ElementoGrafico elem3 = new ElementoGrafico();
                        elem3.setComponente(splitter);
                        elem3.setDibujo(dibujo3);
                        elem3.setId(Integer.valueOf(partes[1]));
                        VentanaSplitterController aux3= new VentanaSplitterController();
                        aux3.init(con, stage, Pane1, scroll);
                        
                        con.getDibujos().add(elem3);
                        dibujo3.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo3);
                        aux3.eventos(elem3);
                        aux3.setIdS(splitter.getIdS()+1);
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
                        fuente.setLongitudOnda(Integer.parseInt(partes[10]));
                        fuente.setPulso(Float.parseFloat(partes[11]), Float.parseFloat(partes[12]), Float.parseFloat(partes[13]), Float.parseFloat(partes[14]), Float.parseFloat(partes[15]));
                        fuente.setIdFuente(Integer.parseInt(partes[16]));
                        con.getElementos().add(fuente);
                        
                        Label dibujo4 = new Label();
                        dibujo4.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
                        dibujo4.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
                        dibujo4.setLayoutX(Double.parseDouble(partes[17]));
                        dibujo4.setLayoutY(Double.parseDouble(partes[18]));
                        dibujo4.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem4 = new ElementoGrafico();
                        elem4.setComponente(fuente);
                        elem4.setDibujo(dibujo4);
                        elem4.setId(Integer.valueOf(partes[1]));
                        VentanaFuenteController aux4= new VentanaFuenteController();
                        aux4.init(con, stage, Pane1, scroll);

                        con.getDibujos().add(elem4);
                        dibujo4.setVisible(true);
                        Pane1.getChildren().add(dibujo4);
                        aux4.eventos(elem4);
                        aux4.setIdFuente(fuente.getIdFuente()+1);
                        break;
                        
                    case "power":
                        MedidorPotencia potencia= new MedidorPotencia();
                        potencia.setId(Integer.valueOf(partes[1]));
                        potencia.setNombre(nombre);
                        potencia.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        potencia.setElementoConectadoEntrada(partes[3]);
                        potencia.setConectadoSalida(Boolean.valueOf(partes[4]));
                        potencia.setElementoConectadoSalida(partes[5]);
                        potencia.setIdPotencia(Integer.valueOf(partes[6]));
                        System.out.println(potencia.getIdPotencia());
                        con.getElementos().add(potencia);
                        
                        Label dibujo5 = new Label();
                        dibujo5.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
                        dibujo5.setText(potencia.getNombre() + "_"+ potencia.getIdPotencia());
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
                        idPotencia=potencia.getIdPotencia()+1;
                        break;    
                      
                    case "spectrum":
                        MedidorEspectro espectro = new MedidorEspectro();
                        espectro.setId(Integer.valueOf(partes[1]));
                        espectro.setNombre(nombre);
                        espectro.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        espectro.setElementoConectadoEntrada(partes[3]);
                        espectro.setConectadoSalida(Boolean.valueOf(partes[4]));
                        espectro.setElementoConectadoSalida(partes[5]);
                        espectro.setIdEspectro(Integer.valueOf(partes[6]));
                        System.out.println(espectro.getIdEspectro());
                        con.getElementos().add(espectro);
                        
                        Label dibujo6 = new Label();
                        dibujo6.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
                        dibujo6.setText(espectro.getNombre() + "_"+ espectro.getIdEspectro());
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
                        idEspectro=espectro.getIdEspectro()+1;
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
                        System.out.println(fbg.getIdFBG());
                        con.getElementos().add(fbg);
                        PuertoSalida ps=new PuertoSalida();
                        ps.setConectadoSalida(Boolean.parseBoolean(partes[11]));
                        ps.setElementoConectadoSalida(partes[12]);
                        fbg.getConexiones().add(ps);
                        Label dibujo7 = new Label();
                        dibujo7.setGraphic(new ImageView(new Image("images/dibujo_fbg.png")));
                        dibujo7.setText(fbg.getNombre() + "_"+ fbg.getIdFBG());
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
                        idFBG=fbg.getIdFBG()+1;
                        /*------------------------------------------------------*/
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
                        System.out.println(osciloscopio.getIdOsciloscopio());
                        con.getElementos().add(osciloscopio);
                        
                        Label dibujo8 = new Label();
                        dibujo8.setGraphic(new ImageView(new Image("images/dibujo_osciloscopio.png")));
                        dibujo8.setText(osciloscopio.getNombre() + "_"+ osciloscopio.getIdOsciloscopio());
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
                        eventosOsciloscopio(dibujo8, elem8);
                        idOsciloscopio=osciloscopio.getIdOsciloscopio()+1;
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
                        
                        //System.out.println(fbg.getIdS());
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
                        
                        dibujo9.setText(mux.getNombre() + "_"+ mux.getIdMux());
                        dibujo9.setContentDisplay(ContentDisplay.TOP);
                        
                        //String [] conexiones = linea.split(",");
                        for(int i=1;i<mux.getEntradas(); i++){
                            PuertoEntrada puerto= new PuertoEntrada();
                            puerto.setConectadoEntrada(Boolean.parseBoolean(partes[(8+(2*i)-1)]));
                            puerto.setElementoConectadoEntrada(partes[(8+(2*i))]);
                            mux.getConexionEntradas().add(puerto);
                        }
                        ElementoGrafico elem9 = new ElementoGrafico();
                        elem9.setComponente(mux);
                        elem9.setDibujo(dibujo9);
                        elem9.setId(Integer.valueOf(partes[1]));
                        VentanaMultiplexorController aux9= new VentanaMultiplexorController();
                        aux9.init(con, stage, Pane1, scroll);
                        
                        con.getDibujos().add(elem9);
                        dibujo9.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo9);
                        aux9.eventos(elem9);
                        aux9.setIdMux(mux.getIdMux()+1);
                        break;
                        
                    default:
                        con.setContadorElemento(Integer.valueOf(partes[0]));
                }
            }
            controlador=con;
            redibujarLinea();
        }
        catch(IOException | NumberFormatException e){
            e.printStackTrace();
        }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
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
        String ruta_archivo="";
        try {
            ruta_archivo = manejador.getSelectedFile().getPath();
            //System.out.println(ruta_archivo);
            abrirTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardar trabajo
        }
    }   
    
    /**
     * Metodo que permite visualizar la conexion hacia delante del medidor de 
     * potencia o del analizador de espectro con otro elemento
     * @param elemG Elemento grafico del medidor de potencia o del analizador 
     * de espectro
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
        line.setStartY(elemG.getDibujo().getLayoutY()+7);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
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
     * @param elemG Elemento grafico del divisor optico
     */
    public void dibujarLineaFBG(ElementoGrafico elemG) {
        FBG fbg=(FBG)elemG.getComponente();
        
        for(int lap=0; lap<fbg.getSalidas()-1;lap++){
            if(fbg.getConexiones().get(lap).isConectadoSalida()){
                Line line= new Line();

                switch (fbg.getSalidas()) {
                    default:
                        line.setStartX(elemG.getDibujo().getLayoutX()+22);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(30+(5*(lap+1))));
                        break;
                }
                ElementoGrafico aux= new ElementoGrafico();
                //System.out.println("primer");
                for(int ir=0; ir<controlador.getDibujos().size();ir++){
                    if(fbg.getConexiones().get(lap).getElementoConectadoSalida().equals(controlador.getDibujos().get(ir).getDibujo().getText())){
                        aux=controlador.getDibujos().get(ir);
                        //aux.getComponente().setConectadoEntrada(true);
                        //aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    //System.out.println("cd");
                    line.setStrokeWidth(2);
                    line.setStroke(javafx.scene.paint.Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+12);
                    line.setEndY(aux.getDibujo().getLayoutY()+8);
                    //splitter.actuaizarSalidas(salida);
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
     * @param elem Elemento grafico del medidor de potencia o del analizador 
     * de espectro
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(javafx.scene.paint.Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        if(elem.getDibujo().getText().contains("spectrum")){
            line.setEndX(elem.getDibujo().getLayoutX()+12);
            line.setEndY(elem.getDibujo().getLayoutY()+6);
        }
        else if(elem.getDibujo().getText().contains("power")){
            line.setEndX(elem.getDibujo().getLayoutX()+5);
            line.setEndY(elem.getDibujo().getLayoutY()+6);
        }
        else{
            line.setEndX(elem.getDibujo().getLayoutX()+18);
            line.setEndY(elem.getDibujo().getLayoutY()+8);
        }
        setLinea(line);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
            
    }
    
    /**
     * Metodo utilizado para hacer visible la conexion entre elementos al abrir 
     * un trabajo
     */
    public void redibujarLinea(){
        for(int w=0; w<controlador.getDibujos().size();w++){
            ElementoGrafico elem=controlador.getDibujos().get(w);
            if(elem.getComponente().isConectadoSalida()){
                if(elem.getDibujo().getText().contains("connector")){
                    VentanaConectorController fue= new VentanaConectorController();
                    fue.init(controlador, stage, Pane1, scroll);
                    fue.dibujarLinea(elem);
                }
                else if(elem.getDibujo().getText().contains("source")){
                    VentanaFuenteController fue= new VentanaFuenteController();
                    fue.init(controlador, stage, Pane1, scroll);
                    fue.dibujarLinea(elem);
                }
                else if(elem.getDibujo().getText().contains("fiber")){
                    VentanaFibraController fue= new VentanaFibraController();
                    fue.init(controlador, stage, Pane1, scroll);
                    fue.dibujarLinea(elem);
                }
                else if(elem.getDibujo().getText().contains("splice")){
                    VentanaEmpalmeController fue= new VentanaEmpalmeController();
                    fue.init(controlador, stage, Pane1, scroll);
                    fue.dibujarLinea(elem);
                    
                }
            }
            if(elem.getDibujo().getText().contains("splitter")){
                VentanaSplitterController fue= new VentanaSplitterController();
                fue.init(controlador, stage, Pane1, scroll);
                System.out.println("dibujar lineas splitter");
                fue.dibujarLinea(elem);
            }
            if(elem.getDibujo().getText().contains("mux")){
                VentanaMultiplexorController fue= new VentanaMultiplexorController();
                fue.init(controlador, stage, Pane1, scroll);
                System.out.println("dibujar lineas mux");
                fue.dibujarLinea(elem);
            }
            if(elem.getDibujo().getText().contains("fbg")){
                VentanaFBGController fue= new VentanaFBGController();
                fue.init(controlador, stage, Pane1, scroll, elem);
                System.out.println("dibujar lineas fbg");
                fue.dibujarLineaFBG(elem);
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del divisor optico 
     * con el medidor de potencia
     * @param elem Elemento grafico del medidor de potencia
     * @param aux Elemento grafico del divisor optico
     * @param puerto Puerto salida del divisor optico
     */
    public void dibujarLineaAtrasSplitter(ElementoGrafico elem, ElementoGrafico aux, int puerto){
        Line line= new Line(); 
        if("splitter".equals(aux.getComponente().getNombre())){
            Splitter sptt=(Splitter) aux.getComponente();
            sptt.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);
        
            switch (sptt.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX()+50);
                    line.setStartY(aux.getDibujo().getLayoutY()+(24+(5*(puerto+1))));
                    break;
                case 4:
                    line.setStartX(aux.getDibujo().getLayoutX()+50);
                    line.setStartY(aux.getDibujo().getLayoutY()+(18+(5*(puerto+1))));
                    break;
                case 8:
                    line.setStartX(aux.getDibujo().getLayoutX()+80);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(9*(puerto+1))));
                    break;
                case 16:
                    line.setStartX(aux.getDibujo().getLayoutX()+94);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(5.1*(puerto+1))));
                    break;
                case 32:
                    line.setStartX(aux.getDibujo().getLayoutX()+110);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(3.2*(puerto+1))));
                    break;
                case 64:
                    line.setStartX(aux.getDibujo().getLayoutX()+120);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(2*(puerto+1))));
                    break;
                default:
                    break;
            }
            line.setEndX(elem.getDibujo().getLayoutX()+1);
            line.setEndY(elem.getDibujo().getLayoutY()+7);
            line.setVisible(true);
            Pane1.getChildren().add(line); 
            sptt.getConexiones().get(puerto).setLinea(line);
        }
        else if("fbg".equals(aux.getComponente().getNombre())){
            FBG sptt=(FBG) aux.getComponente();
            sptt.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);
        
            switch (sptt.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX()+50);
                    line.setStartY(aux.getDibujo().getLayoutY()+(24+(5*(puerto+1))));
                    break;
            }
        }
        else{
            Demultiplexor sptt=(Demultiplexor) aux.getComponente();
            sptt.getConexiones().get(puerto).getLinea().setVisible(false);
            line.setStrokeWidth(2);
            line.setStroke(javafx.scene.paint.Color.BLACK);
        
            switch (sptt.getSalidas()) {
                case 2:
                    line.setStartX(aux.getDibujo().getLayoutX()+50);
                    line.setStartY(aux.getDibujo().getLayoutY()+(24+(5*(puerto+1))));
                    break;
                case 4:
                    line.setStartX(aux.getDibujo().getLayoutX()+50);
                    line.setStartY(aux.getDibujo().getLayoutY()+(18+(5*(puerto+1))));
                    break;
                case 8:
                    line.setStartX(aux.getDibujo().getLayoutX()+80);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(9*(puerto+1))));
                    break;
                case 16:
                    line.setStartX(aux.getDibujo().getLayoutX()+94);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(5.1*(puerto+1))));
                    break;
                case 32:
                    line.setStartX(aux.getDibujo().getLayoutX()+110);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(3.2*(puerto+1))));
                    break;
                case 64:
                    line.setStartX(aux.getDibujo().getLayoutX()+120);
                    line.setStartY(aux.getDibujo().getLayoutY()+(10+(2*(puerto+1))));
                    break;
                default:
                    break;
            }
            line.setEndX(elem.getDibujo().getLayoutX()+1);
            line.setEndY(elem.getDibujo().getLayoutY()+7);
            line.setVisible(true);
            Pane1.getChildren().add(line); 
            sptt.getConexiones().get(puerto).setLinea(line);
        } 
    }
    
    /**
     * Metodo que delimita el movimiento en el eje X en el panel para que el 
     * elemento grafico no salga del area de trabajo
     */
    private boolean outSideParentBoundsX( Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = Pane1.getLayoutBounds();

        //check if too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }
        //check if too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }
        return false;
    }
    
    /**
     * Metodo que delimita el movimiento en el eje Y en el panel para que el 
     * elemento grafico no salga del area de trabajo
     */
    private boolean outSideParentBoundsY( Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = Pane1.getLayoutBounds();
        
        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }
        //check if too up
        if( parentBounds.getMinY()+179 >= (newY + childBounds.getMinY()) ) {
            return true ;
        }
        return false;
    }
    
}
