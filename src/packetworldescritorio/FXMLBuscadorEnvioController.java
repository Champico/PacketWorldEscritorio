/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLBuscadorEnvioController implements Initializable, INavegableChild {

    private INavegacion nav;

    @FXML
    private TextArea taGuia;
    @FXML
    private Label lbErrorGuia;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_ENVIOS);
    }

        private void irPerfilEnvio(Envio envio) {
        nav.navegar(Constantes.PG_PERFIL_ENVIO, envio);
    }
        
    @FXML
    private void clickBuscarEnvio(ActionEvent event) {
        if (UIUtilidad.esInputVacio(taGuia, lbErrorGuia) == true) {
            return;
        }

        UIUtilidad.limpiarError(taGuia, lbErrorGuia);
        
        Envio envio = obtenerEnvioPorGuia(taGuia.getText().trim());
        
        if(envio != null){
            irPerfilEnvio(envio);
        }else{
            Utilidades.mostrarAlertaSimple("No encontrado", "No se encontro ningún paquete con esa guía", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }
    
    private Envio obtenerEnvioPorGuia(String guia){
        return EnvioImp.obtenerEnvioPorGuia(guia);
    }

}
