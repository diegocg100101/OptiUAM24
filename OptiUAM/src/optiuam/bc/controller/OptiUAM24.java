
package optiuam.bc.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Clase principal la cual se encarga de ejecutar la pantalla de inicio del
 * simulador
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Application
 */
public class OptiUAM24 extends Application {
    
    /**
    * Método start, encargado de mostrar la pantalla de inicio del simulador
    * @throws java.lang.Exception Proporciona diferentes excepciones lanzadas 
    * bajo el paquete java lang
    */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Inicio.fxml"));
        Parent root = loader.load();
        
        Image ico = new Image("/images/acercaDe.png"); 
        primaryStage.getIcons().add(ico);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Método main
     * @param args the command line arguments
     * @throws java.lang.Exception Proporciona diferentes excepciones lanzadas 
     * bajo el paquete java lang
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
