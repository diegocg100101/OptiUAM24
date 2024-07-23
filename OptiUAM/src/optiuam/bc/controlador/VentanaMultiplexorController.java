
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
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Multiplexor;

/**
 * Clase VentanMultiplexorController la cual se encarga de instanciar un 
 * multiplexor
 * @author Arturo Borja
 * @see ControladorGeneral
 */
public class VentanaMultiplexorController extends ControladorGeneral implements Initializable {
    
    /**Identificador del multiplexor*/
    static int idMux = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico del multiplexor*/
    ElementoGrafico elemG;
    /**Controlador del multiplexor*/
    VentanaMultiplexorController multiplexorControl;
    /**Posicion del multiplexor en el eje X*/
    static double posX;
    /**Posicion del multiplexor en el eje Y*/
    static double posY;
    /**Longitudes de onda del multiplexor*/
    static String longitudesOnda[] = {"1470", "1490", "1510", "1530", "1550", 
        "1570", "1590", "1610"}; 
    /*
        Mux 2: longitudesOnda[3], longitudesOnda[4], perdida insercion max 0.6
        Mux 4: longitudesOnda[2] a longitudesOnda[5], perdida insercion max 1.6
        Mux 8: longitudesOnda[], perdida insercion max 2.5
    */
    
    /**Lista desplegable del numero de entradas que tiene el multiplexor*/
    @FXML
    public ComboBox cboxNumeroEntradas;
    /**Lista desplegable de cada entrada que tiene el multiplexor*/
    @FXML
    ComboBox cboxEntradas;
    /**Lista desplegable de elementos disponibles para conectar el multiplexor*/
    @FXML
    ComboBox cboxConectarA;
    /**Lista desplegable de longitudes de onda del multiplexor*/
    @FXML
    ComboBox cboxLongitudesOnda;
    /**Boton para desconectar el multiplexor*/
    @FXML
    Button btnDesconectar;
    /**Boton para crear un multiplexor*/
    @FXML
    Button btnCrear;
    /**Boton para modificar el multiplexor*/
    @FXML
    Button btnModificar;
    /**Caja de texto para ingresar la perdida de insercion del multiplexor*/
    @FXML
    TextField txtPerdidaInsercion;
    /**Etiqueta de la lista desplegable de cada entrada del multiplexor*/
    @FXML
    Label lblEntrada;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar 
     * el multiplexor*/
    @FXML
    Label lblConectarA;
    /**Etiqueta de la lista desplegable de longitudes de onda del multiplexor*/
    @FXML
    Label lblLongitudesOnda;
    /**Separador de la ventana del multiplexor*/
    @FXML
    Separator separator;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo que muestra el identificador del multiplexor
     * @return idMux
     */
    public static int getIdMux() {
        return idMux;
    }
    
    /**
     * Metodo que modifica el identificador del multiplexor
     * @param idMux Identificador del multiplexor
     */
    public static void setIdMux(int idMux) {
        VentanaMultiplexorController.idMux = idMux;
    }
    
    /**
     * Metodo que muestra la posicion del multiplexor en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion del multiplexor en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaMultiplexorController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion del multiplexor en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion del multiplexor en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaMultiplexorController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana del multiplexor
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxNumeroEntradas.getItems().removeAll(cboxNumeroEntradas.getItems());
        cboxNumeroEntradas.getItems().addAll("2", "4", "8");
        cboxNumeroEntradas.getSelectionModel().select("2");
        Tooltip perdidaI = new Tooltip();
        perdidaI.setText("2: The loss must be max: 0.6"
                + "\n4: The loss must be max: 1.6"
                + "\n8: The loss must be max: 2.5");
        txtPerdidaInsercion.setTooltip(perdidaI);
        
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
        lblLongitudesOnda.setVisible(false);
        cboxLongitudesOnda.setVisible(false);
    }    
    
    /**
     * Metodo el cual captura los datos obtenidos de la ventana del multiplexor 
     * optico y crea uno
     * @param event Representa cualquier tipo de accion 
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        int entradas=0;
        double perdida;
       
        if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("2")){
            entradas = 2;
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("4")){
            entradas = 4;
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("8")){
            entradas = 8;
        }
        if (txtPerdidaInsercion.getText().isEmpty() || txtPerdidaInsercion.getText().compareTo("")==0 
                || !txtPerdidaInsercion.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
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
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("2") && 
                Double.parseDouble(txtPerdidaInsercion.getText()) > 0.6 || 
                Double.parseDouble(txtPerdidaInsercion.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + 0.6);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + 0.6,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("4") && 
                Double.parseDouble(txtPerdidaInsercion.getText()) > 1.6 || 
                Double.parseDouble(txtPerdidaInsercion.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + 1.6);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + 1.6,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("8") && 
                Double.parseDouble(txtPerdidaInsercion.getText()) > 2.5 || 
                Double.parseDouble(txtPerdidaInsercion.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + 2.5);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + 2.5,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else{
            perdida = Double.parseDouble(txtPerdidaInsercion.getText());
            txtPerdidaInsercion.setText(String.valueOf(perdida));
            Multiplexor mux = new Multiplexor();
            mux.setConectadoEntrada(false);
            mux.setConectadoSalida(false);
            mux.setPerdidaInsercion(perdida);
            mux.setEntradas(entradas);
            mux.setNombre("mux");
            mux.setIdMux(idMux);
            mux.modificarEntradas(entradas);
            idMux++;
            guardarMultiplexor(mux);
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda el multiplexor en el panel
     * @param mux Multiplexor con valores almacenados
     */
    public void guardarMultiplexor(Multiplexor mux) {
        mux.setId(controlador.getContadorElemento());
        controlador.getElementos().add(mux);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(mux);
        elem.setId(controlador.getContadorElemento());
        
        if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("2")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_mux2.png")));
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("4")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_mux4.png")));
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("8")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_mux8.png")));
        }
        
        dibujo.setText(mux.getNombre() + "_"+ mux.getIdMux());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nMultiplexer created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que duplica un multiplexor
     * @param mux Multiplexor a duplicar
     * @param el Elemento grafico del divisor optico a duplicar
     */
    public void duplicarMultiplexor(Multiplexor mux, ElementoGrafico el) {
        mux.setId(controlador.getContadorElemento());
        mux.setNombre("mux");
        controlador.getElementos().add(mux);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(mux);
        elem.setId(controlador.getContadorElemento());
        
        if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("2")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_mux2.png")));
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("4")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_mux4.png")));
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("8")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_mux8.png")));
        }
        
        dibujo.setText(mux.getNombre() + "_"+ mux.getIdMux());
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
     * Metodo el cual le proporciona eventos al multiplexor tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico del divisor optico
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
                }
                else{
                    elem.getDibujo().setLayoutX(Pane1.getChildren().get(j).getLayoutX()+event.getX()+1);
                }
                if(outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }
                else{
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
                            aux.getComponente().getLinea().setVisible(false);
                            dibujarLineaAtras(elem); 
                        }
                    }
                }
                Multiplexor k= (Multiplexor) elem.getComponente();
                for(int p=0;p<k.getEntradas()-1;p++){
                    if(k.getConexionEntradas().get(p).isConectadoEntrada()){
                        ElementoGrafico g;
                        for(int it=0; it<controlador.getDibujos().size();it++){
                            if(k.getConexionEntradas().get(p).getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                                g=controlador.getDibujos().get(it);
                                g.getComponente().getLinea().setVisible(false);
                                dibujarLineaAtras2(elem, g, p+1);
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
            if(event.getButton()==MouseButton.PRIMARY){
                try{
                    Stage stage1 = new Stage(StageStyle.UTILITY);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaMultiplexor.fxml"));
                    Parent root = loader.load();
                    VentanaMultiplexorController multiplexorController = (VentanaMultiplexorController) loader.getController();
                    multiplexorController.init(controlador, stage, Pane1, scroll,ventana_principal);
                    multiplexorController.init2(elem,multiplexorController);
                    Multiplexor mult= (Multiplexor) elem.getComponente();
                    multiplexorController.btnCrear.setVisible(false);
                    multiplexorController.lblLongitudesOnda.setVisible(true);
                    multiplexorController.cboxLongitudesOnda.setVisible(true);
                    multiplexorController.separator.setVisible(true);
                    multiplexorController.btnDesconectar.setVisible(true);
                    multiplexorController.lblConectarA.setVisible(true);
                    multiplexorController.cboxConectarA.setVisible(true);
                    multiplexorController.btnModificar.setVisible(true);

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
                    Logger.getLogger(VentanaMultiplexorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(event.getButton()==MouseButton.SECONDARY){
                mostrarMenu(elem);
            }
        });
    }
    
    /**
     * Metodo el cual muestra un menu de acciones para duplicar, eliminar o 
     * ver propiedades del multiplexor
     * @param dibujo Elemento grafico del multiplexor
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicate");
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Multiplexor aux=new Multiplexor();
                    Multiplexor aux1=(Multiplexor)controlador.getElementos().get(elemento);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setElementoConectadoEntrada("");
                    aux.setElementoConectadoSalida("");
                    aux.setNombre(aux1.getNombre());
                    aux.setIdMux(idMux);
                    duplicarMultiplexor(aux,dibujo);
                    idMux++;
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
            Multiplexor sp=(Multiplexor)dibujo.getComponente();
            for(int cz=0; cz<sp.getConexionEntradas().size(); cz++){
                if(sp.getConexionEntradas().get(cz).isConectadoEntrada()){
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(sp.getConexionEntradas().get(cz).getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                            Componente aux= controlador.getElementos().get(elemento);
                            aux.setConectadoSalida(false);
                            aux.setElementoConectadoSalida("");
                            aux.getLinea().setVisible(false);
                        }
                    }
                }
            }
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Multiplexor aux= (Multiplexor)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            dibujo.getDibujo().setVisible(false);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nRemoved multiplexer!",
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
                    Image ico = new Image("images/ico_mux.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Multiplexor aux= (Multiplexor)controlador.getElementos().get(elemento);
                    Label label;
                    label = new Label("  Name: "+aux.getNombre()+
                        "\n  Id: "+aux.getIdMux()+
                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                        "\n  Output :"+aux.getElementoConectadoSalida()+
                        /*"\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                        "\n  Outputs: "+aux.getSalidas()+*/
                        "\n  Insertion Loss: "+aux.getPerdidaInsercion()+" dB");
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
     * Metodo para cerrar la ventana del multiplexor
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar el multiplexor
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(multiplexorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                break;
            }
        }
        multiplexorControl.cboxConectarA.getSelectionModel().select(0);
        if(elemG.getComponente().isConectadoSalida()){
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
      
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected multiplexer!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar el multiplexor
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     */
    @FXML
    public void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        Multiplexor aux = (Multiplexor) elemG.getComponente();
        int entradas=0, longitudOnda=0;
        double perdida;
       
        if((multiplexorControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }
        else{
            if(aux.isConectadoSalida()){
                elemG.getComponente().getLinea().setVisible(false);
            }
            aux.setConectadoSalida(true);
            
            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(multiplexorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    ElementoGrafico eg= controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(eg.getDibujo().getText());
                    aux.setConectadoSalida(true);
                    eg.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    eg.getComponente().setConectadoEntrada(true);
                    Multiplexor muxAu=(Multiplexor) elemG.getComponente();
                    
                    for(int f=0; f<muxAu.getConexionEntradas().size();f++){
                        if(muxAu.getSeñalEntrada()!=null){
                            ventana_principal.elemConected(elemG.getComponente(),false);
                            break;
                        }
                        else if(muxAu.getConexionEntradas().get(f).getSeñalEntrada()!=null){
                            ventana_principal.elemConected(elemG.getComponente(),false);
                            break;
                        }
                    }
                    if(elemG.getComponente().getSeñalEntrada()!=null){
                        elemG.getComponente().setSeñalSalida(null);
                        ventana_principal.elemConected(aux,true);
                    }
                    break;
                }
            }
            dibujarLinea(elemG);
        }
        if (txtPerdidaInsercion.getText().isEmpty() || txtPerdidaInsercion.getText().compareTo("")==0 
                || !txtPerdidaInsercion.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
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
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("2") && 
                Double.parseDouble(txtPerdidaInsercion.getText()) > 0.6 || 
                Double.parseDouble(txtPerdidaInsercion.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + 0.6);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + 0.6,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("4") && 
                Double.parseDouble(txtPerdidaInsercion.getText()) > 1.6 || 
                Double.parseDouble(txtPerdidaInsercion.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + 1.6);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + 1.6,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("8") && 
                Double.parseDouble(txtPerdidaInsercion.getText()) > 2.5 || 
                Double.parseDouble(txtPerdidaInsercion.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + 2.5);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + 2.5,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else{
            if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("2")){
                entradas = 2;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_mux2.png")));
            }
            else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("4")){
                entradas = 4;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_mux4.png")));
            }
            else if(cboxNumeroEntradas.getSelectionModel().getSelectedItem().equals("8")){
                entradas = 8;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_mux8.png")));
            }
            perdida = Double.parseDouble(txtPerdidaInsercion.getText());
            txtPerdidaInsercion.setText(String.valueOf(perdida));
            aux.setPerdidaInsercion(perdida);
            aux.setNombre("mux");
            cerrarVentana(event);
            VentanaPrincipal.btnStart = false;

            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified multiplexer!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param scroll Espacio en el cual el usuario puede desplazarse
     * @param ventana Ventana principal
     */
    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll, VentanaPrincipal ventana) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
        this.ventana_principal= ventana;
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
     * Metodo que permite visualizar la conexion hacia delante del multiplexor
     * con otro elemento
     * @param elemG Elemento grafico del multiplexor
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line(); 
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        Multiplexor c=(Multiplexor) elemG.getComponente();
        switch (c.getEntradas()) {
            case 2:
                line.setStartX(elemG.getDibujo().getLayoutX()+30);
                line.setStartY(elemG.getDibujo().getLayoutY()+28);
                break;
            case 4:
                line.setStartX(elemG.getDibujo().getLayoutX()+30);
                line.setStartY(elemG.getDibujo().getLayoutY()+28);
                break;
            case 8:
                line.setStartX(elemG.getDibujo().getLayoutX()+58);
                line.setStartY(elemG.getDibujo().getLayoutY()+41);
                break;
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX()+3);
        line.setEndY(aux.getDibujo().getLayoutY()+12);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del multiplexor  
     * con otro elemento
     * @param elem Elemento grafico del multiplexor
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
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth()-3);
        line.setStartY(aux.getDibujo().getLayoutY()+20);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+10);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del multiplexor  
     * con otro elemento de acuerdo al numero de entradas
     * @param elem Elemento grafico del multiplexor
     * @param aux Elemento grafico del componente conectado a n entrada del multiplexor
     * @param num Numero de entradas del multiplexor
     */
    public void dibujarLineaAtras2(ElementoGrafico elem,ElementoGrafico aux,int num) {
        Line line= new Line();   
        Multiplexor m=(Multiplexor) elem.getComponente();
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth()-3);
        line.setStartY(aux.getDibujo().getLayoutY()+20);
        switch (m.getEntradas()) {
            case 2:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+20+13);
                break;
            case 4:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+14+(num*10));
                break;
            case 8:
                line.setEndX(elem.getDibujo().getLayoutX()+8);
                line.setEndY(elem.getDibujo().getLayoutY()+7+(num*9.4));
                break;
        }
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
    }
    
    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento
     * @param elem Elemento grafico
     * @param muxController Controlador del multiplexor
     */
    public void init2(ElementoGrafico elem, VentanaMultiplexorController muxController) {
        this.elemG=elem;
        this.multiplexorControl=muxController;
        Multiplexor mul= (Multiplexor) elem.getComponente();
        
        if(elemG.getComponente().isConectadoSalida()==true){
            muxController.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
            muxController.cboxConectarA.getItems().add("Desconected");
            muxController.cboxConectarA.getSelectionModel().select(0);
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if("fbg".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "spectrum".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "demux".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "power".equals(controlador.getElementos().get(elemento).getNombre())){
                    if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        muxController.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
            }
        }
        int salix=0;
        switch (mul.getEntradas()) {
            case 2:
                salix=0;
                cboxLongitudesOnda.getItems().removeAll(cboxLongitudesOnda.getItems());
                cboxLongitudesOnda.getItems().add("Wavelengths");
                cboxLongitudesOnda.getSelectionModel().select(0);
                cboxLongitudesOnda.getItems().addAll(longitudesOnda[3], longitudesOnda[4]);
                break;
            case 4:
                salix=1;
                cboxLongitudesOnda.getItems().removeAll(cboxLongitudesOnda.getItems());
                cboxLongitudesOnda.getItems().add("Wavelengths");
                cboxLongitudesOnda.getSelectionModel().select(0);
                cboxLongitudesOnda.getItems().addAll(longitudesOnda[2], longitudesOnda[3], 
                        longitudesOnda[4], longitudesOnda[5]);
                break;
            case 8:
                salix=2;
                cboxLongitudesOnda.getItems().removeAll(cboxLongitudesOnda.getItems());
                cboxLongitudesOnda.getItems().add("Wavelengths");
                cboxLongitudesOnda.getSelectionModel().select(0);
                cboxLongitudesOnda.getItems().addAll((Object[]) longitudesOnda);
                break;
            default:
                break;
        }
        multiplexorControl.cboxNumeroEntradas.getSelectionModel().select(salix);
        multiplexorControl.txtPerdidaInsercion.setText(String.valueOf(mul.getPerdidaInsercion()));
        multiplexorControl.cboxNumeroEntradas.setDisable(true);
    }
    
}
