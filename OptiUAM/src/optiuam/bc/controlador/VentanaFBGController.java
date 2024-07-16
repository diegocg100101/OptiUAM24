
package optiuam.bc.controlador;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.FBG;
import optiuam.bc.modelo.MatrizTransferencia;
import optiuam.bc.modelo.Multiplexor;
import optiuam.bc.modelo.PuertoSalida;
import optiuam.bc.modelo.Listas;


/**
 * Clase VentanaFBGController la cual se encarga de proporcionar la
 * funcionalidad a la rejilla de Bragg (FBG)
 * @author Karen Cruz
 * @see ControladorGeneral
 */
public class VentanaFBGController extends ControladorGeneral implements Initializable {
    
    /**Longitud de la j-esima seccion uniforme de la rejilla de Bragg*/
    static double dz;
    /**Longitud de onda a filtrar de la rejilla de Bragg*/
    public double longitudOnda;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico de la rejilla de Bragg*/
    ElementoGrafico elemG;
    /**Controlador de la rejilla de Bragg*/
    VentanaFBGController fbgControl;
    /**Posicion de la rejilla de Bragg en el eje X*/
    static double posX;
    /**Posicion de la rejilla de Bragg en el eje Y*/
    static double posY;
    /**Señal filtrada*/
    static int reflexionS;
    /**Señales sin la reflexion*/
    static String transmisionS;
    /**Lista de enlaces creados*/
    LinkedList<Listas> listaListas= new LinkedList();
    /**Longitudes de onda del multiplexor*/
    static String longitudesOnda[] = {"1470", "1490", "1510", "1530", "1550", "1570", "1590", "1610"}; 
    //                                  0        1       2       3       4      5       6        7
    /*
        Mux 2: longitudesOnda[3], longitudesOnda[4], perdida insercion max 0.6
        Mux 4: longitudesOnda[2] a longitudesOnda[5], perdida insercion max 1.6
        Mux 8: longitudesOnda[], perdida insercion max 2.5
    */
    
    /**Salidas de la rejilla de Bragg (FBG)*/
    @FXML
    ComboBox cboxSalidas;
    /**Caja de texto para ingresar la longitud de la rejilla de Bragg*/
    @FXML
    TextField txtLongitudFBG;
    /**Boton para calcular la reflexion y tranmision de la rejilla de Bragg*/
    @FXML
    Button btnCalcular;
    /**Boton para modificar la rejilla de Bragg*/
    @FXML
    Button btnModificar;
    /**Coeficiente de reflexion obtenido por el metodo de matriz de transferencia*/
    @FXML
    TextField txtReflexion;
    /**Coeficiente de transmision obtenido por el metodo de matriz de transferencia*/
    @FXML
    TextField txtTransmision;
    /**Longitud de onda de la señal filtrada*/
    @FXML
    TextField txtLongitudOnda;
    /**Lista desplegable de elementos disponibles para conectar la rejilla de Bragg*/
    @FXML
    ComboBox cboxConectarA;
    /**Lista desplegable de señales a filtrar*/
    @FXML
    ComboBox cboxFiltrar;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo que muestra la posicion de la rejilla de Bragg en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion de la rejilla de Bragg en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaFBGController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion de la rejilla de Bragg en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion de la rejilla de Bragg en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaFBGController.posY = posY;
    }

    /**
     * Metodo que muestra la señal filtrada de la rejilla de Bragg
     * @return reflexionS
     */
    public static int getReflexionS() {
        return reflexionS;
    }

    /**
     * Metodo que modifica la señal filtrada de la rejilla de Bragg
     * @param reflexionS Señal filtrada
     */
    public static void setReflexionS(int reflexionS) {
        VentanaFBGController.reflexionS = reflexionS;
    }

    /**
     * Metodo que muestra todas las señales a excepcion de la filtrada de la rejilla de Bragg
     * @return transmisionS
     */
    public static String getTransmisionS() {
        return transmisionS;
    }

    /**
     * Metodo que modifica todas las señales a excepcion de la filtrada de la rejilla de Bragg
     * @param transmisionS Señales sin la reflexion (señal filtrada)
     */
    public static void setTransmisionS(String transmisionS) {
        VentanaFBGController.transmisionS = transmisionS;
    }

    /**
     * Metodo que muestra la longitud de la j-esima seccion uniforme de la rejilla de Bragg
     * @return dz
     */
    public static double getDz() {
        return dz;
    }

    /**
     * Metodo que modifica la longitud de la j-esima seccion uniforme de la rejilla de Bragg
     * @param dz Longitud de la j-esima seccion uniforme de la FBG
     */
    public static void setDz(double dz) {
        VentanaFBGController.dz = dz;
    }

    /**
     * Metodo el cual inicializa la ventana de la rejilla de Bragg (FBG)
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip salidas = new Tooltip();
        salidas.setText("Output 1 corresponds to Transmission"+
                "\nOutput 2 corresponds to Reflection");
        cboxSalidas.setTooltip(salidas);
        cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
        cboxSalidas.getItems().addAll("1", "2");
        cboxSalidas.getSelectionModel().select("1");
    }    
    
    /**
     * Metodo que realiza el calculo de la matriz de transferencia con los datos 
     * ingresados por el usuario tales como la longitud de la FBG y la longitud 
     * de onda a filtrar
     * @throws java.text.ParseException Señala que se ha alcanzado un error inesperadamente durante el análisis
     */
    @FXML
    public void calcularMatriz() throws ParseException{
        double reflexion, transmision, total;
        txtLongitudOnda.setEditable(false);
        if (txtLongitudFBG.getText().isEmpty() || txtLongitudFBG.getText().compareTo("")==0 || !txtLongitudFBG.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid length value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid length value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtLongitudFBG.setText("");
        }
        else if (txtLongitudOnda.getText().isEmpty() || txtLongitudOnda.getText().compareTo("")==0 || !txtLongitudOnda.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid wavelength value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid wavelength value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtLongitudFBG.setText("");
        }
        else{
            FBG fbg = new FBG();
            dz = Double.parseDouble(txtLongitudFBG.getText());
            
            fbg.setDz(dz);
            longitudOnda = Double.parseDouble(txtLongitudOnda.getText());
            fbg.setLongitudOnda(longitudOnda);
            System.out.println("CALCULO CON LONGITUD DE ONDA "+longitudOnda);
            MatrizTransferencia m = new MatrizTransferencia();
            m.calculoMatriz(dz*1000, 1550);
            
            reflexion = m.getReflexion();
            DecimalFormat ref = new DecimalFormat("###0.############");
            reflexion = ref.parse(ref.format(reflexion)).doubleValue();
            
            transmision =  m.getTransmision();
            DecimalFormat tra = new DecimalFormat("###0.#########");
            transmision = tra.parse(tra.format(transmision)).doubleValue();
            
            txtReflexion.setText(String.valueOf(reflexion));
            txtTransmision.setText(String.valueOf(transmision));
            txtReflexion.setEditable(false);
            txtTransmision.setEditable(false);
            fbg.setReflexion(reflexion);
            fbg.setTransmision(transmision);
            System.out.println("Reflexion = " + reflexion);
            System.out.println("Transmision = " + transmision);
            total = reflexion + transmision;
            System.out.println("Reflexion + Transmision = " + total);
        }
    }
    
    /**
     * Metodo para cerrar la ventana de la rejilla de Bragg 
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar la rejilla de Bragg
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(fbgControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                break;
            }
        }
        fbgControl.cboxConectarA.getSelectionModel().select(0);
        if(fbgControl.cboxSalidas.getSelectionModel().getSelectedItem().equals("1")){
            if(elemG.getComponente().isConectadoSalida()){
                elemG.getComponente().setConectadoSalida(false);
                elemG.getComponente().setElementoConectadoSalida("");
                elemG.getComponente().getLinea().setVisible(false);
            }
        }
        else{
            int salida= Integer.parseInt(fbgControl.cboxSalidas.getSelectionModel().getSelectedItem().toString())-2;
            FBG aux=(FBG) elemG.getComponente();
            if(aux.getConexiones().get(salida).isConectadoSalida()){
                aux.getConexiones().get(salida).setConectadoSalida(false);
                aux.getConexiones().get(salida).setElementoConectadoSalida("");
                aux.getConexiones().get(salida).getLinea().setVisible(false);
            }
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected FBG!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar la configuracion de la rejilla de Bragg
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     * @throws java.text.ParseException Señala que se ha alcanzado un error inesperadamente durante el análisis.
     */
    @FXML
    public void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException, ParseException{
        double reflexion, transmision, total;
        FBG fbg = (FBG) elemG.getComponente();
        if((fbgControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }
        else{
            conectar();
        }
        txtLongitudOnda.setEditable(false);
        if (txtLongitudFBG.getText().isEmpty() || txtLongitudFBG.getText().compareTo("")==0 || !txtLongitudFBG.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid length value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid length value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (txtLongitudOnda.getText().isEmpty() || txtLongitudOnda.getText().compareTo("")==0 || !txtLongitudOnda.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid wavelength value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid wavelength value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            dz = Double.parseDouble(txtLongitudFBG.getText());
            
            fbg.setDz(dz);
            longitudOnda = Double.parseDouble(txtLongitudOnda.getText());
            fbg.setLongitudOnda(longitudOnda);
            System.out.println("CALCULO CON LONGITUD DE ONDA "+longitudOnda);
            MatrizTransferencia m = new MatrizTransferencia();
            m.calculoMatriz(dz*1000, 1550);
            
            reflexion = m.getReflexion();
            DecimalFormat ref = new DecimalFormat("###0.############");
            reflexion = ref.parse(ref.format(reflexion)).doubleValue();
            
            transmision =  m.getTransmision();
            DecimalFormat tra = new DecimalFormat("###0.#########");
            transmision = tra.parse(tra.format(transmision)).doubleValue();
            
            txtReflexion.setText(String.valueOf(reflexion));
            txtTransmision.setText(String.valueOf(transmision));
            txtReflexion.setEditable(false);
            txtTransmision.setEditable(false);
            fbg.setReflexion(reflexion);
            fbg.setTransmision(transmision);
            System.out.println("Reflexion = " + reflexion);
            System.out.println("Transmision = " + transmision);
            total = reflexion + transmision;
            System.out.println("Reflexion + Transmision = " + total);
            
            cerrarVentana(event);

            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified FBG!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    /**
     * Metodo que indica si una salida de la rejilla de Bragg esta conectada a un componente
     * @param salida Salida n de la FBG
     * @param componente Componente a conectar
     * @return FBG conectado
     */
    public boolean conectarFBG(int salida,String componente){
        FBG fbg = (FBG) elemG.getComponente();
        if (txtLongitudFBG.getText().isEmpty() || txtLongitudFBG.getText().compareTo("")==0 || !txtLongitudFBG.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nThe element will not connect until you enter a correct insertion loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe element will not connect until you enter a correct insertion loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtLongitudFBG.setText("");
        }
        /*else if (txtLongitudOnda.getText().isEmpty() || txtLongitudOnda.getText().compareTo("")==0 || !txtLongitudOnda.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nThe element will not connect until you have selected a wavelength to filter");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe element will not connect until you have selected a wavelength to filter",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtLongitudFBG.setText("");
        }*/
        else{
            if (salida==0) { 
                if(fbg.isConectadoSalida()==false){
                    fbg.setConectadoSalida(true);
                    fbg.setElementoConectadoSalida(componente);
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
                            fbg.setLinea(line);
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
            else if(salida>0&&salida<=fbg.getSalidas()){
                if(fbg.getConexiones().get(salida-1).isConectadoSalida()==false){
                    fbg.getConexiones().get(salida-1).setConectadoSalida(true);
                    fbg.getConexiones().get(salida-1).setElementoConectadoSalida(componente);
                    Line line= new Line();
                    switch (fbg.getSalidas()) {
                        default:
                            line.setStartX(elemG.getDibujo().getLayoutX()+50);
                            line.setStartY(elemG.getDibujo().getLayoutY()+(24+(5*(salida))));
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
                        fbg.getConexiones().get(salida-1).setLinea(line);
                        fbg.actuaizarSalidas(salida);
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
        }
        
        return false;
    }
    
    /**
     * Metodo que conecta la salida n de la rejilla de Bragg a un componente
     */
    public void conectar(){
        int salida = cboxSalidas.getSelectionModel().getSelectedIndex();
        
        String componente = cboxConectarA.getSelectionModel().getSelectedItem().toString();
        if(cboxConectarA.getItems().size() <= 1 && elemG.getComponente().isConectadoSalida()==false){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nFBG cannot be connected to anything",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            if(conectarFBG(salida,componente)){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "\nFBG output "+(salida+1)+ " was connected to component: "+componente,
                        aceptar);
                alert.setTitle("Succes");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }
    
    /**
     * Metodo que muestra la conexion de la salida n de la rejilla de Bragg a un componente 
     */
    @FXML
    public void actCbox(){
        fbgControl.cboxConectarA.getItems().clear();
        if(fbgControl.cboxSalidas.getSelectionModel().getSelectedIndex()==0){
            if(elemG.getComponente().isConectadoSalida()==true){
                fbgControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
            }
            else{
                fbgControl.cboxConectarA.getItems().add("Desconected");
                fbgControl.cboxConectarA.getSelectionModel().select(0);
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        if((controlador.getElementos().get(elemento).getNombre()).startsWith("spectrum")
                                ||(controlador.getElementos().get(elemento).getNombre()).startsWith("demux")
                                ||(controlador.getElementos().get(elemento).getNombre()).startsWith("power")){
                            fbgControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                        }
                    }
                }
            }
        }
        else{
            FBG aux=(FBG)elemG.getComponente();
            //System.out.println(aux.getConexiones().toString());
            if(aux.getConexiones() != null && !aux.getConexiones().isEmpty()){
                if(aux.getConexiones().get(fbgControl.cboxSalidas.getSelectionModel().getSelectedIndex()-1).isConectadoSalida()==true){
                    fbgControl.cboxConectarA.getSelectionModel().select(aux.getConexiones().get(fbgControl.cboxSalidas.getSelectionModel().getSelectedIndex()-1).getElementoConectadoSalida());
                }
                else{
                    fbgControl.cboxConectarA.getItems().add("Desconected");
                    
                    fbgControl.cboxConectarA.getSelectionModel().select(0);
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if((controlador.getElementos().get(elemento).getNombre()).startsWith("spectrum")
                                ||(controlador.getElementos().get(elemento).getNombre()).startsWith("demux")
                                ||(controlador.getElementos().get(elemento).getNombre()).startsWith("power")){
                            if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                                fbgControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Método que muestra las señales a filtrar junto con su longitud de onda
     */
    @FXML
    public void filtrarSeñal(){
        String nombre, id,longitud;
        for(int i = 0; i < cboxFiltrar.getItems().size(); i++){
            if(cboxFiltrar.getSelectionModel().isSelected(i)){
                String str = (String) cboxFiltrar.getSelectionModel().getSelectedItem();
                StringTokenizer st = new StringTokenizer(str, "_");
                while (st.hasMoreTokens()){
                    nombre = st.nextToken();
                    id = st.nextToken();
                    longitud = st.nextToken();
                    txtLongitudOnda.setText(longitud);
                    txtLongitudOnda.setEditable(false);
                    reflexionS = Integer.parseInt(txtLongitudOnda.getText());
                    System.out.println("\nLONGITUD DE ONDA SELECCIONADA: "+txtLongitudOnda.getText());
                    ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "\nThe wavelength to filter is: " + txtLongitudOnda.getText(), aceptar);
                    alert.setTitle("Succes");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }             
            }
            LinkedList transmisionLista = new LinkedList();
            transmisionLista.addAll(cboxFiltrar.getItems());
            String señalFiltrada = (String) cboxFiltrar.getSelectionModel().getSelectedItem();
            if(transmisionLista.contains(señalFiltrada)){
                transmisionLista.remove(señalFiltrada);
            }
            else{
                transmisionLista.add(señalFiltrada);
            }
            String datosLista = "";
            for(Object elemento: transmisionLista){
                datosLista += elemento + ", ";
            }
            datosLista = datosLista.trim();
            if(datosLista != null && datosLista.length() > 0 && datosLista.charAt(datosLista.length() - 1) == ','){           
              datosLista = datosLista.substring(0, datosLista.length() - 1);              
            }
            transmisionS = datosLista;
        }
        System.out.println("transmision s: " + transmisionS);
    }
    
    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param scroll Espacio en el cual el usuario puede desplazarse
     * @param elem Elemento grafico
     */
    void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll, ElementoGrafico elem) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
        this.elemG=elem;
        PuertoSalida p= new PuertoSalida();
        FBG fbg = (FBG) elem.getComponente();
        fbg.getConexiones().add(p);
        fbg.setSalidas(2);
    }
    
    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento 
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param elem Elemento grafico
     * @param fbgController Controlador de la rejilla de Bragg 
    */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,ElementoGrafico elem, VentanaFBGController fbgController) {
        this.elemG=elem;
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.fbgControl=fbgController;
        FBG fbg= (FBG) elem.getComponente();
        if(elemG.getComponente().isConectadoEntrada()==false){
            fbgControl.cboxFiltrar.getItems().add("Signals");
            fbgControl.cboxFiltrar.getSelectionModel().select(0);
        }
        //Añadir señales
        else{
            añadirSeñales(elem);
        }
        if(fbg.getDz()!=0){
            fbgControl.txtLongitudFBG.setText(String.valueOf(fbg.getDz()));
        }
        else{
            fbgControl.txtLongitudFBG.setText("0.0");
        }
        if(fbg.getReflexion()!=0){
            fbgControl.txtReflexion.setText(String.valueOf(fbg.getReflexion()));
        }
        else{
            fbgControl.txtReflexion.setText("0.0");
        }
        if(fbg.getTransmision()!=0){
            fbgControl.txtTransmision.setText(String.valueOf(fbg.getTransmision()));
        }
        else{
            fbgControl.txtTransmision.setText("0.0");
        }
        if(fbg.getLongitudOnda()!=0){
            fbgControl.txtLongitudOnda.setText(String.valueOf(fbg.getLongitudOnda()));
        }
        else{
            fbgControl.txtLongitudOnda.setText("");
        }
        
        fbgControl.txtReflexion.setEditable(false);
        fbgControl.txtTransmision.setEditable(false);
        fbgControl.txtLongitudOnda.setEditable(false);
        
        if(elemG.getComponente().isConectadoSalida()==true){
            fbgControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
            fbgControl.cboxConectarA.getItems().add("Desconected");
            fbgControl.cboxConectarA.getSelectionModel().select(0);
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                    if("spectrum".equals(controlador.getElementos().get(elemento).getNombre())
                            ||(controlador.getElementos().get(elemento).getNombre()).startsWith("demux")
                            ||(controlador.getElementos().get(elemento).getNombre()).startsWith("power")){
                       fbgControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
            }
        }
    }
    
    /**
     * Metodo que obtiene las señales disponibles para filtrar
     * @param elem Elemento grafico de la FBG
     */
    public void añadirSeñales(ElementoGrafico elem){
        Multiplexor mul= encontrarMux(elemG);
        ElementoGrafico w=new ElementoGrafico();
        for(int d=0; d<controlador.getDibujos().size();d++){
            if(controlador.getDibujos().get(d).getDibujo().getText().equals(elem.getComponente().getElementoConectadoEntrada())){
                w=controlador.getDibujos().get(d);
                break;
            }    
        }
        switch(mul.getEntradas()){
            case 2:
                int a=3;
                if(mul.isConectadoEntrada()){
                    //fbgControl.cboxFiltrar.getItems().add(longitudesOnda[a]);
                    fbgControl.cboxFiltrar.getItems().add(w.getDibujo().getText()+"_"+longitudesOnda[a]);
                }
                for(int g=0; g<mul.getConexionEntradas().size(); g++){
                    a+=1;
                    if(mul.getConexionEntradas().get(g).isConectadoEntrada()){
                        //fbgControl.cboxFiltrar.getItems().add(longitudesOnda[a]);
                        fbgControl.cboxFiltrar.getItems().add(w.getDibujo().getText()+"_"+longitudesOnda[a]);
                    }
                }
                break;
                
            case 4:
                int a2=2;
                if(mul.isConectadoEntrada()){
                    //fbgControl.cboxFiltrar.getItems().add(longitudesOnda[a2]);
                    fbgControl.cboxFiltrar.getItems().add(w.getDibujo().getText()+"_"+longitudesOnda[a2]);
                }
                for(int g=0; g<mul.getConexionEntradas().size(); g++){
                    a2+=1;
                    if(mul.getConexionEntradas().get(g).isConectadoEntrada()){
                        //fbgControl.cboxFiltrar.getItems().add(longitudesOnda[a2]);
                        fbgControl.cboxFiltrar.getItems().add(w.getDibujo().getText()+"_"+longitudesOnda[a2]);
                    }
                }
                break;
                
            case 8:
                int a3=0;
                if(mul.isConectadoEntrada()){
                    //fbgControl.cboxFiltrar.getItems().add(longitudesOnda[a3]);
                    fbgControl.cboxFiltrar.getItems().add(w.getDibujo().getText()+"_"+longitudesOnda[a3]);
                }
                for(int g=0; g<mul.getConexionEntradas().size(); g++){
                    a3+=1;
                    if(mul.getConexionEntradas().get(g).isConectadoEntrada()){
                        //fbgControl.cboxFiltrar.getItems().add(longitudesOnda[a3]);
                        fbgControl.cboxFiltrar.getItems().add(w.getDibujo().getText()+"_"+longitudesOnda[a3]);
                    }
                }
                break;
        }    
    }
    
    /**
     * Metodo que obtiene el multiplexor conectado a la rejilla de Bragg
     * @param elem Elemento grafico de la FBG
     * @return Multiplexor
     */
    public Multiplexor encontrarMux(ElementoGrafico elem){
        Multiplexor mux=new Multiplexor();
        if(elem.getComponente().isConectadoEntrada()){
            if(elem.getComponente().getElementoConectadoEntrada().contains("mux")){
                for(int d=0; d<controlador.getDibujos().size();d++){
                    if(controlador.getDibujos().get(d).getDibujo().getText().equals(elem.getComponente().getElementoConectadoEntrada())){
                        mux=(Multiplexor) controlador.getElementos().get(d);
                        break;
                    }    
                }
            }
            else{
                for(int d=0; d<controlador.getDibujos().size();d++){
                    if(controlador.getDibujos().get(d).getDibujo().getText().equals(elem.getComponente().getElementoConectadoEntrada())){
                        ElementoGrafico el= controlador.getDibujos().get(d);
                        mux= encontrarMux(el);
                    }    
                }
            }
            
        }
        return mux;
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante de la rejilla de Bragg 
     * con otro elemento
     * @param elemG Elemento grafico del FBG
     */
    public void dibujarLineaFBG(ElementoGrafico elemG) {
        FBG fbg=(FBG)elemG.getComponente();
        if(fbg.isConectadoSalida()){
            Line line= new Line();   
            switch (fbg.getSalidas()) {
                default:
                    line.setStartX(elemG.getDibujo().getLayoutX()+45);
                    line.setStartY(elemG.getDibujo().getLayoutY()+12);
                    break;
            }
            ElementoGrafico aux= new ElementoGrafico();
            for(int it=0; it<controlador.getDibujos().size();it++){
                if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                    aux=controlador.getDibujos().get(it);
                    line.setStrokeWidth(2);
                    line.setStroke(javafx.scene.paint.Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+12);
                    line.setEndY(aux.getDibujo().getLayoutY()+8);
                    line.setVisible(true);
                    Pane1.getChildren().add(line); 
                    elemG.getComponente().setLinea(line);  
                }
            }
        }
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
                for(int ir=0; ir<controlador.getDibujos().size();ir++){
                    if(fbg.getConexiones().get(lap).getElementoConectadoSalida().equals(controlador.getDibujos().get(ir).getDibujo().getText())){
                        aux=controlador.getDibujos().get(ir);
                        line.setStrokeWidth(2);
                        line.setStroke(javafx.scene.paint.Color.BLACK);
                        line.setEndX(aux.getDibujo().getLayoutX()+12);
                        line.setEndY(aux.getDibujo().getLayoutY()+8);
                        line.setVisible(true);
                        Pane1.getChildren().add(line);
                        fbg.getConexiones().get(lap).setLinea(line);
                    }
                }
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras de la rejilla de Bragg 
     * con otro elemento
     * @param elem Elemento grafico del FBG
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(3);
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+26);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
    }
     
    /**
     * Metodo que elimina la conexion de la rejilla de Bragg
     * @param elem Elemento grafico del FBG
     */
    public void borrarLineaFBG(ElementoGrafico elem){
        FBG fbg =(FBG)elem.getComponente();
        if(fbg.isConectadoSalida()){
            fbg.getLinea().setVisible(false);
        }
        for(int po=0; po<fbg.getSalidas()-1;po++){
            if(fbg.getConexiones().get(po).isConectadoSalida()){
                fbg.getConexiones().get(po).getLinea().setVisible(false);
            }
        }
    }
    
    /**
     * Metodo que permite ver los componentes conectados antes de la rejilla de Bragg
     * @return Componentes
     */
    public LinkedList verComponentesConectados(){
        LinkedList<Componente> lista=new LinkedList();
        System.out.println(elemG.getComponente().getNombre());
        añadirComponentesConectados(lista, elemG.getComponente());
        return lista;
    }
    
    /**
     * Metodo que agrega a una lista los componentes conectados antes de la rejilla de Bragg
     * @param lista Lista de componentes
     * @param comp Componentes
     */
    public void añadirComponentesConectados(LinkedList lista, Componente comp){
        lista.add(comp);
        if(comp.isConectadoEntrada()){
            for(int i=0; i<controlador.getElementos().size();i++){
                if(comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(i).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(i);
                    añadirComponentesConectados(lista, aux);
                    break;
                }
            }
        }
        if(comp.getNombre().startsWith("mux")){
            Multiplexor muxi= (Multiplexor) comp;
            for(int n=0; n<muxi.getEntradas()-1; n++){
                if(muxi.getConexionEntradas().get(n).isConectadoEntrada()){
                    LinkedList auxList= new LinkedList();
                    for(int j=0; j<lista.size()-1; j++){
                        auxList.add(lista.get(j));
                        Componente w= (Componente) lista.get(j);
                        if("mux".equals(w.getNombre())){
                            break;
                        }
                    }
                    for(int z=0; z<controlador.getDibujos().size();z++){
                        if(muxi.getConexionEntradas().get(n).getElementoConectadoEntrada().equals(controlador.getDibujos().get(z).getDibujo().getText())){
                            Componente aux2= controlador.getElementos().get(z);
                            //System.out.println("\t"+aux2.getNombre());
                            añadirComponentesConectados(auxList, aux2);
                            Listas listaxd= new Listas();
                            listaxd.setLista(auxList);
                            listaxd.setN(n);
                            listaListas.add(listaxd);
                            break;
                        }     
                    }
                }
            } 
        }
    }
    
}
