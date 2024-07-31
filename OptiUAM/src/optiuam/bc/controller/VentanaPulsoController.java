package optiuam.bc.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 * Clase VentanaPulsoController para ventana informativa
 * @author Carlos Elizarraras
 */
public class VentanaPulsoController implements Initializable {
    private Stage stage; // Referencia al Stage de la ventana
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Label titleLabel;
    @FXML
    private Separator separator;
    @FXML
    private ImageView imageView2;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Button btnOk;
    
    
    /**
     * Metodo el cual inicializa la ventana
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Aquí puedes inicializar elementos de la interfaz de usuario y configurar eventos
    }

    /**
     * Método para establecer la referencia al Stage de la ventana.
     * Se llama desde el controlador que abre esta ventana.
     *
     * @param stage El Stage de la ventana
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método para cerrar la ventana.
     * Se llama desde otro controlador para cerrar esta ventana.
     */
    public void cerrarVentana() {
        stage.close();
    }
}
