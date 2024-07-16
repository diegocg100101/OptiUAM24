
package optiuam.bc.controlador;

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
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Demultiplexor;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.PuertoSalida;

/**
 * Clase VentanDemultiplexorController la cual se encarga de instanciar un 
 * demultiplexor
 * @author Arturo Borja
 * @see ControladorGeneral
 */
public class VentanaDemultiplexorController extends ControladorGeneral implements Initializable {
    
    /**Identificador del demultiplexor*/
    static int idDemux = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico del demultiplexor*/
    ElementoGrafico elemG;
    /**Controlador del demultiplexor*/
    VentanaDemultiplexorController demultiplexorControl;
    /**Posicion del demultiplexor en el eje X*/
    static double posX;
    /**Posicion del demultiplexor en el eje Y*/
    static double posY;
    
    /**Lista desplegable del numero de salidas que tiene el demultiplexor*/
    @FXML
    ComboBox cboxNumeroSalidas;
    /**Lista desplegable de cada salida que tiene el demultiplexor*/
    @FXML
    ComboBox cboxSalidas;
    /**Lista desplegable de elementos disponibles para conectar el demultiplexor*/
    @FXML
    ComboBox cboxConectarA;
    /**Boton para desconectar el demultiplexor*/
    @FXML
    Button btnDesconectar;
    /**Boton para crear un demultiplexor*/
    @FXML
    Button btnCrear;
    /**Boton para modificar el demultiplexor*/
    @FXML
    Button btnModificar;
    /**Caja de texto para ingresar la perdida de insercion del demultiplexor*/
    @FXML
    TextField txtPerdidaInsercion;
    /**Etiqueta de la lista desplegable de cada salida del demultiplexor*/
    @FXML
    Label lblSalida;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar 
     * el demultiplexor*/
    @FXML
    Label lblConectarA;
    /**Separador de la ventana del demultiplexor*/
    @FXML
    Separator separator;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo que muestra el identificador del demultiplexor
     * @return idDemux
     */
    public static int getIdDemux() {
        return idDemux;
    }

    /**
     * Metodo que modifica el identificador del demultiplexor
     * @param idDemux Identificador del demultiplexor
     */
    public static void setIdDemux(int idDemux) {
        VentanaDemultiplexorController.idDemux = idDemux;
    }

    /**
     * Metodo que muestra la posicion del demultiplexor en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion del demultiplexor en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaDemultiplexorController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion del demultiplexor en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion del demultiplexor en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaDemultiplexorController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana del demultiplexor
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxNumeroSalidas.getItems().removeAll(cboxNumeroSalidas.getItems());
        cboxNumeroSalidas.getItems().addAll("2", "4", "8");
        cboxNumeroSalidas.getSelectionModel().select("2");
        Tooltip perdidaI = new Tooltip();
        perdidaI.setText("2: The loss must be max: 0.6"
                + "\n4: The loss must be max: 1.6"
                + "\n8: The loss must be max: 2.5");
        txtPerdidaInsercion.setTooltip(perdidaI);
        
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        lblSalida.setVisible(false);
        cboxSalidas.setVisible(false);
        btnModificar.setVisible(false);
    }   
    
    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param scroll Espacio en el cual el usuario puede desplazarse
     */
    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }
    
    /**
     * Metodo el cual captura los datos obtenidos de la ventana del demultiplexor 
     * y crea uno
     * @param event Representa cualquier tipo de accion 
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        int salidas=0, longitudOnda=0, id=0;
        double perdida;
        
        if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
            salidas = 2;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
            salidas = 4;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
            salidas = 8;
        }
        
        cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
        for(int i = 0; i<salidas;i++){
            cboxSalidas.getItems().addAll(String.valueOf(i+1));
        }
        if (txtPerdidaInsercion.getText().isEmpty() || txtPerdidaInsercion.getText().compareTo("")==0 || !txtPerdidaInsercion.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else{
            perdida = Double.parseDouble(txtPerdidaInsercion.getText());
            txtPerdidaInsercion.setText(String.valueOf(perdida));
            Demultiplexor demux= new Demultiplexor();
            demux.setConectadoEntrada(false);
            demux.setConectadoSalida(false);
            demux.setPerdidaInsercion(perdida);
            demux.setSalidas(salidas);
            //demux.setLongitudOnda(longitudOnda);
            demux.setNombre("demux");
            demux.setIdDemux(idDemux);
            demux.modificarSalidas(salidas);
            idDemux++;
            guardarDemux(demux);
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda el demultiplexor en el panel
     * @param demux Demultiplexor con valores almacenados
     */
    public void guardarDemux(Demultiplexor demux) {
        demux.setId(controlador.getContadorElemento());
        controlador.getElementos().add(demux);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(demux);
        elem.setId(controlador.getContadorElemento());
        
        if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_demux2.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_demux4.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_demux8.png")));
        }
        
        dibujo.setText(demux.getNombre() + "_"+ demux.getIdDemux());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDemultiplexer created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que duplica un demultiplexor
     * @param demux Demultiplexor a duplicar
     * @param el Elemento grafico del demultiplexor a duplicar
     */
    public void duplicarDemux(Demultiplexor demux, ElementoGrafico el) {
        demux.setId(controlador.getContadorElemento());
        demux.setNombre("demux");
        controlador.getElementos().add(demux);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(demux);
        elem.setId(controlador.getContadorElemento());
        
        if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_demux2.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_demux4.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_demux8.png")));
        }
        
        dibujo.setText(demux.getNombre() + "_"+ demux.getIdDemux());
        dibujo.setContentDisplay(ContentDisplay.TOP);
            
        dibujo.setLayoutX(el.getDibujo().getLayoutX()+35);
        dibujo.setLayoutY(el.getDibujo().getLayoutY()+20);
        
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDuplicate multiplexer!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    /**
     * Metodo el cual le proporciona eventos al demultiplexor tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico del demultiplexor
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
                    elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY()+event.getY()+1);}
                    
                        elem.getComponente().getLinea().setVisible(false);
                        dibujarLinea(elem);
                    
                    if(elem.getComponente().isConectadoEntrada()){
                        ElementoGrafico aux;
                        for(int it=0; it<controlador.getDibujos().size();it++){
                            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                                aux=controlador.getDibujos().get(it);
                                elem.getComponente().getLinea().setVisible(false);
                            }
                        }
                        dibujarLineaAtras(elem);
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
                    try{
                        Stage stage1 = new Stage(StageStyle.UTILITY);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaDemultiplexor.fxml"));
                        Parent root = loader.load();
                        
                        //Se crea una instancia del controlador del demux
                        VentanaDemultiplexorController demuxController = (VentanaDemultiplexorController) loader.getController();
                        demuxController.init(controlador, stage, Pane1, scroll);
                        //demultiplexorController.init(controlador, this.stage, this.Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        demuxController.init2(elem,demuxController);
                        Demultiplexor demult= (Demultiplexor) elem.getComponente();
                        demuxController.btnCrear.setVisible(false);
                        demuxController.separator.setVisible(true);
                        demuxController.lblSalida.setVisible(true);
                        demuxController.cboxSalidas.setVisible(true);
                        demuxController.btnDesconectar.setVisible(true);
                        demuxController.lblConectarA.setVisible(true);
                        demuxController.cboxConectarA.setVisible(true);
                        demuxController.btnModificar.setVisible(true);
                        
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setScene(scene);
                        stage1.setResizable(false);
                        stage1.showAndWait();
                    }
                    catch(IOException ex){
                        Logger.getLogger(VentanaDemultiplexorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenu(elem);
                }
        });
    }
    
    /**
     * Metodo el cual muestra un menu de acciones para duplicar, eliminar o 
     * ver propiedades del demultiplexor
     * @param dibujo Elemento grafico del demultiplexor
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        MenuItem menuItem3 = new MenuItem("-Deleted");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    //System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Demultiplexor aux=new Demultiplexor();
                    Demultiplexor aux1=(Demultiplexor)controlador.getElementos().get(elemento);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setElementoConectadoEntrada("");
                    aux.setElementoConectadoSalida("");
                    //aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setNombre(aux1.getNombre());
                    aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    aux.setSalidas(aux1.getSalidas());
                    aux.setIdDemux(idDemux);
                    //LinkedList conex= new LinkedList();
                    for(int cz=0; cz<aux1.getSalidas();cz++){
                        PuertoSalida p=new PuertoSalida();
                        aux.getConexiones().add(p);        
                    }
                    
                    duplicarDemux(aux,dibujo);
                    //System.out.println(aux);
                    idDemux++;
                    break;
                }
            }
        });

        /*Eliminar*/
        menuItem3.setOnAction(e ->{
            if(dibujo.getComponente().isConectadoSalida()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        //System.out.println();
                        aux.setConectadoEntrada(false);
                        aux.setElementoConectadoEntrada("-");
                        dibujo.getComponente().getLinea().setVisible(false);
                    }
                }   
            }
            if(dibujo.getComponente().isConectadoEntrada()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        aux.setConectadoSalida(false);
                        aux.setElementoConectadoSalida("-");
                        aux.getLinea().setVisible(false);
                    }
                }
            }
            Demultiplexor dm=(Demultiplexor)dibujo.getComponente();
            for(int cz=0; cz<dm.getConexiones().size(); cz++){
                if(dm.getConexiones().get(cz).isConectadoSalida()){
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(dm.getConexiones().get(cz).getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                            Componente aux= controlador.getElementos().get(elemento);
                            //System.out.println();
                            aux.setConectadoEntrada(false);
                            aux.setElementoConectadoEntrada("-");
                            dm.getConexiones().get(cz).getLinea().setVisible(false);
                        }
                    }
                }
            }
            
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Demultiplexor aux= (Demultiplexor)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            dibujo.getDibujo().setVisible(false);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nRemoved demultiplexer!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();

        });
        
        /*Propiedades*/
        menuItem4.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Stage s = new Stage(StageStyle.DECORATED);
                    Image ico = new Image("images/ico_demux.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Demultiplexor aux= (Demultiplexor)controlador.getElementos().get(elemento);
                    Label label;
                    label = new Label("  Name: "+aux.getNombre()+
                        "\n  Id: "+aux.getIdDemux()+
                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                        "\n  Output :"+aux.getElementoConectadoSalida()/*+
                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                        "\n  Outputs: "+aux.getSalidas()+
                        "\n  Insertion Loss: "+aux.getPerdidaInsercion()+" dB"*/);
                    Scene scene = new Scene(label, 190, 130);
                    s.setScene(scene);
                    s.setResizable(false);
                    s.showAndWait();
                }
            }
        });

        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    /**
     * Metodo para cerrar la ventana del demultiplexor
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para modificar el demultiplexor
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     */
    @FXML
    public void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        Demultiplexor aux = (Demultiplexor) elemG.getComponente();
        int salidas=0, longitudOnda=0, id=0;
        double perdida;
        
        if((demultiplexorControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }
        else{
            conectar();
        }
        
        for(int i = 0; i<salidas;i++){
            cboxSalidas.getItems().addAll(String.valueOf(i+1));
            cboxSalidas.getSelectionModel().selectFirst();
        }
        
        if (txtPerdidaInsercion.getText().isEmpty() || txtPerdidaInsercion.getText().compareTo("")==0 || !txtPerdidaInsercion.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else{
            if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
                salidas = 2;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_demux2.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
                salidas = 4;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_demux4.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
                salidas = 8;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_demux8.png")));
            }
            perdida = Double.parseDouble(txtPerdidaInsercion.getText());
            txtPerdidaInsercion.setText(String.valueOf(perdida));
            aux.setPerdidaInsercion(perdida);
            aux.setSalidas(salidas);
            //aux.setLongitudOnda(longitudOnda);
            aux.setNombre("demux");
            cerrarVentana(event);

            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified demultiplexer!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
            
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
    
    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento
     * @param elem Elemento grafico
     * @param demultiplexorController Controlador del demultiplexor
     */
    public void init2(ElementoGrafico elem, VentanaDemultiplexorController demultiplexorController) {
        this.elemG=elem;
        this.demultiplexorControl=demultiplexorController;
        
        if(elemG.getComponente().isConectadoSalida()==true){
            demultiplexorControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
           demultiplexorControl.cboxConectarA.getItems().add("Desconected");
            demultiplexorControl.cboxConectarA.getSelectionModel().select(0);
             for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                    "power".equals(controlador.getElementos().get(elemento).getNombre())){
                     if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        demultiplexorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                         //System.out.println("");
                    }
                }
             }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Demultiplexor spl= (Demultiplexor)controlador.getElementos().get(elemento);
                
                cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
                for(int i = 0; i<spl.getSalidas();i++){
                    cboxSalidas.getItems().addAll(String.valueOf(i+1));
                    cboxSalidas.getSelectionModel().selectFirst();
                }
                demultiplexorControl.txtPerdidaInsercion.setText(String.valueOf(spl.getPerdidaInsercion()));
                int salix=0;
                switch (spl.getSalidas()) {
                    case 2:
                        salix=0;
                        break;
                    case 4:
                        salix=1;
                        break;
                    case 8:
                        salix=2;
                        break;
                    default:
                        break;
                }
                demultiplexorControl.cboxNumeroSalidas.getSelectionModel().select(salix);
                demultiplexorControl.cboxNumeroSalidas.setDisable(true);
            }
        }
    }
    
    /**
     * Metodo que muestra la conexion de la salida n del demultiplexor a un componente 
     */
    @FXML
    public void actCbox(){
        if(!demultiplexorControl.btnCrear.isVisible()){
            demultiplexorControl.cboxConectarA.getItems().clear();
            if(demultiplexorControl.cboxSalidas.getSelectionModel().getSelectedIndex()==0){
                if(elemG.getComponente().isConectadoSalida()==true){
                    demultiplexorControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
                }
                else{

                    demultiplexorControl.cboxConectarA.getItems().add("Desconected");
                    demultiplexorControl.cboxConectarA.getSelectionModel().select(0);
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "power".equals(controlador.getElementos().get(elemento).getNombre())){
                            if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                                demultiplexorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                            }
                        }
                    }
                }
            }
            else{
                Demultiplexor aux=(Demultiplexor)elemG.getComponente();
                if(aux.getConexiones().get(demultiplexorControl.cboxSalidas.getSelectionModel().getSelectedIndex()-1).isConectadoSalida()==true){
                    demultiplexorControl.cboxConectarA.getSelectionModel().select(aux.getConexiones().get(demultiplexorControl.cboxSalidas.getSelectionModel().getSelectedIndex()-1).getElementoConectadoSalida());
                }
                else{
                    demultiplexorControl.cboxConectarA.getItems().add("Desconected");
                    demultiplexorControl.cboxConectarA.getSelectionModel().select(0);
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "power".equals(controlador.getElementos().get(elemento).getNombre())){
                            if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                                demultiplexorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante del demultiplexor
     * con otro elemento
     * @param elemG Elemento grafico del demultiplexor
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Demultiplexor demux=(Demultiplexor)elemG.getComponente();
        if(demux.isConectadoSalida()){
            Line line= new Line();   
            switch (demux.getSalidas()) {
                case 2:
                    line.setStartX(elemG.getDibujo().getLayoutX()+45);
                    line.setStartY(elemG.getDibujo().getLayoutY()+24);
                    break;
                case 4:
                    line.setStartX(elemG.getDibujo().getLayoutX()+45);
                    line.setStartY(elemG.getDibujo().getLayoutY()+15);
                    break;
                case 8:
                    line.setStartX(elemG.getDibujo().getLayoutX()+65);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                default:
                    break;
            }

            ElementoGrafico aux= new ElementoGrafico();
            for(int it=0; it<controlador.getDibujos().size();it++){
                if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                    aux=controlador.getDibujos().get(it);
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+3);
                    line.setEndY(aux.getDibujo().getLayoutY()+10);
                    line.setVisible(true);
                    Pane1.getChildren().add(line); 
                    elemG.getComponente().setLinea(line);  
                }
            }
        }
        for(int lap=0; lap<demux.getSalidas()-1;lap++){
            if(demux.getConexiones().get(lap).isConectadoSalida()){
                Line line= new Line();

                switch (demux.getSalidas()) {
                    case 2:
                        line.setStartX(elemG.getDibujo().getLayoutX()+45);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(24+(10*(lap+1))));
                        break;
                    case 4:
                        line.setStartX(elemG.getDibujo().getLayoutX()+45);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(15+(10*(lap+1))));
                        break;
                    case 8:
                        line.setStartX(elemG.getDibujo().getLayoutX()+65);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(9*(lap+1))));
                        break;
                    
                    default:
                        break;
                }
                ElementoGrafico aux= new ElementoGrafico();
                //System.out.println("primer");
                for(int ir=0; ir<controlador.getDibujos().size();ir++){
                    if(demux.getConexiones().get(lap).getElementoConectadoSalida().equals(controlador.getDibujos().get(ir).getDibujo().getText())){
                        aux=controlador.getDibujos().get(ir);
                        aux.getComponente().setConectadoEntrada(true);
                        aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    //System.out.println("cd");
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+3);
                    line.setEndY(aux.getDibujo().getLayoutY()+10);
                    //splitter.actuaizarSalidas(salida);
                    line.setVisible(true);
                    Pane1.getChildren().add(line);
                    demux.getConexiones().get(lap).setLinea(line);
                    }
                }
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del demultiplexor  
     * con otro elemento
     * @param elem Elemento grafico del demultiplexor
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        Demultiplexor demux= (Demultiplexor) elem.getComponente();
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        switch (demux.getSalidas()) {
            case 2:
            case 4:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+26);
                break;
            case 8:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+41);
                break;
            case 16:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+50);
                break;
            case 32:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+60);
                break;
            case 64:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+70);
                break;
            default:
                break;
        }
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
    }
   
    /**
     * Metodo que elimina la conexion del demultiplexor
     * @param elem Elemento grafico del demultiplexor
     */
    public void borrarLineaDemux(ElementoGrafico elem){
        Demultiplexor demux=(Demultiplexor)elem.getComponente();
            if(demux.isConectadoSalida()){
                demux.getLinea().setVisible(false);
            }
            for(int po=0; po<demux.getSalidas()-1;po++){
                if(demux.getConexiones().get(po).isConectadoSalida()){
                    demux.getConexiones().get(po).getLinea().setVisible(false);
                }
            }
    }
    
    /**
     * Metodo que conecta la salida n del demultiplexor a un componente
     */
    public void conectar(){
        int salida = cboxSalidas.getSelectionModel().getSelectedIndex();
        
        String componente = cboxConectarA.getSelectionModel().getSelectedItem().toString();
        if(cboxConectarA.getItems().size() <= 1){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe demultiplexer cannot be connected to anything",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            if(conectarDemux(salida,componente)){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "\nDemultiplexer output "+(salida+1)+ " was connected to component: "+componente,
                        aceptar);
                alert.setTitle("Succes");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }
    
    /**
     * Metodo que indica si una salida del demultiplexor esta conectado a un componente
     * @param salida Salida n del demultiplexor
     * @param componente Componente a conectar
     * @return Demultiplexor conectado
     */
    public boolean conectarDemux(int salida,String componente){
        Demultiplexor demux = (Demultiplexor) elemG.getComponente();
        
        if (salida==0) { 
            if(demux.isConectadoSalida()==false){
                demux.setConectadoSalida(true);
                demux.setElementoConectadoSalida(componente);
                Line line= new Line();
                line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
                line.setStartY(elemG.getDibujo().getLayoutY()+10);
                ElementoGrafico aux= new ElementoGrafico();
                for(int it=0; it<controlador.getDibujos().size();it++){
                    if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                        aux=controlador.getDibujos().get(it);
                        aux.getComponente().setConectadoEntrada(true);
                        aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                        line.setStrokeWidth(2);
                        line.setStroke(Color.BLACK);
                        line.setEndX(aux.getDibujo().getLayoutX()+3);
                        line.setEndY(aux.getDibujo().getLayoutY()+20);
                        demux.setLinea(line);
                        line.setVisible(true);
                        Pane1.getChildren().add(line);
                        return true;
                    }
                }
            }
            else{
                System.out.println("ya esta conectado");
            }
        }
        else if(salida>0&&salida<=demux.getSalidas()){
            if(demux.getConexiones().get(salida-1).isConectadoSalida()==false){
                //dibujarLinea(elemG);
                demux.getConexiones().get(salida-1).setConectadoSalida(true);
                demux.getConexiones().get(salida-1).setElementoConectadoSalida(componente);
                Line line= new Line();
                /*line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
                line.setStartY(elemG.getDibujo().getLayoutY()+(10+(10*(salida))));*/
                switch (demux.getSalidas()) {
                    case 2:
                        line.setStartX(elemG.getDibujo().getLayoutX()+45);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(24+(10*(salida))));
                        break;
                    case 4:
                        line.setStartX(elemG.getDibujo().getLayoutX()+45);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(18+(10*(salida))));
                        break;
                    case 8:
                        line.setStartX(elemG.getDibujo().getLayoutX()+70);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(9*(salida))));
                        break;
                    default:
                        break;
                }
                ElementoGrafico aux= new ElementoGrafico();
                
                for(int it=0; it<controlador.getDibujos().size();it++){
                    if(componente.equals(controlador.getDibujos().get(it).getDibujo().getText())){
                        aux=controlador.getDibujos().get(it);
                        aux.getComponente().setConectadoEntrada(true);
                    aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+3);
                    line.setEndY(aux.getDibujo().getLayoutY()+20);
                    demux.getConexiones().get(salida-1).setLinea(line);
                    //splitter.actuaizarSalidas(salida);
                    line.setVisible(true);
                    Pane1.getChildren().add(line);
                    return true;
                    }
                }
            }
            else{
                System.out.println("ya esta conectado");
            }
        }
        return false;
    }
    
    /**
     * Metodo para desconectar el demultiplexor
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(demultiplexorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                //System.out.println(comp.getNombre());
                break;
            }
        }
        demultiplexorControl.cboxConectarA.getSelectionModel().select(0);
        if(demultiplexorControl.cboxSalidas.getSelectionModel().getSelectedItem().equals("1")){
            if(elemG.getComponente().isConectadoSalida()){
                elemG.getComponente().setConectadoSalida(false);
                elemG.getComponente().setElementoConectadoSalida("");
                elemG.getComponente().getLinea().setVisible(false);
            }
        }
        else{
            int salida= Integer.parseInt(demultiplexorControl.cboxSalidas.getSelectionModel().getSelectedItem().toString())-2;
            Demultiplexor aux=(Demultiplexor) elemG.getComponente();
            if(aux.getConexiones().get(salida).isConectadoSalida()){
                aux.getConexiones().get(salida).setConectadoSalida(false);
                aux.getConexiones().get(salida).setElementoConectadoSalida("");
                aux.getConexiones().get(salida).getLinea().setVisible(false);
            }
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected demultiplexer!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }
    
}
