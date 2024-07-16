
package optiuam.bc.controlador;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.FBG;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Listas;
import optiuam.bc.modelo.Multiplexor;
import optiuam.bc.modelo.Splitter;

/**
 * Clase VentanaPotenciaController la cual se encarga de proporcionar la
 * funcionalidad al medidor de potencia
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class VentanaPotenciaController implements Initializable {
    
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Controlador del espectro*/
    VentanaPotenciaController potenciaControl;
    /**Componentes conectados antes del medidor de potencia*/
    LinkedList<Componente> elementos;
    /**Elemento grafico del medidor de potencia*/
    ElementoGrafico elem;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Lista de enlaces creados*/
    LinkedList<Listas> listaListas= new LinkedList();
    
    /**Boton para calcular la potencia*/
    @FXML
    Button btnCalcularPotencia;
    /**Caja de texto para ingresar la sensibilidad*/
    @FXML
    TextField txtSensibilidad;
    /**Etiqueta que mostrará el resultado del calculo de la potencia*/
    @FXML
    Label lblPotencia;
    /**Etiqueta que muestra el titulo del medidor de potencia*/
    @FXML
    Label lblTitulo;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;
    
    /**
     * Metodo el cual inicializa la ventana del medidor de potencia
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Metodo para cerrar la ventana del medidor de potencia
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo que calcula la potencia
     * @param sensibilidad Sensibilidad utilizada para calcular la potencia
     * @param lista Lista de componentes creados
     * @param flagM Indica si hay un multiplexor conectado
     * @return potencia calculada
     */
    public double calcularPotencia(Double sensibilidad, LinkedList<Componente> lista, boolean flagM) {
        double Dt= 0.0; //dispersion cromatica total
        double Pa= 0.0; //perdida por atenuacion de la fibra L*Fa 
        double S = 0.0; // anchura espectral
        double L = 0.0; // longitud de la fibra en km
        double Fa= 0.0; // atenuacion de la fibra
        double Dc= 0.0; // dispersion de la fibra
        double B = 0.0; // taza de bits
        double Pd= 0.0; //perdida por dispersion del enlace
        double Pt= 0.0; //perdida total del enlace 
        double Tp= 0.0; //potencia de la fuente dB
        //int    Tc=0;    //cantidad de conectores en el enlace
        double Pc =0.0; //perdida de los conectores
        LinkedList<Double> conectores = new LinkedList<>();//guarda las perdidas de los conectores en un enlace
        //int    Te=0;    //cantidad de empalmes en el enlace
        double Pe=0.0;  //perdida de los empalmes
        LinkedList<Double> empalmes = new LinkedList<>();//guarda las perdidas de los empalmes en un enlace
        double Ps=0.0;  //perdida del splitter
        int    Se=0;    //salidas del splitter
        double Pm=0.0;  //perdida del mux
       // boolean isSplitter=false; //para saber si hubo un splitter en el enlace
        
        elementos = lista;
        for(int o=0; o<elementos.size();o++){
           // System.out.println(elementos.get(o).toString());
        }
        //System.out.println("\n\n");
        //System.out.println(elementos.get(Se));
        for(int i = elementos.size()-1;i>=0;i--){
            if(elementos.get(i).getNombre().contains("splitter")){
                //isSplitter=true;
                Splitter splitter_aux = (Splitter)elementos.get(i);
                Ps= splitter_aux.getPerdidaInsercion();
                Se=splitter_aux.getSalidas();
                Dt = Dc * S *L; // picosegungo x10-12
                Pd = -10 * Math.log10(1-((0.5)*Math.pow((Math.PI*(B*Math.pow(10, 9))),2)* Math.pow((Dt*Math.pow(10, -12)),2)));
                System.out.println("Splitter ---Perdida por dispersion ="+ Pd);
                Pc=perdidaConectores_Empalmes(conectores);
                System.out.println("Splitter ---Perdida conectores="+Pc);
                Pe=perdidaConectores_Empalmes(empalmes);
                System.out.println("Splitter ---Perdida empalmes="+Pe);
                Pa = L*Fa;
                System.out.println("Splitter ---Perdida atenuacion fibra="+Pa);
                // Math.pow(2,(Se+1)) -> puede ser 2,4,8,16,32,64 
                Tp=(Tp-(Pd + Pc + Pe + Pa + Ps))/Math.pow(2,(Se+1)); 
                System.out.println("Splitter ----Potencia ="+Tp);
                //inicializar los valores a 0 para los nuevos enlaces
                L=0.0;
                conectores = new LinkedList<>();
                empalmes = new LinkedList<>();
            } 
            if(elementos.get(i).getNombre().contains("source")){
                Fuente fuente_aux = (Fuente)elementos.get(i);
                B=fuente_aux.getVelocidad();
                S=fuente_aux.getAnchura();
                Tp=fuente_aux.getPotencia();
            } 
            if(elementos.get(i).getNombre().contains("connector")){
                Conector conector_aux = (Conector)elementos.get(i);
                conectores.add(conector_aux.getPerdidaInsercion());
            }
            
            if(elementos.get(i).getNombre().contains("fiber")){
                Fibra fibra_aux = (Fibra)elementos.get(i);
                Dc = fibra_aux.getDispersion();
                Fa = fibra_aux.getAtenuacion();
                L = L + fibra_aux.getLongitud_km();
            }
            if(elementos.get(i).getNombre().contains("splice")){ 
                Empalme empalme_aux = (Empalme)elementos.get(i);
                empalmes.add(empalme_aux.getPerdidaInsercion());
            }
            if(elementos.get(i).getNombre().contains("mux")){ 
                Multiplexor mux = (Multiplexor)elementos.get(i);
                Pm = mux.getPerdidaInsercion();
            }
        }
        Dt = Dc * S *L; // picosegungo x10^12
        Pd = -10 * Math.log10(1-((0.5)*Math.pow((Math.PI*(B*Math.pow(10, 9))),2)* Math.pow((Dt*Math.pow(10, -12)),2)));
        System.out.println("Perdida por dispersion ="+ Pd);
        Pc=perdidaConectores_Empalmes(conectores);
        System.out.println("Perdida conectores="+Pc);
        Pe=perdidaConectores_Empalmes(empalmes);
        System.out.println("Perdida empalmes="+Pe);
        Pa = L*Fa;
        System.out.println("Perdida atenuacion fibra="+Pa);
        System.out.println("Perdida multiplexor = " + Pm);
        
        if(flagM){
            Pt=(Tp-(sensibilidad))-(Pd + Pc + Pe + Pa);
        }
        else{
            Pt=(Tp-(sensibilidad))-(Pd + Pc + Pe + Pa+Pm);
        }
        //System.out.println("Potencia ="+Pt);
        System.out.println("Potencia Total="+Math.floor(Pt*100)/100);
        return (Math.floor(Pt*100)/100);
    }
    
    /**
     * Metodo que muestra la perdida de conectores y empalmes
     * @param lista Conectores y empalmes
     * @return perdida total
     */
    public double perdidaConectores_Empalmes(LinkedList<Double> lista){
        Double perdidaTotal = 0.0;
        if(lista.isEmpty())
            return perdidaTotal;
        for(Double perdida:lista)
            perdidaTotal=perdidaTotal+perdida;
        return perdidaTotal;
    }
    
    /**
     * Metodo que muestra la potencia calculada
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnCalcularPotenciaAction(ActionEvent event){
        LinkedList<Componente> ele=verComponentesConectados();
        try{
            if(ele.get(1).getNombre().contains("fbg")){
                FBG fbg = (FBG) ele.get(1);
                if(fbg.getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                    /*POTENCIA TRANSMISION*/
                    if(VentanaFBGController.getTransmisionS() != null && fbg.getTransmision() != 0){
                        double potenciaT=0;
                        boolean flag2=false;
                        for(int yu=0; yu<listaListas.size(); yu++){
                            if(!(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS())){
                                ele=listaListas.get(yu).getLista();
                                if(ele.getLast().getNombre().contains("source")){
                                    if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
                                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                            "\nInvalid sensitivity value",
                                            aceptar);
                                        alert.setTitle("Error");
                                        alert.setHeaderText(null);
                                        alert.showAndWait();
                                    }
                                    else{
                                        //POTENCIA TRANSMISION (SIN REFLEXION)
                                        Double potencia = calcularPotencia(Double.valueOf(txtSensibilidad.getText()),ele,flag2);
                                        flag2=true;
                                        if(potencia !=-1){
                                            potenciaT+=potencia;
                                            //potenciaT = potenciaT-(-potenciaR);
                                            DecimalFormat pot = new DecimalFormat("###0.####");
                                            potenciaT = pot.parse(pot.format(potenciaT)).doubleValue();
                                            lblPotencia.setText(String.valueOf(potenciaT + " dBm"));
                                        }
                                        else if(potencia ==-2){
                                            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                                "\nLink calculation error",
                                                aceptar);
                                            alert.setTitle("Error");
                                            alert.setHeaderText(null);
                                            alert.showAndWait();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    /*POTENCIA REFLEXION*/
                    if(VentanaFBGController.getReflexionS()!= 0 && fbg.getReflexion()!= 0){
                        for(int yu=0; yu<listaListas.size(); yu++){
                            if(listaListas.get(yu).getN()==VentanaFBGController.getReflexionS()){
                                ele=listaListas.get(yu).getLista();
                                double potenciaT=0;
                                if(ele.getLast().getNombre().contains("source")){
                                    if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
                                        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                            "\nInvalid sensitivity value",
                                            aceptar);
                                        alert.setTitle("Error");
                                        alert.setHeaderText(null);
                                        alert.showAndWait();
                                    }
                                    else{
                                        Double potencia = calcularPotencia(Double.valueOf(txtSensibilidad.getText()),ele,false);
                                        if(potencia !=-1){
                                            potenciaT+=potencia;
                                            DecimalFormat pot = new DecimalFormat("###0.####");
                                            potenciaT = pot.parse(pot.format(potenciaT)).doubleValue();
                                            lblPotencia.setText(String.valueOf(potenciaT + " dBm"));
                                        }
                                        else if(potencia ==-2){
                                            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                                "\nLink calculation error",
                                                aceptar);
                                            alert.setTitle("Error");
                                            alert.setHeaderText(null);
                                            alert.showAndWait();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                /*POTENCIA DE UN SOLO ENLACE*/
                if(listaListas.isEmpty()){
                    if(ele.getLast().getNombre().contains("source")){
                        if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
                            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                "\nInvalid sensitivity value",
                                aceptar);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
                        else{
                            Double potencia = calcularPotencia(Double.valueOf(txtSensibilidad.getText()),ele,true);
                            if(potencia !=-1){
                                DecimalFormat pot = new DecimalFormat("###0.####");
                                potencia = pot.parse(pot.format(potencia)).doubleValue();
                                lblPotencia.setText(String.valueOf(potencia + " dBm"));
                            }
                            else if(potencia ==-2){
                                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                Alert alert = new Alert(Alert.AlertType.ERROR,
                                    "\nLink calculation error",
                                    aceptar);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.showAndWait();
                            }
                        }
                    }
                }
                /*POTENCIA DE VARIOS ENLACES*/ 
                if(listaListas.size()>0){
                    double potenciaT=0;
                    boolean flag2=false;
                    for(int yu=0; yu<listaListas.size(); yu++){
                        ele=listaListas.get(yu).getLista();
                        if(ele.getLast().getNombre().contains("source")){
                            if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
                                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                Alert alert = new Alert(Alert.AlertType.ERROR,
                                    "\nInvalid sensitivity value",
                                    aceptar);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.showAndWait();
                            }
                            else{
                                Double potencia = calcularPotencia(Double.valueOf(txtSensibilidad.getText()),ele,flag2);
                                flag2=true;
                                if(potencia !=-1){
                                    potenciaT+=potencia;
                                    DecimalFormat pot = new DecimalFormat("###0.####");
                                    potenciaT = pot.parse(pot.format(potenciaT)).doubleValue();
                                    lblPotencia.setText(String.valueOf(potenciaT + " dBm"));
                                }
                                else if(potencia ==-2){
                                    ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                                    Alert alert = new Alert(Alert.AlertType.ERROR,
                                        "\nLink calculation error",
                                        aceptar);
                                    alert.setTitle("Error");
                                    alert.setHeaderText(null);
                                    alert.showAndWait();
                                }
                            }
                        }
                    }
                }
            }
            if(!ele.getLast().getNombre().contains("source")&&listaListas.isEmpty()){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "\nLink error",
                        aceptar);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        catch(NumberFormatException | ParseException e){
            System.out.println(e);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
               Alert alert = new Alert(Alert.AlertType.ERROR,
                       "\nLink error",
                       aceptar);
               alert.setTitle("Error");
               alert.setHeaderText(null);
               alert.showAndWait();
        }
    }
    
    /**
     * Metodo que permite ver los componentes conectados antes del medidor de potencia
     * @return Componentes
     */
    public LinkedList verComponentesConectados(){
        LinkedList<Componente> lista=new LinkedList();
        añadirComponentesConectados(lista, elem.getComponente());
        //sacar componentes de la lista default
        for(int xi=0; xi<lista.size(); xi++){
                //System.out.println(lista.get(xi).toString());
        }
        //System.out.println("\n");
        //Sacar la lista
        for(int ci=0; ci<listaListas.size(); ci++){
            //Sacar componente
                Listas xd= listaListas.get(ci);
                //System.out.println(xd.getN()+"\t"+xd.getLista().getLast().toString());
            //System.out.println("\n");
        }
        return lista;
    }
    
    /**
     * Metodo que agrega a una lista los componentes conectados antes del medidor de potencia
     * @param lista Lista de componentes
     * @param comp Componentes
     * @return Lista de componentes conectados
     */
    public LinkedList añadirComponentesConectados(LinkedList lista, Componente comp){
        lista.add(comp);
        if(comp.isConectadoEntrada()){
            for(int i=0; i<controlador.getElementos().size(); i++){
                if(comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(i).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(i);
                    lista=añadirComponentesConectados(lista, aux);
                    if("mux".equals(aux.getNombre())){
                            break;
                    }
                }
            }
        }
        if(comp.getNombre().startsWith("mux")){
            Multiplexor muxi= (Multiplexor) comp;
            if(comp.isConectadoEntrada()){
                Listas listaAux= new Listas();
                for(int fe=0; fe<controlador.getDibujos().size();fe++){
                    if(muxi.getElementoConectadoEntrada().equals(controlador.getDibujos().get(fe).getDibujo().getText())){
                        Componente aux2= controlador.getElementos().get(fe);
                        LinkedList auxList= new LinkedList();
                        for(int ju=0; ju<lista.size()-1; ju++){
                            auxList.add(lista.get(ju));
                            Componente w= (Componente) lista.get(ju);
                            if("mux".equals(w.getNombre())){
                                 break;
                            }
                        }
                        añadirComponentesConectados(auxList, aux2);
                        switch(muxi.getEntradas()){
                            case 2:
                                listaAux.setN(1530);
                                break;
                                
                            case 4:
                                listaAux.setN(1510);
                                break;

                            case 8:
                                listaAux.setN(1470);
                                break;

                        }
                        listaAux.setLista(auxList);
                        Boolean flag=false;
                        for(int j=0; j<listaListas.size(); j++){
                            if(listaAux.getN()==listaListas.get(j).getN()){
                                flag=true;
                                break;
                            }
                        }
                        if(!flag){
                            listaListas.add(listaAux);
                        }
                        break;
                    }   
                }
            }
            for(int n=0; n<muxi.getEntradas()-1; n++){
                if(muxi.getConexionEntradas().get(n).isConectadoEntrada()){
                    Listas listaAux= new Listas();
                    for(int fe=0; fe<controlador.getDibujos().size();fe++){
                        if(muxi.getConexionEntradas().get(n).getElementoConectadoEntrada().equals(controlador.getDibujos().get(fe).getDibujo().getText())){
                            Componente aux2= controlador.getElementos().get(fe);
                            LinkedList auxList= new LinkedList();
                            for(int ju=0; ju<lista.size()-1; ju++){
                                auxList.add(lista.get(ju));
                                Componente w= (Componente) lista.get(ju);
                                if("mux".equals(w.getNombre())){
                                     break;
                                }
                            }
                            añadirComponentesConectados(auxList, aux2);
                            switch(muxi.getEntradas()){
                                case 2:
                                    if(n==0){
                                       listaAux.setN(1550); 
                                       break;
                                    }
                                    
                                case 4:
                                    if(n==0){
                                       listaAux.setN(1530); 
                                       break;
                                    }
                                    else if(n==1){
                                       listaAux.setN(1550);
                                       break;
                                    }
                                    else if(n==2){
                                       listaAux.setN(1570); 
                                       break;
                                    }
                                    
                                case 8:
                                    if(n==0){
                                       listaAux.setN(1490); 
                                       break;
                                    }
                                    else if(n==1){
                                       listaAux.setN(1510);
                                       break;
                                    }
                                    else if(n==2){
                                       listaAux.setN(1530);
                                       break;
                                    }
                                    else if(n==3){
                                       listaAux.setN(1550); 
                                    }
                                    else if(n==4){
                                       listaAux.setN(1570);
                                       break;
                                    }
                                    else if(n==5){
                                       listaAux.setN(1590);
                                       break;
                                    }
                                    else if(n==6){
                                       listaAux.setN(1610); 
                                       break;
                                    }  
                            }
                            listaAux.setLista(auxList);
                            Boolean flag=false;
                            for(int j=0; j<listaListas.size(); j++){
                                if(listaAux.getN()==listaListas.get(j).getN()){
                                    flag=true;
                                    break;
                                }
                            }
                            if(!flag){
                                listaListas.add(listaAux);
                            }
                            break;
                        }   
                    }
                }       
            } 
        }
        return lista;
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
        this.elem=elem;
    }
    
    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento 
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param elem Elemento grafico
     * @param potenciaController Controlador del medidor de potencia
     */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,ElementoGrafico elem, VentanaPotenciaController potenciaController) {
        this.elem=elem;
        this.potenciaControl=potenciaController;
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        try{
            LinkedList<Componente> ele=verComponentesConectados();
            FBG fbg = new FBG();
            if(ele.get(1).getNombre().contains("fbg")){
                fbg = (FBG) ele.get(1);
                if(fbg.getElementoConectadoSalida().equals(elem.getDibujo().getText())){
                    lblTitulo.setText("Power Meter - Transmission");
                }
                else{
                    lblTitulo.setText("Power Meter - Reflection");
                }
            }
            else{
                lblTitulo.setText("Power Meter");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
