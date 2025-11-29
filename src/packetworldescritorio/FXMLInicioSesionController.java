/**  @authores  Pipe, Kevin, champ */

package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


public class FXMLInicioSesionController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickIniciarSesion(ActionEvent event) {
    }

    @FXML
    private void clickCerrar(ActionEvent event) {
        Platform.exit();
    }
    
}
