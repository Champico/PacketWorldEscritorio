/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;


public class FXMLPrincipalController implements Initializable, INavegableChild {

private INavegacion nav;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }
    

    
}
