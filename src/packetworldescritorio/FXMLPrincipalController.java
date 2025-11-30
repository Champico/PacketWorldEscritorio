/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import packetworldescritorio.pojo.Colaborador;


public class FXMLPrincipalController implements Initializable {

 private Colaborador colaboradorSesion;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRol;
    @FXML
    private Label lbNumPersonal;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        public void cargarInformacion(Colaborador colaborador){
        colaboradorSesion = colaborador;
        lbNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno());
        lbNumPersonal.setText("No. de personal " + colaborador.getNoPersonal());
        lbRol.setText("Rol: " + colaborador.getRol());
    }
    
}
