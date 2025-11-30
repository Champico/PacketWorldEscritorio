/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import packetworldescritorio.pojo.Colaborador;


public class FXMLPrincipalController implements Initializable {

 private Colaborador colaboradorSesion;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        public void cargarInformacion(Colaborador colaborador){
        colaboradorSesion = colaborador;
        /*lbNombre.setText(profesor.getNombre() + " " + profesor.getApellidoPaterno() + " " + profesor.getApellidoMaterno());
        lbNumPersonal.setText("No. de personal " + profesor.getNoPersonal());
        lbRol.setText("Rol: " + profesor.getRol());*/
    }
    
}
