/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;

public class FXMLModuloSucursalesController implements Initializable, INavegableChild {

        private INavegacion nav;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }
}
